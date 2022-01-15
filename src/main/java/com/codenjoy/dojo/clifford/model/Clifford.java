package com.codenjoy.dojo.clifford.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.clifford.model.items.*;
import com.codenjoy.dojo.clifford.model.items.clue.ClueGlove;
import com.codenjoy.dojo.clifford.model.items.clue.ClueKnife;
import com.codenjoy.dojo.clifford.model.items.clue.ClueRing;
import com.codenjoy.dojo.clifford.model.items.door.Door;
import com.codenjoy.dojo.clifford.model.items.door.Key;
import com.codenjoy.dojo.clifford.model.items.potion.Potion;
import com.codenjoy.dojo.clifford.model.items.robber.Robber;
import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.annotations.PerformanceOptimized;
import com.codenjoy.dojo.services.field.Accessor;
import com.codenjoy.dojo.services.field.Generator;
import com.codenjoy.dojo.services.field.PointField;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.services.round.RoundField;
import com.codenjoy.dojo.utils.whatsnext.WhatsNextUtils;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.codenjoy.dojo.clifford.model.items.door.KeyType.*;
import static com.codenjoy.dojo.clifford.model.items.potion.PotionType.MASK_POTION;
import static com.codenjoy.dojo.clifford.services.Event.Type.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.services.field.Generator.generate;

public class Clifford extends RoundField<Player, Hero> implements Field {

    private Level level;
    private PointField field;
    private List<Player> players;
    private Dice dice;
    private GameSettings settings;

    private int backWaysTimer;
    private Multimap<Hero, Hero> deathMatch;

    private int goldenKeys;
    private int silverKeys;
    private int bronzeKeys;

    public Clifford(Dice dice, Level level, GameSettings settings) {
        super(START_ROUND, WIN_ROUND, settings);

        this.level = level;
        this.dice = dice;
        this.settings = settings;
        this.field = new PointField();
        this.players = new LinkedList<>();

        clearScore();
    }

    private void generateAll() {
        generatePotions();
        generateClue();
        generateBackWays();
        generateRobbers();
        if (settings.bool(GENERATE_KEYS)) {
            generateKeys();
        }
    }

    @Override
    public void clearScore() {
        if (level == null) return;

        level.saveTo(field);
        field.init(this);

        goldenKeys = level.goldenKeys().size();
        silverKeys = level.silverKeys().size();
        bronzeKeys = level.bronzeKeys().size();
        deathMatch = LinkedHashMultimap.create();
        resetBackWaysTimer();
        generateAll();

        super.clearScore();
    }

    @Override
    public List<Player> players() {
        return players;
    }

    @Override
    protected void tickField() {
        bulletsGo();
        heroesGo();
        robbersGo();
        bricksGo();
        backWaysGo();

        rewardMurderers();
        generateAll();
    }

    private void diedFromHunter() {
        aliveActiveHeroes()
                .forEach(Hero::checkDiedFromHunter);
    }

    private void diedFromWall() {
        aliveActiveHeroes()
                .forEach(Hero::checkDiedFromWall);
    }

    @Override
    public void oneMoreDead(Player player, Object loseEvent) {
        if (!settings.bool(GENERATE_KEYS)) {
            generateKeys();
        }
        super.oneMoreDead(player, loseEvent);
    }

    private void rewardMurderers() {
        deathMatch.asMap().forEach((hunter, preys) -> {
            for (Hero prey : preys) {
                if (hunter == prey) {
                    hunter.event(SUICIDE);
                }
                if (!hunter.isAlive()) continue;

                hunter.fireKillHero(prey);
            }
        });
        deathMatch.clear();
    }

    private void generateClue() {
        generate(clueKnife(), size(),
                settings, CLUE_COUNT_KNIFE,
                player -> freeRandom((Player) player),
                ClueKnife::new);

        generate(clueGlove(), size(),
                settings, CLUE_COUNT_GLOVE,
                player -> freeRandom((Player) player),
                ClueGlove::new);

        generate(clueRing(), size(),
                settings, CLUE_COUNT_RING,
                player -> freeRandom((Player) player),
                ClueRing::new);
    }

    private void generatePotions() {
        generate(potions(), size(),
                settings, MASK_POTIONS_COUNT,
                player -> freeRandom((Player) player),
                pt -> new Potion(pt, MASK_POTION));
    }

    private void generateRobbers() {
        generate(robbers(), size(),
                settings, ROBBERS_COUNT,
                player -> freeRandom((Player) player),
                pt -> {
                    Robber robber = new Robber(pt, LEFT);
                    robber.init(this);
                    return robber;
                });
    }

    private void generateBackWays() {
        generate(backways(), size(),
                settings, BACKWAYS_COUNT,
                player -> freeRandom((Player) player),
                BackWay::new);
    }

    private void generateKeys() {
        generate(keys(), size(),
                goldenKeys - keys().filter(Key::golden).size(),
                player -> freeRandom((Player) player),
                pt -> new Key(pt, GOLD));

        generate(keys(), size(),
                silverKeys - keys().filter(Key::silver).size() ,
                player -> freeRandom((Player) player),
                pt -> new Key(pt, SILVER));

        generate(keys(), size(),
                bronzeKeys - keys().filter(Key::bronze).size(),
                player -> freeRandom((Player) player),
                pt -> new Key(pt, BRONZE));
    }

    private void bulletsGo() {
        for (Bullet bullet : bullets().copy()) {
            bullet.move();
        }
    }

    @Override
    public void affect(Bullet bullet) {
        for (Hero prey : heroes().getAt(bullet)) {
            if (prey.under(MASK_POTION)) continue;
            if (prey == bullet.getOwner() && !bullet.isBounced()) continue;

            prey.die();
            bullet.remove();
            deathMatch.put(bullet.getOwner(), prey);
        }

        for (Brick brick : bricks().getAt(bullet)) {
            if (brick.isNotTransparentForBullet()) {
                brick.crack(bullet.getOwner());
                bullet.remove();
            }
        }

        if (borders().contains(bullet)) {
            if (!bullet.isBounced()) {
                bullet.invertDirection();
            } else {
                bullet.remove();
            }
        }
    }

    private void bricksGo() {
        bricks().forEach(Brick::tick);
        diedFromWall();
        players.stream()
                .map(GamePlayer::getHero)
                .filter(hero -> !hero.isAlive())
                .forEach(prey -> bricks().getAt(prey).stream()
                        .map(Brick::getCrackedBy)
                        .filter(Objects::nonNull)
                        .forEach(hunter -> deathMatch.put(hunter, prey)));
    }

    private Optional<Brick> getBrick(Point pt) {
        return bricks().stream()
                .filter(brick -> brick.equals(pt))
                .findFirst();
    }

    private void heroesGo() {
        for (Player player : players) {
            Hero hero = player.getHero();
            if (!hero.isActiveAndAlive()) {
                continue;
            }

            hero.tick();
            diedFromHunter();

            if (bullets().contains(hero)) {
                bullets().getAt(hero).forEach(this::affect);
                if (!hero.isActiveAndAlive()) {
                    continue;
                }
            }

            if (isClueKnife(hero)) {
                clueKnife().removeAt(hero);
                hero.pickClue(GET_CLUE_KNIFE);
            }
            if (isClueGlove(hero)) {
                clueGlove().removeAt(hero);
                hero.pickClue(GET_CLUE_GLOVE);
            }
            if (isClueRing(hero)) {
                clueRing().removeAt(hero);
                hero.pickClue(GET_CLUE_RING);
            }

            if (keys().contains(hero)) {
                Key key = keys().getFirstAt(hero);
                hero.pick(key.getType());
                keys().removeAt(hero);
            }

            if (potions().contains(hero)) {
                potions().removeAt(hero);
                hero.pick(MASK_POTION);
            }

            if (isBackway(hero)) {
                transport(hero);
            }
        }
        heroes().stream()
                .filter(hero -> hero.under(MASK_POTION))
                .forEach(mask -> heroes().getAt(mask).stream()
                        .filter(hero -> hero != mask)
                        .filter(hero -> !hero.under(MASK_POTION))
                        .forEach(prey -> deathMatch.put(mask, prey)));
    }

    private void transport(PointImpl point) {
        List<BackWay> backways = backways().all();
        for (int index = 0; index < backways.size(); index++) {
            if (backways.get(index).equals(point)) {
                BackWay back = backways.get(index < backways.size() - 1 ? index + 1 : 0);
                point.move(back.getX(), back.getY());
                return;
            }
        }
    }

    private void robbersGo() {
        for (Robber robber : robbers().copy()) {
            robber.tick();
            diedFromHunter();

            if (isClueKnife(robber) && !robber.withClue()) {
                clueKnife().removeAt(robber);
                robber.getClue(ClueKnife.class);
            } else if (isClueGlove(robber) && !robber.withClue()) {
                clueGlove().removeAt(robber);
                robber.getClue(ClueGlove.class);
            } else if (isClueRing(robber) && !robber.withClue()) {
                clueRing().removeAt(robber);
                robber.getClue(ClueRing.class);
            }

            if (isBackway(robber)) {
                transport(robber);
            }
        }
    }

    @Override
    public boolean isBackway(Point pt) {
        return backways().contains(pt);
    }

    @Override
    public boolean isClueRing(Point pt) {
        return clueRing().contains(pt);
    }

    @Override
    public boolean isClueGlove(Point pt) {
        return clueGlove().contains(pt);
    }

    // TODO сделать чтобы каждый черный ход сам тикал свое время
    private void backWaysGo() {
        if (backWaysTimer == 0) {
            resetBackWaysTimer();
            backways().clear();
        } else {
            backWaysTimer--;
        }
    }

    private void resetBackWaysTimer() {
        backWaysTimer = Math.max(1, settings.integer(BACKWAY_TICKS));
    }

    @Override
    public boolean isBarrier(Point pt) {
        return pt.isOutOf(size())
                || isFullBrick(pt)
                || isBorder(pt)
                || isRegularHero(pt)
                || isClosedDoors(pt);
    }

    private boolean isClosedDoors(Point pt) {
        Door door = doors().getFirstAt(pt);
        return door != null
                && door.isClosed();
    }

    @Override
    public void suicide(Hero hero) {
        hero.getPlayer().event(SUICIDE);
    }

    @Override
    public boolean tryToCrack(Hero byHero, Point pt) {
        if (!isFullBrick(pt)) {
            return false;
        }

        Point over = UP.change(pt);
        if (isLadder(over)
                || isClueKnife(over)
                || isClueGlove(over)
                || isClueRing(over)
                || isFullBrick(over)
                || isHero(over)
                || isRobber(over)) {
            return false;
        }

        getBrick(pt).ifPresent(brick -> brick.crack(byHero));

        return true;
    }

    private boolean isClueKnife(Point over) {
        return clueKnife().contains(over);
    }

    @Override
    public boolean isRobber(Point pt) {
        return robbers().contains(pt);
    }

    @Override
    public boolean isPit(Point pt) {
        Point under = DOWN.change(pt);

        return !(isFullBrick(under)
                || isLadder(under)
                || isBorder(under)
                || isHero(under)
                || isRobber(under));
    }

    @Override
    public boolean isFullBrick(Point pt) {
        Brick brick = bricks().getFirstAt(pt);
        return brick != null
                && brick.isFull();
    }

    @Override
    public Optional<Point> freeRandom(Player player) {
        return Generator.freeRandom(size(), dice, this::isFree);
    }

    @Override
    public boolean isLadder(Point pt) {
        return ladder().contains(pt);
    }

    @Override
    public boolean isPipe(Point pt) {
        return pipe().contains(pt);
    }

    @Override
    public boolean isFree(Point pt) {
        return field.get(pt).isEmpty();
    }

    @Override
    public boolean isHero(Point pt) {
        return isHero(pt, hero -> true);
    }

    @Override
    public List<Hero> visibleHeroes() {
        // TODO test что воры не гонятся за точками спауна
        return heroes(Hero::isVisible);
    }

    @Override
    public boolean isRegularHero(Point pt) {
        return isHero(pt, hero -> !hero.under(MASK_POTION));
    }

    private boolean isHero(Point pt, Predicate<Hero> filter) {
        for (Player player : players) {
            Hero hero = player.getHero();
            if (hero.isActiveAndAlive()
                    && hero.itsMe(pt)
                    && filter.test(hero))
            {
                return true;
            }
        }
        return false;
    }

    private List<Hero> heroes(Predicate<Hero> filter) {
        List<Hero> result = new ArrayList<>(players.size());
        for (Player player : players) {
            Hero hero = player.getHero();
            if (hero.isActiveAndAlive() && filter.test(hero)) {
                result.add(hero);
            }
        }
        return result;
    }

    // TODO test
    //      может ли пройти через него вор - да
    //      можно ли простреливать под ним - да
    //      является ли место с ним дыркой - да
    //      является ли место с ним препятствием - нет


    @Override
    public Accessor<Hero> heroes() {
        return field.of(Hero.class);
    }

    @Override
    public boolean isBrick(Point pt) {
        return bricks().contains(pt);
    }

    @Override
    public boolean isHunter(Point pt) {
        return isRobber(pt)
                || isAnyHeroMaskAt(pt);
    }

    @PerformanceOptimized
    private boolean isAnyHeroMaskAt(Point pt) {
        for (Hero hero : heroes().getAt(pt)) {
            if (hero.under(MASK_POTION)) {
                if (hero.equals(pt)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void leaveClue(Point pt, Class<? extends Point> type) {
        if (type == ClueKnife.class) {
            clueKnife().add(new ClueKnife(pt));
        } else if (type == ClueGlove.class) {
            clueGlove().add(new ClueGlove(pt));
        } else if (type == ClueRing.class) {
            clueRing().add(new ClueRing(pt));
        }
    }

    @Override
    public int size() {
        return field.size();
    }

    @Override
    public boolean isBorder(Point pt) {
        return borders().contains(pt);
    }

    @Override
    protected void onAdd(Player player) {
        player.newHero(this);
    }

    @Override
    protected void onRemove(Player player) {
        heroes().removeExact(player.getHero());
    }

    @Override
    public GameSettings settings() {
        return settings;
    }

    @Override
    public BoardReader<Player> reader() {
        return field.reader(
                Hero.class,
                Robber.class,
                ClueKnife.class,
                ClueGlove.class,
                ClueRing.class,
                Border.class,
                Brick.class,
                Ladder.class,
                Potion.class,
                Pipe.class,
                BackWay.class,
                Door.class,
                Key.class,
                Bullet.class);
    }

    @Override
    public List<Player> load(String board, Function<Hero, Player> player) {
        level = new Level(board);
        return WhatsNextUtils.load(this, level.heroes(), player);
    }

    public Accessor<BackWay> backways() {
        return field.of(BackWay.class);
    }

    public Accessor<ClueKnife> clueKnife() {
        return field.of(ClueKnife.class);
    }

    public Accessor<ClueGlove> clueGlove() {
        return field.of(ClueGlove.class);
    }

    public Accessor<ClueRing> clueRing() {
        return field.of(ClueRing.class);
    }

    public Accessor<Border> borders() {
        return field.of(Border.class);
    }

    @Override
    public Accessor<Robber> robbers() {
        return field.of(Robber.class);
    }

    @Override
    public Accessor<Brick> bricks() {
        return field.of(Brick.class);
    }

    public Accessor<Ladder> ladder() {
        return field.of(Ladder.class);
    }

    public Accessor<Potion> potions() {
        return field.of(Potion.class);
    }

    public Accessor<Pipe> pipe() {
        return field.of(Pipe.class);
    }

    public int getBackWaysTimer() {
        return backWaysTimer;
    }

    @Override
    public Accessor<Door> doors() {
        return field.of(Door.class);
    }

    @Override
    public Accessor<Key> keys() {
        return field.of(Key.class);
    }

    public Accessor<Bullet> bullets() {
        return field.of(Bullet.class);
    }

    public int countPlayers() {
        return players.size();
    }
}

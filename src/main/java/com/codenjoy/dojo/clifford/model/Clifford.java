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
import com.codenjoy.dojo.clifford.model.items.potion.PotionType;
import com.codenjoy.dojo.clifford.model.items.robber.Robber;
import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.games.clifford.Element;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.codenjoy.dojo.clifford.model.items.door.KeyType.*;
import static com.codenjoy.dojo.clifford.model.items.potion.PotionType.MASK_POTION;
import static com.codenjoy.dojo.clifford.services.Event.Type.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.services.field.Generator.generate2;
import static java.util.stream.Collectors.toList;

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
        List<Point> free = freeForObjects();

        generatePotions(free);
        generateClue(free);
        generateBackWays(free);
        generateRobbers(free);
        if (settings.bool(GENERATE_KEYS)) {
            generateKeys(free);
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
            generateKeys(freeForObjects());
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

    private void generateClue(List<Point> free) {
        generate2(clueKnife(), dice,
                settings, CLUE_COUNT_KNIFE,
                () -> free,
                ClueKnife::new);

        generate2(clueGlove(), dice,
                settings, CLUE_COUNT_GLOVE,
                () -> free,
                ClueGlove::new);

        generate2(clueRing(), dice,
                settings, CLUE_COUNT_RING,
                () -> free,
                ClueRing::new);
    }

    private void generatePotions(List<Point> free) {
        generate2(potions(), dice,
                settings, MASK_POTIONS_COUNT,
                () -> free,
                pt -> new Potion(pt, MASK_POTION));
    }

    private void generateRobbers(List<Point> free) {
        generate2(robbers(), dice,
                settings, ROBBERS_COUNT,
                () -> free,
                pt -> {
                    Robber robber = new Robber(pt, LEFT);
                    robber.init(this);
                    return robber;
                });
    }

    private void generateBackWays(List<Point> free) {
        generate2(backways(), dice,
                settings, BACKWAYS_COUNT,
                () -> free,
                BackWay::new);
    }

    private void generateKeys(List<Point> free) {
        generate2(keys(), dice,
                goldenKeys - keys().filter(Key::golden).size(),
                () -> free,
                pt -> new Key(pt, GOLD));

        generate2(keys(), dice,
                silverKeys - keys().filter(Key::silver).size() ,
                () -> free,
                pt -> new Key(pt, SILVER));

        generate2(keys(), dice,
                bronzeKeys - keys().filter(Key::bronze).size(),
                () -> free,
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

            if (clueKnife().contains(hero)) {
                clueKnife().removeAt(hero);
                hero.pickClue(GET_CLUE_KNIFE);
            }
            if (clueGlove().contains(hero)) {
                clueGlove().removeAt(hero);
                hero.pickClue(GET_CLUE_GLOVE);
            }
            if (clueRing().contains(hero)) {
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

            if (backways().contains(hero)) {
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

            if (clueKnife().contains(robber) && !robber.withClue()) {
                clueKnife().removeAt(robber);
                robber.getClue(ClueKnife.class);
            } else if (clueGlove().contains(robber) && !robber.withClue()) {
                clueGlove().removeAt(robber);
                robber.getClue(ClueGlove.class);
            } else if (clueRing().contains(robber) && !robber.withClue()) {
                clueRing().removeAt(robber);
                robber.getClue(ClueRing.class);
            }

            if (backways().contains(robber)) {
                transport(robber);
            }
        }
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
        return pt.getX() > size() - 1 || pt.getX() < 0
                || pt.getY() < 0 || pt.getY() > size() - 1
                || isFullBrick(pt)
                || isBorder(pt)
                || (isHero(pt) && !under(pt, MASK_POTION))
                || doors().getAt(pt).stream().anyMatch(Door::isClosed);
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
                || clueKnife().contains(over)
                || clueGlove().contains(over)
                || clueRing().contains(over)
                || isFullBrick(over)
                || isHero(over)
                || robbers().contains(over)) {
            return false;
        }

        getBrick(pt).ifPresent(brick -> brick.crack(byHero));

        return true;
    }

    @Override
    public boolean isPit(Point pt) {
        Point under = DOWN.change(pt);

        return !(isFullBrick(under)
                || isLadder(under)
                || isBorder(under)
                || isHero(under)
                || robbers().contains(under));
    }

    @Override
    public boolean isFullBrick(Point pt) {
        return bricks().getAt(pt).stream()
                .anyMatch(brick -> brick.state(null) == Element.BRICK);
    }

    @Override
    public Optional<Point> freeRandom(Player player) {
        return Generator.freeRandom(size(), dice, this::isFree);
    }

    @PerformanceOptimized
    public List<Point> freeForObjects() {
        return field.pointsMatch(this::freeForObject);
    }

    private boolean freeForObject(List<Point> objects) {
        return (objects == null || objects.isEmpty());
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
        return aliveActiveHeroes()
                .anyMatch(hero -> hero.itsMe(pt));
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
        return robbers().contains(pt) || isAnyHeroMaskAt(pt);
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
    public boolean under(Point pt, PotionType potion) {
        return heroes().stream()
                .filter(hero -> hero.equals(pt))
                .anyMatch(hero -> hero.under(potion));
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

    @Override
    public List<Hero> visibleHeroes() {
        return aliveActiveHeroes()   // TODO test что воры не гонятся за точками спауна
                .filter(Hero::isVisible)
                .collect(toList());
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

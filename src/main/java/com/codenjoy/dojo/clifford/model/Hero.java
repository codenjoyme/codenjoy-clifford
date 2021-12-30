package com.codenjoy.dojo.clifford.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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


import com.codenjoy.dojo.clifford.model.items.Bullet;
import com.codenjoy.dojo.clifford.model.items.Ladder;
import com.codenjoy.dojo.clifford.model.items.Pipe;
import com.codenjoy.dojo.clifford.model.items.Potion.PotionType;
import com.codenjoy.dojo.clifford.model.items.door.Door;
import com.codenjoy.dojo.clifford.model.items.door.KeyType;
import com.codenjoy.dojo.clifford.services.Event;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.State;
import com.codenjoy.dojo.services.StateUtils;
import com.codenjoy.dojo.services.round.RoundPlayerHero;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.codenjoy.dojo.clifford.services.Event.Type.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.MASK_TICKS;
import static com.codenjoy.dojo.games.clifford.Element.*;
import static com.codenjoy.dojo.services.Direction.DOWN;
import static com.codenjoy.dojo.services.StateUtils.filter;
import static com.codenjoy.dojo.services.StateUtils.filterOne;

public class Hero extends RoundPlayerHero<Field> implements State<Element, Player> {

    private static final int ACT_SUICIDE = 0;
    private static final int ACT_SHOOT = 1;
    private static final int ACT_OPEN_DOOR = 2;
    private static final int ACT_CLOSE_DOOR = 3;

    protected Direction direction;
    private PotionType potion;
    private int potionTicks;
    private Map<KeyType, Integer> keys;
    private boolean moving;
    private boolean crack;
    private boolean jump;
    private boolean openDoor;
    private boolean closeDoor;
    private boolean shoot;
    private int score;
    private int rings;
    private int gloves;
    private int knives;

    public Hero(Point pt, Direction direction) {
        super(pt);
        this.direction = direction;
        moving = false;
        crack = false;
        jump = false;
        openDoor = false;
        closeDoor = false;
        clearScores();
    }

    public void clearScores() {
        score = 0;
        rings = 0;
        gloves = 0;
        knives = 0;
        potion = null;
        keys = new EnumMap<>(KeyType.class) {{
            put(KeyType.GOLD, 0);
            put(KeyType.SILVER, 0);
            put(KeyType.BRONZE, 0);
        }};
    }

    public void addScore(int added) {
        score = Math.max(0, score + added);
    }

    public Map<KeyType, Integer> getKeys() {
        return Collections.unmodifiableMap(keys);
    }

    @Override
    public void init(Field field) {
        super.init(field);

        field.heroes().add(this);
    }

    @Override
    public void down() {
        if (!isActiveAndAlive()) return;

        if (field.isLadder(this) || field.isLadder(underHero())) {
            direction = DOWN;
            moving = true;
        } else if (field.isPipe(this)) {
            jump = true;
        }
    }

    @Override
    public void up() {
        if (!isActiveAndAlive()) return;

        if (field.isLadder(this)) {
            direction = Direction.UP;
            moving = true;
        }
    }

    @Override
    public void left() {
        if (!isActiveAndAlive()) return;

        direction = Direction.LEFT;
        moving = true;
    }

    @Override
    public void right() {
        if (!isActiveAndAlive()) return;

        direction = Direction.RIGHT;
        moving = true;
    }

    @Override
    public void act(int... p) {
        if (!isActiveAndAlive()) return;

        if (p.length == 0) {
            crack = true;
        } else if (p[0] == ACT_SUICIDE) {
            die();
            field.suicide(this);
        } else if (p[0] == ACT_SHOOT) {
            shoot = true;
        } else if (p[0] == ACT_OPEN_DOOR) {
            openDoor = true;
        } else if (p[0] == ACT_CLOSE_DOOR) {
            closeDoor = true;
        }
    }

    public void crack() {
        act();
    }

    public void crack(Direction direction) {
        switch (direction) {
            case LEFT: crack(); left(); break;
            case RIGHT: crack(); right(); break;
        }
    }

    public void shoot() {
        act(ACT_SHOOT);
    }

    public void shoot(Direction direction) {
        switch (direction) {
            case LEFT: shoot(); left(); break;
            case RIGHT: shoot(); right(); break;
        }
    }

    public void openDoor() {
        act(ACT_OPEN_DOOR);
    }

    public void openDoor(Direction direction) {
        switch (direction) {
            case LEFT: openDoor(); left(); break;
            case RIGHT: openDoor(); right(); break;
        }
    }

    public void closeDoor() {
        act(ACT_CLOSE_DOOR);
    }

    public void closeDoor(Direction direction) {
        switch (direction) {
            case LEFT: closeDoor(); left(); break;
            case RIGHT: closeDoor(); right(); break;
        }
    }

    public void suicide() {
        act(ACT_SUICIDE);
    }

    @Override
    public void die() {
        die(HERO_DIED);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void tick() {
        if (!isActiveAndAlive()) return;

        Point destination = direction.change(this);

        if (field.doors().contains(destination)) {
            if (openDoor) {
                tryToInteractWithDoor(destination, Door::isClosed, Door::open);
            } else if (closeDoor) {
                tryToInteractWithDoor(destination, Door::isOpened, Door::close);
            }
        }

        if (isFall()) {
            move(DOWN);
        } else if (shoot) {
            shootBullet();
        } else if (crack) {
            Point hole = DOWN.change(destination);
            field.tryToCrack(this, hole);
        } else if (moving || jump) {
            Point dest;
            if (jump) {
                dest = DOWN.change(this);
            } else {
                dest = destination;
            }

            boolean noPhysicalBarrier = !field.isBarrier(dest);
            boolean victim = isRegularPlayerAt(dest) && isMask();
            if (noPhysicalBarrier || victim) {
                move(dest);
            }
        }
        crack = false;
        moving = false;
        jump = false;
        openDoor = false;
        closeDoor = false;
        shoot = false;
        dissolvePotions();
    }

    private void shootBullet() {
        Bullet bullet = new Bullet(this);
        field.bullets().add(bullet);
        bullet.doFirstMoveAffect();
    }

    private void tryToInteractWithDoor(Point destination,
                                       Predicate<Door> validState, Consumer<Door> action) {
        field.doors().getAt(destination).stream()
                .filter(validState)
                .forEach(door -> {
                    Integer availableKeys = keys.getOrDefault(door.type(), 0);
                    if (availableKeys > 0) {
                        action.accept(door);
                        keys.put(door.type(), availableKeys - 1);
                    }
                });
    }

    private boolean isMask() {
        return under(PotionType.MASK_POTION);
    }

    private boolean isRegularPlayerAt(Point pt) {
        return field.isHero(pt)
                && !field.under(pt, PotionType.MASK_POTION);
    }

    private void dissolvePotions() {
        potionTicks--;
        if (potionTicks < 0) {
            potionTicks = 0;
            potion = null;
        }
    }

    @Override
    public int scores() {
        return score;
    }

    public boolean isVisible() {
        return !under(PotionType.MASK_POTION);
    }

    public boolean under(PotionType potion) {
        return this.potion == potion;
    }

    public void pick(PotionType potion) {
        this.potion = potion;
        // TODO test +=
        potionTicks += settings().integer(MASK_TICKS);
    }

    public void pick(KeyType key) {
        keys.put(key, keys.getOrDefault(key, 0) + 1);
    }

    // TODO test me
    public void checkDiedFromHunter() {
        if (!super.isActive()) return;
        if (isMask()) return;

        if (field.isHunter(this)) {
            die();
        }
    }

    // TODO test me
    public void checkDiedFromWall() {
        if (!super.isActive()) return;

        if (field.isFullBrick(this)) {
            die();
        }
    }

    public boolean isFall() {
        return (field.isPit(this) && !field.isPipe(this) && !field.isLadder(this))
                || (isMask() && isRegularPlayerAt(underHero()));
    }

    private Point underHero() {
        return DOWN.change(this);
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        if (StateUtils.itsMe(player, this, alsoAtPoint, player.getHero())) {
            Hero hero = player.getHero();
            Element state = hero.state(alsoAtPoint);
            return hero.isMask()
                    ? state.mask()
                    : state;
        } else {
            Element state = state(alsoAtPoint);
            state = anyHeroFromAnotherTeam(player, filter(alsoAtPoint, Hero.class))
                    ? state.enemyHero()
                    : state.otherHero();
            return isMask()
                    ? state.mask()
                    : state;
        }
    }

    private Element state(Object[] alsoAtPoint) {
        Ladder ladder = filterOne(alsoAtPoint, Ladder.class);
        Pipe pipe = filterOne(alsoAtPoint, Pipe.class);

        if (!isAlive() || !isActive()) {
            return HERO_DIE;
        }

        if (ladder != null) {
            return HERO_LADDER;
        }

        if (pipe != null) {
            return HERO_PIPE;
        }

        if (isPit()) {
            return HERO_PIT;
        }

        if (isFall()) {
            return HERO_FALL;
        }

        return isLeftTurn()
                ? HERO_LEFT
                : HERO_RIGHT;
    }

    private boolean isPit() {
        return field.isBrick(this) && field.isBarrier(underHero());
    }

    private boolean isLeftTurn() {
        return direction.equals(Direction.LEFT);
    }

    private boolean anyHeroFromAnotherTeam(Player player, List<Hero> heroes) {
        return heroes.stream()
                .anyMatch(h -> player.getTeamId() != h.getPlayer().getTeamId());
    }

    public void pickClue(Event.Type clue) {
        getPlayer().event(new Event(clue).with(increaseClue(clue)));
    }

    private int increaseClue(Event.Type clue) {
        switch (clue) {
            case GET_CLUE_KNIFE :
                return ++knives;
            case GET_CLUE_RING :
                return ++rings;
            case GET_CLUE_GLOVE :
                return ++gloves;
        }
        return 0;
    }

    public int getTeamId() {
        return getPlayer().getTeamId();
    }

    public void fireKillHero(Hero prey) {
        if (getTeamId() == prey.getTeamId()) {
            event(KILL_OTHER_HERO);
        } else {
            event(KILL_ENEMY_HERO);
        }
    }
}

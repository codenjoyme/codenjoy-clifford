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


import com.codenjoy.dojo.clifford.model.items.*;
import com.codenjoy.dojo.clifford.model.items.Potion.PotionType;
import com.codenjoy.dojo.clifford.model.items.clue.ClueGlove;
import com.codenjoy.dojo.clifford.model.items.clue.ClueKnife;
import com.codenjoy.dojo.clifford.model.items.clue.ClueRing;
import com.codenjoy.dojo.clifford.model.items.door.Door;
import com.codenjoy.dojo.clifford.model.items.door.Key;
import com.codenjoy.dojo.clifford.model.items.robber.Robber;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.field.AbstractLevel;
import com.codenjoy.dojo.services.field.PointField;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.codenjoy.dojo.clifford.model.items.door.Door.State.CLOSED;
import static com.codenjoy.dojo.clifford.model.items.door.Door.State.OPENED;
import static com.codenjoy.dojo.clifford.model.items.door.KeyType.*;
import static com.codenjoy.dojo.games.clifford.Element.*;

public class Level extends AbstractLevel {

    public Level(String map) {
        super(map);
    }

    public List<Hero> heroes() {
        EnumSet<Element> left = EnumSet.of(
                HERO_CRACK_LEFT, HERO_LEFT, HERO_FALL_LEFT, HERO_PIPE_LEFT,
                HERO_MASK_CRACK_LEFT,
                HERO_MASK_LEFT, HERO_MASK_FALL_LEFT, HERO_MASK_PIPE_LEFT);

        EnumSet<Element> right = EnumSet.of(
                HERO_CRACK_RIGHT, HERO_RIGHT, HERO_FALL_RIGHT, HERO_PIPE_RIGHT,
                HERO_MASK_CRACK_RIGHT,
                HERO_MASK_RIGHT, HERO_MASK_FALL_RIGHT, HERO_MASK_PIPE_RIGHT);

        return find(new LinkedHashMap<>() {{
            left.forEach(element -> put(element, pt -> new Hero(pt, Direction.LEFT)));
            right.forEach(element -> put(element, pt -> new Hero(pt, Direction.RIGHT)));
        }});
    }

    public List<Brick> bricks() {
        return find(Brick::new, BRICK);
    }

    public List<Border> borders() {
        return find(Border::new, STONE);
    }

    public List<ClueKnife> clueKnife() {
        return find(ClueKnife::new, CLUE_KNIFE);
    }

    public List<ClueGlove> clueGlove() {
        return find(ClueGlove::new, CLUE_GLOVE);
    }

    public List<ClueRing> clueRing() {
        return find(ClueRing::new, CLUE_RING);
    }

    public List<Ladder> ladder() {
        return find(Ladder::new, LADDER);
    }

    public List<Pipe> pipe() {
        return find(Pipe::new, PIPE);
    }

    public List<Robber> robbers() {
        return find(new HashMap<>() {{
            put(ROBBER_LEFT, pt -> new Robber(pt, Direction.LEFT));
            put(ROBBER_RIGHT, pt -> new Robber(pt, Direction.RIGHT));
        }});
    }

    public List<Potion> potions() {
        return find(pt -> new Potion(pt, PotionType.MASK_POTION), MASK_POTION);
    }

    public List<Backway> backways() {
        return find(Backway::new, BACKWAY);
    }

    public List<Door> doors() {
        return find(new HashMap<>() {{
            put(OPENED_DOOR_GOLD, pt -> new Door(pt, OPENED, GOLD));
            put(OPENED_DOOR_SILVER, pt -> new Door(pt, OPENED, SILVER));
            put(OPENED_DOOR_BRONZE, pt -> new Door(pt, OPENED, BRONZE));
            put(CLOSED_DOOR_GOLD, pt -> new Door(pt, CLOSED, GOLD));
            put(CLOSED_DOOR_SILVER, pt -> new Door(pt, CLOSED, SILVER));
            put(CLOSED_DOOR_BRONZE, pt -> new Door(pt, CLOSED, BRONZE));
        }});
    }

    public List<Key> keys() {
        return find(new HashMap<>() {{
            put(KEY_GOLD, pt -> new Key(pt, GOLD));
            put(KEY_SILVER, pt -> new Key(pt, SILVER));
            put(KEY_BRONZE, pt -> new Key(pt, BRONZE));
        }});
    }

    @Override
    protected void fill(PointField field) {
        field.addAll(borders());
        field.addAll(pipe());
        field.addAll(ladder());
        field.addAll(bricks());
        field.addAll(backways());
        field.addAll(potions());
        field.addAll(clueKnife());
        field.addAll(clueGlove());
        field.addAll(clueRing());
        field.addAll(robbers());
        field.addAll(doors());
        field.addAll(keys());
    }
}
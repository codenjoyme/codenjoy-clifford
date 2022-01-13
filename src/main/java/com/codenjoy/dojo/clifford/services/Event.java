package com.codenjoy.dojo.clifford.services;

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

import com.codenjoy.dojo.services.event.EventObject;

public class Event implements EventObject<Event.Type, Integer> {

    private Type type;
    private int value;

    public enum Type {

        START_ROUND,      // раунд стартовал
        WIN_ROUND,        // герой победил в раунде

        // TODO: implement KILL_ROBBER (for this time robber cannot be killed);
        KILL_OTHER_HERO,  // герой замуровал в стенке другого героя
        KILL_ENEMY_HERO,  // герой замуровал в стенке другого вражеского героя
        HERO_DIED,        // героя убили

        GET_CLUE_KNIFE,   // подобрано улику
        GET_CLUE_GLOVE,
        GET_CLUE_RING,

        SUICIDE,          // герой заблудился и решил суициднуться
    }

    public Event with(int amount) {
        this.value = amount;
        return this;
    }

    @Override
    public String toString() {
        return type + ((value != 0)?("(" + value + ")"):"");
    }

    public Event(Type type) {
        this.type = type;
    }

    public Event(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public Type type() {
        return type;
    }
}

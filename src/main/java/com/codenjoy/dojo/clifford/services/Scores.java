package com.codenjoy.dojo.clifford.services;

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


import com.codenjoy.dojo.services.event.AbstractScores;
import com.codenjoy.dojo.services.settings.SettingsReader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.codenjoy.dojo.clifford.services.Event.Type.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class Scores extends AbstractScores<Integer> {
    
    public Scores(int score, SettingsReader settings) {
        super(score, settings);
    }

    @Override
    protected Map<Object, Function<Integer, Integer>> eventToScore() {
        return map(settings);
    }

    public static Map<Object, Function<Integer, Integer>> map(SettingsReader settings) {
        return new HashMap<>(){{
            put(Event.Type.GET_CLUE_KNIFE,
                    value -> settings.integer(CLUE_SCORE_KNIFE)
                            + value * settings.integer(CLUE_SCORE_KNIFE_INCREMENT));

            put(Event.Type.GET_CLUE_GLOVE,
                    value -> settings.integer(CLUE_SCORE_GLOVE)
                            + value * settings.integer(CLUE_SCORE_GLOVE_INCREMENT));

            put(Event.Type.GET_CLUE_RING,
                    value -> settings.integer(CLUE_SCORE_RING)
                            + value * settings.integer(CLUE_SCORE_RING_INCREMENT));

            put(Event.Type.KILL_HERO,
                    value -> settings.integer(KILL_HERO_SCORE));

            put(Event.Type.KILL_ENEMY,
                    value -> settings.integer(KILL_ENEMY_SCORE));

            put(Event.Type.HERO_DIE,
                    value -> settings.integer(HERO_DIE_PENALTY));

            put(Event.Type.SUICIDE,
                    value -> settings.integer(SUICIDE_PENALTY));

            put(Event.Type.WIN_ROUND,
                    value -> settings.integer(ROUND_WIN));
        }};
    }
}
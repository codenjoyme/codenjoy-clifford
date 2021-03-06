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


import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.services.settings.SettingsReader;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class Scores extends ScoresMap<Integer> {
    
    public Scores(SettingsReader settings) {
        super(settings);

        put(Event.Type.GET_CLUE_KNIFE,
                value -> settings.integer(CLUE_SCORE_KNIFE)
                        + value * settings.integer(CLUE_SCORE_KNIFE_INCREMENT));

        put(Event.Type.GET_CLUE_GLOVE,
                value -> settings.integer(CLUE_SCORE_GLOVE)
                        + value * settings.integer(CLUE_SCORE_GLOVE_INCREMENT));

        put(Event.Type.GET_CLUE_RING,
                value -> settings.integer(CLUE_SCORE_RING)
                        + value * settings.integer(CLUE_SCORE_RING_INCREMENT));

        put(Event.Type.KILL_OTHER_HERO,
                value -> settings.integer(KILL_OTHER_HERO_SCORE));

        put(Event.Type.KILL_ENEMY_HERO,
                value -> settings.integer(KILL_ENEMY_HERO_SCORE));

        put(Event.Type.HERO_DIED,
                value -> heroDie(HERO_DIED_PENALTY));

        put(Event.Type.SUICIDE,
                value -> settings.integer(SUICIDE_PENALTY));

        put(Event.Type.WIN_ROUND,
                value -> settings.integer(ROUND_WIN_SCORE));
    }
}
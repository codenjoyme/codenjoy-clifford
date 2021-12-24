package com.codenjoy.dojo.clifford;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2019 Codenjoy
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

import com.codenjoy.dojo.clifford.services.GameSettings;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;

public class TestGameSettings extends GameSettings {

    /**
     * Here you can override the settings for all tests.
     */
    public TestGameSettings() {
        bool(ROUNDS_ENABLED, false);

        integer(HERO_DIE_PENALTY, -0);
        integer(KILL_HERO_SCORE, 20);
        integer(KILL_ENEMY_SCORE, 50);

        integer(SUICIDE_PENALTY, -0);
        integer(MASK_TICKS, 15);
        integer(MASK_POTIONS_COUNT, 0);
        integer(BACKWAY_TICKS, 10);
        integer(BACKWAYS_COUNT, 0);

        integer(CLUE_COUNT_KNIFE, -1);
        integer(CLUE_COUNT_GLOVE, 0);
        integer(CLUE_COUNT_RING, 0);
        integer(CLUE_SCORE_KNIFE, 1);
        integer(CLUE_SCORE_GLOVE, 5);
        integer(CLUE_SCORE_RING, 10);

        integer(ROBBERS_COUNT, 0);
    }
}
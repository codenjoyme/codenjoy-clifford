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

import com.codenjoy.dojo.client.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameSettingsTest {

    @Test
    public void shouldGetAllKeys() {
        assertEquals("[MASK_POTIONS_COUNT, \n" +
                        "MASK_TICKS, \n" +
                        "BACKWAYS_COUNT, \n" +
                        "BACKWAY_TICKS, \n" +
                        "ROBBERS_COUNT, \n" +
                        "GENERATE_KEYS, \n" +
                        "CLUE_COUNT_GLOVE, \n" +
                        "CLUE_SCORE_GLOVE, \n" +
                        "CLUE_SCORE_GLOVE_INCREMENT, \n" +
                        "CLUE_COUNT_KNIFE, \n" +
                        "CLUE_SCORE_KNIFE, \n" +
                        "CLUE_SCORE_KNIFE_INCREMENT, \n" +
                        "CLUE_COUNT_RING, \n" +
                        "CLUE_SCORE_RING, \n" +
                        "CLUE_SCORE_RING_INCREMENT, \n" +
                        "HANDGUN_TICKS_PER_SHOOT, \n" +
                        "HANDGUN_CLIP_SIZE, \n" +
                        "HANDGUN_UNLIMITED_AMMO, \n" +
                        "ROUND_WIN_SCORE, \n" +
                        "KILL_OTHER_HERO_SCORE, \n" +
                        "KILL_ENEMY_HERO_SCORE, \n" +
                        "HERO_DIED_PENALTY, \n" +
                        "SUICIDE_PENALTY, \n" +
                        "SCORE_COUNTING_TYPE]",
                Utils.split(new GameSettings().allKeys(), ", \n"));
    }
}
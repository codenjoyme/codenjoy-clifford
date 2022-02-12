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

import com.codenjoy.dojo.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameSettingsTest {

    @Test
    public void shouldGetAllKeys() {
        assertEquals("MASK_POTIONS_COUNT         =[Game] Mask potions count\n" +
                        "MASK_TICKS                 =[Game] Mask ticks\n" +
                        "BACKWAYS_COUNT             =[Game] Back ways count\n" +
                        "BACKWAY_TICKS              =[Game] Back way ticks\n" +
                        "ROBBERS_COUNT              =[Game] Robbers count\n" +
                        "GENERATE_KEYS              =[Game] Generate picked keys\n" +
                        "CLUE_COUNT_GLOVE           =[Game] Glove clue count\n" +
                        "CLUE_SCORE_GLOVE           =[Score] Glove clue score\n" +
                        "CLUE_SCORE_GLOVE_INCREMENT =[Score] Glove clue score increment\n" +
                        "CLUE_COUNT_KNIFE           =[Game] Knife clue count\n" +
                        "CLUE_SCORE_KNIFE           =[Score] Knife clue score\n" +
                        "CLUE_SCORE_KNIFE_INCREMENT =[Score] Knife clue score increment\n" +
                        "CLUE_COUNT_RING            =[Game] Ring clue count\n" +
                        "CLUE_SCORE_RING            =[Score] Ring clue score\n" +
                        "CLUE_SCORE_RING_INCREMENT  =[Score] Ring clue score increment\n" +
                        "HANDGUN_TICKS_PER_SHOOT    =[Game] Gun recharge\n" +
                        "HANDGUN_CLIP_SIZE          =[Game] Count of bullet by default\n" +
                        "HANDGUN_UNLIMITED_AMMO     =[Game] unlimited ammo\n" +
                        "ROUND_WIN_SCORE            =[Score] Round win\n" +
                        "KILL_OTHER_HERO_SCORE      =[Score] Kill hero score\n" +
                        "KILL_ENEMY_HERO_SCORE      =[Score] Kill enemy score\n" +
                        "HERO_DIED_PENALTY          =[Score] Hero die penalty\n" +
                        "SUICIDE_PENALTY            =[Score] Suicide penalty\n" +
                        "SCORE_COUNTING_TYPE        =[Score] Counting score mode",
                TestUtils.toString(new GameSettings().allKeys()));
    }
}
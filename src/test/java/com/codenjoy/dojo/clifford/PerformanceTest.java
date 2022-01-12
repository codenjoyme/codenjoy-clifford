package com.codenjoy.dojo.clifford;

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

import com.codenjoy.dojo.client.local.DiceGenerator;
import com.codenjoy.dojo.clifford.services.GameRunner;
import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.ROBBERS_COUNT;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;
import static com.codenjoy.dojo.utils.TestUtils.assertPerformance;

public class PerformanceTest {

    @Test
    public void test() {

        // about 7.3 sec
        int robbers = 4;
        int players = 100;
        int ticks = 100;

        int expectedCreation = 1300;
        int expectedTick = 20000;
        int expectedPrint = 3000;

        Dice dice = new DiceGenerator().getDice(2000);
        GameRunner runner = new GameRunner(){

            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                return new GameSettings()
                    .bool(ROUNDS_ENABLED, false)
                    .integer(ROBBERS_COUNT, robbers);
            }
        };

        boolean printBoard = false;
        assertPerformance(runner,
                players, ticks,
                expectedCreation, expectedTick, expectedPrint,
                printBoard);
    }

}
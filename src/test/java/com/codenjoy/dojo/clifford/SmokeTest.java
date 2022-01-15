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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.clifford.services.GameRunner;
import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.clifford.services.Levels;
import com.codenjoy.dojo.clifford.services.ai.AISolver;
import com.codenjoy.dojo.games.clifford.Board;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import com.codenjoy.dojo.utils.Smoke;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class SmokeTest {

    private Smoke smoke;
    private Dice dice;
    private Supplier<Solver> solver;

    @Before
    public void setup() {
        smoke = new Smoke();
        dice = smoke.dice();

        solver = () -> new AISolver(dice);
    }

    @Test
    public void testSoft() {
        // about 0.7 sec
        int ticks = 1000;
        int players = 2;

        smoke.settings().showPlayers(null);

        smoke.play(ticks, "SmokeTest.data",
                new GameRunner() {
                    @Override
                    public Dice getDice() {
                        return dice;
                    }

                    @Override
                    public GameSettings getSettings() {
                        return new TestGameSettings()
                                .setLevelMaps(LevelProgress.levelsStartsFrom1,
                                        "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                                        "☼~~~~~~~~H   ~~~☼\n" +
                                        "☼        H###   ☼\n" +
                                        "☼   ~~~~~H    ##☼\n" +
                                        "☼H##     H##    ☼\n" +
                                        "☼H       H~~~~~ ☼\n" +
                                        "☼H       H      ☼\n" +
                                        "☼H#####  H      ☼\n" +
                                        "☼H         #####☼\n" +
                                        "☼H  ~~~»        ☼\n" +
                                        "☼H##   ######H H☼\n" +
                                        "☼H~~~        H H☼\n" +
                                        "☼H             H☼\n" +
                                        "☼H   ~~~~~~~~~ H☼\n" +
                                        "☼###H    H     H☼\n" +
                                        "☼   H    H     H☼\n" +
                                        "☼###############☼\n" +
                                        "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n")
                                .integer(BACKWAYS_COUNT, 5)
                                .integer(MASK_POTIONS_COUNT, 1)
                                .integer(CLUE_COUNT_GLOVE, 5)
                                .integer(CLUE_COUNT_RING, 6)
                                .integer(CLUE_COUNT_KNIFE, 7)
                                .integer(ROBBERS_COUNT, 2);
                    }
                },
                players, solver, Board::new);
    }

    @Test
    public void testHard() {
        // about 2.3 sec
        int ticks = 100;
        int players = 10;
        int robbers = 5;

        dice = smoke.dice(100, 20000);

        smoke.settings().showPlayers("1");

        smoke.play(ticks, "SmokeTestHard.data",
                new GameRunner() {
                    @Override
                    public Dice getDice() {
                        return dice;
                    }

                    @Override
                    public GameSettings getSettings() {
                        return new TestGameSettings()
                                .setLevelMaps(LevelProgress.levelsStartsFrom1,
                                        Levels.BIG_LEVEL.replaceAll("[◄«]", " "))
                                .integer(ROBBERS_COUNT, robbers);
                    }
                },
                players, solver, Board::new);
    }
}
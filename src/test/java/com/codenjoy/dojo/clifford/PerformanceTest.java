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
import com.codenjoy.dojo.clifford.services.Levels;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;
import static com.codenjoy.dojo.utils.TestUtils.assertPerformance;
import static org.junit.Assert.assertEquals;

public class PerformanceTest {

    @Test
    public void test() {
        // about 4.8 sec
        int robbers = 10;
        int players = 20;
        int ticks = 100;

        int expectedCreation = 1400;
        int expectedPrint = 7300;
        int expectedTick = 500;

        Dice dice = new DiceGenerator().getDice(2000);
        GameRunner runner = new GameRunner(){

            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                return new GameSettings()
                    .setLevelMaps(LevelProgress.levelsStartsFrom1, Levels.BIG_LEVEL)
                    .bool(ROUNDS_ENABLED, false)
                    .integer(HANDGUN_CLIP_SIZE, 20)
                    .integer(HANDGUN_TICKS_PER_SHOOT, 2)
                    .bool(HANDGUN_UNLIMITED_AMMO, false)
                    .integer(ROBBERS_COUNT, robbers);
            }
        };

        boolean printBoard = false;
        String board = assertPerformance(runner,
                players, ticks,
                expectedCreation, expectedPrint, expectedTick,
                printBoard);

        assertEquals(
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼                             ~~~~~~~~~      &    ~~~~~~~☼\n" +
                "☼##H########################H#H       H##########H       ☼\n" +
                "☼  H                        H######H  H        » H#☼☼☼☼☼#☼\n" +
                "☼H☼☼#☼☼H    H#########H     H#     H#####H### #H##  ~~~~~☼\n" +
                "☼H     H    H &       H#####H#     H ~   H     H  ~~     ☼\n" +
                "☼H#☼#☼#H    H         H  ~~~ #####H#     H     H    ~~   ☼\n" +
                "☼H  ~  H~~~~H~~~~~~  •H  «        H   H######H##   @  ~~ ☼\n" +
                "☼H     H  F H     H###☼☼☼☼☼☼H☼    H~~~H      H          #☼\n" +
                "☼H     H    H#####H         D     H      H ########H     ☼\n" +
                "☼☼###☼##☼##☼H         H###H##    H##     H#       ##     ☼\n" +
                "☼☼###☼~~~~  H         H   H######H######### H###H #####H#☼\n" +
                "☼☼   ☼      H   ~~~~~~H   H      H          H# #H      H ☼\n" +
                "☼########H###☼☼☼☼     H  ############ & ###### ##########☼\n" +
                "☼    &   H            H     •                         $  ☼\n" +
                "☼H##########################H########~~~####H############☼\n" +
                "☼H          »      ~~~   •  H               H         &  ☼\n" +
                "☼#######H ######            X###~~~~      ############H  ☼\n" +
                "☼&      H~~~~~~~~~~W   &    H         •               H  ☼\n" +
                "☼    $  H    ##H   #######H##########~~~~~~~H######## H $☼\n" +
                "☼       H    ##H •)       H                 H    •    H  ☼\n" +
                "☼##H#####    ########H#######~~~J  ~~~#########~~~~~  H  ☼\n" +
                "☼• H              (  H  &        •        •    &  ~~~~H  ☼\n" +
                "☼###1#####H##########H        #☼☼☼☼☼☼#   ☼☼☼☼☼☼☼      H  ☼\n" +
                "☼         H          H   &    ~      ~       ) •     WH  ☼\n" +
                "☼☼☼@      H~~YJ~~~~~~H    •«   #2 ###   ###########   H  ☼\n" +
                "☼ &  H######         #######H  )        ~~~~~~~~~~~~~~H  ☼\n" +
                "☼H☼  H                      H« H####H                 H  ☼\n" +
                "☼H☼☼#☼☼☼☼☼☼☼☼☼☼☼☼###☼☼☼☼☼☼☼☼H☼☼☼☼☼☼☼☼#☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼#☼\n" +
                "☼H        &   ~~H~~~~☼☼☼☼☼☼☼D☼☼☼☼☼☼☼    @  H   ~~~~~~~~~H☼\n" +
                "☼H~~~~& ######  H         H☼H☼H        ####H  ☼         H☼\n" +
                "☼H &            ##H#######H☼H☼H######H     ###☼☼☼☼☼☼☼☼ ~H☼\n" +
                "☼H#########       H    ~~~H☼H☼H~~~   H~~~~~  #        ~ H☼\n" +
                "☼H        ###H####H##H     ☼H☼       H     ###☼☼☼☼☼☼ ~  H☼\n" +
                "☼H   &       H      #######☼H☼#####  H#####   ~~~~~~~ ~ H☼\n" +
                "☼~~~~~~~~~~~~H       H~~~~~☼H☼~~~~~  H   •         ~ ~  H☼\n" +
                "☼     H              H     ☼H☼     #######3##H      $   H☼\n" +
                "☼ ### #############H H#####☼H☼$              H ######## H☼\n" +
                "☼H                 H       ☼H☼###### •     • H  &       H☼\n" +
                "☼H####          H##H####                ###H#########   H☼\n" +
                "☼H      H######### H  @############        X            H☼\n" +
                "☼H##    X          H~~~~~~                 H #######H## H☼\n" +
                "☼~~~~#####H#   ~~~~H        W########H     H        H   H☼\n" +
                "☼   $     H        H      ~~~~~~~~   H    •H   »    H   H☼\n" +
                "☼   ########H    ######H##        #############y    H   H☼\n" +
                "☼     •     H   « W$   H        ~~~~~            #H#####H☼\n" +
                "☼H @  # #######*#H     H#####H  « •  & H##H       H    •H☼\n" +
                "☼H###            H     H      ##########  ##H###  H »   H☼\n" +
                "☼H  ######$ ##H#####2  H       $            H   ##X###  H☼\n" +
                "☼H      »     H ~~~~~##H###H     #########H##   &       H☼\n" +
                "☼    H########H#  W    H   ######•     ►  H        •    H☼\n" +
                "☼ ###H        H   »   • ~~~~~H   •   #H###H####H###     H☼\n" +
                "☼    H###### #H#########     H        H        H     $  H☼\n" +
                "☼H   H           &           H        H •      H  •   & H☼\n" +
                "☼H  ####H######         #####H########H##      H#####   H☼\n" +
                "☼H      H      H#######H » » »              •  H  •     H☼\n" +
                "☼##############H       H#### #################### #######☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n",
                board);
    }
}
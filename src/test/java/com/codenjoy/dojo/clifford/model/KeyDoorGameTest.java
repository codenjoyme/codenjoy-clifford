package com.codenjoy.dojo.clifford.model;

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

import com.codenjoy.dojo.clifford.model.items.door.Door;
import com.codenjoy.dojo.clifford.model.items.door.Key;
import org.junit.Test;

import java.util.List;

import static com.codenjoy.dojo.client.Utils.split;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.GENERATE_KEYS;
import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;

public class KeyDoorGameTest extends AbstractGameTest {

    @Test
    public void accessGivenDoorsAndKeys() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼ggssbb☼\n" +
                "☼ GSSB ☼\n" +
                "☼ +-!  ☼\n" +
                "☼ + ! ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        List<Door> doors = field().doors().all();
        
        // then 
        assertEquals(
                "[[1,5=GOLD:OPENED], \n" +
                "[2,5=GOLD:OPENED], \n" +
                "[3,5=SILVER:OPENED], \n" +
                "[4,5=SILVER:OPENED], \n" +
                "[5,5=BRONZE:OPENED], \n" +
                "[6,5=BRONZE:OPENED], \n" +
                "[2,4=GOLD:CLOSED], \n" +
                "[3,4=SILVER:CLOSED], \n" +
                "[4,4=SILVER:CLOSED], \n" +
                "[5,4=BRONZE:CLOSED]]",
                split(doors, "], \n["));

        // when
        List<Key> keys = field().keys().all();
        
        // then 
        assertEquals(
                "[[2,3=GOLD], \n" +
                "[2,2=GOLD], \n" +
                "[3,3=SILVER], \n" +
                "[4,3=BRONZE], \n" +
                "[4,2=BRONZE]]",
                split(keys, "], \n["));

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼ggssbb☼\n" +
                "☼ GSSB ☼\n" +
                "☼ +-!  ☼\n" +
                "☼ + ! ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void pickKeysWithoutGeneration() {
        // given
        settings().bool(GENERATE_KEYS, false);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►++-!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►+-!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►-!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=2, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();
        
        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ►!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=2, SILVER=1, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=2, SILVER=1, BRONZE=1}");

        // when
        hero().clearScores();

        // then
        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");
    }

    @Test
    public void pickKeysWithGeneration() {
        // given
        settings().bool(GENERATE_KEYS, true);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►++-!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        dice(1, 5); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼+     ☼\n" +
                "☼      ☼\n" +
                "☼  ►+-!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        dice(2, 5); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼++    ☼\n" +
                "☼      ☼\n" +
                "☼   ►-!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=2, SILVER=0, BRONZE=0}");

        // when
        dice(3, 5); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼++-   ☼\n" +
                "☼      ☼\n" +
                "☼    ►!☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=2, SILVER=1, BRONZE=0}");

        // when
        dice(4, 5); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼++-!  ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=2, SILVER=1, BRONZE=1}");

        // when
        hero().clearScores();

        // then
        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");
    }

    @Test
    public void heroCannotGoThroughClosedDoor() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ► G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void heroCanGoThroughOpenedDoor() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ► g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    g► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void openDoor_right() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ►+G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().openDoor();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    g► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void openDoor_left() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  G+◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  G◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().openDoor();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  g◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ◄    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ◄g    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void heroCannotOpenDoorWithoutKeys() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().openDoor();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►G  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");
    }

    @Test
    public void closeDoor_right() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ►+g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    g► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().closeDoor(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    G◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");
    }

    @Test
    public void closeDoor_left() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  g+◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  g◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ◄    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ◄g    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().closeDoor(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ►G    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");
    }

    @Test
    public void closeDoorAtHeroPositionHasNoEffect() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  g+◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  g◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ◄    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().openDoor(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  g►   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");
    }

    @Test
    public void heroCannotCloseDoorWithoutKeys() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    g► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().openDoor(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ◄g  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void heroShouldReleaseKeysAfterDeath_ifGenerateDisable() {
        // given
        settings().bool(GENERATE_KEYS, false);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►+-! ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►-! ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►! ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=1, BRONZE=0}");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ► ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=1, BRONZE=1}");

        // when
        dice(1, 5,  // key
             2, 5,  // key
             3, 5); // key
        hero().die();
        tick();

        verifyAllEvents("[HERO_DIED]");

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼+-!   ☼\n" +
                "☼      ☼\n" +
                "☼    O ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=1, BRONZE=1}");

        assertEquals(false, hero().isAlive());

        // when
        dice(1, 3);
        game().newGame();

        // then
        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");
    }

    @Test
    public void heroShouldNotReleaseKeysAfterDeath_itGenerateEnable() {
        // given
        settings().bool(GENERATE_KEYS, true);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►+-! ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        // when
        dice(4, 6); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   +  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►-! ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=0, BRONZE=0}");

        // when
        dice(5, 6); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   +- ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►! ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=1, BRONZE=0}");

        // when
        dice(6, 6); // key
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   +-!☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ► ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=1, BRONZE=1}");

        // when
        // тут не будет генерации, но если будет мы заметим
        dice(1, 5,  // key
             2, 5,  // key
             3, 5); // key
        hero().die();
        tick();

        verifyAllEvents("[HERO_DIED]");

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   +-!☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    O ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertHeroKeys("{GOLD=1, SILVER=1, BRONZE=1}");
        assertEquals(false, hero().isAlive());

        // when
        dice(1, 3);
        game().newGame();

        // then
        assertHeroKeys("{GOLD=0, SILVER=0, BRONZE=0}");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   +-!☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►     ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    private void assertHeroKeys(String expected) {
        assertEquals(expected, hero().getKeys().toString());
    }
}
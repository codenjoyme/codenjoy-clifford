package com.codenjoy.dojo.clifford.game;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
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
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.GENERATE_KEYS;

public class KeyDoorGameTest extends AbstractGameCheckTest {

    @Test
    public void accessGivenDoorsAndKeys() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼⍙⍙⍚⍚⍜⍜☼\n" +
                "☼ ⍍⌺⌺⌼ ☼\n" +
                "☼ ✦✼⍟  ☼\n" +
                "☼ ✦ ⍟ ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // then door
        assertEquals(10, field().doors().all().size());
        assertEquals(6L, field().doors().all().stream()
                .filter(Door::isOpened)
                .count());
        assertEquals(4L, field().doors().all().stream()
                .filter(Door::isClosed)
                .count());

        // then keys
        assertEquals(5, field().keys().all().size());
        assertEquals(2L, field().keys().all().stream()
                .filter(key -> key.getKeyType().isGold())
                .count());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼⍙⍙⍚⍚⍜⍜☼\n" +
                "☼ ⍍⌺⌺⌼ ☼\n" +
                "☼ ✦✼⍟  ☼\n" +
                "☼ ✦ ⍟ ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void pickKeysWithoutGeneration() {
        settings().bool(GENERATE_KEYS, false);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►✦✦✼⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►✦✼⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►✼⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=2, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ►⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=1}", hero().getKeys().toString());

        hero().clearScores();
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void pickKeysWithGeneration() {
        settings().bool(GENERATE_KEYS, true);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►✦✦✼⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(1, 5);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼✦     ☼\n" +
                "☼      ☼\n" +
                "☼  ►✦✼⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(2, 5);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼✦✦    ☼\n" +
                "☼      ☼\n" +
                "☼   ►✼⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=2, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(3, 5);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼✦✦✼   ☼\n" +
                "☼      ☼\n" +
                "☼    ►⍟☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=0}", hero().getKeys().toString());

        dice(4, 5);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼✦✦✼⍟  ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=1}", hero().getKeys().toString());

        hero().clearScores();
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void heroCannotGoThroughClosedDoor() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ► ⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void heroCanGoThroughOpenedDoor() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ► ⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ⍙► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void openDoor_right() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ►✦⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().act(2);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ⍙► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void openDoor_left() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍍✦◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍍◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().act(2);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍙◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ◄    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ◄⍙    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void heroCannotOpenDoorWithoutKeys() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍍  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void closeDoor_right() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ►✦⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ⍙► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().left();
        hero().act(3);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ⍍◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void closeDoor_left() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍙✦◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍙◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ◄    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ◄⍙    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        hero().act(3);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ►⍍    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void closeDoorAtHeroPositionHasNoEffect() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍙✦◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍙◄   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ◄    ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        hero().act(2);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ⍙►   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void heroCannotCloseDoorWithoutKeys() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ⍙► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        hero().act(2);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ◄⍙  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void heroShouldReleaseKeysAfterDeath_ifGenerateDisable() {
        settings().bool(GENERATE_KEYS, false);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►✦✼⍟ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►✼⍟ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►⍟ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=1, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ► ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=1, BRONZE=1}", hero().getKeys().toString());

        dice(1, 5,
             2, 5,
             3, 5);
        hero().die();
        tick();

        events().verifyAllEvents("[HERO_DIE]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼✦✼⍟   ☼\n" +
                "☼      ☼\n" +
                "☼    Ѡ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=1, BRONZE=1}", hero().getKeys().toString());
        assertEquals(false, hero().isAlive());

        dice(1, 3);
        game().newGame();

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void heroShouldNotReleaseKeysAfterDeath_itGenerateEnable() {
        settings().bool(GENERATE_KEYS, true);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►✦✼⍟ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(4, 6);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ✦  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►✼⍟ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(5, 6);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ✦✼ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►⍟ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=1, BRONZE=0}", hero().getKeys().toString());

        dice(6, 6);
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ✦✼⍟☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ► ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        assertEquals("{GOLD=1, SILVER=1, BRONZE=1}", hero().getKeys().toString());

        dice(1, 5,
             2, 5,
             3, 5);
        hero().die();
        tick();

        events().verifyAllEvents("[HERO_DIE]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ✦✼⍟☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    Ѡ ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals("{GOLD=1, SILVER=1, BRONZE=1}", hero().getKeys().toString());
        assertEquals(false, hero().isAlive());

        dice(1, 3);
        game().newGame();

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

}

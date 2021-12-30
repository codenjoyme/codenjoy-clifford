package com.codenjoy.dojo.clifford.model;

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

import com.codenjoy.dojo.clifford.model.items.Potion;
import org.junit.Test;

import static com.codenjoy.dojo.services.Direction.RIGHT;

public class BulletGameTest extends AbstractGameTest {

    @Test
    public void heroCanShoot_rightDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►• ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");
    }

    @Test
    public void heroCanShoot_leftDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ •◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT]]");
    }

    @Test
    public void heroShoot_upDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().shoot();
        hero().up();
        tick();

        // then bullet should fly left(hero direction),
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ •◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBullets("[[2,2,LEFT]]");
    }

    @Test
    public void heroShootAndMoveRight_shouldSurvive() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►• ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");
        verifyAllEvents("");
    }

    @Test
    public void bulletIsRemovedOutOfBoard() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "  •◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "•  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[0,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");
    }

    @Test
    public void bulletInteractWithBrick_сase3() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼### ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when hero shot several times through the line of bricks
        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼###•◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[4,2,LEFT]]");

        hero().shoot();
        tick();

        // then bricks are destroyed one by one
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼##*•◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[4,2,LEFT]]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#* •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[4,2,LEFT]]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#• •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[2,2,LEFT], [4,2,LEFT]]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼*• •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[2,2,LEFT], [4,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ •  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[0,2,RIGHT], [2,2,LEFT]]");
    }

    @Test
    public void bulletInteractWithBrick_сase2() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ #  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ # •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBullets("[[4,2,LEFT]]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ * •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ • •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT], [4,2,LEFT]]");
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ •  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT], [2,2,LEFT]]");
    }

    @Test
    public void bulletInteractWithBrick() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ #  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ #  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ # •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ *  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 4  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when hero shoots through a restoring brick
        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 3 •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 2  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        // then bullet must go through pit brick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 1  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT]]");

        // when wall has been restored
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ *  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        tick();

        // then bullet must affect the brick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        // when brick destroyed
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();
        tick();

        // then bullet must not affect the empty field
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ •  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void bulletInteractWithBorder() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  •◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼•  ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[1,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼•  ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[1,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  •◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,RIGHT]]");
    }

    @Test
    public void bulletCanBounceOnlyOnce() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ◄ ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    •◄ ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[5,2,LEFT]]");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  • U  ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼•      ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[1,2,LEFT]]");

        // bounce from border
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼•      ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[1,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  •    ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    •  ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼      •☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");
    }

    @Test
    public void bouncedBulletKillOwner() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼•◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼•◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ O ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED, SUICIDE]");
    }


    @Test
    public void heroNotMoveWhenShoot() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().shoot(RIGHT);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►• ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");
    }

    @Test
    public void heroKillOtherHero() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►  ►   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►  »   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►• »   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[2,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►  C   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");
    }

    @Test
    public void heroWithMaskIsImmortal_bulletGoThrough() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► ► ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(1).pick(Potion.PotionType.MASK_POTION);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► z »  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(0).shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►•z »  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[2,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► z•»  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[4,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► z C  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(2) => [HERO_DIED]\n");
    }

    @Test
    public void heroFallOnBullet_shouldDie() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►   ☼\n" +
                "☼       ☼\n" +
                "☼      ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼       ☼\n" +
                "☼      ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼     •◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[6,3,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   C  ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");
    }

    @Test
    public void heroFallOnBullet_notTakeClue() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►   ☼\n" +
                "☼       ☼\n" +
                "☼   $  ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼       ☼\n" +
                "☼   $  ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼   $ •◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[6,3,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   C  ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        assertEquals("[[4,3]]", field().clueKnife().toString());

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");
    }

    // todo Продумать, должен ли выживать.
    @Test
    public void heroFallOnBullet_shouldSurvive() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ►  ☼\n" +
                "☼       ☼\n" +
                "☼      ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    F  ☼\n" +
                "☼       ☼\n" +
                "☼      ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    F  ☼\n" +
                "☼     •◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[6,3,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   •» ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[4,3,LEFT]]");

        verifyAllEvents("");
    }

    @Test
    public void twoShootersKillHero() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► ► ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼» » ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(0).shoot();
        hero(1).shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»•»•◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[4,3,LEFT], [2,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼» C ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(2) => [HERO_DIED]\n");
    }

    @Test
    public void bulletInteractWithBrick_сase1() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ #◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ #◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when hero shoot next to brick
        hero().shoot();
        tick();

        // then brick should be cracked. Bullet should be deleted
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ *◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBullets("[]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void bulletInteractWithBorder_сase1() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "◄   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "◄   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when hero shoot
        hero().shoot();
        tick();

        // then bullet should left the field
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "◄   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBullets("[]");
    }

    @Test
    public void bulletInteractWithWall_сase1() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when hero shoot
        hero().shoot();
        tick();

        // then bullet should bounced
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBullets("[[0,2,RIGHT]]");

        tick();

        // then kill hero
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼O  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        
        verifyAllEvents("[HERO_DIED, SUICIDE]");
    }

    @Test
    public void noEventIfOwnerNotAlive() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ►   ► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(0).shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ►•  » ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[3,3,RIGHT]]");

        hero(0).die();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ O  •» ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents(
                "listener(0) => [HERO_DIED]\n");
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ O   C ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");
        
        verifyAllEvents(
                "listener(1) => [HERO_DIED]\n");
    }

    @Test
    public void twoShootersKillEachOther() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►     ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(0).shoot();
        hero(1).shoot();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»•   •◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[6,3,LEFT], [2,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»  •  ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»•   •◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼C     O☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");
        
        verifyAllEvents(
                "listener(0) => [HERO_DIED]\n" +
                "listener(1) => [HERO_DIED]\n");
    }
}

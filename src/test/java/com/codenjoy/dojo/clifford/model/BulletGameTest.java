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

import com.codenjoy.dojo.clifford.model.items.potion.PotionType;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;

public class BulletGameTest extends AbstractGameTest {

    @Test
    public void heroCanShoot_rightDirection() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►• ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");
    }

    @Test
    public void heroCanShoot_leftDirection() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ •◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT]]");
    }

    @Test
    public void heroShoot_upDirection() {
        // given
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

        // then
        // bullet should fly left(hero direction),
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ •◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");
    }

    @Test
    public void heroShootAndMoveRight_shouldSurvive() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►• ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        // when
        hero().right();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "  •◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "•  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[0,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");
    }

    @Test
    public void bulletInteractWithBrick_case3() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼### ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        // hero shot several times through the line of bricks
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼###•◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then
        // bricks are destroyed one by one
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼##*•◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#* •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#• •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT], [4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼*• •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT], [4,2,LEFT]]");

        // when
        tick();

        // then
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
    public void bulletInteractWithBrick_case2() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ #  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ # •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ * •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ • •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT], [4,2,LEFT]]");

        // when
        tick();

        // then
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
        // given
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

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ # •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ *  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 4  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        // hero shoots through a restoring brick
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 3 •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 2  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        // bullet must go through pit brick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ 1  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT]]");

        // when
        // wall has been restored
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ *  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        tick();

        // then
        // bullet must affect the brick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        // brick destroyed
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();
        tick();

        // then
        // bullet must not affect the empty field
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  •◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼•  ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[1,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼•  ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[1,2,RIGHT]]");

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ◄ ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();

        // then
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

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  • U  ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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

        // when
        // bounce from border
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    •  ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼      •☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼•◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼•◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,RIGHT]]");

        // when
        tick();

        // then
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
    public void gunRechargeTest_Case1() {
        // given
        settings().integer(HANDGUN_TICKS_PER_SHOOT,1);

        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#   ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#  •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then new bullet shouldn't be created. Recharge 1 tick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#•  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then new bullet should be created. Recharge end
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼*  •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then new bullet shouldn't be created. Recharge 1 tick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ •  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");
    }

    @Test
    public void gunRechargeTest_Case2() {
        // given
        settings().integer(HANDGUN_TICKS_PER_SHOOT,2);

        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#   ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().shoot();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#  •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then new bullet shouldn't be created. Recharge 2 tick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#•  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        // when
        hero().shoot();
        tick();

        // then new bullet shouldn't be created. Recharge 2 tick
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼*   ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        hero().shoot();
        tick();

        // then new bullet shouldn't be created. Recharge end
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   •◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");
    }

    @Test
    public void heroNotMoveWhenShoot() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().shoot(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►• ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");
    }

    @Test
    public void heroKillOtherHero() {
        // given
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

        // when
        hero().shoot();
        tick();

        // then
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

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► ► ►  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero(1).pick(PotionType.MASK_POTION);

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► z »  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero(0).shoot();
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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
        // given
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

        // when
        hero().shoot();
        tick();

        // then
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

        // when
        tick();

        // then
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
        // given
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

        // when
        hero().shoot();
        tick();

        // then
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

        // when
        tick();

        // then
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
        // given
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

        // when
        hero().shoot();
        tick();

        // then
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

        // when
        tick();

        // then
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
        // given
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

        // when
        hero(0).shoot();
        hero(1).shoot();
        tick();

        // then
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

        // when
        tick();

        // then
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
    public void bulletInteractWithBrick_case1() {
        // given
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

        // when
        // hero shoot next to brick
        hero().shoot();
        tick();

        // then
        // brick should be cracked. Bullet should be deleted
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ *◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void bulletInteractWithBorder_case1() {
        // given
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

        // when
        hero().shoot();
        tick();

        // then
        // bullet should left the field
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "◄   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");
    }

    @Test
    public void bulletInteractWithWall_case1() {
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

        // when
        hero().shoot();
        tick();

        // then
        // bullet should bounced
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT]]");

        // when
        tick();

        // then
        // kill hero
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼O  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        
        verifyAllEvents("[HERO_DIED, SUICIDE]");
    }

    @Test
    public void noEventIfOwnerNotAlive() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ►   ► ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero(0).shoot();
        tick();

        // then
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

        // when
        hero(0).die();
        dice(1, 3); // new hero position
        tick(); // new game

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►   •» ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents(
                "listener(0) => [HERO_DIED]\n");
        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►    C ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");
        
        verifyAllEvents(
                "listener(1) => [HERO_DIED]\n");
    }

    @Test
    public void twoShootersKillEachOther() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►     ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero(0).shoot();
        hero(1).shoot();
        tick();

        // then
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

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»  •  ◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»•   •◄☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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

    @Test
    public void shouldPickUpAdditionalAmmo() {
        // given
        settings().integer(HANDGUN_CLIP_SIZE, 1)
                .bool(HANDGUN_UNLIMITED_AMMO, false);

        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼# M ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼# M ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        // hero shoot two times
        hero().shoot();
        tick();
        hero().shoot();
        tick();

        // then
        // only one bullet should be available
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼#•M ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        hero().shoot();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼* M ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        // hero pick up ammo_clip
        hero().left();
        tick();
        hero().left();
        tick();
        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ► ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        // he should can shoot again
        hero().shoot(LEFT);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  •◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

    }
}
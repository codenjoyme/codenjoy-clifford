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

import com.codenjoy.dojo.clifford.game.check.AbstractGameCheckTest;
import com.codenjoy.dojo.clifford.model.items.Potion;
import org.junit.Test;

public class BulletGameTest extends AbstractGameCheckTest {

    private void assertBullets(String expected) {
        assertEquals(expected, field().bullets().toString());
    }

    @Test
    public void heroCanShoot_rightDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[1,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼► •☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[3,2,RIGHT]]");
    }

    @Test
    public void heroCanShoot_leftDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼• ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[1,2,LEFT]]");
    }

    @Test
    public void bulletIsRemovedOutOfBoard() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[3,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                " • ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[1,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");
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

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ #  ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[5,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ #• ◄☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

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

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ • ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[0,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ • ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");
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

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ◄ ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[6,2,LEFT]]");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   •U  ☼\n" +
                "☼#### ##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ •     ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,LEFT]]");

        // bounce from border
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

        assertBullets("[[0,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ •     ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   •   ☼\n" +
                "☼####◄##☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     • ☼\n" +
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

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[4,2,LEFT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertBullets("[[2,2,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ O ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE, SUICIDE]");
    }


    @Test
    public void heroNotMoveWhenShoot() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[1,2,RIGHT]]");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►•☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
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

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►  »   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[1,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► •»   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

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
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
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

        hero(0).act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► z »  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[1,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► z »  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[3,3,RIGHT]]");

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
                "listener(0) => [KILL_HERO]\n" +
                "listener(2) => [HERO_DIE]\n");
    }

    @Test
    public void heroFallOnBullet() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►   ☼\n" +
                "☼       ☼\n" +
                "☼     ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼       ☼\n" +
                "☼     ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼     ◄ ☼\n" +
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
                "☼   C ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
    }

    @Test
    public void heroFallOnBullet_notTakeClue() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ►   ☼\n" +
                "☼       ☼\n" +
                "☼   $ ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼       ☼\n" +
                "☼   $ ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   F   ☼\n" +
                "☼   $ ◄ ☼\n" +
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
                "☼   C ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");

        assertEquals("[[4,3]]", field().clueKnife().toString());

        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
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

        hero(0).act(1);
        hero(1).act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼» » ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[5,3,LEFT], [1,3,RIGHT]]");

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
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(2) => [HERO_DIE]\n");
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

        hero(0).act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ►   » ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[2,3,RIGHT]]");

        hero(0).die();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ O • » ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents(
                "listener(0) => [HERO_DIE]\n");
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
                "listener(1) => [HERO_DIE]\n");
    }

    @Test
    public void twoShootersKillEachOther() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►   ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(0).act(1);
        hero(1).act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼»   ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[5,3,LEFT], [1,3,RIGHT]]");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼» • ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼C   O  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[]");
        
        verifyAllEvents(
                "listener(0) => [HERO_DIE]\n" +
                "listener(1) => [HERO_DIE]\n");
    }
}

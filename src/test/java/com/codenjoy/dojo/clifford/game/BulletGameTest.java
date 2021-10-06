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

import com.codenjoy.dojo.clifford.model.items.Potion;
import org.junit.Test;

import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class BulletGameTest extends AbstractGameCheckTest {

    private void assertBulletAt(int x, int y) {
        assertEquals(true, field().bullets().contains(pt(x, y)));
    }

    private void assertBulletCount(int count) {
        assertEquals(count, field().bullets().size());
    }

    @Test
    public void heroCanShoot_rightDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBulletCount(0);

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletAt(1, 2);

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼► •☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletCount(1);
    }

    @Test
    public void heroCanShoot_leftDirection() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBulletCount(0);

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletAt(3, 2);

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼• ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletCount(1);
    }

    @Test
    public void bulletIsRemovedOutOfBoard() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertBulletCount(0);

        hero().act(1);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletAt(3, 2);

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                " • ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletCount(1);

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "   ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
        assertBulletCount(0);
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
        assertBulletAt(5, 2);

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
        assertBulletCount(0);

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
        assertBulletAt(4, 2);

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ • ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertEquals(true, field().bullets().getAt(pt(2, 2)).stream()
                .allMatch(bullet -> bullet.getDirection() == LEFT));

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBulletAt(0, 2);

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ • ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertEquals(true, field().bullets().getAt(pt(2, 2)).stream()
                .allMatch(bullet -> bullet.getDirection() == RIGHT));
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
        assertBulletAt(6, 2);

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   •]  ☼\n" +
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
        assertEquals(true, field().bullets().getAt(pt(2, 2)).stream()
                .allMatch(bullet -> bullet.getDirection() == LEFT));

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
        assertBulletAt(0, 2);

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
        assertEquals(true, field().bullets().getAt(pt(2, 2)).stream()
                .allMatch(bullet -> bullet.getDirection() == RIGHT));

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
        assertBulletCount(0);
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
        assertBulletAt(4, 2);

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ ◄ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");
        assertBulletAt(2, 2);

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ☼ Ѡ ☼\n" +
                "☼#####☼\n" +
                "☼☼☼☼☼☼☼\n");

        events().verifyAllEvents("[HERO_DIE, SUICIDE]");
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
        assertBulletAt(1, 2);

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
                "☼►  (   ☼\n" +
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
                "☼►  (   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(1, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► •(   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►  Z   ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        events().verifyAllEvents(
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
                "☼► ⋉ (  ☼\n" +
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
                "☼► ⋉ (  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(1, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► ⋉ (  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(3, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼► ⋉ Z  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        events().verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => []\n" +
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
                "☼   ⊏   ☼\n" +
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
                "☼   ⊏   ☼\n" +
                "☼     ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(6, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   Z ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        events().verifyAllEvents(
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
                "☼   ⊏   ☼\n" +
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
                "☼   ⊏   ☼\n" +
                "☼   $ ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(6, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   Z ◄ ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        assertEquals(1, field().clueKnife().size());
        events().verifyAllEvents(
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
                "☼( ( ◄  ☼\n" +
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
                "☼( ( ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(1, 3);
        assertBulletAt(5, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼( Z ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        events().verifyAllEvents(
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
                "☼ ►   ( ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(2, 3);

        hero(0).die();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ Ѡ • ( ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        events().verifyAllEvents(
                "listener(0) => [HERO_DIE]\n" +
                "listener(1) => []\n");
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ Ѡ   Z ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        events().verifyAllEvents(
                "listener(0) => []\n" +
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
                "☼(   ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        assertBulletAt(1, 3);
        assertBulletAt(5, 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼( • ◄  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼Z   Ѡ  ☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBulletCount(0);
        events().verifyAllEvents(
                "listener(0) => [HERO_DIE]\n" +
                "listener(1) => [HERO_DIE]\n");
    }
}

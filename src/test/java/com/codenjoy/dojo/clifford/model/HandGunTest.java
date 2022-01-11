package com.codenjoy.dojo.clifford.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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

import org.junit.Test;

import static com.codenjoy.dojo.clifford.model.HandGun.UNLIM_CLIP_SIZE;
import static com.codenjoy.dojo.clifford.model.HandGun.SHOOT_WITHOUT_RECHARGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandGunTest {
    HandGun gun;

    private void tick() {
        gun.tick();
    }

    private void gun(int ticksPerShoot, int clipSize) {
        gun = new HandGun(ticksPerShoot, clipSize);
    }

    @Test
    public void shouldIncreaseAmmo() {
        // given
        gun(SHOOT_WITHOUT_RECHARGE, 2);

        // when spent all ammo
        assertTrue(gun.tryToFire());
        tick();
        assertTrue(gun.tryToFire());
        tick();

        // then couldn't shoot
        assertFalse(gun.tryToFire());

        // when get additional ammo
        gun.addAmmo(2);
        tick();

        // then should shoot two times
        assertTrue(gun.tryToFire());
        tick();
        assertTrue(gun.tryToFire());
        tick();
    }

    @Test
    public void shouldShootWithRecharge_limitedAmmo() {
        // given
        gun(1, 2);

        // gun should shoot two times with recharge
        assertTrue(gun.tryToFire());

        // then recharge
        tick();
        assertFalse(gun.tryToFire());

        // then shoot
        tick();
        assertTrue(gun.tryToFire());
        // when ammo off

        // then gun should be able again to shoot
        tick();
        assertFalse(gun.tryToFire());
        tick();
        assertFalse(gun.tryToFire());
        tick();
        assertFalse(gun.tryToFire());
        tick();
        assertFalse(gun.tryToFire());
    }

    @Test
    public void shouldShootWithRecharge_unlimitedAmmo_case1() {
        // given
        gun(1, UNLIM_CLIP_SIZE);

        // when gun shoot for the first time
        assertTrue(gun.tryToFire());
        tick();

        // then shouldn't shoot
        assertFalse(gun.tryToFire());

        // when one tick recharge
        tick();

        // then gun should be able again to shoot
        assertTrue(gun.tryToFire());
    }

    @Test
    public void shouldShootWithRecharge_unlimitedAmmo_case2() {
        // given
        int ticksPerShoot = 3;
        gun(ticksPerShoot, UNLIM_CLIP_SIZE);

        // when gun shoot one time and recharging
        assertTrue(gun.tryToFire());

        // then can't shoot 3 ticks
        for (int i = 0; i < ticksPerShoot; i++) {
            tick();
            assertFalse(gun.tryToFire());
        }

        // when tick after recharge
        tick();

        // then gun should be able again to shoot
        assertTrue(gun.tryToFire());
    }


    @Test
    public void shouldShootWithoutDelay_unlimitedAmmo() {
        // given
        gun(SHOOT_WITHOUT_RECHARGE, UNLIM_CLIP_SIZE);

        // when gun shoot without breaks and recharging
        for (int i = 0; i < 100; i++) {
            // then gun should return true;
            assertTrue(gun.tryToFire());
            tick();
        }
    }

    @Test
    public void shouldShootWithoutDelay_limitedAmmo() {
        // given
        int ammoLimit = 10;
        gun(SHOOT_WITHOUT_RECHARGE, ammoLimit);

        // when gun gun ammo off
        for (int i = 0; i < ammoLimit; i++) {
            // then gun should return true;
            assertTrue(gun.tryToFire());
            tick();
        }
        // then gun couldn't shoot
        assertFalse(gun.tryToFire());
    }
}
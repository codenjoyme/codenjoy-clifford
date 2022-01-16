package com.codenjoy.dojo.clifford.model.items;

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

import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.services.settings.SettingsReader;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static org.junit.Assert.assertEquals;

public class HandGunTest {

    private HandGun gun;

    private void tick() {
        gun.tick();
    }

    private void gun(int ticksPerShoot, int clipSize, boolean unlimitedAmmo) {
        SettingsReader settings = new GameSettings()
                .integer(HANDGUN_TICKS_PER_SHOOT, ticksPerShoot)
                .integer(HANDGUN_CLIP_SIZE, clipSize)
                .bool(HANDGUN_UNLIMITED_AMMO, unlimitedAmmo);

        gun = new HandGun(settings);
    }

    @Test
    public void shouldIncreaseAmmo() {
        // given
        int ticksPerShoot = 0;
        int clipSize = 2;
        boolean unlimitedAmmo = false;

        gun(ticksPerShoot, clipSize, unlimitedAmmo);

        // when
        // spent all ammo
        assertFired();

        tick();

        assertFired();

        tick();

        // then
        // couldn't shoot
        assertCantFire();

        // when
        // get additional ammo
        gun.addAmmo(2);

        tick();

        // then
        // should shoot two times
        assertFired();

        tick();

        assertFired();

        tick();

        // then
        // couldn't shoot
        assertCantFire();
    }

    @Test
    public void shouldShootWithRecharge_limitedAmmo() {
        // given
        int ticksPerShoot = 1;
        int clipSize = 2;
        boolean unlimitedAmmo = false;

        gun(ticksPerShoot, clipSize, unlimitedAmmo);

        // when
        // gun should shoot two times with recharge
        assertFired();

        // then
        // recharge
        tick();

        assertCantFire();

        // then
        // shoot
        tick();

        assertFired();

        // then
        // gun should be able again to shoot
        // but ammo is off
        tick();

        assertCantFire();

        tick();

        assertCantFire();

        tick();

        assertCantFire();

        tick();

        assertCantFire();
    }

    @Test
    public void shouldShootWithRecharge_unlimitedAmmo_case1() {
        // given
        int ticksPerShoot = 1;
        int clipSize = 0;
        boolean unlimitedAmmo = true;

        gun(ticksPerShoot, clipSize, unlimitedAmmo);

        // when
        // gun shoot for the first time
        assertFired();

        tick();

        // then
        // shouldn't shoot
        assertCantFire();

        // when
        // one tick recharge
        tick();

        // then
        // gun should be able again to shoot
        assertFired();
    }

    private void assertCantFire() {
        assertEquals(false, gun.canFire());
    }

    private void assertFired() {
        assertEquals(true, gun.canFire());
    }

    @Test
    public void shouldShootWithRecharge_unlimitedAmmo_case2() {
        // given
        int ticksPerShoot = 3;
        int clipSize = 0;
        boolean unlimitedAmmo = true;

        gun(ticksPerShoot, clipSize, unlimitedAmmo);

        // when
        // gun shoot one time and recharging
        assertFired();

        // then
        // can't shoot 3 ticks
        for (int i = 0; i < ticksPerShoot; i++) {
            tick();
            assertCantFire();
        }

        // when
        // tick after recharge
        tick();

        // then
        // gun should be able again to shoot
        assertFired();
    }

    @Test
    public void shouldShootWithoutDelay_unlimitedAmmo() {
        // given
        int ticksPerShoot = 0;
        int clipSize = 12;
        boolean unlimitedAmmo = true;

        gun(ticksPerShoot, clipSize, unlimitedAmmo);

        // when then
        // gun shoot without breaks and recharging
        for (int tick = 0; tick < 100; tick++) {
            assertFired();
            tick();
        }
    }

    @Test
    public void shouldShootWithoutDelay_limitedAmmo() {
        // given
        int ticksPerShoot = 0;
        int ammoLimit = 10;
        boolean unlimitedAmmo = false;

        gun(ticksPerShoot, ammoLimit, unlimitedAmmo);

        // when
        // gun ammo on
        for (int tick = 0; tick < ammoLimit; tick++) {
            assertFired();
            tick();
        }

        // then
        // gun couldn't shoot
        assertCantFire();
    }
}
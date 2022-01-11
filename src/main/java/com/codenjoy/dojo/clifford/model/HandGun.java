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


import com.codenjoy.dojo.services.Tickable;
import com.codenjoy.dojo.services.settings.SettingsReader;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.HANDGUN_CLIP_SIZE;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.HANDGUN_TICKS_PER_SHOOT;

public class HandGun implements Tickable {

    private boolean canFire;
    private int ticks;
    private int shootDelay;
    private SettingsReader<?> settings;
    private int clip;

    public HandGun(SettingsReader<?> settings) {
        this.settings = settings;
        reset();
    }

    private int ticksPerShoot() {
        return settings.integer(HANDGUN_TICKS_PER_SHOOT);
    }

    private int clipSize() {
        return settings.integer(HANDGUN_CLIP_SIZE);
    }

    private void reset() {
        rechargeReset();
        clip = clipSize();
    }

    private void rechargeReset() {
        ticks = 0;
        canFire = true;
        shootDelay = ticksPerShoot();
    }

    @Override
    public void tick() {
        if (!canFire) {
            ticks++;
        }
        if (ticks > shootDelay) {
            rechargeReset();
        }
    }

    public boolean tryToFire() {
        boolean result = isEnoughBulletForShoot() && canFire;
        if (result) shoot();
        return result;
    }

    public void addAmmo(int ammo) {
        clip = Math.max(0, clip + ammo);
    }

    private void shoot() {
        canFire = false;
        reduceBulletsCount();
    }

    private void reduceBulletsCount() {
        if (!isUnlimitedBullets()) clip = Math.max(0, --clip);
    }

    private boolean isEnoughBulletForShoot() {
        if (isUnlimitedBullets()) return true;
        return clip > 0;
    }

    private boolean isUnlimitedBullets() {
        return clip == -1;
    }
}

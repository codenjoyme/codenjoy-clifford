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


import com.codenjoy.dojo.services.Tickable;
import com.codenjoy.dojo.services.settings.SettingsReader;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class HandGun implements Tickable {

    private SettingsReader settings;

    private boolean canFire;
    private int ticks;
    private int shootDelay;
    private int ammoInClip;

    public HandGun(SettingsReader settings) {
        this.settings = settings;
        reset();
    }

    public int ticksPerShoot() {
        return settings.integer(HANDGUN_TICKS_PER_SHOOT);
    }

    public int clipSize() {
        return settings.integer(HANDGUN_CLIP_SIZE);
    }

    private boolean isUnlimited() {
        return settings.bool(HANDGUN_UNLIMITED_AMMO);
    }

    private void reset() {
        rechargeReset();
        ammoInClip = clipSize();
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

    public boolean canFire() {
        boolean result = isReadyToShoot() && canFire;
        if (result) {
            canFire = false;
            reduceBulletsCount();
        }
        return result;
    }

    public void addAmmo(int count) {
        ammoInClip = Math.max(0, ammoInClip + count);
    }

    private void reduceBulletsCount() {
        if (!isUnlimited()) {
            ammoInClip--;
            ammoInClip = Math.max(0, ammoInClip);
        }
    }

    private boolean isReadyToShoot() {
        if (isUnlimited()) {
            return true;
        }
        return ammoInClip > 0;
    }
}
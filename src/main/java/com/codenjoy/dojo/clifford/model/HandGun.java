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

public class HandGun implements Tickable {
    public static final int UNLIM_CLIP_SIZE = -1;
    public static final int SHOOT_WITHOUT_RECHARGE = 0;

    private boolean canFire;
    private int ticks;
    private int shootDelay;
    private int clip;

    private int ticksPerShoot;
    private int clipSize;

    public HandGun() {
        ticksPerShoot = SHOOT_WITHOUT_RECHARGE;
        clipSize = UNLIM_CLIP_SIZE;
    }

    public HandGun(int ticksPerShoot, int clipSize) {
        this.ticksPerShoot = ticksPerShoot;
        this.clipSize = clipSize;
        reset();
    }

    public int getTicksPerShoot() {
        return ticksPerShoot;
    }

    public int getClipSize() {
        return clipSize;
    }

    private void reset() {
        rechargeReset();
        clip = getClipSize();
    }

    private void rechargeReset() {
        ticks = 0;
        canFire = true;
        shootDelay = getTicksPerShoot();
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

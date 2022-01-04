package com.codenjoy.dojo.clifford.model.items;

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


import com.codenjoy.dojo.clifford.model.Hero;
import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.*;

import static com.codenjoy.dojo.services.StateUtils.filterOne;

public class Brick extends PointImpl implements Tickable, State<Element, Player> {

    // TODO move to settings
    public static int CRACK_TIMER = 13;
    private int crack;

    private Hero crackedBy;

    public Brick(Point pt) {
        super(pt);
        crack = 0;
    }

    public void crack(Hero hero) {
        this.crackedBy = hero;
        crack = CRACK_TIMER;
    }

    @Override
    public void tick() {
        if (crack == 0) {
            crackedBy = null;
        }
        if (crack > 0) {
            crack--;
        }
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        if (crack == CRACK_TIMER - 1) {
            return Element.CRACK_PIT;
        }
            switch (crack) {
                case 0 : return Element.BRICK;
                case 1 : return Element.PIT_FILL_1;
                case 2 : return Element.PIT_FILL_2;
                case 3 : return Element.PIT_FILL_3;
                case 4 : return Element.PIT_FILL_4;
                default: return getNoneOrBullet(alsoAtPoint);
        }
    }

    private Element getNoneOrBullet(Object[] alsoAtPoint) {
        final Bullet bullet = filterOne(alsoAtPoint, Bullet.class);
        return bullet == null ? Element.NONE : Element.BULLET;
    }

    public Hero getCrackedBy() {
        return crackedBy;
    }

    public boolean isNotTransparentForBullet() {
        return crack <= 1;
    }
}

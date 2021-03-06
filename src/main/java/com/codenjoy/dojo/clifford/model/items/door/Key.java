package com.codenjoy.dojo.clifford.model.items.door;

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

import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.printer.state.State;

import static com.codenjoy.dojo.clifford.model.items.door.KeyType.*;

public class Key extends PointImpl implements State<Element, Player> {

    private KeyType type;

    public Key(Point pt, KeyType type) {
        super(pt);
        this.type = type;
    }

    public KeyType getType() {
        return type;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        switch (type) {
            case GOLD: return Element.KEY_GOLD;
            case SILVER: return Element.KEY_SILVER;
            case BRONZE: return Element.KEY_BRONZE;
        }
        throw new IllegalArgumentException("invalid keyType " + type);
    }

    @Override
    public String toString() {
        return String.format("[%s,%s=%s]",
                x, y,
                type);
    }

    public boolean golden() {
        return type == GOLD;
    }

    public boolean silver() {
        return type == SILVER;
    }

    public boolean bronze() {
        return type == BRONZE;
    }
}

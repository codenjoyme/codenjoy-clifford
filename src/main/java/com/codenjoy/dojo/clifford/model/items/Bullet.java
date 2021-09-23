package com.codenjoy.dojo.clifford.model.items;

import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;

public class Bullet extends PointImpl implements State<Element, Player> {

    public Bullet(Point point) {
        super(point);
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.BULLET;
    }
}

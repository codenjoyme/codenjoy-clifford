package com.codenjoy.dojo.clifford.model.items;

import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.MovingObject;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.State;

public class Bullet extends MovingObject implements State<Element, Player> {

    public Bullet(Point point, Direction direction) {
        super(point.getX(), point.getY(), direction);
        moving = true;
        speed = 2;
    }

    @Override
    protected void moving(Point pt) {
        move(pt);
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.BULLET;
    }
}

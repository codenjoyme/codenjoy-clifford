package com.codenjoy.dojo.clifford.model.items;

import com.codenjoy.dojo.clifford.model.Field;
import com.codenjoy.dojo.clifford.model.Hero;
import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.MovingObject;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.State;

public class Bullet extends MovingObject implements State<Element, Player> {

    private Hero owner;
    private Field field;

    public Bullet(Hero owner, Point point, Direction direction) {
        super(point.getX(), point.getY(), direction);
        moving = true;
        speed = 2;
        this.owner = owner;
        this.field = owner.field();
    }

    public Hero getOwner() {
        return owner;
    }

    @Override
    protected void moving(Point pt) {
        if (pt.isOutOf(field.size())) {
            remove();
        } else {
            move(pt);
            field.affect(this);
        }
    }

    public void remove() {
        moving = false;
        field.bullets().removeExact(this);
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.BULLET;
    }
}

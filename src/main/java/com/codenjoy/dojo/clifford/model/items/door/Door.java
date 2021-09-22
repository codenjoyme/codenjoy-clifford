package com.codenjoy.dojo.clifford.model.items.door;

import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;

public class Door extends PointImpl implements State<Element, Player> {

    private State state;
    private KeyType keyType;

    public Door(Point point, State state, KeyType keyType) {
        super(point);
        this.state = state;
        this.keyType = keyType;
    }

    public boolean isOpened() {
        return state == State.OPENED;
    }

    public boolean isClosed() {
        return state == State.CLOSED;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        switch (keyType) {
            case GOLD:
                return isOpened()
                        ? Element.OPENED_DOOR_GOLD
                        : Element.CLOSED_DOOR_GOLD;
            case SILVER:
                return isOpened()
                        ? Element.OPENED_DOOR_SILVER
                        : Element.CLOSED_DOOR_SILVER;
            case BRONZE:
                return isOpened()
                        ? Element.OPENED_DOOR_BRONZE
                        : Element.CLOSED_DOOR_BRONZE;
        }
        throw new IllegalArgumentException("invalid keyType " + keyType);
    }

    public enum State {
        OPENED, CLOSED
    }
}

package com.codenjoy.dojo.clifford.model.items.door;

import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;

public class DoorClosed extends PointImpl implements State<Element, Player> {

    private KeyType keyType;

    public DoorClosed(Point point, KeyType keyType) {
        super(point);
        this.keyType = keyType;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        switch (keyType) {
            case GOLD: return Element.CLOSED_DOOR_GOLD;
            case SILVER: return Element.CLOSED_DOOR_SILVER;
            case BRONZE: return Element.CLOSED_DOOR_BRONZE;
        }
        throw new IllegalArgumentException("invalid keyType " + keyType);
    }
}

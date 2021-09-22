package com.codenjoy.dojo.clifford.model.items.door;

import com.codenjoy.dojo.clifford.model.Player;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;

public class Key extends PointImpl implements State<Element, Player> {

    private KeyType keyType;

    public Key(Point point, KeyType keyType) {
        super(point);
        this.keyType = keyType;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        switch (keyType) {
            case GOLD: return Element.KEY_GOLD;
            case SILVER: return Element.KEY_SILVER;
            case BRONZE: return Element.KEY_BRONZE;
        }
        throw new IllegalArgumentException("invalid keyType " + keyType);
    }
}

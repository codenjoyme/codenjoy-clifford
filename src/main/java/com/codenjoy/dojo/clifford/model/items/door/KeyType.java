package com.codenjoy.dojo.clifford.model.items.door;

public enum KeyType {
    GOLD, SILVER, BRONZE;

    public boolean isGold() {
        return name().equals("GOLD");
    }

    public boolean isSilver() {
        return name().equals("SILVER");
    }

    public boolean isBronze() {
        return name().equals("BRONZE");
    }
}

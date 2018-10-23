package spacetrader.game.enums;

public enum ShipType implements SpaceTraderEnum {

    Flea,        // 0
    Gnat,        // 1
    Firefly,
    Mosquito,
    Bumblebee,
    Beetle,        // 5
    Hornet,
    Grasshopper,//7
    Termite,
    Wasp,        // 9
    SpaceMonster, //10
    Dragonfly,    // 11
    Mantis,        // 12
    Scarab,        // 13
    Bottle,        // 14
    Custom,        // 15
    Scorpion;    // 16

    public static ShipType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}

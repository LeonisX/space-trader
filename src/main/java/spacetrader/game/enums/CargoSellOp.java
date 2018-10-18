package spacetrader.game.enums;

public enum CargoSellOp implements SpaceTraderEnum {

    SELL_SYSTEM,
    SELL_TRADER,
    DUMP,
    JETTISON;

    public int castToInt() {
        return ordinal();
    }
}

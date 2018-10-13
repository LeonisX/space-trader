package spacetrader.enums;

public enum CargoSellOp implements SpaceTraderEnum {
    SellSystem,
    SellTrader,
    Dump,
    Jettison;

    public int castToInt() {
        return ordinal();
    }
};
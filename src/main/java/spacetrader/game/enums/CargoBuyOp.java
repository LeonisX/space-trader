package spacetrader.game.enums;

public enum CargoBuyOp implements SpaceTraderEnum {
    BuySystem,
    BuyTrader,
    Plunder;

    public int castToInt() {
        return ordinal();
    }
};
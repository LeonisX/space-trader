package spacetrader.game.enums;

public enum CargoBuyOp implements SpaceTraderEnum {

    BUY_SYSTEM,
    BUY_TRADER,
    PLUNDER;

    @Override
    public int castToInt() {
        return ordinal();
    }
}

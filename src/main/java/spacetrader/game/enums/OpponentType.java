package spacetrader.game.enums;

public enum OpponentType implements SpaceTraderEnum//: int
{
    Bottle, FamousCaptain, Mantis, Pirate, Police, Trader;

    public int castToInt() {
        return ordinal();
    }
}

package spacetrader.enums;

public enum SpecialResource implements SpaceTraderEnum// : int
{
    NA, // = -1,
    Nothing, // = 0,
    MineralRich, // = 1,
    MineralPoor, // = 2,
    Desert, // = 3,
    SweetOceans, // = 4,
    RichSoil, // = 5,
    PoorSoil, // = 6,
    RichFauna, // = 7,
    Lifeless, // = 8,
    WeirdMushrooms, // = 9,
    SpecialHerbs, // = 10,
    Artistic, // = 11,
    Warlike, // = 12
    ;

    public static SpecialResource fromInt(int i) {
        return values()[i + 1];
    }

    public int castToInt() {
        return ordinal() - 1;
    }
}
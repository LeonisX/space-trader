package spacetrader.game.enums;

public enum CrewMemberId implements SpaceTraderEnum {

    NA, // = -1,
    COMMANDER, // = 0,
    ALYSSA, // = 1,
    ARMATUR, // = 2,
    BENTOS, // = 3,
    C2U2, // = 4,
    CHI_TI, // = 5,
    CRYSTAL, // = 6,
    DANE, // = 7,
    DEIRDRE, // = 8,
    DOC, // = 9,
    DRACO, // = 10,
    IRANDA, // = 11,
    JEREMIAH, // = 12,
    JUJUBAL, // = 13,
    KRYDON, // = 14,
    LUIS, // = 15,
    MERCEDEZ, // = 16,
    MILETE, // = 17,
    MURI_L, // = 18,
    MYSTYC, // = 19,
    NANDI, // = 20,
    ORESTES, // = 21,
    PANCHO, // = 22,
    PS37, // = 23,
    QUARCK, // = 24,
    SOSUMI, // = 25,
    UMA, // = 26,
    WESLEY, // = 27,
    WONTON, // = 28,
    YORVICK, // = 29,
    OPPONENT, // = 31,
    ARAGORN, // = 38,
    BRADY, // = 39,
    EIGHT_OF_NINE, // = 40,
    FANGORN, // = 41,
    GAGARIN, // = 42,
    HOSHI, // = 43,
    JACKSON, // = 44,
    KAYLEE, // = 45,
    MARCUS, // = 46,
    O_NEILL, // = 47,
    RIPLEY, // = 48,
    STILGAR, // = 49,
    TAGGART, // = 50,
    VANSEN, // = 51,
    XIZOR; // = 52,

    public static CrewMemberId fromInt(int i) {
        if (i >= 1000) {
            return NA;
        }
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}

package spacetrader.game.cheat;

import java.util.Hashtable;

@CheatCode
public enum SomeStringsForCheatSwitch {

    Bazaar,
    Cover,
    DeLorean,
    Diamond,
    Energize,
    Events,
    Fame,
    Go,
    Ice,
    Pirate,
    Police,
    Trader,
    Indemnity,
    IOU,
    Iron,
    Juice,
    Knack,
    LifeBoat,
    L_Engle,
    MonsterCom,
    PlanB,
    Posse,
    RapSheet,
    Rarity,
    Scratch,
    Skin,
    Status,
    Artifact,
    Dragonfly,
    Experiment,
    Gemulon,
    Japori,
    //Jarek,
    Moon,
    //Reactor,
    //Princess,
    Scarab,
    //Sculpture,
    //SpaceMonster,
    //Wild,
    Swag,
    Test,
    Tool,
    //Varmints,
    Yellow,
    Cheetah,
    I,
    N,
    P,
    F,
    T,
    E,
    S,
    Q,
    M,

    __void__;

    static Hashtable<String, SomeStringsForCheatSwitch> specialStrings = new Hashtable<>();

    static {
        specialStrings.put("Monster.com", MonsterCom);
        specialStrings.put("L'Engle", L_Engle);
    }

    static public SomeStringsForCheatSwitch find(String string) {
        SomeStringsForCheatSwitch specialValue = specialStrings.get(string);
        if (specialValue != null) {
            return specialValue;
        }
        try {
            return valueOf(string);
        } catch (IllegalArgumentException e) {
            return __void__;
        }
    }
}

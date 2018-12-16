package spacetrader.game.quest.enums;

import spacetrader.game.quest.Phase;

import java.io.Serializable;

public interface SimpleValueEnumWithPhase<T> extends SimpleValueEnum<T>, Serializable {

    Phase getPhase();
    void setPhase(Phase phase);

}

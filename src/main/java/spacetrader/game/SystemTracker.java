package spacetrader.game;

import spacetrader.game.enums.StarSystemId;

public interface SystemTracker {

    void selectNextSystemWithinRange(boolean forward);

    boolean isTargetWormhole();

    int[] getWormholes();

    void setTargetWormhole(boolean value);

    StarSystem[] getUniverse();

    StarSystem getSelectedSystem();

    StarSystemId getSelectedSystemId();

    void setSelectedSystemId(StarSystemId value);

    void setSelectedSystemByName(String value);

    StarSystem getTrackedSystem();

    void setTrackedSystemId(StarSystemId trackedSystemId);

    boolean isShowTrackedRange();

    StarSystem getWarpSystem();

    boolean getCanSuperWarp();

    void setCanSuperWarp(boolean canSuperWarp);

    void setWarp(boolean viaSingularity);

}

package spacetrader.game;

import spacetrader.game.enums.StarSystemId;

public interface SystemTracker {
    void SelectNextSystemWithinRange(boolean forward);

    StarSystem TrackedSystem();


    boolean TargetWormhole();

    void TargetWormhole(boolean value);

    StarSystem WarpSystem();

    StarSystem[] Universe();

    int[] Wormholes();

    StarSystem SelectedSystem();

    StarSystemId SelectedSystemId();

    void SelectedSystemId(StarSystemId value);

    void setSelectedSystemByName(String value);

    boolean getCanSuperWarp();

    void setCanSuperWarp(boolean canSuperWarp);

    void Warp(boolean viaSingularity);

    void setTrackedSystemId(StarSystemId trackedSystemId);

    boolean isShowTrackedRange();
}

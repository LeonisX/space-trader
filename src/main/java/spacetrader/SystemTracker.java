package spacetrader;

import spacetrader.enums.StarSystemId;

public interface SystemTracker
{
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


	void setCanSuperWarp(boolean canSuperWarp);
	boolean getCanSuperWarp();
	void Warp(boolean viaSingularity);
	void setTrackedSystemId(StarSystemId trackedSystemId);

	boolean isShowTrackedRange();
}

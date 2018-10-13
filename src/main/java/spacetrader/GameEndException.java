package spacetrader;

import spacetrader.enums.GameEndType;

public class GameEndException extends RuntimeException
{

	public GameEndException(GameEndType endType)
	{
		Game.CurrentGame().setEndStatus(endType);
	}

}

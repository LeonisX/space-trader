package spacetrader.game.exceptions;

import spacetrader.game.Game;
import spacetrader.game.enums.GameEndType;

public class GameEndException extends RuntimeException {

    public GameEndException(int endType) {
        Game.getCurrentGame().setEndStatus(endType);
    }

}

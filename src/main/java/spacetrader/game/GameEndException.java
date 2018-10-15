package spacetrader.game;

import spacetrader.game.enums.GameEndType;

public class GameEndException extends RuntimeException {

    public GameEndException(GameEndType endType) {
        Game.currentGame().setEndStatus(endType);
    }

}

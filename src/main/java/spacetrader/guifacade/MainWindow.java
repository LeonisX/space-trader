package spacetrader.guifacade;

import spacetrader.game.Game;

/**
 * Marks the abilities of the game's main window.
 *
 * @author Aviv
 */
public interface MainWindow extends GuiWindow {

    void updateAll();

    void updateStatusBar();

    void setInGameControlsEnabled(boolean enabled);

    void setGame(Game game);
}

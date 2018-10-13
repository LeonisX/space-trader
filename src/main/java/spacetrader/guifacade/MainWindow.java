package spacetrader.guifacade;

import spacetrader.Game;

/**
 * Marks the abilities of the game's main window.
 *
 * @author Aviv
 */
public interface MainWindow extends GuiWindow {

    void updateAll();

    void updateStatusBar();

    void SetInGameControlsEnabled(boolean enabled);

    void setGame(Game game);

}

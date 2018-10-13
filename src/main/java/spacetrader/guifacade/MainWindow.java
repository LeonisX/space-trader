package spacetrader.guifacade;

import spacetrader.Game;

/**
 * Marks the abilities of the game's main window.
 *
 * @author Aviv
 */
public interface MainWindow extends GuiWindow
{
	void UpdateAll();
	void UpdateStatusBar();
	void SetInGameControlsEnabled(boolean enabled);
	void setGame(Game game);
}

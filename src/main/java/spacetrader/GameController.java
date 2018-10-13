package spacetrader;

import spacetrader.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

/**
 * This is kind-a temp class, to hold functions that are moved from the gui classes (VIEW) downwards. There is already a
 * CONTROLLER class (Called Game) but it's just too big to factor everything directly into it.
 *
 * I'm guessing that later on, all methods from this class will be distributed around the various data classes, or into the Game
 * class.
 *
 * @author Aviv
 */
public class GameController
{
	private final Game game;
	private final MainWindow mainWindow;

	public GameController(Game game, MainWindow spaceTrader)
	{
		this.game = game;
		mainWindow = spaceTrader;
	}


	public String SaveGameFile = null;
	public int SaveGameDays = -1;


	public void CargoBuy(int tradeItem, boolean max)
	{
		game.CargoBuySystem(tradeItem, max);
		mainWindow.UpdateAll();
	}

	public void CargoSell(int tradeItem, boolean all)
	{
		if (game.PriceCargoSell()[tradeItem] > 0)
			game.CargoSellSystem(tradeItem, all);
		else
			game.CargoDump(tradeItem);
		mainWindow.UpdateAll();
	}

	public void ClearHighScores()
	{
		HighScoreRecord[] highScores = new HighScoreRecord[3];
		Functions.SaveFile(Consts.HighScoreFile, STSerializableObject.ArrayToArrayList(highScores));
	}

	public void AddHighScore(HighScoreRecord highScore)
	{
		HighScoreRecord[] highScores = Functions.GetHighScores();
		highScores[0] = highScore;
		Util.sort(highScores);

		Functions.SaveFile(Consts.HighScoreFile, STSerializableObject.ArrayToArrayList(highScores));
	}

	public void GameEnd()
	{
		mainWindow.SetInGameControlsEnabled(false);

		AlertType alertType = AlertType.Alert;
		switch (game.getEndStatus())
		{
		case Killed:
			alertType = AlertType.GameEndKilled;
			break;
		case Retired:
			alertType = AlertType.GameEndRetired;
			break;
		case BoughtMoon:
			alertType = AlertType.GameEndBoughtMoon;
			break;
		}

		GuiFacade.alert(alertType);

		GuiFacade.alert(AlertType.GameEndScore, Functions.FormatNumber(game.Score() / 10), Functions
		.FormatNumber(game.Score() % 10));

		HighScoreRecord candidate = new HighScoreRecord(game.Commander().Name(), game.Score(), game.getEndStatus(),
				game.Commander().getDays(), game.Commander().Worth(), game.Difficulty());
		if (candidate.CompareTo(Functions.GetHighScores()[0]) > 0)
		{
			if (game.Cheats().cheatMode)
				GuiFacade.alert(AlertType.GameEndHighScoreCheat);
			else
			{
				AddHighScore(candidate);
				GuiFacade.alert(AlertType.GameEndHighScoreAchieved);
			}
		} else
			GuiFacade.alert(AlertType.GameEndHighScoreMissed);

		Game.CurrentGame(null);
		mainWindow.setGame(null);
	}

	public void LoadGame(String fileName)
	{
		try
		{
			Object obj = Functions.LoadFile(fileName, false);
			if (obj != null)
			{
				mainWindow.setGame(new Game((Hashtable)obj, mainWindow));
				SaveGameFile = fileName;
				SaveGameDays = game.Commander().getDays();

				mainWindow.SetInGameControlsEnabled(true);
				mainWindow.UpdateAll();
			}
		} catch (FutureVersionException ex)
		{
			GuiFacade.alert(AlertType.FileErrorOpen, fileName, Strings.FileFutureVersion);
		}
	}

	public void SaveGame(String fileName, boolean saveFileName)
	{
		if (Functions.SaveFile(fileName, game.Serialize()) && saveFileName)
			SaveGameFile = fileName;

		SaveGameDays = game.Commander().getDays();
	}

	static final String SAVE_ARRIVAL = "autosave_arrival.sav";
	static final String SAVE_DEPARTURE = "autosave_departure.sav";

	public void autoSave_arive()
	{
		if (game.getAutoSave())
			SaveGame(SAVE_ARRIVAL, false);
	}

	public void autoSave_depart()
	{
		if (game.getAutoSave())
			SaveGame(SAVE_DEPARTURE, false);
	}
}

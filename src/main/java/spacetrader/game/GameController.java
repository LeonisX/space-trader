package spacetrader.game;

import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

/**
 * This is kind-a temp class, to hold functions that are moved from the gui classes (VIEW) downwards. There is already a
 * CONTROLLER class (Called Game) but it's just too big to factor everything directly into it.
 * <p>
 * I'm guessing that later on, all methods from this class will be distributed around the various data classes, or into the Game
 * class.
 *
 * @author Aviv
 */
public class GameController {

    private static final String SAVE_ARRIVAL = "autosave_arrival.sav";
    private static final String SAVE_DEPARTURE = "autosave_departure.sav";
    private final Game game;
    private final MainWindow mainWindow;
    public String saveGameFile = null;
    public int saveGameDays = -1;

    public GameController(Game game, MainWindow spaceTrader) {
        this.game = game;
        mainWindow = spaceTrader;
    }

    public void cargoBuy(int tradeItem, boolean max) {
        game.cargoBuySystem(tradeItem, max);
        mainWindow.updateAll();
    }

    public void cargoSell(int tradeItem, boolean all) {
        if (game.getPriceCargoSell()[tradeItem] > 0) {
            game.cargoSellSystem(tradeItem, all);
        } else {
            game.cargoDump(tradeItem);
        }
        mainWindow.updateAll();
    }

    //TODO ???
    public void clearHighScores() {
        HighScoreRecord[] highScores = new HighScoreRecord[3];
        Functions.saveFile(Consts.HighScoreFile, STSerializableObject.arrayToArrayList(highScores));
    }

    private void addHighScore(HighScoreRecord highScore) {
        HighScoreRecord[] highScores = Functions.getHighScores();
        highScores[0] = highScore;
        Util.sort(highScores);

        Functions.saveFile(Consts.HighScoreFile, STSerializableObject.arrayToArrayList(highScores));
    }

    public void gameEnd() {
        mainWindow.setInGameControlsEnabled(false);

        AlertType alertType = AlertType.Alert;
        switch (game.getEndStatus()) {
            case KILLED:
                alertType = AlertType.GameEndKilled;
                break;
            case RETIRED:
                alertType = AlertType.GameEndRetired;
                break;
            case BOUGHT_MOON:
                alertType = AlertType.GameEndBoughtMoon;
                break;
        }

        GuiFacade.alert(alertType);

        GuiFacade.alert(AlertType.GameEndScore, Functions.formatNumber(game.getScore() / 10), Functions
                .formatNumber(game.getScore() % 10));

        HighScoreRecord candidate = new HighScoreRecord(game.getCommander().getName(), game.getScore(), game.getEndStatus(),
                game.getCommander().getDays(), game.getCommander().getWorth(), game.getDifficulty());
        if (candidate.compareTo(Functions.getHighScores()[0]) > 0) {
            if (game.getCheats().isCheatMode()) {
                GuiFacade.alert(AlertType.GameEndHighScoreCheat);
            } else {
                addHighScore(candidate);
                GuiFacade.alert(AlertType.GameEndHighScoreAchieved);
            }
        } else {
            GuiFacade.alert(AlertType.GameEndHighScoreMissed);
        }

        Game.setCurrentGame(null);
        mainWindow.setGame(null);
    }

    public void loadGame(String fileName) {
        try {
            Object obj = Functions.loadFile(fileName, false);
            if (obj != null) {
                mainWindow.setGame(new Game((Hashtable) obj, mainWindow));
                saveGameFile = fileName;
                saveGameDays = game.getCommander().getDays();

                mainWindow.setInGameControlsEnabled(true);
                mainWindow.updateAll();
            }
        } catch (FutureVersionException ex) {
            GuiFacade.alert(AlertType.FileErrorOpen, fileName, Strings.FileFutureVersion);
        }
    }

    public void saveGame(String fileName, boolean saveFileName) {
        if (Functions.saveFile(fileName, game.serialize()) && saveFileName)
            saveGameFile = fileName;

        saveGameDays = game.getCommander().getDays();
    }

    public void autoSaveOnArrival() {
        if (game.getAutoSave()) {
            saveGame(SAVE_ARRIVAL, false);
        }
    }

    public void autoSaveOnDeparture() {
        if (game.getAutoSave()) {
            saveGame(SAVE_DEPARTURE, false);
        }
    }
}

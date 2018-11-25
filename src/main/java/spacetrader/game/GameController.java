package spacetrader.game;

import spacetrader.game.enums.AlertType;
import spacetrader.game.exceptions.FutureVersionException;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.util.Functions;
import spacetrader.util.IOUtils;
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
    private final transient MainWindow mainWindow;
    private String saveGameFile = null;
    private int saveGameDays = -1;

    public GameController(Game game, MainWindow spaceTrader) {
        this.game = game;
        mainWindow = spaceTrader;
        if (game != null) {
            game.setController(this);
        }
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
        IOUtils.writeObjectToFile(Consts.HighScoreFile, highScores);
    }

    private void addHighScore(HighScoreRecord highScore) {
        HighScoreRecord[] highScores = IOUtils.getHighScores();
        highScores[0] = highScore;
        Util.sort(highScores);

        IOUtils.writeObjectToFile(Consts.HighScoreFile, highScores);
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
        if (candidate.compareTo(IOUtils.getHighScores()[0]) > 0) {
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

    public static GameController loadGame(String fileName, MainWindow mainWindow) {
        try {
            Game game = (Game) IOUtils.readObjectFromFile(fileName, false).orElse(null);
            if (game != null) {
                game.setParentWindow(mainWindow);
                Game.setCurrentGame(game);
                GameController gameController = new GameController(game, mainWindow);
                gameController.setSaveGameFile(fileName);
                gameController.setSaveGameDays(game.getCommander().getDays());

                mainWindow.setGame(game);
                mainWindow.setInGameControlsEnabled(true);
                mainWindow.updateAll();
                return gameController;
            }
        } catch (FutureVersionException ex) {
            GuiFacade.alert(AlertType.FileErrorOpen, fileName, Strings.FileFutureVersion);
        }
        return new GameController(null, mainWindow);
    }

    public void saveGame(String fileName, boolean saveFileName) {
        if (IOUtils.writeObjectToFile(fileName, game) && saveFileName)
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

    public String getSaveGameFile() {
        return saveGameFile;
    }

    public void setSaveGameFile(String saveGameFile) {
        this.saveGameFile = saveGameFile;
    }

    public int getSaveGameDays() {
        return saveGameDays;
    }

    public void setSaveGameDays(int saveGameDays) {
        this.saveGameDays = saveGameDays;
    }
}

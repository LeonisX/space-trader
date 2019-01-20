package spacetrader.game;

import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.GameEndType;
import spacetrader.game.exceptions.FutureVersionException;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.util.Functions;
import spacetrader.util.IOUtils;
import spacetrader.util.Util;

import java.io.Serializable;

import static spacetrader.game.quest.enums.EventName.ON_BEFORE_GAME_END;
import static spacetrader.game.quest.enums.EventName.ON_GAME_END_ALERT;

/**
 * This is kind-a temp class, to hold functions that are moved from the gui classes (VIEW) downwards. There is already a
 * CONTROLLER class (Called Game) but it's just too big to factor everything directly into it.
 * <p>
 * I'm guessing that later on, all methods from this class will be distributed around the various data classes, or into the Game
 * class.
 *
 * @author Aviv
 */
public class GameController implements Serializable {

    private static final String SAVE_ARRIVAL = "autosave_arrival.sav";
    private static final String SAVE_DEPARTURE = "autosave_departure.sav";
    private final Game game;
    private final transient MainWindow mainWindow;
    private String saveGameFile = null;
    private int saveGameDays = -1;

    public GameController(Game game, MainWindow spaceTrader) {
        this.game = game;
        this.mainWindow = spaceTrader;
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

        game.getQuestSystem().fireEvent(ON_BEFORE_GAME_END);

        if (game.getEndStatus() < 1000) {

            AlertType alertType = AlertType.Alert;
            switch (GameEndType.fromInt(game.getEndStatus())) {
                case KILLED:
                    alertType = AlertType.GameEndKilled;
                    break;
                case RETIRED:
                    alertType = AlertType.GameEndRetired;
                    break;
            }
            GuiFacade.alert(alertType);
        } else {
            game.getQuestSystem().fireEvent(ON_GAME_END_ALERT);
        }

        GuiFacade.alert(AlertType.GameEndScore, Functions.formatNumber(game.getScore() / 10), Functions
                .formatNumber(game.getScore() % 10));

        HighScoreRecord candidate = new HighScoreRecord(Game.getCommander().getName(), game.getScore(), game.getEndStatus(),
                Game.getCommander().getDays(), Game.getCommander().getWorth(), Game.getDifficulty());
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
        mainWindow.updateAll();
    }

    public static GameController loadGame(String fileName, MainWindow mainWindow) {
        try {
            Game game = (Game) IOUtils.readObjectFromFile(fileName, false).orElse(null);
            if (game != null) {
                game.setParentWindow(mainWindow);
                Game.setCurrentGame(game);
                //QuestSystem.setQuestSystem(game.isQuestSystem());
                game.getQuestSystem().initializeTransitionMaps();
                game.getQuestSystem().initializeLoggers();
                game.getQuestSystem().localizeQuests();
                GameController gameController = new GameController(game, mainWindow);
                gameController.setSaveGameFile(fileName);
                gameController.setSaveGameDays(Game.getCommander().getDays());

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
        if (IOUtils.writeObjectToFile(fileName, game) && saveFileName) {
            saveGameFile = fileName;
        }

        saveGameDays = Game.getCommander().getDays();
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

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}

package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.HighScoreRecord;
import spacetrader.game.Strings;
import spacetrader.game.enums.Difficulty;
import spacetrader.util.Functions;
import spacetrader.util.IOUtils;
import spacetrader.util.ReflectionUtils;

import java.util.List;
import java.util.stream.IntStream;

public class FormViewHighScores extends SpaceTraderForm {

    private Button closeButton = new Button();
    private Label rankLabel1 = new Label();
    private Label rankLabel2 = new Label();
    private Label rankLabel3 = new Label();
    private Label scoreLabelValue1 = new Label();
    private Label scoreLabelValue2 = new Label();
    private Label scoreLabelValue3 = new Label();
    private Label nameLabelValue1 = new Label();
    private Label nameLabelValue2 = new Label();
    private Label nameLabelValue3 = new Label();
    private Label statusLabelValue1 = new Label();
    private Label statusLabelValue2 = new Label();
    private Label statusLabelValue3 = new Label();

    public FormViewHighScores() {
        initializeComponent();

        Label[] lblName = new Label[]{nameLabelValue1, nameLabelValue2, nameLabelValue3};
        Label[] lblScore = new Label[]{scoreLabelValue1, scoreLabelValue2, scoreLabelValue3};
        Label[] lblStatus = new Label[]{statusLabelValue1, statusLabelValue2, statusLabelValue3};

        List<HighScoreRecord> highScores = IOUtils.getHighScores();
        IntStream.range(0, highScores.size()).forEach(i -> {
            HighScoreRecord highScore = highScores.get(i);
            lblName[i].setText(highScore.getName());
            lblScore[i].setText(Functions.formatNumber(highScore.getScore() / 10) + "." + highScore.getScore() % 10 + "%");
            String gameCompletion = highScore.getType() < 1000
                    ? Strings.GameCompletionTypes[highScore.getType()]
                    : getGameCompletionText(highScore.getType());
            lblStatus[i].setText(Functions.stringVars(Strings.HighScoreStatus, new String[]
                    {
                            gameCompletion,
                            Integer.toString(highScore.getDays()),
                            Functions.plural(highScore.getWorth(), Strings.MoneyUnit),
                            Strings.DifficultyLevels[highScore.getDifficulty().castToInt()].toLowerCase()
                    }));

            lblScore[i].setVisible(true);
            lblStatus[i].setVisible(true);
        });
    }

    private String getGameCompletionText(int type) {
        Game game;
        if (null == Game.getCurrentGame()) {
            game = new Game("", Difficulty.BEGINNER,1,1,1,1, null);
        } else {
            game = Game.getCurrentGame();
        }
        return game.getQuestSystem().getGameCompletionText(type);
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewHighScores");
        setText("High Scores");
        setClientSize(250, 221);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        rankLabel1.setAutoSize(true);
        rankLabel1.setLocation(8, 8);
        //rankLabel1.setSize(14, 13);
        rankLabel1.setText("1.");

        nameLabelValue1.setAutoSize(true);
        nameLabelValue1.setLocation(24, 8);
        //nameLabelValue1.setSize(144, 13);
        nameLabelValue1.setText(Strings.HighScoreEmpty);

        scoreLabelValue1.setAutoSize(true);
        scoreLabelValue1.setLocation(210, 8);
        //scoreLabelValue1.setSize(43, 13);
        //scoreLabelValue1.setText("888.8%");
        scoreLabelValue1.setVisible(false);

        statusLabelValue1.setLocation(24, 24);
        statusLabelValue1.setSize(200, 48);
        //statusLabelValue1.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        statusLabelValue1.setVisible(false);

        rankLabel2.setAutoSize(true);
        rankLabel2.setLocation(8, 76);
        //rankLabel2.setSize(14, 13);
        rankLabel2.setText("2.");

        nameLabelValue2.setAutoSize(true);
        nameLabelValue2.setLocation(24, 76);
        //nameLabelValue2.setSize(144, 13);
        nameLabelValue2.setText(Strings.HighScoreEmpty);

        scoreLabelValue2.setAutoSize(true);
        scoreLabelValue2.setLocation(210, 76);
        //scoreLabelValue2.setSize(43, 13);
        //scoreLabelValue2.setText("888.8%");
        scoreLabelValue2.setVisible(false);

        statusLabelValue2.setLocation(24, 92);
        statusLabelValue2.setSize(200, 48);
        //statusLabelValue2.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        statusLabelValue2.setVisible(false);

        rankLabel3.setAutoSize(true);
        rankLabel3.setLocation(8, 144);
        //rankLabel3.setSize(14, 13);
        rankLabel3.setText("3.");

        nameLabelValue3.setAutoSize(true);
        nameLabelValue3.setLocation(24, 144);
        //nameLabelValue3.setSize(144, 13);
        nameLabelValue3.setText(Strings.HighScoreEmpty);

        scoreLabelValue3.setAutoSize(true);
        scoreLabelValue3.setLocation(210, 144);
        //scoreLabelValue3.setSize(43, 13);
        //scoreLabelValue3.setText("888.8%");
        scoreLabelValue3.setVisible(false);

        statusLabelValue3.setLocation(24, 160);
        statusLabelValue3.setSize(200, 48);
        //statusLabelValue3.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        statusLabelValue3.setVisible(false);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(rankLabel1, nameLabelValue1, scoreLabelValue1, statusLabelValue1, rankLabel2,
                nameLabelValue2, scoreLabelValue2, statusLabelValue2, rankLabel3, nameLabelValue3,
                scoreLabelValue3, statusLabelValue3, closeButton);

        ReflectionUtils.loadControlsData(this);
    }
}

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.Difficulty;
import spacetrader.gui.cheat.FormInput;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

public class FormViewCommander extends SpaceTraderForm {

    private Button closeButton = new Button();
    private Label nameLabel = new Label();
    private Label nameLabelValue = new Label();
    private Label difficultyLabelValue = new Label();
    private Label timeLabel = new Label();
    private Label cashLabel = new Label();
    private Label debtLabel = new Label();
    private Label netWorthLabel = new Label();
    private Label difficultyLabel = new Label();
    private Label timeLabelValue = new Label();
    private Panel skillsPanel = new Panel();
    private Label engineerLabelValue = new Label();
    private Label traderLabelValue = new Label();
    private Label fighterLabelValue = new Label();
    private Label pilotLabelValue = new Label();
    private Label engineerLabel = new Label();
    private Label traderLabel = new Label();
    private Label fighterLabel = new Label();
    private Label pilotLabel = new Label();
    private Panel financesPanel = new Panel();
    private Label netWorthLabelValue = new Label();
    private Label debtLabelValue = new Label();
    private Label cashLabelValue = new Label();
    private Label killsLabelValue = new Label();
    private Label reputationLabelValue = new Label();
    private Label policeLabelValue = new Label();
    private Label policeLabel = new Label();
    private Label reputationLabel = new Label();
    private Label killsLabel = new Label();
    private Panel notorietyPanel = new Panel();
    private Label bountyTitleLabelValue = new Label();
    private Label bountyLabelValue = new Label();

    //Cheats
    private Button difficultyButton = new Button();
    private Button addDayButton = new Button();
    private Button addNineDaysButton = new Button();
    private Button cashButton = new Button();
    private Button debtButton = new Button();
    private Button reputationButton = new Button();
    private Button policeRecordButton = new Button();

    public FormViewCommander() {
        initializeComponent();
        initializeScreen();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewCommander");
        setText("Commander Status");
        setClientSize(235, 310);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        nameLabel.setAutoSize(true);
        nameLabel.setFont(FontCollection.bold825);
        nameLabel.setLocation(8, 9);
        //nameLabel.setSize(39, 16);
        nameLabel.setText("Name:");

        nameLabelValue.setAutoSize(true);
        nameLabelValue.setLocation(74, 8);
        //nameLabelValue.setSize(155, 13);
        //nameLabelValue.setText("Yuri Gagarin");

        difficultyLabel.setAutoSize(true);
        difficultyLabel.setFont(FontCollection.bold825);
        difficultyLabel.setLocation(8, 25);
        //difficultyLabel.setSize(53, 16);
        difficultyLabel.setText("Difficulty:");

        difficultyLabelValue.setAutoSize(true);
        difficultyLabelValue.setLocation(74, 24);
        //difficultyLabelValue.setSize(58, 13);
        //difficultyLabelValue.setText("Impossible");

        difficultyButton.setLocation(205, 23);
        difficultyButton.setSize(16, 16);
        difficultyButton.setText("");
        difficultyButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                FormInput formInput = new FormInput(Game.getDifficultyId(), "Difficulty", "What is the difficulty of the game [0..4]?");
                if (formInput.showDialog() == DialogResult.OK) {
                    Game.setDifficulty(Difficulty.fromInt(formInput.getValue()));
                    initializeScreen();
                    Game.getCurrentGame().getController().getMainWindow().updateAll();
                }
            }
        });

        timeLabel.setAutoSize(true);
        timeLabel.setFont(FontCollection.bold825);
        timeLabel.setLocation(8, 41);
        //timeLabel.setSize(34, 16);
        timeLabel.setText("Time:");

        timeLabelValue.setAutoSize(true);
        timeLabelValue.setLocation(74, 40);
        //timeLabelValue.setSize(66, 13);
        //timeLabelValue.setText("88,888 days");

        addDayButton.setAutoWidth(true);
        addDayButton.setLocation(120, 38);
        addDayButton.setSize(22, 18);
        addDayButton.setText("+1");
        addDayButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                addDayButtonClick(1);
                Game.getCurrentGame().getController().getMainWindow().updateAll();
            }
        });

        addNineDaysButton.setAutoWidth(true);
        addNineDaysButton.setLocation(150, 38);
        addNineDaysButton.setSize(22, 18);
        addNineDaysButton.setText("+9");
        addNineDaysButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                addDayButtonClick(9);
                Game.getCurrentGame().getController().getMainWindow().updateAll();
            }
        });

        skillsPanel.setLocation(8, 64);
        skillsPanel.setSize(220, 60);
        skillsPanel.setTabStop(false);
        skillsPanel.setText("Skills");

        skillsPanel.getControls().addAll(pilotLabel, pilotLabelValue, traderLabel, traderLabelValue,
                fighterLabel, fighterLabelValue, engineerLabel, engineerLabelValue);

        pilotLabel.setAutoSize(true);
        pilotLabel.setFont(FontCollection.bold825);
        pilotLabel.setLocation(10, 21);
        //pilotLabel.setSize(31, 16);
        pilotLabel.setText("Pilot:");

        pilotLabelValue.setAutoSize(true);
        pilotLabelValue.setLocation(70, 20);
        //pilotLabelValue.setSize(40, 13);
        //pilotLabelValue.setText("88 (88)");

        traderLabel.setAutoSize(true);
        traderLabel.setFont(FontCollection.bold825);
        traderLabel.setLocation(10, 37);
        //traderLabel.setSize(42, 16);
        traderLabel.setText("Trader:");

        traderLabelValue.setAutoSize(true);
        traderLabelValue.setLocation(70, 36);
        //traderLabelValue.setSize(40, 13);
        //traderLabelValue.setText("88 (88)");

        fighterLabel.setAutoSize(true);
        fighterLabel.setFont(FontCollection.bold825);
        fighterLabel.setLocation(113, 21);
        //fighterLabel.setSize(44, 16);
        fighterLabel.setText("Fighter:");

        fighterLabelValue.setAutoSize(true);
        fighterLabelValue.setLocation(176, 20);
        //fighterLabelValue.setSize(40, 13);
        //fighterLabelValue.setText("88 (88)");

        engineerLabel.setAutoSize(true);
        engineerLabel.setFont(FontCollection.bold825);
        engineerLabel.setLocation(113, 37);
        //engineerLabel.setSize(55, 16);
        engineerLabel.setText("Engineer:");

        engineerLabelValue.setAutoSize(true);
        engineerLabelValue.setLocation(176, 36);
        //engineerLabelValue.setSize(40, 13);
        //engineerLabelValue.setText("88 (88)");

        financesPanel.setLocation(8, 128);
        financesPanel.setSize(220, 77);
        financesPanel.setTabStop(false);
        financesPanel.setText("Finances");

        financesPanel.getControls().addAll(cashLabel, cashLabelValue, cashButton, debtLabel,
                debtLabelValue, debtButton, netWorthLabel, netWorthLabelValue);

        cashLabel.setAutoSize(true);
        cashLabel.setFont(FontCollection.bold825);
        cashLabel.setLocation(10, 21);
        //cashLabel.setSize(35, 16);
        cashLabel.setText("Cash:");

        cashLabelValue.setAutoSize(true);
        cashLabelValue.setLocation(113, 21);
        //cashLabelValue.setSize(70, 13);
        //cashLabelValue.setText("8,888,888 cr.");

        cashButton.setLocation(195, 20);
        cashButton.setSize(16, 16);
        cashButton.setText("");
        cashButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                FormInput formInput = new FormInput(Game.getCommander().getCash(), "Cash", "How much cash does the commander has?");
                if (formInput.showDialog() == DialogResult.OK) {
                    Game.getCommander().setCash(formInput.getValue());
                    initializeScreen();
                    Game.getCurrentGame().getController().getMainWindow().updateAll();
                }
            }
        });

        debtLabel.setAutoSize(true);
        debtLabel.setFont(FontCollection.bold825);
        debtLabel.setLocation(10, 37);
        //debtLabel.setSize(32, 16);
        debtLabel.setText("Debt:");

        debtLabelValue.setAutoSize(true);
        debtLabelValue.setLocation(113, 37);
        //debtLabelValue.setSize(70, 13);
        //debtLabelValue.setText("8,888,888 cr.");

        debtButton.setLocation(195, 36);
        debtButton.setSize(16, 16);
        debtButton.setText("");
        debtButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                FormInput formInput = new FormInput(Game.getCommander().getDebt(), "Debt", "What is the debt of the commander?");
                if (formInput.showDialog() == DialogResult.OK) {
                    Game.getCommander().setDebt(formInput.getValue());
                    initializeScreen();
                    Game.getCurrentGame().getController().getMainWindow().updateAll();
                }
            }
        });

        netWorthLabel.setAutoSize(true);
        netWorthLabel.setFont(FontCollection.bold825);
        netWorthLabel.setLocation(10, 53);
        //netWorthLabel.setSize(60, 16);
        netWorthLabel.setText("Net Worth:");

        netWorthLabelValue.setAutoSize(true);
        netWorthLabelValue.setLocation(113, 53);
        //netWorthLabelValue.setSize(70, 13);
        //netWorthLabelValue.setText("8,888,888 cr.");

        notorietyPanel.setLocation(8, 210);
        notorietyPanel.setSize(220, 92);
        notorietyPanel.setTabStop(false);
        notorietyPanel.setText("Notoriety");

        notorietyPanel.getControls().addAll(killsLabel, killsLabelValue, reputationLabel, reputationLabelValue,
                reputationButton, policeLabel, policeLabelValue, policeRecordButton,bountyTitleLabelValue,
                bountyLabelValue);

        killsLabel.setAutoSize(true);
        killsLabel.setFont(FontCollection.bold825);
        killsLabel.setLocation(10, 21);
        //killsLabel.setSize(30, 16);
        killsLabel.setText("Kills:");

        killsLabelValue.setAutoSize(true);
        killsLabelValue.setLocation(115, 20);
        //killsLabelValue.setSize(33, 13);
        //killsLabelValue.setText("8,888");

        reputationLabel.setAutoSize(true);
        reputationLabel.setFont(FontCollection.bold825);
        reputationLabel.setLocation(10, 37);
        //reputationLabel.setSize(65, 16);
        reputationLabel.setText("Reputation:");

        reputationLabelValue.setAutoSize(true);
        reputationLabelValue.setLocation(115, 36);
        //reputationLabelValue.setSize(88, 13);
        //reputationLabelValue.setText("Mostly Harmless");

        reputationButton.setLocation(195, 35);
        reputationButton.setSize(16, 16);
        reputationButton.setText("");
        reputationButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                FormInput formInput = new FormInput(Game.getCommander().getReputationScore(), "Reputation", "What is the reputation of the commander [0..1500]?");
                if (formInput.showDialog() == DialogResult.OK) {
                    Game.getCommander().setReputationScore(formInput.getValue());
                    initializeScreen();
                    Game.getCurrentGame().getController().getMainWindow().updateAll();
                }
            }
        });

        policeLabel.setAutoSize(true);
        policeLabel.setFont(FontCollection.bold825);
        policeLabel.setLocation(10, 53);
        //policeLabel.setSize(81, 16);
        policeLabel.setText("Police Record:");

        policeLabelValue.setAutoSize(true);
        policeLabelValue.setLocation(115, 52);
        //policeLabelValue.setSize(63, 13);
        //policeLabelValue.setText("Psychopath");

        policeRecordButton.setLocation(195, 51);
        policeRecordButton.setSize(16, 16);
        policeRecordButton.setText("");
        policeRecordButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                FormInput formInput = new FormInput(Game.getCommander().getPoliceRecordScore(), "Police Record", "What is the police record of the commander [-100..75]?");
                if (formInput.showDialog() == DialogResult.OK) {
                    Game.getCommander().setPoliceRecordScore(formInput.getValue());
                    initializeScreen();
                    Game.getCurrentGame().getController().getMainWindow().updateAll();
                }
            }
        });

        bountyTitleLabelValue.setAutoSize(true);
        bountyTitleLabelValue.setFont(FontCollection.bold825);
        bountyTitleLabelValue.setLocation(11, 69);
        //bountyTitleLabelValue.setSize(84, 16);
        //bountyTitleLabelValue.setText("Bounty offered:");
        bountyTitleLabelValue.setVisible(false);

        bountyLabelValue.setAutoSize(true);
        bountyLabelValue.setLocation(115, 68);
        //bountyLabelValue.setSize(72, 13);
        //bountyLabelValue.setText("8,888,888 cr.");
        bountyLabelValue.setVisible(false);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(26, 27);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(nameLabel, nameLabelValue, difficultyLabel,  difficultyLabelValue, difficultyButton,
                timeLabel, timeLabelValue, addDayButton, addNineDaysButton, skillsPanel, financesPanel, notorietyPanel,
                closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void initializeScreen() {
        Commander cmdr = Game.getCommander();

        nameLabelValue.setText(cmdr.getName());
        difficultyLabelValue.setText(Strings.DifficultyLevels[Game.getDifficultyId()]);
        timeLabelValue.setText(Functions.plural(cmdr.getDays(), Strings.TimeUnit));

        pilotLabelValue.setText(cmdr.getPilot() + " (" + cmdr.getShip().getPilot() + ")");
        fighterLabelValue.setText(cmdr.getFighter() + " (" + cmdr.getShip().getFighter() + ")");
        traderLabelValue.setText(cmdr.getTrader() + " (" + cmdr.getShip().getTrader() + ")");
        engineerLabelValue.setText(cmdr.getEngineer() + " (" + cmdr.getShip().getEngineer() + ")");

        cashLabelValue.setText(Functions.formatMoney(cmdr.getCash()));
        debtLabelValue.setText(Functions.formatMoney(cmdr.getDebt()));
        netWorthLabelValue.setText(Functions.formatMoney(cmdr.getWorth()));

        killsLabelValue.setText(Functions.formatNumber(cmdr.getKillsPirate() + cmdr.getKillsPolice() + cmdr.getKillsTrader()));
        policeLabelValue.setText(PoliceRecord.getPoliceRecordFromScore().getName());
        reputationLabelValue.setText(Reputation.getReputationFromScore().getName());

        int score = cmdr.getPoliceRecordScore();
        if (score <= Consts.PoliceRecordScoreCrook) {
            bountyTitleLabelValue.setVisible(true);
            bountyTitleLabelValue.setText(Strings.CommanderBountyOffered);
            bountyLabelValue.setVisible(true);
            bountyLabelValue.setText(Functions.formatMoney(-1000 * score));
        } else if (score >= Consts.PoliceRecordScoreTrusted) {
            bountyTitleLabelValue.setVisible(true);
            bountyTitleLabelValue.setText(Strings.CommanderAngryKingpins);
            bountyLabelValue.setVisible(true);
            bountyLabelValue.setText(Functions.formatNumber(score / 5));
        } else {
            bountyTitleLabelValue.setVisible(false);
            bountyLabelValue.setVisible(false);
        }

        boolean visible = GlobalAssets.isDebug() || Game.getCurrentGame().getCheats().isCheatMode();
        cashButton.setVisible(visible);
        debtButton.setVisible(visible);
        reputationButton.setVisible(visible);
        policeRecordButton.setVisible(visible);
    }

    private void addDayButtonClick(int daysCount) {
        Game.getCurrentGame().incDays(daysCount);
        initializeScreen();
    }
}

package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

public class FormViewCommander extends SpaceTraderForm {
    
    private Game game = Game.getCurrentGame();
    
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

        suspendLayout();

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

        timeLabel.setAutoSize(true);
        timeLabel.setFont(FontCollection.bold825);
        timeLabel.setLocation(8, 41);
        //timeLabel.setSize(34, 16);
        timeLabel.setText("Time:");

        timeLabelValue.setAutoSize(true);
        timeLabelValue.setLocation(74, 40);
        //timeLabelValue.setSize(66, 13);
        //timeLabelValue.setText("88,888 days");

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
        engineerLabelValue.setLocation(173, 36);
        //engineerLabelValue.setSize(40, 13);
        //engineerLabelValue.setText("88 (88)");

        financesPanel.setLocation(8, 128);
        financesPanel.setSize(220, 77);
        financesPanel.setTabStop(false);
        financesPanel.setText("Finances");

        financesPanel.getControls().addAll(cashLabel, cashLabelValue, debtLabel,
                debtLabelValue, netWorthLabel, netWorthLabelValue);

        cashLabel.setAutoSize(true);
        cashLabel.setFont(FontCollection.bold825);
        cashLabel.setLocation(10, 21);
        //cashLabel.setSize(35, 16);
        cashLabel.setText("Cash:");

        cashLabelValue.setAutoSize(true);
        cashLabelValue.setLocation(113, 21);
        //cashLabelValue.setSize(70, 13);
        //cashLabelValue.setText("8,888,888 cr.");

        debtLabel.setAutoSize(true);
        debtLabel.setFont(FontCollection.bold825);
        debtLabel.setLocation(10, 37);
        //debtLabel.setSize(32, 16);
        debtLabel.setText("Debt:");

        debtLabelValue.setAutoSize(true);
        debtLabelValue.setLocation(113, 37);
        //debtLabelValue.setSize(70, 13);
        //debtLabelValue.setText("8,888,888 cr.");

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

        notorietyPanel.getControls().addAll(killsLabel, killsLabelValue, reputationLabel,
                reputationLabelValue, policeLabel, policeLabelValue, bountyTitleLabelValue, bountyLabelValue);

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

        policeLabel.setAutoSize(true);
        policeLabel.setFont(FontCollection.bold825);
        policeLabel.setLocation(10, 53);
        //policeLabel.setSize(81, 16);
        policeLabel.setText("Police Record:");

        policeLabelValue.setAutoSize(true);
        policeLabelValue.setLocation(115, 52);
        //policeLabelValue.setSize(63, 13);
        //policeLabelValue.setText("Psychopath");

        bountyTitleLabelValue.setAutoSize(true);
        bountyTitleLabelValue.setFont(FontCollection.bold825);
        bountyTitleLabelValue.setLocation(11, 68);
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

        controls.addAll(nameLabel, nameLabelValue, difficultyLabel,  difficultyLabelValue,
                timeLabel, timeLabelValue, skillsPanel, financesPanel, notorietyPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void initializeScreen() {
        Commander cmdr = game.getCommander();

        nameLabelValue.setText(cmdr.getName());
        difficultyLabelValue.setText(Strings.DifficultyLevels[game.getDifficulty().castToInt()]);
        // TODO день, дня, дней
        timeLabelValue.setText(Functions.plural(cmdr.getDays(), Strings.TimeUnit));

        pilotLabelValue.setText(cmdr.getPilot() + " (" + cmdr.getShip().getPilot() + ")");
        fighterLabelValue.setText(cmdr.getFighter() + " (" + cmdr.getShip().getFighter() + ")");
        traderLabelValue.setText(cmdr.getTrader() + " (" + cmdr.getShip().getTrader() + ")");
        engineerLabelValue.setText(cmdr.getEngineer() + " (" + cmdr.getShip().getEngineer() + ")");

        cashLabelValue.setText(Functions.formatMoney(cmdr.getCash()));
        debtLabelValue.setText(Functions.formatMoney(cmdr.getDebt()));
        netWorthLabelValue.setText(Functions.formatMoney(cmdr.getWorth()));

        killsLabelValue.setText(Functions.formatNumber(cmdr.getKillsPirate() + cmdr.getKillsPolice() + cmdr.getKillsTrader()));
        policeLabelValue.setText(PoliceRecord.getPoliceRecordFromScore(cmdr.getPoliceRecordScore()).getName());
        reputationLabelValue.setText(Reputation.getReputationFromScore(cmdr.getReputationScore()).getName());

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
    }
}

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.*;
import spacetrader.game.Consts;
import spacetrader.game.GlobalAssets;
import spacetrader.game.Strings;
import spacetrader.game.enums.Difficulty;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

public class FormNewCommander extends SpaceTraderForm {

    private Label nameLabel = new Label();
    private TextBox nameTextBox = new TextBox();
    private Button closeButton = new Button();
    private Label difficultyLabel = new Label();
    private Label skillPointsLabel = new Label();
    private Label pilotLabel = new Label();
    private Label fighterLabel = new Label();
    private Label traderLabel = new Label();
    private Label engineerLabel = new Label();
    private ComboBox<String> difficultyComboBox = new ComboBox<>();
    private NumericUpDown numPilot = new NumericUpDown();
    private Button okButton = new Button();
    private Label skillPointsRemainingLabel = new Label();
    private Label skillPointsLabelValue = new Label();
    private NumericUpDown numFighter = new NumericUpDown();
    private NumericUpDown numTrader = new NumericUpDown();
    private NumericUpDown numEngineer = new NumericUpDown();

    private int points = Consts.AvailableSkillPointsOnStart;

    public FormNewCommander() {
        initializeComponent();
        difficultyComboBox.setSelectedIndex(2);
        updateOkEnabled();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formNewCommander");
        setText("New Commander");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(212, 250);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(closeButton);

        nameLabel.setAutoSize(true);
        nameLabel.setLocation(8, 8);
        //nameLabel.setSize(38, 13);
        nameLabel.setText("Name:");

        nameTextBox.setLocation(80, 5);
        nameTextBox.setSize(120, 20);
        nameTextBox.setTabIndex(1);
        nameTextBox.setTextChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                updateOkEnabled();
            }
        });

        difficultyLabel.setAutoSize(true);
        difficultyLabel.setLocation(8, 40);
        //difficultyLabel.setSize(50, 13);
        difficultyLabel.setText("Difficulty:");

        difficultyComboBox.getItems().addAll(Strings.DifficultyLevels);
        difficultyComboBox.setLocation(80, 37);
        difficultyComboBox.setSize(120, 21);
        difficultyComboBox.setTabIndex(2);

        skillPointsLabel.setAutoSize(true);
        skillPointsLabel.setLocation(8, 72);
        //skillPointsLabel.setSize(63, 13);
        skillPointsLabel.setText("Skill Points:");

        skillPointsLabelValue.setAutoSize(true);
        skillPointsLabelValue.setLocation(82, 72);
        //skillPointsLabelValue.setSize(17, 13);
        skillPointsLabelValue.setText(formatSkillPoints());

        pilotLabel.setAutoSize(true);
        pilotLabel.setLocation(25, 96);
        //pilotLabel.setSize(29, 13);
        pilotLabel.setText("Pilot:");

        numPilot.setLocation(80, 94);
        numPilot.setMaximum(10);
        numPilot.setMinimum(1);
        numPilot.setSize(46, 20);
        numPilot.setTabIndex(3);
        numPilot.setTextAlign(HorizontalAlignment.CENTER);
        numPilot.setValue(1);
        numPilot.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        numPilot.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });
        numPilot.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        fighterLabel.setAutoSize(true);
        fighterLabel.setLocation(25, 120);
        //fighterLabel.setSize(43, 13);
        fighterLabel.setText("Fighter:");

        numFighter.setLocation(80, 118);
        numFighter.setMaximum(10);
        numFighter.setMinimum(1);
        numFighter.setSize(46, 20);
        numFighter.setTabIndex(4);
        numFighter.setTextAlign(HorizontalAlignment.CENTER);
        numFighter.setValue(1);
        numFighter.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        numFighter.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });
        numFighter.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        traderLabel.setAutoSize(true);
        traderLabel.setLocation(25, 144);
        //traderLabel.setSize(41, 13);
        traderLabel.setText("Trader:");

        numTrader.setLocation(80, 142);
        numTrader.setMaximum(10);
        numTrader.setMinimum(1);
        numTrader.setSize(46, 20);
        numTrader.setTabIndex(5);
        numTrader.setTextAlign(HorizontalAlignment.CENTER);
        numTrader.setValue(1);
        numTrader.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        numTrader.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });
        numTrader.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        engineerLabel.setAutoSize(true);
        engineerLabel.setLocation(25, 168);
        //engineerLabel.setSize(53, 13);
        engineerLabel.setText("Engineer:");

        numEngineer.setLocation(80, 166);
        numEngineer.setMaximum(10);
        numEngineer.setMinimum(1);
        numEngineer.setSize(46, 20);
        numEngineer.setTabIndex(6);
        numEngineer.setTextAlign(HorizontalAlignment.CENTER);
        numEngineer.setValue(1);
        numEngineer.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        numEngineer.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });
        numEngineer.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.CENTER);
        okButton.setLocation(85, 200);
        okButton.setSize(36, 22);
        okButton.setTabStop(false);
        okButton.setText("Ok");

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(30, 31);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(nameLabel, nameTextBox, difficultyLabel, difficultyComboBox, skillPointsLabel,
                skillPointsLabelValue, skillPointsRemainingLabel, pilotLabel, numPilot, fighterLabel, numFighter,
                traderLabel, numTrader, engineerLabel, numEngineer, okButton, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void updateOkEnabled() {
        okButton.setEnabled((0 == points && nameTextBox.getText().length() > 0) || GlobalAssets.isDebug());
    }

    private void numValueChanged() {
        points = Consts.MaximalSkillPointsOnStart - (getPilot() + getFighter() + getTrader() + getEngineer());
        skillPointsLabelValue.setText(formatSkillPoints());
        numPilot.setMaximum(Math.min(10, getPilot() + points));
        numFighter.setMaximum(Math.min(10, getFighter() + points));
        numTrader.setMaximum(Math.min(10, getTrader() + points));
        numEngineer.setMaximum(Math.min(10, getEngineer() + points));

        updateOkEnabled();
    }

    private String formatSkillPoints() {
        return Functions.stringVars(Strings.PointsRemaining, Integer.toString(points));
    }

    private void numValueEnter(Object sender) {
        ((NumericUpDown) sender).select(0, (Integer.toString(((NumericUpDown) sender).getValue())).length());
    }

    String getCommanderName() {
        return nameTextBox.getText();
    }

    public Difficulty getDifficulty() {
        return Difficulty.fromInt(difficultyComboBox.getSelectedIndex());
    }

    public int getPilot() {
        return numPilot.getValue();
    }

    public int getFighter() {
        return numFighter.getValue();
    }

    public int getTrader() {
        return numTrader.getValue();
    }

    public int getEngineer() {
        return numEngineer.getValue();
    }
}

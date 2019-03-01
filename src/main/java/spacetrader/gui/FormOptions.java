package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.GameOptions;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

public class FormOptions extends SpaceTraderForm {

    private Button okButton = new Button();
    private Button cancelButton = new Button();
    private Label emptyLabelValue = new Label();
    private Label ignoreLabel = new Label();
    private CheckBox fuelCheckBox = new CheckBox();
    private CheckBox continuousAttackCheckBox = new CheckBox();
    private CheckBox attackFleeingCheckBox = new CheckBox();
    private CheckBox disableOpponentsCheckBox = new CheckBox();
    private CheckBox newspaperCheckBox = new CheckBox();
    private CheckBox showNewspaperCheckBox = new CheckBox();
    private CheckBox rangeCheckBox = new CheckBox();
    private CheckBox stopTrackingCheckBox = new CheckBox();
    private CheckBox loanCheckBox = new CheckBox();
    private CheckBox ignoreDealingTradersCheckBox = new CheckBox();
    private CheckBox reserveMoneyCheckBox = new CheckBox();
    private CheckBox ignoreTradersCheckBox = new CheckBox();
    private CheckBox ignorePiratesCheckBox = new CheckBox();
    private CheckBox ignorePoliceCheckBox = new CheckBox();
    private CheckBox repairCheckBox = new CheckBox();
    private NumericUpDown emptyNumericUpDown = new NumericUpDown();
    private Button saveButton = new Button();
    private Button loadButton = new Button();

    private boolean initializing = true;

    private GameOptions options = new GameOptions(false);

    public FormOptions() {
        initializeComponent();

        Game game = Game.getCurrentGame();
        if (game != null)
            getOptions().copyValues(game.getOptions());
        else {
            getOptions().loadFromDefaults(false);
            okButton.setEnabled(false);
            GuiFacade.alert(AlertType.OptionsNoGame);
        }

        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formOptions");
        setText("Options");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(545, 281);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(cancelButton);

        fuelCheckBox.setLocation(8, 8);
        fuelCheckBox.setSize(245, 16);
        fuelCheckBox.setTabIndex(1);
        fuelCheckBox.setText("Get full fuel tanks on arrival");
        fuelCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        repairCheckBox.setLocation(8, 24);
        repairCheckBox.setSize(245, 16);
        repairCheckBox.setTabIndex(2);
        repairCheckBox.setText("Get full hull repairs on arrival");
        repairCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        newspaperCheckBox.setLocation(8, 40);
        newspaperCheckBox.setSize(245, 16);
        newspaperCheckBox.setTabIndex(3);
        newspaperCheckBox.setText("Always pay for newspaper");
        newspaperCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        showNewspaperCheckBox.setLocation(24, 56);
        showNewspaperCheckBox.setSize(245, 16);
        showNewspaperCheckBox.setTabIndex(53);
        showNewspaperCheckBox.setText("Show newspaper on arrival");
        showNewspaperCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        rangeCheckBox.setLocation(250, 8);
        rangeCheckBox.setSize(310, 16);
        rangeCheckBox.setTabIndex(5);
        rangeCheckBox.setText("Show range to tracked system");
        rangeCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        stopTrackingCheckBox.setLocation(250, 24);
        stopTrackingCheckBox.setSize(310, 16);
        stopTrackingCheckBox.setTabIndex(6);
        stopTrackingCheckBox.setText("Stop tracking on arrival");
        stopTrackingCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        reserveMoneyCheckBox.setLocation(250, 40);
        reserveMoneyCheckBox.setSize(310, 16);
        reserveMoneyCheckBox.setTabIndex(7);
        reserveMoneyCheckBox.setText("Reserve money for warp costs");
        reserveMoneyCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        loanCheckBox.setLocation(250, 56);
        loanCheckBox.setSize(310, 16);
        loanCheckBox.setTabIndex(4);
        loanCheckBox.setText("Remind about loans");
        loanCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        emptyNumericUpDown.setLocation(8, 88);
        emptyNumericUpDown.setMaximum(99);
        emptyNumericUpDown.setSize(40, 20);
        emptyNumericUpDown.setTabIndex(8);
        emptyNumericUpDown.setValue(88);
        emptyNumericUpDown.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        emptyLabelValue.setAutoSize(true);
        emptyLabelValue.setLocation(52, 90);
        //emptyLabelValue.setSize(300, 16);
        emptyLabelValue.setText(getEmptyLabelValue());

        ignoreLabel.setAutoSize(true);
        ignoreLabel.setLocation(8, 120);
        //ignoreLabel.setSize(152, 16);
        ignoreLabel.setText("Always ignore when it is safe:");

        ignorePiratesCheckBox.setLocation(8, 136);
        ignorePiratesCheckBox.setSize(70, 16);
        ignorePiratesCheckBox.setTabIndex(9);
        ignorePiratesCheckBox.setText("Pirates");
        ignorePiratesCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        ignorePoliceCheckBox.setLocation(86, 136);
        ignorePoliceCheckBox.setSize(80, 16);
        ignorePoliceCheckBox.setTabIndex(10);
        ignorePoliceCheckBox.setText("Police");
        ignorePoliceCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        ignoreTradersCheckBox.setLocation(167, 136);
        ignoreTradersCheckBox.setSize(80, 16);
        ignoreTradersCheckBox.setTabIndex(11);
        ignoreTradersCheckBox.setText("Traders");
        ignoreTradersCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        ignoreDealingTradersCheckBox.setLocation(185, 152);
        ignoreDealingTradersCheckBox.setSize(310, 16);
        ignoreDealingTradersCheckBox.setTabIndex(12);
        ignoreDealingTradersCheckBox.setText("Ignore dealing traders");
        ignoreDealingTradersCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        continuousAttackCheckBox.setLocation(8, 176);
        continuousAttackCheckBox.setSize(300, 16);
        continuousAttackCheckBox.setTabIndex(13);
        continuousAttackCheckBox.setText("Continuous attack and flight");
        continuousAttackCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        attackFleeingCheckBox.setLocation(24, 192);
        attackFleeingCheckBox.setSize(300, 16);
        attackFleeingCheckBox.setTabIndex(14);
        attackFleeingCheckBox.setText("Continue attacking fleeing ship");
        attackFleeingCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        disableOpponentsCheckBox.setLocation(8, 208);
        disableOpponentsCheckBox.setSize(350, 16);
        disableOpponentsCheckBox.setTabIndex(54);
        disableOpponentsCheckBox.setText("Attempt to disable opponents when possible");
        disableOpponentsCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        okButton.setDialogResult(DialogResult.OK);
        okButton.setLocation(14, 240);
        okButton.setSize(40, 22);
        okButton.setTabIndex(15);
        okButton.setText("Ok");

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setAutoWidth(true);
        cancelButton.setControlBinding(ControlBinding.LEFT);
        cancelButton.setLocation(62, 240);
        cancelButton.setSize(49, 22);
        cancelButton.setTabIndex(16);
        cancelButton.setText("Cancel");

        saveButton.setAutoWidth(true);
        saveButton.setControlBinding(ControlBinding.RIGHT);
        saveButton.setLocation(265, 240);
        saveButton.setSize(107, 22);
        saveButton.setTabIndex(17);
        saveButton.setText("Save As Defaults");
        saveButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                saveButtonClick();
            }
        });

        loadButton.setAutoWidth(true);
        loadButton.setControlBinding(ControlBinding.LEFT);
        loadButton.setLocation(377, 240);
        loadButton.setSize(114, 22);
        loadButton.setTabIndex(18);
        loadButton.setText("Load from Defaults");
        loadButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                loadButtonClick();
            }
        });

        controls.addAll(fuelCheckBox, repairCheckBox, newspaperCheckBox, showNewspaperCheckBox, rangeCheckBox,
                stopTrackingCheckBox, reserveMoneyCheckBox, loanCheckBox, emptyNumericUpDown, emptyLabelValue, ignoreLabel,
                ignorePiratesCheckBox, ignorePoliceCheckBox, ignoreTradersCheckBox, ignoreDealingTradersCheckBox,
                continuousAttackCheckBox, attackFleeingCheckBox, disableOpponentsCheckBox, okButton, cancelButton,
                saveButton, loadButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void updateAll() {
        initializing = true;

        fuelCheckBox.setChecked(getOptions().isAutoFuel());
        repairCheckBox.setChecked(getOptions().isAutoRepair());
        newspaperCheckBox.setChecked(getOptions().isNewsAutoPay());
        showNewspaperCheckBox.setChecked(getOptions().isNewsAutoShow());
        loanCheckBox.setChecked(getOptions().isRemindLoans());
        rangeCheckBox.setChecked(getOptions().isShowTrackedRange());
        stopTrackingCheckBox.setChecked(getOptions().isTrackAutoOff());
        reserveMoneyCheckBox.setChecked(getOptions().isReserveMoney());
        emptyNumericUpDown.setValue(getOptions().getLeaveEmpty());
        ignorePiratesCheckBox.setChecked(getOptions().isAlwaysIgnorePirates());
        ignorePoliceCheckBox.setChecked(getOptions().isAlwaysIgnorePolice());
        ignoreTradersCheckBox.setChecked(getOptions().isAlwaysIgnoreTraders());
        ignoreDealingTradersCheckBox.setChecked(getOptions().isAlwaysIgnoreTradeInOrbit());
        continuousAttackCheckBox.setChecked(getOptions().isContinuousAttack());
        attackFleeingCheckBox.setChecked(getOptions().isContinuousAttackFleeing());
        disableOpponentsCheckBox.setChecked(getOptions().isDisableOpponents());

        updateContinueAttackFleeing();
        updateIgnoreTradersDealing();
        updateNewsAutoShow();

        initializing = false;
    }

    private void updateContinueAttackFleeing() {
        if (!continuousAttackCheckBox.isChecked()) {
            attackFleeingCheckBox.setChecked(false);
        }
        attackFleeingCheckBox.setEnabled(continuousAttackCheckBox.isChecked());
    }

    private void updateIgnoreTradersDealing() {
        if (!ignoreTradersCheckBox.isChecked()) {
            ignoreDealingTradersCheckBox.setChecked(false);
        }
        ignoreDealingTradersCheckBox.setEnabled(ignoreTradersCheckBox.isChecked());
    }

    private void updateNewsAutoShow() {
        if (!newspaperCheckBox.isChecked()) {
            showNewspaperCheckBox.setChecked(false);
        }
        showNewspaperCheckBox.setEnabled(newspaperCheckBox.isChecked());
    }

    private void loadButtonClick() {
        getOptions().loadFromDefaults(true);
        updateAll();
    }

    private void saveButtonClick() {
        getOptions().saveAsDefaults();
    }

    private void controlChanged() {
        if (!initializing) {
            initializing = true;
            updateContinueAttackFleeing();
            updateIgnoreTradersDealing();
            updateNewsAutoShow();
            initializing = false;

            getOptions().setAutoFuel(fuelCheckBox.isChecked());
            getOptions().setAutoRepair(repairCheckBox.isChecked());
            getOptions().setNewsAutoPay(newspaperCheckBox.isChecked());
            getOptions().setNewsAutoShow(showNewspaperCheckBox.isChecked());
            getOptions().setRemindLoans(loanCheckBox.isChecked());
            getOptions().setShowTrackedRange(rangeCheckBox.isChecked());
            getOptions().setTrackAutoOff(stopTrackingCheckBox.isChecked());
            getOptions().setReserveMoney(reserveMoneyCheckBox.isChecked());
            getOptions().setLeaveEmpty(emptyNumericUpDown.getValue());
            getOptions().setAlwaysIgnorePirates(ignorePiratesCheckBox.isChecked());
            getOptions().setAlwaysIgnorePolice(ignorePoliceCheckBox.isChecked());
            getOptions().setAlwaysIgnoreTraders(ignoreTradersCheckBox.isChecked());
            getOptions().setAlwaysIgnoreTradeInOrbit(ignoreDealingTradersCheckBox.isChecked());
            getOptions().setContinuousAttack(continuousAttackCheckBox.isChecked());
            getOptions().setContinuousAttackFleeing(attackFleeingCheckBox.isChecked());
            getOptions().setDisableOpponents(disableOpponentsCheckBox.isChecked());

            emptyLabelValue.setText(getEmptyLabelValue());
        }
    }

    private String getEmptyLabelValue() {
        return Functions.pluralWoNumber(emptyNumericUpDown.getValue(), Strings.CargoBay) + " "
                + Strings.OptionsKeepEmptyCargoBays;
    }

    GameOptions getOptions() {
        return options;
    }
}

/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron
 * Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.game.Game;
import spacetrader.game.GameOptions;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;
import spacetrader.util.ReflectionUtils;

public class FormOptions extends SpaceTraderForm {

    private Button okButton = new Button();
    private Button cancelButton = new Button();
    private Label emptyLabel = new Label();
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
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(362, 271));
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(cancelButton);
        
        emptyNumericUpDown.beginInit();
        suspendLayout();

        fuelCheckBox.setLocation(new Point(8, 8));
        fuelCheckBox.setSize(new Size(160, 16));
        fuelCheckBox.setTabIndex(1);
        fuelCheckBox.setText("Get full fuel tanks on arrival");
        fuelCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        repairCheckBox.setLocation(new Point(8, 24));
        repairCheckBox.setSize(new Size(167, 16));
        repairCheckBox.setTabIndex(2);
        repairCheckBox.setText("Get full hull repairs on arrival");
        repairCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        newspaperCheckBox.setLocation(new Point(8, 40));
        newspaperCheckBox.setSize(new Size(155, 16));
        newspaperCheckBox.setTabIndex(3);
        newspaperCheckBox.setText("Always pay for newspaper");
        newspaperCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        showNewspaperCheckBox.setLocation(new Point(24, 56));
        showNewspaperCheckBox.setSize(new Size(160, 16));
        showNewspaperCheckBox.setTabIndex(53);
        showNewspaperCheckBox.setText("Show newspaper on arrival");
        showNewspaperCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        rangeCheckBox.setLocation(new Point(184, 8));
        rangeCheckBox.setSize(new Size(175, 16));
        rangeCheckBox.setTabIndex(5);
        rangeCheckBox.setText("Show range to tracked system");
        rangeCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        stopTrackingCheckBox.setLocation(new Point(184, 24));
        stopTrackingCheckBox.setSize(new Size(139, 16));
        stopTrackingCheckBox.setTabIndex(6);
        stopTrackingCheckBox.setText("Stop tracking on arrival");
        stopTrackingCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        reserveMoneyCheckBox.setLocation(new Point(184, 40));
        reserveMoneyCheckBox.setSize(new Size(176, 16));
        reserveMoneyCheckBox.setTabIndex(7);
        reserveMoneyCheckBox.setText("Reserve money for warp costs");
        reserveMoneyCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        loanCheckBox.setLocation(new Point(184, 56));
        loanCheckBox.setSize(new Size(124, 16));
        loanCheckBox.setTabIndex(4);
        loanCheckBox.setText("Remind about loans");
        loanCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        emptyNumericUpDown.setLocation(new Point(8, 88));
        emptyNumericUpDown.setMaximum(99);
        emptyNumericUpDown.setSize(new Size(40, 20));
        emptyNumericUpDown.setTabIndex(8);
        emptyNumericUpDown.setValue(88);
        emptyNumericUpDown.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        emptyLabel.setAutoSize(true);
        emptyLabel.setLocation(new Point(52, 90));
        emptyLabel.setSize(new Size(292, 16));
        emptyLabel.setTabIndex(38);
        emptyLabel.setText("Cargo Bays to leave empty when buying goods in-system");

        ignoreLabel.setAutoSize(true);
        ignoreLabel.setLocation(new Point(8, 120));
        ignoreLabel.setSize(new Size(152, 16));
        ignoreLabel.setTabIndex(52);
        ignoreLabel.setText("Always ignore when it is safe:");

        ignorePiratesCheckBox.setLocation(new Point(8, 136));
        ignorePiratesCheckBox.setSize(new Size(63, 16));
        ignorePiratesCheckBox.setTabIndex(9);
        ignorePiratesCheckBox.setText("Pirates");
        ignorePiratesCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        ignorePoliceCheckBox.setLocation(new Point(79, 136));
        ignorePoliceCheckBox.setSize(new Size(59, 16));
        ignorePoliceCheckBox.setTabIndex(10);
        ignorePoliceCheckBox.setText("Police");
        ignorePoliceCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        ignoreTradersCheckBox.setLocation(new Point(146, 136));
        ignoreTradersCheckBox.setSize(new Size(67, 16));
        ignoreTradersCheckBox.setTabIndex(11);
        ignoreTradersCheckBox.setText("Traders");
        ignoreTradersCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        ignoreDealingTradersCheckBox.setLocation(new Point(162, 152));
        ignoreDealingTradersCheckBox.setSize(new Size(133, 16));
        ignoreDealingTradersCheckBox.setTabIndex(12);
        ignoreDealingTradersCheckBox.setText("Ignore dealing traders");
        ignoreDealingTradersCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        continuousAttackCheckBox.setLocation(new Point(8, 176));
        continuousAttackCheckBox.setSize(new Size(163, 16));
        continuousAttackCheckBox.setTabIndex(13);
        continuousAttackCheckBox.setText("Continuous attack and flight");
        continuousAttackCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        attackFleeingCheckBox.setLocation(new Point(24, 192));
        attackFleeingCheckBox.setSize(new Size(177, 16));
        attackFleeingCheckBox.setTabIndex(14);
        attackFleeingCheckBox.setText("Continue attacking fleeing ship");
        attackFleeingCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        disableOpponentsCheckBox.setLocation(new Point(8, 208));
        disableOpponentsCheckBox.setSize(new Size(244, 16));
        disableOpponentsCheckBox.setTabIndex(54);
        disableOpponentsCheckBox.setText("Attempt to disable opponents when possible");
        disableOpponentsCheckBox.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                controlChanged();
            }
        });

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(14, 240));
        okButton.setSize(new Size(40, 22));
        okButton.setTabIndex(15);
        okButton.setText("Ok");

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setFlatStyle(FlatStyle.FLAT);
        cancelButton.setLocation(new Point(62, 240));
        cancelButton.setSize(new Size(49, 22));
        cancelButton.setTabIndex(16);
        cancelButton.setText("Cancel");

        saveButton.setFlatStyle(FlatStyle.FLAT);
        saveButton.setLocation(new Point(119, 240));
        saveButton.setSize(new Size(107, 22));
        saveButton.setTabIndex(17);
        saveButton.setText("Save As Defaults");
        saveButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                saveButtonClick();
            }
        });

        loadButton.setFlatStyle(FlatStyle.FLAT);
        loadButton.setLocation(new Point(234, 240));
        loadButton.setSize(new Size(114, 22));
        loadButton.setTabIndex(18);
        loadButton.setText("Load from Defaults");
        loadButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                loadButtonClick();
            }
        });

        controls.addAll(fuelCheckBox, repairCheckBox, newspaperCheckBox, showNewspaperCheckBox, rangeCheckBox,
                stopTrackingCheckBox, reserveMoneyCheckBox, loanCheckBox, emptyNumericUpDown, emptyLabel, ignoreLabel,
                ignorePiratesCheckBox, ignorePoliceCheckBox, ignoreTradersCheckBox, ignoreDealingTradersCheckBox,
                continuousAttackCheckBox, attackFleeingCheckBox, disableOpponentsCheckBox, okButton, cancelButton,
                saveButton, loadButton);
        
        emptyNumericUpDown.endInit();
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
        }
    }

    GameOptions getOptions() {
        return options;
    }
}

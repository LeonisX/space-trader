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

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.game.Strings;
import spacetrader.game.enums.Difficulty;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

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

    FormNewCommander() {
        initializeComponent();
        difficultyComboBox.setSelectedIndex(2);
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formNewCommander");
        setText("New Commander");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(202, 231));
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(closeButton);
        
        numPilot.beginInit();
        numFighter.beginInit();
        numTrader.beginInit();
        numEngineer.beginInit();
        suspendLayout();

        nameLabel.setAutoSize(true);
        nameLabel.setLocation(new Point(8, 8));
        nameLabel.setSize(new Size(38, 13));
        nameLabel.setTabIndex(0);
        nameLabel.setText("Name:");

        nameTextBox.setLocation(new Point(72, 5));
        nameTextBox.setSize(new Size(120, 20));
        nameTextBox.setTabIndex(1);
        nameTextBox.setTextChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                updateOkEnabled();
            }
        });

        difficultyLabel.setAutoSize(true);
        difficultyLabel.setLocation(new Point(8, 40));
        difficultyLabel.setSize(new Size(50, 13));
        difficultyLabel.setTabIndex(34);
        difficultyLabel.setText("Difficulty:");

        difficultyComboBox.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        difficultyComboBox.getItems().addRange(Strings.DifficultyLevels);
        difficultyComboBox.setLocation(new Point(72, 37));
        difficultyComboBox.setSize(new Size(120, 21));
        difficultyComboBox.setTabIndex(2);

        skillPointsLabel.setAutoSize(true);
        skillPointsLabel.setLocation(new Point(8, 72));
        skillPointsLabel.setSize(new Size(63, 13));
        skillPointsLabel.setTabIndex(35);
        skillPointsLabel.setText("Skill Points:");

        skillPointsLabelValue.setLocation(new Point(73, 72));
        skillPointsLabelValue.setSize(new Size(17, 13));
        skillPointsLabelValue.setTabIndex(41);
        //skillPointsLabelValue.setText("16");
        skillPointsLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        skillPointsRemainingLabel.setAutoSize(true);
        skillPointsRemainingLabel.setLocation(new Point(91, 72));
        skillPointsRemainingLabel.setSize(new Size(90, 13));
        skillPointsRemainingLabel.setTabIndex(40);
        skillPointsRemainingLabel.setText("points remaining.");

        pilotLabel.setAutoSize(true);
        pilotLabel.setLocation(new Point(16, 96));
        pilotLabel.setSize(new Size(29, 13));
        pilotLabel.setTabIndex(36);
        pilotLabel.setText("Pilot:");

        numPilot.setLocation(new Point(72, 94));
        numPilot.setMaximum(10);
        numPilot.setMinimum(1);
        numPilot.setSize(new Size(46, 20));
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
        fighterLabel.setLocation(new Point(16, 120));
        fighterLabel.setSize(new Size(43, 13));
        fighterLabel.setTabIndex(37);
        fighterLabel.setText("Fighter:");

        numFighter.setLocation(new Point(72, 118));
        numFighter.setMaximum(10);
        numFighter.setMinimum(1);
        numFighter.setSize(new Size(46, 20));
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
        traderLabel.setLocation(new Point(16, 144));
        traderLabel.setSize(new Size(41, 13));
        traderLabel.setTabIndex(38);
        traderLabel.setText("Trader:");

        numTrader.setLocation(new Point(72, 142));
        numTrader.setMaximum(10);
        numTrader.setMinimum(1);
        numTrader.setSize(new Size(46, 20));
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
        engineerLabel.setLocation(new Point(16, 168));
        engineerLabel.setSize(new Size(53, 13));
        engineerLabel.setTabIndex(39);
        engineerLabel.setText("Engineer:");

        numEngineer.setLocation(new Point(72, 166));
        numEngineer.setMaximum(10);
        numEngineer.setMinimum(1);
        numEngineer.setSize(new Size(46, 20));
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
        okButton.setEnabled(false);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(83, 200));
        okButton.setSize(new Size(36, 22));
        okButton.setTabIndex(7);
        okButton.setText("Ok");
        okButton.setEnabled(false);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(new Point(-32, -32));
        closeButton.setSize(new Size(30, 31));
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(nameLabel, nameTextBox, difficultyLabel, difficultyComboBox, skillPointsLabel,
                skillPointsLabelValue, skillPointsRemainingLabel, pilotLabel, numPilot, fighterLabel, numFighter,
                traderLabel, numTrader, engineerLabel, numEngineer, okButton, closeButton);
        
        numPilot.endInit();
        numFighter.endInit();
        numTrader.endInit();
        numEngineer.endInit();
    }

    private void updateOkEnabled() {
        okButton.setEnabled(skillPointsLabelValue.getText().equals("0") && nameTextBox.getText().length() > 0);
    }

    private void numValueChanged() {
        int points = 20 - (getPilot() + getFighter() + getTrader() + getEngineer());
        skillPointsLabelValue.setText(points);
        numPilot.setMaximum(Math.min(10, getPilot() + points));
        numFighter.setMaximum(Math.min(10, getFighter() + points));
        numTrader.setMaximum(Math.min(10, getTrader() + points));
        numEngineer.setMaximum(Math.min(10, getEngineer() + points));

        updateOkEnabled();
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

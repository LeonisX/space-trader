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
import spacetrader.controls.enums.ContentAlignment;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;

import spacetrader.game.enums.Difficulty;
import spacetrader.gui.debug.Launcher;
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

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Launcher.runForm(new FormViewCommander());
    }
    
    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewCommander");
        setText("Commander Status");
        setAutoScaleBaseSize(5, 13);
        setClientSize(232, 304);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        nameLabel.setAutoSize(true);
        nameLabel.setFont(FontCollection.bold825);
        nameLabel.setLocation(8, 8);
        nameLabel.setSize(39, 16);
        nameLabel.setTabIndex(2);
        nameLabel.setText("Name:");

        nameLabelValue.setLocation(69, 8);
        nameLabelValue.setSize(155, 13);
        nameLabelValue.setTabIndex(4);
        //nameLabelValue.setText("Yuri Gagarin");

        difficultyLabel.setAutoSize(true);
        difficultyLabel.setFont(FontCollection.bold825);
        difficultyLabel.setLocation(8, 24);
        difficultyLabel.setSize(53, 16);
        difficultyLabel.setTabIndex(43);
        difficultyLabel.setText("Difficulty:");

        difficultyLabelValue.setLocation(69, 24);
        difficultyLabelValue.setSize(58, 13);
        difficultyLabelValue.setTabIndex(5);
        //difficultyLabelValue.setText("Impossible");

        timeLabel.setAutoSize(true);
        timeLabel.setFont(FontCollection.bold825);
        timeLabel.setLocation(8, 40);
        timeLabel.setSize(34, 16);
        timeLabel.setTabIndex(37);
        timeLabel.setText("Time:");

        timeLabelValue.setLocation(69, 40);
        timeLabelValue.setSize(66, 13);
        timeLabelValue.setTabIndex(44);
        //timeLabelValue.setText("88,888 days");

        skillsPanel.setLocation(8, 64);
        skillsPanel.setSize(216, 56);
        skillsPanel.setTabStop(false);
        skillsPanel.setText("Skills");

        skillsPanel.getControls().addAll(pilotLabel, pilotLabelValue, traderLabel, traderLabelValue,
                fighterLabel, fighterLabelValue, engineerLabel, engineerLabelValue);

        pilotLabel.setAutoSize(true);
        pilotLabel.setFont(FontCollection.bold825);
        pilotLabel.setLocation(8, 16);
        pilotLabel.setSize(31, 16);
        pilotLabel.setTabIndex(49);
        pilotLabel.setText("Pilot:");

        pilotLabelValue.setLocation(58, 16);
        pilotLabelValue.setSize(40, 13);
        pilotLabelValue.setTabIndex(53);
        //pilotLabelValue.setText("88 (88)");

        traderLabel.setAutoSize(true);
        traderLabel.setFont(FontCollection.bold825);
        traderLabel.setLocation(8, 32);
        traderLabel.setSize(42, 16);
        traderLabel.setTabIndex(51);
        traderLabel.setText("Trader:");

        traderLabelValue.setLocation(58, 32);
        traderLabelValue.setSize(40, 13);
        traderLabelValue.setTabIndex(55);
        //traderLabelValue.setText("88 (88)");

        fighterLabel.setAutoSize(true);
        fighterLabel.setFont(FontCollection.bold825);
        fighterLabel.setLocation(104, 16);
        fighterLabel.setSize(44, 16);
        fighterLabel.setTabIndex(50);
        fighterLabel.setText("Fighter:");

        fighterLabelValue.setLocation(167, 16);
        fighterLabelValue.setSize(40, 13);
        fighterLabelValue.setTabIndex(54);
        //fighterLabelValue.setText("88 (88)");

        engineerLabel.setAutoSize(true);
        engineerLabel.setFont(FontCollection.bold825);
        engineerLabel.setLocation(104, 32);
        engineerLabel.setSize(55, 16);
        engineerLabel.setTabIndex(52);
        engineerLabel.setText("Engineer:");

        engineerLabelValue.setLocation(167, 32);
        engineerLabelValue.setSize(40, 13);
        engineerLabelValue.setTabIndex(56);
        //engineerLabelValue.setText("88 (88)");

        financesPanel.setLocation(8, 128);
        financesPanel.setSize(216, 72);
        financesPanel.setTabStop(false);
        financesPanel.setText("Finances");

        financesPanel.getControls().addAll(cashLabel, cashLabelValue, debtLabel,
                debtLabelValue, netWorthLabel, netWorthLabelValue);

        cashLabel.setAutoSize(true);
        cashLabel.setFont(FontCollection.bold825);
        cashLabel.setLocation(8, 16);
        cashLabel.setSize(35, 16);
        cashLabel.setTabIndex(38);
        cashLabel.setText("Cash:");

        cashLabelValue.setLocation(104, 16);
        cashLabelValue.setSize(70, 13);
        cashLabelValue.setTabIndex(43);
        //cashLabelValue.setText("8,888,888 cr.");
        cashLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        debtLabel.setAutoSize(true);
        debtLabel.setFont(FontCollection.bold825);
        debtLabel.setLocation(8, 32);
        debtLabel.setSize(32, 16);
        debtLabel.setTabIndex(39);
        debtLabel.setText("Debt:");

        debtLabelValue.setLocation(104, 32);
        debtLabelValue.setSize(70, 13);
        debtLabelValue.setTabIndex(42);
        //debtLabelValue.setText("8,888,888 cr.");
        debtLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        netWorthLabel.setAutoSize(true);
        netWorthLabel.setFont(FontCollection.bold825);
        netWorthLabel.setLocation(8, 48);
        netWorthLabel.setSize(60, 16);
        netWorthLabel.setTabIndex(40);
        netWorthLabel.setText("Net Worth:");

        netWorthLabelValue.setLocation(104, 48);
        netWorthLabelValue.setSize(70, 13);
        netWorthLabelValue.setTabIndex(41);
        //netWorthLabelValue.setText("8,888,888 cr.");
        netWorthLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        notorietyPanel.setLocation(8, 208);
        notorietyPanel.setSize(216, 88);
        notorietyPanel.setTabStop(false);
        notorietyPanel.setText("Notoriety");

        notorietyPanel.getControls().addAll(killsLabel, killsLabelValue, reputationLabel,
                reputationLabelValue, policeLabel, policeLabelValue, bountyTitleLabelValue, bountyLabelValue);

        killsLabel.setAutoSize(true);
        killsLabel.setFont(FontCollection.bold825);
        killsLabel.setLocation(8, 16);
        killsLabel.setSize(30, 16);
        killsLabel.setTabIndex(44);
        killsLabel.setText("Kills:");

        killsLabelValue.setLocation(104, 16);
        killsLabelValue.setSize(33, 13);
        killsLabelValue.setTabIndex(43);
        //killsLabelValue.setText("8,888");

        reputationLabel.setAutoSize(true);
        reputationLabel.setFont(FontCollection.bold825);
        reputationLabel.setLocation(8, 32);
        reputationLabel.setSize(65, 16);
        reputationLabel.setTabIndex(45);
        reputationLabel.setText("Reputation:");

        reputationLabelValue.setLocation(104, 32);
        reputationLabelValue.setSize(88, 13);
        reputationLabelValue.setTabIndex(42);
        //reputationLabelValue.setText("Mostly Harmless");

        policeLabel.setAutoSize(true);
        policeLabel.setFont(FontCollection.bold825);
        policeLabel.setLocation(8, 48);
        policeLabel.setSize(81, 16);
        policeLabel.setTabIndex(46);
        policeLabel.setText("Police Record:");

        policeLabelValue.setLocation(104, 48);
        policeLabelValue.setSize(63, 13);
        policeLabelValue.setTabIndex(41);
        //policeLabelValue.setText("Psychopath");

        bountyTitleLabelValue.setAutoSize(true);
        bountyTitleLabelValue.setFont(FontCollection.bold825);
        bountyTitleLabelValue.setLocation(8, 64);
        bountyTitleLabelValue.setSize(84, 16);
        bountyTitleLabelValue.setTabIndex(48);
        //bountyTitleLabelValue.setText("Bounty offered:");
        bountyTitleLabelValue.setVisible(false);

        bountyLabelValue.setLocation(104, 64);
        bountyLabelValue.setSize(72, 13);
        bountyLabelValue.setTabIndex(47);
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
        timeLabelValue.setText(Functions.multiples(cmdr.getDays(), Strings.TimeUnit));

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
            bountyTitleLabelValue.setText("Bounty offered:");
            bountyLabelValue.setVisible(true);
            bountyLabelValue.setText(Functions.formatMoney(-1000 * score));
        } else if (score >= Consts.PoliceRecordScoreTrusted) {
            bountyTitleLabelValue.setVisible(true);
            bountyTitleLabelValue.setText("Angry kingpins:");
            bountyLabelValue.setVisible(true);
            bountyLabelValue.setText(Functions.formatNumber(score / 5));
        } else {
            bountyTitleLabelValue.setVisible(false);
            bountyLabelValue.setVisible(false);
        }
    }
}

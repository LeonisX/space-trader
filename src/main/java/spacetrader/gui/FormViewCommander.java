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
//using System;
//using System.Drawing;
//using System.Collections;
//using System.ComponentModel;
//using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.*;

import java.util.Arrays;
import spacetrader.gui.debug.Launcher;

public class FormViewCommander extends SpaceTraderForm {
    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.Label lblNameLabel;
    private spacetrader.controls.Label lblName;
    private spacetrader.controls.Label lblDifficulty;
    private spacetrader.controls.Label lblTimeLabel;
    private spacetrader.controls.Label lblCashLabel;
    private spacetrader.controls.Label lblDebtLabel;
    private spacetrader.controls.Label lblNetWorthLabel;
    private spacetrader.controls.Label lblDifficultyLabel;
    private spacetrader.controls.Label lblTime;
    private Panel boxSkills;
    private spacetrader.controls.Label lblEngineer;
    private spacetrader.controls.Label lblTrader;
    private spacetrader.controls.Label lblFighter;
    private spacetrader.controls.Label lblPilot;
    private spacetrader.controls.Label lblEngineerLabel;
    private spacetrader.controls.Label lblTraderLabel;
    private spacetrader.controls.Label lblFighterLabel;
    private spacetrader.controls.Label lblPilotLabel;
    private Panel boxFinances;
    private spacetrader.controls.Label lblNetWorth;
    private spacetrader.controls.Label lblDebt;
    private spacetrader.controls.Label lblCash;
    private spacetrader.controls.Label lblKills;
    private spacetrader.controls.Label lblReputation;
    private spacetrader.controls.Label lblRecord;
    private spacetrader.controls.Label lblPoliceLabel;
    private spacetrader.controls.Label lblReputationLabel;
    private spacetrader.controls.Label lblKillsLabel;
    private Panel boxNotoriety;
    private spacetrader.controls.Label lblBountyLabel;
    private spacetrader.controls.Label lblBounty;
    private Game game = Game.getCurrentGame();
    // #endregion

    // #region Member Declarations

    public FormViewCommander() {
        initializeComponent();

        InitializeScreen();
    }

    // #endregion

    // #region Methods

    public static void main(String[] args) throws Exception {
        FormViewCommander form = new FormViewCommander();
        Launcher.runForm(form);
    } // #region Control Declarations

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        this.lblNameLabel = new spacetrader.controls.Label();
        this.lblName = new spacetrader.controls.Label();
        this.lblDifficulty = new spacetrader.controls.Label();
        this.btnClose = new spacetrader.controls.Button();
        this.lblTimeLabel = new spacetrader.controls.Label();
        this.lblCashLabel = new spacetrader.controls.Label();
        this.lblDebtLabel = new spacetrader.controls.Label();
        this.lblNetWorthLabel = new spacetrader.controls.Label();
        this.lblDifficultyLabel = new spacetrader.controls.Label();
        this.lblTime = new spacetrader.controls.Label();
        this.boxSkills = new Panel();
        this.lblEngineer = new spacetrader.controls.Label();
        this.lblTrader = new spacetrader.controls.Label();
        this.lblFighter = new spacetrader.controls.Label();
        this.lblPilot = new spacetrader.controls.Label();
        this.lblEngineerLabel = new spacetrader.controls.Label();
        this.lblTraderLabel = new spacetrader.controls.Label();
        this.lblFighterLabel = new spacetrader.controls.Label();
        this.lblPilotLabel = new spacetrader.controls.Label();
        this.boxFinances = new Panel();
        this.lblCash = new spacetrader.controls.Label();
        this.lblDebt = new spacetrader.controls.Label();
        this.lblNetWorth = new spacetrader.controls.Label();
        this.boxNotoriety = new Panel();
        this.lblPoliceLabel = new spacetrader.controls.Label();
        this.lblReputationLabel = new spacetrader.controls.Label();
        this.lblKillsLabel = new spacetrader.controls.Label();
        this.lblKills = new spacetrader.controls.Label();
        this.lblReputation = new spacetrader.controls.Label();
        this.lblRecord = new spacetrader.controls.Label();
        this.lblBountyLabel = new spacetrader.controls.Label();
        this.lblBounty = new spacetrader.controls.Label();
        this.boxSkills.suspendLayout();
        this.boxFinances.suspendLayout();
        this.boxNotoriety.suspendLayout();
        this.suspendLayout();
        //
        // lblNameLabel
        //
        this.lblNameLabel.setAutoSize(true);
        this.lblNameLabel.setFont(FontCollection.bold825);
        this.lblNameLabel.setLocation(new java.awt.Point(8, 8));
        this.lblNameLabel.setSize(new spacetrader.controls.Size(39, 16));
        this.lblNameLabel.setTabIndex(2);
        this.lblNameLabel.setText("Name:");
        //
        // lblName
        //
        this.lblName.setLocation(new java.awt.Point(69, 8));
        this.lblName.setSize(new spacetrader.controls.Size(155, 13));
        this.lblName.setTabIndex(4);
        this.lblName.setText("XXXXXXXXXXXXXXXXXX");
        //
        // lblDifficulty
        //
        this.lblDifficulty.setLocation(new java.awt.Point(69, 24));
        this.lblDifficulty.setSize(new spacetrader.controls.Size(58, 13));
        this.lblDifficulty.setTabIndex(5);
        this.lblDifficulty.setText("Impossible");
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.CANCEL);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setSize(new spacetrader.controls.Size(26, 27));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblTimeLabel
        //
        this.lblTimeLabel.setAutoSize(true);
        this.lblTimeLabel.setFont(FontCollection.bold825);
        this.lblTimeLabel.setLocation(new java.awt.Point(8, 40));
        this.lblTimeLabel.setSize(new spacetrader.controls.Size(34, 16));
        this.lblTimeLabel.setTabIndex(37);
        this.lblTimeLabel.setText("Time:");
        //
        // lblCashLabel
        //
        this.lblCashLabel.setAutoSize(true);
        this.lblCashLabel.setFont(FontCollection.bold825);
        this.lblCashLabel.setLocation(new java.awt.Point(8, 16));
        this.lblCashLabel.setSize(new spacetrader.controls.Size(35, 16));
        this.lblCashLabel.setTabIndex(38);
        this.lblCashLabel.setText("Cash:");
        //
        // lblDebtLabel
        //
        this.lblDebtLabel.setAutoSize(true);
        this.lblDebtLabel.setFont(FontCollection.bold825);
        this.lblDebtLabel.setLocation(new java.awt.Point(8, 32));
        this.lblDebtLabel.setSize(new spacetrader.controls.Size(32, 16));
        this.lblDebtLabel.setTabIndex(39);
        this.lblDebtLabel.setText("Debt:");
        //
        // lblNetWorthLabel
        //
        this.lblNetWorthLabel.setAutoSize(true);
        this.lblNetWorthLabel.setFont(FontCollection.bold825);
        this.lblNetWorthLabel.setLocation(new java.awt.Point(8, 48));
        this.lblNetWorthLabel.setSize(new spacetrader.controls.Size(60, 16));
        this.lblNetWorthLabel.setTabIndex(40);
        this.lblNetWorthLabel.setText("Net Worth:");
        //
        // lblDifficultyLabel
        //
        this.lblDifficultyLabel.setAutoSize(true);
        this.lblDifficultyLabel.setFont(FontCollection.bold825);
        this.lblDifficultyLabel.setLocation(new java.awt.Point(8, 24));
        this.lblDifficultyLabel.setSize(new spacetrader.controls.Size(53, 16));
        this.lblDifficultyLabel.setTabIndex(43);
        this.lblDifficultyLabel.setText("Difficulty:");
        //
        // lblTime
        //
        this.lblTime.setLocation(new java.awt.Point(69, 40));
        this.lblTime.setSize(new spacetrader.controls.Size(66, 13));
        this.lblTime.setTabIndex(44);
        this.lblTime.setText("88,888 days");
        //
        // boxSkills
        //
        this.boxSkills.getControls().addAll((new BaseComponent[]{this.lblEngineer, this.lblTrader, this.lblFighter,
                this.lblPilot, this.lblEngineerLabel, this.lblTraderLabel, this.lblFighterLabel, this.lblPilotLabel}));
        this.boxSkills.setLocation(new java.awt.Point(8, 64));
        this.boxSkills.setSize(new spacetrader.controls.Size(216, 56));
        this.boxSkills.setTabIndex(49);
        this.boxSkills.setTabStop(false);
        this.boxSkills.setText("Skills");
        //
        // lblEngineer
        //
        this.lblEngineer.setLocation(new java.awt.Point(167, 32));
        this.lblEngineer.setSize(new spacetrader.controls.Size(40, 13));
        this.lblEngineer.setTabIndex(56);
        this.lblEngineer.setText("88 (88)");
        //
        // lblTrader
        //
        this.lblTrader.setLocation(new java.awt.Point(58, 32));
        this.lblTrader.setSize(new spacetrader.controls.Size(40, 13));
        this.lblTrader.setTabIndex(55);
        this.lblTrader.setText("88 (88)");
        //
        // lblFighter
        //
        this.lblFighter.setLocation(new java.awt.Point(167, 16));
        this.lblFighter.setSize(new spacetrader.controls.Size(40, 13));
        this.lblFighter.setTabIndex(54);
        this.lblFighter.setText("88 (88)");
        //
        // lblPilot
        //
        this.lblPilot.setLocation(new java.awt.Point(58, 16));
        this.lblPilot.setSize(new spacetrader.controls.Size(40, 13));
        this.lblPilot.setTabIndex(53);
        this.lblPilot.setText("88 (88)");
        //
        // lblEngineerLabel
        //
        this.lblEngineerLabel.setAutoSize(true);
        this.lblEngineerLabel.setFont(FontCollection.bold825);
        this.lblEngineerLabel.setLocation(new java.awt.Point(104, 32));
        this.lblEngineerLabel.setSize(new spacetrader.controls.Size(55, 16));
        this.lblEngineerLabel.setTabIndex(52);
        this.lblEngineerLabel.setText("Engineer:");
        //
        // lblTraderLabel
        //
        this.lblTraderLabel.setAutoSize(true);
        this.lblTraderLabel.setFont(FontCollection.bold825);
        this.lblTraderLabel.setLocation(new java.awt.Point(8, 32));
        this.lblTraderLabel.setSize(new spacetrader.controls.Size(42, 16));
        this.lblTraderLabel.setTabIndex(51);
        this.lblTraderLabel.setText("Trader:");
        //
        // lblFighterLabel
        //
        this.lblFighterLabel.setAutoSize(true);
        this.lblFighterLabel.setFont(FontCollection.bold825);
        this.lblFighterLabel.setLocation(new java.awt.Point(104, 16));
        this.lblFighterLabel.setSize(new spacetrader.controls.Size(44, 16));
        this.lblFighterLabel.setTabIndex(50);
        this.lblFighterLabel.setText("Fighter:");
        //
        // lblPilotLabel
        //
        this.lblPilotLabel.setAutoSize(true);
        this.lblPilotLabel.setFont(FontCollection.bold825);
        this.lblPilotLabel.setLocation(new java.awt.Point(8, 16));
        this.lblPilotLabel.setSize(new spacetrader.controls.Size(31, 16));
        this.lblPilotLabel.setTabIndex(49);
        this.lblPilotLabel.setText("Pilot:");
        //
        // boxFinances
        //
        this.boxFinances.getControls().addAll(this.lblCash, this.lblDebt, this.lblNetWorth, this.lblNetWorthLabel,
                this.lblCashLabel, this.lblDebtLabel);
        this.boxFinances.setLocation(new java.awt.Point(8, 128));
        this.boxFinances.setSize(new spacetrader.controls.Size(216, 72));
        this.boxFinances.setTabIndex(50);
        this.boxFinances.setTabStop(false);
        this.boxFinances.setText("Finances");
        //
        // lblCash
        //
        this.lblCash.setLocation(new java.awt.Point(104, 16));
        this.lblCash.setSize(new spacetrader.controls.Size(70, 13));
        this.lblCash.setTabIndex(43);
        this.lblCash.setText("8,888,888 cr.");
        this.lblCash.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblDebt
        //
        this.lblDebt.setLocation(new java.awt.Point(104, 32));
        this.lblDebt.setSize(new spacetrader.controls.Size(70, 13));
        this.lblDebt.setTabIndex(42);
        this.lblDebt.setText("8,888,888 cr.");
        this.lblDebt.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblNetWorth
        //
        this.lblNetWorth.setLocation(new java.awt.Point(104, 48));
        this.lblNetWorth.setSize(new spacetrader.controls.Size(70, 13));
        this.lblNetWorth.setTabIndex(41);
        this.lblNetWorth.setText("8,888,888 cr.");
        this.lblNetWorth.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // boxNotoriety
        //
        this.boxNotoriety.getControls().addAll((new BaseComponent[]{this.lblBountyLabel, this.lblBounty,
                this.lblPoliceLabel, this.lblReputationLabel, this.lblKillsLabel, this.lblKills, this.lblReputation,
                this.lblRecord}));
        this.boxNotoriety.setLocation(new java.awt.Point(8, 208));
        this.boxNotoriety.setSize(new spacetrader.controls.Size(216, 88));
        this.boxNotoriety.setTabIndex(51);
        this.boxNotoriety.setTabStop(false);
        this.boxNotoriety.setText("Notoriety");
        //
        // lblPoliceLabel
        //
        this.lblPoliceLabel.setAutoSize(true);
        this.lblPoliceLabel.setFont(FontCollection.bold825);
        this.lblPoliceLabel.setLocation(new java.awt.Point(8, 48));
        this.lblPoliceLabel.setSize(new spacetrader.controls.Size(81, 16));
        this.lblPoliceLabel.setTabIndex(46);
        this.lblPoliceLabel.setText("Police Record:");
        //
        // lblReputationLabel
        //
        this.lblReputationLabel.setAutoSize(true);
        this.lblReputationLabel.setFont(FontCollection.bold825);
        this.lblReputationLabel.setLocation(new java.awt.Point(8, 32));
        this.lblReputationLabel.setSize(new spacetrader.controls.Size(65, 16));
        this.lblReputationLabel.setTabIndex(45);
        this.lblReputationLabel.setText("Reputation:");
        //
        // lblKillsLabel
        //
        this.lblKillsLabel.setAutoSize(true);
        this.lblKillsLabel.setFont(FontCollection.bold825);
        this.lblKillsLabel.setLocation(new java.awt.Point(8, 16));
        this.lblKillsLabel.setSize(new spacetrader.controls.Size(30, 16));
        this.lblKillsLabel.setTabIndex(44);
        this.lblKillsLabel.setText("Kills:");
        //
        // lblKills
        //
        this.lblKills.setLocation(new java.awt.Point(104, 16));
        this.lblKills.setSize(new spacetrader.controls.Size(33, 13));
        this.lblKills.setTabIndex(43);
        this.lblKills.setText("8,888");
        //
        // lblReputation
        //
        this.lblReputation.setLocation(new java.awt.Point(104, 32));
        this.lblReputation.setSize(new spacetrader.controls.Size(88, 13));
        this.lblReputation.setTabIndex(42);
        this.lblReputation.setText("Mostly Harmless");
        //
        // lblRecord
        //
        this.lblRecord.setLocation(new java.awt.Point(104, 48));
        this.lblRecord.setSize(new spacetrader.controls.Size(63, 13));
        this.lblRecord.setTabIndex(41);
        this.lblRecord.setText("Psychopath");
        //
        // lblBountyLabel
        //
        this.lblBountyLabel.setAutoSize(true);
        this.lblBountyLabel.setFont(FontCollection.bold825);
        this.lblBountyLabel.setLocation(new java.awt.Point(8, 64));
        this.lblBountyLabel.setSize(new spacetrader.controls.Size(84, 16));
        this.lblBountyLabel.setTabIndex(48);
        this.lblBountyLabel.setText("Bounty offered:");
        this.lblBountyLabel.setVisible(false);
        //
        // lblBounty
        //
        this.lblBounty.setLocation(new java.awt.Point(104, 64));
        this.lblBounty.setSize(new spacetrader.controls.Size(72, 13));
        this.lblBounty.setTabIndex(47);
        this.lblBounty.setText("8,888,888 cr.");
        this.lblBounty.setVisible(false);
        // FormViewCommander
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new spacetrader.controls.Size(232, 304));
        this.controls.addAll(Arrays.asList(this.boxNotoriety, this.boxFinances, this.boxSkills, this.lblTime,
                this.lblDifficultyLabel, this.lblTimeLabel, this.lblNameLabel, this.btnClose, this.lblDifficulty,
                this.lblName));
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FIXED_DIALOG);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewCommander");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Commander Status");
    }

    // #endregion

    private void InitializeScreen() {
        Commander cmdr = game.getCommander();

        lblName.setText(cmdr.getName());
        lblDifficulty.setText(Strings.DifficultyLevels[game.getDifficulty().castToInt()]);
        lblTime.setText(Functions.multiples(cmdr.getDays(), Strings.TimeUnit));

        lblPilot.setText(cmdr.getPilot() + " (" + cmdr.getShip().Pilot() + ")");
        lblFighter.setText(cmdr.getFighter() + " (" + cmdr.getShip().Fighter() + ")");
        lblTrader.setText(cmdr.getTrader() + " (" + cmdr.getShip().getTrader() + ")");
        lblEngineer.setText(cmdr.getEngineer() + " (" + cmdr.getShip().Engineer() + ")");

        lblCash.setText(Functions.formatMoney(cmdr.getCash()));
        lblDebt.setText(Functions.formatMoney(cmdr.getDebt()));
        lblNetWorth.setText(Functions.formatMoney(cmdr.Worth()));

        lblKills.setText(Functions.formatNumber(cmdr.getKillsPirate() + cmdr.getKillsPolice() + cmdr.getKillsTrader()));
        lblRecord.setText(PoliceRecord.GetPoliceRecordFromScore(cmdr.getPoliceRecordScore()).Name());
        lblReputation.setText(Reputation.GetReputationFromScore(cmdr.getReputationScore()).Name());

        int score = cmdr.getPoliceRecordScore();
        if (score <= Consts.PoliceRecordScoreCrook) {
            lblBountyLabel.setVisible(true);
            lblBountyLabel.setText("Bounty offered:");
            lblBounty.setVisible(true);
            lblBounty.setText(Functions.formatMoney(-1000 * score));
        } else if (score >= Consts.PoliceRecordScoreTrusted) {
            lblBountyLabel.setVisible(true);
            lblBountyLabel.setText("Angry kingpins:");
            lblBounty.setVisible(true);
            lblBounty.setText(Functions.formatNumber(score / 5));
        } else {
            lblBountyLabel.setVisible(false);
            lblBounty.setVisible(false);
        }
    }
    // #endregion
}

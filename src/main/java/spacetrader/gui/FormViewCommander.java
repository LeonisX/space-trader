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

import java.util.Arrays;

import jwinforms.*;

import spacetrader.*;

public class FormViewCommander extends SpaceTraderForm
{
	public static void main(String[] args) throws Exception
	{
		FormViewCommander form = new FormViewCommander();
		Launcher.runForm(form);
	} // #region Control Declarations

	private Button btnClose;
	private Label lblNameLabel;
	private Label lblName;
	private Label lblDifficulty;
	private Label lblTimeLabel;
	private Label lblCashLabel;
	private Label lblDebtLabel;
	private Label lblNetWorthLabel;
	private Label lblDifficultyLabel;
	private Label lblTime;
	private GroupBox boxSkills;
	private Label lblEngineer;
	private Label lblTrader;
	private Label lblFighter;
	private Label lblPilot;
	private Label lblEngineerLabel;
	private Label lblTraderLabel;
	private Label lblFighterLabel;
	private Label lblPilotLabel;
	private GroupBox boxFinances;
	private Label lblNetWorth;
	private Label lblDebt;
	private Label lblCash;
	private Label lblKills;
	private Label lblReputation;
	private Label lblRecord;
	private Label lblPoliceLabel;
	private Label lblReputationLabel;
	private Label lblKillsLabel;
	private GroupBox boxNotoriety;
	private Label lblBountyLabel;
	private Label lblBounty;
	// #endregion

	// #region Member Declarations

	private Game game = Game.CurrentGame();

	// #endregion

	// #region Methods

	public FormViewCommander()
	{
		InitializeComponent();

		InitializeScreen();
	}

	// #region Windows Form Designer generated code
	// / <summary>
	// / Required method for Designer support - do not modify
	// / the contents of this method with the code editor.
	// / </summary>
	private void InitializeComponent()
	{
		this.lblNameLabel = new Label();
		this.lblName = new Label();
		this.lblDifficulty = new Label();
		this.btnClose = new Button();
		this.lblTimeLabel = new Label();
		this.lblCashLabel = new Label();
		this.lblDebtLabel = new Label();
		this.lblNetWorthLabel = new Label();
		this.lblDifficultyLabel = new Label();
		this.lblTime = new Label();
		this.boxSkills = new GroupBox();
		this.lblEngineer = new Label();
		this.lblTrader = new Label();
		this.lblFighter = new Label();
		this.lblPilot = new Label();
		this.lblEngineerLabel = new Label();
		this.lblTraderLabel = new Label();
		this.lblFighterLabel = new Label();
		this.lblPilotLabel = new Label();
		this.boxFinances = new GroupBox();
		this.lblCash = new Label();
		this.lblDebt = new Label();
		this.lblNetWorth = new Label();
		this.boxNotoriety = new GroupBox();
		this.lblPoliceLabel = new Label();
		this.lblReputationLabel = new Label();
		this.lblKillsLabel = new Label();
		this.lblKills = new Label();
		this.lblReputation = new Label();
		this.lblRecord = new Label();
		this.lblBountyLabel = new Label();
		this.lblBounty = new Label();
		this.boxSkills.SuspendLayout();
		this.boxFinances.SuspendLayout();
		this.boxNotoriety.SuspendLayout();
		this.SuspendLayout();
		//
		// lblNameLabel
		//
		this.lblNameLabel.setAutoSize(true);
		this.lblNameLabel.setFont(FontCollection.bold825);
		this.lblNameLabel.setLocation(new java.awt.Point(8, 8));
		this.lblNameLabel.setName("lblNameLabel");
		this.lblNameLabel.setSize(new Size(39, 16));
		this.lblNameLabel.setTabIndex(2);
		this.lblNameLabel.setText("Name:");
		//
		// lblName
		//
		this.lblName.setLocation(new java.awt.Point(69, 8));
		this.lblName.setName("lblName");
		this.lblName.setSize(new Size(155, 13));
		this.lblName.setTabIndex(4);
		this.lblName.setText("XXXXXXXXXXXXXXXXXX");
		//
		// lblDifficulty
		//
		this.lblDifficulty.setLocation(new java.awt.Point(69, 24));
		this.lblDifficulty.setName("lblDifficulty");
		this.lblDifficulty.setSize(new Size(58, 13));
		this.lblDifficulty.setTabIndex(5);
		this.lblDifficulty.setText("Impossible");
		//
		// btnClose
		//
		this.btnClose.setDialogResult(DialogResult.Cancel);
		this.btnClose.setLocation(new java.awt.Point(-32, -32));
		this.btnClose.setName("btnClose");
		this.btnClose.setSize(new Size(26, 27));
		this.btnClose.setTabIndex(32);
		this.btnClose.setTabStop(false);
		this.btnClose.setText("X");
		//
		// lblTimeLabel
		//
		this.lblTimeLabel.setAutoSize(true);
		this.lblTimeLabel.setFont(FontCollection.bold825);
		this.lblTimeLabel.setLocation(new java.awt.Point(8, 40));
		this.lblTimeLabel.setName("lblTimeLabel");
		this.lblTimeLabel.setSize(new Size(34, 16));
		this.lblTimeLabel.setTabIndex(37);
		this.lblTimeLabel.setText("Time:");
		//
		// lblCashLabel
		//
		this.lblCashLabel.setAutoSize(true);
		this.lblCashLabel.setFont(FontCollection.bold825);
		this.lblCashLabel.setLocation(new java.awt.Point(8, 16));
		this.lblCashLabel.setName("lblCashLabel");
		this.lblCashLabel.setSize(new Size(35, 16));
		this.lblCashLabel.setTabIndex(38);
		this.lblCashLabel.setText("Cash:");
		//
		// lblDebtLabel
		//
		this.lblDebtLabel.setAutoSize(true);
		this.lblDebtLabel.setFont(FontCollection.bold825);
		this.lblDebtLabel.setLocation(new java.awt.Point(8, 32));
		this.lblDebtLabel.setName("lblDebtLabel");
		this.lblDebtLabel.setSize(new Size(32, 16));
		this.lblDebtLabel.setTabIndex(39);
		this.lblDebtLabel.setText("Debt:");
		//
		// lblNetWorthLabel
		//
		this.lblNetWorthLabel.setAutoSize(true);
		this.lblNetWorthLabel.setFont(FontCollection.bold825);
		this.lblNetWorthLabel.setLocation(new java.awt.Point(8, 48));
		this.lblNetWorthLabel.setName("lblNetWorthLabel");
		this.lblNetWorthLabel.setSize(new Size(60, 16));
		this.lblNetWorthLabel.setTabIndex(40);
		this.lblNetWorthLabel.setText("Net Worth:");
		//
		// lblDifficultyLabel
		//
		this.lblDifficultyLabel.setAutoSize(true);
		this.lblDifficultyLabel.setFont(FontCollection.bold825);
		this.lblDifficultyLabel.setLocation(new java.awt.Point(8, 24));
		this.lblDifficultyLabel.setName("lblDifficultyLabel");
		this.lblDifficultyLabel.setSize(new Size(53, 16));
		this.lblDifficultyLabel.setTabIndex(43);
		this.lblDifficultyLabel.setText("Difficulty:");
		//
		// lblTime
		//
		this.lblTime.setLocation(new java.awt.Point(69, 40));
		this.lblTime.setName("lblTime");
		this.lblTime.setSize(new Size(66, 13));
		this.lblTime.setTabIndex(44);
		this.lblTime.setText("88,888 days");
		//
		// boxSkills
		//
		this.boxSkills.Controls.addAll((new WinformControl[] { this.lblEngineer, this.lblTrader, this.lblFighter,
				this.lblPilot, this.lblEngineerLabel, this.lblTraderLabel, this.lblFighterLabel, this.lblPilotLabel }));
		this.boxSkills.setLocation(new java.awt.Point(8, 64));
		this.boxSkills.setName("boxSkills");
		this.boxSkills.setSize(new Size(216, 56));
		this.boxSkills.setTabIndex(49);
		this.boxSkills.setTabStop(false);
		this.boxSkills.setText("Skills");
		//
		// lblEngineer
		//
		this.lblEngineer.setLocation(new java.awt.Point(167, 32));
		this.lblEngineer.setName("lblEngineer");
		this.lblEngineer.setSize(new Size(40, 13));
		this.lblEngineer.setTabIndex(56);
		this.lblEngineer.setText("88 (88)");
		//
		// lblTrader
		//
		this.lblTrader.setLocation(new java.awt.Point(58, 32));
		this.lblTrader.setName("lblTrader");
		this.lblTrader.setSize(new Size(40, 13));
		this.lblTrader.setTabIndex(55);
		this.lblTrader.setText("88 (88)");
		//
		// lblFighter
		//
		this.lblFighter.setLocation(new java.awt.Point(167, 16));
		this.lblFighter.setName("lblFighter");
		this.lblFighter.setSize(new Size(40, 13));
		this.lblFighter.setTabIndex(54);
		this.lblFighter.setText("88 (88)");
		//
		// lblPilot
		//
		this.lblPilot.setLocation(new java.awt.Point(58, 16));
		this.lblPilot.setName("lblPilot");
		this.lblPilot.setSize(new Size(40, 13));
		this.lblPilot.setTabIndex(53);
		this.lblPilot.setText("88 (88)");
		//
		// lblEngineerLabel
		//
		this.lblEngineerLabel.setAutoSize(true);
		this.lblEngineerLabel.setFont(FontCollection.bold825);
		this.lblEngineerLabel.setLocation(new java.awt.Point(104, 32));
		this.lblEngineerLabel.setName("lblEngineerLabel");
		this.lblEngineerLabel.setSize(new Size(55, 16));
		this.lblEngineerLabel.setTabIndex(52);
		this.lblEngineerLabel.setText("Engineer:");
		//
		// lblTraderLabel
		//
		this.lblTraderLabel.setAutoSize(true);
		this.lblTraderLabel.setFont(FontCollection.bold825);
		this.lblTraderLabel.setLocation(new java.awt.Point(8, 32));
		this.lblTraderLabel.setName("lblTraderLabel");
		this.lblTraderLabel.setSize(new Size(42, 16));
		this.lblTraderLabel.setTabIndex(51);
		this.lblTraderLabel.setText("Trader:");
		//
		// lblFighterLabel
		//
		this.lblFighterLabel.setAutoSize(true);
		this.lblFighterLabel.setFont(FontCollection.bold825);
		this.lblFighterLabel.setLocation(new java.awt.Point(104, 16));
		this.lblFighterLabel.setName("lblFighterLabel");
		this.lblFighterLabel.setSize(new Size(44, 16));
		this.lblFighterLabel.setTabIndex(50);
		this.lblFighterLabel.setText("Fighter:");
		//
		// lblPilotLabel
		//
		this.lblPilotLabel.setAutoSize(true);
		this.lblPilotLabel.setFont(FontCollection.bold825);
		this.lblPilotLabel.setLocation(new java.awt.Point(8, 16));
		this.lblPilotLabel.setName("lblPilotLabel");
		this.lblPilotLabel.setSize(new Size(31, 16));
		this.lblPilotLabel.setTabIndex(49);
		this.lblPilotLabel.setText("Pilot:");
		//
		// boxFinances
		//
		this.boxFinances.Controls.addAll(this.lblCash, this.lblDebt, this.lblNetWorth, this.lblNetWorthLabel,
				this.lblCashLabel, this.lblDebtLabel);
		this.boxFinances.setLocation(new java.awt.Point(8, 128));
		this.boxFinances.setName("boxFinances");
		this.boxFinances.setSize(new Size(216, 72));
		this.boxFinances.setTabIndex(50);
		this.boxFinances.setTabStop(false);
		this.boxFinances.setText("Finances");
		//
		// lblCash
		//
		this.lblCash.setLocation(new java.awt.Point(104, 16));
		this.lblCash.setName("lblCash");
		this.lblCash.setSize(new Size(70, 13));
		this.lblCash.setTabIndex(43);
		this.lblCash.setText("8,888,888 cr.");
		this.lblCash.TextAlign = ContentAlignment.TopRight;
		//
		// lblDebt
		//
		this.lblDebt.setLocation(new java.awt.Point(104, 32));
		this.lblDebt.setName("lblDebt");
		this.lblDebt.setSize(new Size(70, 13));
		this.lblDebt.setTabIndex(42);
		this.lblDebt.setText("8,888,888 cr.");
		this.lblDebt.TextAlign = ContentAlignment.TopRight;
		//
		// lblNetWorth
		//
		this.lblNetWorth.setLocation(new java.awt.Point(104, 48));
		this.lblNetWorth.setName("lblNetWorth");
		this.lblNetWorth.setSize(new Size(70, 13));
		this.lblNetWorth.setTabIndex(41);
		this.lblNetWorth.setText("8,888,888 cr.");
		this.lblNetWorth.TextAlign = ContentAlignment.TopRight;
		//
		// boxNotoriety
		//
		this.boxNotoriety.Controls.addAll((new WinformControl[] { this.lblBountyLabel, this.lblBounty,
				this.lblPoliceLabel, this.lblReputationLabel, this.lblKillsLabel, this.lblKills, this.lblReputation,
				this.lblRecord }));
		this.boxNotoriety.setLocation(new java.awt.Point(8, 208));
		this.boxNotoriety.setName("boxNotoriety");
		this.boxNotoriety.setSize(new Size(216, 88));
		this.boxNotoriety.setTabIndex(51);
		this.boxNotoriety.setTabStop(false);
		this.boxNotoriety.setText("Notoriety");
		//
		// lblPoliceLabel
		//
		this.lblPoliceLabel.setAutoSize(true);
		this.lblPoliceLabel.setFont(FontCollection.bold825);
		this.lblPoliceLabel.setLocation(new java.awt.Point(8, 48));
		this.lblPoliceLabel.setName("lblPoliceLabel");
		this.lblPoliceLabel.setSize(new Size(81, 16));
		this.lblPoliceLabel.setTabIndex(46);
		this.lblPoliceLabel.setText("Police Record:");
		//
		// lblReputationLabel
		//
		this.lblReputationLabel.setAutoSize(true);
		this.lblReputationLabel.setFont(FontCollection.bold825);
		this.lblReputationLabel.setLocation(new java.awt.Point(8, 32));
		this.lblReputationLabel.setName("lblReputationLabel");
		this.lblReputationLabel.setSize(new Size(65, 16));
		this.lblReputationLabel.setTabIndex(45);
		this.lblReputationLabel.setText("Reputation:");
		//
		// lblKillsLabel
		//
		this.lblKillsLabel.setAutoSize(true);
		this.lblKillsLabel.setFont(FontCollection.bold825);
		this.lblKillsLabel.setLocation(new java.awt.Point(8, 16));
		this.lblKillsLabel.setName("lblKillsLabel");
		this.lblKillsLabel.setSize(new Size(30, 16));
		this.lblKillsLabel.setTabIndex(44);
		this.lblKillsLabel.setText("Kills:");
		//
		// lblKills
		//
		this.lblKills.setLocation(new java.awt.Point(104, 16));
		this.lblKills.setName("lblKills");
		this.lblKills.setSize(new Size(33, 13));
		this.lblKills.setTabIndex(43);
		this.lblKills.setText("8,888");
		//
		// lblReputation
		//
		this.lblReputation.setLocation(new java.awt.Point(104, 32));
		this.lblReputation.setName("lblReputation");
		this.lblReputation.setSize(new Size(88, 13));
		this.lblReputation.setTabIndex(42);
		this.lblReputation.setText("Mostly Harmless");
		//
		// lblRecord
		//
		this.lblRecord.setLocation(new java.awt.Point(104, 48));
		this.lblRecord.setName("lblRecord");
		this.lblRecord.setSize(new Size(63, 13));
		this.lblRecord.setTabIndex(41);
		this.lblRecord.setText("Psychopath");
		//
		// lblBountyLabel
		//
		this.lblBountyLabel.setAutoSize(true);
		this.lblBountyLabel.setFont(FontCollection.bold825);
		this.lblBountyLabel.setLocation(new java.awt.Point(8, 64));
		this.lblBountyLabel.setName("lblBountyLabel");
		this.lblBountyLabel.setSize(new Size(84, 16));
		this.lblBountyLabel.setTabIndex(48);
		this.lblBountyLabel.setText("Bounty offered:");
		this.lblBountyLabel.setVisible(false);
		//
		// lblBounty
		//
		this.lblBounty.setLocation(new java.awt.Point(104, 64));
		this.lblBounty.setName("lblBounty");
		this.lblBounty.setSize(new Size(72, 13));
		this.lblBounty.setTabIndex(47);
		this.lblBounty.setText("8,888,888 cr.");
		this.lblBounty.setVisible(false);
		// FormViewCommander
		//
		this.setAutoScaleBaseSize(new Size(5, 13));
		this.setCancelButton(this.btnClose);
		this.setClientSize(new Size(232, 304));
		this.Controls.addAll(Arrays.asList(this.boxNotoriety, this.boxFinances, this.boxSkills, this.lblTime,
				this.lblDifficultyLabel, this.lblTimeLabel, this.lblNameLabel, this.btnClose, this.lblDifficulty,
				this.lblName));
		this.setFormBorderStyle(FormBorderStyle.FixedDialog);
		this.setMaximizeBox(false);
		this.setMinimizeBox(false);
		this.setName("FormViewCommander");
		this.setShowInTaskbar(false);
		this.setStartPosition(FormStartPosition.CenterParent);
		this.setText("Commander Status");
	}

	// #endregion

	private void InitializeScreen()
	{
		Commander cmdr = game.Commander();
		
		lblName.setText(cmdr.Name());
		lblDifficulty.setText(Strings.DifficultyLevels[game.Difficulty().CastToInt()]);
		lblTime.setText(Functions.Multiples(cmdr.getDays(), Strings.TimeUnit));

		lblPilot.setText(cmdr.Pilot() + " (" + cmdr.getShip().Pilot() + ")");
		lblFighter.setText(cmdr.Fighter() + " (" + cmdr.getShip().Fighter() + ")");
		lblTrader.setText(cmdr.Trader() + " (" + cmdr.getShip().Trader() + ")");
		lblEngineer.setText(cmdr.Engineer() + " (" + cmdr.getShip().Engineer() + ")");

		lblCash.setText(Functions.FormatMoney(cmdr.getCash()));
		lblDebt.setText(Functions.FormatMoney(cmdr.getDebt()));
		lblNetWorth.setText(Functions.FormatMoney(cmdr.Worth()));

		lblKills.setText(Functions.FormatNumber(cmdr.getKillsPirate() + cmdr.getKillsPolice() + cmdr.getKillsTrader()));
		lblRecord.setText(PoliceRecord.GetPoliceRecordFromScore(cmdr.getPoliceRecordScore()).Name());
		lblReputation.setText(Reputation.GetReputationFromScore(cmdr.getReputationScore()).Name());

		int score = cmdr.getPoliceRecordScore();
		if (score <= Consts.PoliceRecordScoreCrook)
		{
			lblBountyLabel.setVisible(true);
			lblBountyLabel.setText("Bounty offered:");
			lblBounty.setVisible(true);
			lblBounty.setText(Functions.FormatMoney(-1000 * score));
		} else if (score >= Consts.PoliceRecordScoreTrusted)
		{
			lblBountyLabel.setVisible(true);
			lblBountyLabel.setText("Angry kingpins:");
			lblBounty.setVisible(true);
			lblBounty.setText(Functions.FormatNumber(score / 5));
		} else
		{
			lblBountyLabel.setVisible(false);
			lblBounty.setVisible(false);
		}
	}
	// #endregion
}

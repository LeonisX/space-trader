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
import spacetrader.game.enums.AlertType;
import spacetrader.game.*;
import spacetrader.guifacade.GuiFacade;

import java.util.Arrays;

public class FormViewBank extends SpaceTraderForm {
    //#region Control Declarations

    private final Container components = null;
    private final Game game = Game.getCurrentGame();
    private final Commander cmdr = Game.getCurrentGame().getCommander();
    private final int MaxLoan = Game.getCurrentGame().getCommander().getPoliceRecordScore() >=
            Consts.PoliceRecordScoreClean ?
            Math.min(25000, Math.max(1000, Game.getCurrentGame().getCommander().Worth() / 5000 * 500)) :
            500;
    private spacetrader.controls.Label lblLoan;
    private spacetrader.controls.Label lblCurrentDebtLabel;
    private spacetrader.controls.Label lblMaxLoanLabel;
    private spacetrader.controls.Label lblCurrentDebt;
    private spacetrader.controls.Label lblMaxLoan;
    private spacetrader.controls.Button btnGetLoan;
    private spacetrader.controls.Button btnBuyInsurance;
    private spacetrader.controls.Label lblNoClaim;
    private spacetrader.controls.Label lblShipValue;
    private spacetrader.controls.Label lblNoClaimLabel;
    private spacetrader.controls.Label lblShipValueLabel;
    private spacetrader.controls.Label lblInsurance;
    private spacetrader.controls.Label lblInsAmt;
    private spacetrader.controls.Label lblInsAmtLabel;

    //#endregion

    //#region Member Declarations
    private spacetrader.controls.Button btnPayBack;
    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.Label lblMaxNoClaim;

    //#endregion

    //#region Methods

    public FormViewBank() {
        initializeComponent();

        UpdateAll();
    }


    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        lblLoan = new spacetrader.controls.Label();
        lblCurrentDebtLabel = new spacetrader.controls.Label();
        lblMaxLoanLabel = new spacetrader.controls.Label();
        lblCurrentDebt = new spacetrader.controls.Label();
        lblMaxLoan = new spacetrader.controls.Label();
        btnGetLoan = new spacetrader.controls.Button();
        btnBuyInsurance = new spacetrader.controls.Button();
        lblNoClaim = new spacetrader.controls.Label();
        lblShipValue = new spacetrader.controls.Label();
        lblNoClaimLabel = new spacetrader.controls.Label();
        lblShipValueLabel = new spacetrader.controls.Label();
        lblInsurance = new spacetrader.controls.Label();
        lblInsAmt = new spacetrader.controls.Label();
        lblInsAmtLabel = new spacetrader.controls.Label();
        btnPayBack = new spacetrader.controls.Button();
        btnClose = new spacetrader.controls.Button();
        lblMaxNoClaim = new spacetrader.controls.Label();
        this.suspendLayout();
        //
        // lblLoan
        //
        lblLoan.setAutoSize(true);
        lblLoan.setFont(FontCollection.bold12);
        lblLoan.setLocation(new java.awt.Point(8, 8));
        lblLoan.setName("lblLoan");
        lblLoan.setSize(new spacetrader.controls.Size(44, 19));
        lblLoan.setTabIndex(1);
        lblLoan.setText("Loan");
        //
        // lblCurrentDebtLabel
        //
        lblCurrentDebtLabel.setAutoSize(true);
        lblCurrentDebtLabel.setFont(FontCollection.bold825);
        lblCurrentDebtLabel.setLocation(new java.awt.Point(16, 32));
        lblCurrentDebtLabel.setName("lblCurrentDebtLabel");
        lblCurrentDebtLabel.setSize(new spacetrader.controls.Size(75, 13));
        lblCurrentDebtLabel.setTabIndex(2);
        lblCurrentDebtLabel.setText("Current Debt:");
        //
        // lblMaxLoanLabel
        //
        lblMaxLoanLabel.setAutoSize(true);
        lblMaxLoanLabel.setFont(FontCollection.bold825);
        lblMaxLoanLabel.setLocation(new java.awt.Point(16, 52));
        lblMaxLoanLabel.setName("lblMaxLoanLabel");
        lblMaxLoanLabel.setSize(new spacetrader.controls.Size(88, 13));
        lblMaxLoanLabel.setTabIndex(3);
        lblMaxLoanLabel.setText("Maximum Loan:");
        //
        // lblCurrentDebt
        //
        lblCurrentDebt.setLocation(new java.awt.Point(136, 32));
        lblCurrentDebt.setName("lblCurrentDebt");
        lblCurrentDebt.setSize(new spacetrader.controls.Size(56, 13));
        lblCurrentDebt.setTabIndex(4);
        lblCurrentDebt.setText("88,888 cr.");
        lblCurrentDebt.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblMaxLoan
        //
        lblMaxLoan.setLocation(new java.awt.Point(136, 52));
        lblMaxLoan.setName("lblMaxLoan");
        lblMaxLoan.setSize(new spacetrader.controls.Size(56, 13));
        lblMaxLoan.setTabIndex(5);
        lblMaxLoan.setText("88,888 cr.");
        lblMaxLoan.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // btnGetLoan
        //
        btnGetLoan.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnGetLoan.setLocation(new java.awt.Point(16, 72));
        btnGetLoan.setName("btnGetLoan");
        btnGetLoan.setSize(new spacetrader.controls.Size(61, 22));
        btnGetLoan.setTabIndex(1);
        btnGetLoan.setText("Get Loan");
        btnGetLoan.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnGetLoan_Click(sender, e);
            }
        });
        //
        // btnBuyInsurance
        //
        btnBuyInsurance.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnBuyInsurance.setLocation(new java.awt.Point(16, 196));
        btnBuyInsurance.setName("btnBuyInsurance");
        btnBuyInsurance.setSize(new spacetrader.controls.Size(90, 22));
        btnBuyInsurance.setTabIndex(3);
        btnBuyInsurance.setText("Stop Insurance");
        btnBuyInsurance.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnBuyInsurance_Click(sender, e);
            }
        });
        //
        // lblNoClaim
        //
        lblNoClaim.setLocation(new java.awt.Point(154, 156));
        lblNoClaim.setName("lblNoClaim");
        lblNoClaim.setSize(new spacetrader.controls.Size(32, 13));
        lblNoClaim.setTabIndex(27);
        lblNoClaim.setText("88%");
        lblNoClaim.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblShipValue
        //
        lblShipValue.setLocation(new java.awt.Point(136, 136));
        lblShipValue.setName("lblShipValue");
        lblShipValue.setSize(new spacetrader.controls.Size(56, 13));
        lblShipValue.setTabIndex(26);
        lblShipValue.setText("88,888 cr.");
        lblShipValue.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblNoClaimLabel
        //
        lblNoClaimLabel.setAutoSize(true);
        lblNoClaimLabel.setFont(FontCollection.bold825);
        lblNoClaimLabel.setLocation(new java.awt.Point(16, 156));
        lblNoClaimLabel.setName("lblNoClaimLabel");
        lblNoClaimLabel.setSize(new spacetrader.controls.Size(106, 13));
        lblNoClaimLabel.setTabIndex(25);
        lblNoClaimLabel.setText("No-Claim Discount:");
        //
        // lblShipValueLabel
        //
        lblShipValueLabel.setAutoSize(true);
        lblShipValueLabel.setFont(FontCollection.bold825);
        lblShipValueLabel.setLocation(new java.awt.Point(16, 136));
        lblShipValueLabel.setName("lblShipValueLabel");
        lblShipValueLabel.setSize(new spacetrader.controls.Size(65, 13));
        lblShipValueLabel.setTabIndex(24);
        lblShipValueLabel.setText("Ship Value:");
        //
        // lblInsurance
        //
        lblInsurance.setAutoSize(true);
        lblInsurance.setFont(FontCollection.bold12);
        lblInsurance.setLocation(new java.awt.Point(8, 112));
        lblInsurance.setName("lblInsurance");
        lblInsurance.setSize(new spacetrader.controls.Size(81, 19));
        lblInsurance.setTabIndex(23);
        lblInsurance.setText("Insurance");
        //
        // lblInsAmt
        //
        lblInsAmt.setLocation(new java.awt.Point(136, 176));
        lblInsAmt.setName("lblInsAmt");
        lblInsAmt.setSize(new spacetrader.controls.Size(82, 13));
        lblInsAmt.setTabIndex(30);
        lblInsAmt.setText("8,888 cr. daily");
        lblInsAmt.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblInsAmtLabel
        //
        lblInsAmtLabel.setAutoSize(true);
        lblInsAmtLabel.setFont(FontCollection.bold825);
        lblInsAmtLabel.setLocation(new java.awt.Point(16, 176));
        lblInsAmtLabel.setName("lblInsAmtLabel");
        lblInsAmtLabel.setSize(new spacetrader.controls.Size(38, 13));
        lblInsAmtLabel.setTabIndex(29);
        lblInsAmtLabel.setText("Costs:");
        //
        // btnPayBack
        //
        btnPayBack.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPayBack.setLocation(new java.awt.Point(88, 72));
        btnPayBack.setName("btnPayBack");
        btnPayBack.setSize(new spacetrader.controls.Size(90, 22));
        btnPayBack.setTabIndex(2);
        btnPayBack.setText("Pay Back Loan");
        btnPayBack.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPayBack_Click(sender, e);
            }
        });
        //
        // btnClose
        //
        btnClose.setDialogResult(DialogResult.CANCEL);
        btnClose.setLocation(new java.awt.Point(-32, -32));
        btnClose.setName("btnClose");
        btnClose.setSize(new spacetrader.controls.Size(26, 27));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");
        //
        // lblMaxNoClaim
        //
        lblMaxNoClaim.setAutoSize(true);
        lblMaxNoClaim.setLocation(new java.awt.Point(182, 156));
        lblMaxNoClaim.setName("lblMaxNoClaim");
        lblMaxNoClaim.setSize(new spacetrader.controls.Size(33, 13));
        lblMaxNoClaim.setTabIndex(33);
        lblMaxNoClaim.setText("(max)");
        lblMaxNoClaim.setVisible(false);
        //
        // FormViewBank
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(btnClose);
        this.setClientSize(new spacetrader.controls.Size(226, 231));
        controls.addAll(Arrays.asList(
                lblMaxNoClaim,
                btnClose,
                btnPayBack,
                lblInsAmt,
                lblInsAmtLabel,
                lblNoClaimLabel,
                lblShipValueLabel,
                lblInsurance,
                lblMaxLoanLabel,
                lblCurrentDebtLabel,
                lblLoan,
                btnBuyInsurance,
                lblNoClaim,
                lblShipValue,
                btnGetLoan,
                lblMaxLoan,
                lblCurrentDebt));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewBank");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Bank");
    }
    //#endregion

    private void UpdateAll() {
        // Loan Info
        lblCurrentDebt.setText(Functions.formatMoney(cmdr.getDebt()));
        lblMaxLoan.setText(Functions.formatMoney(MaxLoan));
        btnPayBack.setVisible((cmdr.getDebt() > 0));

        // Insurance Info
        lblShipValue.setText(Functions.formatMoney(cmdr.getShip().BaseWorth(true)));
        lblNoClaim.setText(Functions.FormatPercent(cmdr.NoClaim()));
        lblMaxNoClaim.setVisible((cmdr.NoClaim() == Consts.MaxNoClaim));
        lblInsAmt.setText(Functions.stringVars(Strings.MoneyRateSuffix,
                Functions.formatMoney(game.InsuranceCosts())));
        btnBuyInsurance.setText(Functions.stringVars(Strings.BankInsuranceButtonText, cmdr.getInsurance() ?
                Strings.BankInsuranceButtonStop : Strings.BankInsuranceButtonBuy));
    }

    //#endregion

    //#region Event Handlers

    private void btnGetLoan_Click(Object sender, EventArgs e) {
        if (cmdr.getDebt() >= MaxLoan)
            GuiFacade.alert(AlertType.DebtTooLargeLoan);
        else {
            FormGetLoan form = new FormGetLoan(MaxLoan - cmdr.getDebt());
            if (form.showDialog(this) == DialogResult.OK) {
                cmdr.setCash(cmdr.getCash() + form.Amount());
                cmdr.setDebt(cmdr.getDebt() + form.Amount());

                UpdateAll();
                game.getParentWindow().updateAll();
            }
        }
    }

    private void btnPayBack_Click(Object sender, EventArgs e) {
        if (cmdr.getDebt() == 0)
            GuiFacade.alert(AlertType.DebtNone);
        else {
            FormPayBackLoan form = new FormPayBackLoan();
            if (form.showDialog(this) == DialogResult.OK) {
                cmdr.setCash(cmdr.getCash() - form.Amount());
                cmdr.setDebt(cmdr.getDebt() - form.Amount());

                UpdateAll();
                game.getParentWindow().updateAll();
            }
        }
    }

    private void btnBuyInsurance_Click(Object sender, EventArgs e) {
        if (cmdr.getInsurance()) {
            if (GuiFacade.alert(AlertType.InsuranceStop) == DialogResult.YES) {
                cmdr.setInsurance(false);
                cmdr.NoClaim(0);
            }
        } else if (!cmdr.getShip().getEscapePod())
            GuiFacade.alert(AlertType.InsuranceNoEscapePod);
        else {
            cmdr.setInsurance(true);
            cmdr.NoClaim(0);
        }

        UpdateAll();
        game.getParentWindow().updateAll();
    }

    //#endregion
}

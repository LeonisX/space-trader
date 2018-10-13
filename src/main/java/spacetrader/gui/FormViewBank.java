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

import jwinforms.*;
import spacetrader.*;
import spacetrader.enums.AlertType;
import spacetrader.guifacade.GuiFacade;

import java.util.Arrays;

public class FormViewBank extends SpaceTraderForm {
    //#region Control Declarations

    private final Container components = null;
    private final Game game = Game.CurrentGame();
    private final Commander cmdr = Game.CurrentGame().Commander();
    private final int MaxLoan = Game.CurrentGame().Commander().getPoliceRecordScore() >=
            Consts.PoliceRecordScoreClean ?
            Math.min(25000, Math.max(1000, Game.CurrentGame().Commander().Worth() / 5000 * 500)) :
            500;
    private jwinforms.Label lblLoan;
    private jwinforms.Label lblCurrentDebtLabel;
    private jwinforms.Label lblMaxLoanLabel;
    private jwinforms.Label lblCurrentDebt;
    private jwinforms.Label lblMaxLoan;
    private jwinforms.Button btnGetLoan;
    private jwinforms.Button btnBuyInsurance;
    private jwinforms.Label lblNoClaim;
    private jwinforms.Label lblShipValue;
    private jwinforms.Label lblNoClaimLabel;
    private jwinforms.Label lblShipValueLabel;
    private jwinforms.Label lblInsurance;
    private jwinforms.Label lblInsAmt;
    private jwinforms.Label lblInsAmtLabel;

    //#endregion

    //#region Member Declarations
    private jwinforms.Button btnPayBack;
    private jwinforms.Button btnClose;
    private jwinforms.Label lblMaxNoClaim;

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
        lblLoan = new jwinforms.Label();
        lblCurrentDebtLabel = new jwinforms.Label();
        lblMaxLoanLabel = new jwinforms.Label();
        lblCurrentDebt = new jwinforms.Label();
        lblMaxLoan = new jwinforms.Label();
        btnGetLoan = new jwinforms.Button();
        btnBuyInsurance = new jwinforms.Button();
        lblNoClaim = new jwinforms.Label();
        lblShipValue = new jwinforms.Label();
        lblNoClaimLabel = new jwinforms.Label();
        lblShipValueLabel = new jwinforms.Label();
        lblInsurance = new jwinforms.Label();
        lblInsAmt = new jwinforms.Label();
        lblInsAmtLabel = new jwinforms.Label();
        btnPayBack = new jwinforms.Button();
        btnClose = new jwinforms.Button();
        lblMaxNoClaim = new jwinforms.Label();
        this.suspendLayout();
        //
        // lblLoan
        //
        lblLoan.setAutoSize(true);
        lblLoan.setFont(FontCollection.bold12);
        lblLoan.setLocation(new java.awt.Point(8, 8));
        lblLoan.setName("lblLoan");
        lblLoan.setSize(new jwinforms.Size(44, 19));
        lblLoan.setTabIndex(1);
        lblLoan.setText("Loan");
        //
        // lblCurrentDebtLabel
        //
        lblCurrentDebtLabel.setAutoSize(true);
        lblCurrentDebtLabel.setFont(FontCollection.bold825);
        lblCurrentDebtLabel.setLocation(new java.awt.Point(16, 32));
        lblCurrentDebtLabel.setName("lblCurrentDebtLabel");
        lblCurrentDebtLabel.setSize(new jwinforms.Size(75, 13));
        lblCurrentDebtLabel.setTabIndex(2);
        lblCurrentDebtLabel.setText("Current Debt:");
        //
        // lblMaxLoanLabel
        //
        lblMaxLoanLabel.setAutoSize(true);
        lblMaxLoanLabel.setFont(FontCollection.bold825);
        lblMaxLoanLabel.setLocation(new java.awt.Point(16, 52));
        lblMaxLoanLabel.setName("lblMaxLoanLabel");
        lblMaxLoanLabel.setSize(new jwinforms.Size(88, 13));
        lblMaxLoanLabel.setTabIndex(3);
        lblMaxLoanLabel.setText("Maximum Loan:");
        //
        // lblCurrentDebt
        //
        lblCurrentDebt.setLocation(new java.awt.Point(136, 32));
        lblCurrentDebt.setName("lblCurrentDebt");
        lblCurrentDebt.setSize(new jwinforms.Size(56, 13));
        lblCurrentDebt.setTabIndex(4);
        lblCurrentDebt.setText("88,888 cr.");
        lblCurrentDebt.TextAlign = ContentAlignment.TopRight;
        //
        // lblMaxLoan
        //
        lblMaxLoan.setLocation(new java.awt.Point(136, 52));
        lblMaxLoan.setName("lblMaxLoan");
        lblMaxLoan.setSize(new jwinforms.Size(56, 13));
        lblMaxLoan.setTabIndex(5);
        lblMaxLoan.setText("88,888 cr.");
        lblMaxLoan.TextAlign = ContentAlignment.TopRight;
        //
        // btnGetLoan
        //
        btnGetLoan.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnGetLoan.setLocation(new java.awt.Point(16, 72));
        btnGetLoan.setName("btnGetLoan");
        btnGetLoan.setSize(new jwinforms.Size(61, 22));
        btnGetLoan.setTabIndex(1);
        btnGetLoan.setText("Get Loan");
        btnGetLoan.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnGetLoan_Click(sender, e);
            }
        });
        //
        // btnBuyInsurance
        //
        btnBuyInsurance.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyInsurance.setLocation(new java.awt.Point(16, 196));
        btnBuyInsurance.setName("btnBuyInsurance");
        btnBuyInsurance.setSize(new jwinforms.Size(90, 22));
        btnBuyInsurance.setTabIndex(3);
        btnBuyInsurance.setText("Stop Insurance");
        btnBuyInsurance.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInsurance_Click(sender, e);
            }
        });
        //
        // lblNoClaim
        //
        lblNoClaim.setLocation(new java.awt.Point(154, 156));
        lblNoClaim.setName("lblNoClaim");
        lblNoClaim.setSize(new jwinforms.Size(32, 13));
        lblNoClaim.setTabIndex(27);
        lblNoClaim.setText("88%");
        lblNoClaim.TextAlign = ContentAlignment.TopRight;
        //
        // lblShipValue
        //
        lblShipValue.setLocation(new java.awt.Point(136, 136));
        lblShipValue.setName("lblShipValue");
        lblShipValue.setSize(new jwinforms.Size(56, 13));
        lblShipValue.setTabIndex(26);
        lblShipValue.setText("88,888 cr.");
        lblShipValue.TextAlign = ContentAlignment.TopRight;
        //
        // lblNoClaimLabel
        //
        lblNoClaimLabel.setAutoSize(true);
        lblNoClaimLabel.setFont(FontCollection.bold825);
        lblNoClaimLabel.setLocation(new java.awt.Point(16, 156));
        lblNoClaimLabel.setName("lblNoClaimLabel");
        lblNoClaimLabel.setSize(new jwinforms.Size(106, 13));
        lblNoClaimLabel.setTabIndex(25);
        lblNoClaimLabel.setText("No-Claim Discount:");
        //
        // lblShipValueLabel
        //
        lblShipValueLabel.setAutoSize(true);
        lblShipValueLabel.setFont(FontCollection.bold825);
        lblShipValueLabel.setLocation(new java.awt.Point(16, 136));
        lblShipValueLabel.setName("lblShipValueLabel");
        lblShipValueLabel.setSize(new jwinforms.Size(65, 13));
        lblShipValueLabel.setTabIndex(24);
        lblShipValueLabel.setText("Ship Value:");
        //
        // lblInsurance
        //
        lblInsurance.setAutoSize(true);
        lblInsurance.setFont(FontCollection.bold12);
        lblInsurance.setLocation(new java.awt.Point(8, 112));
        lblInsurance.setName("lblInsurance");
        lblInsurance.setSize(new jwinforms.Size(81, 19));
        lblInsurance.setTabIndex(23);
        lblInsurance.setText("Insurance");
        //
        // lblInsAmt
        //
        lblInsAmt.setLocation(new java.awt.Point(136, 176));
        lblInsAmt.setName("lblInsAmt");
        lblInsAmt.setSize(new jwinforms.Size(82, 13));
        lblInsAmt.setTabIndex(30);
        lblInsAmt.setText("8,888 cr. daily");
        lblInsAmt.TextAlign = ContentAlignment.TopRight;
        //
        // lblInsAmtLabel
        //
        lblInsAmtLabel.setAutoSize(true);
        lblInsAmtLabel.setFont(FontCollection.bold825);
        lblInsAmtLabel.setLocation(new java.awt.Point(16, 176));
        lblInsAmtLabel.setName("lblInsAmtLabel");
        lblInsAmtLabel.setSize(new jwinforms.Size(38, 13));
        lblInsAmtLabel.setTabIndex(29);
        lblInsAmtLabel.setText("Costs:");
        //
        // btnPayBack
        //
        btnPayBack.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnPayBack.setLocation(new java.awt.Point(88, 72));
        btnPayBack.setName("btnPayBack");
        btnPayBack.setSize(new jwinforms.Size(90, 22));
        btnPayBack.setTabIndex(2);
        btnPayBack.setText("Pay Back Loan");
        btnPayBack.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnPayBack_Click(sender, e);
            }
        });
        //
        // btnClose
        //
        btnClose.setDialogResult(DialogResult.Cancel);
        btnClose.setLocation(new java.awt.Point(-32, -32));
        btnClose.setName("btnClose");
        btnClose.setSize(new jwinforms.Size(26, 27));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");
        //
        // lblMaxNoClaim
        //
        lblMaxNoClaim.setAutoSize(true);
        lblMaxNoClaim.setLocation(new java.awt.Point(182, 156));
        lblMaxNoClaim.setName("lblMaxNoClaim");
        lblMaxNoClaim.setSize(new jwinforms.Size(33, 13));
        lblMaxNoClaim.setTabIndex(33);
        lblMaxNoClaim.setText("(max)");
        lblMaxNoClaim.setVisible(false);
        //
        // FormViewBank
        //
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(btnClose);
        this.setClientSize(new jwinforms.Size(226, 231));
        Controls.addAll(Arrays.asList(
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
        this.setStartPosition(FormStartPosition.CenterParent);
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
        lblInsAmt.setText(Functions.StringVars(Strings.MoneyRateSuffix,
                Functions.formatMoney(game.InsuranceCosts())));
        btnBuyInsurance.setText(Functions.StringVars(Strings.BankInsuranceButtonText, cmdr.getInsurance() ?
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
            if (GuiFacade.alert(AlertType.InsuranceStop) == DialogResult.Yes) {
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

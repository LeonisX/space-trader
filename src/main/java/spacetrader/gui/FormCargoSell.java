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
import spacetrader.enums.CargoSellOp;
import spacetrader.guifacade.Facaded;

@Facaded
public class FormCargoSell extends SpaceTraderForm {
    // #region Control Declarations

    private final Container components = null;
    private final Game game = Game.CurrentGame();
    private jwinforms.Button btnOk;
    private jwinforms.Button btnAll;
    private jwinforms.Button btnNone;
    private jwinforms.Label lblStatement;
    private jwinforms.Label lblQuestion;
    private jwinforms.NumericUpDown numAmount;
    private jwinforms.Label lblPaid;

    // #endregion

    // #region Member Declarations
    private jwinforms.Label lblProfit;

    // #endregion

    // #region Methods

    public FormCargoSell(int item, int maxAmount, CargoSellOp op, int price) {
        initializeComponent();

        Commander cmdr = game.Commander();
        int cost = cmdr.PriceCargo()[item] / cmdr.getShip().Cargo()[item];

        numAmount.setMaximum(maxAmount);
        numAmount.setValue(numAmount.getMinimum());
        this.setText(Functions.StringVars(Strings.CargoTitle,
                Strings.CargoSellOps[op.castToInt()],
                Consts.TradeItems[item].Name()));
        lblQuestion.setText(Functions.StringVars(Strings.CargoSellQuestion,
                Strings.CargoSellOps[op.castToInt()].toLowerCase()));
        lblPaid.setText(Functions.StringVars(
                op == CargoSellOp.SellTrader ? Strings.CargoSellPaidTrader
                        : Strings.CargoSellPaid, Functions.formatMoney(cost),
                Functions.Multiples(maxAmount, Strings.CargoUnit)));
        lblProfit.setText(Functions.StringVars(Strings.CargoSellProfit,
                price >= cost ? "profit" : "loss", Functions
                        .formatMoney(price >= cost ? price - cost : cost
                                - price)));

        // Override defaults for some ops.
        switch (op) {
            case Dump:
                lblStatement.setText(Functions.StringVars(
                        Strings.CargoSellStatementDump,
                        Strings.CargoSellOps[op.castToInt()].toLowerCase(),
                        Functions.formatNumber(maxAmount)));
                lblProfit.setText(Functions.StringVars(Strings.CargoSellDumpCost,
                        Functions.formatMoney(-price)));
                break;
            case Jettison:
                lblStatement.setText(Functions.StringVars(
                        Strings.CargoSellStatementDump,
                        Strings.CargoSellOps[op.castToInt()].toLowerCase(),
                        Functions.formatNumber(maxAmount)));
                break;
            case SellSystem:
                lblStatement.setText(Functions.StringVars(
                        Strings.CargoSellStatement, Functions
                                .formatNumber(maxAmount), Functions
                                .formatMoney(price)));
                break;
            case SellTrader:
                lblStatement.setText(Functions.StringVars(
                        Strings.CargoSellStatementTrader,
                        Consts.TradeItems[item].Name(), Functions.formatMoney(price)));
                break;
        }
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        lblQuestion = new jwinforms.Label();
        lblStatement = new jwinforms.Label();
        numAmount = new jwinforms.NumericUpDown();
        btnOk = new jwinforms.Button();
        btnAll = new jwinforms.Button();
        btnNone = new jwinforms.Button();
        lblPaid = new jwinforms.Label();
        lblProfit = new jwinforms.Label();
        ((ISupportInitialize) (numAmount)).beginInit();
        this.suspendLayout();
        //
        // lblQuestion
        //
        lblQuestion.setLocation(new java.awt.Point(8, 50));
        lblQuestion.setName("lblQuestion");
        lblQuestion.setSize(new jwinforms.Size(160, 13));
        lblQuestion.setTabIndex(1);
        lblQuestion.setText("How many do you want to sell?");
        //
        // lblStatement
        //
        lblStatement.setLocation(new java.awt.Point(8, 8));
        lblStatement.setName("lblStatement");
        lblStatement.setSize(new jwinforms.Size(302, 13));
        lblStatement.setTabIndex(3);
        lblStatement.setText("The trader wants to by Machines, and offers 8,888 cr. each.");
        //
        // numAmount
        //
        numAmount.setLocation(new java.awt.Point(168, 48));
        numAmount.setMinimum(1);
        numAmount.setName("numAmount");
        numAmount.setSize(new jwinforms.Size(38, 20));
        numAmount.setTabIndex(1);
        numAmount.setValue(88);
        //
        // btnOk
        //
        btnOk.setDialogResult(DialogResult.OK);
        btnOk.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnOk.setLocation(new java.awt.Point(83, 74));
        btnOk.setName("btnOk");
        btnOk.setSize(new jwinforms.Size(41, 22));
        btnOk.setTabIndex(2);
        btnOk.setText("Ok");
        //
        // btnAll
        //
        btnAll.setDialogResult(DialogResult.OK);
        btnAll.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnAll.setLocation(new java.awt.Point(131, 74));
        btnAll.setName("btnAll");
        btnAll.setSize(new jwinforms.Size(41, 22));
        btnAll.setTabIndex(3);
        btnAll.setText("All");
        btnAll.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnAll_Click(sender, e);
            }
        });
        //
        // btnNone
        //
        btnNone.setDialogResult(DialogResult.Cancel);
        btnNone.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnNone.setLocation(new java.awt.Point(179, 74));
        btnNone.setName("btnNone");
        btnNone.setSize(new jwinforms.Size(41, 22));
        btnNone.setTabIndex(4);
        btnNone.setText("None");
        //
        // lblPaid
        //
        lblPaid.setLocation(new java.awt.Point(8, 21));
        lblPaid.setName("lblPaid");
        lblPaid.setSize(new jwinforms.Size(280, 13));
        lblPaid.setTabIndex(5);
        lblPaid.setText("You paid about 8,888 cr. per unit, and can sell 88 units.");
        //
        // lblProfit
        //
        lblProfit.setLocation(new java.awt.Point(8, 34));
        lblProfit.setName("lblProfit");
        lblProfit.setSize(new jwinforms.Size(200, 13));
        lblProfit.setTabIndex(6);
        lblProfit.setText("It costs 8,888 cr. per unit for disposal.");
        //
        // FormCargoSell
        //
        this.setAcceptButton(btnOk);
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(btnNone);
        this.setClientSize(new jwinforms.Size(302, 105));
        this.setControlBox(false);
        Controls.add(lblProfit);
        Controls.add(lblPaid);
        Controls.add(btnNone);
        Controls.add(btnAll);
        Controls.add(btnOk);
        Controls.add(numAmount);
        Controls.add(lblQuestion);
        Controls.add(lblStatement);
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setName("FormCargoSell");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Sell Xxxxxxxxxx");
        ((ISupportInitialize) (numAmount)).endInit();

    }

    // #endregion

    // #endregion

    // #region Event Handlers

    private void btnAll_Click(Object sender, EventArgs e) {
        numAmount.setValue(numAmount.getMaximum());
    }

    // #endregion

    // #region Properties


    public int Amount() {
        return numAmount.getValue();
    }

    // #endregion
}

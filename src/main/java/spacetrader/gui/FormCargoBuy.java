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
import spacetrader.game.enums.CargoBuyOp;
import spacetrader.game.*;
import spacetrader.guifacade.Facaded;

public class FormCargoBuy extends SpaceTraderForm {
    // #region Control Declarations

    private final Game game = Game.currentGame();
    private spacetrader.controls.Button btnOk;
    private spacetrader.controls.Button btnAll;
    private spacetrader.controls.Button btnNone;
    private spacetrader.controls.Label lblQuestion;
    private spacetrader.controls.Label lblStatement;
    private spacetrader.controls.NumericUpDown numAmount;
    private spacetrader.controls.Label lblAvailable;
    private spacetrader.controls.Label lblAfford;

    @Facaded
    public FormCargoBuy(int item, int maxAmount, CargoBuyOp op) {
        initializeComponent();

        Commander cmdr = game.getCommander();
        numAmount.setMaximum(maxAmount);
        numAmount.setValue(numAmount.getMinimum());
        this.setText(Functions.stringVars(Strings.CargoTitle, Strings.CargoBuyOps[op.castToInt()],
                Consts.TradeItems[item].Name()));
        lblQuestion.setText(Functions.stringVars(Strings.CargoBuyQuestion, Strings.CargoBuyOps[op.castToInt()]
                .toLowerCase()));

        switch (op) {
            case BuySystem:
                lblStatement.setText(Functions.stringVars(Strings.CargoBuyStatement, Functions.formatMoney(game
                        .getPriceCargoBuy()[item]), Functions.formatNumber(maxAmount)));

                this.setHeight(btnOk.getTop() + btnOk.getHeight() + 34);
                break;
            case BuyTrader:
                int afford = Math.min(cmdr.getCash() / game.getPriceCargoBuy()[item], cmdr.getShip()
                        .FreeCargoBays());
                if (afford < maxAmount)
                    numAmount.setMaximum(afford);

                lblStatement.setText(Functions.stringVars(Strings.CargoBuyStatementTrader, Consts.TradeItems[item].Name(),
                        Functions.formatMoney(game.getPriceCargoBuy()[item])));
                lblAvailable.setText(Functions.stringVars(Strings.CargoBuyAvailable, Functions.multiples(game.getOpponent()
                        .getCargo()[item], Strings.CargoUnit)));
                lblAfford.setText(Functions.stringVars(Strings.CargoBuyAfford, Functions.multiples(afford,
                        Strings.CargoUnit)));

                lblAvailable.setVisible(true);
                lblAfford.setVisible(true);

                btnOk.setTop(btnOk.getTop() + 26);
                btnAll.setTop(btnAll.getTop() + 26);
                btnNone.setTop(btnNone.getTop() + 26);
                lblQuestion.setTop(lblQuestion.getTop() + 26);
                numAmount.setTop(numAmount.getTop() + 26);

                break;
            case Plunder:
                lblStatement.setText(Functions.stringVars(Strings.CargoBuyStatementSteal, Functions.formatNumber(game
                        .getOpponent().getCargo()[item])));

                this.setHeight(btnOk.getTop() + btnOk.getHeight() + 34);
                break;
        }
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        lblQuestion = new spacetrader.controls.Label();
        lblStatement = new spacetrader.controls.Label();
        numAmount = new spacetrader.controls.NumericUpDown();
        btnOk = new spacetrader.controls.Button();
        btnAll = new spacetrader.controls.Button();
        btnNone = new spacetrader.controls.Button();
        lblAvailable = new spacetrader.controls.Label();
        lblAfford = new spacetrader.controls.Label();
        ((ISupportInitialize) (numAmount)).beginInit();
        this.suspendLayout();
        //
        // lblQuestion
        //
        lblQuestion.setAutoSize(true);
        lblQuestion.setLocation(new java.awt.Point(8, 24));
        lblQuestion.setName("lblQuestion");
        lblQuestion.setSize(new spacetrader.controls.Size(161, 16));
        lblQuestion.setTabIndex(1);
        lblQuestion.setText("How many do you want to buy?");
        //
        // lblStatement
        //
        lblStatement.setLocation(new java.awt.Point(8, 8));
        lblStatement.setName("lblStatement");
        lblStatement.setSize(new spacetrader.controls.Size(326, 13));
        lblStatement.setTabIndex(3);
        lblStatement.setText("The trader wants to sell Machines for the price of 8,888 cr. each.");
        //
        // numAmount
        //
        numAmount.setLocation(new java.awt.Point(168, 22));
        numAmount.setMaximum(999);
        numAmount.setMinimum(1);
        numAmount.setName("numAmount");
        numAmount.setSize(new spacetrader.controls.Size(44, 20));
        numAmount.setTabIndex(1);
        numAmount.setValue(1);
        //
        // btnOk
        //
        btnOk.setDialogResult(DialogResult.OK);
        btnOk.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnOk.setLocation(new java.awt.Point(95, 48));
        btnOk.setName("btnOk");
        btnOk.setSize(new spacetrader.controls.Size(41, 22));
        btnOk.setTabIndex(2);
        btnOk.setText("Ok");
        //
        // btnAll
        //
        btnAll.setDialogResult(DialogResult.OK);
        btnAll.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnAll.setLocation(new java.awt.Point(143, 48));
        btnAll.setName("btnAll");
        btnAll.setSize(new spacetrader.controls.Size(41, 22));
        btnAll.setTabIndex(3);
        btnAll.setText("All");
        btnAll.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnAll_Click(sender, e);
            }
        });
        //
        // btnNone
        //
        btnNone.setDialogResult(DialogResult.CANCEL);
        btnNone.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnNone.setLocation(new java.awt.Point(191, 48));
        btnNone.setName("btnNone");
        btnNone.setSize(new spacetrader.controls.Size(41, 22));
        btnNone.setTabIndex(4);
        btnNone.setText("None");
        //
        // lblAvailable
        //
        lblAvailable.setLocation(new java.awt.Point(8, 21));
        lblAvailable.setName("lblAvailable");
        lblAvailable.setSize(new spacetrader.controls.Size(163, 13));
        lblAvailable.setTabIndex(5);
        lblAvailable.setText("The trader has 88 units for sale.");
        lblAvailable.setVisible(false);
        //
        // lblAfford
        //
        lblAfford.setLocation(new java.awt.Point(8, 34));
        lblAfford.setName("lblAfford");
        lblAfford.setSize(new spacetrader.controls.Size(157, 13));
        lblAfford.setTabIndex(6);
        lblAfford.setText("You can afford to buy 88 units.");
        lblAfford.setVisible(false);
        //
        // FormCargoBuy
        //
        this.setAcceptButton(btnOk);
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(btnNone);
        this.setClientSize(new spacetrader.controls.Size(326, 105));
        this.setControlBox(false);
        Controls.add(btnNone);
        Controls.add(btnAll);
        Controls.add(btnOk);
        Controls.add(numAmount);
        Controls.add(lblQuestion);
        Controls.add(lblStatement);
        Controls.add(lblAfford);
        Controls.add(lblAvailable);
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FixedDialog);
        this.setName("FormCargoBuy");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Buy Xxxxxxxxxx");
        ((ISupportInitialize) (numAmount)).endInit();

    }

    private void btnAll_Click(Object sender, EventArgs e) {
        numAmount.setValue(numAmount.getMaximum());
    }


    @Facaded
    public int Amount() {
        return numAmount.getValue();
    }
}

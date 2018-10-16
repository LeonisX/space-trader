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
import spacetrader.game.Commander;
import spacetrader.game.Functions;
import spacetrader.game.Game;
import spacetrader.game.Strings;

public class FormPayBackLoan extends SpaceTraderForm {
    private spacetrader.controls.Button btnOk;
    private spacetrader.controls.Label lblQuestion;
    private spacetrader.controls.Button btnMax;
    private spacetrader.controls.Button btnNothing;
    private spacetrader.controls.NumericUpDown numAmount;
    private spacetrader.controls.Label lblStatement;

    public FormPayBackLoan() {
        initializeComponent();

        Commander cmdr = Game.getCurrentGame().getCommander();
        int max = Math.min(cmdr.getDebt(), cmdr.getCash());
        numAmount.setMaximum(max);
        numAmount.setValue(numAmount.getMinimum());
        lblStatement.setText(Functions.stringVars(Strings.BankLoanStatementDebt, Functions.multiples(cmdr.getDebt(),
                Strings.MoneyUnit)));
    }

    public static void main(String[] args) throws Exception {
        FormPayBackLoan form = new FormPayBackLoan();
        Launcher.runForm(form);
        System.out.println(form.Amount());
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        lblQuestion = new spacetrader.controls.Label();
        numAmount = new spacetrader.controls.NumericUpDown();
        btnOk = new spacetrader.controls.Button();
        btnMax = new spacetrader.controls.Button();
        btnNothing = new spacetrader.controls.Button();
        lblStatement = new spacetrader.controls.Label();
        ((ISupportInitialize) (numAmount)).beginInit();
        this.suspendLayout();
        //
        // lblQuestion
        //
        lblQuestion.setAutoSize(true);
        lblQuestion.setLocation(new java.awt.Point(8, 24));
        lblQuestion.setName("lblQuestion");
        lblQuestion.setSize(new spacetrader.controls.Size(188, 13));
        lblQuestion.setTabIndex(3);
        lblQuestion.setText("How much do you want to pay back?");
        //
        // numAmount
        //
        numAmount.setLocation(new java.awt.Point(196, 22));
        numAmount.setMaximum(999999);
        numAmount.setMinimum(1);
        numAmount.setName("numAmount");
        numAmount.setSize(new spacetrader.controls.Size(58, 20));
        numAmount.setTabIndex(1);
        numAmount.ThousandsSeparator = true;
        numAmount.setValue(88888);
        //
        // btnOk
        //
        btnOk.setDialogResult(DialogResult.OK);
        btnOk.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnOk.setLocation(new java.awt.Point(58, 48));
        btnOk.setName("btnOk");
        btnOk.setSize(new spacetrader.controls.Size(41, 22));
        btnOk.setTabIndex(2);
        btnOk.setText("Ok");
        //
        // btnMax
        //
        btnMax.setDialogResult(DialogResult.OK);
        btnMax.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnMax.setLocation(new java.awt.Point(106, 48));
        btnMax.setName("btnMax");
        btnMax.setSize(new spacetrader.controls.Size(41, 22));
        btnMax.setTabIndex(3);
        btnMax.setText("Max");
        btnMax.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnMax_Click(sender, e);
            }
        });
        //
        // btnNothing
        //
        btnNothing.setDialogResult(DialogResult.CANCEL);
        btnNothing.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnNothing.setLocation(new java.awt.Point(154, 48));
        btnNothing.setName("btnNothing");
        btnNothing.setSize(new spacetrader.controls.Size(53, 22));
        btnNothing.setTabIndex(4);
        btnNothing.setText("Nothing");
        //
        // lblStatement
        //
        lblStatement.setLocation(new java.awt.Point(8, 8));
        lblStatement.setName("lblStatement");
        lblStatement.setSize(new spacetrader.controls.Size(176, 13));
        lblStatement.setTabIndex(5);
        lblStatement.setText("You have a debt of 88,888 credits.");
        //
        // FormPayBackLoan
        //
        this.setAcceptButton(btnOk);
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(btnNothing);
        this.setClientSize(new spacetrader.controls.Size(264, 79));
        this.setControlBox(false);
        controls.addAll(lblStatement, btnNothing, btnMax, btnOk, numAmount,
                lblQuestion);
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FixedDialog);
        this.setName("FormPayBackLoan");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Pay Back Loan");
        ((ISupportInitialize) (numAmount)).endInit();
    }

    private void btnMax_Click(Object sender, EventArgs e) {
        numAmount.setValue(numAmount.getMaximum());
    }

    public int Amount() {
        return numAmount.getValue();
    }
}

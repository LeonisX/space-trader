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

import jwinforms.*;
import spacetrader.Commander;
import spacetrader.Functions;
import spacetrader.Game;
import spacetrader.Strings;

public class FormPayBackLoan extends SpaceTraderForm {
    private jwinforms.Button btnOk;
    private jwinforms.Label lblQuestion;
    private jwinforms.Button btnMax;
    private jwinforms.Button btnNothing;
    private jwinforms.NumericUpDown numAmount;
    private jwinforms.Label lblStatement;

    public FormPayBackLoan() {
        InitializeComponent();

        Commander cmdr = Game.CurrentGame().Commander();
        int max = Math.min(cmdr.getDebt(), cmdr.getCash());
        numAmount.setMaximum(max);
        numAmount.setValue(numAmount.getMinimum());
        lblStatement.setText(Functions.StringVars(Strings.BankLoanStatementDebt, Functions.Multiples(cmdr.getDebt(),
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
    private void InitializeComponent() {
        lblQuestion = new jwinforms.Label();
        numAmount = new jwinforms.NumericUpDown();
        btnOk = new jwinforms.Button();
        btnMax = new jwinforms.Button();
        btnNothing = new jwinforms.Button();
        lblStatement = new jwinforms.Label();
        ((ISupportInitialize) (numAmount)).beginInit();
        this.SuspendLayout();
        //
        // lblQuestion
        //
        lblQuestion.setAutoSize(true);
        lblQuestion.setLocation(new java.awt.Point(8, 24));
        lblQuestion.setName("lblQuestion");
        lblQuestion.setSize(new jwinforms.Size(188, 13));
        lblQuestion.setTabIndex(3);
        lblQuestion.setText("How much do you want to pay back?");
        //
        // numAmount
        //
        numAmount.setLocation(new java.awt.Point(196, 22));
        numAmount.setMaximum(999999);
        numAmount.setMinimum(1);
        numAmount.setName("numAmount");
        numAmount.setSize(new jwinforms.Size(58, 20));
        numAmount.setTabIndex(1);
        numAmount.ThousandsSeparator = true;
        numAmount.setValue(88888);
        //
        // btnOk
        //
        btnOk.setDialogResult(DialogResult.OK);
        btnOk.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnOk.setLocation(new java.awt.Point(58, 48));
        btnOk.setName("btnOk");
        btnOk.setSize(new jwinforms.Size(41, 22));
        btnOk.setTabIndex(2);
        btnOk.setText("Ok");
        //
        // btnMax
        //
        btnMax.setDialogResult(DialogResult.OK);
        btnMax.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnMax.setLocation(new java.awt.Point(106, 48));
        btnMax.setName("btnMax");
        btnMax.setSize(new jwinforms.Size(41, 22));
        btnMax.setTabIndex(3);
        btnMax.setText("Max");
        btnMax.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnMax_Click(sender, e);
            }
        });
        //
        // btnNothing
        //
        btnNothing.setDialogResult(DialogResult.Cancel);
        btnNothing.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnNothing.setLocation(new java.awt.Point(154, 48));
        btnNothing.setName("btnNothing");
        btnNothing.setSize(new jwinforms.Size(53, 22));
        btnNothing.setTabIndex(4);
        btnNothing.setText("Nothing");
        //
        // lblStatement
        //
        lblStatement.setLocation(new java.awt.Point(8, 8));
        lblStatement.setName("lblStatement");
        lblStatement.setSize(new jwinforms.Size(176, 13));
        lblStatement.setTabIndex(5);
        lblStatement.setText("You have a debt of 88,888 credits.");
        //
        // FormPayBackLoan
        //
        this.setAcceptButton(btnOk);
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(btnNothing);
        this.setClientSize(new jwinforms.Size(264, 79));
        this.setControlBox(false);
        Controls.addAll(lblStatement, btnNothing, btnMax, btnOk, numAmount,
                lblQuestion);
        this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedDialog);
        this.setName("FormPayBackLoan");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
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

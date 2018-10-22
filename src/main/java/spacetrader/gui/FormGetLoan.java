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
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

import static spacetrader.game.Functions.multiples;
import static spacetrader.game.Functions.stringVars;
import static spacetrader.game.Strings.BankLoanStatementBorrow;
import static spacetrader.game.Strings.MoneyUnit;

class FormGetLoan extends SpaceTraderForm {

    private Button okButton = new Button();
    private Label questionLabel = new Label();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();
    private NumericUpDown numAmount = new NumericUpDown();
    private Label statementLabelValue = new Label();

    FormGetLoan(int max) {
        initializeComponent();

        numAmount.setMaximum(max);
        numAmount.setValue(numAmount.getMinimum());
        statementLabelValue.setText(stringVars(BankLoanStatementBorrow, multiples(max, MoneyUnit)));
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formGetLoan");
        setText("Get Loan");
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(252, 79));
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);

        ((ISupportInitialize) (numAmount)).beginInit();
        suspendLayout();

        statementLabelValue.setLocation(new Point(8, 8));
        statementLabelValue.setSize(new Size(189, 13));
        statementLabelValue.setTabIndex(5);
        //statementLabelValue.setText("You can borrow up to 88,888 credits.");

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(new Point(8, 24));
        questionLabel.setSize(new Size(178, 13));
        questionLabel.setTabIndex(3);
        questionLabel.setText("How much do you want to borrow?");

        numAmount.setLocation(new Point(184, 22));
        numAmount.setMinimum(1);
        numAmount.setSize(new Size(64, 20));
        numAmount.setTabIndex(1);
        numAmount.setThousandsSeparator(true);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(52, 48));
        okButton.setSize(new Size(41, 22));
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        maxButton.setDialogResult(DialogResult.OK);
        maxButton.setFlatStyle(FlatStyle.FLAT);
        maxButton.setLocation(new Point(100, 48));
        maxButton.setSize(new Size(41, 22));
        maxButton.setTabIndex(3);
        maxButton.setText("Max");
        maxButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numAmount.setValue(numAmount.getMaximum());
            }
        });

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setFlatStyle(FlatStyle.FLAT);
        nothingButton.setLocation(new Point(148, 48));
        nothingButton.setSize(new Size(53, 22));
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        ((ISupportInitialize) (numAmount)).endInit();

        controls.addAll(Arrays.asList(statementLabelValue, nothingButton, maxButton, okButton, numAmount, questionLabel));
    }

    int getAmount() {
        return numAmount.getValue();
    }
}

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

import static spacetrader.game.Functions.multiples;
import static spacetrader.game.Functions.stringVars;
import static spacetrader.game.Strings.BankLoanStatementDebt;
import static spacetrader.game.Strings.MoneyUnit;

import java.awt.Point;
import spacetrader.controls.Button;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.controls.Label;
import spacetrader.controls.NumericUpDown;
import spacetrader.controls.Size;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.gui.debug.Launcher;
import spacetrader.util.ReflectionUtils;

public class FormPayBackLoan extends SpaceTraderForm {
    
    private Button okButton = new Button();
    private Label questionLabel = new Label();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();
    private NumericUpDown numAmount = new NumericUpDown();
    private Label statementLabelValue = new Label();

    public FormPayBackLoan() {
        initializeComponent();

        Commander commander = Game.getCurrentGame().getCommander();
        int max = Math.min(commander.getDebt(), commander.getCash());
        numAmount.setMaximum(max);
        numAmount.setValue(numAmount.getMinimum());
        statementLabelValue.setText(stringVars(BankLoanStatementDebt, multiples(commander.getDebt(), MoneyUnit)));
    }

    public static void main(String[] args) throws Exception {
        new Game("name", Difficulty.BEGINNER,8,8,8,8, null);
        FormPayBackLoan form = new FormPayBackLoan();
        Launcher.runForm(form);
        System.out.println(form.getAmount());
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formPayBackLoan");
        setText("Pay Back Loan");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(264, 79));
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);

        numAmount.beginInit();
        suspendLayout();

        statementLabelValue.setLocation(new Point(8, 8));
        statementLabelValue.setSize(new Size(176, 13));
        statementLabelValue.setTabIndex(5);
        statementLabelValue.setText("You have a debt of 88,888 credits.");

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(new Point(8, 24));
        questionLabel.setSize(new Size(188, 13));
        questionLabel.setTabIndex(3);
        questionLabel.setText("How much do you want to pay back?");

        numAmount.setLocation(new Point(196, 22));
        numAmount.setMinimum(1);
        numAmount.setSize(new Size(58, 20));
        numAmount.setTabIndex(1);
        numAmount.setThousandsSeparator(true);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(58, 48));
        okButton.setSize(new Size(41, 22));
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        maxButton.setDialogResult(DialogResult.OK);
        maxButton.setFlatStyle(FlatStyle.FLAT);
        maxButton.setLocation(new Point(106, 48));
        maxButton.setSize(new Size(41, 22));
        maxButton.setTabIndex(3);
        maxButton.setText("Max");
        maxButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numAmount.setValue(numAmount.getMaximum());
            }
        });

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setFlatStyle(FlatStyle.FLAT);
        nothingButton.setLocation(new Point(154, 48));
        nothingButton.setSize(new Size(53, 22));
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        controls.addAll(statementLabelValue, questionLabel, numAmount, okButton, maxButton, nothingButton);

        numAmount.endInit();
    }
    
    int getAmount() {
        return numAmount.getValue();
    }
}

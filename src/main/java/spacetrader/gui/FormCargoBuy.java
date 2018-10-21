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
import spacetrader.game.*;
import spacetrader.game.enums.CargoBuyOp;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

import static spacetrader.game.Functions.formatMoney;
import static spacetrader.game.Functions.formatNumber;
import static spacetrader.game.Functions.stringVars;

public class FormCargoBuy extends SpaceTraderForm {
    
    private Button okButton;
    private Button allButton;
    private Button noneButton;
    private Label questionLabel;
    private Label statementLabelValue;
    private NumericUpDown numericUpDown;
    private Label availableLabelValue;
    private Label affordLabelValue;

    @Facaded
    public FormCargoBuy(int item, int maxAmount, CargoBuyOp op) {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander cmdr = game.getCommander();
        numericUpDown.setMaximum(maxAmount);
        numericUpDown.setValue(numericUpDown.getMinimum());
        setText(stringVars(Strings.CargoTitle, Strings.CargoBuyOps[op.castToInt()], Consts.TradeItems[item].Name()));
        questionLabel.setText(stringVars(Strings.CargoBuyQuestion, Strings.CargoBuyOps[op.castToInt()].toLowerCase()));

        switch (op) {
            case BUY_SYSTEM:
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatement,
                        formatMoney(game.getPriceCargoBuy()[item]), formatNumber(maxAmount)));

                //TODO multiplier
                setHeight(okButton.getTop() + okButton.getHeight() + 34);
                break;
            case BUY_TRADER:
                int afford = Math.min(cmdr.getCash() / game.getPriceCargoBuy()[item], cmdr.getShip().getFreeCargoBays());
                if (afford < maxAmount) {
                    numericUpDown.setMaximum(afford);
                }
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatementTrader, Consts.TradeItems[item].Name(),
                        formatMoney(game.getPriceCargoBuy()[item])));
                availableLabelValue.setText(stringVars(Strings.CargoBuyAvailable, Functions.multiples(game.getOpponent()
                        .getCargo()[item], Strings.CargoUnit)));
                affordLabelValue.setText(stringVars(Strings.CargoBuyAfford, Functions.multiples(afford,
                        Strings.CargoUnit)));

                availableLabelValue.setVisible(true);
                affordLabelValue.setVisible(true);
                //TODO multiplier
                okButton.setTop(okButton.getTop() + 26);
                allButton.setTop(allButton.getTop() + 26);
                noneButton.setTop(noneButton.getTop() + 26);
                questionLabel.setTop(questionLabel.getTop() + 26);
                numericUpDown.setTop(numericUpDown.getTop() + 26);

                break;
            case PLUNDER:
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatementSteal, formatNumber(game
                        .getOpponent().getCargo()[item])));
                //TODO multiplier
                setHeight(okButton.getTop() + okButton.getHeight() + 34);
                break;
        }
    }

    private void initializeComponent() {
        questionLabel = new Label();
        statementLabelValue = new Label();
        numericUpDown = new NumericUpDown();
        okButton = new Button();
        allButton = new Button();
        noneButton = new Button();
        availableLabelValue = new Label();
        affordLabelValue = new Label();
        ((ISupportInitialize) (numericUpDown)).beginInit();

        setName("formCargoBuy");
        ReflectionUtils.setAllComponentNames(this);

        suspendLayout();

        //TODO delete all texts & sizes
        statementLabelValue.setLocation(new Point(8, 8));
        statementLabelValue.setSize(new Size(326, 13));
        statementLabelValue.setTabIndex(3);
        //statementLabelValue.setText("The trader wants to sell Machines for the price of 8,888 cr. each.");

        availableLabelValue.setLocation(new Point(8, 21));
        availableLabelValue.setSize(new Size(163, 13));
        availableLabelValue.setTabIndex(5);
        //availableLabelValue.setText("The trader has 88 units for sale.");
        availableLabelValue.setVisible(false);

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(new Point(8, 24));
        questionLabel.setSize(new Size(161, 16));
        questionLabel.setTabIndex(1);
        questionLabel.setText("How many do you want to buy?");

        numericUpDown.setLocation(new Point(168, 22));
        numericUpDown.setMaximum(999);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(new Size(44, 20));
        numericUpDown.setTabIndex(1);
        numericUpDown.setValue(1);

        affordLabelValue.setLocation(new Point(8, 34));
        affordLabelValue.setSize(new Size(157, 13));
        affordLabelValue.setTabIndex(6);
        //affordLabelValue.setText("You can afford to buy 88 units.");
        affordLabelValue.setVisible(false);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(95, 48));
        okButton.setSize(new Size(41, 22));
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        allButton.setDialogResult(DialogResult.OK);
        allButton.setFlatStyle(FlatStyle.FLAT);
        allButton.setLocation(new Point(143, 48));
        allButton.setSize(new Size(41, 22));
        allButton.setTabIndex(3);
        allButton.setText("All");
        allButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numericUpDown.setValue(numericUpDown.getMaximum());
            }
        });

        noneButton.setDialogResult(DialogResult.CANCEL);
        noneButton.setFlatStyle(FlatStyle.FLAT);
        noneButton.setLocation(new Point(191, 48));
        noneButton.setSize(new Size(41, 22));
        noneButton.setTabIndex(4);
        noneButton.setText("None");

        setAcceptButton(okButton);
        setAutoScaleBaseSize(new Size(5, 13));
        setCancelButton(noneButton);
        setClientSize(new Size(326, 105));
        setControlBox(false);

        controls.add(statementLabelValue);
        controls.add(availableLabelValue);
        controls.add(questionLabel);
        controls.add(numericUpDown);
        controls.add(affordLabelValue);
        controls.add(okButton);
        controls.add(allButton);
        controls.add(noneButton);

        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setShowInTaskbar(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        //setText("Buy Xxxxxxxxxx");
        ((ISupportInitialize) (numericUpDown)).endInit();
    }

    @Facaded
    public int Amount() {
        return numericUpDown.getValue();
    }
}

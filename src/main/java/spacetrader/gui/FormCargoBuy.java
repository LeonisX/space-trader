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
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.CargoBuyOp;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

import static spacetrader.game.Functions.formatMoney;
import static spacetrader.game.Functions.formatNumber;
import static spacetrader.game.Functions.stringVars;

public class FormCargoBuy extends SpaceTraderForm {
    
    private Button okButton = new Button();
    private Button allButton = new Button();
    private Button noneButton = new Button();
    private Label questionLabel = new Label();
    private Label statementLabelValue = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Label availableLabelValue = new Label();
    private Label affordLabelValue = new Label();

    @Facaded
    public FormCargoBuy(int item, int maxAmount, CargoBuyOp op) {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander cmdr = game.getCommander();
        numericUpDown.setMaximum(maxAmount);
        numericUpDown.setValue(numericUpDown.getMinimum());
        setText(stringVars(Strings.CargoTitle, Strings.CargoBuyOps[op.castToInt()], Consts.TradeItems[item].getName()));
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
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatementTrader, Consts.TradeItems[item].getName(),
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
        ReflectionUtils.setAllComponentNames(this);

        setName("formCargoBuy");
        //setText("Buy Xxxxxxxxxx");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(5, 13);
        setClientSize(326, 105);
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(noneButton);
        
        numericUpDown.beginInit();
        suspendLayout();

        //TODO delete all texts & sizes
        statementLabelValue.setLocation(8, 8);
        statementLabelValue.setSize(326, 13);
        statementLabelValue.setTabIndex(3);
        //statementLabelValue.setText("The trader wants to sell Machines for the price of 8,888 cr. each.");

        availableLabelValue.setLocation(8, 21);
        availableLabelValue.setSize(163, 13);
        availableLabelValue.setTabIndex(5);
        //availableLabelValue.setText("The trader has 88 units for sale.");
        availableLabelValue.setVisible(false);

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 24);
        questionLabel.setSize(161, 16);
        questionLabel.setTabIndex(1);
        questionLabel.setText("How many do you want to buy?");

        numericUpDown.setLocation(168, 22);
        //numericUpDown.setMaximum(999);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(44, 20);
        numericUpDown.setTabIndex(1);
        numericUpDown.setValue(1);

        affordLabelValue.setLocation(8, 34);
        affordLabelValue.setSize(157, 13);
        affordLabelValue.setTabIndex(6);
        //affordLabelValue.setText("You can afford to buy 88 units.");
        affordLabelValue.setVisible(false);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(95, 48);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        allButton.setDialogResult(DialogResult.OK);
        allButton.setFlatStyle(FlatStyle.FLAT);
        allButton.setLocation(143, 48);
        allButton.setSize(41, 22);
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
        noneButton.setLocation(191, 48);
        noneButton.setSize(41, 22);
        noneButton.setTabIndex(4);
        noneButton.setText("None");

        controls.addAll(statementLabelValue, availableLabelValue, questionLabel, numericUpDown, affordLabelValue,
                okButton, allButton, noneButton);

        numericUpDown.endInit();

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
    }

    @Facaded
    public int Amount() {
        return numericUpDown.getValue();
    }
}

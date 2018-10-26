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
import spacetrader.game.enums.CargoSellOp;
import spacetrader.game.*;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import static spacetrader.game.Functions.*;

@Facaded
public class FormCargoSell extends SpaceTraderForm {

    private Label statementLabelValue = new Label();
    private Label questionLabel = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Label paidLabelValue = new Label();
    private Label profitLabelValue = new Label();
    private Button okButton = new Button();
    private Button allButton = new Button();
    private Button noneButton = new Button();

    public FormCargoSell(int item, int maxAmount, CargoSellOp op, int price) {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander commander = game.getCommander();
        int cost = commander.getPriceCargo()[item] / commander.getShip().getCargo()[item];

        numericUpDown.setMaximum(maxAmount);
        numericUpDown.setValue(numericUpDown.getMinimum());
        setText(stringVars(Strings.CargoTitle, Strings.CargoSellOps[op.castToInt()], Consts.TradeItems[item].getName()));
        questionLabel.setText(stringVars(Strings.CargoSellQuestion, Strings.CargoSellOps[op.castToInt()].toLowerCase()));
        paidLabelValue.setText(stringVars(op == CargoSellOp.SELL_TRADER ? Strings.CargoSellPaidTrader
                        : Strings.CargoSellPaid, formatMoney(cost), multiples(maxAmount, Strings.CargoUnit)));
        profitLabelValue.setText(stringVars(Strings.CargoSellProfitPerUnit,
                price >= cost ? Strings.CargoProfit : Strings.CargoLoss, formatMoney(price >= cost ? price - cost : cost - price)));

        // Override defaults for some ops.
        switch (op) {
            case DUMP:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatementDump,
                        Strings.CargoSellOps[op.castToInt()].toLowerCase(), formatNumber(maxAmount)));
                profitLabelValue.setText(stringVars(Strings.CargoSellDumpCost, formatMoney(-price)));
                break;
            case JETTISON:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatementDump,
                        Strings.CargoSellOps[op.castToInt()].toLowerCase(), formatNumber(maxAmount)));
                break;
            case SELL_SYSTEM:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatement, formatNumber(maxAmount), formatMoney(price)));
                break;
            case SELL_TRADER:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatementTrader,
                        Consts.TradeItems[item].getName(), formatMoney(price)));
                break;
        }
    }
    
    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formCargoSell");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(5, 13);
        setClientSize(302, 105);
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(noneButton);
        
        numericUpDown.beginInit();
        suspendLayout();

        statementLabelValue.setLocation(8, 8);
        statementLabelValue.setSize(302, 13);
        statementLabelValue.setTabIndex(3);
        //statementLabelValue.setText("The trader wants to by Machines, and offers 8,888 cr. each.");

        paidLabelValue.setLocation(8, 21);
        paidLabelValue.setSize(280, 13);
        paidLabelValue.setTabIndex(5);
        //paidLabelValue.setText("You paid about 8,888 cr. per unit, and can sell 88 units.");

        profitLabelValue.setLocation(8, 34);
        profitLabelValue.setSize(200, 13);
        profitLabelValue.setTabIndex(6);
        //profitLabelValue.setText("It costs 8,888 cr. per unit for disposal.");

        questionLabel.setLocation(8, 50);
        questionLabel.setSize(160, 13);
        questionLabel.setTabIndex(1);
        questionLabel.setText("How many do you want to sell?");

        numericUpDown.setLocation(168, 48);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(38, 20);
        numericUpDown.setTabIndex(1);
        numericUpDown.setValue(88);
        
        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(83, 74);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");
        
        allButton.setDialogResult(DialogResult.OK);
        allButton.setFlatStyle(FlatStyle.FLAT);
        allButton.setLocation(131, 74);
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
        noneButton.setLocation(179, 74);
        noneButton.setSize(41, 22);
        noneButton.setTabIndex(4);
        noneButton.setText("None");

        controls.addAll(statementLabelValue, paidLabelValue, profitLabelValue, questionLabel, numericUpDown,
                okButton, allButton, noneButton);

        numericUpDown.endInit();

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
    }

    public int Amount() {
        return numericUpDown.getValue();
    }
}

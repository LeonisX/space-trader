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
import spacetrader.game.enums.CargoSellOp;
import spacetrader.game.*;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

import static spacetrader.game.Functions.*;

@Facaded
public class FormCargoSell extends SpaceTraderForm {

    private Label statementLabelValue;
    private Label questionLabel;
    private NumericUpDown numericUpDown;
    private Label paidLabelValue;
    private Label profitLabelValue;
    private Button okButton;
    private Button allButton;
    private Button noneButton;

    public FormCargoSell(int item, int maxAmount, CargoSellOp op, int price) {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander commander = game.getCommander();
        int cost = commander.getPriceCargo()[item] / commander.getShip().getCargo()[item];

        numericUpDown.setMaximum(maxAmount);
        numericUpDown.setValue(numericUpDown.getMinimum());
        setText(stringVars(Strings.CargoTitle, Strings.CargoSellOps[op.castToInt()], Consts.TradeItems[item].Name()));
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
                        Consts.TradeItems[item].Name(), formatMoney(price)));
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
        paidLabelValue = new Label();
        profitLabelValue = new Label();

        ((ISupportInitialize) (numericUpDown)).beginInit();

        setName("formCargoSell");
        ReflectionUtils.setAllComponentNames(this);

        suspendLayout();

        statementLabelValue.setLocation(new Point(8, 8));
        statementLabelValue.setSize(new Size(302, 13));
        statementLabelValue.setTabIndex(3);
        //statementLabelValue.setText("The trader wants to by Machines, and offers 8,888 cr. each.");

        paidLabelValue.setLocation(new Point(8, 21));
        paidLabelValue.setSize(new Size(280, 13));
        paidLabelValue.setTabIndex(5);
        //paidLabelValue.setText("You paid about 8,888 cr. per unit, and can sell 88 units.");

        profitLabelValue.setLocation(new Point(8, 34));
        profitLabelValue.setSize(new Size(200, 13));
        profitLabelValue.setTabIndex(6);
        //profitLabelValue.setText("It costs 8,888 cr. per unit for disposal.");

        questionLabel.setLocation(new Point(8, 50));
        questionLabel.setSize(new Size(160, 13));
        questionLabel.setTabIndex(1);
        questionLabel.setText("How many do you want to sell?");

        numericUpDown.setLocation(new Point(168, 48));
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(new Size(38, 20));
        numericUpDown.setTabIndex(1);
        numericUpDown.setValue(88);
        
        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(83, 74));
        okButton.setSize(new Size(41, 22));
        okButton.setTabIndex(2);
        okButton.setText("Ok");
        
        allButton.setDialogResult(DialogResult.OK);
        allButton.setFlatStyle(FlatStyle.FLAT);
        allButton.setLocation(new Point(131, 74));
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
        noneButton.setLocation(new java.awt.Point(179, 74));
        noneButton.setSize(new Size(41, 22));
        noneButton.setTabIndex(4);
        noneButton.setText("None");

        setAcceptButton(okButton);
        setAutoScaleBaseSize(new Size(5, 13));
        setCancelButton(noneButton);
        setClientSize(new Size(302, 105));
        setControlBox(false);

        controls.add(statementLabelValue);
        controls.add(paidLabelValue);
        controls.add(profitLabelValue);
        controls.add(questionLabel);
        controls.add(numericUpDown);
        controls.add(okButton);
        controls.add(allButton);
        controls.add(noneButton);

        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setShowInTaskbar(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        //setText("Sell Xxxxxxxxxx");
        ((ISupportInitialize) (numericUpDown)).endInit();

    }

    public int Amount() {
        return numericUpDown.getValue();
    }
}

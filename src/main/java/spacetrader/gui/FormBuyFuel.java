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

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

public class FormBuyFuel extends SpaceTraderForm {

    private Label questionLabel;
    private NumericUpDown numericUpDown;
    private Button okButton;
    private Button maxButton;
    private Button nothingButton;

    FormBuyFuel() {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander cmdr = game.getCommander();
        numericUpDown.setMaximum(Math.min(cmdr.getCash(),
                (cmdr.getShip().getFuelTanks() - cmdr.getShip().getFuel()) * cmdr.getShip().getFuelCost()));
        numericUpDown.setValue(numericUpDown.getMaximum());
    }

    private void initializeComponent() {
        this.questionLabel = new Label();
        this.numericUpDown = new NumericUpDown();
        this.okButton = new Button();
        this.maxButton = new Button();
        this.nothingButton = new Button();
        ((ISupportInitialize) (this.numericUpDown)).beginInit();

        this.setName("formBuyFuel");
        ReflectionUtils.setAllComponentNames(this);

        this.suspendLayout();

        this.questionLabel.setAutoSize(true);
        this.questionLabel.setLocation(new Point(8, 8));
        this.questionLabel.setSize(new Size(211, 13));
        this.questionLabel.setTabIndex(3);
        this.questionLabel.setText("How much do you want to spend on fuel?");

        this.numericUpDown.setLocation(new Point(216, 6));
        //this.numericUpDown.setMaximum(999);
        this.numericUpDown.setMinimum(1);
        this.numericUpDown.setSize(new Size(44, 20));
        this.numericUpDown.setTabIndex(1);
        //this.numericUpDown.setValue(888);

        this.okButton.setDialogResult(DialogResult.OK);
        this.okButton.setFlatStyle(FlatStyle.FLAT);
        this.okButton.setLocation(new Point(61, 32));
        this.okButton.setSize(new Size(41, 22));
        this.okButton.setTabIndex(2);
        this.okButton.setText("Ok");

        this.maxButton.setDialogResult(DialogResult.OK);
        this.maxButton.setFlatStyle(FlatStyle.FLAT);
        //TODO delete all sizes
        this.maxButton.setLocation(new Point(109, 32));
        this.maxButton.setSize(new Size(41, 22));
        this.maxButton.setTabIndex(3);
        //TODO delete all texts
        this.maxButton.setText("Max");
        this.maxButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numericUpDown.setValue(numericUpDown.getMaximum());
            }
        });

        this.nothingButton.setDialogResult(DialogResult.CANCEL);
        this.nothingButton.setFlatStyle(FlatStyle.FLAT);
        this.nothingButton.setLocation(new Point(157, 32));
        this.nothingButton.setSize(new Size(53, 22));
        this.nothingButton.setTabIndex(4);
        this.nothingButton.setText("Nothing");

        this.setAcceptButton(this.okButton);
        this.setAutoScaleBaseSize(new Size(5, 13));
        this.setCancelButton(this.nothingButton);
        this.setClientSize(new Size(270, 63));
        this.setControlBox(false);

        this.controls.addAll(Arrays.asList(questionLabel, numericUpDown, okButton, maxButton, nothingButton));

        this.setFormBorderStyle(FormBorderStyle.FixedDialog);

        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Buy Fuel");
        ((ISupportInitialize) (this.numericUpDown)).endInit();
    }

    public int getAmount() {
        return numericUpDown.getValue();
    }
}

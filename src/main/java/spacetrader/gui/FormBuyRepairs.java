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
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.gui.debug.Launcher;
import spacetrader.util.ReflectionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class FormBuyRepairs extends WinformForm {
    
    private Label questionLabel;
    private NumericUpDown numericUpDown;
    private Button okButton;
    private Button maxButton;
    private Button nothingButton;

    public FormBuyRepairs() {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander cmdr = game.getCommander();
        numericUpDown.setMaximum(Math.min(cmdr.getCash(),
                (cmdr.getShip().getHullStrength() - cmdr.getShip().getHull()) * cmdr.getShip().getRepairCost()));
        numericUpDown.setValue(numericUpDown.getMaximum());
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        FormBuyRepairs form = new FormBuyRepairs();
        Launcher.runForm(form);
        System.out.println(form.getAmount());
    }

    private void initializeComponent() {
        questionLabel = new Label();
        numericUpDown = new NumericUpDown();
        okButton = new Button();
        maxButton = new Button();
        nothingButton = new Button();
        ((ISupportInitialize) (numericUpDown)).beginInit();

        setName("formBuyRepair");
        ReflectionUtils.setAllComponentNames(this);

        suspendLayout();

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(new Point(8, 8));
        questionLabel.setSize(new Size(227, 13));
        questionLabel.setTabIndex(3);
        questionLabel.setText("How much do you want to spend on repairs?");

        numericUpDown.setLocation(new Point(232, 6));
        //numericUpDown.setMaximum(999);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(new Size(44, 20));
        numericUpDown.setTabIndex(1);
        //numericUpDown.setValue(888);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(69, 32));
        okButton.setSize(new Size(41, 22));
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        maxButton.setDialogResult(DialogResult.OK);
        maxButton.setFlatStyle(FlatStyle.FLAT);
        maxButton.setLocation(new Point(117, 32));
        maxButton.setSize(new Size(41, 22));
        maxButton.setTabIndex(3);
        maxButton.setText("Max");

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setFlatStyle(FlatStyle.FLAT);
        nothingButton.setLocation(new Point(165, 32));
        nothingButton.setSize(new Size(53, 22));
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        setAcceptButton(okButton);
        setAutoScaleBaseSize(new Size(5, 13));
        setCancelButton(nothingButton);
        setClientSize(new Size(286, 63));
        setControlBox(false);
        controls.addAll(Arrays.asList(questionLabel, numericUpDown, okButton, maxButton, nothingButton));
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);

        setShowInTaskbar(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setText("Hull Repair");
        ((ISupportInitialize) (numericUpDown)).endInit();
    }

    public int getAmount() {
        return numericUpDown.getValue();
    }
}

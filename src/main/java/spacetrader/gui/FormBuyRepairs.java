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

import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.NumericUpDown;
import spacetrader.controls.WinformForm;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.gui.debug.Launcher;
import spacetrader.util.ReflectionUtils;

import java.util.Arrays;

public class FormBuyRepairs extends WinformForm {
    
    private Label questionLabel = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Button okButton = new Button();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();

    public FormBuyRepairs() {
        initializeComponent();

        Commander cmdr = Game.getCurrentGame().getCommander();
        numericUpDown.setMaximum(Math.min(cmdr.getCash(),
                (cmdr.getShip().getHullStrength() - cmdr.getShip().getHull()) * cmdr.getShip().getRepairCost()));
        numericUpDown.setValue(numericUpDown.getMaximum());
    }

    public static void main(String[] args) {
        FormBuyRepairs form = new FormBuyRepairs();
        Launcher.runForm(form);
        System.out.println(form.getAmount());
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        
        setName("formBuyRepair");
        setText("Hull Repair");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(5, 13);
        setClientSize(286, 63);
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);
        
        //TODO need??
        numericUpDown.beginInit();
        suspendLayout();

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 8);
        questionLabel.setSize(227, 13);
        questionLabel.setTabIndex(3);
        questionLabel.setText("How much do you want to spend on repairs?");

        numericUpDown.setLocation(232, 6);
        //numericUpDown.setMaximum(999);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(44, 20);
        numericUpDown.setTabIndex(1);
        //numericUpDown.setValue(888);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(69, 32);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        maxButton.setDialogResult(DialogResult.OK);
        maxButton.setFlatStyle(FlatStyle.FLAT);
        maxButton.setLocation(117, 32);
        maxButton.setSize(41, 22);
        maxButton.setTabIndex(3);
        maxButton.setText("Max");

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setFlatStyle(FlatStyle.FLAT);
        nothingButton.setLocation(165, 32);
        nothingButton.setSize(53, 22);
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        controls.addAll(Arrays.asList(questionLabel, numericUpDown, okButton, maxButton, nothingButton));
        
        numericUpDown.endInit();

        ReflectionUtils.loadControlsData(this);
    }

    int getAmount() {
        return numericUpDown.getValue();
    }
}

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
import spacetrader.util.ReflectionUtils;

import java.awt.*;

public class FormFind extends SpaceTraderForm {

    private Label questionLabel = new Label();
    private Button okButton = new Button();
    private Button cancelButton = new Button();
    private TextBox systemTextBox = new TextBox();
    private CheckBox trackSystemCheckBox = new CheckBox();

    public FormFind() {
        initializeComponent();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        setName("formFind");
        setText("Find System");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(184, 97));
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(cancelButton);

        this.suspendLayout();

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(new Point(8, 8));
        questionLabel.setSize(new Size(177, 13));
        questionLabel.setTabIndex(3);
        questionLabel.setText("Which system are you looking for?");

        systemTextBox.setLocation(new Point(8, 24));
        systemTextBox.setSize(new Size(168, 20));
        systemTextBox.setTabIndex(1);

        trackSystemCheckBox.setLocation(new Point(8, 48));
        trackSystemCheckBox.setSize(new Size(112, 16));
        trackSystemCheckBox.setTabIndex(2);
        trackSystemCheckBox.setText("Track this system");

        okButton.setDialogResult(DialogResult.OK);
        okButton.setFlatStyle(FlatStyle.FLAT);
        okButton.setLocation(new Point(43, 68));
        okButton.setSize(new Size(40, 22));
        okButton.setTabIndex(3);
        okButton.setText("Ok");

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setFlatStyle(FlatStyle.FLAT);
        cancelButton.setLocation(new Point(91, 68));
        cancelButton.setSize(new Size(50, 22));
        cancelButton.setTabIndex(4);
        cancelButton.setText("cancel");

        controls.addAll(questionLabel, systemTextBox, trackSystemCheckBox, okButton, cancelButton);
    }

    String getSystemName() {
        return systemTextBox.getText();
    }

    boolean isTrackSystem() {
        return trackSystemCheckBox.isChecked();
    }
}

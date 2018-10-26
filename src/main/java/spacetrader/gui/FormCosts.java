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
import spacetrader.controls.enums.ContentAlignment;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Functions;
import spacetrader.game.Game;
import spacetrader.game.GlobalAssets;
import spacetrader.util.ReflectionUtils;

class FormCosts extends SpaceTraderForm {

    private Button closeButton = new Button();
    private Label mercenariesLabelValue = new Label();
    private Label insuranceLabelValue = new Label();
    private Label interestLabelValue = new Label();
    private Label wormholeTaxLabelValue = new Label();
    private Label totalLabelValue = new Label();
    private Label totalLabel = new Label();
    private Label wormholeTaxLabel = new Label();
    private Label interestLabel = new Label();
    private Label mercenariesLabel = new Label();
    private Label insuranceLabel = new Label();
    private HorizontalLine horizontalLine = new HorizontalLine();

    FormCosts() {
        initializeComponent();

        Game game = Game.getCurrentGame();
        mercenariesLabelValue.setText(Functions.formatMoney(game.getMercenaryCosts()));
        insuranceLabelValue.setText(Functions.formatMoney(game.getInsuranceCosts()));
        interestLabelValue.setText(Functions.formatMoney(game.getInterestCosts()));
        wormholeTaxLabelValue.setText(Functions.formatMoney(game.getWormholeCosts()));
        totalLabelValue.setText(Functions.formatMoney(game.getCurrentCosts()));
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        
        setName("formCosts");
        setText("Cost Specification");
        setAutoScaleBaseSize(5, 13);
        setClientSize(164, 99);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);
        
        suspendLayout();

        mercenariesLabel.setAutoSize(true);
        mercenariesLabel.setFont(FontCollection.bold825);
        mercenariesLabel.setLocation(8, 8);
        mercenariesLabel.setSize(72, 13);
        mercenariesLabel.setTabIndex(4);
        mercenariesLabel.setText("Mercenaries:");

        mercenariesLabelValue.setLocation(104, 8);
        mercenariesLabelValue.setSize(39, 13);
        mercenariesLabelValue.setTabIndex(36);
        //mercenariesLabelValue.setText("888 cr.");
        mercenariesLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        insuranceLabel.setAutoSize(true);
        insuranceLabel.setFont(FontCollection.bold825);
        insuranceLabel.setLocation(8, 24);
        insuranceLabel.setSize(59, 13);
        insuranceLabel.setTabIndex(3);
        insuranceLabel.setText("Insurance:");

        insuranceLabelValue.setLocation(104, 24);
        insuranceLabelValue.setSize(39, 13);
        insuranceLabelValue.setTabIndex(40);
        //insuranceLabelValue.setText("888 cr.");
        insuranceLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        interestLabel.setAutoSize(true);
        interestLabel.setFont(FontCollection.bold825);
        interestLabel.setLocation(8, 40);
        interestLabel.setSize(47, 13);
        interestLabel.setTabIndex(5);
        interestLabel.setText("Interest:");

        interestLabelValue.setLocation(104, 40);
        interestLabelValue.setSize(39, 13);
        interestLabelValue.setTabIndex(44);
        //interestLabelValue.setText("888 cr.");
        interestLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        wormholeTaxLabel.setAutoSize(true);
        wormholeTaxLabel.setFont(FontCollection.bold825);
        wormholeTaxLabel.setLocation(8, 56);
        wormholeTaxLabel.setSize(84, 13);
        wormholeTaxLabel.setTabIndex(6);
        wormholeTaxLabel.setText("Wormhole Tax:");

        wormholeTaxLabelValue.setLocation(104, 56);
        wormholeTaxLabelValue.setSize(39, 13);
        wormholeTaxLabelValue.setTabIndex(48);
        //wormholeTaxLabelValue.setText("888 cr.");
        wormholeTaxLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        //horizontalLine.setBackground(Color.darkGray);
        horizontalLine.setLocation(6, 73);
        horizontalLine.setSize(138, 1);
        horizontalLine.setTabIndex(134);
        horizontalLine.setTabStop(false);

        totalLabel.setAutoSize(true);
        totalLabel.setFont(FontCollection.bold825);
        totalLabel.setLocation(8, 79);
        totalLabel.setSize(34, 13);
        totalLabel.setTabIndex(7);
        totalLabel.setText("Total:");

        totalLabelValue.setLocation(104, 79);
        totalLabelValue.setSize(39, 13);
        totalLabelValue.setTabIndex(52);
        //totalLabelValue.setText("888 cr.");
        totalLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setVisible(false);
        closeButton.setTabStop(false);

        controls.addAll(mercenariesLabel, mercenariesLabelValue, insuranceLabel, insuranceLabelValue, 
                interestLabel, interestLabelValue, wormholeTaxLabel, wormholeTaxLabelValue, horizontalLine, 
                totalLabel, totalLabelValue, closeButton);

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
    }
}

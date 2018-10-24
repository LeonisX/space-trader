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
import spacetrader.game.Functions;
import spacetrader.game.GlobalAssets;
import spacetrader.game.HighScoreRecord;
import spacetrader.game.Strings;
import spacetrader.gui.debug.Launcher;
import spacetrader.util.ReflectionUtils;

public class FormViewHighScores extends SpaceTraderForm {

    private Button closeButton = new Button();
    private Label rankLabel1 = new Label();
    private Label rankLabel2 = new Label();
    private Label rankLabel3 = new Label();
    private Label scoreLabelValue1 = new Label();
    private Label scoreLabelValue2 = new Label();
    private Label scoreLabelValue3 = new Label();
    private Label nameLabelValue1 = new Label();
    private Label nameLabelValue2 = new Label();
    private Label nameLabelValue3 = new Label();
    private Label statusLabelValue1 = new Label();
    private Label statusLabelValue2 = new Label();
    private Label statusLabelValue3 = new Label();

    public static void main(String[] args) {
        Launcher.runForm(new FormViewHighScores());
    }

    public FormViewHighScores() {
        initializeComponent();

        Label[] lblName = new Label[]{nameLabelValue1, nameLabelValue2, nameLabelValue3};
        Label[] lblScore = new Label[]{scoreLabelValue1, scoreLabelValue2, scoreLabelValue3};
        Label[] lblStatus = new Label[]{statusLabelValue1, statusLabelValue2, statusLabelValue3};

        HighScoreRecord[] highScores = Functions.getHighScores();
        for (int i = highScores.length - 1; i >= 0 && highScores[i] != null; i--) {
            lblName[2 - i].setText(highScores[i].getName());
            lblScore[2 - i].setText(Functions.formatNumber(highScores[i].getScore() / 10) + "." + highScores[i].getScore() % 10);
            lblStatus[2 - i].setText(Functions.stringVars(Strings.HighScoreStatus, new String[]
                    {
                            Strings.GameCompletionTypes[highScores[i].getType().castToInt()],
                            Functions.multiples(highScores[i].getDays(), Strings.TimeUnit),
                            Functions.multiples(highScores[i].getWorth(), Strings.MoneyUnit),
                            Strings.DifficultyLevels[highScores[i].getDifficulty().castToInt()].toLowerCase()
                    }));

            lblScore[2 - i].setVisible(true);
            lblStatus[2 - i].setVisible(true);
        }
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewHighScores");
        setText("High Scores");
        setAutoScaleBaseSize(5, 13);
        setClientSize(218, 191);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        rankLabel1.setAutoSize(true);
        rankLabel1.setLocation(8, 8);
        rankLabel1.setSize(14, 13);
        rankLabel1.setTabIndex(33);
        rankLabel1.setText("1.");
        rankLabel1.setTextAlign(ContentAlignment.TOP_RIGHT);

        nameLabelValue1.setLocation(24, 8);
        nameLabelValue1.setSize(144, 13);
        nameLabelValue1.setTabIndex(39);
        nameLabelValue1.setText("Empty");

        scoreLabelValue1.setLocation(168, 8);
        scoreLabelValue1.setSize(43, 13);
        scoreLabelValue1.setTabIndex(36);
        scoreLabelValue1.setText("888.8%");
        scoreLabelValue1.setTextAlign(ContentAlignment.TOP_RIGHT);
        scoreLabelValue1.setVisible(false);

        statusLabelValue1.setLocation(24, 24);
        statusLabelValue1.setSize(200, 26);
        statusLabelValue1.setTabIndex(42);
        statusLabelValue1.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        statusLabelValue1.setVisible(false);

        rankLabel2.setAutoSize(true);
        rankLabel2.setLocation(8, 72);
        rankLabel2.setSize(14, 13);
        rankLabel2.setTabIndex(35);
        rankLabel2.setText("2.");
        rankLabel2.setTextAlign(ContentAlignment.TOP_RIGHT);

        nameLabelValue2.setLocation(24, 72);
        nameLabelValue2.setSize(144, 13);
        nameLabelValue2.setTabIndex(40);
        nameLabelValue2.setText("Empty");

        scoreLabelValue2.setLocation(168, 72);
        scoreLabelValue2.setSize(43, 13);
        scoreLabelValue2.setTabIndex(37);
        scoreLabelValue2.setText("888.8%");
        scoreLabelValue2.setTextAlign(ContentAlignment.TOP_RIGHT);
        scoreLabelValue2.setVisible(false);

        statusLabelValue2.setLocation(24, 88);
        statusLabelValue2.setSize(200, 26);
        statusLabelValue2.setTabIndex(43);
        statusLabelValue2.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        statusLabelValue2.setVisible(false);

        rankLabel3.setAutoSize(true);
        rankLabel3.setLocation(8, 136);
        rankLabel3.setSize(14, 13);
        rankLabel3.setTabIndex(34);
        rankLabel3.setText("3.");
        rankLabel3.setTextAlign(ContentAlignment.TOP_RIGHT);

        nameLabelValue3.setLocation(24, 136);
        nameLabelValue3.setSize(144, 13);
        nameLabelValue3.setTabIndex(41);
        nameLabelValue3.setText("Empty");

        scoreLabelValue3.setLocation(168, 136);
        scoreLabelValue3.setSize(43, 13);
        scoreLabelValue3.setTabIndex(38);
        scoreLabelValue3.setText("888.8%");
        scoreLabelValue3.setTextAlign(ContentAlignment.TOP_RIGHT);
        scoreLabelValue3.setVisible(false);
        
        statusLabelValue3.setLocation(24, 152);
        statusLabelValue3.setSize(200, 26);
        statusLabelValue3.setTabIndex(44);
        statusLabelValue3.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        statusLabelValue3.setVisible(false);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        closeButton.setText("X");

        controls.addAll(rankLabel1, nameLabelValue1, scoreLabelValue1, statusLabelValue1, rankLabel2,
                nameLabelValue2, scoreLabelValue2, statusLabelValue2, rankLabel3, nameLabelValue3,
                scoreLabelValue3, statusLabelValue3, closeButton);
        
        ReflectionUtils.loadControlsDimensions(asSwingObject(), getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(asSwingObject(), getName(), GlobalAssets.getStrings());
    }
}

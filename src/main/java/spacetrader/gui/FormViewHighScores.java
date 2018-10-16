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
import spacetrader.game.Functions;
import spacetrader.game.HighScoreRecord;
import spacetrader.game.Strings;

import java.util.Arrays;

public class FormViewHighScores extends SpaceTraderForm {
    //#region Control Declarations

    private final Container components = null;
    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.Label lblRank0;
    private spacetrader.controls.Label lblRank2;
    private spacetrader.controls.Label lblRank1;
    private spacetrader.controls.Label lblScore0;
    private spacetrader.controls.Label lblScore1;
    private spacetrader.controls.Label lblScore2;
    private spacetrader.controls.Label lblName0;
    private spacetrader.controls.Label lblName1;
    private spacetrader.controls.Label lblName2;
    private spacetrader.controls.Label lblStatus0;
    private spacetrader.controls.Label lblStatus1;
    private spacetrader.controls.Label lblStatus2;

    //#endregion

    //#region Methods

    public FormViewHighScores() {
        initializeComponent();

        Label[] lblName = new Label[]{lblName0, lblName1, lblName2};
        Label[] lblScore = new Label[]{lblScore0, lblScore1, lblScore2};
        Label[] lblStatus = new Label[]{lblStatus0, lblStatus1, lblStatus2};

        HighScoreRecord[] highScores = Functions.GetHighScores();
        for (int i = highScores.length - 1; i >= 0 && highScores[i] != null; i--) {
            lblName[2 - i].setText(highScores[i].Name());
            lblScore[2 - i].setText(Functions.formatNumber(highScores[i].Score() / 10) + "." + highScores[i].Score() % 10);
            lblStatus[2 - i].setText(Functions.stringVars(Strings.HighScoreStatus, new String[]
                    {
                            Strings.GameCompletionTypes[highScores[i].Type().castToInt()],
                            Functions.multiples(highScores[i].Days(), Strings.TimeUnit),
                            Functions.multiples(highScores[i].Worth(), Strings.MoneyUnit),
                            Strings.DifficultyLevels[highScores[i].Difficulty().castToInt()].toLowerCase()
                    }));

            lblScore[2 - i].setVisible(true);
            lblStatus[2 - i].setVisible(true);
        }
    }


    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        btnClose = new spacetrader.controls.Button();
        lblRank0 = new spacetrader.controls.Label();
        lblRank2 = new spacetrader.controls.Label();
        lblRank1 = new spacetrader.controls.Label();
        lblScore0 = new spacetrader.controls.Label();
        lblScore1 = new spacetrader.controls.Label();
        lblScore2 = new spacetrader.controls.Label();
        lblName0 = new spacetrader.controls.Label();
        lblName1 = new spacetrader.controls.Label();
        lblName2 = new spacetrader.controls.Label();
        lblStatus0 = new spacetrader.controls.Label();
        lblStatus1 = new spacetrader.controls.Label();
        lblStatus2 = new spacetrader.controls.Label();
        this.suspendLayout();
        //
        // btnClose
        //
        btnClose.setDialogResult(DialogResult.CANCEL);
        btnClose.setLocation(new java.awt.Point(-32, -32));
        btnClose.setName("btnClose");
        btnClose.setSize(new spacetrader.controls.Size(32, 32));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");
        //
        // lblRank0
        //
        lblRank0.setAutoSize(true);
        lblRank0.setLocation(new java.awt.Point(8, 8));
        lblRank0.setName("lblRank0");
        lblRank0.setSize(new spacetrader.controls.Size(14, 13));
        lblRank0.setTabIndex(33);
        lblRank0.setText("1.");
        lblRank0.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblRank2
        //
        lblRank2.setAutoSize(true);
        lblRank2.setLocation(new java.awt.Point(8, 136));
        lblRank2.setName("lblRank2");
        lblRank2.setSize(new spacetrader.controls.Size(14, 13));
        lblRank2.setTabIndex(34);
        lblRank2.setText("3.");
        lblRank2.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblRank1
        //
        lblRank1.setAutoSize(true);
        lblRank1.setLocation(new java.awt.Point(8, 72));
        lblRank1.setName("lblRank1");
        lblRank1.setSize(new spacetrader.controls.Size(14, 13));
        lblRank1.setTabIndex(35);
        lblRank1.setText("2.");
        lblRank1.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblScore0
        //
        lblScore0.setLocation(new java.awt.Point(168, 8));
        lblScore0.setName("lblScore0");
        lblScore0.setSize(new spacetrader.controls.Size(43, 13));
        lblScore0.setTabIndex(36);
        lblScore0.setText("888.8%");
        lblScore0.textAlign = ContentAlignment.TOP_RIGHT;
        lblScore0.setVisible(false);
        //
        // lblScore1
        //
        lblScore1.setLocation(new java.awt.Point(168, 72));
        lblScore1.setName("lblScore1");
        lblScore1.setSize(new spacetrader.controls.Size(43, 13));
        lblScore1.setTabIndex(37);
        lblScore1.setText("888.8%");
        lblScore1.textAlign = ContentAlignment.TOP_RIGHT;
        lblScore1.setVisible(false);
        //
        // lblScore2
        //
        lblScore2.setLocation(new java.awt.Point(168, 136));
        lblScore2.setName("lblScore2");
        lblScore2.setSize(new spacetrader.controls.Size(43, 13));
        lblScore2.setTabIndex(38);
        lblScore2.setText("888.8%");
        lblScore2.textAlign = ContentAlignment.TOP_RIGHT;
        lblScore2.setVisible(false);
        //
        // lblName0
        //
        lblName0.setLocation(new java.awt.Point(24, 8));
        lblName0.setName("lblName0");
        lblName0.setSize(new spacetrader.controls.Size(144, 13));
        lblName0.setTabIndex(39);
        lblName0.setText("Empty");
        //
        // lblName1
        //
        lblName1.setLocation(new java.awt.Point(24, 72));
        lblName1.setName("lblName1");
        lblName1.setSize(new spacetrader.controls.Size(144, 13));
        lblName1.setTabIndex(40);
        lblName1.setText("Empty");
        //
        // lblName2
        //
        lblName2.setLocation(new java.awt.Point(24, 136));
        lblName2.setName("lblName2");
        lblName2.setSize(new spacetrader.controls.Size(144, 13));
        lblName2.setTabIndex(41);
        lblName2.setText("Empty");
        //
        // lblStatus0
        //
        lblStatus0.setLocation(new java.awt.Point(24, 24));
        lblStatus0.setName("lblStatus0");
        lblStatus0.setSize(new spacetrader.controls.Size(200, 26));
        lblStatus0.setTabIndex(42);
        lblStatus0.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        lblStatus0.setVisible(false);
        //
        // lblStatus1
        //
        lblStatus1.setLocation(new java.awt.Point(24, 88));
        lblStatus1.setName("lblStatus1");
        lblStatus1.setSize(new spacetrader.controls.Size(200, 26));
        lblStatus1.setTabIndex(43);
        lblStatus1.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        lblStatus1.setVisible(false);
        //
        // lblStatus2
        //
        lblStatus2.setLocation(new java.awt.Point(24, 152));
        lblStatus2.setName("lblStatus2");
        lblStatus2.setSize(new spacetrader.controls.Size(200, 26));
        lblStatus2.setTabIndex(44);
        lblStatus2.setText("Claimed moon in 888,888 days, worth 8,888,888 credits on impossible level.");
        lblStatus2.setVisible(false);
        //
        // FormViewHighScores
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(btnClose);
        this.setClientSize(new spacetrader.controls.Size(218, 191));
        Controls.addAll(Arrays.asList(lblStatus2, lblStatus1, lblStatus0, lblName2, lblName1, lblName0, lblScore2,
                lblScore1, lblScore0, lblRank1, lblRank2, lblRank0, btnClose));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewHighScores");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("High Scores");
    }
    //#endregion

    //#endregion
}

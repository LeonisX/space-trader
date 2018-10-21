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
import spacetrader.game.enums.Difficulty;

import java.util.Arrays;

public class FormNewCommander extends SpaceTraderForm {
    // #region Control Declarations

    private spacetrader.controls.Label lblName;
    private spacetrader.controls.TextBox txtName;
    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.Label lblDifficulty;
    private spacetrader.controls.Label lblSkillPoints;
    private spacetrader.controls.Label lblPilot;
    private spacetrader.controls.Label lblFighter;
    private spacetrader.controls.Label lblTrader;
    private spacetrader.controls.Label lblEngineer;
    private spacetrader.controls.ComboBox selDifficulty;
    private spacetrader.controls.NumericUpDown numPilot;
    private spacetrader.controls.Button btnOk;
    private spacetrader.controls.Label lblPointsRemaining;
    private spacetrader.controls.Label lblPoints;
    private spacetrader.controls.NumericUpDown numFighter;
    private spacetrader.controls.NumericUpDown numTrader;
    private spacetrader.controls.NumericUpDown numEngineer;

    // #endregion

    // #region Methods

    public FormNewCommander() {
        initializeComponent();

        selDifficulty.setSelectedIndex(2);
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        this.lblName = new spacetrader.controls.Label();
        this.txtName = new spacetrader.controls.TextBox();
        this.btnClose = new spacetrader.controls.Button();
        this.lblDifficulty = new spacetrader.controls.Label();
        this.lblSkillPoints = new spacetrader.controls.Label();
        this.lblPilot = new spacetrader.controls.Label();
        this.lblFighter = new spacetrader.controls.Label();
        this.lblTrader = new spacetrader.controls.Label();
        this.lblEngineer = new spacetrader.controls.Label();
        this.selDifficulty = new spacetrader.controls.ComboBox();
        this.numPilot = new spacetrader.controls.NumericUpDown();
        this.numFighter = new spacetrader.controls.NumericUpDown();
        this.numTrader = new spacetrader.controls.NumericUpDown();
        this.numEngineer = new spacetrader.controls.NumericUpDown();
        this.btnOk = new spacetrader.controls.Button();
        this.lblPointsRemaining = new spacetrader.controls.Label();
        this.lblPoints = new spacetrader.controls.Label();
        ((ISupportInitialize) (this.numPilot)).beginInit();
        ((ISupportInitialize) (this.numFighter)).beginInit();
        ((ISupportInitialize) (this.numTrader)).beginInit();
        ((ISupportInitialize) (this.numEngineer)).beginInit();
        this.suspendLayout();
        //
        // lblName
        //
        this.lblName.setAutoSize(true);
        this.lblName.setLocation(new java.awt.Point(8, 8));
        this.lblName.setName("lblName");
        this.lblName.setSize(new spacetrader.controls.Size(38, 13));
        this.lblName.setTabIndex(0);
        this.lblName.setText("Name:");
        //
        // txtName
        //
        this.txtName.setLocation(new java.awt.Point(72, 5));
        this.txtName.setName("txtName");
        this.txtName.setSize(new spacetrader.controls.Size(120, 20));
        this.txtName.setTabIndex(1);
        this.txtName.setText("");
        this.txtName.setTextChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                txtName_TextChanged(sender, e);
            }
        });
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.CANCEL);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new spacetrader.controls.Size(30, 31));
        this.btnClose.setTabIndex(33);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblDifficulty
        //
        this.lblDifficulty.setAutoSize(true);
        this.lblDifficulty.setLocation(new java.awt.Point(8, 40));
        this.lblDifficulty.setName("lblDifficulty");
        this.lblDifficulty.setSize(new spacetrader.controls.Size(50, 13));
        this.lblDifficulty.setTabIndex(34);
        this.lblDifficulty.setText("Difficulty:");
        //
        // lblSkillPoints
        //
        this.lblSkillPoints.setAutoSize(true);
        this.lblSkillPoints.setLocation(new java.awt.Point(8, 72));
        this.lblSkillPoints.setName("lblSkillPoints");
        this.lblSkillPoints.setSize(new spacetrader.controls.Size(63, 13));
        this.lblSkillPoints.setTabIndex(35);
        this.lblSkillPoints.setText("Skill Points:");
        //
        // lblPilot
        //
        this.lblPilot.setAutoSize(true);
        this.lblPilot.setLocation(new java.awt.Point(16, 96));
        this.lblPilot.setName("lblPilot");
        this.lblPilot.setSize(new spacetrader.controls.Size(29, 13));
        this.lblPilot.setTabIndex(36);
        this.lblPilot.setText("Pilot:");
        //
        // lblFighter
        //
        this.lblFighter.setAutoSize(true);
        this.lblFighter.setLocation(new java.awt.Point(16, 120));
        this.lblFighter.setName("lblFighter");
        this.lblFighter.setSize(new spacetrader.controls.Size(43, 13));
        this.lblFighter.setTabIndex(37);
        this.lblFighter.setText("Fighter:");
        //
        // lblTrader
        //
        this.lblTrader.setAutoSize(true);
        this.lblTrader.setLocation(new java.awt.Point(16, 144));
        this.lblTrader.setName("lblTrader");
        this.lblTrader.setSize(new spacetrader.controls.Size(41, 13));
        this.lblTrader.setTabIndex(38);
        this.lblTrader.setText("Trader:");
        //
        // lblEngineer
        //
        this.lblEngineer.setAutoSize(true);
        this.lblEngineer.setLocation(new java.awt.Point(16, 168));
        this.lblEngineer.setName("lblEngineer");
        this.lblEngineer.setSize(new spacetrader.controls.Size(53, 13));
        this.lblEngineer.setTabIndex(39);
        this.lblEngineer.setText("Engineer:");
        //
        // selDifficulty
        //
        this.selDifficulty.dropDownStyle = spacetrader.controls.ComboBoxStyle.DropDownList;
        this.selDifficulty.items.AddRange(new Object[]{"Beginner", "Easy", "Normal", "Hard", "Impossible"});
        this.selDifficulty.setLocation(new java.awt.Point(72, 37));
        this.selDifficulty.setName("selDifficulty");
        this.selDifficulty.setSize(new spacetrader.controls.Size(120, 21));
        this.selDifficulty.setTabIndex(2);
        //
        // numPilot
        //
        this.numPilot.setLocation(new java.awt.Point(72, 94));
        this.numPilot.setMaximum(10);
        this.numPilot.setMinimum(1);
        this.numPilot.setName("numPilot");
        this.numPilot.setSize(new spacetrader.controls.Size(36, 20));
        this.numPilot.setTabIndex(3);
        this.numPilot.TextAlign = spacetrader.controls.HorizontalAlignment.Center;
        this.numPilot.setValue(1);
        this.numPilot.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numPilot.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numPilot.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numFighter
        //
        this.numFighter.setLocation(new java.awt.Point(72, 118));
        this.numFighter.setMaximum(10);
        this.numFighter.setMinimum(1);
        this.numFighter.setName("numFighter");
        this.numFighter.setSize(new spacetrader.controls.Size(36, 20));
        this.numFighter.setTabIndex(4);
        this.numFighter.TextAlign = spacetrader.controls.HorizontalAlignment.Center;
        this.numFighter.setValue(1);
        this.numFighter.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numFighter.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numFighter.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numTrader
        //
        this.numTrader.setLocation(new java.awt.Point(72, 142));
        this.numTrader.setMaximum(10);
        this.numTrader.setMinimum(1);
        this.numTrader.setName("numTrader");
        this.numTrader.setSize(new spacetrader.controls.Size(36, 20));
        this.numTrader.setTabIndex(5);
        this.numTrader.TextAlign = spacetrader.controls.HorizontalAlignment.Center;
        this.numTrader.setValue(1);
        this.numTrader.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numTrader.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numTrader.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numEngineer
        //
        this.numEngineer.setLocation(new java.awt.Point(72, 166));
        this.numEngineer.setMaximum(10);
        this.numEngineer.setMinimum(1);
        this.numEngineer.setName("numEngineer");
        this.numEngineer.setSize(new spacetrader.controls.Size(36, 20));
        this.numEngineer.setTabIndex(6);
        this.numEngineer.TextAlign = spacetrader.controls.HorizontalAlignment.Center;
        this.numEngineer.setValue(1);
        this.numEngineer.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numEngineer.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numEngineer.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // btnOk
        //
        this.btnOk.setDialogResult(DialogResult.OK);
        this.btnOk.setEnabled(false);
        this.btnOk.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        this.btnOk.setLocation(new java.awt.Point(83, 200));
        this.btnOk.setName("btnOk");
        this.btnOk.setSize(new spacetrader.controls.Size(36, 22));
        this.btnOk.setTabIndex(7);
        this.btnOk.setText("Ok");
        this.btnOk.setEnabled(false);
        //
        // lblPointsRemaining
        //
        this.lblPointsRemaining.setAutoSize(true);
        this.lblPointsRemaining.setLocation(new java.awt.Point(91, 72));
        this.lblPointsRemaining.setName("lblPointsRemaining");
        this.lblPointsRemaining.setSize(new spacetrader.controls.Size(90, 13));
        this.lblPointsRemaining.setTabIndex(40);
        this.lblPointsRemaining.setText("points remaining.");
        //
        // lblPoints
        //
        this.lblPoints.setLocation(new java.awt.Point(73, 72));
        this.lblPoints.setName("lblPoints");
        this.lblPoints.setSize(new spacetrader.controls.Size(17, 13));
        this.lblPoints.setTabIndex(41);
        this.lblPoints.setText("16");
        this.lblPoints.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // FormNewCommander
        //
        this.setAcceptButton(this.btnOk);
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new spacetrader.controls.Size(202, 231));
        this.controls.addAll(Arrays.asList(this.lblPoints, this.lblPointsRemaining, this.lblEngineer, this.lblTrader,
                this.lblFighter, this.lblPilot, this.lblSkillPoints, this.lblDifficulty, this.lblName, this.btnOk,
                this.numEngineer, this.numTrader, this.numFighter, this.numPilot, this.selDifficulty, this.btnClose,
                this.txtName));
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FIXED_DIALOG);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormNewCommander");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("New Commander");
        ((ISupportInitialize) (this.numPilot)).endInit();
        ((ISupportInitialize) (this.numFighter)).endInit();
        ((ISupportInitialize) (this.numTrader)).endInit();
        ((ISupportInitialize) (this.numEngineer)).endInit();

    }

    // #endregion

    private void UpdateOkEnabled() {
        btnOk.setEnabled(lblPoints.getText().equals("0") && txtName.getText().length() > 0);
    }

    // #endregion

    // #region Event Handlers

    private void num_ValueChanged(Object sender, EventArgs e) {
        int points = 20 - (Pilot() + Fighter() + Trader() + Engineer());
        lblPoints.setText("" + points);
        numPilot.setMaximum(Math.min(10, Pilot() + points));
        numFighter.setMaximum(Math.min(10, Fighter() + points));
        numTrader.setMaximum(Math.min(10, Trader() + points));
        numEngineer.setMaximum(Math.min(10, Engineer() + points));

        UpdateOkEnabled();
    }

    private void num_ValueEnter(Object sender, EventArgs e) {
        ((NumericUpDown) sender).Select(0, ("" + ((NumericUpDown) sender).getValue()).length());
    }

    private void txtName_TextChanged(Object sender, EventArgs e) {
        UpdateOkEnabled();
    }

    // #endregion

    // #region Properties


    public String CommanderName() {
        return txtName.getText();
    }


    public Difficulty Difficulty() {
        return Difficulty.fromInt(selDifficulty.getSelectedIndex());
    }

    public int Pilot() {
        return numPilot.getValue();
    }


    public int Fighter() {
        return numFighter.getValue();
    }


    public int Trader() {
        return numTrader.getValue();
    }

    public int Engineer() {
        return numEngineer.getValue();
    }

    // #endregion
}

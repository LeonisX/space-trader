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

import jwinforms.*;
import spacetrader.enums.Difficulty;

import java.util.Arrays;

public class FormNewCommander extends SpaceTraderForm {
    // #region Control Declarations

    private jwinforms.Label lblName;
    private jwinforms.TextBox txtName;
    private jwinforms.Button btnClose;
    private jwinforms.Label lblDifficulty;
    private jwinforms.Label lblSkillPoints;
    private jwinforms.Label lblPilot;
    private jwinforms.Label lblFighter;
    private jwinforms.Label lblTrader;
    private jwinforms.Label lblEngineer;
    private jwinforms.ComboBox selDifficulty;
    private jwinforms.NumericUpDown numPilot;
    private jwinforms.Button btnOk;
    private jwinforms.Label lblPointsRemaining;
    private jwinforms.Label lblPoints;
    private jwinforms.NumericUpDown numFighter;
    private jwinforms.NumericUpDown numTrader;
    private jwinforms.NumericUpDown numEngineer;

    // #endregion

    // #region Methods

    public FormNewCommander() {
        InitializeComponent();

        selDifficulty.setSelectedIndex(2);
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void InitializeComponent() {
        this.lblName = new jwinforms.Label();
        this.txtName = new jwinforms.TextBox();
        this.btnClose = new jwinforms.Button();
        this.lblDifficulty = new jwinforms.Label();
        this.lblSkillPoints = new jwinforms.Label();
        this.lblPilot = new jwinforms.Label();
        this.lblFighter = new jwinforms.Label();
        this.lblTrader = new jwinforms.Label();
        this.lblEngineer = new jwinforms.Label();
        this.selDifficulty = new jwinforms.ComboBox();
        this.numPilot = new jwinforms.NumericUpDown();
        this.numFighter = new jwinforms.NumericUpDown();
        this.numTrader = new jwinforms.NumericUpDown();
        this.numEngineer = new jwinforms.NumericUpDown();
        this.btnOk = new jwinforms.Button();
        this.lblPointsRemaining = new jwinforms.Label();
        this.lblPoints = new jwinforms.Label();
        ((ISupportInitialize) (this.numPilot)).beginInit();
        ((ISupportInitialize) (this.numFighter)).beginInit();
        ((ISupportInitialize) (this.numTrader)).beginInit();
        ((ISupportInitialize) (this.numEngineer)).beginInit();
        this.SuspendLayout();
        //
        // lblName
        //
        this.lblName.setAutoSize(true);
        this.lblName.setLocation(new java.awt.Point(8, 8));
        this.lblName.setName("lblName");
        this.lblName.setSize(new jwinforms.Size(38, 13));
        this.lblName.setTabIndex(0);
        this.lblName.setText("Name:");
        //
        // txtName
        //
        this.txtName.setLocation(new java.awt.Point(72, 5));
        this.txtName.setName("txtName");
        this.txtName.setSize(new jwinforms.Size(120, 20));
        this.txtName.setTabIndex(1);
        this.txtName.setText("");
        this.txtName.setTextChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                txtName_TextChanged(sender, e);
            }
        });
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.Cancel);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new jwinforms.Size(30, 31));
        this.btnClose.setTabIndex(33);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblDifficulty
        //
        this.lblDifficulty.setAutoSize(true);
        this.lblDifficulty.setLocation(new java.awt.Point(8, 40));
        this.lblDifficulty.setName("lblDifficulty");
        this.lblDifficulty.setSize(new jwinforms.Size(50, 13));
        this.lblDifficulty.setTabIndex(34);
        this.lblDifficulty.setText("Difficulty:");
        //
        // lblSkillPoints
        //
        this.lblSkillPoints.setAutoSize(true);
        this.lblSkillPoints.setLocation(new java.awt.Point(8, 72));
        this.lblSkillPoints.setName("lblSkillPoints");
        this.lblSkillPoints.setSize(new jwinforms.Size(63, 13));
        this.lblSkillPoints.setTabIndex(35);
        this.lblSkillPoints.setText("Skill Points:");
        //
        // lblPilot
        //
        this.lblPilot.setAutoSize(true);
        this.lblPilot.setLocation(new java.awt.Point(16, 96));
        this.lblPilot.setName("lblPilot");
        this.lblPilot.setSize(new jwinforms.Size(29, 13));
        this.lblPilot.setTabIndex(36);
        this.lblPilot.setText("Pilot:");
        //
        // lblFighter
        //
        this.lblFighter.setAutoSize(true);
        this.lblFighter.setLocation(new java.awt.Point(16, 120));
        this.lblFighter.setName("lblFighter");
        this.lblFighter.setSize(new jwinforms.Size(43, 13));
        this.lblFighter.setTabIndex(37);
        this.lblFighter.setText("Fighter:");
        //
        // lblTrader
        //
        this.lblTrader.setAutoSize(true);
        this.lblTrader.setLocation(new java.awt.Point(16, 144));
        this.lblTrader.setName("lblTrader");
        this.lblTrader.setSize(new jwinforms.Size(41, 13));
        this.lblTrader.setTabIndex(38);
        this.lblTrader.setText("Trader:");
        //
        // lblEngineer
        //
        this.lblEngineer.setAutoSize(true);
        this.lblEngineer.setLocation(new java.awt.Point(16, 168));
        this.lblEngineer.setName("lblEngineer");
        this.lblEngineer.setSize(new jwinforms.Size(53, 13));
        this.lblEngineer.setTabIndex(39);
        this.lblEngineer.setText("Engineer:");
        //
        // selDifficulty
        //
        this.selDifficulty.DropDownStyle = jwinforms.ComboBoxStyle.DropDownList;
        this.selDifficulty.Items.AddRange(new Object[]{"Beginner", "Easy", "Normal", "Hard", "Impossible"});
        this.selDifficulty.setLocation(new java.awt.Point(72, 37));
        this.selDifficulty.setName("selDifficulty");
        this.selDifficulty.setSize(new jwinforms.Size(120, 21));
        this.selDifficulty.setTabIndex(2);
        //
        // numPilot
        //
        this.numPilot.setLocation(new java.awt.Point(72, 94));
        this.numPilot.setMaximum(10);
        this.numPilot.setMinimum(1);
        this.numPilot.setName("numPilot");
        this.numPilot.setSize(new jwinforms.Size(36, 20));
        this.numPilot.setTabIndex(3);
        this.numPilot.TextAlign = jwinforms.HorizontalAlignment.Center;
        this.numPilot.setValue(1);
        this.numPilot.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numPilot.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numPilot.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
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
        this.numFighter.setSize(new jwinforms.Size(36, 20));
        this.numFighter.setTabIndex(4);
        this.numFighter.TextAlign = jwinforms.HorizontalAlignment.Center;
        this.numFighter.setValue(1);
        this.numFighter.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numFighter.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numFighter.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
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
        this.numTrader.setSize(new jwinforms.Size(36, 20));
        this.numTrader.setTabIndex(5);
        this.numTrader.TextAlign = jwinforms.HorizontalAlignment.Center;
        this.numTrader.setValue(1);
        this.numTrader.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numTrader.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numTrader.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
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
        this.numEngineer.setSize(new jwinforms.Size(36, 20));
        this.numEngineer.setTabIndex(6);
        this.numEngineer.TextAlign = jwinforms.HorizontalAlignment.Center;
        this.numEngineer.setValue(1);
        this.numEngineer.setEnter(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        this.numEngineer.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        this.numEngineer.setLeave(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // btnOk
        //
        this.btnOk.setDialogResult(DialogResult.OK);
        this.btnOk.setEnabled(false);
        this.btnOk.setFlatStyle(jwinforms.FlatStyle.Flat);
        this.btnOk.setLocation(new java.awt.Point(83, 200));
        this.btnOk.setName("btnOk");
        this.btnOk.setSize(new jwinforms.Size(36, 22));
        this.btnOk.setTabIndex(7);
        this.btnOk.setText("Ok");
        this.btnOk.setEnabled(false);
        //
        // lblPointsRemaining
        //
        this.lblPointsRemaining.setAutoSize(true);
        this.lblPointsRemaining.setLocation(new java.awt.Point(91, 72));
        this.lblPointsRemaining.setName("lblPointsRemaining");
        this.lblPointsRemaining.setSize(new jwinforms.Size(90, 13));
        this.lblPointsRemaining.setTabIndex(40);
        this.lblPointsRemaining.setText("points remaining.");
        //
        // lblPoints
        //
        this.lblPoints.setLocation(new java.awt.Point(73, 72));
        this.lblPoints.setName("lblPoints");
        this.lblPoints.setSize(new jwinforms.Size(17, 13));
        this.lblPoints.setTabIndex(41);
        this.lblPoints.setText("16");
        this.lblPoints.TextAlign = ContentAlignment.TopRight;
        //
        // FormNewCommander
        //
        this.setAcceptButton(this.btnOk);
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new jwinforms.Size(202, 231));
        this.Controls.addAll(Arrays.asList(this.lblPoints, this.lblPointsRemaining, this.lblEngineer, this.lblTrader,
                this.lblFighter, this.lblPilot, this.lblSkillPoints, this.lblDifficulty, this.lblName, this.btnOk,
                this.numEngineer, this.numTrader, this.numFighter, this.numPilot, this.selDifficulty, this.btnClose,
                this.txtName));
        this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormNewCommander");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
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

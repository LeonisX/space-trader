/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson,
 * Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
// using System;
// using System.Drawing;
// using System.Collections;
// using System.ComponentModel;
// using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.guifacade.Facaded;

import java.util.Arrays;

@Facaded
public class FormJettison extends SpaceTraderForm {
    //#region Control Declarations

    private final Container components = null;
    private final Button[] btnJettisonQty;
    private final Button[] btnJettisonAll;
    private final Game game = Game.getCurrentGame();
    private spacetrader.controls.Button btnJettisonAll9;
    private spacetrader.controls.Button btnJettisonQty9;
    private spacetrader.controls.Button btnJettisonAll8;
    private spacetrader.controls.Button btnJettisonQty8;
    private spacetrader.controls.Button btnJettisonAll7;
    private spacetrader.controls.Button btnJettisonQty7;
    private spacetrader.controls.Button btnJettisonAll6;
    private spacetrader.controls.Button btnJettisonQty6;
    private spacetrader.controls.Button btnJettisonAll5;
    private spacetrader.controls.Button btnJettisonQty5;
    private spacetrader.controls.Button btnJettisonAll4;
    private spacetrader.controls.Button btnJettisonQty4;
    private spacetrader.controls.Button btnJettisonAll3;
    private spacetrader.controls.Button btnJettisonQty3;
    private spacetrader.controls.Button btnJettisonAll2;
    private spacetrader.controls.Button btnJettisonQty2;
    private spacetrader.controls.Button btnJettisonAll1;
    private spacetrader.controls.Button btnJettisonQty1;
    private spacetrader.controls.Button btnJettisonAll0;
    private spacetrader.controls.Button btnJettisonQty0;
    private spacetrader.controls.Label lblTradeCmdty9;
    private spacetrader.controls.Label lblTradeCmdty8;
    private spacetrader.controls.Label lblTradeCmdty2;
    private spacetrader.controls.Label lblTradeCmdty0;
    private spacetrader.controls.Label lblTradeCmdty1;
    private spacetrader.controls.Label lblTradeCmdty6;
    private spacetrader.controls.Label lblTradeCmdty5;
    private spacetrader.controls.Label lblTradeCmdty4;
    private spacetrader.controls.Label lblTradeCmdty3;
    private spacetrader.controls.Label lblTradeCmdty7;
    private spacetrader.controls.Label lblBaysLabel;
    private spacetrader.controls.Label lblBays;

    //#endregion

    //#region Member Declarations
    private spacetrader.controls.Button btnDone;

    //#endregion

    //#region Methods

    public FormJettison() {
        initializeComponent();

        //#region Arrays of Cargo spacetrader.controls
        btnJettisonQty = new Button[]{btnJettisonQty0, btnJettisonQty1, btnJettisonQty2, btnJettisonQty3,
                btnJettisonQty4, btnJettisonQty5, btnJettisonQty6, btnJettisonQty7, btnJettisonQty8, btnJettisonQty9};

        btnJettisonAll = new Button[]{btnJettisonAll0, btnJettisonAll1, btnJettisonAll2, btnJettisonAll3,
                btnJettisonAll4, btnJettisonAll5, btnJettisonAll6, btnJettisonAll7, btnJettisonAll8, btnJettisonAll9};
        //#endregion

        UpdateAll();
    }

    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        btnJettisonAll9 = new spacetrader.controls.Button();
        btnJettisonQty9 = new spacetrader.controls.Button();
        btnJettisonAll8 = new spacetrader.controls.Button();
        btnJettisonQty8 = new spacetrader.controls.Button();
        btnJettisonAll7 = new spacetrader.controls.Button();
        btnJettisonQty7 = new spacetrader.controls.Button();
        btnJettisonAll6 = new spacetrader.controls.Button();
        btnJettisonQty6 = new spacetrader.controls.Button();
        btnJettisonAll5 = new spacetrader.controls.Button();
        btnJettisonQty5 = new spacetrader.controls.Button();
        btnJettisonAll4 = new spacetrader.controls.Button();
        btnJettisonQty4 = new spacetrader.controls.Button();
        btnJettisonAll3 = new spacetrader.controls.Button();
        btnJettisonQty3 = new spacetrader.controls.Button();
        btnJettisonAll2 = new spacetrader.controls.Button();
        btnJettisonQty2 = new spacetrader.controls.Button();
        btnJettisonAll1 = new spacetrader.controls.Button();
        btnJettisonQty1 = new spacetrader.controls.Button();
        btnJettisonAll0 = new spacetrader.controls.Button();
        btnJettisonQty0 = new spacetrader.controls.Button();
        lblTradeCmdty9 = new spacetrader.controls.Label();
        lblTradeCmdty8 = new spacetrader.controls.Label();
        lblTradeCmdty2 = new spacetrader.controls.Label();
        lblTradeCmdty0 = new spacetrader.controls.Label();
        lblTradeCmdty1 = new spacetrader.controls.Label();
        lblTradeCmdty6 = new spacetrader.controls.Label();
        lblTradeCmdty5 = new spacetrader.controls.Label();
        lblTradeCmdty4 = new spacetrader.controls.Label();
        lblTradeCmdty3 = new spacetrader.controls.Label();
        lblTradeCmdty7 = new spacetrader.controls.Label();
        lblBaysLabel = new spacetrader.controls.Label();
        lblBays = new spacetrader.controls.Label();
        btnDone = new spacetrader.controls.Button();
        this.suspendLayout();
        //
        // btnJettisonAll9
        //
        btnJettisonAll9.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll9.setLocation(new java.awt.Point(100, 220));
        btnJettisonAll9.setName("btnJettisonAll9");
        btnJettisonAll9.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll9.setTabIndex(141);
        btnJettisonAll9.setText("All");
        btnJettisonAll9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty9
        //
        btnJettisonQty9.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty9.setLocation(new java.awt.Point(68, 220));
        btnJettisonQty9.setName("btnJettisonQty9");
        btnJettisonQty9.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty9.setTabIndex(140);
        btnJettisonQty9.setText("88");
        btnJettisonQty9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll8
        //
        btnJettisonAll8.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll8.setLocation(new java.awt.Point(100, 196));
        btnJettisonAll8.setName("btnJettisonAll8");
        btnJettisonAll8.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll8.setTabIndex(139);
        btnJettisonAll8.setText("All");
        btnJettisonAll8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty8
        //
        btnJettisonQty8.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty8.setLocation(new java.awt.Point(68, 196));
        btnJettisonQty8.setName("btnJettisonQty8");
        btnJettisonQty8.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty8.setTabIndex(138);
        btnJettisonQty8.setText("88");
        btnJettisonQty8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll7
        //
        btnJettisonAll7.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll7.setLocation(new java.awt.Point(100, 172));
        btnJettisonAll7.setName("btnJettisonAll7");
        btnJettisonAll7.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll7.setTabIndex(137);
        btnJettisonAll7.setText("All");
        btnJettisonAll7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty7
        //
        btnJettisonQty7.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty7.setLocation(new java.awt.Point(68, 172));
        btnJettisonQty7.setName("btnJettisonQty7");
        btnJettisonQty7.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty7.setTabIndex(136);
        btnJettisonQty7.setText("88");
        btnJettisonQty7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll6
        //
        btnJettisonAll6.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll6.setLocation(new java.awt.Point(100, 148));
        btnJettisonAll6.setName("btnJettisonAll6");
        btnJettisonAll6.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll6.setTabIndex(135);
        btnJettisonAll6.setText("All");
        btnJettisonAll6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty6
        //
        btnJettisonQty6.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty6.setLocation(new java.awt.Point(68, 148));
        btnJettisonQty6.setName("btnJettisonQty6");
        btnJettisonQty6.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty6.setTabIndex(134);
        btnJettisonQty6.setText("88");
        btnJettisonQty6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll5
        //
        btnJettisonAll5.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll5.setLocation(new java.awt.Point(100, 124));
        btnJettisonAll5.setName("btnJettisonAll5");
        btnJettisonAll5.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll5.setTabIndex(133);
        btnJettisonAll5.setText("All");
        btnJettisonAll5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty5
        //
        btnJettisonQty5.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty5.setLocation(new java.awt.Point(68, 124));
        btnJettisonQty5.setName("btnJettisonQty5");
        btnJettisonQty5.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty5.setTabIndex(132);
        btnJettisonQty5.setText("88");
        btnJettisonQty5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll4
        //
        btnJettisonAll4.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll4.setLocation(new java.awt.Point(100, 100));
        btnJettisonAll4.setName("btnJettisonAll4");
        btnJettisonAll4.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll4.setTabIndex(131);
        btnJettisonAll4.setText("All");
        btnJettisonAll4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty4
        //
        btnJettisonQty4.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty4.setLocation(new java.awt.Point(68, 100));
        btnJettisonQty4.setName("btnJettisonQty4");
        btnJettisonQty4.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty4.setTabIndex(130);
        btnJettisonQty4.setText("88");
        btnJettisonQty4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll3
        //
        btnJettisonAll3.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll3.setLocation(new java.awt.Point(100, 76));
        btnJettisonAll3.setName("btnJettisonAll3");
        btnJettisonAll3.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll3.setTabIndex(129);
        btnJettisonAll3.setText("All");
        btnJettisonAll3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty3
        //
        btnJettisonQty3.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty3.setLocation(new java.awt.Point(68, 76));
        btnJettisonQty3.setName("btnJettisonQty3");
        btnJettisonQty3.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty3.setTabIndex(128);
        btnJettisonQty3.setText("88");
        btnJettisonQty3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll2
        //
        btnJettisonAll2.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll2.setLocation(new java.awt.Point(100, 52));
        btnJettisonAll2.setName("btnJettisonAll2");
        btnJettisonAll2.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll2.setTabIndex(127);
        btnJettisonAll2.setText("All");
        btnJettisonAll2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty2
        //
        btnJettisonQty2.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty2.setLocation(new java.awt.Point(68, 52));
        btnJettisonQty2.setName("btnJettisonQty2");
        btnJettisonQty2.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty2.setTabIndex(126);
        btnJettisonQty2.setText("88");
        btnJettisonQty2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll1
        //
        btnJettisonAll1.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll1.setLocation(new java.awt.Point(100, 28));
        btnJettisonAll1.setName("btnJettisonAll1");
        btnJettisonAll1.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll1.setTabIndex(125);
        btnJettisonAll1.setText("All");
        btnJettisonAll1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty1
        //
        btnJettisonQty1.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty1.setLocation(new java.awt.Point(68, 28));
        btnJettisonQty1.setName("btnJettisonQty1");
        btnJettisonQty1.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty1.setTabIndex(124);
        btnJettisonQty1.setText("88");
        btnJettisonQty1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonAll0
        //
        btnJettisonAll0.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonAll0.setLocation(new java.awt.Point(100, 4));
        btnJettisonAll0.setName("btnJettisonAll0");
        btnJettisonAll0.setSize(new spacetrader.controls.Size(32, 22));
        btnJettisonAll0.setTabIndex(123);
        btnJettisonAll0.setText("All");
        btnJettisonAll0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // btnJettisonQty0
        //
        btnJettisonQty0.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettisonQty0.setLocation(new java.awt.Point(68, 4));
        btnJettisonQty0.setName("btnJettisonQty0");
        btnJettisonQty0.setSize(new spacetrader.controls.Size(28, 22));
        btnJettisonQty0.setTabIndex(122);
        btnJettisonQty0.setText("88");
        btnJettisonQty0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // lblTradeCmdty9
        //
        lblTradeCmdty9.setAutoSize(true);
        lblTradeCmdty9.setLocation(new java.awt.Point(8, 224));
        lblTradeCmdty9.setName("lblTradeCmdty9");
        lblTradeCmdty9.setSize(new spacetrader.controls.Size(40, 13));
        lblTradeCmdty9.setTabIndex(151);
        lblTradeCmdty9.setText("Robots");
        //
        // lblTradeCmdty8
        //
        lblTradeCmdty8.setAutoSize(true);
        lblTradeCmdty8.setLocation(new java.awt.Point(8, 200));
        lblTradeCmdty8.setName("lblTradeCmdty8");
        lblTradeCmdty8.setSize(new spacetrader.controls.Size(51, 13));
        lblTradeCmdty8.setTabIndex(150);
        lblTradeCmdty8.setText("Narcotics");
        //
        // lblTradeCmdty2
        //
        lblTradeCmdty2.setAutoSize(true);
        lblTradeCmdty2.setLocation(new java.awt.Point(8, 56));
        lblTradeCmdty2.setName("lblTradeCmdty2");
        lblTradeCmdty2.setSize(new spacetrader.controls.Size(30, 13));
        lblTradeCmdty2.setTabIndex(149);
        lblTradeCmdty2.setText("Food");
        //
        // lblTradeCmdty0
        //
        lblTradeCmdty0.setAutoSize(true);
        lblTradeCmdty0.setLocation(new java.awt.Point(8, 8));
        lblTradeCmdty0.setName("lblTradeCmdty0");
        lblTradeCmdty0.setSize(new spacetrader.controls.Size(34, 13));
        lblTradeCmdty0.setTabIndex(148);
        lblTradeCmdty0.setText("Water");
        //
        // lblTradeCmdty1
        //
        lblTradeCmdty1.setAutoSize(true);
        lblTradeCmdty1.setLocation(new java.awt.Point(8, 32));
        lblTradeCmdty1.setName("lblTradeCmdty1");
        lblTradeCmdty1.setSize(new spacetrader.controls.Size(27, 13));
        lblTradeCmdty1.setTabIndex(147);
        lblTradeCmdty1.setText("Furs");
        //
        // lblTradeCmdty6
        //
        lblTradeCmdty6.setAutoSize(true);
        lblTradeCmdty6.setLocation(new java.awt.Point(8, 152));
        lblTradeCmdty6.setName("lblTradeCmdty6");
        lblTradeCmdty6.setSize(new spacetrader.controls.Size(50, 13));
        lblTradeCmdty6.setTabIndex(146);
        lblTradeCmdty6.setText("Medicine");
        //
        // lblTradeCmdty5
        //
        lblTradeCmdty5.setAutoSize(true);
        lblTradeCmdty5.setLocation(new java.awt.Point(8, 128));
        lblTradeCmdty5.setName("lblTradeCmdty5");
        lblTradeCmdty5.setSize(new spacetrader.controls.Size(49, 13));
        lblTradeCmdty5.setTabIndex(145);
        lblTradeCmdty5.setText("Firearms");
        //
        // lblTradeCmdty4
        //
        lblTradeCmdty4.setAutoSize(true);
        lblTradeCmdty4.setLocation(new java.awt.Point(8, 104));
        lblTradeCmdty4.setName("lblTradeCmdty4");
        lblTradeCmdty4.setSize(new spacetrader.controls.Size(41, 13));
        lblTradeCmdty4.setTabIndex(144);
        lblTradeCmdty4.setText("Games");
        //
        // lblTradeCmdty3
        //
        lblTradeCmdty3.setAutoSize(true);
        lblTradeCmdty3.setLocation(new java.awt.Point(8, 80));
        lblTradeCmdty3.setName("lblTradeCmdty3");
        lblTradeCmdty3.setSize(new spacetrader.controls.Size(23, 13));
        lblTradeCmdty3.setTabIndex(143);
        lblTradeCmdty3.setText("Ore");
        //
        // lblTradeCmdty7
        //
        lblTradeCmdty7.setAutoSize(true);
        lblTradeCmdty7.setLocation(new java.awt.Point(8, 176));
        lblTradeCmdty7.setName("lblTradeCmdty7");
        lblTradeCmdty7.setSize(new spacetrader.controls.Size(53, 13));
        lblTradeCmdty7.setTabIndex(142);
        lblTradeCmdty7.setText("Machines");
        //
        // lblBaysLabel
        //
        lblBaysLabel.setAutoSize(true);
        lblBaysLabel.setLocation(new java.awt.Point(144, 8));
        lblBaysLabel.setName("lblBaysLabel");
        lblBaysLabel.setSize(new spacetrader.controls.Size(33, 13));
        lblBaysLabel.setTabIndex(152);
        lblBaysLabel.setText("Bays:");
        //
        // lblBays
        //
        lblBays.setLocation(new java.awt.Point(176, 8));
        lblBays.setName("lblBays");
        lblBays.setSize(new spacetrader.controls.Size(33, 13));
        lblBays.setTabIndex(153);
        lblBays.setText("88/88");
        //
        // btnDone
        //
        btnDone.setDialogResult(DialogResult.CANCEL);
        btnDone.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnDone.setLocation(new java.awt.Point(87, 252));
        btnDone.setName("btnDone");
        btnDone.setSize(new spacetrader.controls.Size(44, 22));
        btnDone.setTabIndex(154);
        btnDone.setText("Done");
        //
        // FormJettison
        //
        this.setAcceptButton(btnDone);
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(btnDone);
        this.setClientSize(new spacetrader.controls.Size(218, 283));
        Controls.addAll(Arrays.asList(btnDone, lblBays, lblBaysLabel, lblTradeCmdty9, lblTradeCmdty8, lblTradeCmdty2,
                lblTradeCmdty0, lblTradeCmdty1, lblTradeCmdty6, lblTradeCmdty5, lblTradeCmdty4, lblTradeCmdty3,
                lblTradeCmdty7, btnJettisonAll9, btnJettisonQty9, btnJettisonAll8, btnJettisonQty8, btnJettisonAll7,
                btnJettisonQty7, btnJettisonAll6, btnJettisonQty6, btnJettisonAll5, btnJettisonQty5, btnJettisonAll4,
                btnJettisonQty4, btnJettisonAll3, btnJettisonQty3, btnJettisonAll2, btnJettisonQty2, btnJettisonAll1,
                btnJettisonQty1, btnJettisonAll0, btnJettisonQty0));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormJettison");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Jettison Cargo");
    }

    //#endregion

    private void Jettison(int tradeItem, boolean all) {
        game.CargoJettison(tradeItem, all);
        UpdateAll();
    }

    private void UpdateAll() {
        Ship ship = game.getCommander().getShip();

        for (int i = 0; i < btnJettisonQty.length; i++)
            btnJettisonQty[i].setText("" + ship.getCargo()[i]);

        lblBays.setText(ship.getFilledCargoBays() + "/" + ship.getCargoBays());
    }

    //#endregion

    //#region Event Handlers

    private void btnJettison_Click(Object sender, EventArgs e) {
        String name = ((Button) sender).getName();
        boolean all = name.indexOf("Qty") < 0;
        int index = Integer.parseInt(name.substring(name.length() - 1));

        Jettison(index, all);
    }

    //#endregion
}

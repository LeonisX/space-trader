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

@Facaded
public class FormPlunder extends SpaceTraderForm {
    //#region Control Declarations

    private final Container components = null;
    private final Button[] btnPlunderQty;
    private final Button[] btnPlunderAll;
    private final Game game = Game.getCurrentGame();
    private spacetrader.controls.Button btnPlunderAll9;
    private spacetrader.controls.Button btnPlunderQty9;
    private spacetrader.controls.Button btnPlunderAll8;
    private spacetrader.controls.Button btnPlunderQty8;
    private spacetrader.controls.Button btnPlunderAll7;
    private spacetrader.controls.Button btnPlunderQty7;
    private spacetrader.controls.Button btnPlunderAll6;
    private spacetrader.controls.Button btnPlunderQty6;
    private spacetrader.controls.Button btnPlunderAll5;
    private spacetrader.controls.Button btnPlunderQty5;
    private spacetrader.controls.Button btnPlunderAll4;
    private spacetrader.controls.Button btnPlunderQty4;
    private spacetrader.controls.Button btnPlunderAll3;
    private spacetrader.controls.Button btnPlunderQty3;
    private spacetrader.controls.Button btnPlunderAll2;
    private spacetrader.controls.Button btnPlunderQty2;
    private spacetrader.controls.Button btnPlunderAll1;
    private spacetrader.controls.Button btnPlunderQty1;
    private spacetrader.controls.Button btnPlunderAll0;
    private spacetrader.controls.Button btnPlunderQty0;
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
    private spacetrader.controls.Button btnDone;

    //#endregion

    //#region Member Declarations
    private spacetrader.controls.Button btnJettison;

    //#endregion

    //#region Methods

    public FormPlunder() {
        initializeComponent();

        //#region Arrays of Cargo spacetrader.controls
        btnPlunderQty = new Button[]{btnPlunderQty0, btnPlunderQty1, btnPlunderQty2, btnPlunderQty3, btnPlunderQty4,
                btnPlunderQty5, btnPlunderQty6, btnPlunderQty7, btnPlunderQty8, btnPlunderQty9};

        btnPlunderAll = new Button[]{btnPlunderAll0, btnPlunderAll1, btnPlunderAll2, btnPlunderAll3, btnPlunderAll4,
                btnPlunderAll5, btnPlunderAll6, btnPlunderAll7, btnPlunderAll8, btnPlunderAll9};
        //#endregion

        UpdateAll();
    }

    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        btnPlunderAll9 = new spacetrader.controls.Button();
        btnPlunderQty9 = new spacetrader.controls.Button();
        btnPlunderAll8 = new spacetrader.controls.Button();
        btnPlunderQty8 = new spacetrader.controls.Button();
        btnPlunderAll7 = new spacetrader.controls.Button();
        btnPlunderQty7 = new spacetrader.controls.Button();
        btnPlunderAll6 = new spacetrader.controls.Button();
        btnPlunderQty6 = new spacetrader.controls.Button();
        btnPlunderAll5 = new spacetrader.controls.Button();
        btnPlunderQty5 = new spacetrader.controls.Button();
        btnPlunderAll4 = new spacetrader.controls.Button();
        btnPlunderQty4 = new spacetrader.controls.Button();
        btnPlunderAll3 = new spacetrader.controls.Button();
        btnPlunderQty3 = new spacetrader.controls.Button();
        btnPlunderAll2 = new spacetrader.controls.Button();
        btnPlunderQty2 = new spacetrader.controls.Button();
        btnPlunderAll1 = new spacetrader.controls.Button();
        btnPlunderQty1 = new spacetrader.controls.Button();
        btnPlunderAll0 = new spacetrader.controls.Button();
        btnPlunderQty0 = new spacetrader.controls.Button();
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
        btnJettison = new spacetrader.controls.Button();
        this.suspendLayout();
        //
        // btnPlunderAll9
        //
        btnPlunderAll9.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll9.setLocation(new java.awt.Point(100, 220));
        btnPlunderAll9.setName("btnPlunderAll9");
        btnPlunderAll9.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll9.setTabIndex(141);
        btnPlunderAll9.setText("All");
        btnPlunderAll9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty9
        //
        btnPlunderQty9.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty9.setLocation(new java.awt.Point(68, 220));
        btnPlunderQty9.setName("btnPlunderQty9");
        btnPlunderQty9.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty9.setTabIndex(140);
        btnPlunderQty9.setText("88");
        btnPlunderQty9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll8
        //
        btnPlunderAll8.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll8.setLocation(new java.awt.Point(100, 196));
        btnPlunderAll8.setName("btnPlunderAll8");
        btnPlunderAll8.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll8.setTabIndex(139);
        btnPlunderAll8.setText("All");
        btnPlunderAll8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty8
        //
        btnPlunderQty8.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty8.setLocation(new java.awt.Point(68, 196));
        btnPlunderQty8.setName("btnPlunderQty8");
        btnPlunderQty8.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty8.setTabIndex(138);
        btnPlunderQty8.setText("88");
        btnPlunderQty8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll7
        //
        btnPlunderAll7.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll7.setLocation(new java.awt.Point(100, 172));
        btnPlunderAll7.setName("btnPlunderAll7");
        btnPlunderAll7.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll7.setTabIndex(137);
        btnPlunderAll7.setText("All");
        btnPlunderAll7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty7
        //
        btnPlunderQty7.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty7.setLocation(new java.awt.Point(68, 172));
        btnPlunderQty7.setName("btnPlunderQty7");
        btnPlunderQty7.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty7.setTabIndex(136);
        btnPlunderQty7.setText("88");
        btnPlunderQty7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll6
        //
        btnPlunderAll6.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll6.setLocation(new java.awt.Point(100, 148));
        btnPlunderAll6.setName("btnPlunderAll6");
        btnPlunderAll6.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll6.setTabIndex(135);
        btnPlunderAll6.setText("All");
        btnPlunderAll6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty6
        //
        btnPlunderQty6.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty6.setLocation(new java.awt.Point(68, 148));
        btnPlunderQty6.setName("btnPlunderQty6");
        btnPlunderQty6.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty6.setTabIndex(134);
        btnPlunderQty6.setText("88");
        btnPlunderQty6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll5
        //
        btnPlunderAll5.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll5.setLocation(new java.awt.Point(100, 124));
        btnPlunderAll5.setName("btnPlunderAll5");
        btnPlunderAll5.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll5.setTabIndex(133);
        btnPlunderAll5.setText("All");
        btnPlunderAll5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty5
        //
        btnPlunderQty5.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty5.setLocation(new java.awt.Point(68, 124));
        btnPlunderQty5.setName("btnPlunderQty5");
        btnPlunderQty5.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty5.setTabIndex(132);
        btnPlunderQty5.setText("88");
        btnPlunderQty5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll4
        //
        btnPlunderAll4.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll4.setLocation(new java.awt.Point(100, 100));
        btnPlunderAll4.setName("btnPlunderAll4");
        btnPlunderAll4.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll4.setTabIndex(131);
        btnPlunderAll4.setText("All");
        btnPlunderAll4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty4
        //
        btnPlunderQty4.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty4.setLocation(new java.awt.Point(68, 100));
        btnPlunderQty4.setName("btnPlunderQty4");
        btnPlunderQty4.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty4.setTabIndex(130);
        btnPlunderQty4.setText("88");
        btnPlunderQty4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll3
        //
        btnPlunderAll3.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll3.setLocation(new java.awt.Point(100, 76));
        btnPlunderAll3.setName("btnPlunderAll3");
        btnPlunderAll3.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll3.setTabIndex(129);
        btnPlunderAll3.setText("All");
        btnPlunderAll3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty3
        //
        btnPlunderQty3.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty3.setLocation(new java.awt.Point(68, 76));
        btnPlunderQty3.setName("btnPlunderQty3");
        btnPlunderQty3.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty3.setTabIndex(128);
        btnPlunderQty3.setText("88");
        btnPlunderQty3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll2
        //
        btnPlunderAll2.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll2.setLocation(new java.awt.Point(100, 52));
        btnPlunderAll2.setName("btnPlunderAll2");
        btnPlunderAll2.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll2.setTabIndex(127);
        btnPlunderAll2.setText("All");
        btnPlunderAll2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty2
        //
        btnPlunderQty2.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty2.setLocation(new java.awt.Point(68, 52));
        btnPlunderQty2.setName("btnPlunderQty2");
        btnPlunderQty2.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty2.setTabIndex(126);
        btnPlunderQty2.setText("88");
        btnPlunderQty2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll1
        //
        btnPlunderAll1.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll1.setLocation(new java.awt.Point(100, 28));
        btnPlunderAll1.setName("btnPlunderAll1");
        btnPlunderAll1.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll1.setTabIndex(125);
        btnPlunderAll1.setText("All");
        btnPlunderAll1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty1
        //
        btnPlunderQty1.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty1.setLocation(new java.awt.Point(68, 28));
        btnPlunderQty1.setName("btnPlunderQty1");
        btnPlunderQty1.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty1.setTabIndex(124);
        btnPlunderQty1.setText("88");
        btnPlunderQty1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderAll0
        //
        btnPlunderAll0.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderAll0.setLocation(new java.awt.Point(100, 4));
        btnPlunderAll0.setName("btnPlunderAll0");
        btnPlunderAll0.setSize(new spacetrader.controls.Size(32, 22));
        btnPlunderAll0.setTabIndex(123);
        btnPlunderAll0.setText("All");
        btnPlunderAll0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnPlunderQty0
        //
        btnPlunderQty0.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunderQty0.setLocation(new java.awt.Point(68, 4));
        btnPlunderQty0.setName("btnPlunderQty0");
        btnPlunderQty0.setSize(new spacetrader.controls.Size(28, 22));
        btnPlunderQty0.setTabIndex(122);
        btnPlunderQty0.setText("88");
        btnPlunderQty0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // lblTradeCmdty9
        //
        lblTradeCmdty9.setAutoSize(true);
        lblTradeCmdty9.setLocation(new java.awt.Point(8, 224));
        lblTradeCmdty9.setName("lblTradeCmdty9");
        lblTradeCmdty9.setSize(new spacetrader.controls.Size(41, 13));
        lblTradeCmdty9.setTabIndex(151);
        lblTradeCmdty9.setText("Robots");
        //
        // lblTradeCmdty8
        //
        lblTradeCmdty8.setAutoSize(true);
        lblTradeCmdty8.setLocation(new java.awt.Point(8, 200));
        lblTradeCmdty8.setName("lblTradeCmdty8");
        lblTradeCmdty8.setSize(new spacetrader.controls.Size(52, 13));
        lblTradeCmdty8.setTabIndex(150);
        lblTradeCmdty8.setText("Narcotics");
        //
        // lblTradeCmdty2
        //
        lblTradeCmdty2.setAutoSize(true);
        lblTradeCmdty2.setLocation(new java.awt.Point(8, 56));
        lblTradeCmdty2.setName("lblTradeCmdty2");
        lblTradeCmdty2.setSize(new spacetrader.controls.Size(31, 13));
        lblTradeCmdty2.setTabIndex(149);
        lblTradeCmdty2.setText("Food");
        //
        // lblTradeCmdty0
        //
        lblTradeCmdty0.setAutoSize(true);
        lblTradeCmdty0.setLocation(new java.awt.Point(8, 8));
        lblTradeCmdty0.setName("lblTradeCmdty0");
        lblTradeCmdty0.setSize(new spacetrader.controls.Size(36, 13));
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
        lblTradeCmdty5.setSize(new spacetrader.controls.Size(46, 13));
        lblTradeCmdty5.setTabIndex(145);
        lblTradeCmdty5.setText("Firearms");
        //
        // lblTradeCmdty4
        //
        lblTradeCmdty4.setAutoSize(true);
        lblTradeCmdty4.setLocation(new java.awt.Point(8, 104));
        lblTradeCmdty4.setName("lblTradeCmdty4");
        lblTradeCmdty4.setSize(new spacetrader.controls.Size(40, 13));
        lblTradeCmdty4.setTabIndex(144);
        lblTradeCmdty4.setText("Games");
        //
        // lblTradeCmdty3
        //
        lblTradeCmdty3.setAutoSize(true);
        lblTradeCmdty3.setLocation(new java.awt.Point(8, 80));
        lblTradeCmdty3.setName("lblTradeCmdty3");
        lblTradeCmdty3.setSize(new spacetrader.controls.Size(24, 13));
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
        lblBays.setSize(new spacetrader.controls.Size(48, 13));
        lblBays.setTabIndex(153);
        lblBays.setText("888/888");
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
        // btnJettison
        //
        btnJettison.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnJettison.setLocation(new java.awt.Point(150, 24));
        btnJettison.setName("btnJettison");
        btnJettison.setSize(new spacetrader.controls.Size(53, 22));
        btnJettison.setTabIndex(155);
        btnJettison.setText("Jettison");
        btnJettison.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJettison_Click(sender, e);
            }
        });
        //
        // FormPlunder
        //
        this.setAcceptButton(btnDone);
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(btnDone);
        this.setClientSize(new spacetrader.controls.Size(230, 283));
        Controls.add(btnJettison);
        Controls.add(btnDone);
        Controls.add(lblBays);
        Controls.add(lblBaysLabel);
        Controls.add(lblTradeCmdty9);
        Controls.add(lblTradeCmdty8);
        Controls.add(lblTradeCmdty2);
        Controls.add(lblTradeCmdty0);
        Controls.add(lblTradeCmdty1);
        Controls.add(lblTradeCmdty6);
        Controls.add(lblTradeCmdty5);
        Controls.add(lblTradeCmdty4);
        Controls.add(lblTradeCmdty3);
        Controls.add(lblTradeCmdty7);
        Controls.add(btnPlunderAll9);
        Controls.add(btnPlunderQty9);
        Controls.add(btnPlunderAll8);
        Controls.add(btnPlunderQty8);
        Controls.add(btnPlunderAll7);
        Controls.add(btnPlunderQty7);
        Controls.add(btnPlunderAll6);
        Controls.add(btnPlunderQty6);
        Controls.add(btnPlunderAll5);
        Controls.add(btnPlunderQty5);
        Controls.add(btnPlunderAll4);
        Controls.add(btnPlunderQty4);
        Controls.add(btnPlunderAll3);
        Controls.add(btnPlunderQty3);
        Controls.add(btnPlunderAll2);
        Controls.add(btnPlunderQty2);
        Controls.add(btnPlunderAll1);
        Controls.add(btnPlunderQty1);
        Controls.add(btnPlunderAll0);
        Controls.add(btnPlunderQty0);
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormPlunder");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Plunder Cargo");
        this.PerformLayout();

    }

    //#endregion

    private void Plunder(int tradeItem, boolean all) {
        game.CargoPlunder(tradeItem, all);

        UpdateAll();
    }

    private void UpdateAll() {
        Ship ship = game.getCommander().getShip();
        Ship opp = game.getOpponent();

        for (int i = 0; i < btnPlunderQty.length; i++)
            btnPlunderQty[i].setText("" + opp.getCargo()[i]);

        lblBays.setText(ship.getFilledCargoBays() + "/" + ship.getCargoBays());
    }

    //#endregion

    //#region Event Handlers

    private void btnJettison_Click(Object sender, EventArgs e) {
        (new FormJettison()).showDialog(this);
    }

    //#endregion

    //#region Properties

    private void btnPlunder_Click(Object sender, EventArgs e) {
        String name = ((Button) sender).getName();
        boolean all = name.indexOf("Qty") < 0;
        int index = Integer.parseInt(name.substring(name.length() - 1));

        Plunder(index, all);
    }

    //#endregion
}

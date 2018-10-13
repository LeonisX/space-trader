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
import spacetrader.*;
import spacetrader.enums.AlertType;
import spacetrader.guifacade.GuiFacade;

import java.util.Arrays;

public class FormShipList extends SpaceTraderForm {
    //#region Control Declarations

    private final Container components = null;
    private final Label[] lblPrice;
    private final Button[] btnBuy;
    private final Game game = Game.CurrentGame();
    private final int[] prices = new int[Consts.ShipSpecs.length];
    private jwinforms.Button btnClose;
    private jwinforms.Button btnBuy0;
    private jwinforms.Label lblName0;
    private jwinforms.Button btnInfo0;
    private jwinforms.Label lblPrice0;
    private jwinforms.Label lblPrice1;
    private jwinforms.Button btnInfo1;
    private jwinforms.Label lblName1;
    private jwinforms.Button btnBuy1;
    private jwinforms.Label lblPrice2;
    private jwinforms.Button btnInfo2;
    private jwinforms.Label lblName2;
    private jwinforms.Button btnBuy2;
    private jwinforms.Label lblPrice3;
    private jwinforms.Button btnInfo3;
    private jwinforms.Label lblName3;
    private jwinforms.Button btnBuy3;
    private jwinforms.Label lblPrice4;
    private jwinforms.Button btnInfo4;
    private jwinforms.Label lblName4;
    private jwinforms.Button btnBuy4;
    private jwinforms.Label lblPrice5;
    private jwinforms.Button btnInfo5;
    private jwinforms.Label lblName5;
    private jwinforms.Button btnBuy5;
    private jwinforms.Label lblPrice6;
    private jwinforms.Button btnInfo6;
    private jwinforms.Label lblName6;
    private jwinforms.Button btnBuy6;
    private jwinforms.Label lblPrice7;
    private jwinforms.Button btnInfo7;
    private jwinforms.Label lblName7;
    private jwinforms.Button btnBuy7;
    private jwinforms.Label lblPrice8;
    private jwinforms.Button btnInfo8;
    private jwinforms.Label lblName8;
    private jwinforms.Button btnBuy8;
    private jwinforms.Label lblPrice9;
    private jwinforms.Button btnInfo9;
    private jwinforms.Label lblName9;
    private jwinforms.Button btnBuy9;
    private jwinforms.GroupBox boxShipInfo;
    private jwinforms.Label lblSizeLabel;
    private jwinforms.Label lblNameLabel;
    private jwinforms.Label lblBaysLabel;
    private jwinforms.Label lblRangeLabel;
    private jwinforms.Label lblHullLabel;
    private jwinforms.Label lblWeaponLabel;
    private jwinforms.Label lblShieldLabel;
    private jwinforms.Label lblCrewLabel;
    private jwinforms.Label lblGadgetLabel;
    private jwinforms.PictureBox picShip;
    private jwinforms.Label lblName;
    private jwinforms.Label lblSize;
    private jwinforms.Label lblBays;
    private jwinforms.Label lblRange;
    private jwinforms.Label lblHull;
    private jwinforms.Label lblWeapon;
    private jwinforms.Label lblShield;

    //#endregion

    //#region Member Declarations
    private jwinforms.Label lblGadget;
    private jwinforms.Label lblCrew;

    //#endregion

    //#region Methods

    public FormShipList() {
        InitializeComponent();

        //#region Array of controls
        lblPrice = new Label[]
                {
                        lblPrice0,
                        lblPrice1,
                        lblPrice2,
                        lblPrice3,
                        lblPrice4,
                        lblPrice5,
                        lblPrice6,
                        lblPrice7,
                        lblPrice8,
                        lblPrice9,
                };

        btnBuy = new Button[]
                {
                        btnBuy0,
                        btnBuy1,
                        btnBuy2,
                        btnBuy3,
                        btnBuy4,
                        btnBuy5,
                        btnBuy6,
                        btnBuy7,
                        btnBuy8,
                        btnBuy9,
                };
        //#endregion

        UpdateAll();
        Info(game.Commander().getShip().Type().castToInt());

        if (game.Commander().getShip().getTribbles() > 0 && !game.getTribbleMessage()) {
            GuiFacade.alert(AlertType.TribblesTradeIn);
            game.setTribbleMessage(true);
        }
    }


    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent() {
        btnClose = new jwinforms.Button();
        btnBuy0 = new jwinforms.Button();
        lblName0 = new jwinforms.Label();
        btnInfo0 = new jwinforms.Button();
        lblPrice0 = new jwinforms.Label();
        lblPrice1 = new jwinforms.Label();
        btnInfo1 = new jwinforms.Button();
        lblName1 = new jwinforms.Label();
        btnBuy1 = new jwinforms.Button();
        lblPrice2 = new jwinforms.Label();
        btnInfo2 = new jwinforms.Button();
        lblName2 = new jwinforms.Label();
        btnBuy2 = new jwinforms.Button();
        lblPrice3 = new jwinforms.Label();
        btnInfo3 = new jwinforms.Button();
        lblName3 = new jwinforms.Label();
        btnBuy3 = new jwinforms.Button();
        lblPrice4 = new jwinforms.Label();
        btnInfo4 = new jwinforms.Button();
        lblName4 = new jwinforms.Label();
        btnBuy4 = new jwinforms.Button();
        lblPrice5 = new jwinforms.Label();
        btnInfo5 = new jwinforms.Button();
        lblName5 = new jwinforms.Label();
        btnBuy5 = new jwinforms.Button();
        lblPrice6 = new jwinforms.Label();
        btnInfo6 = new jwinforms.Button();
        lblName6 = new jwinforms.Label();
        btnBuy6 = new jwinforms.Button();
        lblPrice7 = new jwinforms.Label();
        btnInfo7 = new jwinforms.Button();
        lblName7 = new jwinforms.Label();
        btnBuy7 = new jwinforms.Button();
        lblPrice8 = new jwinforms.Label();
        btnInfo8 = new jwinforms.Button();
        lblName8 = new jwinforms.Label();
        btnBuy8 = new jwinforms.Button();
        lblPrice9 = new jwinforms.Label();
        btnInfo9 = new jwinforms.Button();
        lblName9 = new jwinforms.Label();
        btnBuy9 = new jwinforms.Button();
        boxShipInfo = new jwinforms.GroupBox();
        lblCrew = new jwinforms.Label();
        lblGadget = new jwinforms.Label();
        lblShield = new jwinforms.Label();
        lblWeapon = new jwinforms.Label();
        lblHull = new jwinforms.Label();
        lblRange = new jwinforms.Label();
        lblBays = new jwinforms.Label();
        lblSize = new jwinforms.Label();
        lblName = new jwinforms.Label();
        picShip = new jwinforms.PictureBox();
        lblGadgetLabel = new jwinforms.Label();
        lblCrewLabel = new jwinforms.Label();
        lblShieldLabel = new jwinforms.Label();
        lblWeaponLabel = new jwinforms.Label();
        lblHullLabel = new jwinforms.Label();
        lblRangeLabel = new jwinforms.Label();
        lblBaysLabel = new jwinforms.Label();
        lblNameLabel = new jwinforms.Label();
        lblSizeLabel = new jwinforms.Label();
        boxShipInfo.SuspendLayout();
        this.SuspendLayout();
        //
        // btnClose
        //
        btnClose.setDialogResult(DialogResult.Cancel);
        btnClose.setLocation(new java.awt.Point(-32, -32));
        btnClose.setName("btnClose");
        btnClose.setSize(new jwinforms.Size(32, 32));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");
        //
        // btnBuy0
        //
        btnBuy0.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy0.setLocation(new java.awt.Point(8, 8));
        btnBuy0.setName("btnBuy0");
        btnBuy0.setSize(new jwinforms.Size(35, 22));
        btnBuy0.setTabIndex(1);
        btnBuy0.setText("Buy");
        btnBuy0.setVisible(false);
        btnBuy0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName0
        //
        lblName0.setLocation(new java.awt.Point(48, 12));
        lblName0.setName("lblName0");
        lblName0.setSize(new jwinforms.Size(70, 13));
        lblName0.setTabIndex(34);
        lblName0.setText("Flea");
        //
        // btnInfo0
        //
        btnInfo0.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo0.setLocation(new java.awt.Point(120, 8));
        btnInfo0.setName("btnInfo0");
        btnInfo0.setSize(new jwinforms.Size(34, 22));
        btnInfo0.setTabIndex(11);
        btnInfo0.setText("Info");
        btnInfo0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice0
        //
        lblPrice0.setLocation(new java.awt.Point(160, 12));
        lblPrice0.setName("lblPrice0");
        lblPrice0.setSize(new jwinforms.Size(64, 13));
        lblPrice0.setTabIndex(36);
        lblPrice0.setText("-888,888 cr.");
        lblPrice0.TextAlign = ContentAlignment.TopRight;
        //
        // lblPrice1
        //
        lblPrice1.setLocation(new java.awt.Point(160, 36));
        lblPrice1.setName("lblPrice1");
        lblPrice1.setSize(new jwinforms.Size(64, 13));
        lblPrice1.setTabIndex(40);
        lblPrice1.setText("-888,888 cr.");
        lblPrice1.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo1
        //
        btnInfo1.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo1.setLocation(new java.awt.Point(120, 32));
        btnInfo1.setName("btnInfo1");
        btnInfo1.setSize(new jwinforms.Size(34, 22));
        btnInfo1.setTabIndex(12);
        btnInfo1.setText("Info");
        btnInfo1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName1
        //
        lblName1.setLocation(new java.awt.Point(48, 36));
        lblName1.setName("lblName1");
        lblName1.setSize(new jwinforms.Size(70, 13));
        lblName1.setTabIndex(38);
        lblName1.setText("Gnat");
        //
        // btnBuy1
        //
        btnBuy1.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy1.setLocation(new java.awt.Point(8, 32));
        btnBuy1.setName("btnBuy1");
        btnBuy1.setSize(new jwinforms.Size(35, 22));
        btnBuy1.setTabIndex(2);
        btnBuy1.setText("Buy");
        btnBuy1.setVisible(false);
        btnBuy1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice2
        //
        lblPrice2.setLocation(new java.awt.Point(160, 60));
        lblPrice2.setName("lblPrice2");
        lblPrice2.setSize(new jwinforms.Size(64, 13));
        lblPrice2.setTabIndex(44);
        lblPrice2.setText("-888,888 cr.");
        lblPrice2.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo2
        //
        btnInfo2.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo2.setLocation(new java.awt.Point(120, 56));
        btnInfo2.setName("btnInfo2");
        btnInfo2.setSize(new jwinforms.Size(34, 22));
        btnInfo2.setTabIndex(13);
        btnInfo2.setText("Info");
        btnInfo2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName2
        //
        lblName2.setLocation(new java.awt.Point(48, 60));
        lblName2.setName("lblName2");
        lblName2.setSize(new jwinforms.Size(70, 13));
        lblName2.setTabIndex(42);
        lblName2.setText("Firefly");
        //
        // btnBuy2
        //
        btnBuy2.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy2.setLocation(new java.awt.Point(8, 56));
        btnBuy2.setName("btnBuy2");
        btnBuy2.setSize(new jwinforms.Size(35, 22));
        btnBuy2.setTabIndex(3);
        btnBuy2.setText("Buy");
        btnBuy2.setVisible(false);
        btnBuy2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice3
        //
        lblPrice3.setLocation(new java.awt.Point(160, 84));
        lblPrice3.setName("lblPrice3");
        lblPrice3.setSize(new jwinforms.Size(64, 13));
        lblPrice3.setTabIndex(48);
        lblPrice3.setText("-888,888 cr.");
        lblPrice3.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo3
        //
        btnInfo3.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo3.setLocation(new java.awt.Point(120, 80));
        btnInfo3.setName("btnInfo3");
        btnInfo3.setSize(new jwinforms.Size(34, 22));
        btnInfo3.setTabIndex(14);
        btnInfo3.setText("Info");
        btnInfo3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName3
        //
        lblName3.setLocation(new java.awt.Point(48, 84));
        lblName3.setName("lblName3");
        lblName3.setSize(new jwinforms.Size(70, 13));
        lblName3.setTabIndex(46);
        lblName3.setText("Mosquito");
        //
        // btnBuy3
        //
        btnBuy3.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy3.setLocation(new java.awt.Point(8, 80));
        btnBuy3.setName("btnBuy3");
        btnBuy3.setSize(new jwinforms.Size(35, 22));
        btnBuy3.setTabIndex(4);
        btnBuy3.setText("Buy");
        btnBuy3.setVisible(false);
        btnBuy3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice4
        //
        lblPrice4.setLocation(new java.awt.Point(160, 108));
        lblPrice4.setName("lblPrice4");
        lblPrice4.setSize(new jwinforms.Size(64, 13));
        lblPrice4.setTabIndex(52);
        lblPrice4.setText("-888,888 cr.");
        lblPrice4.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo4
        //
        btnInfo4.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo4.setLocation(new java.awt.Point(120, 104));
        btnInfo4.setName("btnInfo4");
        btnInfo4.setSize(new jwinforms.Size(34, 22));
        btnInfo4.setTabIndex(15);
        btnInfo4.setText("Info");
        btnInfo4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName4
        //
        lblName4.setLocation(new java.awt.Point(48, 108));
        lblName4.setName("lblName4");
        lblName4.setSize(new jwinforms.Size(70, 13));
        lblName4.setTabIndex(50);
        lblName4.setText("Bumblebee");
        //
        // btnBuy4
        //
        btnBuy4.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy4.setLocation(new java.awt.Point(8, 104));
        btnBuy4.setName("btnBuy4");
        btnBuy4.setSize(new jwinforms.Size(35, 22));
        btnBuy4.setTabIndex(5);
        btnBuy4.setText("Buy");
        btnBuy4.setVisible(false);
        btnBuy4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice5
        //
        lblPrice5.setLocation(new java.awt.Point(160, 132));
        lblPrice5.setName("lblPrice5");
        lblPrice5.setSize(new jwinforms.Size(64, 13));
        lblPrice5.setTabIndex(56);
        lblPrice5.setText("got one");
        lblPrice5.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo5
        //
        btnInfo5.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo5.setLocation(new java.awt.Point(120, 128));
        btnInfo5.setName("btnInfo5");
        btnInfo5.setSize(new jwinforms.Size(34, 22));
        btnInfo5.setTabIndex(16);
        btnInfo5.setText("Info");
        btnInfo5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName5
        //
        lblName5.setLocation(new java.awt.Point(48, 132));
        lblName5.setName("lblName5");
        lblName5.setSize(new jwinforms.Size(70, 13));
        lblName5.setTabIndex(54);
        lblName5.setText("Beetle");
        //
        // btnBuy5
        //
        btnBuy5.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy5.setLocation(new java.awt.Point(8, 128));
        btnBuy5.setName("btnBuy5");
        btnBuy5.setSize(new jwinforms.Size(35, 22));
        btnBuy5.setTabIndex(6);
        btnBuy5.setText("Buy");
        btnBuy5.setVisible(false);
        btnBuy5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice6
        //
        lblPrice6.setLocation(new java.awt.Point(160, 156));
        lblPrice6.setName("lblPrice6");
        lblPrice6.setSize(new jwinforms.Size(64, 13));
        lblPrice6.setTabIndex(60);
        lblPrice6.setText("-888,888 cr.");
        lblPrice6.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo6
        //
        btnInfo6.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo6.setLocation(new java.awt.Point(120, 152));
        btnInfo6.setName("btnInfo6");
        btnInfo6.setSize(new jwinforms.Size(34, 22));
        btnInfo6.setTabIndex(17);
        btnInfo6.setText("Info");
        btnInfo6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName6
        //
        lblName6.setLocation(new java.awt.Point(48, 156));
        lblName6.setName("lblName6");
        lblName6.setSize(new jwinforms.Size(70, 13));
        lblName6.setTabIndex(58);
        lblName6.setText("Hornet");
        //
        // btnBuy6
        //
        btnBuy6.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy6.setLocation(new java.awt.Point(8, 152));
        btnBuy6.setName("btnBuy6");
        btnBuy6.setSize(new jwinforms.Size(35, 22));
        btnBuy6.setTabIndex(7);
        btnBuy6.setText("Buy");
        btnBuy6.setVisible(false);
        btnBuy6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice7
        //
        lblPrice7.setLocation(new java.awt.Point(160, 180));
        lblPrice7.setName("lblPrice7");
        lblPrice7.setSize(new jwinforms.Size(64, 13));
        lblPrice7.setTabIndex(64);
        lblPrice7.setText("-888,888 cr.");
        lblPrice7.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo7
        //
        btnInfo7.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo7.setLocation(new java.awt.Point(120, 176));
        btnInfo7.setName("btnInfo7");
        btnInfo7.setSize(new jwinforms.Size(34, 22));
        btnInfo7.setTabIndex(18);
        btnInfo7.setText("Info");
        btnInfo7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName7
        //
        lblName7.setLocation(new java.awt.Point(48, 180));
        lblName7.setName("lblName7");
        lblName7.setSize(new jwinforms.Size(70, 13));
        lblName7.setTabIndex(62);
        lblName7.setText("Grasshopper");
        //
        // btnBuy7
        //
        btnBuy7.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy7.setLocation(new java.awt.Point(8, 176));
        btnBuy7.setName("btnBuy7");
        btnBuy7.setSize(new jwinforms.Size(35, 22));
        btnBuy7.setTabIndex(8);
        btnBuy7.setText("Buy");
        btnBuy7.setVisible(false);
        btnBuy7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice8
        //
        lblPrice8.setLocation(new java.awt.Point(160, 204));
        lblPrice8.setName("lblPrice8");
        lblPrice8.setSize(new jwinforms.Size(64, 13));
        lblPrice8.setTabIndex(68);
        lblPrice8.setText("-888,888 cr.");
        lblPrice8.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo8
        //
        btnInfo8.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo8.setLocation(new java.awt.Point(120, 200));
        btnInfo8.setName("btnInfo8");
        btnInfo8.setSize(new jwinforms.Size(34, 22));
        btnInfo8.setTabIndex(19);
        btnInfo8.setText("Info");
        btnInfo8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName8
        //
        lblName8.setLocation(new java.awt.Point(48, 204));
        lblName8.setName("lblName8");
        lblName8.setSize(new jwinforms.Size(70, 13));
        lblName8.setTabIndex(66);
        lblName8.setText("Termite");
        //
        // btnBuy8
        //
        btnBuy8.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy8.setLocation(new java.awt.Point(8, 200));
        btnBuy8.setName("btnBuy8");
        btnBuy8.setSize(new jwinforms.Size(35, 22));
        btnBuy8.setTabIndex(9);
        btnBuy8.setText("Buy");
        btnBuy8.setVisible(false);
        btnBuy8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblPrice9
        //
        lblPrice9.setLocation(new java.awt.Point(160, 228));
        lblPrice9.setName("lblPrice9");
        lblPrice9.setSize(new jwinforms.Size(64, 13));
        lblPrice9.setTabIndex(72);
        lblPrice9.setText("not sold");
        lblPrice9.TextAlign = ContentAlignment.TopRight;
        //
        // btnInfo9
        //
        btnInfo9.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInfo9.setLocation(new java.awt.Point(120, 224));
        btnInfo9.setName("btnInfo9");
        btnInfo9.setSize(new jwinforms.Size(34, 22));
        btnInfo9.setTabIndex(20);
        btnInfo9.setText("Info");
        btnInfo9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // lblName9
        //
        lblName9.setLocation(new java.awt.Point(48, 228));
        lblName9.setName("lblName9");
        lblName9.setSize(new jwinforms.Size(70, 13));
        lblName9.setTabIndex(70);
        lblName9.setText("Wasp");
        //
        // btnBuy9
        //
        btnBuy9.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuy9.setLocation(new java.awt.Point(8, 224));
        btnBuy9.setName("btnBuy9");
        btnBuy9.setSize(new jwinforms.Size(35, 22));
        btnBuy9.setTabIndex(10);
        btnBuy9.setText("Buy");
        btnBuy9.setVisible(false);
        btnBuy9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuyInfo_Click(sender, e);
            }
        });
        //
        // boxShipInfo
        //
        boxShipInfo.Controls.addAll((new WinformControl[]{
                lblCrew,
                lblGadget,
                lblShield,
                lblWeapon,
                lblHull,
                lblRange,
                lblBays,
                lblSize,
                lblName,
                picShip,
                lblGadgetLabel,
                lblCrewLabel,
                lblShieldLabel,
                lblWeaponLabel,
                lblHullLabel,
                lblRangeLabel,
                lblBaysLabel,
                lblNameLabel,
                lblSizeLabel}));
        boxShipInfo.setLocation(new java.awt.Point(232, 0));
        boxShipInfo.setName("boxShipInfo");
        boxShipInfo.setSize(new jwinforms.Size(200, 248));
        boxShipInfo.setTabIndex(73);
        boxShipInfo.setTabStop(false);
        boxShipInfo.setText("Ship Information");
        //
        // lblCrew
        //
        lblCrew.setLocation(new java.awt.Point(96, 224));
        lblCrew.setName("lblCrew");
        lblCrew.setSize(new jwinforms.Size(10, 13));
        lblCrew.setTabIndex(43);
        lblCrew.setText("8");
        //
        // lblGadget
        //
        lblGadget.setLocation(new java.awt.Point(96, 208));
        lblGadget.setName("lblGadget");
        lblGadget.setSize(new jwinforms.Size(10, 13));
        lblGadget.setTabIndex(42);
        lblGadget.setText("8");
        //
        // lblShield
        //
        lblShield.setLocation(new java.awt.Point(96, 192));
        lblShield.setName("lblShield");
        lblShield.setSize(new jwinforms.Size(10, 13));
        lblShield.setTabIndex(41);
        lblShield.setText("8");
        //
        // lblWeapon
        //
        lblWeapon.setLocation(new java.awt.Point(96, 176));
        lblWeapon.setName("lblWeapon");
        lblWeapon.setSize(new jwinforms.Size(10, 13));
        lblWeapon.setTabIndex(40);
        lblWeapon.setText("8");
        //
        // lblHull
        //
        lblHull.setLocation(new java.awt.Point(96, 160));
        lblHull.setName("lblHull");
        lblHull.setSize(new jwinforms.Size(23, 13));
        lblHull.setTabIndex(39);
        lblHull.setText("888");
        //
        // lblRange
        //
        lblRange.setLocation(new java.awt.Point(96, 144));
        lblRange.setName("lblRange");
        lblRange.setSize(new jwinforms.Size(59, 13));
        lblRange.setTabIndex(38);
        lblRange.setText("88 parsecs");
        //
        // lblBays
        //
        lblBays.setLocation(new java.awt.Point(96, 128));
        lblBays.setName("lblBays");
        lblBays.setSize(new jwinforms.Size(17, 13));
        lblBays.setTabIndex(37);
        lblBays.setText("88");
        //
        // lblSize
        //
        lblSize.setLocation(new java.awt.Point(96, 112));
        lblSize.setName("lblSize");
        lblSize.setSize(new jwinforms.Size(45, 13));
        lblSize.setTabIndex(36);
        lblSize.setText("Medium");
        //
        // lblName
        //
        lblName.setLocation(new java.awt.Point(96, 96));
        lblName.setName("lblName");
        lblName.setSize(new jwinforms.Size(100, 13));
        lblName.setTabIndex(35);
        lblName.setText("Grasshopper");
        //
        // picShip
        //
        picShip.setBackColor(java.awt.Color.white);
        picShip.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        picShip.setLocation(new java.awt.Point(67, 25));
        picShip.setName("picShip");
        picShip.setSize(new jwinforms.Size(66, 54));
        picShip.setTabIndex(12);
        picShip.setTabStop(false);
        //
        // lblGadgetLabel
        //
        lblGadgetLabel.setAutoSize(true);
        lblGadgetLabel.setFont(FontCollection.bold825);
        lblGadgetLabel.setLocation(new java.awt.Point(8, 208));
        lblGadgetLabel.setName("lblGadgetLabel");
        lblGadgetLabel.setSize(new jwinforms.Size(76, 13));
        lblGadgetLabel.setTabIndex(11);
        lblGadgetLabel.setText("Gadget Slots:");
        //
        // lblCrewLabel
        //
        lblCrewLabel.setAutoSize(true);
        lblCrewLabel.setFont(FontCollection.bold825);
        lblCrewLabel.setLocation(new java.awt.Point(8, 224));
        lblCrewLabel.setName("lblCrewLabel");
        lblCrewLabel.setSize(new jwinforms.Size(84, 13));
        lblCrewLabel.setTabIndex(10);
        lblCrewLabel.setText("Crew Quarters:");
        //
        // lblShieldLabel
        //
        lblShieldLabel.setAutoSize(true);
        lblShieldLabel.setFont(FontCollection.bold825);
        lblShieldLabel.setLocation(new java.awt.Point(8, 192));
        lblShieldLabel.setName("lblShieldLabel");
        lblShieldLabel.setSize(new jwinforms.Size(70, 13));
        lblShieldLabel.setTabIndex(9);
        lblShieldLabel.setText("Shield Slots:");
        //
        // lblWeaponLabel
        //
        lblWeaponLabel.setAutoSize(true);
        lblWeaponLabel.setFont(FontCollection.bold825);
        lblWeaponLabel.setLocation(new java.awt.Point(8, 176));
        lblWeaponLabel.setName("lblWeaponLabel");
        lblWeaponLabel.setSize(new jwinforms.Size(81, 13));
        lblWeaponLabel.setTabIndex(8);
        lblWeaponLabel.setText("Weapon Slots:");
        //
        // lblHullLabel
        //
        lblHullLabel.setAutoSize(true);
        lblHullLabel.setFont(FontCollection.bold825);
        lblHullLabel.setLocation(new java.awt.Point(8, 160));
        lblHullLabel.setName("lblHullLabel");
        lblHullLabel.setSize(new jwinforms.Size(73, 13));
        lblHullLabel.setTabIndex(7);
        lblHullLabel.setText("Hull Strength");
        //
        // lblRangeLabel
        //
        lblRangeLabel.setAutoSize(true);
        lblRangeLabel.setFont(FontCollection.bold825);
        lblRangeLabel.setLocation(new java.awt.Point(8, 144));
        lblRangeLabel.setName("lblRangeLabel");
        lblRangeLabel.setSize(new jwinforms.Size(42, 13));
        lblRangeLabel.setTabIndex(6);
        lblRangeLabel.setText("Range:");
        //
        // lblBaysLabel
        //
        lblBaysLabel.setAutoSize(true);
        lblBaysLabel.setFont(FontCollection.bold825);
        lblBaysLabel.setLocation(new java.awt.Point(8, 128));
        lblBaysLabel.setName("lblBaysLabel");
        lblBaysLabel.setSize(new jwinforms.Size(69, 13));
        lblBaysLabel.setTabIndex(5);
        lblBaysLabel.setText("Cargo Bays:");
        //
        // lblNameLabel
        //
        lblNameLabel.setAutoSize(true);
        lblNameLabel.setFont(FontCollection.bold825);
        lblNameLabel.setLocation(new java.awt.Point(8, 96));
        lblNameLabel.setName("lblNameLabel");
        lblNameLabel.setSize(new jwinforms.Size(39, 13));
        lblNameLabel.setTabIndex(4);
        lblNameLabel.setText("Name:");
        //
        // lblSizeLabel
        //
        lblSizeLabel.setAutoSize(true);
        lblSizeLabel.setFont(FontCollection.bold825);
        lblSizeLabel.setLocation(new java.awt.Point(8, 112));
        lblSizeLabel.setName("lblSizeLabel");
        lblSizeLabel.setSize(new jwinforms.Size(31, 13));
        lblSizeLabel.setTabIndex(3);
        lblSizeLabel.setText("Size:");
        //
        // FormShipList
        //
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(btnClose);
        this.setClientSize(new jwinforms.Size(438, 255));
        Controls.addAll(Arrays.asList(
                boxShipInfo,
                lblPrice9,
                btnInfo9,
                lblName9,
                btnBuy9,
                lblPrice8,
                btnInfo8,
                lblName8,
                btnBuy8,
                lblPrice7,
                btnInfo7,
                lblName7,
                btnBuy7,
                lblPrice6,
                btnInfo6,
                lblName6,
                btnBuy6,
                lblPrice5,
                btnInfo5,
                lblName5,
                btnBuy5,
                lblPrice4,
                btnInfo4,
                lblName4,
                btnBuy4,
                lblPrice3,
                btnInfo3,
                lblName3,
                btnBuy3,
                lblPrice2,
                btnInfo2,
                lblName2,
                btnBuy2,
                lblPrice1,
                btnInfo1,
                lblName1,
                btnBuy1,
                lblPrice0,
                btnInfo0,
                lblName0,
                btnBuy0,
                btnClose));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormShipList");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Ship List");
    }
    //#endregion

    private void Buy(int id) {
        Info(id);

        if (game.Commander().TradeShip(Consts.ShipSpecs[id], prices[id])) {
            if (game.getQuestStatusScarab() == SpecialEvent.StatusScarabDone)
                game.setQuestStatusScarab(SpecialEvent.StatusScarabNotStarted);

            UpdateAll();
            game.getParentWindow().updateAll();
        }
    }

    private void Info(int id) {
        ShipSpec spec = Consts.ShipSpecs[id];

        picShip.setImage(spec.Image());
        lblName.setText(spec.Name());
        lblSize.setText(Strings.Sizes[spec.getSize().castToInt()]);
        lblBays.setText(Functions.formatNumber(spec.CargoBays()));
        lblRange.setText(Functions.Multiples(spec.FuelTanks(), Strings.DistanceUnit));
        lblHull.setText(Functions.formatNumber(spec.HullStrength()));
        lblWeapon.setText(Functions.formatNumber(spec.getWeaponSlots()));
        lblShield.setText(Functions.formatNumber(spec.getShieldSlots()));
        lblGadget.setText(Functions.formatNumber(spec.getGadgetSlots()));
        lblCrew.setText(Functions.formatNumber(spec.getCrewQuarters()));
    }

    private void UpdateAll() {
        for (int i = 0; i < lblPrice.length; i++) {
            btnBuy[i].setVisible(false);

            if (Consts.ShipSpecs[i].MinimumTechLevel().castToInt() > game.Commander().getCurrentSystem().TechLevel().castToInt())
                lblPrice[i].setText(Strings.CargoBuyNA);
            else if (Consts.ShipSpecs[i].Type() == game.Commander().getShip().Type())
                lblPrice[i].setText(Strings.ShipBuyGotOne);
            else {
                btnBuy[i].setVisible(true);
                prices[i] = Consts.ShipSpecs[i].getPrice() - game.Commander().getShip().Worth(false);
                lblPrice[i].setText(Functions.formatMoney(prices[i]));
            }
        }
    }

    //#endregion

    //#region Event Handlers

    private void btnBuyInfo_Click(Object sender, EventArgs e) {
        String name = ((Button) sender).getName();
        int index = Integer.parseInt(name.substring(name.length() - 1));

        if (name.indexOf("Buy") < 0)
            Info(index);
        else
            Buy(index);
    }

    //#endregion
}

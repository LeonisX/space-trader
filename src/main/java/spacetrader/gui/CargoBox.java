/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

package spacetrader.gui;

import jwinforms.Button;
import jwinforms.*;
import jwinforms.Font;
import jwinforms.Label;
import spacetrader.*;

import java.awt.*;
import java.util.Map;

class CargoBox extends jwinforms.GroupBox {

    private static final Font BOLD_FONT = FontCollection.bold825;

    private SpaceTraderGame game = null;
    private GameController controller = null;
    private jwinforms.Button btnBuyMax0;
    private jwinforms.Button btnBuyQty0;
    private jwinforms.Button btnSellQty0;
    private jwinforms.Button btnSellAll0;
    private jwinforms.Button btnBuyMax1;
    private jwinforms.Button btnBuyQty1;
    private jwinforms.Button btnSellQty1;
    private jwinforms.Button btnSellAll1;
    private jwinforms.Button btnSellQty2;
    private jwinforms.Button btnSellAll2;
    private jwinforms.Button btnBuyQty2;
    private jwinforms.Button btnBuyMax2;
    private jwinforms.Button btnSellQty3;
    private jwinforms.Button btnSellAll3;
    private jwinforms.Button btnBuyQty3;
    private jwinforms.Button btnBuyMax3;
    private jwinforms.Button btnSellQty4;
    private jwinforms.Button btnSellAll4;
    private jwinforms.Button btnBuyQty4;
    private jwinforms.Button btnBuyMax4;
    private jwinforms.Button btnSellQty5;
    private jwinforms.Button btnSellAll5;
    private jwinforms.Button btnBuyQty5;
    private jwinforms.Button btnBuyMax5;
    private jwinforms.Button btnSellQty6;
    private jwinforms.Button btnSellAll6;
    private jwinforms.Button btnBuyQty6;
    private jwinforms.Button btnBuyMax6;
    private jwinforms.Button btnSellQty7;
    private jwinforms.Button btnSellAll7;
    private jwinforms.Button btnBuyQty7;
    private jwinforms.Button btnBuyMax7;
    private jwinforms.Button btnSellQty8;
    private jwinforms.Button btnSellAll8;
    private jwinforms.Button btnBuyQty8;
    private jwinforms.Button btnBuyMax8;
    private jwinforms.Button btnSellQty9;
    private jwinforms.Button btnSellAll9;
    private jwinforms.Button btnBuyQty9;
    private jwinforms.Button btnBuyMax9;
    private jwinforms.ImageList ilChartImages;
    private jwinforms.ImageList ilDirectionImages;
    private jwinforms.ImageList ilEquipmentImages;
    private jwinforms.ImageList ilShipImages;
    private jwinforms.Label lblBuy;
    private jwinforms.Label lblBuyPrice0;
    private jwinforms.Label lblBuyPrice1;
    private jwinforms.Label lblBuyPrice2;
    private jwinforms.Label lblBuyPrice3;
    private jwinforms.Label lblBuyPrice4;
    private jwinforms.Label lblBuyPrice5;
    private jwinforms.Label lblBuyPrice6;
    private jwinforms.Label lblBuyPrice7;
    private jwinforms.Label lblBuyPrice8;
    private jwinforms.Label lblBuyPrice9;
    private jwinforms.Label lblSell;
    private jwinforms.Label lblSellPrice0;
    private jwinforms.Label lblSellPrice1;
    private jwinforms.Label lblSellPrice2;
    private jwinforms.Label lblSellPrice3;
    private jwinforms.Label lblSellPrice4;
    private jwinforms.Label lblSellPrice5;
    private jwinforms.Label lblSellPrice6;
    private jwinforms.Label lblSellPrice7;
    private jwinforms.Label lblSellPrice8;
    private jwinforms.Label lblSellPrice9;
    private jwinforms.Label lblTargetDiff0;
    private jwinforms.Label lblTargetDiff1;
    private jwinforms.Label lblTargetDiff2;
    private jwinforms.Label lblTargetDiff3;
    private jwinforms.Label lblTargetDiff4;
    private jwinforms.Label lblTargetDiff5;
    private jwinforms.Label lblTargetDiff6;
    private jwinforms.Label lblTargetDiff7;
    private jwinforms.Label lblTargetDiff8;
    private jwinforms.Label lblTargetDiff9;
    private jwinforms.Label lblTargetDiffLabel;
    private jwinforms.Label lblTargetPct0;
    private jwinforms.Label lblTargetPct1;
    private jwinforms.Label lblTargetPct2;
    private jwinforms.Label lblTargetPct3;
    private jwinforms.Label lblTargetPct4;
    private jwinforms.Label lblTargetPct5;
    private jwinforms.Label lblTargetPct6;
    private jwinforms.Label lblTargetPct7;
    private jwinforms.Label lblTargetPct8;
    private jwinforms.Label lblTargetPct9;
    private jwinforms.Label lblTargetPctLabel;
    private jwinforms.Label lblTargetPrice0;
    private jwinforms.Label lblTargetPrice1;
    private jwinforms.Label lblTargetPrice2;
    private jwinforms.Label lblTargetPrice3;
    private jwinforms.Label lblTargetPrice4;
    private jwinforms.Label lblTargetPrice5;
    private jwinforms.Label lblTargetPrice6;
    private jwinforms.Label lblTargetPrice7;
    private jwinforms.Label lblTargetPrice8;
    private jwinforms.Label lblTargetPrice9;
    private jwinforms.Label lblTargetPriceLabel;
    private jwinforms.Label lblTradeCmdty0;
    private jwinforms.Label lblTradeCmdty1;
    private jwinforms.Label lblTradeCmdty2;
    private jwinforms.Label lblTradeCmdty3;
    private jwinforms.Label lblTradeCmdty4;
    private jwinforms.Label lblTradeCmdty5;
    private jwinforms.Label lblTradeCmdty6;
    private jwinforms.Label lblTradeCmdty7;
    private jwinforms.Label lblTradeCmdty8;
    private jwinforms.Label lblTradeCmdty9;
    private jwinforms.Label lblTradeTarget;
    private jwinforms.PictureBox picCargoLine0;
    private jwinforms.PictureBox picCargoLine1;
    private jwinforms.PictureBox picCargoLine2;
    private jwinforms.PictureBox picCargoLine3;
    private jwinforms.IContainer components;
    private Label[] lblSellPrice;
    private Label[] lblBuyPrice;
    private Label[] lblTargetPrice;
    private Label[] lblTargetDiff;
    private Label[] lblTargetPct;
    private Button[] btnSellQty;
    private Button[] btnSellAll;
    private Button[] btnBuyQty;
    private Button[] btnBuyMax;

    void setGame(SpaceTraderGame game, GameController controller) {
        this.game = game;
        this.controller = controller;
    }

    private void finishInit() {
        lblSellPrice = new Label[]{lblSellPrice0, lblSellPrice1, lblSellPrice2, lblSellPrice3, lblSellPrice4,
                lblSellPrice5, lblSellPrice6, lblSellPrice7, lblSellPrice8, lblSellPrice9};

        lblBuyPrice = new Label[]{lblBuyPrice0, lblBuyPrice1, lblBuyPrice2, lblBuyPrice3, lblBuyPrice4, lblBuyPrice5,
                lblBuyPrice6, lblBuyPrice7, lblBuyPrice8, lblBuyPrice9};

        lblTargetPrice = new Label[]{lblTargetPrice0, lblTargetPrice1, lblTargetPrice2, lblTargetPrice3,
                lblTargetPrice4, lblTargetPrice5, lblTargetPrice6, lblTargetPrice7, lblTargetPrice8, lblTargetPrice9};

        lblTargetDiff = new Label[]{lblTargetDiff0, lblTargetDiff1, lblTargetDiff2, lblTargetDiff3, lblTargetDiff4,
                lblTargetDiff5, lblTargetDiff6, lblTargetDiff7, lblTargetDiff8, lblTargetDiff9};

        lblTargetPct = new Label[]{lblTargetPct0, lblTargetPct1, lblTargetPct2, lblTargetPct3, lblTargetPct4,
                lblTargetPct5, lblTargetPct6, lblTargetPct7, lblTargetPct8, lblTargetPct9};

        btnSellQty = new Button[]{btnSellQty0, btnSellQty1, btnSellQty2, btnSellQty3, btnSellQty4, btnSellQty5,
                btnSellQty6, btnSellQty7, btnSellQty8, btnSellQty9};

        btnSellAll = new Button[]{btnSellAll0, btnSellAll1, btnSellAll2, btnSellAll3, btnSellAll4, btnSellAll5,
                btnSellAll6, btnSellAll7, btnSellAll8, btnSellAll9};

        btnBuyQty = new Button[]{btnBuyQty0, btnBuyQty1, btnBuyQty2, btnBuyQty3, btnBuyQty4, btnBuyQty5, btnBuyQty6,
                btnBuyQty7, btnBuyQty8, btnBuyQty9};

        btnBuyMax = new Button[]{btnBuyMax0, btnBuyMax1, btnBuyMax2, btnBuyMax3, btnBuyMax4, btnBuyMax5, btnBuyMax6,
                btnBuyMax7, btnBuyMax8, btnBuyMax9};
    }

    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    void initializeComponent(Map<String, String> strings) {
        components = new jwinforms.Container();
        jwinforms.ResourceManager resources = new jwinforms.ResourceManager(SpaceTrader.class);
        picCargoLine3 = new jwinforms.PictureBox();
        picCargoLine2 = new jwinforms.PictureBox();
        picCargoLine0 = new jwinforms.PictureBox();
        picCargoLine1 = new jwinforms.PictureBox();
        lblTargetPct9 = new jwinforms.Label();
        lblTargetDiff9 = new jwinforms.Label();
        lblTargetPrice9 = new jwinforms.Label();
        btnBuyMax9 = new jwinforms.Button();
        btnBuyQty9 = new jwinforms.Button();
        lblBuyPrice9 = new jwinforms.Label();
        btnSellAll9 = new jwinforms.Button();
        btnSellQty9 = new jwinforms.Button();
        lblSellPrice9 = new jwinforms.Label();
        lblTargetPct8 = new jwinforms.Label();
        lblTargetDiff8 = new jwinforms.Label();
        lblTargetPrice8 = new jwinforms.Label();
        btnBuyMax8 = new jwinforms.Button();
        btnBuyQty8 = new jwinforms.Button();
        lblBuyPrice8 = new jwinforms.Label();
        btnSellAll8 = new jwinforms.Button();
        btnSellQty8 = new jwinforms.Button();
        lblSellPrice8 = new jwinforms.Label();
        lblTargetPct7 = new jwinforms.Label();
        lblTargetDiff7 = new jwinforms.Label();
        lblTargetPrice7 = new jwinforms.Label();
        btnBuyMax7 = new jwinforms.Button();
        btnBuyQty7 = new jwinforms.Button();
        lblBuyPrice7 = new jwinforms.Label();
        btnSellAll7 = new jwinforms.Button();
        btnSellQty7 = new jwinforms.Button();
        lblSellPrice7 = new jwinforms.Label();
        lblTargetPct6 = new jwinforms.Label();
        lblTargetDiff6 = new jwinforms.Label();
        lblTargetPrice6 = new jwinforms.Label();
        btnBuyMax6 = new jwinforms.Button();
        btnBuyQty6 = new jwinforms.Button();
        lblBuyPrice6 = new jwinforms.Label();
        btnSellAll6 = new jwinforms.Button();
        btnSellQty6 = new jwinforms.Button();
        lblSellPrice6 = new jwinforms.Label();
        lblTargetPct5 = new jwinforms.Label();
        lblTargetDiff5 = new jwinforms.Label();
        lblTargetPrice5 = new jwinforms.Label();
        btnBuyMax5 = new jwinforms.Button();
        btnBuyQty5 = new jwinforms.Button();
        lblBuyPrice5 = new jwinforms.Label();
        btnSellAll5 = new jwinforms.Button();
        btnSellQty5 = new jwinforms.Button();
        lblSellPrice5 = new jwinforms.Label();
        lblTargetPct4 = new jwinforms.Label();
        lblTargetDiff4 = new jwinforms.Label();
        lblTargetPrice4 = new jwinforms.Label();
        btnBuyMax4 = new jwinforms.Button();
        btnBuyQty4 = new jwinforms.Button();
        lblBuyPrice4 = new jwinforms.Label();
        btnSellAll4 = new jwinforms.Button();
        btnSellQty4 = new jwinforms.Button();
        lblSellPrice4 = new jwinforms.Label();
        lblTargetPct3 = new jwinforms.Label();
        lblTargetDiff3 = new jwinforms.Label();
        lblTargetPrice3 = new jwinforms.Label();
        btnBuyMax3 = new jwinforms.Button();
        btnBuyQty3 = new jwinforms.Button();
        lblBuyPrice3 = new jwinforms.Label();
        btnSellAll3 = new jwinforms.Button();
        btnSellQty3 = new jwinforms.Button();
        lblSellPrice3 = new jwinforms.Label();
        lblTargetPct2 = new jwinforms.Label();
        lblTargetDiff2 = new jwinforms.Label();
        lblTargetPrice2 = new jwinforms.Label();
        btnBuyMax2 = new jwinforms.Button();
        btnBuyQty2 = new jwinforms.Button();
        lblBuyPrice2 = new jwinforms.Label();
        btnSellAll2 = new jwinforms.Button();
        btnSellQty2 = new jwinforms.Button();
        lblSellPrice2 = new jwinforms.Label();
        lblTargetPct1 = new jwinforms.Label();
        lblTargetDiff1 = new jwinforms.Label();
        lblTargetPrice1 = new jwinforms.Label();
        btnBuyMax1 = new jwinforms.Button();
        btnBuyQty1 = new jwinforms.Button();
        lblBuyPrice1 = new jwinforms.Label();
        lblTargetPctLabel = new jwinforms.Label();
        lblTargetDiffLabel = new jwinforms.Label();
        lblTargetPriceLabel = new jwinforms.Label();
        lblTargetPct0 = new jwinforms.Label();
        lblTargetDiff0 = new jwinforms.Label();
        lblTargetPrice0 = new jwinforms.Label();
        btnBuyMax0 = new jwinforms.Button();
        btnBuyQty0 = new jwinforms.Button();
        lblBuyPrice0 = new jwinforms.Label();
        btnSellAll1 = new jwinforms.Button();
        btnSellQty1 = new jwinforms.Button();
        lblSellPrice1 = new jwinforms.Label();
        btnSellAll0 = new jwinforms.Button();
        btnSellQty0 = new jwinforms.Button();
        lblSellPrice0 = new jwinforms.Label();
        lblTradeTarget = new jwinforms.Label();
        lblBuy = new jwinforms.Label();
        lblSell = new jwinforms.Label();
        lblTradeCmdty9 = new jwinforms.Label();
        lblTradeCmdty8 = new jwinforms.Label();
        lblTradeCmdty2 = new jwinforms.Label();
        lblTradeCmdty0 = new jwinforms.Label();
        lblTradeCmdty1 = new jwinforms.Label();
        lblTradeCmdty6 = new jwinforms.Label();
        lblTradeCmdty5 = new jwinforms.Label();
        lblTradeCmdty4 = new jwinforms.Label();
        lblTradeCmdty3 = new jwinforms.Label();
        lblTradeCmdty7 = new jwinforms.Label();
        ilChartImages = new jwinforms.ImageList(components);
        ilShipImages = new jwinforms.ImageList(components);
        ilDirectionImages = new jwinforms.ImageList(components);
        ilEquipmentImages = new jwinforms.ImageList(components);
        this.suspendLayout();
        //
        // boxCargo
        //
        Anchor = jwinforms.AnchorStyles.Top_Right;
        Controls.add(picCargoLine3);
        Controls.add(picCargoLine2);
        Controls.add(picCargoLine0);
        Controls.add(picCargoLine1);
        Controls.add(lblTargetPct9);
        Controls.add(lblTargetDiff9);
        Controls.add(lblTargetPrice9);
        Controls.add(btnBuyMax9);
        Controls.add(btnBuyQty9);
        Controls.add(lblBuyPrice9);
        Controls.add(btnSellAll9);
        Controls.add(btnSellQty9);
        Controls.add(lblSellPrice9);
        Controls.add(lblTargetPct8);
        Controls.add(lblTargetDiff8);
        Controls.add(lblTargetPrice8);
        Controls.add(btnBuyMax8);
        Controls.add(btnBuyQty8);
        Controls.add(lblBuyPrice8);
        Controls.add(btnSellAll8);
        Controls.add(btnSellQty8);
        Controls.add(lblSellPrice8);
        Controls.add(lblTargetPct7);
        Controls.add(lblTargetDiff7);
        Controls.add(lblTargetPrice7);
        Controls.add(btnBuyMax7);
        Controls.add(btnBuyQty7);
        Controls.add(lblBuyPrice7);
        Controls.add(btnSellAll7);
        Controls.add(btnSellQty7);
        Controls.add(lblSellPrice7);
        Controls.add(lblTargetPct6);
        Controls.add(lblTargetDiff6);
        Controls.add(lblTargetPrice6);
        Controls.add(btnBuyMax6);
        Controls.add(btnBuyQty6);
        Controls.add(lblBuyPrice6);
        Controls.add(btnSellAll6);
        Controls.add(btnSellQty6);
        Controls.add(lblSellPrice6);
        Controls.add(lblTargetPct5);
        Controls.add(lblTargetDiff5);
        Controls.add(lblTargetPrice5);
        Controls.add(btnBuyMax5);
        Controls.add(btnBuyQty5);
        Controls.add(lblBuyPrice5);
        Controls.add(btnSellAll5);
        Controls.add(btnSellQty5);
        Controls.add(lblSellPrice5);
        Controls.add(lblTargetPct4);
        Controls.add(lblTargetDiff4);
        Controls.add(lblTargetPrice4);
        Controls.add(btnBuyMax4);
        Controls.add(btnBuyQty4);
        Controls.add(lblBuyPrice4);
        Controls.add(btnSellAll4);
        Controls.add(btnSellQty4);
        Controls.add(lblSellPrice4);
        Controls.add(lblTargetPct3);
        Controls.add(lblTargetDiff3);
        Controls.add(lblTargetPrice3);
        Controls.add(btnBuyMax3);
        Controls.add(btnBuyQty3);
        Controls.add(lblBuyPrice3);
        Controls.add(btnSellAll3);
        Controls.add(btnSellQty3);
        Controls.add(lblSellPrice3);
        Controls.add(lblTargetPct2);
        Controls.add(lblTargetDiff2);
        Controls.add(lblTargetPrice2);
        Controls.add(btnBuyMax2);
        Controls.add(btnBuyQty2);
        Controls.add(lblBuyPrice2);
        Controls.add(btnSellAll2);
        Controls.add(btnSellQty2);
        Controls.add(lblSellPrice2);
        Controls.add(lblTargetPct1);
        Controls.add(lblTargetDiff1);
        Controls.add(lblTargetPrice1);
        Controls.add(btnBuyMax1);
        Controls.add(btnBuyQty1);
        Controls.add(lblBuyPrice1);
        Controls.add(lblTargetPctLabel);
        Controls.add(lblTargetDiffLabel);
        Controls.add(lblTargetPriceLabel);
        Controls.add(lblTargetPct0);
        Controls.add(lblTargetDiff0);
        Controls.add(lblTargetPrice0);
        Controls.add(btnBuyMax0);
        Controls.add(btnBuyQty0);
        Controls.add(lblBuyPrice0);
        Controls.add(btnSellAll1);
        Controls.add(btnSellQty1);
        Controls.add(lblSellPrice1);
        Controls.add(btnSellAll0);
        Controls.add(btnSellQty0);
        Controls.add(lblSellPrice0);
        Controls.add(lblTradeTarget);
        Controls.add(lblBuy);
        Controls.add(lblSell);
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
        setName("boxCargo");
        setSize(new jwinforms.Size(512, 300));
        setTabIndex(8);
        setTabStop(false);
        setText(strings.get("group.box.cargo.title"));
        //
        // picCargoLine3
        //
        picCargoLine3.setBackColor(Color.darkGray);
        picCargoLine3.setLocation(new Point(8, 52));
        picCargoLine3.setName("picCargoLine3");
        picCargoLine3.setSize(new jwinforms.Size(496, 1));
        picCargoLine3.setTabIndex(131);
        picCargoLine3.setTabStop(false);
        //
        // picCargoLine2
        //
        picCargoLine2.setBackColor(Color.darkGray);
        picCargoLine2.setLocation(new Point(352, 32));
        picCargoLine2.setName("picCargoLine2");
        picCargoLine2.setSize(new jwinforms.Size(1, 262));
        picCargoLine2.setTabIndex(130);
        picCargoLine2.setTabStop(false);
        //
        // picCargoLine0
        //
        picCargoLine0.setBackColor(Color.darkGray);
        picCargoLine0.setLocation(new Point(71, 32));
        picCargoLine0.setName("picCargoLine0");
        picCargoLine0.setSize(new jwinforms.Size(1, 262));
        picCargoLine0.setTabIndex(129);
        picCargoLine0.setTabStop(false);
        //
        // picCargoLine1
        //
        picCargoLine1.setBackColor(Color.darkGray);
        picCargoLine1.setLocation(new Point(218, 32));
        picCargoLine1.setName("picCargoLine1");
        picCargoLine1.setSize(new jwinforms.Size(1, 262));
        picCargoLine1.setTabIndex(128);
        picCargoLine1.setTabStop(false);
        //
        // lblTargetPct9
        //
        lblTargetPct9.setLocation(new Point(466, 276));
        lblTargetPct9.setName("lblTargetPct9");
        lblTargetPct9.setSize(new jwinforms.Size(37, 13));
        lblTargetPct9.setTabIndex(127);
        lblTargetPct9.setText("--------");
        lblTargetPct9.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff9
        //
        lblTargetDiff9.setLocation(new Point(410, 276));
        lblTargetDiff9.setName("lblTargetDiff9");
        lblTargetDiff9.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff9.setTabIndex(126);
        lblTargetDiff9.setText("------------");
        lblTargetDiff9.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice9
        //
        lblTargetPrice9.setLocation(new Point(358, 276));
        lblTargetPrice9.setName("lblTargetPrice9");
        lblTargetPrice9.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice9.setTabIndex(125);
        lblTargetPrice9.setText("-----------");
        lblTargetPrice9.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax9
        //
        btnBuyMax9.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax9.setLocation(new Point(262, 272));
        btnBuyMax9.setName("btnBuyMax9");
        btnBuyMax9.setSize(new jwinforms.Size(36, 22));
        btnBuyMax9.setTabIndex(51);
        btnBuyMax9.setText(strings.get("group.box.cargo.max"));
        btnBuyMax9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });

        //
        // btnBuyQty9
        //
        btnBuyQty9.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty9.setLocation(new Point(227, 272));
        btnBuyQty9.setName("btnBuyQty9");
        btnBuyQty9.setSize(new jwinforms.Size(28, 22));
        btnBuyQty9.setTabIndex(50);
        btnBuyQty9.setText("88");
        btnBuyQty9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice9
        //
        lblBuyPrice9.setLocation(new Point(302, 276));
        lblBuyPrice9.setName("lblBuyPrice9");
        lblBuyPrice9.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice9.setTabIndex(122);
        lblBuyPrice9.setText(strings.get("group.box.cargo.not.sold"));
        lblBuyPrice9.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll9
        //
        btnSellAll9.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll9.setLocation(new Point(115, 272));
        btnSellAll9.setName("btnSellAll9");
        btnSellAll9.setSize(new jwinforms.Size(44, 22));
        btnSellAll9.setTabIndex(49);
        btnSellAll9.setText(strings.get("group.box.cargo.dump"));
        btnSellAll9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty9
        //
        btnSellQty9.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty9.setLocation(new Point(80, 272));
        btnSellQty9.setName("btnSellQty9");
        btnSellQty9.setSize(new jwinforms.Size(28, 22));
        btnSellQty9.setTabIndex(48);
        btnSellQty9.setText("88");
        btnSellQty9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice9
        //
        lblSellPrice9.setLocation(new Point(163, 276));
        lblSellPrice9.setName("lblSellPrice9");
        lblSellPrice9.setSize(new jwinforms.Size(48, 13));
        lblSellPrice9.setTabIndex(119);
        lblSellPrice9.setText(strings.get("group.box.cargo.no.trade"));
        lblSellPrice9.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct8
        //
        lblTargetPct8.setLocation(new Point(466, 252));
        lblTargetPct8.setName("lblTargetPct8");
        lblTargetPct8.setSize(new jwinforms.Size(37, 13));
        lblTargetPct8.setTabIndex(118);
        lblTargetPct8.setText("-888%");
        lblTargetPct8.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff8
        //
        lblTargetDiff8.setLocation(new Point(410, 252));
        lblTargetDiff8.setName("lblTargetDiff8");
        lblTargetDiff8.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff8.setTabIndex(117);
        lblTargetDiff8.setText("-8,888 cr.");
        lblTargetDiff8.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice8
        //
        lblTargetPrice8.setLocation(new Point(358, 252));
        lblTargetPrice8.setName("lblTargetPrice8");
        lblTargetPrice8.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice8.setTabIndex(116);
        lblTargetPrice8.setText("8,888 cr.");
        lblTargetPrice8.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax8
        //
        btnBuyMax8.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax8.setLocation(new Point(262, 248));
        btnBuyMax8.setName("btnBuyMax8");
        btnBuyMax8.setSize(new jwinforms.Size(36, 22));
        btnBuyMax8.setTabIndex(47);
        btnBuyMax8.setText(strings.get("group.box.cargo.max"));
        btnBuyMax8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty8
        //
        btnBuyQty8.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty8.setLocation(new Point(227, 248));
        btnBuyQty8.setName("btnBuyQty8");
        btnBuyQty8.setSize(new jwinforms.Size(28, 22));
        btnBuyQty8.setTabIndex(46);
        btnBuyQty8.setText("88");
        btnBuyQty8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice8
        //
        lblBuyPrice8.setLocation(new Point(302, 252));
        lblBuyPrice8.setName("lblBuyPrice8");
        lblBuyPrice8.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice8.setTabIndex(113);
        lblBuyPrice8.setText("8,888 cr.");
        lblBuyPrice8.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll8
        //
        btnSellAll8.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll8.setLocation(new Point(115, 248));
        btnSellAll8.setName("btnSellAll8");
        btnSellAll8.setSize(new jwinforms.Size(44, 22));
        btnSellAll8.setTabIndex(45);
        btnSellAll8.setText(strings.get("group.box.cargo.all"));
        btnSellAll8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty8
        //
        btnSellQty8.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty8.setLocation(new Point(80, 248));
        btnSellQty8.setName("btnSellQty8");
        btnSellQty8.setSize(new jwinforms.Size(28, 22));
        btnSellQty8.setTabIndex(44);
        btnSellQty8.setText("88");
        btnSellQty8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice8
        //
        lblSellPrice8.setLocation(new Point(163, 252));
        lblSellPrice8.setName("lblSellPrice8");
        lblSellPrice8.setSize(new jwinforms.Size(48, 13));
        lblSellPrice8.setTabIndex(110);
        lblSellPrice8.setText("8,888 cr.");
        lblSellPrice8.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct7
        //
        lblTargetPct7.setLocation(new Point(466, 228));
        lblTargetPct7.setName("lblTargetPct7");
        lblTargetPct7.setSize(new jwinforms.Size(37, 13));
        lblTargetPct7.setTabIndex(109);
        lblTargetPct7.setText("-888%");
        lblTargetPct7.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff7
        //
        lblTargetDiff7.setFont(FontCollection.regular825);
        lblTargetDiff7.setLocation(new Point(410, 228));
        lblTargetDiff7.setName("lblTargetDiff7");
        lblTargetDiff7.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff7.setTabIndex(108);
        lblTargetDiff7.setText("-8,888 cr.");
        lblTargetDiff7.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice7
        //
        lblTargetPrice7.setLocation(new Point(358, 228));
        lblTargetPrice7.setName("lblTargetPrice7");
        lblTargetPrice7.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice7.setTabIndex(107);
        lblTargetPrice7.setText("8,888 cr.");
        lblTargetPrice7.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax7
        //
        btnBuyMax7.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax7.setLocation(new Point(262, 224));
        btnBuyMax7.setName("btnBuyMax7");
        btnBuyMax7.setSize(new jwinforms.Size(36, 22));
        btnBuyMax7.setTabIndex(43);
        btnBuyMax7.setText(strings.get("group.box.cargo.max"));
        btnBuyMax7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty7
        //
        btnBuyQty7.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty7.setLocation(new Point(227, 224));
        btnBuyQty7.setName("btnBuyQty7");
        btnBuyQty7.setSize(new jwinforms.Size(28, 22));
        btnBuyQty7.setTabIndex(42);
        btnBuyQty7.setText("88");
        btnBuyQty7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice7
        //
        lblBuyPrice7.setLocation(new Point(302, 228));
        lblBuyPrice7.setName("lblBuyPrice7");
        lblBuyPrice7.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice7.setTabIndex(104);
        lblBuyPrice7.setText("8,888 cr.");
        lblBuyPrice7.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll7
        //
        btnSellAll7.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll7.setLocation(new Point(115, 224));
        btnSellAll7.setName("btnSellAll7");
        btnSellAll7.setSize(new jwinforms.Size(44, 22));
        btnSellAll7.setTabIndex(41);
        btnSellAll7.setText(strings.get("group.box.cargo.all"));
        btnSellAll7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty7
        //
        btnSellQty7.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty7.setLocation(new Point(80, 224));
        btnSellQty7.setName("btnSellQty7");
        btnSellQty7.setSize(new jwinforms.Size(28, 22));
        btnSellQty7.setTabIndex(40);
        btnSellQty7.setText("88");
        btnSellQty7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice7
        //
        lblSellPrice7.setLocation(new Point(163, 228));
        lblSellPrice7.setName("lblSellPrice7");
        lblSellPrice7.setSize(new jwinforms.Size(48, 13));
        lblSellPrice7.setTabIndex(101);
        lblSellPrice7.setText("8,888 cr.");
        lblSellPrice7.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct6
        //
        lblTargetPct6.setLocation(new Point(466, 204));
        lblTargetPct6.setName("lblTargetPct6");
        lblTargetPct6.setSize(new jwinforms.Size(37, 13));
        lblTargetPct6.setTabIndex(100);
        lblTargetPct6.setText("-888%");
        lblTargetPct6.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff6
        //
        lblTargetDiff6.setLocation(new Point(410, 204));
        lblTargetDiff6.setName("lblTargetDiff6");
        lblTargetDiff6.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff6.setTabIndex(99);
        lblTargetDiff6.setText("-8,888 cr.");
        lblTargetDiff6.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice6
        //
        lblTargetPrice6.setLocation(new Point(358, 204));
        lblTargetPrice6.setName("lblTargetPrice6");
        lblTargetPrice6.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice6.setTabIndex(98);
        lblTargetPrice6.setText("8,888 cr.");
        lblTargetPrice6.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax6
        //
        btnBuyMax6.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax6.setLocation(new Point(262, 200));
        btnBuyMax6.setName("btnBuyMax6");
        btnBuyMax6.setSize(new jwinforms.Size(36, 22));
        btnBuyMax6.setTabIndex(39);
        btnBuyMax6.setText(strings.get("group.box.cargo.max"));
        btnBuyMax6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty6
        //
        btnBuyQty6.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty6.setLocation(new Point(227, 200));
        btnBuyQty6.setName("btnBuyQty6");
        btnBuyQty6.setSize(new jwinforms.Size(28, 22));
        btnBuyQty6.setTabIndex(38);
        btnBuyQty6.setText("88");
        btnBuyQty6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice6
        //
        lblBuyPrice6.setLocation(new Point(302, 204));
        lblBuyPrice6.setName("lblBuyPrice6");
        lblBuyPrice6.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice6.setTabIndex(95);
        lblBuyPrice6.setText("8,888 cr.");
        lblBuyPrice6.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll6
        //
        btnSellAll6.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll6.setLocation(new Point(115, 200));
        btnSellAll6.setName("btnSellAll6");
        btnSellAll6.setSize(new jwinforms.Size(44, 22));
        btnSellAll6.setTabIndex(37);
        btnSellAll6.setText(strings.get("group.box.cargo.all"));
        btnSellAll6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty6
        //
        btnSellQty6.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty6.setLocation(new Point(80, 200));
        btnSellQty6.setName("btnSellQty6");
        btnSellQty6.setSize(new jwinforms.Size(28, 22));
        btnSellQty6.setTabIndex(36);
        btnSellQty6.setText("88");
        btnSellQty6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice6
        //
        lblSellPrice6.setLocation(new Point(163, 204));
        lblSellPrice6.setName("lblSellPrice6");
        lblSellPrice6.setSize(new jwinforms.Size(48, 13));
        lblSellPrice6.setTabIndex(92);
        lblSellPrice6.setText("8,888 cr.");
        lblSellPrice6.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct5
        //
        lblTargetPct5.setLocation(new Point(466, 180));
        lblTargetPct5.setName("lblTargetPct5");
        lblTargetPct5.setSize(new jwinforms.Size(37, 13));
        lblTargetPct5.setTabIndex(91);
        lblTargetPct5.setText("-888%");
        lblTargetPct5.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff5
        //
        lblTargetDiff5.setLocation(new Point(410, 180));
        lblTargetDiff5.setName("lblTargetDiff5");
        lblTargetDiff5.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff5.setTabIndex(90);
        lblTargetDiff5.setText("-8,888 cr.");
        lblTargetDiff5.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice5
        //
        lblTargetPrice5.setLocation(new Point(358, 180));
        lblTargetPrice5.setName("lblTargetPrice5");
        lblTargetPrice5.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice5.setTabIndex(89);
        lblTargetPrice5.setText("8,888 cr.");
        lblTargetPrice5.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax5
        //
        btnBuyMax5.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax5.setLocation(new Point(262, 176));
        btnBuyMax5.setName("btnBuyMax5");
        btnBuyMax5.setSize(new jwinforms.Size(36, 22));
        btnBuyMax5.setTabIndex(35);
        btnBuyMax5.setText(strings.get("group.box.cargo.max"));
        btnBuyMax5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty5
        //
        btnBuyQty5.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty5.setLocation(new Point(227, 176));
        btnBuyQty5.setName("btnBuyQty5");
        btnBuyQty5.setSize(new jwinforms.Size(28, 22));
        btnBuyQty5.setTabIndex(34);
        btnBuyQty5.setText("88");
        btnBuyQty5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice5
        //
        lblBuyPrice5.setLocation(new Point(302, 180));
        lblBuyPrice5.setName("lblBuyPrice5");
        lblBuyPrice5.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice5.setTabIndex(86);
        lblBuyPrice5.setText("8,888 cr.");
        lblBuyPrice5.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll5
        //
        btnSellAll5.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll5.setLocation(new Point(115, 176));
        btnSellAll5.setName("btnSellAll5");
        btnSellAll5.setSize(new jwinforms.Size(44, 22));
        btnSellAll5.setTabIndex(33);
        btnSellAll5.setText(strings.get("group.box.cargo.all"));
        btnSellAll5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty5
        //
        btnSellQty5.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty5.setLocation(new Point(80, 176));
        btnSellQty5.setName("btnSellQty5");
        btnSellQty5.setSize(new jwinforms.Size(28, 22));
        btnSellQty5.setTabIndex(32);
        btnSellQty5.setText("88");
        btnSellQty5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice5
        //
        lblSellPrice5.setLocation(new Point(163, 180));
        lblSellPrice5.setName("lblSellPrice5");
        lblSellPrice5.setSize(new jwinforms.Size(48, 13));
        lblSellPrice5.setTabIndex(83);
        lblSellPrice5.setText("8,888 cr.");
        lblSellPrice5.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct4
        //
        lblTargetPct4.setLocation(new Point(466, 156));
        lblTargetPct4.setName("lblTargetPct4");
        lblTargetPct4.setSize(new jwinforms.Size(37, 13));
        lblTargetPct4.setTabIndex(82);
        lblTargetPct4.setText("-888%");
        lblTargetPct4.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff4
        //
        lblTargetDiff4.setLocation(new Point(410, 156));
        lblTargetDiff4.setName("lblTargetDiff4");
        lblTargetDiff4.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff4.setTabIndex(81);
        lblTargetDiff4.setText("-8,888 cr.");
        lblTargetDiff4.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice4
        //
        lblTargetPrice4.setLocation(new Point(358, 156));
        lblTargetPrice4.setName("lblTargetPrice4");
        lblTargetPrice4.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice4.setTabIndex(80);
        lblTargetPrice4.setText("8,888 cr.");
        lblTargetPrice4.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax4
        //
        btnBuyMax4.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax4.setLocation(new Point(262, 152));
        btnBuyMax4.setName("btnBuyMax4");
        btnBuyMax4.setSize(new jwinforms.Size(36, 22));
        btnBuyMax4.setTabIndex(31);
        btnBuyMax4.setText(strings.get("group.box.cargo.max"));
        btnBuyMax4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty4
        //
        btnBuyQty4.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty4.setLocation(new Point(227, 152));
        btnBuyQty4.setName("btnBuyQty4");
        btnBuyQty4.setSize(new jwinforms.Size(28, 22));
        btnBuyQty4.setTabIndex(30);
        btnBuyQty4.setText("88");
        btnBuyQty4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice4
        //
        lblBuyPrice4.setLocation(new Point(302, 156));
        lblBuyPrice4.setName("lblBuyPrice4");
        lblBuyPrice4.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice4.setTabIndex(77);
        lblBuyPrice4.setText("8,888 cr.");
        lblBuyPrice4.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll4
        //
        btnSellAll4.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll4.setLocation(new Point(115, 152));
        btnSellAll4.setName("btnSellAll4");
        btnSellAll4.setSize(new jwinforms.Size(44, 22));
        btnSellAll4.setTabIndex(29);
        btnSellAll4.setText(strings.get("group.box.cargo.all"));
        btnSellAll4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty4
        //
        btnSellQty4.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty4.setLocation(new Point(80, 152));
        btnSellQty4.setName("btnSellQty4");
        btnSellQty4.setSize(new jwinforms.Size(28, 22));
        btnSellQty4.setTabIndex(28);
        btnSellQty4.setText("88");
        btnSellQty4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice4
        //
        lblSellPrice4.setLocation(new Point(163, 156));
        lblSellPrice4.setName("lblSellPrice4");
        lblSellPrice4.setSize(new jwinforms.Size(48, 13));
        lblSellPrice4.setTabIndex(74);
        lblSellPrice4.setText("8,888 cr.");
        lblSellPrice4.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct3
        //
        lblTargetPct3.setLocation(new Point(466, 132));
        lblTargetPct3.setName("lblTargetPct3");
        lblTargetPct3.setSize(new jwinforms.Size(37, 13));
        lblTargetPct3.setTabIndex(73);
        lblTargetPct3.setText("-888%");
        lblTargetPct3.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff3
        //
        lblTargetDiff3.setLocation(new Point(410, 132));
        lblTargetDiff3.setName("lblTargetDiff3");
        lblTargetDiff3.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff3.setTabIndex(72);
        lblTargetDiff3.setText("-8,888 cr.");
        lblTargetDiff3.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice3
        //
        lblTargetPrice3.setLocation(new Point(358, 132));
        lblTargetPrice3.setName("lblTargetPrice3");
        lblTargetPrice3.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice3.setTabIndex(71);
        lblTargetPrice3.setText("8,888 cr.");
        lblTargetPrice3.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax3
        //
        btnBuyMax3.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax3.setLocation(new Point(262, 128));
        btnBuyMax3.setName("btnBuyMax3");
        btnBuyMax3.setSize(new jwinforms.Size(36, 22));
        btnBuyMax3.setTabIndex(27);
        btnBuyMax3.setText(strings.get("group.box.cargo.max"));
        btnBuyMax3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty3
        //
        btnBuyQty3.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty3.setLocation(new Point(227, 128));
        btnBuyQty3.setName("btnBuyQty3");
        btnBuyQty3.setSize(new jwinforms.Size(28, 22));
        btnBuyQty3.setTabIndex(26);
        btnBuyQty3.setText("88");
        btnBuyQty3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice3
        //
        lblBuyPrice3.setLocation(new Point(302, 132));
        lblBuyPrice3.setName("lblBuyPrice3");
        lblBuyPrice3.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice3.setTabIndex(68);
        lblBuyPrice3.setText("8,888 cr.");
        lblBuyPrice3.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll3
        //
        btnSellAll3.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll3.setLocation(new Point(115, 128));
        btnSellAll3.setName("btnSellAll3");
        btnSellAll3.setSize(new jwinforms.Size(44, 22));
        btnSellAll3.setTabIndex(25);
        btnSellAll3.setText(strings.get("group.box.cargo.all"));
        btnSellAll3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty3
        //
        btnSellQty3.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty3.setLocation(new Point(80, 128));
        btnSellQty3.setName("btnSellQty3");
        btnSellQty3.setSize(new jwinforms.Size(28, 22));
        btnSellQty3.setTabIndex(24);
        btnSellQty3.setText("88");
        btnSellQty3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice3
        //
        lblSellPrice3.setLocation(new Point(163, 132));
        lblSellPrice3.setName("lblSellPrice3");
        lblSellPrice3.setSize(new jwinforms.Size(48, 13));
        lblSellPrice3.setTabIndex(65);
        lblSellPrice3.setText("8,888 cr.");
        lblSellPrice3.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct2
        //
        lblTargetPct2.setLocation(new Point(466, 108));
        lblTargetPct2.setName("lblTargetPct2");
        lblTargetPct2.setSize(new jwinforms.Size(37, 13));
        lblTargetPct2.setTabIndex(64);
        lblTargetPct2.setText("-888%");
        lblTargetPct2.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff2
        //
        lblTargetDiff2.setLocation(new Point(410, 108));
        lblTargetDiff2.setName("lblTargetDiff2");
        lblTargetDiff2.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff2.setTabIndex(63);
        lblTargetDiff2.setText("-8,888 cr.");
        lblTargetDiff2.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice2
        //
        lblTargetPrice2.setLocation(new Point(358, 108));
        lblTargetPrice2.setName("lblTargetPrice2");
        lblTargetPrice2.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice2.setTabIndex(62);
        lblTargetPrice2.setText("8,888 cr.");
        lblTargetPrice2.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax2
        //
        btnBuyMax2.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax2.setLocation(new Point(262, 104));
        btnBuyMax2.setName("btnBuyMax2");
        btnBuyMax2.setSize(new jwinforms.Size(36, 22));
        btnBuyMax2.setTabIndex(23);
        btnBuyMax2.setText(strings.get("group.box.cargo.max"));
        btnBuyMax2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty2
        //
        btnBuyQty2.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty2.setLocation(new Point(227, 104));
        btnBuyQty2.setName("btnBuyQty2");
        btnBuyQty2.setSize(new jwinforms.Size(28, 22));
        btnBuyQty2.setTabIndex(22);
        btnBuyQty2.setText("88");
        btnBuyQty2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice2
        //
        lblBuyPrice2.setLocation(new Point(302, 108));
        lblBuyPrice2.setName("lblBuyPrice2");
        lblBuyPrice2.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice2.setTabIndex(59);
        lblBuyPrice2.setText("8,888 cr.");
        lblBuyPrice2.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll2
        //
        btnSellAll2.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll2.setLocation(new Point(115, 104));
        btnSellAll2.setName("btnSellAll2");
        btnSellAll2.setSize(new jwinforms.Size(44, 22));
        btnSellAll2.setTabIndex(21);
        btnSellAll2.setText(strings.get("group.box.cargo.all"));
        btnSellAll2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty2
        //
        btnSellQty2.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty2.setLocation(new Point(80, 104));
        btnSellQty2.setName("btnSellQty2");
        btnSellQty2.setSize(new jwinforms.Size(28, 22));
        btnSellQty2.setTabIndex(20);
        btnSellQty2.setText("88");
        btnSellQty2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice2
        //
        lblSellPrice2.setLocation(new Point(163, 108));
        lblSellPrice2.setName("lblSellPrice2");
        lblSellPrice2.setSize(new jwinforms.Size(48, 13));
        lblSellPrice2.setTabIndex(56);
        lblSellPrice2.setText("8,888 cr.");
        lblSellPrice2.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPct1
        //
        lblTargetPct1.setLocation(new Point(466, 84));
        lblTargetPct1.setName("lblTargetPct1");
        lblTargetPct1.setSize(new jwinforms.Size(37, 13));
        lblTargetPct1.setTabIndex(55);
        lblTargetPct1.setText("-888%");
        lblTargetPct1.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff1
        //
        lblTargetDiff1.setLocation(new Point(410, 84));
        lblTargetDiff1.setName("lblTargetDiff1");
        lblTargetDiff1.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff1.setTabIndex(54);
        lblTargetDiff1.setText("-8,888 cr.");
        lblTargetDiff1.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice1
        //
        lblTargetPrice1.setLocation(new Point(358, 84));
        lblTargetPrice1.setName("lblTargetPrice1");
        lblTargetPrice1.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice1.setTabIndex(53);
        lblTargetPrice1.setText("8,888 cr.");
        lblTargetPrice1.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax1
        //
        btnBuyMax1.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax1.setLocation(new Point(262, 80));
        btnBuyMax1.setName("btnBuyMax1");
        btnBuyMax1.setSize(new jwinforms.Size(36, 22));
        btnBuyMax1.setTabIndex(19);
        btnBuyMax1.setText(strings.get("group.box.cargo.max"));
        btnBuyMax1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty1
        //
        btnBuyQty1.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty1.setLocation(new Point(227, 80));
        btnBuyQty1.setName("btnBuyQty1");
        btnBuyQty1.setSize(new jwinforms.Size(28, 22));
        btnBuyQty1.setTabIndex(18);
        btnBuyQty1.setText("88");
        btnBuyQty1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice1
        //
        lblBuyPrice1.setLocation(new Point(302, 84));
        lblBuyPrice1.setName("lblBuyPrice1");
        lblBuyPrice1.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice1.setTabIndex(50);
        lblBuyPrice1.setText("8,888 cr.");
        lblBuyPrice1.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPctLabel
        //
        lblTargetPctLabel.setAutoSize(true);
        lblTargetPctLabel.setLocation(new Point(476, 34));
        lblTargetPctLabel.setName("lblTargetPctLabel");
        lblTargetPctLabel.setSize(new jwinforms.Size(14, 16));
        lblTargetPctLabel.setTabIndex(49);
        lblTargetPctLabel.setText("%");
        //
        // lblTargetDiffLabel
        //
        lblTargetDiffLabel.setAutoSize(true);
        lblTargetDiffLabel.setLocation(new Point(424, 34));
        lblTargetDiffLabel.setName("lblTargetDiffLabel");
        lblTargetDiffLabel.setSize(new jwinforms.Size(18, 16));
        lblTargetDiffLabel.setTabIndex(48);
        lblTargetDiffLabel.setText("+/-");
        //
        // lblTargetPriceLabel
        //
        lblTargetPriceLabel.setAutoSize(true);
        lblTargetPriceLabel.setLocation(new Point(360, 34));
        lblTargetPriceLabel.setName("lblTargetPriceLabel");
        lblTargetPriceLabel.setSize(new jwinforms.Size(30, 16));
        lblTargetPriceLabel.setTabIndex(47);
        lblTargetPriceLabel.setText(strings.get("group.box.cargo.price"));
        //
        // lblTargetPct0
        //
        lblTargetPct0.setLocation(new Point(466, 60));
        lblTargetPct0.setName("lblTargetPct0");
        lblTargetPct0.setSize(new jwinforms.Size(37, 13));
        lblTargetPct0.setTabIndex(46);
        lblTargetPct0.setText("-888%");
        lblTargetPct0.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetDiff0
        //
        lblTargetDiff0.setLocation(new Point(410, 60));
        lblTargetDiff0.setName("lblTargetDiff0");
        lblTargetDiff0.setSize(new jwinforms.Size(52, 13));
        lblTargetDiff0.setTabIndex(45);
        lblTargetDiff0.setText("-8,888 cr.");
        lblTargetDiff0.TextAlign = ContentAlignment.TopRight;
        //
        // lblTargetPrice0
        //
        lblTargetPrice0.setLocation(new Point(358, 60));
        lblTargetPrice0.setName("lblTargetPrice0");
        lblTargetPrice0.setSize(new jwinforms.Size(48, 13));
        lblTargetPrice0.setTabIndex(44);
        lblTargetPrice0.setText("8,888 cr.");
        lblTargetPrice0.TextAlign = ContentAlignment.TopRight;
        //
        // btnBuyMax0
        //
        btnBuyMax0.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyMax0.setLocation(new Point(262, 56));
        btnBuyMax0.setName("btnBuyMax0");
        btnBuyMax0.setSize(new jwinforms.Size(36, 22));
        btnBuyMax0.setTabIndex(15);
        btnBuyMax0.setText(strings.get("group.box.cargo.max"));
        btnBuyMax0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnBuyQty0
        //
        btnBuyQty0.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBuyQty0.setLocation(new Point(227, 56));
        btnBuyQty0.setName("btnBuyQty0");
        btnBuyQty0.setSize(new jwinforms.Size(28, 22));
        btnBuyQty0.setTabIndex(14);
        btnBuyQty0.setText("88");
        btnBuyQty0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblBuyPrice0
        //
        lblBuyPrice0.setLocation(new Point(302, 60));
        lblBuyPrice0.setName("lblBuyPrice0");
        lblBuyPrice0.setSize(new jwinforms.Size(48, 13));
        lblBuyPrice0.setTabIndex(41);
        lblBuyPrice0.setText("8,888 cr.");
        lblBuyPrice0.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll1
        //
        btnSellAll1.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll1.setLocation(new Point(115, 80));
        btnSellAll1.setName("btnSellAll1");
        btnSellAll1.setSize(new jwinforms.Size(44, 22));
        btnSellAll1.setTabIndex(17);
        btnSellAll1.setText(strings.get("group.box.cargo.all"));
        btnSellAll1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty1
        //
        btnSellQty1.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty1.setLocation(new Point(80, 80));
        btnSellQty1.setName("btnSellQty1");
        btnSellQty1.setSize(new jwinforms.Size(28, 22));
        btnSellQty1.setTabIndex(16);
        btnSellQty1.setText("88");
        btnSellQty1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice1
        //
        lblSellPrice1.setLocation(new Point(163, 84));
        lblSellPrice1.setName("lblSellPrice1");
        lblSellPrice1.setSize(new jwinforms.Size(48, 13));
        lblSellPrice1.setTabIndex(38);
        lblSellPrice1.setText("8,888 cr.");
        lblSellPrice1.TextAlign = ContentAlignment.TopRight;
        //
        // btnSellAll0
        //
        btnSellAll0.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellAll0.setLocation(new Point(115, 56));
        btnSellAll0.setName("btnSellAll0");
        btnSellAll0.setSize(new jwinforms.Size(44, 22));
        btnSellAll0.setTabIndex(13);
        btnSellAll0.setText(strings.get("group.box.cargo.all"));
        btnSellAll0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // btnSellQty0
        //
        btnSellQty0.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSellQty0.setLocation(new Point(80, 56));
        btnSellQty0.setName("btnSellQty0");
        btnSellQty0.setSize(new jwinforms.Size(28, 22));
        btnSellQty0.setTabIndex(12);
        btnSellQty0.setText("88");
        btnSellQty0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBuySell_Click(((Button) sender).getName());
            }
        });
        //
        // lblSellPrice0
        //
        lblSellPrice0.setLocation(new Point(163, 60));
        lblSellPrice0.setName("lblSellPrice0");
        lblSellPrice0.setSize(new jwinforms.Size(48, 13));
        lblSellPrice0.setTabIndex(35);
        lblSellPrice0.setText("8,888 cr.");
        lblSellPrice0.TextAlign = ContentAlignment.TopRight;
        //
        // lblTradeTarget
        //
        lblTradeTarget.setAutoSize(true);
        lblTradeTarget.setLocation(new Point(391, 16));
        lblTradeTarget.setName("lblTradeTarget");
        lblTradeTarget.setSize(new jwinforms.Size(78, 16));
        lblTradeTarget.setTabIndex(28);
        lblTradeTarget.setText(strings.get("group.box.cargo.target.system"));
        //
        // lblBuy
        //
        lblBuy.setAutoSize(true);
        lblBuy.setLocation(new Point(273, 34));
        lblBuy.setName("lblBuy");
        lblBuy.setSize(new jwinforms.Size(24, 16));
        lblBuy.setTabIndex(27);
        lblBuy.setText(strings.get("group.box.cargo.buy"));
        //
        // lblSell
        //
        lblSell.setAutoSize(true);
        lblSell.setFont(FontCollection.regular825);
        lblSell.setLocation(new Point(132, 34));
        lblSell.setName("lblSell");
        lblSell.setSize(new jwinforms.Size(23, 16));
        lblSell.setTabIndex(26);
        lblSell.setText(strings.get("group.box.cargo.sell"));
        //
        // lblTradeCmdty9
        //
        lblTradeCmdty9.setAutoSize(true);
        lblTradeCmdty9.setLocation(new Point(8, 276));
        lblTradeCmdty9.setName("lblTradeCmdty9");
        lblTradeCmdty9.setSize(new jwinforms.Size(40, 16));
        lblTradeCmdty9.setTabIndex(25);
        lblTradeCmdty9.setText(strings.get("group.box.cargo.robots"));
        //
        // lblTradeCmdty8
        //
        lblTradeCmdty8.setAutoSize(true);
        lblTradeCmdty8.setLocation(new Point(8, 252));
        lblTradeCmdty8.setName("lblTradeCmdty8");
        lblTradeCmdty8.setSize(new jwinforms.Size(51, 16));
        lblTradeCmdty8.setTabIndex(24);
        lblTradeCmdty8.setText(strings.get("group.box.cargo.narcotics"));
        //
        // lblTradeCmdty2
        //
        lblTradeCmdty2.setAutoSize(true);
        lblTradeCmdty2.setLocation(new Point(8, 108));
        lblTradeCmdty2.setName("lblTradeCmdty2");
        lblTradeCmdty2.setSize(new jwinforms.Size(30, 16));
        lblTradeCmdty2.setTabIndex(23);
        lblTradeCmdty2.setText(strings.get("group.box.cargo.food"));
        //
        // lblTradeCmdty0
        //
        lblTradeCmdty0.setAutoSize(true);
        lblTradeCmdty0.setLocation(new Point(8, 60));
        lblTradeCmdty0.setName("lblTradeCmdty0");
        lblTradeCmdty0.setSize(new jwinforms.Size(34, 16));
        lblTradeCmdty0.setTabIndex(22);
        lblTradeCmdty0.setText(strings.get("group.box.cargo.water"));
        //
        // lblTradeCmdty1
        //
        lblTradeCmdty1.setAutoSize(true);
        lblTradeCmdty1.setLocation(new Point(8, 84));
        lblTradeCmdty1.setName("lblTradeCmdty1");
        lblTradeCmdty1.setSize(new jwinforms.Size(27, 16));
        lblTradeCmdty1.setTabIndex(21);
        lblTradeCmdty1.setText(strings.get("group.box.cargo.furs"));
        //
        // lblTradeCmdty6
        //
        lblTradeCmdty6.setAutoSize(true);
        lblTradeCmdty6.setLocation(new Point(8, 204));
        lblTradeCmdty6.setName("lblTradeCmdty6");
        lblTradeCmdty6.setSize(new jwinforms.Size(50, 16));
        lblTradeCmdty6.setTabIndex(20);
        lblTradeCmdty6.setText(strings.get("group.box.cargo.medicine"));
        //
        // lblTradeCmdty5
        //
        lblTradeCmdty5.setAutoSize(true);
        lblTradeCmdty5.setLocation(new Point(8, 180));
        lblTradeCmdty5.setName("lblTradeCmdty5");
        lblTradeCmdty5.setSize(new jwinforms.Size(49, 16));
        lblTradeCmdty5.setTabIndex(19);
        lblTradeCmdty5.setText(strings.get("group.box.cargo.firearms"));
        //
        // lblTradeCmdty4
        //
        lblTradeCmdty4.setAutoSize(true);
        lblTradeCmdty4.setLocation(new Point(8, 156));
        lblTradeCmdty4.setName("lblTradeCmdty4");
        lblTradeCmdty4.setSize(new jwinforms.Size(41, 16));
        lblTradeCmdty4.setTabIndex(18);
        lblTradeCmdty4.setText(strings.get("group.box.cargo.games"));
        //
        // lblTradeCmdty3
        //
        lblTradeCmdty3.setAutoSize(true);
        lblTradeCmdty3.setLocation(new Point(8, 132));
        lblTradeCmdty3.setName("lblTradeCmdty3");
        lblTradeCmdty3.setSize(new jwinforms.Size(23, 16));
        lblTradeCmdty3.setTabIndex(17);
        lblTradeCmdty3.setText(strings.get("group.box.cargo.ore"));
        //
        // lblTradeCmdty7
        //
        lblTradeCmdty7.setAutoSize(true);
        lblTradeCmdty7.setLocation(new Point(8, 228));
        lblTradeCmdty7.setName("lblTradeCmdty7");
        lblTradeCmdty7.setSize(new jwinforms.Size(53, 16));
        lblTradeCmdty7.setTabIndex(16);
        lblTradeCmdty7.setText(strings.get("group.box.cargo.machines"));
        //
        // ilChartImages
        //
        ilChartImages.setImageSize(new jwinforms.Size(7, 7));
        ilChartImages.setImageStream(((jwinforms.ImageListStreamer) (resources.getObject("ilChartImages.ImageStream"))));
        ilChartImages.setTransparentColor(Color.white);
        //
        // ilShipImages
        //
        ilShipImages.setImageSize(new jwinforms.Size(64, 52));
        ilShipImages.setImageStream(((jwinforms.ImageListStreamer) (resources.getObject("ilShipImages.ImageStream"))));
        ilShipImages.setTransparentColor(Color.white);
        //
        // ilDirectionImages
        //
        ilDirectionImages.setImageSize(new jwinforms.Size(13, 13));
        ilDirectionImages.setImageStream(((jwinforms.ImageListStreamer) (resources
                .getObject("ilDirectionImages.ImageStream"))));
        ilDirectionImages.setTransparentColor(Color.white);
        //
        // ilEquipmentImages
        //
        ilEquipmentImages.setImageSize(new jwinforms.Size(64, 52));
        ilEquipmentImages.setImageStream(((jwinforms.ImageListStreamer) (resources
                .getObject("ilEquipmentImages.ImageStream"))));
        ilEquipmentImages.setTransparentColor(Color.white);

        finishInit();
    }

    void update(Map<String, String> strings) {
        int i;

        if (game == null || game.Commander().getCurrentSystem() == null) {
            for (i = 0; i < lblSellPrice.length; i++) {
                lblSellPrice[i].setText("");
                lblBuyPrice[i].setText("");
                lblTargetPrice[i].setText("");
                lblTargetDiff[i].setText("");
                lblTargetPct[i].setText("");
                btnSellQty[i].setVisible(false);
                btnSellAll[i].setVisible(false);
                btnBuyQty[i].setVisible(false);
                btnBuyMax[i].setVisible(false);
            }
            return;
        }
        int[] buy = game.PriceCargoBuy();
        int[] sell = game.PriceCargoSell();
        Commander cmdr = game.Commander();
        StarSystem warpSys = game.WarpSystem();

        for (i = 0; i < lblSellPrice.length; i++) {
            int price = warpSys == null ? 0 : Consts.TradeItems[i].StandardPrice(warpSys);

            lblSellPrice[i].setText(sell[i] > 0
                    ? Functions.formatMoney(sell[i], strings.get("group.box.cargo.credit"))
                    : Strings.CargoSellNA);
            btnSellQty[i].setText("" + cmdr.getShip().Cargo()[i]);
            btnSellQty[i].setVisible(true);
            btnSellAll[i].setText(sell[i] > 0
                    ? strings.get("group.box.cargo.all")
                    : strings.get("group.box.cargo.dump")
            );
            btnSellAll[i].setVisible(true);
            lblBuyPrice[i].setText(buy[i] > 0
                    ? Functions.formatMoney(buy[i], strings.get("group.box.cargo.credit"))
                    : Strings.CargoBuyNA);
            btnBuyQty[i].setText("" + cmdr.getCurrentSystem().TradeItems()[i]);
            btnBuyQty[i].setVisible(buy[i] > 0);
            btnBuyMax[i].setVisible(buy[i] > 0);

            if (sell[i] * cmdr.getShip().Cargo()[i] > cmdr.PriceCargo()[i])
                lblSellPrice[i].setFont(BOLD_FONT);
            else
                lblSellPrice[i].setFont(lblSell.getFont());

            if (warpSys != null && warpSys.DestOk() && price > 0)
                lblTargetPrice[i].setText(Functions.formatMoney(price, strings.get("group.box.cargo.credit")));
            else
                lblTargetPrice[i].setText("-----------");

            if (warpSys != null && warpSys.DestOk() && price > 0 && buy[i] > 0) {
                int diff = price - buy[i];
                lblTargetDiff[i].setText((diff > 0 ? "+" : "")
                        + Functions.formatMoney(diff, strings.get("group.box.cargo.credit")));
                lblTargetPct[i].setText((diff > 0 ? "+" : "") + Functions.formatNumber(100 * diff / buy[i]) + "%");
                lblBuyPrice[i].setFont((diff > 0 && cmdr.getCurrentSystem().TradeItems()[i] > 0) ? BOLD_FONT : lblBuy
                        .getFont());
            } else {
                lblTargetDiff[i].setText("------------");
                lblTargetPct[i].setText("--------");
                lblBuyPrice[i].setFont(lblBuy.getFont());
            }

            lblTargetPrice[i].setFont(lblBuyPrice[i].getFont());
            lblTargetDiff[i].setFont(lblBuyPrice[i].getFont());
            lblTargetPct[i].setFont(lblBuyPrice[i].getFont());
        }
    }

    private void btnBuySell_Click(String buttonName) {
        boolean all = !buttonName.contains("Qty");
        int index = Integer.parseInt(buttonName.substring(buttonName.length() - 1));

        if (!buttonName.contains("Buy")) {
            controller.cargoSell(index, all);
        } else {
            controller.cargoBuy(index, all);
        }
    }
}

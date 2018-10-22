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

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Container;
import spacetrader.controls.Font;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.*;

import java.awt.*;
import java.util.Arrays;

import static spacetrader.game.Functions.formatMoney;
import static spacetrader.game.Functions.formatNumber;
import static spacetrader.game.Strings.*;

class CargoPanel extends Panel {

    private static final Font BOLD_FONT = FontCollection.bold825;

    private static final String TARGET_PRICE_NA = "-----------";
    private static final String TARGET_DIFF_NA = "------------";
    private static final String TARGET_PERCENTAGE_NA = "--------";
                    
    private SpaceTraderGame game = null;
    private GameController controller = null;

    private Button buyMaxButton0;
    private Button buyButton0;
    private Button sellButton0;
    private Button sellAllButton0;
    private Button buyMaxButton1;
    private Button buyButton1;
    private Button sellButton1;
    private Button sellAllButton1;
    private Button sellButton2;
    private Button sellAllButton2;
    private Button buyButton2;
    private Button buyMaxButton2;
    private Button sellButton3;
    private Button sellAllButton3;
    private Button buyButton3;
    private Button buyMaxButton3;
    private Button sellButton4;
    private Button sellAllButton4;
    private Button buyButton4;
    private Button buyMaxButton4;
    private Button sellButton5;
    private Button sellAllButton5;
    private Button buyButton5;
    private Button buyMaxButton5;
    private Button sellButton6;
    private Button sellAllButton6;
    private Button buyButton6;
    private Button buyMaxButton6;
    private Button sellButton7;
    private Button sellAllButton7;
    private Button buyButton7;
    private Button buyMaxButton7;
    private Button sellButton8;
    private Button sellAllButton8;
    private Button buyButton8;
    private Button buyMaxButton8;
    private Button sellButton9;
    private Button sellAllButton9;
    private Button buyButton9;
    private Button buyMaxButton9;

    private Label commodityLabel0;
    private Label commodityLabel1;
    private Label commodityLabel2;
    private Label commodityLabel3;
    private Label commodityLabel4;
    private Label commodityLabel5;
    private Label commodityLabel6;
    private Label commodityLabel7;
    private Label commodityLabel8;
    private Label commodityLabel9;

    private Label buyPriceLabel;
    private Label buyPriceLabelValue0;
    private Label buyPriceLabelValue1;
    private Label buyPriceLabelValue2;
    private Label buyPriceLabelValue3;
    private Label buyPriceLabelValue4;
    private Label buyPriceLabelValue5;
    private Label buyPriceLabelValue6;
    private Label buyPriceLabelValue7;
    private Label buyPriceLabelValue8;
    private Label buyPriceLabelValue9;

    private Label sellPriceLabel;
    private Label sellPriceLabelValue0;
    private Label sellPriceLabelValue1;
    private Label sellPriceLabelValue2;
    private Label sellPriceLabelValue3;
    private Label sellPriceLabelValue4;
    private Label sellPriceLabelValue5;
    private Label sellPriceLabelValue6;
    private Label sellPriceLabelValue7;
    private Label sellPriceLabelValue8;
    private Label sellPriceLabelValue9;

    private Label tradeTargetLabel;

    private Label targetPriceLabel;
    private Label targetPriceLabelValue0;
    private Label targetPriceLabelValue1;
    private Label targetPriceLabelValue2;
    private Label targetPriceLabelValue3;
    private Label targetPriceLabelValue4;
    private Label targetPriceLabelValue5;
    private Label targetPriceLabelValue6;
    private Label targetPriceLabelValue7;
    private Label targetPriceLabelValue8;
    private Label targetPriceLabelValue9;


    private Label targetDiffLabel;
    private Label targetDiffLabelValue0;
    private Label targetDiffLabelValue1;
    private Label targetDiffLabelValue2;
    private Label targetDiffLabelValue3;
    private Label targetDiffLabelValue4;
    private Label targetDiffLabelValue5;
    private Label targetDiffLabelValue6;
    private Label targetDiffLabelValue7;
    private Label targetDiffLabelValue8;
    private Label targetDiffLabelValue9;

    private Label targetPercentageLabel;
    private Label targetPercentageLabelValue0;
    private Label targetPercentageLabelValue1;
    private Label targetPercentageLabelValue2;
    private Label targetPercentageLabelValue3;
    private Label targetPercentageLabelValue4;
    private Label targetPercentageLabelValue5;
    private Label targetPercentageLabelValue6;
    private Label targetPercentageLabelValue7;
    private Label targetPercentageLabelValue8;
    private Label targetPercentageLabelValue9;

    private VerticalLine verticalLine0;
    private VerticalLine verticalLine1;
    private VerticalLine verticalLine2;
    private HorizontalLine horizontalLine;

    private ImageList ilChartImages;
    private ImageList ilDirectionImages;
    private ImageList ilEquipmentImages;
    private ImageList ilShipImages;

    private Label[] sellPriceArray;
    private Label[] buyPriceArray;
    private Label[] targetPriceArray;
    private Label[] targetDiffArray;
    private Label[] targetPercentageArray;

    private Button[] sellButtonArray;
    private Button[] sellAllButtonArray;
    private Button[] buyButtonArray;
    private Button[] buyMaxButtonArray;

    //TODO need???
    private IContainer components;

    CargoPanel() {
    }

    void setGame(SpaceTraderGame game, GameController controller) {
        this.game = game;
        this.controller = controller;
    }

    void initializeComponent() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        components = new Container();

        verticalLine0 = new VerticalLine();
        verticalLine1 = new VerticalLine();
        verticalLine2 = new VerticalLine();
        horizontalLine = new HorizontalLine();

        commodityLabel9 = new Label();
        commodityLabel8 = new Label();
        commodityLabel2 = new Label();
        commodityLabel0 = new Label();
        commodityLabel1 = new Label();
        commodityLabel6 = new Label();
        commodityLabel5 = new Label();
        commodityLabel4 = new Label();
        commodityLabel3 = new Label();
        commodityLabel7 = new Label();

        tradeTargetLabel = new Label();
        buyPriceLabel = new Label();
        sellPriceLabel = new Label();
        targetPriceLabel = new Label();
        targetDiffLabel = new Label();
        targetPercentageLabel = new Label();

        buyMaxButton0 = new Button();
        buyButton0 = new Button();
        buyPriceLabelValue0 = new Label();
        sellAllButton0 = new Button();
        sellButton0 = new Button();
        sellPriceLabelValue0 = new Label();
        targetPercentageLabelValue0 = new Label();
        targetDiffLabelValue0 = new Label();
        targetPriceLabelValue0 = new Label();

        buyMaxButton1 = new Button();
        buyButton1 = new Button();
        buyPriceLabelValue1 = new Label();
        sellAllButton1 = new Button();
        sellButton1 = new Button();
        sellPriceLabelValue1 = new Label();
        targetPercentageLabelValue1 = new Label();
        targetDiffLabelValue1 = new Label();
        targetPriceLabelValue1 = new Label();

        buyMaxButton2 = new Button();
        buyButton2 = new Button();
        buyPriceLabelValue2 = new Label();
        sellAllButton2 = new Button();
        sellButton2 = new Button();
        sellPriceLabelValue2 = new Label();
        targetPercentageLabelValue2 = new Label();
        targetDiffLabelValue2 = new Label();
        targetPriceLabelValue2 = new Label();

        buyMaxButton3 = new Button();
        buyButton3 = new Button();
        buyPriceLabelValue3 = new Label();
        sellAllButton3 = new Button();
        sellButton3 = new Button();
        sellPriceLabelValue3 = new Label();
        targetPercentageLabelValue3 = new Label();
        targetDiffLabelValue3 = new Label();
        targetPriceLabelValue3 = new Label();

        buyMaxButton4 = new Button();
        buyButton4 = new Button();
        buyPriceLabelValue4 = new Label();
        sellAllButton4 = new Button();
        sellButton4 = new Button();
        sellPriceLabelValue4 = new Label();
        targetPercentageLabelValue4 = new Label();
        targetDiffLabelValue4 = new Label();
        targetPriceLabelValue4 = new Label();

        buyMaxButton5 = new Button();
        buyButton5 = new Button();
        buyPriceLabelValue5 = new Label();
        sellAllButton5 = new Button();
        sellButton5 = new Button();
        sellPriceLabelValue5 = new Label();
        targetPercentageLabelValue5 = new Label();
        targetDiffLabelValue5 = new Label();
        targetPriceLabelValue5 = new Label();

        buyMaxButton6 = new Button();
        buyButton6 = new Button();
        buyPriceLabelValue6 = new Label();
        sellAllButton6 = new Button();
        sellButton6 = new Button();
        sellPriceLabelValue6 = new Label();
        targetPercentageLabelValue6 = new Label();
        targetDiffLabelValue6 = new Label();
        targetPriceLabelValue6 = new Label();

        buyMaxButton7 = new Button();
        buyButton7 = new Button();
        buyPriceLabelValue7 = new Label();
        sellAllButton7 = new Button();
        sellButton7 = new Button();
        sellPriceLabelValue7 = new Label();
        targetPercentageLabelValue7 = new Label();
        targetDiffLabelValue7 = new Label();
        targetPriceLabelValue7 = new Label();

        buyMaxButton8 = new Button();
        buyButton8 = new Button();
        buyPriceLabelValue8 = new Label();
        sellAllButton8 = new Button();
        sellButton8 = new Button();
        sellPriceLabelValue8 = new Label();
        targetPercentageLabelValue8 = new Label();
        targetDiffLabelValue8 = new Label();
        targetPriceLabelValue8 = new Label();

        buyMaxButton9 = new Button();
        buyButton9 = new Button();
        buyPriceLabelValue9 = new Label();
        sellAllButton9 = new Button();
        sellButton9 = new Button();
        sellPriceLabelValue9 = new Label();
        targetPercentageLabelValue9 = new Label();
        targetDiffLabelValue9 = new Label();
        targetPriceLabelValue9 = new Label();

        sellPriceArray = new Label[]{sellPriceLabelValue0, sellPriceLabelValue1, sellPriceLabelValue2,
                sellPriceLabelValue3, sellPriceLabelValue4, sellPriceLabelValue5, sellPriceLabelValue6,
                sellPriceLabelValue7, sellPriceLabelValue8, sellPriceLabelValue9};

        buyPriceArray = new Label[]{buyPriceLabelValue0, buyPriceLabelValue1, buyPriceLabelValue2,
                buyPriceLabelValue3, buyPriceLabelValue4, buyPriceLabelValue5, buyPriceLabelValue6,
                buyPriceLabelValue7, buyPriceLabelValue8, buyPriceLabelValue9};

        targetPriceArray = new Label[]{targetPriceLabelValue0, targetPriceLabelValue1, targetPriceLabelValue2,
                targetPriceLabelValue3, targetPriceLabelValue4, targetPriceLabelValue5, targetPriceLabelValue6,
                targetPriceLabelValue7, targetPriceLabelValue8, targetPriceLabelValue9};

        targetDiffArray = new Label[]{targetDiffLabelValue0, targetDiffLabelValue1, targetDiffLabelValue2,
                targetDiffLabelValue3, targetDiffLabelValue4, targetDiffLabelValue5, targetDiffLabelValue6,
                targetDiffLabelValue7, targetDiffLabelValue8, targetDiffLabelValue9};

        targetPercentageArray = new Label[]{targetPercentageLabelValue0, targetPercentageLabelValue1,
                targetPercentageLabelValue2, targetPercentageLabelValue3, targetPercentageLabelValue4,
                targetPercentageLabelValue5, targetPercentageLabelValue6, targetPercentageLabelValue7,
                targetPercentageLabelValue8, targetPercentageLabelValue9};

        sellButtonArray = new Button[]{sellButton0, sellButton1, sellButton2, sellButton3, sellButton4, sellButton5,
                sellButton6, sellButton7, sellButton8, sellButton9};

        sellAllButtonArray = new Button[]{sellAllButton0, sellAllButton1, sellAllButton2, sellAllButton3,
                sellAllButton4, sellAllButton5, sellAllButton6, sellAllButton7, sellAllButton8, sellAllButton9};

        buyButtonArray = new Button[]{buyButton0, buyButton1, buyButton2, buyButton3, buyButton4, buyButton5,
                buyButton6, buyButton7, buyButton8, buyButton9};

        buyMaxButtonArray = new Button[]{buyMaxButton0, buyMaxButton1, buyMaxButton2, buyMaxButton3, buyMaxButton4,
                buyMaxButton5, buyMaxButton6, buyMaxButton7, buyMaxButton8, buyMaxButton9};

        ilChartImages = new ImageList(components);
        ilShipImages = new ImageList(components);
        ilDirectionImages = new ImageList(components);
        ilEquipmentImages = new ImageList(components);

        this.suspendLayout();

        this.anchor = AnchorStyles.TOP_RIGHT;

        controls.add(verticalLine0);
        controls.add(verticalLine1);
        controls.add(verticalLine2);
        controls.add(horizontalLine);

        controls.add(commodityLabel9);
        controls.add(commodityLabel8);
        controls.add(commodityLabel2);
        controls.add(commodityLabel0);
        controls.add(commodityLabel1);
        controls.add(commodityLabel6);
        controls.add(commodityLabel5);
        controls.add(commodityLabel4);
        controls.add(commodityLabel3);
        controls.add(commodityLabel7);

        controls.add(buyPriceLabel);
        controls.add(sellPriceLabel);
        controls.add(tradeTargetLabel);
        controls.add(targetPriceLabel);
        controls.add(targetDiffLabel);
        controls.add(targetPercentageLabel);

        controls.addAll(sellPriceArray);
        controls.addAll(buyPriceArray);
        controls.addAll(targetPriceArray);
        controls.addAll(targetDiffArray);
        controls.addAll(targetPercentageArray);

        controls.addAll(sellButtonArray);
        controls.addAll(sellAllButtonArray);
        controls.addAll(buyButtonArray);
        controls.addAll(buyMaxButtonArray);

        setSize(new Size(512, 300));
        setTabIndex(8);
        setTabStop(false);
        setText("Cargo");

        horizontalLine.setLocation(new Point(8, 52));
        horizontalLine.setWidth(496);
        horizontalLine.setTabIndex(131);
        horizontalLine.setTabStop(false);

        verticalLine0.setLocation(new Point(71, 32));
        verticalLine0.setHeight(262);
        verticalLine0.setTabIndex(129);
        verticalLine0.setTabStop(false);

        verticalLine1.setLocation(new Point(218, 32));
        verticalLine1.setHeight(262);
        verticalLine1.setTabIndex(128);
        verticalLine1.setTabStop(false);

        verticalLine2.setLocation(new Point(352, 32));
        verticalLine2.setHeight(262);
        verticalLine2.setTabIndex(130);
        verticalLine2.setTabStop(false);


        buyPriceLabel.setAutoSize(true);
        buyPriceLabel.setLocation(new Point(273, 34));
        buyPriceLabel.setSize(new Size(24, 16));
        buyPriceLabel.setTabIndex(27);
        buyPriceLabel.setText("Buy");

        sellPriceLabel.setAutoSize(true);
        sellPriceLabel.setFont(FontCollection.regular825);
        sellPriceLabel.setLocation(new Point(132, 34));
        sellPriceLabel.setSize(new Size(23, 16));
        sellPriceLabel.setTabIndex(26);
        sellPriceLabel.setText("Sell");

        tradeTargetLabel.setAutoSize(true);
        tradeTargetLabel.setLocation(new Point(391, 16));
        tradeTargetLabel.setSize(new Size(78, 16));
        tradeTargetLabel.setTabIndex(28);
        tradeTargetLabel.setText("Target System");

        targetPriceLabel.setAutoSize(true);
        targetPriceLabel.setLocation(new Point(360, 34));
        targetPriceLabel.setSize(new Size(30, 16));
        targetPriceLabel.setTabIndex(47);
        targetPriceLabel.setText("Price");

        targetDiffLabel.setAutoSize(true);
        targetDiffLabel.setLocation(new Point(424, 34));
        targetDiffLabel.setSize(new Size(18, 16));
        targetDiffLabel.setTabIndex(48);
        targetDiffLabel.setText("+/-");

        targetPercentageLabel.setAutoSize(true);
        targetPercentageLabel.setLocation(new Point(476, 34));
        targetPercentageLabel.setSize(new Size(14, 16));
        targetPercentageLabel.setTabIndex(49);
        targetPercentageLabel.setText("%");



        commodityLabel0.setAutoSize(true);
        commodityLabel0.setLocation(new Point(8, 60));
        commodityLabel0.setSize(new Size(34, 16));
        commodityLabel0.setTabIndex(22);
        commodityLabel0.setText("Water");

        commodityLabel1.setAutoSize(true);
        commodityLabel1.setLocation(new Point(8, 84));
        commodityLabel1.setSize(new Size(27, 16));
        commodityLabel1.setTabIndex(21);
        commodityLabel1.setText("Furs");

        commodityLabel2.setAutoSize(true);
        commodityLabel2.setLocation(new Point(8, 108));
        commodityLabel2.setSize(new Size(30, 16));
        commodityLabel2.setTabIndex(23);
        commodityLabel2.setText("Food");

        commodityLabel3.setAutoSize(true);
        commodityLabel3.setLocation(new Point(8, 132));
        commodityLabel3.setSize(new Size(23, 16));
        commodityLabel3.setTabIndex(17);
        commodityLabel3.setText("Ore");

        commodityLabel4.setAutoSize(true);
        commodityLabel4.setLocation(new Point(8, 156));
        commodityLabel4.setSize(new Size(41, 16));
        commodityLabel4.setTabIndex(18);
        commodityLabel4.setText("Games");

        commodityLabel5.setAutoSize(true);
        commodityLabel5.setLocation(new Point(8, 180));
        commodityLabel5.setSize(new Size(49, 16));
        commodityLabel5.setTabIndex(19);
        commodityLabel5.setText("Firearms");

        commodityLabel6.setAutoSize(true);
        commodityLabel6.setLocation(new Point(8, 204));
        commodityLabel6.setSize(new Size(50, 16));
        commodityLabel6.setTabIndex(20);
        commodityLabel6.setText("Medicine");

        commodityLabel7.setAutoSize(true);
        commodityLabel7.setLocation(new Point(8, 228));
        commodityLabel7.setSize(new Size(53, 16));
        commodityLabel7.setTabIndex(16);
        commodityLabel7.setText("Machines");

        commodityLabel8.setAutoSize(true);
        commodityLabel8.setLocation(new Point(8, 252));
        commodityLabel8.setSize(new Size(51, 16));
        commodityLabel8.setTabIndex(24);
        commodityLabel8.setText("Narcotics");

        commodityLabel9.setAutoSize(true);
        commodityLabel9.setLocation(new Point(8, 276));
        commodityLabel9.setSize(new Size(40, 16));
        commodityLabel9.setTabIndex(25);
        commodityLabel9.setText("Robots");



        targetPercentageLabelValue9.setLocation(new Point(466, 276));
        targetPercentageLabelValue9.setSize(new Size(37, 13));
        targetPercentageLabelValue9.setTabIndex(127);
        targetPercentageLabelValue9.setText(TARGET_PERCENTAGE_NA);
        targetPercentageLabelValue9.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue9.setLocation(new Point(410, 276));
        targetDiffLabelValue9.setSize(new Size(52, 13));
        targetDiffLabelValue9.setTabIndex(126);
        targetDiffLabelValue9.setText(TARGET_DIFF_NA);
        targetDiffLabelValue9.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue9.setLocation(new Point(358, 276));
        targetPriceLabelValue9.setSize(new Size(48, 13));
        targetPriceLabelValue9.setTabIndex(125);
        targetPriceLabelValue9.setText(TARGET_PRICE_NA);
        targetPriceLabelValue9.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton9.setLocation(new Point(262, 272));
        buyMaxButton9.setSize(new Size(36, 22));
        buyMaxButton9.setTabIndex(51);

        buyButton9.setLocation(new Point(227, 272));
        buyButton9.setSize(new Size(28, 22));
        buyButton9.setTabIndex(50);

        buyPriceLabelValue9.setLocation(new Point(302, 276));
        buyPriceLabelValue9.setSize(new Size(48, 13));
        buyPriceLabelValue9.setTabIndex(122);
        buyPriceLabelValue9.setText("not sold");
        buyPriceLabelValue9.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton9.setLocation(new Point(115, 272));
        sellAllButton9.setSize(new Size(44, 22));
        sellAllButton9.setTabIndex(49);

        sellButton9.setFlatStyle(FlatStyle.FLAT);
        sellButton9.setLocation(new Point(80, 272));
        sellButton9.setSize(new Size(28, 22));
        sellButton9.setTabIndex(48);

        sellPriceLabelValue9.setLocation(new Point(163, 276));
        sellPriceLabelValue9.setSize(new Size(48, 13));
        sellPriceLabelValue9.setTabIndex(119);
        sellPriceLabelValue9.setText("no trade");
        sellPriceLabelValue9.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue8.setLocation(new Point(466, 252));
        targetPercentageLabelValue8.setSize(new Size(37, 13));
        targetPercentageLabelValue8.setTabIndex(118);
        targetPercentageLabelValue8.setText("-888%");
        targetPercentageLabelValue8.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue8.setLocation(new Point(410, 252));
        targetDiffLabelValue8.setSize(new Size(52, 13));
        targetDiffLabelValue8.setTabIndex(117);
        targetDiffLabelValue8.setText("-8,888 cr.");
        targetDiffLabelValue8.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue8.setLocation(new Point(358, 252));
        targetPriceLabelValue8.setSize(new Size(48, 13));
        targetPriceLabelValue8.setTabIndex(116);
        targetPriceLabelValue8.setText("8,888 cr.");
        targetPriceLabelValue8.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton8.setLocation(new Point(262, 248));
        buyMaxButton8.setSize(new Size(36, 22));
        buyMaxButton8.setTabIndex(47);

        buyButton8.setLocation(new Point(227, 248));
        buyButton8.setSize(new Size(28, 22));
        buyButton8.setTabIndex(46);

        buyPriceLabelValue8.setLocation(new Point(302, 252));
        buyPriceLabelValue8.setSize(new Size(48, 13));
        buyPriceLabelValue8.setTabIndex(113);
        buyPriceLabelValue8.setText("8,888 cr.");
        buyPriceLabelValue8.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton8.setLocation(new Point(115, 248));
        sellAllButton8.setSize(new Size(44, 22));
        sellAllButton8.setTabIndex(45);

        sellButton8.setLocation(new Point(80, 248));
        sellButton8.setSize(new Size(28, 22));
        sellButton8.setTabIndex(44);

        sellPriceLabelValue8.setLocation(new Point(163, 252));
        sellPriceLabelValue8.setSize(new Size(48, 13));
        sellPriceLabelValue8.setTabIndex(110);
        sellPriceLabelValue8.setText("8,888 cr.");
        sellPriceLabelValue8.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue7.setLocation(new Point(466, 228));
        targetPercentageLabelValue7.setSize(new Size(37, 13));
        targetPercentageLabelValue7.setTabIndex(109);
        targetPercentageLabelValue7.setText("-888%");
        targetPercentageLabelValue7.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue7.setFont(FontCollection.regular825);
        targetDiffLabelValue7.setLocation(new Point(410, 228));
        targetDiffLabelValue7.setSize(new Size(52, 13));
        targetDiffLabelValue7.setTabIndex(108);
        targetDiffLabelValue7.setText("-8,888 cr.");
        targetDiffLabelValue7.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue7.setLocation(new Point(358, 228));
        targetPriceLabelValue7.setSize(new Size(48, 13));
        targetPriceLabelValue7.setTabIndex(107);
        targetPriceLabelValue7.setText("8,888 cr.");
        targetPriceLabelValue7.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton7.setLocation(new Point(262, 224));
        buyMaxButton7.setSize(new Size(36, 22));
        buyMaxButton7.setTabIndex(43);

        buyButton7.setLocation(new Point(227, 224));
        buyButton7.setSize(new Size(28, 22));
        buyButton7.setTabIndex(42);

        buyPriceLabelValue7.setLocation(new Point(302, 228));
        buyPriceLabelValue7.setSize(new Size(48, 13));
        buyPriceLabelValue7.setTabIndex(104);
        buyPriceLabelValue7.setText("8,888 cr.");
        buyPriceLabelValue7.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton7.setLocation(new Point(115, 224));
        sellAllButton7.setSize(new Size(44, 22));
        sellAllButton7.setTabIndex(41);

        sellButton7.setLocation(new Point(80, 224));
        sellButton7.setSize(new Size(28, 22));
        sellButton7.setTabIndex(40);

        sellPriceLabelValue7.setLocation(new Point(163, 228));
        sellPriceLabelValue7.setSize(new Size(48, 13));
        sellPriceLabelValue7.setTabIndex(101);
        sellPriceLabelValue7.setText("8,888 cr.");
        sellPriceLabelValue7.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue6.setLocation(new Point(466, 204));
        targetPercentageLabelValue6.setSize(new Size(37, 13));
        targetPercentageLabelValue6.setTabIndex(100);
        targetPercentageLabelValue6.setText("-888%");
        targetPercentageLabelValue6.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue6.setLocation(new Point(410, 204));
        targetDiffLabelValue6.setSize(new Size(52, 13));
        targetDiffLabelValue6.setTabIndex(99);
        targetDiffLabelValue6.setText("-8,888 cr.");
        targetDiffLabelValue6.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue6.setLocation(new Point(358, 204));
        targetPriceLabelValue6.setSize(new Size(48, 13));
        targetPriceLabelValue6.setTabIndex(98);
        targetPriceLabelValue6.setText("8,888 cr.");
        targetPriceLabelValue6.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton6.setLocation(new Point(262, 200));
        buyMaxButton6.setSize(new Size(36, 22));
        buyMaxButton6.setTabIndex(39);

        buyButton6.setLocation(new Point(227, 200));
        buyButton6.setSize(new Size(28, 22));
        buyButton6.setTabIndex(38);

        buyPriceLabelValue6.setLocation(new Point(302, 204));
        buyPriceLabelValue6.setSize(new Size(48, 13));
        buyPriceLabelValue6.setTabIndex(95);
        buyPriceLabelValue6.setText("8,888 cr.");
        buyPriceLabelValue6.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton6.setLocation(new Point(115, 200));
        sellAllButton6.setSize(new Size(44, 22));
        sellAllButton6.setTabIndex(37);

        sellButton6.setLocation(new Point(80, 200));
        sellButton6.setSize(new Size(28, 22));
        sellButton6.setTabIndex(36);

        sellPriceLabelValue6.setLocation(new Point(163, 204));
        sellPriceLabelValue6.setSize(new Size(48, 13));
        sellPriceLabelValue6.setTabIndex(92);
        sellPriceLabelValue6.setText("8,888 cr.");
        sellPriceLabelValue6.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue5.setLocation(new Point(466, 180));
        targetPercentageLabelValue5.setSize(new Size(37, 13));
        targetPercentageLabelValue5.setTabIndex(91);
        targetPercentageLabelValue5.setText("-888%");
        targetPercentageLabelValue5.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue5.setLocation(new Point(410, 180));
        targetDiffLabelValue5.setSize(new Size(52, 13));
        targetDiffLabelValue5.setTabIndex(90);
        targetDiffLabelValue5.setText("-8,888 cr.");
        targetDiffLabelValue5.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue5.setLocation(new Point(358, 180));
        targetPriceLabelValue5.setSize(new Size(48, 13));
        targetPriceLabelValue5.setTabIndex(89);
        targetPriceLabelValue5.setText("8,888 cr.");
        targetPriceLabelValue5.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton5.setLocation(new Point(262, 176));
        buyMaxButton5.setSize(new Size(36, 22));
        buyMaxButton5.setTabIndex(35);

        buyButton5.setLocation(new Point(227, 176));
        buyButton5.setSize(new Size(28, 22));
        buyButton5.setTabIndex(34);

        buyPriceLabelValue5.setLocation(new Point(302, 180));
        buyPriceLabelValue5.setSize(new Size(48, 13));
        buyPriceLabelValue5.setTabIndex(86);
        buyPriceLabelValue5.setText("8,888 cr.");
        buyPriceLabelValue5.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton5.setLocation(new Point(115, 176));
        sellAllButton5.setSize(new Size(44, 22));
        sellAllButton5.setTabIndex(33);

        sellButton5.setLocation(new Point(80, 176));
        sellButton5.setSize(new Size(28, 22));
        sellButton5.setTabIndex(32);

        sellPriceLabelValue5.setLocation(new Point(163, 180));
        sellPriceLabelValue5.setSize(new Size(48, 13));
        sellPriceLabelValue5.setTabIndex(83);
        sellPriceLabelValue5.setText("8,888 cr.");
        sellPriceLabelValue5.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue4.setLocation(new Point(466, 156));
        targetPercentageLabelValue4.setSize(new Size(37, 13));
        targetPercentageLabelValue4.setTabIndex(82);
        targetPercentageLabelValue4.setText("-888%");
        targetPercentageLabelValue4.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue4.setLocation(new Point(410, 156));
        targetDiffLabelValue4.setSize(new Size(52, 13));
        targetDiffLabelValue4.setTabIndex(81);
        targetDiffLabelValue4.setText("-8,888 cr.");
        targetDiffLabelValue4.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue4.setLocation(new Point(358, 156));
        targetPriceLabelValue4.setSize(new Size(48, 13));
        targetPriceLabelValue4.setTabIndex(80);
        targetPriceLabelValue4.setText("8,888 cr.");
        targetPriceLabelValue4.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton4.setLocation(new Point(262, 152));
        buyMaxButton4.setSize(new Size(36, 22));
        buyMaxButton4.setTabIndex(31);

        buyButton4.setLocation(new Point(227, 152));
        buyButton4.setSize(new Size(28, 22));
        buyButton4.setTabIndex(30);

        buyPriceLabelValue4.setLocation(new Point(302, 156));
        buyPriceLabelValue4.setSize(new Size(48, 13));
        buyPriceLabelValue4.setTabIndex(77);
        buyPriceLabelValue4.setText("8,888 cr.");
        buyPriceLabelValue4.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton4.setFlatStyle(FlatStyle.FLAT);
        sellAllButton4.setLocation(new Point(115, 152));
        sellAllButton4.setSize(new Size(44, 22));
        sellAllButton4.setTabIndex(29);

        sellButton4.setLocation(new Point(80, 152));
        sellButton4.setSize(new Size(28, 22));
        sellButton4.setTabIndex(28);

        sellPriceLabelValue4.setLocation(new Point(163, 156));
        sellPriceLabelValue4.setSize(new Size(48, 13));
        sellPriceLabelValue4.setTabIndex(74);
        sellPriceLabelValue4.setText("8,888 cr.");
        sellPriceLabelValue4.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue3.setLocation(new Point(466, 132));
        targetPercentageLabelValue3.setSize(new Size(37, 13));
        targetPercentageLabelValue3.setTabIndex(73);
        targetPercentageLabelValue3.setText("-888%");
        targetPercentageLabelValue3.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue3.setLocation(new Point(410, 132));
        targetDiffLabelValue3.setSize(new Size(52, 13));
        targetDiffLabelValue3.setTabIndex(72);
        targetDiffLabelValue3.setText("-8,888 cr.");
        targetDiffLabelValue3.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue3.setLocation(new Point(358, 132));
        targetPriceLabelValue3.setSize(new Size(48, 13));
        targetPriceLabelValue3.setTabIndex(71);
        targetPriceLabelValue3.setText("8,888 cr.");
        targetPriceLabelValue3.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton3.setLocation(new Point(262, 128));
        buyMaxButton3.setSize(new Size(36, 22));
        buyMaxButton3.setTabIndex(27);

        buyButton3.setLocation(new Point(227, 128));
        buyButton3.setSize(new Size(28, 22));
        buyButton3.setTabIndex(26);

        buyPriceLabelValue3.setLocation(new Point(302, 132));
        buyPriceLabelValue3.setSize(new Size(48, 13));
        buyPriceLabelValue3.setTabIndex(68);
        buyPriceLabelValue3.setText("8,888 cr.");
        buyPriceLabelValue3.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton3.setLocation(new Point(115, 128));
        sellAllButton3.setSize(new Size(44, 22));
        sellAllButton3.setTabIndex(25);

        sellButton3.setLocation(new Point(80, 128));
        sellButton3.setSize(new Size(28, 22));
        sellButton3.setTabIndex(24);

        sellPriceLabelValue3.setLocation(new Point(163, 132));
        sellPriceLabelValue3.setSize(new Size(48, 13));
        sellPriceLabelValue3.setTabIndex(65);
        sellPriceLabelValue3.setText("8,888 cr.");
        sellPriceLabelValue3.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue2.setLocation(new Point(466, 108));
        targetPercentageLabelValue2.setSize(new Size(37, 13));
        targetPercentageLabelValue2.setTabIndex(64);
        targetPercentageLabelValue2.setText("-888%");
        targetPercentageLabelValue2.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue2.setLocation(new Point(410, 108));
        targetDiffLabelValue2.setSize(new Size(52, 13));
        targetDiffLabelValue2.setTabIndex(63);
        targetDiffLabelValue2.setText("-8,888 cr.");
        targetDiffLabelValue2.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue2.setLocation(new Point(358, 108));
        targetPriceLabelValue2.setSize(new Size(48, 13));
        targetPriceLabelValue2.setTabIndex(62);
        targetPriceLabelValue2.setText("8,888 cr.");
        targetPriceLabelValue2.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton2.setLocation(new Point(262, 104));
        buyMaxButton2.setSize(new Size(36, 22));
        buyMaxButton2.setTabIndex(23);

        buyButton2.setLocation(new Point(227, 104));
        buyButton2.setSize(new Size(28, 22));
        buyButton2.setTabIndex(22);

        buyPriceLabelValue2.setLocation(new Point(302, 108));
        buyPriceLabelValue2.setSize(new Size(48, 13));
        buyPriceLabelValue2.setTabIndex(59);
        buyPriceLabelValue2.setText("8,888 cr.");
        buyPriceLabelValue2.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton2.setLocation(new Point(115, 104));
        sellAllButton2.setSize(new Size(44, 22));
        sellAllButton2.setTabIndex(21);

        sellButton2.setLocation(new Point(80, 104));
        sellButton2.setSize(new Size(28, 22));
        sellButton2.setTabIndex(20);

        sellPriceLabelValue2.setLocation(new Point(163, 108));
        sellPriceLabelValue2.setSize(new Size(48, 13));
        sellPriceLabelValue2.setTabIndex(56);
        sellPriceLabelValue2.setText("8,888 cr.");
        sellPriceLabelValue2.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue1.setLocation(new Point(466, 84));
        targetPercentageLabelValue1.setSize(new Size(37, 13));
        targetPercentageLabelValue1.setTabIndex(55);
        targetPercentageLabelValue1.setText("-888%");
        targetPercentageLabelValue1.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue1.setLocation(new Point(410, 84));
        targetDiffLabelValue1.setSize(new Size(52, 13));
        targetDiffLabelValue1.setTabIndex(54);
        targetDiffLabelValue1.setText("-8,888 cr.");
        targetDiffLabelValue1.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue1.setLocation(new Point(358, 84));
        targetPriceLabelValue1.setSize(new Size(48, 13));
        targetPriceLabelValue1.setTabIndex(53);
        targetPriceLabelValue1.setText("8,888 cr.");
        targetPriceLabelValue1.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton1.setLocation(new Point(262, 80));
        buyMaxButton1.setSize(new Size(36, 22));
        buyMaxButton1.setTabIndex(19);

        buyButton1.setLocation(new Point(227, 80));
        buyButton1.setSize(new Size(28, 22));
        buyButton1.setTabIndex(18);

        buyPriceLabelValue1.setLocation(new Point(302, 84));
        buyPriceLabelValue1.setSize(new Size(48, 13));
        buyPriceLabelValue1.setTabIndex(50);
        buyPriceLabelValue1.setText("8,888 cr.");
        buyPriceLabelValue1.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton1.setLocation(new Point(115, 80));
        sellAllButton1.setSize(new Size(44, 22));
        sellAllButton1.setTabIndex(17);

        sellButton1.setLocation(new Point(80, 80));
        sellButton1.setSize(new Size(28, 22));
        sellButton1.setTabIndex(16);

        sellPriceLabelValue1.setLocation(new Point(163, 84));
        sellPriceLabelValue1.setSize(new Size(48, 13));
        sellPriceLabelValue1.setTabIndex(38);
        sellPriceLabelValue1.setText("8,888 cr.");
        sellPriceLabelValue1.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPercentageLabelValue0.setLocation(new Point(466, 60));
        targetPercentageLabelValue0.setSize(new Size(37, 13));
        targetPercentageLabelValue0.setTabIndex(46);
        targetPercentageLabelValue0.setText("-888%");
        targetPercentageLabelValue0.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetDiffLabelValue0.setLocation(new Point(410, 60));
        targetDiffLabelValue0.setSize(new Size(52, 13));
        targetDiffLabelValue0.setTabIndex(45);
        targetDiffLabelValue0.setText("-8,888 cr.");
        targetDiffLabelValue0.setTextAlign(ContentAlignment.TOP_RIGHT);

        targetPriceLabelValue0.setLocation(new Point(358, 60));
        targetPriceLabelValue0.setSize(new Size(48, 13));
        targetPriceLabelValue0.setTabIndex(44);
        targetPriceLabelValue0.setText("8,888 cr.");
        targetPriceLabelValue0.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyMaxButton0.setLocation(new Point(262, 56));
        buyMaxButton0.setSize(new Size(36, 22));
        buyMaxButton0.setTabIndex(15);

        buyButton0.setLocation(new Point(227, 56));
        buyButton0.setSize(new Size(28, 22));
        buyButton0.setTabIndex(14);

        buyPriceLabelValue0.setLocation(new Point(302, 60));
        buyPriceLabelValue0.setSize(new Size(48, 13));
        buyPriceLabelValue0.setTabIndex(41);
        buyPriceLabelValue0.setText("8,888 cr.");
        buyPriceLabelValue0.setTextAlign(ContentAlignment.TOP_RIGHT);

        sellAllButton0.setLocation(new Point(115, 56));
        sellAllButton0.setSize(new Size(44, 22));
        sellAllButton0.setTabIndex(13);

        sellButton0.setLocation(new Point(80, 56));
        sellButton0.setSize(new Size(28, 22));
        sellButton0.setTabIndex(12);


        sellPriceLabelValue0.setLocation(new Point(163, 60));
        sellPriceLabelValue0.setSize(new Size(48, 13));
        sellPriceLabelValue0.setTabIndex(35);
        sellPriceLabelValue0.setText("8,888 cr.");
        sellPriceLabelValue0.setTextAlign(ContentAlignment.TOP_RIGHT);

        Arrays.stream(sellButtonArray).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(sellAllButtonArray).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setText("All");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(buyButtonArray).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(buyMaxButtonArray).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setText("Max");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        ilChartImages.setImageSize(new Size(7, 7));
        ilChartImages.setImageStream(((ImageListStreamer) (resources.getObject("ilChartImages.ImageStream"))));
        ilChartImages.setTransparentColor(Color.white);

        ilShipImages.setImageSize(new Size(64, 52));
        ilShipImages.setImageStream(((ImageListStreamer) (resources.getObject("ilShipImages.ImageStream"))));
        ilShipImages.setTransparentColor(Color.white);

        ilDirectionImages.setImageSize(new Size(13, 13));
        ilDirectionImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilDirectionImages.ImageStream"))));
        ilDirectionImages.setTransparentColor(Color.white);

        ilEquipmentImages.setImageSize(new Size(64, 52));
        ilEquipmentImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilEquipmentImages.ImageStream"))));
        ilEquipmentImages.setTransparentColor(Color.white);
    }

    void update() {
        if (game == null || game.getCommander().getCurrentSystem() == null) {
            for (int i = 0; i < sellPriceArray.length; i++) {
                sellPriceArray[i].setText("");
                buyPriceArray[i].setText("");
                targetPriceArray[i].setText("");
                targetDiffArray[i].setText("");
                targetPercentageArray[i].setText("");
                sellButtonArray[i].setVisible(false);
                sellAllButtonArray[i].setVisible(false);
                buyButtonArray[i].setVisible(false);
                buyMaxButtonArray[i].setVisible(false);
            }
            return;
        }
        int[] buy = game.getPriceCargoBuy();
        int[] sell = game.getPriceCargoSell();
        Commander commander = game.getCommander();
        StarSystem warpSystem = game.getWarpSystem();

        for (int i = 0; i < sellPriceArray.length; i++) {
            int price = (warpSystem == null) ? 0 : Consts.TradeItems[i].standardPrice(warpSystem);

            sellPriceArray[i].setText(sell[i] > 0 ? formatMoney(sell[i]) : CargoSellNA);
            sellButtonArray[i].setText(commander.getShip().getCargo()[i]);
            sellButtonArray[i].setVisible(true);
            
            sellAllButtonArray[i].setText(sell[i] > 0 ? CargoAll : CargoDump);
            sellAllButtonArray[i].setVisible(true);
            
            buyPriceArray[i].setText(buy[i] > 0 ? formatMoney(buy[i]) : CargoBuyNA);
            
            buyButtonArray[i].setText(commander.getCurrentSystem().getTradeItems()[i]);
            buyButtonArray[i].setVisible(buy[i] > 0);
            
            buyMaxButtonArray[i].setVisible(buy[i] > 0);

            if (sell[i] * commander.getShip().getCargo()[i] > commander.getPriceCargo()[i]) {
                sellPriceArray[i].setFont(BOLD_FONT);
            } else {
                sellPriceArray[i].setFont(sellPriceLabel.getFont());
            }

            if (warpSystem != null && warpSystem.destOk() && price > 0) {
                targetPriceArray[i].setText(formatMoney(price));
            } else {
                targetPriceArray[i].setText(TARGET_PRICE_NA);
            }

            if (warpSystem != null && warpSystem.destOk() && price > 0 && buy[i] > 0) {
                int diff = price - buy[i];
                targetDiffArray[i].setText((diff > 0 ? "+" : "")  + formatMoney(diff));
                targetPercentageArray[i].setText((diff > 0 ? "+" : "") + formatNumber(100 * diff / buy[i]) + "%");
                buyPriceArray[i].setFont((diff > 0 && commander.getCurrentSystem().getTradeItems()[i] > 0) 
                        ? BOLD_FONT : buyPriceLabel.getFont());
            } else {
                targetDiffArray[i].setText(TARGET_DIFF_NA);
                targetPercentageArray[i].setText(TARGET_PERCENTAGE_NA);
                buyPriceArray[i].setFont(buyPriceLabel.getFont());
            }

            targetPriceArray[i].setFont(buyPriceArray[i].getFont());
            targetDiffArray[i].setFont(buyPriceArray[i].getFont());
            targetPercentageArray[i].setFont(buyPriceArray[i].getFont());
        }
    }

    private void buySellButtonClick(String buttonName) {
        boolean all = buttonName.endsWith("AllButton");
        int index = Integer.parseInt(buttonName.substring(buttonName.length() - 1));

        if (!buttonName.startsWith("buy")) {
            controller.cargoSell(index, all);
        } else {
            controller.cargoBuy(index, all);
        }
    }

}

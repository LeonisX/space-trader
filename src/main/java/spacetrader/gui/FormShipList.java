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

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.*;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

public class FormShipList extends SpaceTraderForm {

    private final int[] prices = new int[Consts.ShipSpecs.length];

    private final Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private Button buyButton0 = new Button();
    private Label nameLabel0 = new Label();
    private Button infoButton0 = new Button();
    private Label priceLabelValue0 = new Label();
    private Label priceLabelValue1 = new Label();
    private Button infoButton1 = new Button();
    private Label nameLabel1 = new Label();
    private Button buyButton1 = new Button();
    private Label priceLabelValue2 = new Label();
    private Button infoButton2 = new Button();
    private Label nameLabel2 = new Label();
    private Button buyButton2 = new Button();
    private Label priceLabelValue3 = new Label();
    private Button infoButton3 = new Button();
    private Label nameLabel3 = new Label();
    private Button buyButton3 = new Button();
    private Label priceLabelValue4 = new Label();
    private Button infoButton4 = new Button();
    private Label nameLabel4 = new Label();
    private Button buyButton4 = new Button();
    private Label priceLabelValue5 = new Label();
    private Button infoButton5 = new Button();
    private Label nameLabel5 = new Label();
    private Button buyButton5 = new Button();
    private Label priceLabelValue6 = new Label();
    private Button infoButton6 = new Button();
    private Label nameLabel6 = new Label();
    private Button buyButton6 = new Button();
    private Label priceLabelValue7 = new Label();
    private Button infoButton7 = new Button();
    private Label nameLabel7 = new Label();
    private Button buyButton7 = new Button();
    private Label priceLabelValue8 = new Label();
    private Button infoButton8 = new Button();
    private Label nameLabel8 = new Label();
    private Button buyButton8 = new Button();
    private Label priceLabelValue9 = new Label();
    private Button infoButton9 = new Button();
    private Label nameLabel9 = new Label();
    private Button buyButton9 = new Button();
    private Panel shipInfoPanel = new Panel();
    private Label sizeLabel = new Label();
    private Label nameLabel = new Label();
    private Label baysLabel = new Label();
    private Label rangeLabel = new Label();
    private Label hullLabel = new Label();
    private Label weaponLabel = new Label();
    private Label shieldLabel = new Label();
    private Label crewLabel = new Label();
    private Label gadgetLabel = new Label();
    private PictureBox shipPictureBox = new PictureBox();
    private Label nameLabelValue = new Label();
    private Label sizeLabelValue = new Label();
    private Label baysLabelValue = new Label();
    private Label rangeLabelValue = new Label();
    private Label hullLabelValue = new Label();
    private Label weaponLabelValue = new Label();
    private Label shieldLabelValue = new Label();
    private Label gadgetLabelValue = new Label();
    private Label crewLabelValue = new Label();

    private final Label[] priceLabels = new Label[]{priceLabelValue0, priceLabelValue1, priceLabelValue2,
            priceLabelValue3, priceLabelValue4, priceLabelValue5, priceLabelValue6, priceLabelValue7, 
            priceLabelValue8, priceLabelValue9};

    private final Label[] nameLabels = new Label[]{nameLabel0, nameLabel1, nameLabel2, nameLabel3, nameLabel4,
            nameLabel5, nameLabel6, nameLabel7, nameLabel8, nameLabel9};

    private final Button[] buyButtons = new Button[]{buyButton0, buyButton1, buyButton2, buyButton3,
            buyButton4, buyButton5, buyButton6, buyButton7, buyButton8, buyButton9};

    private final Button[] infoButtons = new Button[]{infoButton0, infoButton1, infoButton2, infoButton3,
            infoButton4, infoButton5, infoButton6, infoButton7, infoButton8, infoButton9};

    public FormShipList() {
        initializeComponent();
        
        updateAll();
        info(game.getCommander().getShip().getType().castToInt());

        if (game.getCommander().getShip().getTribbles() > 0 && !game.getTribbleMessage()) {
            GuiFacade.alert(AlertType.TribblesTradeIn);
            game.setTribbleMessage(true);
        }
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formShipList");
        setText("Ship List");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(5, 13);
        setClientSize(438, 255);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        buyButton0.setLocation(8, 8);
        buyButton0.setTabIndex(1);

        nameLabel0.setLocation(48, 12);
        nameLabel0.setTabIndex(34);

        infoButton0.setLocation(120, 8);
        infoButton0.setTabIndex(11);

        priceLabelValue0.setLocation(160, 12);
        priceLabelValue0.setTabIndex(36);

        priceLabelValue1.setLocation(160, 36);
        priceLabelValue1.setTabIndex(40);

        infoButton1.setLocation(120, 32);
        infoButton1.setTabIndex(12);

        nameLabel1.setLocation(48, 36);
        nameLabel1.setTabIndex(38);

        buyButton1.setLocation(8, 32);
        buyButton1.setTabIndex(2);

        priceLabelValue2.setLocation(160, 60);
        priceLabelValue2.setTabIndex(44);

        infoButton2.setLocation(120, 56);
        infoButton2.setTabIndex(13);

        nameLabel2.setLocation(48, 60);
        nameLabel2.setTabIndex(42);

        buyButton2.setLocation(8, 56);
        buyButton2.setTabIndex(3);

        priceLabelValue3.setLocation(160, 84);
        priceLabelValue3.setTabIndex(48);

        infoButton3.setLocation(120, 80);
        infoButton3.setTabIndex(14);

        nameLabel3.setLocation(48, 84);
        nameLabel3.setTabIndex(46);

        buyButton3.setLocation(8, 80);
        buyButton3.setTabIndex(4);

        priceLabelValue4.setLocation(160, 108);
        priceLabelValue4.setTabIndex(52);

        infoButton4.setLocation(120, 104);
        infoButton4.setTabIndex(15);

        nameLabel4.setLocation(48, 108);
        nameLabel4.setTabIndex(50);

        buyButton4.setLocation(8, 104);
        buyButton4.setTabIndex(5);

        priceLabelValue5.setLocation(160, 132);
        priceLabelValue5.setTabIndex(56);

        infoButton5.setLocation(120, 128);
        infoButton5.setTabIndex(16);

        nameLabel5.setLocation(48, 132);
        nameLabel5.setTabIndex(54);

        buyButton5.setLocation(8, 128);
        buyButton5.setTabIndex(6);

        priceLabelValue6.setLocation(160, 156);
        priceLabelValue6.setTabIndex(60);

        infoButton6.setLocation(120, 152);
        infoButton6.setTabIndex(17);

        nameLabel6.setLocation(48, 156);
        nameLabel6.setTabIndex(58);

        buyButton6.setLocation(8, 152);
        buyButton6.setTabIndex(7);

        priceLabelValue7.setLocation(160, 180);
        priceLabelValue7.setTabIndex(64);

        infoButton7.setLocation(120, 176);
        infoButton7.setTabIndex(18);

        nameLabel7.setLocation(48, 180);
        nameLabel7.setTabIndex(62);

        buyButton7.setLocation(8, 176);
        buyButton7.setTabIndex(8);

        priceLabelValue8.setLocation(160, 204);
        priceLabelValue8.setTabIndex(68);

        infoButton8.setLocation(120, 200);
        infoButton8.setTabIndex(19);

        nameLabel8.setLocation(48, 204);
        nameLabel8.setTabIndex(66);

        buyButton8.setLocation(8, 200);
        buyButton8.setTabIndex(9);

        priceLabelValue9.setLocation(160, 228);
        priceLabelValue9.setTabIndex(72);

        infoButton9.setLocation(120, 224);
        infoButton9.setTabIndex(20);

        nameLabel9.setLocation(48, 228);
        nameLabel9.setTabIndex(70);

        buyButton9.setLocation(8, 224);
        buyButton9.setTabIndex(10);

        Arrays.stream(priceLabels).forEach(label -> {
            label.setSize(64, 13);
            //label.setText("-888,888 cr.");
            label.setTextAlign(ContentAlignment.TOP_RIGHT);
        });

        for (int i = 0; i < nameLabels.length; i++) {
            nameLabels[i].setSize(70, 13);
            nameLabels[i].setText(Strings.ShipNames[i]);
        }

        Arrays.stream(buyButtons).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setSize(35, 22);
            button.setText(Strings.ShipListBuy);
            button.setVisible(false);
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buyInfoButtonClick(sender);
                }
            });
        });

        Arrays.stream(infoButtons).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setSize(34, 22);
            button.setText(Strings.ShipListInfo);
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buyInfoButtonClick(sender);
                }
            });
        });

        shipInfoPanel.getControls().addAll(shipPictureBox, nameLabel, nameLabelValue, sizeLabel, sizeLabelValue,
                baysLabel, baysLabelValue, rangeLabel, rangeLabelValue, hullLabel, hullLabelValue,
                weaponLabel, weaponLabelValue, shieldLabel, shieldLabelValue, gadgetLabelValue, gadgetLabel,
                crewLabelValue, crewLabel);

        shipInfoPanel.setLocation(232, 0);
        shipInfoPanel.setSize(200, 248);
        shipInfoPanel.setTabStop(false);
        shipInfoPanel.setText("Ship Information");

        shipPictureBox.setBackground(Color.WHITE);
        shipPictureBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipPictureBox.setLocation(67, 25);
        shipPictureBox.setSize(66, 54);
        shipPictureBox.setTabIndex(12);
        shipPictureBox.setTabStop(false);

        nameLabel.setAutoSize(true);
        nameLabel.setFont(FontCollection.bold825);
        nameLabel.setLocation(8, 96);
        nameLabel.setSize(39, 13);
        nameLabel.setTabIndex(4);
        nameLabel.setText("Name:");

        nameLabelValue.setLocation(96, 96);
        nameLabelValue.setSize(100, 13);
        nameLabelValue.setTabIndex(35);
        //nameLabelValue.setText("Grasshopper");

        sizeLabel.setAutoSize(true);
        sizeLabel.setFont(FontCollection.bold825);
        sizeLabel.setLocation(8, 112);
        sizeLabel.setSize(31, 13);
        sizeLabel.setTabIndex(3);
        sizeLabel.setText("Size:");

        sizeLabelValue.setLocation(96, 112);
        sizeLabelValue.setSize(45, 13);
        sizeLabelValue.setTabIndex(36);
        //sizeLabelValue.setText("Medium");

        baysLabel.setAutoSize(true);
        baysLabel.setFont(FontCollection.bold825);
        baysLabel.setLocation(8, 128);
        baysLabel.setSize(69, 13);
        baysLabel.setTabIndex(5);
        baysLabel.setText("Cargo Bays:");

        baysLabelValue.setLocation(96, 128);
        baysLabelValue.setSize(17, 13);
        baysLabelValue.setTabIndex(37);
        //baysLabelValue.setText("88");

        rangeLabel.setAutoSize(true);
        rangeLabel.setFont(FontCollection.bold825);
        rangeLabel.setLocation(8, 144);
        rangeLabel.setSize(42, 13);
        rangeLabel.setTabIndex(6);
        rangeLabel.setText("Range:");

        rangeLabelValue.setLocation(96, 144);
        rangeLabelValue.setSize(59, 13);
        rangeLabelValue.setTabIndex(38);
        //rangeLabelValue.setText("88 parsecs");

        hullLabel.setAutoSize(true);
        hullLabel.setFont(FontCollection.bold825);
        hullLabel.setLocation(8, 160);
        hullLabel.setSize(73, 13);
        hullLabel.setTabIndex(7);
        hullLabel.setText("Hull Strength");

        hullLabelValue.setLocation(96, 160);
        hullLabelValue.setSize(23, 13);
        hullLabelValue.setTabIndex(39);
        //hullLabelValue.setText("888");

        weaponLabel.setAutoSize(true);
        weaponLabel.setFont(FontCollection.bold825);
        weaponLabel.setLocation(8, 176);
        weaponLabel.setSize(81, 13);
        weaponLabel.setTabIndex(8);
        weaponLabel.setText("Weapon Slots:");

        weaponLabelValue.setLocation(96, 176);
        weaponLabelValue.setSize(10, 13);
        weaponLabelValue.setTabIndex(40);
        //weaponLabelValue.setText("8");

        shieldLabel.setAutoSize(true);
        shieldLabel.setFont(FontCollection.bold825);
        shieldLabel.setLocation(8, 192);
        shieldLabel.setSize(70, 13);
        shieldLabel.setTabIndex(9);
        shieldLabel.setText("Shield Slots:");

        shieldLabelValue.setLocation(96, 192);
        shieldLabelValue.setSize(10, 13);
        shieldLabelValue.setTabIndex(41);
        //shieldLabelValue.setText("8");

        gadgetLabel.setAutoSize(true);
        gadgetLabel.setFont(FontCollection.bold825);
        gadgetLabel.setLocation(8, 208);
        gadgetLabel.setSize(76, 13);
        gadgetLabel.setTabIndex(11);
        gadgetLabel.setText("Gadget Slots:");

        gadgetLabelValue.setLocation(96, 208);
        gadgetLabelValue.setSize(10, 13);
        gadgetLabelValue.setTabIndex(42);
        //gadgetLabelValue.setText("8");

        crewLabel.setAutoSize(true);
        crewLabel.setFont(FontCollection.bold825);
        crewLabel.setLocation(8, 224);
        crewLabel.setSize(84, 13);
        crewLabel.setTabIndex(10);
        crewLabel.setText("Crew Quarters:");

        crewLabelValue.setLocation(96, 224);
        crewLabelValue.setSize(10, 13);
        crewLabelValue.setTabIndex(43);
        //crewLabelValue.setText("8");

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabIndex(32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(nameLabels);
        controls.addAll(priceLabels);
        controls.addAll(buyButtons);
        controls.addAll(infoButtons);
        controls.addAll(shipInfoPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void buy(int id) {
        info(id);

        if (game.getCommander().isTradeShip(Consts.ShipSpecs[id], prices[id])) {
            if (game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DONE) {
                game.setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_NOT_STARTED);
            }

            updateAll();
            game.getParentWindow().updateAll();
        }
    }

    private void info(int id) {
        ShipSpec spec = Consts.ShipSpecs[id];

        shipPictureBox.setImage(spec.getImage());
        nameLabelValue.setText(spec.getName());
        sizeLabelValue.setText(Strings.Sizes[spec.getSize().castToInt()]);
        baysLabelValue.setText(Functions.formatNumber(spec.getCargoBays()));
        rangeLabelValue.setText(Functions.multiples(spec.getFuelTanks(), Strings.DistanceUnit));
        hullLabelValue.setText(Functions.formatNumber(spec.getHullStrength()));
        weaponLabelValue.setText(Functions.formatNumber(spec.getWeaponSlots()));
        shieldLabelValue.setText(Functions.formatNumber(spec.getShieldSlots()));
        gadgetLabelValue.setText(Functions.formatNumber(spec.getGadgetSlots()));
        crewLabelValue.setText(Functions.formatNumber(spec.getCrewQuarters()));
    }

    private void updateAll() {
        for (int i = 0; i < priceLabels.length; i++) {
            buyButtons[i].setVisible(false);

            if (Consts.ShipSpecs[i].getMinimumTechLevel().castToInt() > game.getCommander().getCurrentSystem()
                    .getTechLevel().castToInt()) {
                priceLabels[i].setText(Strings.CargoBuyNA);
            } else if (Consts.ShipSpecs[i].getType() == game.getCommander().getShip().getType()) {
                priceLabels[i].setText(Strings.ShipBuyGotOne);
            } else {
                buyButtons[i].setVisible(true);
                prices[i] = Consts.ShipSpecs[i].getPrice() - game.getCommander().getShip().getWorth(false);
                priceLabels[i].setText(Functions.formatMoney(prices[i]));
            }
        }
    }

    private void buyInfoButtonClick(Object sender) {
        String name = ((Button) sender).getName();
        int index = Integer.parseInt(name.substring(name.length() - 1));

        if (name.startsWith("infoButton")) {
            info(index);
        } else {
            buy(index);
        }
    }
}

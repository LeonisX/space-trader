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
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.GadgetType;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

public class FormEquipment extends SpaceTraderForm {
    
    private Button btnClose;
    private Panel boxSell;
    private Panel boxBuy;
    private ListBox lstSellWeapon;
    private ListBox lstSellShield;
    private ListBox lstSellGadget;
    private ListBox lstBuyGadget;
    private ListBox lstBuyShield;
    private ListBox lstBuyWeapon;
    private Panel boxShipInfo;
    private Label lblName;
    private Label lblDescription;
    private PictureBox picEquipment;
    private Label lblSellPrice;
    private Label lblBuyPrice;
    private Label lblSellGadgets;
    private Label lblSellShields;
    private Label lblSellWeapons;
    private Label lblBuyGadgets;
    private Label lblBuyShields;
    private Label lblBuyWeapons;
    private Button btnBuy;
    private Button btnSell;
    private Label lblBuyPriceLabel;
    private Label lblSellPriceLabel;
    private Label lblNameLabel;
    private Label lblTypeLabel;
    private Label lblType;
    private Label lblPowerLabel;
    private Label lblChargeLabel;
    private Label lblPower;
    private Label lblCharge;
    private Label lblSellWeaponNoSlots;
    private Label lblSellShieldNoSlots;
    private Label lblSellGadgetNoSlots;
    private Label lblBuyWeaponNone;
    private Label lblBuyShieldNone;
    private Label lblBuyGadgetNone;
    
    private Game game = Game.getCurrentGame();

    private Equipment[] equipBuy = Consts.EquipmentForSale;
    private Equipment selectedEquipment = null;
    private boolean sellSideSelected = false;
    private boolean handlingSelect = false;
    
    FormEquipment() {
        initializeComponent();

        updateBuy();
        updateSell();
    }

    public static void main(String[] args) throws Exception {
        FormEquipment form = new FormEquipment();
        Launcher.runForm(form);
    }

    private void initializeComponent() {
        btnClose = new Button();
        boxSell = new Panel();
        lblSellGadgetNoSlots = new Label();
        lblSellShieldNoSlots = new Label();
        lblSellWeaponNoSlots = new Label();
        lblSellGadgets = new Label();
        lblSellShields = new Label();
        lblSellWeapons = new Label();
        lstSellGadget = new ListBox();
        lstSellShield = new ListBox();
        lstSellWeapon = new ListBox();
        boxBuy = new Panel();
        lblBuyGadgetNone = new Label();
        lblBuyShieldNone = new Label();
        lblBuyWeaponNone = new Label();
        lblBuyGadgets = new Label();
        lblBuyShields = new Label();
        lblBuyWeapons = new Label();
        lstBuyGadget = new ListBox();
        lstBuyShield = new ListBox();
        lstBuyWeapon = new ListBox();
        boxShipInfo = new Panel();
        lblCharge = new Label();
        lblPower = new Label();
        lblChargeLabel = new Label();
        lblPowerLabel = new Label();
        lblType = new Label();
        lblTypeLabel = new Label();
        lblNameLabel = new Label();
        btnSell = new Button();
        btnBuy = new Button();
        lblBuyPriceLabel = new Label();
        lblBuyPrice = new Label();
        lblSellPriceLabel = new Label();
        picEquipment = new PictureBox();
        lblSellPrice = new Label();
        lblDescription = new Label();
        lblName = new Label();
        boxSell.suspendLayout();
        boxBuy.suspendLayout();
        boxShipInfo.suspendLayout();
        suspendLayout();

        btnClose.setDialogResult(DialogResult.CANCEL);
        btnClose.setLocation(new Point(-32, -32));
        btnClose.setName("btnClose");
        btnClose.setSize(new Size(32, 32));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");

        boxSell.controls.add(lblSellGadgetNoSlots);
        boxSell.controls.add(lblSellShieldNoSlots);
        boxSell.controls.add(lblSellWeaponNoSlots);
        boxSell.controls.add(lblSellGadgets);
        boxSell.controls.add(lblSellShields);
        boxSell.controls.add(lblSellWeapons);
        boxSell.controls.add(lstSellGadget);
        boxSell.controls.add(lstSellShield);
        boxSell.controls.add(lstSellWeapon);
        boxSell.setLocation(new Point(4, 2));
        boxSell.setName("boxSell");
        boxSell.setSize(new Size(144, 304));
        boxSell.setTabIndex(1);
        boxSell.setTabStop(false);
        boxSell.setText("Current Inventory");

        lblSellGadgetNoSlots.setLocation(new Point(24, 228));
        lblSellGadgetNoSlots.setName("lblSellGadgetNoSlots");
        lblSellGadgetNoSlots.setSize(new Size(104, 16));
        lblSellGadgetNoSlots.setTabIndex(149);
        lblSellGadgetNoSlots.setText("No slots");
        lblSellGadgetNoSlots.setVisible(false);

        lblSellShieldNoSlots.setLocation(new Point(24, 132));
        lblSellShieldNoSlots.setName("lblSellShieldNoSlots");
        lblSellShieldNoSlots.setSize(new Size(104, 16));
        lblSellShieldNoSlots.setTabIndex(148);
        lblSellShieldNoSlots.setText("No slots");
        lblSellShieldNoSlots.setVisible(false);

        lblSellWeaponNoSlots.setLocation(new Point(24, 36));
        lblSellWeaponNoSlots.setName("lblSellWeaponNoSlots");
        lblSellWeaponNoSlots.setSize(new Size(104, 16));
        lblSellWeaponNoSlots.setTabIndex(147);
        lblSellWeaponNoSlots.setText("No slots");
        lblSellWeaponNoSlots.setVisible(false);

        lblSellGadgets.setAutoSize(true);
        lblSellGadgets.setLocation(new Point(8, 212));
        lblSellGadgets.setName("lblSellGadgets");
        lblSellGadgets.setSize(new Size(47, 16));
        lblSellGadgets.setTabIndex(146);
        lblSellGadgets.setText("Gadgets");

        lblSellShields.setAutoSize(true);
        lblSellShields.setLocation(new Point(8, 116));
        lblSellShields.setName("lblSellShields");
        lblSellShields.setSize(new Size(41, 16));
        lblSellShields.setTabIndex(145);
        lblSellShields.setText("Shields");

        lblSellWeapons.setAutoSize(true);
        lblSellWeapons.setLocation(new Point(8, 20));
        lblSellWeapons.setName("lblSellWeapons");
        lblSellWeapons.setSize(new Size(52, 16));
        lblSellWeapons.setTabIndex(144);
        lblSellWeapons.setText("Weapons");

        lstSellGadget.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lstSellGadget.setLocation(new Point(8, 228));
        lstSellGadget.setName("lstSellGadget");
        lstSellGadget.setSize(new Size(128, 67));
        lstSellGadget.setTabIndex(3);
        lstSellGadget.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });
        lstSellGadget.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        lstSellShield.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lstSellShield.setLocation(new Point(8, 132));
        lstSellShield.setName("lstSellShield");
        lstSellShield.setSize(new Size(128, 67));
        lstSellShield.setTabIndex(2);
        lstSellShield.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });
        lstSellShield.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        lstSellWeapon.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lstSellWeapon.setLocation(new Point(8, 36));
        lstSellWeapon.setName("lstSellWeapon");
        lstSellWeapon.setSize(new Size(128, 67));
        lstSellWeapon.setTabIndex(1);
        lstSellWeapon.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });
        lstSellWeapon.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        boxBuy.controls.add(lblBuyGadgetNone);
        boxBuy.controls.add(lblBuyShieldNone);
        boxBuy.controls.add(lblBuyWeaponNone);
        boxBuy.controls.add(lblBuyGadgets);
        boxBuy.controls.add(lblBuyShields);
        boxBuy.controls.add(lblBuyWeapons);
        boxBuy.controls.add(lstBuyGadget);
        boxBuy.controls.add(lstBuyShield);
        boxBuy.controls.add(lstBuyWeapon);
        boxBuy.setLocation(new Point(156, 2));
        boxBuy.setName("boxBuy");
        boxBuy.setSize(new Size(144, 304));
        boxBuy.setTabIndex(2);
        boxBuy.setTabStop(false);
        boxBuy.setText("Equipment For Sale");

        lblBuyGadgetNone.setLocation(new Point(24, 228));
        lblBuyGadgetNone.setName("lblBuyGadgetNone");
        lblBuyGadgetNone.setSize(new Size(104, 16));
        lblBuyGadgetNone.setTabIndex(150);
        lblBuyGadgetNone.setText("None for sale");
        lblBuyGadgetNone.setVisible(false);

        lblBuyShieldNone.setLocation(new Point(24, 132));
        lblBuyShieldNone.setName("lblBuyShieldNone");
        lblBuyShieldNone.setSize(new Size(104, 16));
        lblBuyShieldNone.setTabIndex(149);
        lblBuyShieldNone.setText("None for sale");
        lblBuyShieldNone.setVisible(false);

        lblBuyWeaponNone.setLocation(new Point(24, 36));
        lblBuyWeaponNone.setName("lblBuyWeaponNone");
        lblBuyWeaponNone.setSize(new Size(104, 16));
        lblBuyWeaponNone.setTabIndex(148);
        lblBuyWeaponNone.setText("None for sale");
        lblBuyWeaponNone.setVisible(false);

        lblBuyGadgets.setAutoSize(true);
        lblBuyGadgets.setLocation(new Point(8, 212));
        lblBuyGadgets.setName("lblBuyGadgets");
        lblBuyGadgets.setSize(new Size(47, 16));
        lblBuyGadgets.setTabIndex(143);
        lblBuyGadgets.setText("Gadgets");

        lblBuyShields.setAutoSize(true);
        lblBuyShields.setLocation(new Point(8, 116));
        lblBuyShields.setName("lblBuyShields");
        lblBuyShields.setSize(new Size(41, 16));
        lblBuyShields.setTabIndex(142);
        lblBuyShields.setText("Shields");

        lblBuyWeapons.setAutoSize(true);
        lblBuyWeapons.setLocation(new Point(8, 20));
        lblBuyWeapons.setName("lblBuyWeapons");
        lblBuyWeapons.setSize(new Size(52, 16));
        lblBuyWeapons.setTabIndex(141);
        lblBuyWeapons.setText("Weapons");

        lstBuyGadget.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lstBuyGadget.setLocation(new Point(8, 228));
        lstBuyGadget.setName("lstBuyGadget");
        lstBuyGadget.setSize(new Size(128, 67));
        lstBuyGadget.setTabIndex(6);
        lstBuyGadget.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });
        lstBuyGadget.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        lstBuyShield.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lstBuyShield.setLocation(new Point(8, 132));
        lstBuyShield.setName("lstBuyShield");
        lstBuyShield.setSize(new Size(128, 67));
        lstBuyShield.setTabIndex(5);
        lstBuyShield.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });
        lstBuyShield.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        lstBuyWeapon.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lstBuyWeapon.setLocation(new Point(8, 36));
        lstBuyWeapon.setName("lstBuyWeapon");
        lstBuyWeapon.setSize(new Size(128, 67));
        lstBuyWeapon.setTabIndex(4);
        lstBuyWeapon.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });
        lstBuyWeapon.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        boxShipInfo.controls.add(lblCharge);
        boxShipInfo.controls.add(lblPower);
        boxShipInfo.controls.add(lblChargeLabel);
        boxShipInfo.controls.add(lblPowerLabel);
        boxShipInfo.controls.add(lblType);
        boxShipInfo.controls.add(lblTypeLabel);
        boxShipInfo.controls.add(lblNameLabel);
        boxShipInfo.controls.add(btnSell);
        boxShipInfo.controls.add(btnBuy);
        boxShipInfo.controls.add(lblBuyPriceLabel);
        boxShipInfo.controls.add(lblBuyPrice);
        boxShipInfo.controls.add(lblSellPriceLabel);
        boxShipInfo.controls.add(picEquipment);
        boxShipInfo.controls.add(lblSellPrice);
        boxShipInfo.controls.add(lblName);
        boxShipInfo.controls.add(lblDescription);
        boxShipInfo.setLocation(new Point(308, 2));
        boxShipInfo.setName("boxShipInfo");
        boxShipInfo.setSize(new Size(208, 304));
        boxShipInfo.setTabIndex(3);
        boxShipInfo.setTabStop(false);
        boxShipInfo.setText("Equipment Information");

        lblCharge.setLocation(new Point(80, 164));
        lblCharge.setName("lblCharge");
        lblCharge.setSize(new Size(116, 16));
        lblCharge.setTabIndex(67);
        lblCharge.setText("888");

        lblPower.setLocation(new Point(80, 148));
        lblPower.setName("lblPower");
        lblPower.setSize(new Size(116, 16));
        lblPower.setTabIndex(66);
        lblPower.setText("888");

        lblChargeLabel.setAutoSize(true);
        lblChargeLabel.setFont(FontCollection.bold825);
        lblChargeLabel.setLocation(new Point(8, 164));
        lblChargeLabel.setName("lblChargeLabel");
        lblChargeLabel.setSize(new Size(46, 16));
        lblChargeLabel.setTabIndex(65);
        lblChargeLabel.setText("Charge:");

        lblPowerLabel.setAutoSize(true);
        lblPowerLabel.setFont(FontCollection.bold825);
        lblPowerLabel.setLocation(new Point(8, 148));
        lblPowerLabel.setName("lblPowerLabel");
        lblPowerLabel.setSize(new Size(41, 16));
        lblPowerLabel.setTabIndex(64);
        lblPowerLabel.setText("Power:");

        lblType.setLocation(new Point(80, 100));
        lblType.setName("lblType");
        lblType.setSize(new Size(116, 16));
        lblType.setTabIndex(63);
        lblType.setText("Weapon");

        lblTypeLabel.setAutoSize(true);
        lblTypeLabel.setFont(FontCollection.bold825);
        lblTypeLabel.setLocation(new Point(8, 100));
        lblTypeLabel.setName("lblTypeLabel");
        lblTypeLabel.setSize(new Size(34, 16));
        lblTypeLabel.setTabIndex(62);
        lblTypeLabel.setText("Type:");

        lblNameLabel.setAutoSize(true);
        lblNameLabel.setFont(FontCollection.bold825);
        lblNameLabel.setLocation(new Point(8, 84));
        lblNameLabel.setName("lblNameLabel");
        lblNameLabel.setSize(new Size(39, 16));
        lblNameLabel.setTabIndex(61);
        lblNameLabel.setText("Name:");

        btnSell.setFlatStyle(FlatStyle.FLAT);
        btnSell.setLocation(new Point(103, 272));
        btnSell.setName("btnSell");
        btnSell.setSize(new Size(58, 22));
        btnSell.setTabIndex(8);
        btnSell.setText("Sell");
        btnSell.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });

        btnBuy.setFlatStyle(FlatStyle.FLAT);
        btnBuy.setLocation(new Point(31, 272));
        btnBuy.setName("btnBuy");
        btnBuy.setSize(new Size(58, 22));
        btnBuy.setTabIndex(7);
        btnBuy.setText("Buy");
        btnBuy.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });

        lblBuyPriceLabel.setAutoSize(true);
        lblBuyPriceLabel.setFont(FontCollection.bold825);
        lblBuyPriceLabel.setLocation(new Point(8, 116));
        lblBuyPriceLabel.setName("lblBuyPriceLabel");
        lblBuyPriceLabel.setSize(new Size(58, 16));
        lblBuyPriceLabel.setTabIndex(57);
        lblBuyPriceLabel.setText("Buy Price:");

        lblBuyPrice.setLocation(new Point(80, 116));
        lblBuyPrice.setName("lblBuyPrice");
        lblBuyPrice.setSize(new Size(116, 16));
        lblBuyPrice.setTabIndex(56);
        lblBuyPrice.setText("888,888 cr.");

        lblSellPriceLabel.setAutoSize(true);
        lblSellPriceLabel.setFont(FontCollection.bold825);
        lblSellPriceLabel.setLocation(new Point(8, 132));
        lblSellPriceLabel.setName("lblSellPriceLabel");
        lblSellPriceLabel.setSize(new Size(58, 16));
        lblSellPriceLabel.setTabIndex(55);
        lblSellPriceLabel.setText("Sell Price:");

        picEquipment.setBackground(java.awt.Color.white);
        picEquipment.setBorderStyle(BorderStyle.FIXED_SINGLE);
        picEquipment.setLocation(new Point(71, 20));
        picEquipment.setName("picEquipment");
        picEquipment.setSize(new Size(66, 54));
        picEquipment.setTabIndex(54);
        picEquipment.setTabStop(false);
        picEquipment.setVisible(false);

        lblSellPrice.setLocation(new Point(80, 132));
        lblSellPrice.setName("lblSellPrice");
        lblSellPrice.setSize(new Size(116, 16));
        lblSellPrice.setTabIndex(52);
        lblSellPrice.setText("888,888 cr.");

        lblDescription.setLocation(new Point(8, 188));
        lblDescription.setName("lblDescription");
        lblDescription.setSize(new Size(196, 75));
        lblDescription.setTabIndex(47);

        lblName.setLocation(new Point(80, 84));
        lblName.setName("lblName");
        lblName.setSize(new Size(116, 16));
        lblName.setTabIndex(35);
        lblName.setText("Auto-Repair System");

        setAutoScaleBaseSize(new Size(5, 13));
        setCancelButton(btnClose);
        setClientSize(new Size(522, 311));
        controls.add(boxShipInfo);
        controls.add(boxBuy);
        controls.add(boxSell);
        controls.add(btnClose);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setName("formEquipment");
        setShowInTaskbar(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setText("Buy/Sell Equipment");
    }

    private void buy() {
        if (selectedEquipment != null && !sellSideSelected) {
            Commander commander = game.getCommander();
            EquipmentType baseType = selectedEquipment.getEquipmentType();

            if (baseType == EquipmentType.GADGET && commander.getShip().hasGadget(((Gadget) selectedEquipment).getType())
                    && ((Gadget) selectedEquipment).getType() != GadgetType.EXTRA_CARGO_BAYS) {
                GuiFacade.alert(AlertType.EquipmentAlreadyOwn);
            } else if (commander.getDebt() > 0) {
                GuiFacade.alert(AlertType.DebtNoBuy);
            } else if (selectedEquipment.getPrice() > commander.getCashToSpend()) {
                GuiFacade.alert(AlertType.EquipmentIF);
            } else if ((baseType == EquipmentType.WEAPON && commander.getShip().getFreeWeaponSlots() == 0)
                    || (baseType == EquipmentType.SHIELD && commander.getShip().getFreeShieldSlots() == 0)
                    || (baseType == EquipmentType.GADGET && commander.getShip().getFreeGadgetSlots() == 0)) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else if (GuiFacade.alert(AlertType.EquipmentBuy, selectedEquipment.getName(),
                    Functions.formatNumber(selectedEquipment.getPrice())) == DialogResult.YES) {
                commander.getShip().addEquipment(selectedEquipment);
                commander.setCash(commander.getCash() - selectedEquipment.getPrice());

                deselectAll();
                updateSell();
                game.getParentWindow().updateAll();
            }
        }
    }

    private void deselectAll() {
        lstSellWeapon.clearSelected();
        lstSellShield.clearSelected();
        lstSellGadget.clearSelected();
        lstBuyWeapon.clearSelected();
        lstBuyShield.clearSelected();
        lstBuyGadget.clearSelected();
    }

    private void sell() {
        if (selectedEquipment != null && sellSideSelected) {
            if (GuiFacade.alert(AlertType.EquipmentSell) == DialogResult.YES) {
                // The slot is the selected index. Two of the three list boxes
                // will have selected indices of -1, so adding
                // 2 to the total cancels those out.
                int slot = lstSellWeapon.getSelectedIndex() + lstSellShield.getSelectedIndex() + lstSellGadget.getSelectedIndex() + 2;
                Commander commander = game.getCommander();

                if (selectedEquipment.getEquipmentType() == EquipmentType.GADGET
                        && (((Gadget) selectedEquipment).getType() == GadgetType.EXTRA_CARGO_BAYS
                        || ((Gadget) selectedEquipment).getType() == GadgetType.HIDDEN_CARGO_BAYS)
                        && commander.getShip().getFreeCargoBays() < 5) {
                    GuiFacade.alert(AlertType.EquipmentExtraBaysInUse);
                } else {
                    commander.setCash(commander.getCash() + selectedEquipment.getSellPrice());
                    commander.getShip().removeEquipment(selectedEquipment.getEquipmentType(), slot);

                    updateSell();
                    game.getParentWindow().updateAll();
                }
            }
        }
    }

    private void updateBuy() {
        for (Equipment anEquipBuy : equipBuy) {
            if (anEquipBuy.getPrice() > 0) {
                switch (anEquipBuy.getEquipmentType()) {
                    case WEAPON:
                        lstBuyWeapon.getItems().add(anEquipBuy);
                        break;
                    case SHIELD:
                        lstBuyShield.getItems().add(anEquipBuy);
                        break;
                    case GADGET:
                        lstBuyGadget.getItems().add(anEquipBuy);
                        break;
                }
            }
        }

        //TODO
        ListBox[] buyBoxes = new ListBox[]{lstBuyWeapon, lstBuyShield, lstBuyGadget};
        Label[] buyLabels = new Label[]{lblBuyWeaponNone, lblBuyShieldNone, lblBuyGadgetNone};

        for (int i = 0; i < buyBoxes.length; i++) {
            boolean entries = (buyBoxes[i].getItems().size() > 0);
            buyBoxes[i].setVisible(entries);
            buyLabels[i].setVisible(!entries);
            if (entries) {
                buyBoxes[i].setHeight(buyBoxes[i].getItemHeight() * Math.min(buyBoxes[i].getItems().size(), 5) + 2);
            }
        }
    }

    private void updateInfo() {
        boolean visible = (selectedEquipment != null);
        picEquipment.setVisible(visible);
        lblNameLabel.setVisible(visible);
        lblTypeLabel.setVisible(visible);
        lblBuyPriceLabel.setVisible(visible);
        lblSellPriceLabel.setVisible(visible);
        lblPowerLabel.setVisible(visible);
        lblChargeLabel.setVisible(visible);

        if (selectedEquipment == null) {
            lblName.setText("");
            lblType.setText("");
            lblDescription.setText("");
            lblBuyPrice.setText("");
            lblSellPrice.setText("");
            lblPower.setText("");
            lblCharge.setText("");
            btnBuy.setVisible(false);
            btnSell.setVisible(false);
        } else {
            String power = "";
            String charge = "";
            switch (selectedEquipment.getEquipmentType()) {
                case WEAPON:
                    power = Integer.toString(((Weapon) selectedEquipment).getPower());
                    charge = Strings.NA;
                    break;
                case SHIELD:
                    power = Integer.toString(((Shield) selectedEquipment).getPower());
                    charge = sellSideSelected ? Integer.toString(((Shield) selectedEquipment).getCharge()) : Strings.NA;
                    break;
                case GADGET:
                    power = Strings.NA;
                    charge = Strings.NA;
                    break;
            }

            lblName.setText(selectedEquipment.getName());
            lblType.setText(Strings.EquipmentTypes[selectedEquipment.getEquipmentType().castToInt()]);
            lblDescription
                    .setText(Strings.EquipmentDescriptions[selectedEquipment.getEquipmentType().castToInt()][selectedEquipment
                            .getSubType().castToInt()]);
            lblBuyPrice.setText(Functions.formatMoney(selectedEquipment.getPrice()));
            lblSellPrice.setText(Functions.formatMoney(selectedEquipment.getSellPrice()));
            lblPower.setText(power);
            lblCharge.setText(charge);
            picEquipment.setImage(selectedEquipment.getImage());
            btnBuy.setVisible(!sellSideSelected && (selectedEquipment.getPrice() > 0));
            btnSell.setVisible(sellSideSelected);
        }
    }

    private void updateSell() {
        sellSideSelected = false;
        selectedEquipment = null;
        updateInfo();

        lstSellWeapon.getItems().clear();
        lstSellShield.getItems().clear();
        lstSellGadget.getItems().clear();

        Ship ship = Game.getCurrentGame().getCommander().getShip();
        Equipment[] equipSell;
        int index;

        equipSell = ship.getEquipmentByType(EquipmentType.WEAPON);
        for (index = 0; index < equipSell.length; index++)
            lstSellWeapon.getItems().add(equipSell[index] == null ? Strings.EquipmentFreeSlot : equipSell[index]);

        equipSell = ship.getEquipmentByType(EquipmentType.SHIELD);
        for (index = 0; index < equipSell.length; index++)
            lstSellShield.getItems().add(equipSell[index] == null ? Strings.EquipmentFreeSlot : equipSell[index]);

        equipSell = ship.getEquipmentByType(EquipmentType.GADGET);
        for (index = 0; index < equipSell.length; index++)
            lstSellGadget.getItems().add(equipSell[index] == null ? Strings.EquipmentFreeSlot : equipSell[index]);

        //TODO
        ListBox[] sellBoxes = new ListBox[]{lstSellWeapon, lstSellShield, lstSellGadget};
        Label[] sellLabels = new Label[]{lblSellWeaponNoSlots, lblSellShieldNoSlots, lblSellGadgetNoSlots};

        for (int i = 0; i < sellBoxes.length; i++) {
            boolean entries = (sellBoxes[i].getItems().size() > 0);
            sellBoxes[i].setVisible(entries);
            sellLabels[i].setVisible(!entries);
            if (entries) {
                sellBoxes[i].setHeight(sellBoxes[i].getItemHeight() * Math.min(sellBoxes[i].getItems().size(), 5) + 2);
            }
        }
    }

    private void buyButtonClick() {
        if (selectedEquipment != null) {
            buy();
        }
    }
    
    private void sellButtonClick() {
        if (selectedEquipment != null)
            sell();
    }

    private void selectedIndexChanged(Object sender) {
        if (!handlingSelect) {
            handlingSelect = true;

            Object obj = ((ListBox) sender).getSelectedItem();
            deselectAll();
            ((ListBox) sender).setSelectedItem(obj);

            //TODO
            sellSideSelected = (((ListBox) sender).getName().contains("Sell"));

            if (obj instanceof Equipment) {
                selectedEquipment = (Equipment) obj;
            } else {
                selectedEquipment = null;
            }

            handlingSelect = false;
            updateInfo();
        }
    }
}

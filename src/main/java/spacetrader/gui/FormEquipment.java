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
import spacetrader.gui.debug.Launcher;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

public class FormEquipment extends SpaceTraderForm {

    private Panel currentInventoryPanel = new Panel();
    private Panel equipmentForSalePanel = new Panel();
    private ListBox sellWeaponsListBox = new ListBox();
    private ListBox sellShieldsListBox = new ListBox();
    private ListBox sellGadgetsListBox = new ListBox();
    private ListBox buyGadgetsListBox = new ListBox();
    private ListBox buyShieldsListBox = new ListBox();
    private ListBox buyWeaponsListBox = new ListBox();
    private Panel equipmentInformationPanel = new Panel();
    private Label nameLabelValue = new Label();
    private Label descriptionLabel = new Label();
    private PictureBox equipmentPictureBox = new PictureBox();
    private Label sellPriceLabelValue = new Label();
    private Label buyPriceLabelValue = new Label();
    private Label sellGadgetsLabel = new Label();
    private Label sellShieldsLabel = new Label();
    private Label sellWeaponsLabel = new Label();
    private Label buyGadgetsLabel = new Label();
    private Label buyShieldsLabel = new Label();
    private Label buyWeaponsLabel = new Label();
    private Button buyButton = new Button();
    private Button sellButton = new Button();
    private Label buyPriceLabel = new Label();
    private Label sellPriceLabel = new Label();
    private Label nameLabel = new Label();
    private Label typeLabel = new Label();
    private Label typeLabelValue = new Label();
    private Label powerLabel = new Label();
    private Label chargeLabel = new Label();
    private Label powerLabelValue = new Label();
    private Label chargeLabelValue = new Label();
    private Label sellWeaponsNoSlotsLabel = new Label();
    private Label sellShieldsNoSlotsLabel = new Label();
    private Label sellGadgetsNoSlotsLabel = new Label();
    private Label buyWeaponNoneLabel = new Label();
    private Label buyShieldsNoneLabel = new Label();
    private Label buyGadgetsNoneLabel = new Label();
    private Button closeButton = new Button();

    ListBox[] buyListBoxes = new ListBox[]{buyWeaponsListBox, buyShieldsListBox, buyGadgetsListBox};
    Label[] buyNoneLabels = new Label[]{buyWeaponNoneLabel, buyShieldsNoneLabel, buyGadgetsNoneLabel};

    ListBox[] sellBoxes = new ListBox[]{sellWeaponsListBox, sellShieldsListBox, sellGadgetsListBox};
    Label[] sellLabels = new Label[]{sellWeaponsNoSlotsLabel, sellShieldsNoSlotsLabel, sellGadgetsNoSlotsLabel};

    private Game game = Game.getCurrentGame();

    private Equipment[] equipBuy = Consts.EquipmentForSale;
    private Equipment selectedEquipment = null;
    private boolean sellSideSelected = false;
    private boolean handlingSelect = false;
    
    public FormEquipment() {
        initializeComponent();

        updateBuy();
        updateSell();
    }

    public static void main(String[] args) throws Exception {
        FormEquipment form = new FormEquipment();
        Launcher.runForm(form);
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        currentInventoryPanel.suspendLayout();
        equipmentForSalePanel.suspendLayout();
        equipmentInformationPanel.suspendLayout();
        suspendLayout();

        setName("formEquipment");
        setText("Buy/Sell Equipment");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(522, 311));
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        currentInventoryPanel.setLocation(new Point(4, 2));
        currentInventoryPanel.setSize(new Size(144, 304));
        currentInventoryPanel.setTabStop(false);
        currentInventoryPanel.setText("Current Inventory");

        sellWeaponsLabel.setAutoSize(true);
        sellWeaponsLabel.setLocation(new Point(8, 20));
        sellWeaponsLabel.setSize(new Size(52, 16));
        sellWeaponsLabel.setTabIndex(144);
        sellWeaponsLabel.setText("Weapons");

        sellWeaponsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        sellWeaponsListBox.setLocation(new Point(8, 36));
        sellWeaponsListBox.setSize(new Size(128, 67));
        sellWeaponsListBox.setTabIndex(1);
        sellWeaponsListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });
        sellWeaponsListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        sellWeaponsNoSlotsLabel.setLocation(new Point(24, 36));
        sellWeaponsNoSlotsLabel.setSize(new Size(104, 16));
        sellWeaponsNoSlotsLabel.setTabIndex(147);
        sellWeaponsNoSlotsLabel.setText("No slots");
        sellWeaponsNoSlotsLabel.setVisible(false);

        sellShieldsLabel.setAutoSize(true);
        sellShieldsLabel.setLocation(new Point(8, 116));
        sellShieldsLabel.setSize(new Size(41, 16));
        sellShieldsLabel.setTabIndex(145);
        sellShieldsLabel.setText("Shields");

        sellShieldsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        sellShieldsListBox.setLocation(new Point(8, 132));
        sellShieldsListBox.setSize(new Size(128, 67));
        sellShieldsListBox.setTabIndex(2);
        sellShieldsListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });
        sellShieldsListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        sellShieldsNoSlotsLabel.setLocation(new Point(24, 132));
        sellShieldsNoSlotsLabel.setSize(new Size(104, 16));
        sellShieldsNoSlotsLabel.setTabIndex(148);
        sellShieldsNoSlotsLabel.setText("No slots");
        sellShieldsNoSlotsLabel.setVisible(false);

        sellGadgetsLabel.setAutoSize(true);
        sellGadgetsLabel.setLocation(new Point(8, 212));
        sellGadgetsLabel.setSize(new Size(47, 16));
        sellGadgetsLabel.setTabIndex(146);
        sellGadgetsLabel.setText("Gadgets");

        sellGadgetsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        sellGadgetsListBox.setLocation(new Point(8, 228));
        sellGadgetsListBox.setSize(new Size(128, 67));
        sellGadgetsListBox.setTabIndex(3);
        sellGadgetsListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });
        sellGadgetsListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        sellGadgetsNoSlotsLabel.setLocation(new Point(24, 228));
        sellGadgetsNoSlotsLabel.setSize(new Size(104, 16));
        sellGadgetsNoSlotsLabel.setTabIndex(149);
        sellGadgetsNoSlotsLabel.setText("No slots");
        sellGadgetsNoSlotsLabel.setVisible(false);

        currentInventoryPanel.getControls().addAll(sellWeaponsLabel, sellWeaponsListBox, sellWeaponsNoSlotsLabel);
        currentInventoryPanel.getControls().addAll(sellShieldsLabel, sellShieldsListBox, sellShieldsNoSlotsLabel);
        currentInventoryPanel.getControls().addAll(sellGadgetsLabel, sellGadgetsListBox, sellGadgetsNoSlotsLabel);

        equipmentForSalePanel.setLocation(new Point(156, 2));
        equipmentForSalePanel.setSize(new Size(144, 304));
        equipmentForSalePanel.setTabStop(false);
        equipmentForSalePanel.setText("Equipment For Sale");

        buyWeaponsLabel.setAutoSize(true);
        buyWeaponsLabel.setLocation(new Point(8, 20));
        buyWeaponsLabel.setSize(new Size(52, 16));
        buyWeaponsLabel.setTabIndex(141);
        buyWeaponsLabel.setText("Weapons");

        buyWeaponsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        buyWeaponsListBox.setLocation(new Point(8, 36));
        buyWeaponsListBox.setSize(new Size(128, 67));
        buyWeaponsListBox.setTabIndex(4);
        buyWeaponsListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });
        buyWeaponsListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        buyWeaponNoneLabel.setLocation(new Point(24, 36));
        buyWeaponNoneLabel.setSize(new Size(104, 16));
        buyWeaponNoneLabel.setTabIndex(148);
        buyWeaponNoneLabel.setText("None for sale");
        buyWeaponNoneLabel.setVisible(false);

        buyShieldsLabel.setAutoSize(true);
        buyShieldsLabel.setLocation(new Point(8, 116));
        buyShieldsLabel.setSize(new Size(41, 16));
        buyShieldsLabel.setTabIndex(142);
        buyShieldsLabel.setText("Shields");

        buyShieldsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        buyShieldsListBox.setLocation(new Point(8, 132));
        buyShieldsListBox.setSize(new Size(128, 67));
        buyShieldsListBox.setTabIndex(5);
        buyShieldsListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });
        buyShieldsListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        buyShieldsNoneLabel.setLocation(new Point(24, 132));
        buyShieldsNoneLabel.setSize(new Size(104, 16));
        buyShieldsNoneLabel.setTabIndex(149);
        buyShieldsNoneLabel.setText("None for sale");
        buyShieldsNoneLabel.setVisible(false);

        buyGadgetsLabel.setAutoSize(true);
        buyGadgetsLabel.setLocation(new Point(8, 212));
        buyGadgetsLabel.setSize(new Size(47, 16));
        buyGadgetsLabel.setTabIndex(143);
        buyGadgetsLabel.setText("Gadgets");

        buyGadgetsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        buyGadgetsListBox.setLocation(new Point(8, 228));
        buyGadgetsListBox.setSize(new Size(128, 67));
        buyGadgetsListBox.setTabIndex(6);
        buyGadgetsListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });
        buyGadgetsListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        buyGadgetsNoneLabel.setLocation(new Point(24, 228));
        buyGadgetsNoneLabel.setSize(new Size(104, 16));
        buyGadgetsNoneLabel.setTabIndex(150);
        buyGadgetsNoneLabel.setText("None for sale");
        buyGadgetsNoneLabel.setVisible(false);

        equipmentForSalePanel.getControls().addAll(buyWeaponsLabel, buyWeaponsListBox, buyWeaponNoneLabel);
        equipmentForSalePanel.getControls().addAll(buyShieldsLabel, buyShieldsListBox, buyShieldsNoneLabel);
        equipmentForSalePanel.getControls().addAll(buyGadgetsLabel, buyGadgetsListBox, buyGadgetsNoneLabel);

        equipmentInformationPanel.setLocation(new Point(308, 2));
        equipmentInformationPanel.setSize(new Size(208, 304));
        equipmentInformationPanel.setTabStop(false);
        equipmentInformationPanel.setText("Equipment Information");

        equipmentPictureBox.setBackground(Color.WHITE);
        equipmentPictureBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        equipmentPictureBox.setLocation(new Point(71, 20));
        equipmentPictureBox.setSize(new Size(66, 54));
        equipmentPictureBox.setTabStop(false);
        equipmentPictureBox.setVisible(false);

        nameLabel.setAutoSize(true);
        nameLabel.setFont(FontCollection.bold825);
        nameLabel.setLocation(new Point(8, 84));
        nameLabel.setSize(new Size(39, 16));
        nameLabel.setTabIndex(61);
        nameLabel.setText("Name:");

        nameLabelValue.setLocation(new Point(80, 84));
        nameLabelValue.setSize(new Size(116, 16));
        nameLabelValue.setTabIndex(35);
        //nameLabelValue.setText("Auto-Repair System");

        typeLabel.setAutoSize(true);
        typeLabel.setFont(FontCollection.bold825);
        typeLabel.setLocation(new Point(8, 100));
        typeLabel.setSize(new Size(34, 16));
        typeLabel.setTabIndex(62);
        typeLabel.setText("Type:");

        typeLabelValue.setLocation(new Point(80, 100));
        typeLabelValue.setSize(new Size(116, 16));
        typeLabelValue.setTabIndex(63);
        //typeLabelValue.setText("Weapon");

        buyPriceLabel.setAutoSize(true);
        buyPriceLabel.setFont(FontCollection.bold825);
        buyPriceLabel.setLocation(new Point(8, 116));
        buyPriceLabel.setSize(new Size(58, 16));
        buyPriceLabel.setTabIndex(57);
        buyPriceLabel.setText("Buy Price:");

        buyPriceLabelValue.setLocation(new Point(80, 116));
        buyPriceLabelValue.setSize(new Size(116, 16));
        buyPriceLabelValue.setTabIndex(56);
        //buyPriceLabelValue.setText("888,888 cr.");

        sellPriceLabel.setAutoSize(true);
        sellPriceLabel.setFont(FontCollection.bold825);
        sellPriceLabel.setLocation(new Point(8, 132));
        sellPriceLabel.setSize(new Size(58, 16));
        sellPriceLabel.setTabIndex(55);
        sellPriceLabel.setText("Sell Price:");

        sellPriceLabelValue.setLocation(new Point(80, 132));
        sellPriceLabelValue.setSize(new Size(116, 16));
        sellPriceLabelValue.setTabIndex(52);
        //sellPriceLabelValue.setText("888,888 cr.");

        powerLabel.setAutoSize(true);
        powerLabel.setFont(FontCollection.bold825);
        powerLabel.setLocation(new Point(8, 148));
        powerLabel.setSize(new Size(41, 16));
        powerLabel.setTabIndex(64);
        powerLabel.setText("Power:");

        powerLabelValue.setLocation(new Point(80, 148));
        powerLabelValue.setSize(new Size(116, 16));
        powerLabelValue.setTabIndex(66);
        //powerLabelValue.setText("888");

        chargeLabel.setAutoSize(true);
        chargeLabel.setFont(FontCollection.bold825);
        chargeLabel.setLocation(new Point(8, 164));
        chargeLabel.setSize(new Size(46, 16));
        chargeLabel.setTabIndex(65);
        chargeLabel.setText("Charge:");

        chargeLabelValue.setLocation(new Point(80, 164));
        chargeLabelValue.setSize(new Size(116, 16));
        chargeLabelValue.setTabIndex(67);
        //chargeLabelValue.setText("888");

        descriptionLabel.setLocation(new Point(8, 188));
        descriptionLabel.setSize(new Size(196, 75));
        descriptionLabel.setTabIndex(47);

        sellButton.setFlatStyle(FlatStyle.FLAT);
        sellButton.setLocation(new Point(103, 272));
        sellButton.setSize(new Size(58, 22));
        sellButton.setTabIndex(8);
        sellButton.setText("Sell");
        sellButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });

        buyButton.setFlatStyle(FlatStyle.FLAT);
        buyButton.setLocation(new Point(31, 272));
        buyButton.setSize(new Size(58, 22));
        buyButton.setTabIndex(7);
        buyButton.setText("Buy");
        buyButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });

        equipmentInformationPanel.getControls().addAll(equipmentPictureBox, nameLabel, nameLabelValue, typeLabel,
                typeLabelValue, buyPriceLabel, buyPriceLabelValue, sellPriceLabel, sellPriceLabelValue, powerLabel,
                powerLabelValue, chargeLabel, chargeLabelValue, descriptionLabel, sellButton, buyButton);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(new Point(-32, -32));
        closeButton.setSize(new Size(32, 32));
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(currentInventoryPanel, equipmentForSalePanel, equipmentInformationPanel, closeButton);
    }

    private void deselectAll() {
        sellWeaponsListBox.clearSelected();
        sellShieldsListBox.clearSelected();
        sellGadgetsListBox.clearSelected();
        buyWeaponsListBox.clearSelected();
        buyShieldsListBox.clearSelected();
        buyGadgetsListBox.clearSelected();
    }

    private void updateInfo() {
        boolean visible = (selectedEquipment != null);
        equipmentPictureBox.setVisible(visible);
        nameLabel.setVisible(visible);
        typeLabel.setVisible(visible);
        buyPriceLabel.setVisible(visible);
        sellPriceLabel.setVisible(visible);
        powerLabel.setVisible(visible);
        chargeLabel.setVisible(visible);

        if (!visible) {
            nameLabelValue.setText("");
            typeLabelValue.setText("");
            descriptionLabel.setText("");
            buyPriceLabelValue.setText("");
            sellPriceLabelValue.setText("");
            powerLabelValue.setText("");
            chargeLabelValue.setText("");
            buyButton.setVisible(false);
            sellButton.setVisible(false);
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

            nameLabelValue.setText(selectedEquipment.getName());
            typeLabelValue.setText(Strings.EquipmentTypes[selectedEquipment.getEquipmentType().castToInt()]);
            descriptionLabel
                    .setText(Strings.EquipmentDescriptions[selectedEquipment.getEquipmentType().castToInt()][selectedEquipment
                            .getSubType().castToInt()]);
            buyPriceLabelValue.setText(Functions.formatMoney(selectedEquipment.getPrice()));
            sellPriceLabelValue.setText(Functions.formatMoney(selectedEquipment.getSellPrice()));
            powerLabelValue.setText(power);
            chargeLabelValue.setText(charge);
            equipmentPictureBox.setImage(selectedEquipment.getImage());
            buyButton.setVisible(!sellSideSelected && (selectedEquipment.getPrice() > 0));
            sellButton.setVisible(sellSideSelected);
        }
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

    private void updateBuy() {
        for (Equipment equipmentToBuy : equipBuy) {
            if (equipmentToBuy.getPrice() > 0) {
                switch (equipmentToBuy.getEquipmentType()) {
                    case WEAPON:
                        buyWeaponsListBox.getItems().add(equipmentToBuy);
                        break;
                    case SHIELD:
                        buyShieldsListBox.getItems().add(equipmentToBuy);
                        break;
                    case GADGET:
                        buyGadgetsListBox.getItems().add(equipmentToBuy);
                        break;
                }
            }
        }

        for (int i = 0; i < buyListBoxes.length; i++) {
            boolean entries = (buyListBoxes[i].getItems().size() > 0);
            buyListBoxes[i].setVisible(entries);
            buyNoneLabels[i].setVisible(!entries);
            if (entries) {
                buyListBoxes[i].setHeight(buyListBoxes[i].getItemHeight() * Math.min(buyListBoxes[i].getItems().size(), 5) + 2);
            }
        }
    }

    private void sell() {
        if (selectedEquipment != null && sellSideSelected) {
            if (GuiFacade.alert(AlertType.EquipmentSell) == DialogResult.YES) {
                // The slot is the selected index. Two of the three list boxes
                // will have selected indices of -1, so adding
                // 2 to the total cancels those out.
                int slot = sellWeaponsListBox.getSelectedIndex() + sellShieldsListBox.getSelectedIndex()
                        + sellGadgetsListBox.getSelectedIndex() + 2;
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

    private void updateSell() {
        sellSideSelected = false;
        selectedEquipment = null;
        updateInfo();

        sellWeaponsListBox.getItems().clear();
        sellShieldsListBox.getItems().clear();
        sellGadgetsListBox.getItems().clear();

        Ship ship = Game.getCurrentGame().getCommander().getShip();

        for (Equipment equipment : ship.getEquipmentsByType(EquipmentType.WEAPON)) {
            sellWeaponsListBox.getItems().add(equipment == null ? Strings.EquipmentFreeSlot : equipment);
        }

        for (Equipment equipment : ship.getEquipmentsByType(EquipmentType.SHIELD)) {
            sellShieldsListBox.getItems().add(equipment == null ? Strings.EquipmentFreeSlot : equipment);
        }

        for (Equipment equipment : ship.getEquipmentsByType(EquipmentType.GADGET)) {
            sellGadgetsListBox.getItems().add(equipment == null ? Strings.EquipmentFreeSlot : equipment);
        }

        for (int i = 0; i < sellBoxes.length; i++) {
            boolean entries = (sellBoxes[i].getItems().size() > 0);
            sellBoxes[i].setVisible(entries);
            sellLabels[i].setVisible(!entries);
            if (entries) {
                sellBoxes[i].setHeight(sellBoxes[i].getItemHeight() * Math.min(sellBoxes[i].getItems().size(), 5) + 2);
            }
        }
    }

    private void sellButtonClick() {
        if (selectedEquipment != null) {
            sell();
        }
    }

    private void buyButtonClick() {
        if (selectedEquipment != null) {
            buy();
        }
    }

    private void selectedIndexChanged(Object sender) {
        if (!handlingSelect) {
            handlingSelect = true;

            Object selectedItem = ((ListBox) sender).getSelectedItem();
            deselectAll();
            ((ListBox) sender).setSelectedItem(selectedItem);

            sellSideSelected = (((ListBox) sender).getName().startsWith("sell"));

            if (selectedItem instanceof Equipment) {
                selectedEquipment = (Equipment) selectedItem;
            } else {
                selectedEquipment = null;
            }

            //TODO need???
            handlingSelect = false;
            updateInfo();
        }
    }
}

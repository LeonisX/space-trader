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
import spacetrader.enums.EquipmentType;
import spacetrader.enums.GadgetType;
import spacetrader.guifacade.GuiFacade;

public class FormEquipment extends SpaceTraderForm {
    private jwinforms.Button btnClose;
    private jwinforms.GroupBox boxSell;
    private jwinforms.GroupBox boxBuy;
    private jwinforms.ListBox lstSellWeapon;
    private jwinforms.ListBox lstSellShield;
    private jwinforms.ListBox lstSellGadget;
    private jwinforms.ListBox lstBuyGadget;
    private jwinforms.ListBox lstBuyShield;
    private jwinforms.ListBox lstBuyWeapon;
    private jwinforms.GroupBox boxShipInfo;
    private jwinforms.Label lblName;
    private jwinforms.Label lblDescription;
    private jwinforms.PictureBox picEquipment;
    private jwinforms.Label lblSellPrice;
    private jwinforms.Label lblBuyPrice;
    private jwinforms.Label lblSellGadgets;
    private jwinforms.Label lblSellShields;
    private jwinforms.Label lblSellWeapons;
    private jwinforms.Label lblBuyGadgets;
    private jwinforms.Label lblBuyShields;
    private jwinforms.Label lblBuyWeapons;
    private jwinforms.Button btnBuy;
    private jwinforms.Button btnSell;
    private jwinforms.Label lblBuyPriceLabel;
    private jwinforms.Label lblSellPriceLabel;
    private jwinforms.Label lblNameLabel;
    private jwinforms.Label lblTypeLabel;
    private jwinforms.Label lblType;
    private jwinforms.Label lblPowerLabel;
    private jwinforms.Label lblChargeLabel;
    private jwinforms.Label lblPower;
    private jwinforms.Label lblCharge;
    private jwinforms.Label lblSellWeaponNoSlots;
    private jwinforms.Label lblSellShieldNoSlots;
    private jwinforms.Label lblSellGadgetNoSlots;
    private jwinforms.Label lblBuyWeaponNone;
    private jwinforms.Label lblBuyShieldNone;
    private jwinforms.Label lblBuyGadgetNone;
    private Game game = Game.CurrentGame();

    // #endregion

    // #region Member Declarations
    private Equipment[] equipBuy = Consts.EquipmentForSale;
    private Equipment selectedEquipment = null;
    private boolean sellSideSelected = false;
    private boolean handlingSelect = false;
    public FormEquipment() {
        initializeComponent();

        UpdateBuy();
        UpdateSell();
    }

    // #endregion

    // #region Methods

    // #region Control Declarations
    public static void main(String[] args) throws Exception {
        FormEquipment form = new FormEquipment();
        Launcher.runForm(form);
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        this.btnClose = new jwinforms.Button();
        this.boxSell = new jwinforms.GroupBox();
        this.lblSellGadgetNoSlots = new jwinforms.Label();
        this.lblSellShieldNoSlots = new jwinforms.Label();
        this.lblSellWeaponNoSlots = new jwinforms.Label();
        this.lblSellGadgets = new jwinforms.Label();
        this.lblSellShields = new jwinforms.Label();
        this.lblSellWeapons = new jwinforms.Label();
        this.lstSellGadget = new jwinforms.ListBox();
        this.lstSellShield = new jwinforms.ListBox();
        this.lstSellWeapon = new jwinforms.ListBox();
        this.boxBuy = new jwinforms.GroupBox();
        this.lblBuyGadgetNone = new jwinforms.Label();
        this.lblBuyShieldNone = new jwinforms.Label();
        this.lblBuyWeaponNone = new jwinforms.Label();
        this.lblBuyGadgets = new jwinforms.Label();
        this.lblBuyShields = new jwinforms.Label();
        this.lblBuyWeapons = new jwinforms.Label();
        this.lstBuyGadget = new jwinforms.ListBox();
        this.lstBuyShield = new jwinforms.ListBox();
        this.lstBuyWeapon = new jwinforms.ListBox();
        this.boxShipInfo = new jwinforms.GroupBox();
        this.lblCharge = new jwinforms.Label();
        this.lblPower = new jwinforms.Label();
        this.lblChargeLabel = new jwinforms.Label();
        this.lblPowerLabel = new jwinforms.Label();
        this.lblType = new jwinforms.Label();
        this.lblTypeLabel = new jwinforms.Label();
        this.lblNameLabel = new jwinforms.Label();
        this.btnSell = new jwinforms.Button();
        this.btnBuy = new jwinforms.Button();
        this.lblBuyPriceLabel = new jwinforms.Label();
        this.lblBuyPrice = new jwinforms.Label();
        this.lblSellPriceLabel = new jwinforms.Label();
        this.picEquipment = new jwinforms.PictureBox();
        this.lblSellPrice = new jwinforms.Label();
        this.lblDescription = new jwinforms.Label();
        this.lblName = new jwinforms.Label();
        this.boxSell.suspendLayout();
        this.boxBuy.suspendLayout();
        this.boxShipInfo.suspendLayout();
        this.suspendLayout();
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.Cancel);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new jwinforms.Size(32, 32));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // boxSell
        //
        this.boxSell.controls.add(this.lblSellGadgetNoSlots);
        this.boxSell.controls.add(this.lblSellShieldNoSlots);
        this.boxSell.controls.add(this.lblSellWeaponNoSlots);
        this.boxSell.controls.add(this.lblSellGadgets);
        this.boxSell.controls.add(this.lblSellShields);
        this.boxSell.controls.add(this.lblSellWeapons);
        this.boxSell.controls.add(this.lstSellGadget);
        this.boxSell.controls.add(this.lstSellShield);
        this.boxSell.controls.add(this.lstSellWeapon);
        this.boxSell.setLocation(new java.awt.Point(4, 2));
        this.boxSell.setName("boxSell");
        this.boxSell.setSize(new jwinforms.Size(144, 304));
        this.boxSell.setTabIndex(1);
        this.boxSell.setTabStop(false);
        this.boxSell.setText("Current Inventory");
        //
        // lblSellGadgetNoSlots
        //
        this.lblSellGadgetNoSlots.setLocation(new java.awt.Point(24, 228));
        this.lblSellGadgetNoSlots.setName("lblSellGadgetNoSlots");
        this.lblSellGadgetNoSlots.setSize(new jwinforms.Size(104, 16));
        this.lblSellGadgetNoSlots.setTabIndex(149);
        this.lblSellGadgetNoSlots.setText("No slots");
        this.lblSellGadgetNoSlots.setVisible(false);
        //
        // lblSellShieldNoSlots
        //
        this.lblSellShieldNoSlots.setLocation(new java.awt.Point(24, 132));
        this.lblSellShieldNoSlots.setName("lblSellShieldNoSlots");
        this.lblSellShieldNoSlots.setSize(new jwinforms.Size(104, 16));
        this.lblSellShieldNoSlots.setTabIndex(148);
        this.lblSellShieldNoSlots.setText("No slots");
        this.lblSellShieldNoSlots.setVisible(false);
        //
        // lblSellWeaponNoSlots
        //
        this.lblSellWeaponNoSlots.setLocation(new java.awt.Point(24, 36));
        this.lblSellWeaponNoSlots.setName("lblSellWeaponNoSlots");
        this.lblSellWeaponNoSlots.setSize(new jwinforms.Size(104, 16));
        this.lblSellWeaponNoSlots.setTabIndex(147);
        this.lblSellWeaponNoSlots.setText("No slots");
        this.lblSellWeaponNoSlots.setVisible(false);
        //
        // lblSellGadgets
        //
        this.lblSellGadgets.setAutoSize(true);
        this.lblSellGadgets.setLocation(new java.awt.Point(8, 212));
        this.lblSellGadgets.setName("lblSellGadgets");
        this.lblSellGadgets.setSize(new jwinforms.Size(47, 16));
        this.lblSellGadgets.setTabIndex(146);
        this.lblSellGadgets.setText("Gadgets");
        //
        // lblSellShields
        //
        this.lblSellShields.setAutoSize(true);
        this.lblSellShields.setLocation(new java.awt.Point(8, 116));
        this.lblSellShields.setName("lblSellShields");
        this.lblSellShields.setSize(new jwinforms.Size(41, 16));
        this.lblSellShields.setTabIndex(145);
        this.lblSellShields.setText("Shields");
        //
        // lblSellWeapons
        //
        this.lblSellWeapons.setAutoSize(true);
        this.lblSellWeapons.setLocation(new java.awt.Point(8, 20));
        this.lblSellWeapons.setName("lblSellWeapons");
        this.lblSellWeapons.setSize(new jwinforms.Size(52, 16));
        this.lblSellWeapons.setTabIndex(144);
        this.lblSellWeapons.setText("Weapons");
        //
        // lstSellGadget
        //
        this.lstSellGadget.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstSellGadget.setLocation(new java.awt.Point(8, 228));
        this.lstSellGadget.setName("lstSellGadget");
        this.lstSellGadget.setSize(new jwinforms.Size(128, 67));
        this.lstSellGadget.setTabIndex(3);
        this.lstSellGadget.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SellClick(sender, e);
            }
        });
        this.lstSellGadget.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // lstSellShield
        //
        this.lstSellShield.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstSellShield.setLocation(new java.awt.Point(8, 132));
        this.lstSellShield.setName("lstSellShield");
        this.lstSellShield.setSize(new jwinforms.Size(128, 67));
        this.lstSellShield.setTabIndex(2);
        this.lstSellShield.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SellClick(sender, e);
            }
        });
        this.lstSellShield.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // lstSellWeapon
        //
        this.lstSellWeapon.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstSellWeapon.setLocation(new java.awt.Point(8, 36));
        this.lstSellWeapon.setName("lstSellWeapon");
        this.lstSellWeapon.setSize(new jwinforms.Size(128, 67));
        this.lstSellWeapon.setTabIndex(1);
        this.lstSellWeapon.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SellClick(sender, e);
            }
        });
        this.lstSellWeapon.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // boxBuy
        //
        this.boxBuy.controls.add(this.lblBuyGadgetNone);
        this.boxBuy.controls.add(this.lblBuyShieldNone);
        this.boxBuy.controls.add(this.lblBuyWeaponNone);
        this.boxBuy.controls.add(this.lblBuyGadgets);
        this.boxBuy.controls.add(this.lblBuyShields);
        this.boxBuy.controls.add(this.lblBuyWeapons);
        this.boxBuy.controls.add(this.lstBuyGadget);
        this.boxBuy.controls.add(this.lstBuyShield);
        this.boxBuy.controls.add(this.lstBuyWeapon);
        this.boxBuy.setLocation(new java.awt.Point(156, 2));
        this.boxBuy.setName("boxBuy");
        this.boxBuy.setSize(new jwinforms.Size(144, 304));
        this.boxBuy.setTabIndex(2);
        this.boxBuy.setTabStop(false);
        this.boxBuy.setText("Equipment For Sale");
        //
        // lblBuyGadgetNone
        //
        this.lblBuyGadgetNone.setLocation(new java.awt.Point(24, 228));
        this.lblBuyGadgetNone.setName("lblBuyGadgetNone");
        this.lblBuyGadgetNone.setSize(new jwinforms.Size(104, 16));
        this.lblBuyGadgetNone.setTabIndex(150);
        this.lblBuyGadgetNone.setText("None for sale");
        this.lblBuyGadgetNone.setVisible(false);
        //
        // lblBuyShieldNone
        //
        this.lblBuyShieldNone.setLocation(new java.awt.Point(24, 132));
        this.lblBuyShieldNone.setName("lblBuyShieldNone");
        this.lblBuyShieldNone.setSize(new jwinforms.Size(104, 16));
        this.lblBuyShieldNone.setTabIndex(149);
        this.lblBuyShieldNone.setText("None for sale");
        this.lblBuyShieldNone.setVisible(false);
        //
        // lblBuyWeaponNone
        //
        this.lblBuyWeaponNone.setLocation(new java.awt.Point(24, 36));
        this.lblBuyWeaponNone.setName("lblBuyWeaponNone");
        this.lblBuyWeaponNone.setSize(new jwinforms.Size(104, 16));
        this.lblBuyWeaponNone.setTabIndex(148);
        this.lblBuyWeaponNone.setText("None for sale");
        this.lblBuyWeaponNone.setVisible(false);
        //
        // lblBuyGadgets
        //
        this.lblBuyGadgets.setAutoSize(true);
        this.lblBuyGadgets.setLocation(new java.awt.Point(8, 212));
        this.lblBuyGadgets.setName("lblBuyGadgets");
        this.lblBuyGadgets.setSize(new jwinforms.Size(47, 16));
        this.lblBuyGadgets.setTabIndex(143);
        this.lblBuyGadgets.setText("Gadgets");
        //
        // lblBuyShields
        //
        this.lblBuyShields.setAutoSize(true);
        this.lblBuyShields.setLocation(new java.awt.Point(8, 116));
        this.lblBuyShields.setName("lblBuyShields");
        this.lblBuyShields.setSize(new jwinforms.Size(41, 16));
        this.lblBuyShields.setTabIndex(142);
        this.lblBuyShields.setText("Shields");
        //
        // lblBuyWeapons
        //
        this.lblBuyWeapons.setAutoSize(true);
        this.lblBuyWeapons.setLocation(new java.awt.Point(8, 20));
        this.lblBuyWeapons.setName("lblBuyWeapons");
        this.lblBuyWeapons.setSize(new jwinforms.Size(52, 16));
        this.lblBuyWeapons.setTabIndex(141);
        this.lblBuyWeapons.setText("Weapons");
        //
        // lstBuyGadget
        //
        this.lstBuyGadget.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstBuyGadget.setLocation(new java.awt.Point(8, 228));
        this.lstBuyGadget.setName("lstBuyGadget");
        this.lstBuyGadget.setSize(new jwinforms.Size(128, 67));
        this.lstBuyGadget.setTabIndex(6);
        this.lstBuyGadget.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                BuyClick(sender, e);
            }
        });
        this.lstBuyGadget.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // lstBuyShield
        //
        this.lstBuyShield.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstBuyShield.setLocation(new java.awt.Point(8, 132));
        this.lstBuyShield.setName("lstBuyShield");
        this.lstBuyShield.setSize(new jwinforms.Size(128, 67));
        this.lstBuyShield.setTabIndex(5);
        this.lstBuyShield.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                BuyClick(sender, e);
            }
        });
        this.lstBuyShield.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // lstBuyWeapon
        //
        this.lstBuyWeapon.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstBuyWeapon.setLocation(new java.awt.Point(8, 36));
        this.lstBuyWeapon.setName("lstBuyWeapon");
        this.lstBuyWeapon.setSize(new jwinforms.Size(128, 67));
        this.lstBuyWeapon.setTabIndex(4);
        this.lstBuyWeapon.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                BuyClick(sender, e);
            }
        });
        this.lstBuyWeapon.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // boxShipInfo
        //
        this.boxShipInfo.controls.add(this.lblCharge);
        this.boxShipInfo.controls.add(this.lblPower);
        this.boxShipInfo.controls.add(this.lblChargeLabel);
        this.boxShipInfo.controls.add(this.lblPowerLabel);
        this.boxShipInfo.controls.add(this.lblType);
        this.boxShipInfo.controls.add(this.lblTypeLabel);
        this.boxShipInfo.controls.add(this.lblNameLabel);
        this.boxShipInfo.controls.add(this.btnSell);
        this.boxShipInfo.controls.add(this.btnBuy);
        this.boxShipInfo.controls.add(this.lblBuyPriceLabel);
        this.boxShipInfo.controls.add(this.lblBuyPrice);
        this.boxShipInfo.controls.add(this.lblSellPriceLabel);
        this.boxShipInfo.controls.add(this.picEquipment);
        this.boxShipInfo.controls.add(this.lblSellPrice);
        this.boxShipInfo.controls.add(this.lblName);
        this.boxShipInfo.controls.add(this.lblDescription);
        this.boxShipInfo.setLocation(new java.awt.Point(308, 2));
        this.boxShipInfo.setName("boxShipInfo");
        this.boxShipInfo.setSize(new jwinforms.Size(208, 304));
        this.boxShipInfo.setTabIndex(3);
        this.boxShipInfo.setTabStop(false);
        this.boxShipInfo.setText("Equipment Information");
        //
        // lblCharge
        //
        this.lblCharge.setLocation(new java.awt.Point(80, 164));
        this.lblCharge.setName("lblCharge");
        this.lblCharge.setSize(new jwinforms.Size(116, 16));
        this.lblCharge.setTabIndex(67);
        this.lblCharge.setText("888");
        //
        // lblPower
        //
        this.lblPower.setLocation(new java.awt.Point(80, 148));
        this.lblPower.setName("lblPower");
        this.lblPower.setSize(new jwinforms.Size(116, 16));
        this.lblPower.setTabIndex(66);
        this.lblPower.setText("888");
        //
        // lblChargeLabel
        //
        this.lblChargeLabel.setAutoSize(true);
        this.lblChargeLabel.setFont(FontCollection.bold825);
        this.lblChargeLabel.setLocation(new java.awt.Point(8, 164));
        this.lblChargeLabel.setName("lblChargeLabel");
        this.lblChargeLabel.setSize(new jwinforms.Size(46, 16));
        this.lblChargeLabel.setTabIndex(65);
        this.lblChargeLabel.setText("Charge:");
        //
        // lblPowerLabel
        //
        this.lblPowerLabel.setAutoSize(true);
        this.lblPowerLabel.setFont(FontCollection.bold825);
        this.lblPowerLabel.setLocation(new java.awt.Point(8, 148));
        this.lblPowerLabel.setName("lblPowerLabel");
        this.lblPowerLabel.setSize(new jwinforms.Size(41, 16));
        this.lblPowerLabel.setTabIndex(64);
        this.lblPowerLabel.setText("Power:");
        //
        // lblType
        //
        this.lblType.setLocation(new java.awt.Point(80, 100));
        this.lblType.setName("lblType");
        this.lblType.setSize(new jwinforms.Size(116, 16));
        this.lblType.setTabIndex(63);
        this.lblType.setText("Weapon");
        //
        // lblTypeLabel
        //
        this.lblTypeLabel.setAutoSize(true);
        this.lblTypeLabel.setFont(FontCollection.bold825);
        this.lblTypeLabel.setLocation(new java.awt.Point(8, 100));
        this.lblTypeLabel.setName("lblTypeLabel");
        this.lblTypeLabel.setSize(new jwinforms.Size(34, 16));
        this.lblTypeLabel.setTabIndex(62);
        this.lblTypeLabel.setText("Type:");
        //
        // lblNameLabel
        //
        this.lblNameLabel.setAutoSize(true);
        this.lblNameLabel.setFont(FontCollection.bold825);
        this.lblNameLabel.setLocation(new java.awt.Point(8, 84));
        this.lblNameLabel.setName("lblNameLabel");
        this.lblNameLabel.setSize(new jwinforms.Size(39, 16));
        this.lblNameLabel.setTabIndex(61);
        this.lblNameLabel.setText("Name:");
        //
        // btnSell
        //
        this.btnSell.setFlatStyle(jwinforms.FlatStyle.Flat);
        this.btnSell.setLocation(new java.awt.Point(103, 272));
        this.btnSell.setName("btnSell");
        this.btnSell.setSize(new jwinforms.Size(58, 22));
        this.btnSell.setTabIndex(8);
        this.btnSell.setText("Sell");
        this.btnSell.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                SellClick(sender, e);
            }
        });
        //
        // btnBuy
        //
        this.btnBuy.setFlatStyle(jwinforms.FlatStyle.Flat);
        this.btnBuy.setLocation(new java.awt.Point(31, 272));
        this.btnBuy.setName("btnBuy");
        this.btnBuy.setSize(new jwinforms.Size(58, 22));
        this.btnBuy.setTabIndex(7);
        this.btnBuy.setText("Buy");
        this.btnBuy.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                BuyClick(sender, e);
            }
        });
        //
        // lblBuyPriceLabel
        //
        this.lblBuyPriceLabel.setAutoSize(true);
        this.lblBuyPriceLabel.setFont(FontCollection.bold825);
        this.lblBuyPriceLabel.setLocation(new java.awt.Point(8, 116));
        this.lblBuyPriceLabel.setName("lblBuyPriceLabel");
        this.lblBuyPriceLabel.setSize(new jwinforms.Size(58, 16));
        this.lblBuyPriceLabel.setTabIndex(57);
        this.lblBuyPriceLabel.setText("Buy Price:");
        //
        // lblBuyPrice
        //
        this.lblBuyPrice.setLocation(new java.awt.Point(80, 116));
        this.lblBuyPrice.setName("lblBuyPrice");
        this.lblBuyPrice.setSize(new jwinforms.Size(116, 16));
        this.lblBuyPrice.setTabIndex(56);
        this.lblBuyPrice.setText("888,888 cr.");
        //
        // lblSellPriceLabel
        //
        this.lblSellPriceLabel.setAutoSize(true);
        this.lblSellPriceLabel.setFont(FontCollection.bold825);
        this.lblSellPriceLabel.setLocation(new java.awt.Point(8, 132));
        this.lblSellPriceLabel.setName("lblSellPriceLabel");
        this.lblSellPriceLabel.setSize(new jwinforms.Size(58, 16));
        this.lblSellPriceLabel.setTabIndex(55);
        this.lblSellPriceLabel.setText("Sell Price:");
        //
        // picEquipment
        //
        this.picEquipment.setBackColor(java.awt.Color.white);
        this.picEquipment.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.picEquipment.setLocation(new java.awt.Point(71, 20));
        this.picEquipment.setName("picEquipment");
        this.picEquipment.setSize(new jwinforms.Size(66, 54));
        this.picEquipment.setTabIndex(54);
        this.picEquipment.setTabStop(false);
        this.picEquipment.setVisible(false);
        //
        // lblSellPrice
        //
        this.lblSellPrice.setLocation(new java.awt.Point(80, 132));
        this.lblSellPrice.setName("lblSellPrice");
        this.lblSellPrice.setSize(new jwinforms.Size(116, 16));
        this.lblSellPrice.setTabIndex(52);
        this.lblSellPrice.setText("888,888 cr.");
        //
        // lblDescription
        //
        this.lblDescription.setLocation(new java.awt.Point(8, 188));
        this.lblDescription.setName("lblDescription");
        this.lblDescription.setSize(new jwinforms.Size(196, 75));
        this.lblDescription.setTabIndex(47);
        //
        // lblName
        //
        this.lblName.setLocation(new java.awt.Point(80, 84));
        this.lblName.setName("lblName");
        this.lblName.setSize(new jwinforms.Size(116, 16));
        this.lblName.setTabIndex(35);
        this.lblName.setText("Auto-Repair System");
        //
        // FormEquipment
        //
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new jwinforms.Size(522, 311));
        this.Controls.add(this.boxShipInfo);
        this.Controls.add(this.boxBuy);
        this.Controls.add(this.boxSell);
        this.Controls.add(this.btnClose);
        this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormEquipment");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Buy/Sell Equipment");
        GroupBox r = this.boxSell;
        GroupBox r1 = this.boxBuy;
        GroupBox r2 = this.boxShipInfo;

    }

    // #endregion

    private void Buy() {
        if (selectedEquipment != null && !sellSideSelected) {
            Commander cmdr = game.Commander();
            EquipmentType baseType = selectedEquipment.EquipmentType();

            if (baseType == EquipmentType.Gadget && cmdr.getShip().HasGadget(((Gadget) selectedEquipment).Type())
                    && ((Gadget) selectedEquipment).Type() != GadgetType.ExtraCargoBays)
                GuiFacade.alert(AlertType.EquipmentAlreadyOwn);
            else if (cmdr.getDebt() > 0)
                GuiFacade.alert(AlertType.DebtNoBuy);
            else if (selectedEquipment.Price() > cmdr.CashToSpend())
                GuiFacade.alert(AlertType.EquipmentIF);
            else if ((baseType == EquipmentType.Weapon && cmdr.getShip().FreeSlotsWeapon() == 0)
                    || (baseType == EquipmentType.Shield && cmdr.getShip().FreeSlotsShield() == 0)
                    || (baseType == EquipmentType.Gadget && cmdr.getShip().FreeSlotsGadget() == 0))
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            else if (GuiFacade.alert(AlertType.EquipmentBuy, selectedEquipment.Name(), Functions
                    .formatNumber(selectedEquipment.Price())) == DialogResult.Yes) {
                cmdr.getShip().AddEquipment(selectedEquipment);
                cmdr.setCash(cmdr.getCash() - selectedEquipment.Price());

                DeselectAll();
                UpdateSell();
                game.getParentWindow().updateAll();
            }
        }
    }

    private void DeselectAll() {
        lstSellWeapon.clearSelected();
        lstSellShield.clearSelected();
        lstSellGadget.clearSelected();
        lstBuyWeapon.clearSelected();
        lstBuyShield.clearSelected();
        lstBuyGadget.clearSelected();
    }

    private void Sell() {
        if (selectedEquipment != null && sellSideSelected) {
            if (GuiFacade.alert(AlertType.EquipmentSell) == DialogResult.Yes) {
                // The slot is the selected index. Two of the three list boxes
                // will have selected indices of -1, so adding
                // 2 to the total cancels those out.
                int slot = lstSellWeapon.getSelectedIndex() + lstSellShield.getSelectedIndex() + lstSellGadget.getSelectedIndex() + 2;
                Commander cmdr = game.Commander();

                if (selectedEquipment.EquipmentType() == EquipmentType.Gadget
                        && (((Gadget) selectedEquipment).Type() == GadgetType.ExtraCargoBays || ((Gadget) selectedEquipment)
                        .Type() == GadgetType.HiddenCargoBays) && cmdr.getShip().FreeCargoBays() < 5) {
                    GuiFacade.alert(AlertType.EquipmentExtraBaysInUse);
                } else {
                    cmdr.setCash(cmdr.getCash() + selectedEquipment.SellPrice());
                    cmdr.getShip().RemoveEquipment(selectedEquipment.EquipmentType(), slot);

                    UpdateSell();
                    game.getParentWindow().updateAll();
                }
            }
        }
    }

    private void UpdateBuy() {
        for (int i = 0; i < equipBuy.length; i++) {
            if (equipBuy[i].Price() > 0) {
                switch (equipBuy[i].EquipmentType()) {
                    case Weapon:
                        lstBuyWeapon.Items.add(equipBuy[i]);
                        break;
                    case Shield:
                        lstBuyShield.Items.add(equipBuy[i]);
                        break;
                    case Gadget:
                        lstBuyGadget.Items.add(equipBuy[i]);
                        break;
                }
            }
        }

        ListBox[] buyBoxes = new ListBox[]{lstBuyWeapon, lstBuyShield, lstBuyGadget};
        Label[] buyLabels = new Label[]{lblBuyWeaponNone, lblBuyShieldNone, lblBuyGadgetNone};
        for (int i = 0; i < buyBoxes.length; i++) {
            boolean entries = (buyBoxes[i].Items.size() > 0);
            buyBoxes[i].setVisible(entries);
            buyLabels[i].setVisible(!entries);
            if (entries)
                buyBoxes[i].setHeight(buyBoxes[i].getItemHeight() * Math.min(buyBoxes[i].Items.size(), 5) + 2);
        }
    }

    private void UpdateInfo() {
        boolean visible = selectedEquipment != null;
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
            switch (selectedEquipment.EquipmentType()) {
                case Weapon:
                    power = "" + ((Weapon) selectedEquipment).Power();
                    charge = Strings.NA;
                    break;
                case Shield:
                    power = "" + ((Shield) selectedEquipment).Power();
                    charge = sellSideSelected ? "" + ((Shield) selectedEquipment).getCharge() : Strings.NA;
                    break;
                case Gadget:
                    power = Strings.NA;
                    charge = Strings.NA;
                    break;
            }

            lblName.setText(selectedEquipment.Name());
            lblType.setText(Strings.EquipmentTypes[selectedEquipment.EquipmentType().castToInt()]);
            lblDescription
                    .setText(Strings.EquipmentDescriptions[selectedEquipment.EquipmentType().castToInt()][selectedEquipment
                            .SubType().castToInt()]);
            lblBuyPrice.setText(Functions.formatMoney(selectedEquipment.Price()));
            lblSellPrice.setText(Functions.formatMoney(selectedEquipment.SellPrice()));
            lblPower.setText(power);
            lblCharge.setText(charge);
            picEquipment.setImage(selectedEquipment.Image());
            btnBuy.setVisible(!sellSideSelected && (selectedEquipment.Price() > 0));
            btnSell.setVisible(sellSideSelected);
        }
    }

    private void UpdateSell() {
        sellSideSelected = false;
        selectedEquipment = null;
        UpdateInfo();

        lstSellWeapon.Items.clear();
        lstSellShield.Items.clear();
        lstSellGadget.Items.clear();

        Ship ship = Game.CurrentGame().Commander().getShip();
        Equipment[] equipSell;
        int index;

        equipSell = ship.EquipmentByType(EquipmentType.Weapon);
        for (index = 0; index < equipSell.length; index++)
            lstSellWeapon.Items.add(equipSell[index] == null ? (Object) Strings.EquipmentFreeSlot : equipSell[index]);

        equipSell = ship.EquipmentByType(EquipmentType.Shield);
        for (index = 0; index < equipSell.length; index++)
            lstSellShield.Items.add(equipSell[index] == null ? (Object) Strings.EquipmentFreeSlot : equipSell[index]);

        equipSell = ship.EquipmentByType(EquipmentType.Gadget);
        for (index = 0; index < equipSell.length; index++)
            lstSellGadget.Items.add(equipSell[index] == null ? (Object) Strings.EquipmentFreeSlot : equipSell[index]);

        ListBox[] sellBoxes = new ListBox[]{lstSellWeapon, lstSellShield, lstSellGadget};
        Label[] sellLabels = new Label[]{lblSellWeaponNoSlots, lblSellShieldNoSlots, lblSellGadgetNoSlots};
        for (int i = 0; i < sellBoxes.length; i++) {
            boolean entries = (sellBoxes[i].Items.size() > 0);
            sellBoxes[i].setVisible(entries);
            sellLabels[i].setVisible(!entries);
            if (entries)
                sellBoxes[i].setHeight(sellBoxes[i].getItemHeight() * Math.min(sellBoxes[i].Items.size(), 5) + 2);
        }
    }

    // #endregion

    // #region Event Handlers

    private void BuyClick(Object sender, EventArgs e) {
        if (selectedEquipment != null)
            Buy();
    }


    private void SelectedIndexChanged(Object sender, EventArgs e) {
        if (!handlingSelect) {
            handlingSelect = true;

            Object obj = ((ListBox) sender).getSelectedItem();
            DeselectAll();
            ((ListBox) sender).setSelectedItem(obj);

            sellSideSelected = (((ListBox) sender).getName().indexOf("Sell") >= 0);

            if (obj instanceof Equipment)
                selectedEquipment = (Equipment) obj;
            else
                selectedEquipment = null;

            handlingSelect = false;
            UpdateInfo();
        }
    }

    private void SellClick(Object sender, EventArgs e) {
        if (selectedEquipment != null)
            Sell();
    }

    // #endregion
}

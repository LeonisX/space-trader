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
// using System;
// using System.Drawing;
// using System.Collections;
// using System.ComponentModel;
// using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.enums.GadgetType;
import spacetrader.game.*;
import spacetrader.stub.ArrayList;
import spacetrader.util.Util;

import java.util.Arrays;


@SuppressWarnings("unchecked")
public class FormViewShip extends SpaceTraderForm {
    //#region Control Declarations

    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.Label lblTypeLabel;
    private spacetrader.controls.Label lblType;
    private Panel boxSpecialCargo;
    private spacetrader.controls.Label lblSpecialCargo;
    private spacetrader.controls.Label lblEquipLabel;
    private spacetrader.controls.Label lblEquip;
    private Container components = null;

    //#endregion

    //#region Member Declarations

    private Game game = Game.currentGame();
    private Ship ship = Game.currentGame().Commander().getShip();

    //#endregion

    //#region Methods

    public FormViewShip() {
        initializeComponent();

        lblType.setText(ship.Name());
        lblEquipLabel.setText("");
        lblEquip.setText("");

        DisplayEquipment();
        DisplaySpecialCargo();
    }

    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        this.lblTypeLabel = new spacetrader.controls.Label();
        this.lblType = new spacetrader.controls.Label();
        this.btnClose = new spacetrader.controls.Button();
        this.lblEquipLabel = new spacetrader.controls.Label();
        this.lblEquip = new spacetrader.controls.Label();
        this.boxSpecialCargo = new Panel();
        this.lblSpecialCargo = new spacetrader.controls.Label();
        this.boxSpecialCargo.suspendLayout();
        this.suspendLayout();
        //
        // lblTypeLabel
        //
        this.lblTypeLabel.setAutoSize(true);
        this.lblTypeLabel.setFont(FontCollection.bold825);
        this.lblTypeLabel.setLocation(new java.awt.Point(8, 8));
        this.lblTypeLabel.setName("lblTypeLabel");
        this.lblTypeLabel.setSize(new spacetrader.controls.Size(34, 13));
        this.lblTypeLabel.setTabIndex(2);
        this.lblTypeLabel.setText("Type:");
        //
        // lblType
        //
        this.lblType.setLocation(new java.awt.Point(80, 8));
        this.lblType.setName("lblType");
        this.lblType.setSize(new spacetrader.controls.Size(100, 13));
        this.lblType.setTabIndex(4);
        this.lblType.setText("Grasshopper");
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.CANCEL);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new spacetrader.controls.Size(32, 32));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblEquipLabel
        //
        this.lblEquipLabel.setFont(FontCollection.bold825);
        this.lblEquipLabel.setLocation(new java.awt.Point(8, 34));
        this.lblEquipLabel.setName("lblEquipLabel");
        this.lblEquipLabel.setSize(new spacetrader.controls.Size(64, 176));
        this.lblEquipLabel.setTabIndex(43);
        this.lblEquipLabel.setText("Hull:\r\n\r\nEquipment:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\nUnfilled:");
        //
        // lblEquip
        //
        this.lblEquip.setLocation(new java.awt.Point(80, 34));
        this.lblEquip.setName("lblEquip");
        this.lblEquip.setSize(new spacetrader.controls.Size(120, 176));
        this.lblEquip.setTabIndex(44);
        this.lblEquip
                .setText("Hardened\r\n\r\n1 Military Laser\r\n1 Morgan\'s Laser\r\n1 Energy Shield\r\n1 Reflective Shi"
                        + "eld\r\n1 Lightning Shield\r\nNavigating System\r\nAuto-Repair System\r\n10 Extra Cargo Bays\r\nAn Escape Pod\r\n"
                        + "\r\n1 weapon slot\r\n1 gadget slot");
        //
        // boxSpecialCargo
        //
        this.boxSpecialCargo.controls.addAll((new BaseComponent[]{this.lblSpecialCargo}));
        this.boxSpecialCargo.setLocation(new java.awt.Point(192, 8));
        this.boxSpecialCargo.setName("boxSpecialCargo");
        this.boxSpecialCargo.setSize(new spacetrader.controls.Size(200, 204));
        this.boxSpecialCargo.setTabIndex(64);
        this.boxSpecialCargo.setTabStop(false);
        this.boxSpecialCargo.setText("Special Cargo");
        //
        // lblSpecialCargo
        //
        this.lblSpecialCargo.setLocation(new java.awt.Point(8, 16));
        this.lblSpecialCargo.setName("lblSpecialCargo");
        this.lblSpecialCargo.setSize(new spacetrader.controls.Size(190, 176));
        this.lblSpecialCargo.setTabIndex(0);
        this.lblSpecialCargo.setText("No special items.");
        //
        // FormViewShip
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new spacetrader.controls.Size(402, 219));
        this.Controls.addAll(Arrays.asList(this.boxSpecialCargo, this.lblEquip, this.lblEquipLabel, this.btnClose,
                this.lblTypeLabel, this.lblType));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewShip");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Current Ship");
    }

    //#endregion

    private void DisplayEquipment() {
        if (game.getQuestStatusScarab() == SpecialEvent.StatusScarabDone) {
            lblEquipLabel.setText(lblEquipLabel.getText() + ("Hull:" + Strings.newline + Strings.newline));
            lblEquip.setText(lblEquip.getText() + ("Hardened" + Strings.newline + Strings.newline));
        }

        boolean equipPrinted = false;

        for (int i = 0; i < Consts.Weapons.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.Weapons().length; j++) {
                if (ship.Weapons()[j] != null && ship.Weapons()[j].Type() == Consts.Weapons[i].Type())
                    count++;
            }
            if (count > 0) {
                lblEquipLabel.setText(lblEquipLabel.getText()
                        + (equipPrinted ? Strings.newline : "Equipment:" + Strings.newline));
                lblEquip.setText(lblEquip.getText()
                        + (Functions.multiples(count, Consts.Weapons[i].Name()) + Strings.newline));
                equipPrinted = true;
            }
        }

        for (int i = 0; i < Consts.Shields.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.Shields().length; j++) {
                if (ship.Shields()[j] != null && ship.Shields()[j].Type() == Consts.Shields[i].Type())
                    count++;
            }
            if (count > 0) {
                lblEquipLabel.setText(lblEquipLabel.getText()
                        + (equipPrinted ? Strings.newline : "Equipment:" + Strings.newline));
                lblEquip.setText(lblEquip.getText()
                        + (Functions.multiples(count, Consts.Shields[i].Name()) + Strings.newline));
                equipPrinted = true;
            }
        }

        for (int i = 0; i < Consts.Gadgets.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.Gadgets().length; j++) {
                if (ship.Gadgets()[j] != null && ship.Gadgets()[j].Type() == Consts.Gadgets[i].Type())
                    count++;
            }
            if (count > 0) {
                lblEquipLabel.setText(lblEquipLabel.getText()
                        + (equipPrinted ? Strings.newline : "Equipment:" + Strings.newline));

                if (i == GadgetType.ExtraCargoBays.castToInt() || i == GadgetType.HiddenCargoBays.castToInt()) {
                    count *= 5;
                    lblEquip
                            .setText(lblEquip.getText()
                                    + (Functions.formatNumber(count) + Consts.Gadgets[i].Name().substring(1) + Strings.newline));
                } else
                    lblEquip.setText(lblEquip.getText()
                            + (Functions.multiples(count, Consts.Gadgets[i].Name()) + Strings.newline));

                equipPrinted = true;
            }
        }

        if (ship.getEscapePod()) {
            lblEquipLabel.setText(lblEquipLabel.getText()
                    + (equipPrinted ? Strings.newline : "Equipment:" + Strings.newline));
            lblEquip.setText(lblEquip.getText() + ("1 " + Strings.ShipInfoEscapePod + Strings.newline));
            equipPrinted = true;
        }

        if (ship.FreeSlots() > 0) {
            lblEquipLabel.setText(lblEquipLabel.getText() + ((equipPrinted ? Strings.newline : "") + "Unfilled:"));
            lblEquip.setText(lblEquip.getText() + (equipPrinted ? Strings.newline : ""));

            if (ship.FreeSlotsWeapon() > 0)
                lblEquip.setText(lblEquip.getText()
                        + (Functions.multiples(ship.FreeSlotsWeapon(), "weapon slot") + Strings.newline));

            if (ship.FreeSlotsShield() > 0)
                lblEquip.setText(lblEquip.getText()
                        + (Functions.multiples(ship.FreeSlotsShield(), "shield slot") + Strings.newline));

            if (ship.FreeSlotsGadget() > 0)
                lblEquip.setText(lblEquip.getText()
                        + (Functions.multiples(ship.FreeSlotsGadget(), "gadget slot") + Strings.newline));
        }
    }

    private void DisplaySpecialCargo() {
        ArrayList specialCargo = new ArrayList(12);

        if (ship.getTribbles() > 0) {
            if (ship.getTribbles() == Consts.MaxTribbles)
                specialCargo.add(Strings.SpecialCargoTribblesInfest);
            else
                specialCargo.add(Functions.multiples(ship.getTribbles(), Strings.SpecialCargoTribblesCute) + ".");
        }

        if (game.getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit)
            specialCargo.add(Strings.SpecialCargoJapori);

        if (ship.ArtifactOnBoard())
            specialCargo.add(Strings.SpecialCargoArtifact);

        if (game.getQuestStatusJarek() == SpecialEvent.StatusJarekDone)
            specialCargo.add(Strings.SpecialCargoJarek);

        if (ship.ReactorOnBoard()) {
            specialCargo.add(Strings.SpecialCargoReactor);
            specialCargo.add(Functions.multiples(10 - ((game.getQuestStatusReactor() - 1) / 2), "bay")
                    + Strings.SpecialCargoReactorBays);
        }

        if (ship.SculptureOnBoard())
            specialCargo.add(Strings.SpecialCargoSculpture);

        if (game.getCanSuperWarp())
            specialCargo.add(Strings.SpecialCargoExperiment);

        lblSpecialCargo.setText(specialCargo.size() == 0 ? Strings.SpecialCargoNone : Util.StringsJoin(Strings.newline
                + Strings.newline, Functions.ArrayListtoStringArray(specialCargo)));
    }

    //#endregion
}

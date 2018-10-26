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

import spacetrader.controls.BaseComponent;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.GadgetType;
import spacetrader.gui.debug.Launcher;
import spacetrader.stub.ArrayList;
import spacetrader.util.ReflectionUtils;

public class FormViewShip extends SpaceTraderForm {

    private Game game = Game.getCurrentGame();
    private Ship ship = game.getCommander().getShip();

    private Button closeButton = new Button();
    private Label typeLabel = new Label();
    private Label typeLabelValue = new Label();
    private Panel specialCargoPanel = new Panel();
    private Label specialCargoLabelValue = new Label();
    private Label equipTitleLabelValue = new Label();
    private Label equipLabelValue = new Label();

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Launcher.runForm(new FormViewShip());
    }

    public FormViewShip() {
        initializeComponent();

        equipTitleLabelValue.setText("");
        equipLabelValue.setText("");

        displayEquipment();
        displaySpecialCargo();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewShip");
        setText("Current Ship");
        setAutoScaleBaseSize(5, 13);
        setClientSize(402, 219);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        typeLabel.setAutoSize(true);
        typeLabel.setFont(FontCollection.bold825);
        typeLabel.setLocation(8, 8);
        typeLabel.setSize(34, 13);
        typeLabel.setTabIndex(2);
        typeLabel.setText("Type:");

        typeLabelValue.setLocation(80, 8);
        typeLabelValue.setSize(100, 13);
        typeLabelValue.setTabIndex(4);
        typeLabelValue.setText(ship.getName());

        equipTitleLabelValue.setFont(FontCollection.bold825);
        equipTitleLabelValue.setLocation(8, 34);
        equipTitleLabelValue.setSize(64, 176);
        equipTitleLabelValue.setTabIndex(43);
        //equipTitleLabelValue.setText("Hull:\r\n\r\nEquipment:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\nUnfilled:");

        equipLabelValue.setLocation(80, 34);
        equipLabelValue.setSize(120, 176);
        equipLabelValue.setTabIndex(44);
        /*equipLabelValue
                .setText("Hardened\r\n\r\n1 Military Laser\r\n1 Morgan\'s Laser\r\n1 Energy Shield\r\n1 Reflective Shi"
                        + "eld\r\n1 Lightning Shield\r\nNavigating System\r\nAuto-Repair System\r\n10 Extra Cargo Bays\r\nAn Escape Pod\r\n"
                        + "\r\n1 weapon slot\r\n1 gadget slot");*/

        specialCargoPanel.setLocation(192, 8);
        specialCargoPanel.setSize(200, 204);
        specialCargoPanel.setTabIndex(64);
        specialCargoPanel.setTabStop(false);
        specialCargoPanel.setText("Special Cargo");

        specialCargoPanel.getControls().addAll((new BaseComponent[]{specialCargoLabelValue}));

        specialCargoLabelValue.setLocation(8, 16);
        specialCargoLabelValue.setSize(190, 176);
        specialCargoLabelValue.setTabIndex(0);
        specialCargoLabelValue.setText("No special items.");

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(typeLabel, typeLabelValue, equipTitleLabelValue, equipLabelValue, specialCargoPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void displayEquipment() {
        if (game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DONE) {
            equipTitleLabelValue.setText(equipTitleLabelValue.getText() + (Strings.ShipHull + Strings.newline + Strings.newline));
            equipLabelValue.setText(equipLabelValue.getText() + (Strings.ShipHardened + Strings.newline + Strings.newline));
        }

        boolean equipPrinted = false;

        for (int i = 0; i < Consts.Weapons.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.getWeapons().length; j++) {
                if (ship.getWeapons()[j] != null && ship.getWeapons()[j].getType() == Consts.Weapons[i].getType()) {
                    count++;
                }
            }
            if (count > 0) {
                equipTitleLabelValue.setText(equipTitleLabelValue.getText()
                        + (equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline));
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.multiples(count, Consts.Weapons[i].getName()) + Strings.newline));
                equipPrinted = true;
            }
        }

        for (int i = 0; i < Consts.Shields.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.getShields().length; j++) {
                if (ship.getShields()[j] != null && ship.getShields()[j].getType() == Consts.Shields[i].getType()) {
                    count++;
                }
            }
            if (count > 0) {
                equipTitleLabelValue.setText(equipTitleLabelValue.getText()
                        + (equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline));
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.multiples(count, Consts.Shields[i].getName()) + Strings.newline));
                equipPrinted = true;
            }
        }

        for (int i = 0; i < Consts.Gadgets.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.getGadgets().length; j++) {
                if (ship.getGadgets()[j] != null && ship.getGadgets()[j].getType() == Consts.Gadgets[i].getType()) {
                    count++;
                }
            }
            if (count > 0) {
                equipTitleLabelValue.setText(equipTitleLabelValue.getText()
                        + (equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline));

                if (i == GadgetType.EXTRA_CARGO_BAYS.castToInt() || i == GadgetType.HIDDEN_CARGO_BAYS.castToInt()) {
                    count *= 5;
                    equipLabelValue.setText(equipLabelValue.getText()
                            + (Functions.formatNumber(count) + Consts.Gadgets[i].getName().substring(1) + Strings.newline));
                } else {
                    equipLabelValue.setText(equipLabelValue.getText()
                            + (Functions.multiples(count, Consts.Gadgets[i].getName()) + Strings.newline));
                }

                equipPrinted = true;
            }
        }

        if (ship.getEscapePod()) {
            equipTitleLabelValue.setText(equipTitleLabelValue.getText()
                    + (equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline));
            equipLabelValue.setText(equipLabelValue.getText() + ("1 " + Strings.ShipInfoEscapePod + Strings.newline));
            equipPrinted = true;
        }

        if (ship.getFreeSlots() > 0) {
            equipTitleLabelValue.setText(equipTitleLabelValue.getText() + ((equipPrinted ? Strings.newline : "") + Strings.ShipUnfilled));
            equipLabelValue.setText(equipLabelValue.getText() + (equipPrinted ? Strings.newline : ""));

            if (ship.getFreeWeaponSlots() > 0) {
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.multiples(ship.getFreeWeaponSlots(), Strings.ShipWeaponSlot) + Strings.newline));
            }

            if (ship.getFreeShieldSlots() > 0) {
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.multiples(ship.getFreeShieldSlots(), Strings.ShipShiedSlot) + Strings.newline));
            }

            if (ship.getFreeGadgetSlots() > 0) {
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.multiples(ship.getFreeGadgetSlots(), Strings.ShipGadgetSlot) + Strings.newline));
            }
        }
    }

    private void displaySpecialCargo() {
        ArrayList<String> specialCargo = new ArrayList<>(12);

        if (ship.getTribbles() > 0) {
            if (ship.getTribbles() == Consts.MaxTribbles) {
                specialCargo.add(Strings.SpecialCargoTribblesInfest);
            } else {
                specialCargo.add(Functions.multiples(ship.getTribbles(), Strings.SpecialCargoTribblesCute) + ".");
            }
        }

        if (game.getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT) {
            specialCargo.add(Strings.SpecialCargoJapori);
        }

        if (ship.isArtifactOnBoard()) {
            specialCargo.add(Strings.SpecialCargoArtifact);
        }

        if (game.getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_DONE) {
            specialCargo.add(Strings.SpecialCargoJarek);
        }

        if (ship.isReactorOnBoard()) {
            specialCargo.add(Strings.SpecialCargoReactor);
            specialCargo.add(Functions.multiples(10 - ((game.getQuestStatusReactor() - 1) / 2), Strings.ShipBay)
                    + Strings.SpecialCargoReactorBays);
        }

        if (ship.isSculptureOnBoard()) {
            specialCargo.add(Strings.SpecialCargoSculpture);
        }

        if (game.getCanSuperWarp()) {
            specialCargo.add(Strings.SpecialCargoExperiment);
        }

        specialCargoLabelValue.setText(specialCargo.size() == 0 ? Strings.SpecialCargoNone
                : String.join(Strings.newline + Strings.newline, specialCargo));
    }
}

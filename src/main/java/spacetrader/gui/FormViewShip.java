package spacetrader.gui;

import spacetrader.controls.BaseComponent;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.GadgetType;
import spacetrader.game.quest.enums.EventName;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.util.Arrays;
import java.util.List;

import static spacetrader.game.quest.enums.EventName.ON_DISPLAY_SHIP_EQUIPMENT;

public class FormViewShip extends SpaceTraderForm {

    private Game game = Game.getCurrentGame();
    private Ship ship = game.getShip();

    private Button closeButton = new Button();
    private Label typeLabel = new Label();
    private Label typeLabelValue = new Label();
    private Panel specialCargoPanel = new Panel();
    private Label specialCargoLabelValue = new Label();
    private Label equipTitleLabelValue = new Label();
    private Label equipLabelValue = new Label();

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
        setClientSize(455, 249);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        typeLabel.setAutoSize(true);
        typeLabel.setFont(FontCollection.bold825);
        typeLabel.setLocation(8, 8);
        //typeLabel.setSize(34, 13);
        typeLabel.setText("Type:");

        typeLabelValue.setAutoSize(true);
        typeLabelValue.setLocation(90, 8);
        typeLabelValue.setSize(120, 13);
        typeLabelValue.setText(ship.getName());

        equipTitleLabelValue.setFont(FontCollection.bold825);
        equipTitleLabelValue.setLocation(8, 34);
        equipTitleLabelValue.setSize(84, 176);
        //equipTitleLabelValue.setText("Hull:\r\n\r\nEquipment:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\nUnfilled:");

        equipLabelValue.setLocation(90, 34);
        equipLabelValue.setSize(140, 176);
        /*equipLabelValue
                .setText("Hardened\r\n\r\n1 Military Laser\r\n1 Morgan\'s Laser\r\n1 Energy Shield\r\n1 Reflective Shi"
                        + "eld\r\n1 Lightning Shield\r\nNavigating System\r\nAuto-Repair System\r\n10 Extra Cargo Bays\r\nAn Escape Pod\r\n"
                        + "\r\n1 weapon slot\r\n1 gadget slot");*/

        specialCargoPanel.setLocation(242, 8);
        specialCargoPanel.setSize(210, 204);
        specialCargoPanel.setTabStop(false);
        specialCargoPanel.setText("Special Cargo");

        specialCargoPanel.getControls().addAll((new BaseComponent[]{specialCargoLabelValue}));

        specialCargoLabelValue.setLocation(8, 16);
        specialCargoLabelValue.setSize(190, 176);
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
        List<StringBuilder> equipPair = Arrays.asList(new StringBuilder(), new StringBuilder()); // equipTitle, equip

        game.getQuestSystem().fireEvent(ON_DISPLAY_SHIP_EQUIPMENT, equipPair);

        boolean equipPrinted = false;

        for (int i = 0; i < Consts.Weapons.length; i++) {
            int count = 0;
            for (int j = 0; j < ship.getWeapons().length; j++) {
                if (ship.getWeapons()[j] != null && ship.getWeapons()[j].getType() == Consts.Weapons[i].getType()) {
                    count++;
                }
            }
            if (count > 0) {
                equipPair.get(0).append(equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline);
                equipPair.get(1).append(Functions.plural(count, Consts.Weapons[i].getName())).append(Strings.newline);
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
                equipPair.get(0).append(equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline);
                equipPair.get(1).append(Functions.plural(count, Consts.Shields[i].getName())).append(Strings.newline);
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
                equipPair.get(0).append(equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline);

                if (i == GadgetType.EXTRA_CARGO_BAYS.castToInt() || i == GadgetType.HIDDEN_CARGO_BAYS.castToInt()) {
                    count *= 5;
                    equipPair.get(1).append(Consts.Gadgets[i].getName().replace("5", Functions.formatNumber(count))).append(Strings.newline);
                } else {
                    equipPair.get(1).append(Functions.plural(count, Consts.Gadgets[i].getName())).append(Strings.newline);
                }

                equipPrinted = true;
            }
        }

        if (ship.getEscapePod()) {
            equipPair.get(0).append(equipPrinted ? Strings.newline : Strings.ShipEquipment + Strings.newline);
            equipPair.get(1).append("1 ").append(Strings.ShipInfoEscapePod).append(Strings.newline);
            equipPrinted = true;
        }

        if (ship.getFreeSlots() > 0) {
            equipPair.get(0).append(equipPrinted ? Strings.newline : "").append(Strings.ShipUnfilled);
            equipPair.get(1).append(equipPrinted ? Strings.newline : "");

            if (ship.getFreeWeaponSlots() > 0) {
                equipPair.get(1).append(Functions.plural(ship.getFreeWeaponSlots(), Strings.ShipWeaponSlot)).append(Strings.newline);
            }

            if (ship.getFreeShieldSlots() > 0) {
                equipPair.get(1).append(Functions.plural(ship.getFreeShieldSlots(), Strings.ShipShiedSlot)).append(Strings.newline);
            }

            if (ship.getFreeGadgetSlots() > 0) {
                equipPair.get(1).append(Functions.plural(ship.getFreeGadgetSlots(), Strings.ShipGadgetSlot)).append(Strings.newline);
            }
        }

        equipTitleLabelValue.setText(equipPair.get(0).toString());
        equipLabelValue.setText(equipPair.get(1).toString());

        if (equipTitleLabelValue.getText().endsWith("<BR>")) {
            equipTitleLabelValue.setText(equipTitleLabelValue.getText().substring(0, equipTitleLabelValue.getText().length() - 4));
        }
    }

    private void displaySpecialCargo() {
        List<String> specialCargo = new ArrayList<>();

        game.getQuestSystem().fireEvent(EventName.ON_DISPLAY_SPECIAL_CARGO, specialCargo);

        specialCargoLabelValue.setText(specialCargo.size() == 0 ? Strings.SpecialCargoNone
                : String.join(Strings.newline + Strings.newline, specialCargo));
    }
}

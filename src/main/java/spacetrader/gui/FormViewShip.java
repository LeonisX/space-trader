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
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;
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
        setClientSize(445, 219);
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
                        + (Functions.plural(count, Consts.Weapons[i].getName()) + Strings.newline));
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
                        + (Functions.plural(count, Consts.Shields[i].getName()) + Strings.newline));
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
                            + (Functions.singular(Consts.Gadgets[i].getName()).replace("5", Functions.formatNumber(count)) + Strings.newline));
                } else {
                    equipLabelValue.setText(equipLabelValue.getText()
                            + (Functions.plural(count, Consts.Gadgets[i].getName()) + Strings.newline));
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
                        + (Functions.plural(ship.getFreeWeaponSlots(), Strings.ShipWeaponSlot) + Strings.newline));
            }

            if (ship.getFreeShieldSlots() > 0) {
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.plural(ship.getFreeShieldSlots(), Strings.ShipShiedSlot) + Strings.newline));
            }

            if (ship.getFreeGadgetSlots() > 0) {
                equipLabelValue.setText(equipLabelValue.getText()
                        + (Functions.plural(ship.getFreeGadgetSlots(), Strings.ShipGadgetSlot) + Strings.newline));
            }
        }
    }

    private void displaySpecialCargo() {
        ArrayList<String> specialCargo = new ArrayList<>(12);

        if (ship.getTribbles() > 0) {
            if (ship.getTribbles() == Consts.MaxTribbles) {
                specialCargo.add(Strings.SpecialCargoTribblesInfest);
            } else if (ship.getTribbles() == 1){
                specialCargo.add(Strings.SpecialCargoTribbleCute);
            } else {
                specialCargo.add(Strings.SpecialCargoTribblesCute);
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
            specialCargo.add(Functions.plural(10 - ((game.getQuestStatusReactor() - 1) / 2), Strings.ShipBay)
                    + " " + Strings.SpecialCargoReactorBays);
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

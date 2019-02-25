package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.*;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.GadgetType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

public class FormEquipment extends SpaceTraderForm {

    private final String FREE_SLOT = " - " + Strings.EquipmentFreeSlot + " - ";

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

    private ListBox[] buyListBoxes = new ListBox[]{buyWeaponsListBox, buyShieldsListBox, buyGadgetsListBox};
    private Label[] buyNoneLabels = new Label[]{buyWeaponNoneLabel, buyShieldsNoneLabel, buyGadgetsNoneLabel};

    private ListBox[] sellBoxes = new ListBox[]{sellWeaponsListBox, sellShieldsListBox, sellGadgetsListBox};
    private Label[] sellLabels = new Label[]{sellWeaponsNoSlotsLabel, sellShieldsNoSlotsLabel, sellGadgetsNoSlotsLabel};

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

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formEquipment");
        setText("Buy/Sell Equipment");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(572, 341);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        currentInventoryPanel.setLocation(4, 2);
        currentInventoryPanel.setSize(160, 320);
        currentInventoryPanel.setTabStop(false);
        currentInventoryPanel.setText("Current Inventory");

        sellWeaponsLabel.setAutoSize(true);
        sellWeaponsLabel.setLocation(11, 25);
        //sellWeaponsLabel.setSize(52, 16);
        sellWeaponsLabel.setText("Weapons");

        sellWeaponsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        sellWeaponsListBox.setLocation(11, 41);
        sellWeaponsListBox.setSize(138, 67);
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

        sellWeaponsNoSlotsLabel.setAutoSize(true);
        sellWeaponsNoSlotsLabel.setLocation(27, 41);
        //sellWeaponsNoSlotsLabel.setSize(104, 16);
        sellWeaponsNoSlotsLabel.setText(Strings.EquipmentNoSlots);
        sellWeaponsNoSlotsLabel.setVisible(false);

        sellShieldsLabel.setAutoSize(true);
        sellShieldsLabel.setLocation(11, 121);
        //sellShieldsLabel.setSize(41, 16);
        sellShieldsLabel.setText("Shields");

        sellShieldsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        sellShieldsListBox.setLocation(11, 137);
        sellShieldsListBox.setSize(138, 67);
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

        sellShieldsNoSlotsLabel.setAutoSize(true);
        sellShieldsNoSlotsLabel.setLocation(27, 137);
        //sellShieldsNoSlotsLabel.setSize(104, 16);
        sellShieldsNoSlotsLabel.setText(Strings.EquipmentNoSlots);
        sellShieldsNoSlotsLabel.setVisible(false);

        sellGadgetsLabel.setAutoSize(true);
        sellGadgetsLabel.setLocation(11, 217);
        //sellGadgetsLabel.setSize(47, 16);
        sellGadgetsLabel.setText("Gadgets");

        sellGadgetsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        sellGadgetsListBox.setLocation(11, 233);
        sellGadgetsListBox.setSize(138, 67);
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

        sellGadgetsNoSlotsLabel.setAutoSize(true);
        sellGadgetsNoSlotsLabel.setLocation(27, 233);
        //sellGadgetsNoSlotsLabel.setSize(104, 16);
        sellGadgetsNoSlotsLabel.setText(Strings.EquipmentNoSlots);
        sellGadgetsNoSlotsLabel.setVisible(false);

        currentInventoryPanel.getControls().addAll(sellWeaponsLabel, sellWeaponsListBox, sellWeaponsNoSlotsLabel);
        currentInventoryPanel.getControls().addAll(sellShieldsLabel, sellShieldsListBox, sellShieldsNoSlotsLabel);
        currentInventoryPanel.getControls().addAll(sellGadgetsLabel, sellGadgetsListBox, sellGadgetsNoSlotsLabel);

        equipmentForSalePanel.setLocation(170, 2);
        equipmentForSalePanel.setSize(160, 320);
        equipmentForSalePanel.setTabStop(false);
        equipmentForSalePanel.setText("Equipment For Sale");

        buyWeaponsLabel.setAutoSize(true);
        buyWeaponsLabel.setLocation(11, 25);
        //buyWeaponsLabel.setSize(52, 16);
        buyWeaponsLabel.setText("Weapons");

        buyWeaponsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        buyWeaponsListBox.setLocation(11, 41);
        buyWeaponsListBox.setSize(138, 67);
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

        buyWeaponNoneLabel.setAutoSize(true);
        buyWeaponNoneLabel.setLocation(27, 41);
        //buyWeaponNoneLabel.setSize(104, 16);
        buyWeaponNoneLabel.setText(Strings.EquipmentNoneForSale);
        buyWeaponNoneLabel.setVisible(false);

        buyShieldsLabel.setAutoSize(true);
        buyShieldsLabel.setLocation(11, 121);
        //buyShieldsLabel.setSize(41, 16);
        buyShieldsLabel.setText("Shields");

        buyShieldsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        buyShieldsListBox.setLocation(11, 137);
        buyShieldsListBox.setSize(138, 67);
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

        buyShieldsNoneLabel.setAutoSize(true);
        buyShieldsNoneLabel.setLocation(27, 137);
        //buyShieldsNoneLabel.setSize(104, 16);
        buyShieldsNoneLabel.setText(Strings.EquipmentNoneForSale);
        buyShieldsNoneLabel.setVisible(false);

        buyGadgetsLabel.setAutoSize(true);
        buyGadgetsLabel.setLocation(11, 217);
        //buyGadgetsLabel.setSize(47, 16);
        buyGadgetsLabel.setText("Gadgets");

        buyGadgetsListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        buyGadgetsListBox.setLocation(11, 233);
        buyGadgetsListBox.setSize(138, 67);
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

        buyGadgetsNoneLabel.setAutoSize(true);
        buyGadgetsNoneLabel.setLocation(27, 233);
        //buyGadgetsNoneLabel.setSize(104, 16);
        buyGadgetsNoneLabel.setText(Strings.EquipmentNoneForSale);
        buyGadgetsNoneLabel.setVisible(false);

        equipmentForSalePanel.getControls().addAll(buyWeaponsLabel, buyWeaponsListBox, buyWeaponNoneLabel);
        equipmentForSalePanel.getControls().addAll(buyShieldsLabel, buyShieldsListBox, buyShieldsNoneLabel);
        equipmentForSalePanel.getControls().addAll(buyGadgetsLabel, buyGadgetsListBox, buyGadgetsNoneLabel);

        equipmentInformationPanel.setLocation(336, 2);
        equipmentInformationPanel.setSize(233, 320);
        equipmentInformationPanel.setTabStop(false);
        equipmentInformationPanel.setText("Equipment Information");

        equipmentPictureBox.setBackground(Color.WHITE);
        equipmentPictureBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        equipmentPictureBox.setLocation(81, 20);
        equipmentPictureBox.setSize(66, 54);
        equipmentPictureBox.setTabStop(false);
        equipmentPictureBox.setVisible(false);

        nameLabel.setAutoSize(true);
        nameLabel.setFont(FontCollection.bold825);
        nameLabel.setLocation(8, 85);
        //nameLabel.setSize(39, 16);
        nameLabel.setText("Name:");

        nameLabelValue.setAutoSize(true);
        nameLabelValue.setLocation(90, 84);
        //nameLabelValue.setSize(116, 16);
        //nameLabelValue.setText("Auto-Repair System");

        typeLabel.setAutoSize(true);
        typeLabel.setFont(FontCollection.bold825);
        typeLabel.setLocation(8, 101);
        //typeLabel.setSize(34, 16);
        typeLabel.setText("Type:");

        typeLabelValue.setAutoSize(true);
        typeLabelValue.setLocation(90, 100);
        //typeLabelValue.setSize(116, 16);
        //typeLabelValue.setText("Weapon");

        buyPriceLabel.setAutoSize(true);
        buyPriceLabel.setFont(FontCollection.bold825);
        buyPriceLabel.setLocation(8, 117);
        //buyPriceLabel.setSize(58, 16);
        buyPriceLabel.setText("Buy Price:");

        buyPriceLabelValue.setAutoSize(true);
        buyPriceLabelValue.setLocation(90, 116);
        //buyPriceLabelValue.setSize(116, 16);
        //buyPriceLabelValue.setText("888,888 cr.");

        sellPriceLabel.setAutoSize(true);
        sellPriceLabel.setFont(FontCollection.bold825);
        sellPriceLabel.setLocation(8, 133);
        //sellPriceLabel.setSize(58, 16);
        sellPriceLabel.setText("Sell Price:");

        sellPriceLabelValue.setAutoSize(true);
        sellPriceLabelValue.setLocation(90, 132);
        //sellPriceLabelValue.setSize(116, 16);
        //sellPriceLabelValue.setText("888,888 cr.");

        powerLabel.setAutoSize(true);
        powerLabel.setFont(FontCollection.bold825);
        powerLabel.setLocation(8, 149);
        //powerLabel.setSize(41, 16);
        powerLabel.setText("Power:");

        powerLabelValue.setAutoSize(true);
        powerLabelValue.setLocation(90, 148);
        //powerLabelValue.setSize(116, 16);
        //powerLabelValue.setText("888");

        chargeLabel.setAutoSize(true);
        chargeLabel.setFont(FontCollection.bold825);
        chargeLabel.setLocation(8, 165);
        //chargeLabel.setSize(46, 16);
        chargeLabel.setText("Charge:");

        chargeLabelValue.setAutoSize(true);
        chargeLabelValue.setLocation(90, 164);
        //chargeLabelValue.setSize(116, 16);
        //chargeLabelValue.setText("888");

        descriptionLabel.setLocation(8, 183);
        descriptionLabel.setSize(220, 100);

        buyButton.setAutoWidth(true);
        buyButton.setControlBinding(ControlBinding.CENTER);
        buyButton.setLocation(46, 287);
        buyButton.setSize(58, 22);
        buyButton.setTabIndex(7);
        buyButton.setText("Buy");
        buyButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                buyButtonClick();
            }
        });

        sellButton.setAutoWidth(true);
        sellButton.setControlBinding(ControlBinding.CENTER);
        sellButton.setLocation(117, 287);
        sellButton.setSize(58, 22);
        sellButton.setTabIndex(8);
        sellButton.setText("Sell");
        sellButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                sellButtonClick();
            }
        });

        equipmentInformationPanel.getControls().addAll(equipmentPictureBox, nameLabel, nameLabelValue, typeLabel,
                typeLabelValue, buyPriceLabel, buyPriceLabelValue, sellPriceLabel, sellPriceLabelValue, powerLabel,
                powerLabelValue, chargeLabel, chargeLabelValue, descriptionLabel, sellButton, buyButton);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(currentInventoryPanel, equipmentForSalePanel, equipmentInformationPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
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

            String buyPrice = (selectedEquipment.getBuyPrice() == 0)
                    ? Strings.EquipmentNotForSale
                    : Functions.formatMoney(selectedEquipment.getBuyPrice());
            buyPriceLabelValue.setText(buyPrice);
            sellPriceLabelValue.setText(Functions.formatMoney(selectedEquipment.getSellPrice()));
            powerLabelValue.setText(power);
            chargeLabelValue.setText(charge);
            equipmentPictureBox.setImage(selectedEquipment.getImage());
            buyButton.setVisible(!sellSideSelected && (selectedEquipment.getBuyPrice() > 0));
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
                    Functions.plural(selectedEquipment.getPrice(), Strings.MoneyUnit)) == DialogResult.YES) {
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
            if (equipmentToBuy.getBuyPrice() > 0) {
                switch (equipmentToBuy.getEquipmentType()) {
                    case WEAPON:
                        buyWeaponsListBox.getItems().addElement(equipmentToBuy);
                        break;
                    case SHIELD:
                        buyShieldsListBox.getItems().addElement(equipmentToBuy);
                        break;
                    case GADGET:
                        buyGadgetsListBox.getItems().addElement(equipmentToBuy);
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

        Ship ship = game.getShip();

        for (Equipment equipment : ship.getEquipmentsByType(EquipmentType.WEAPON)) {
            sellWeaponsListBox.getItems().addElement(equipment == null ? FREE_SLOT : equipment);
        }

        for (Equipment equipment : ship.getEquipmentsByType(EquipmentType.SHIELD)) {
            sellShieldsListBox.getItems().addElement(equipment == null ? FREE_SLOT : equipment);
        }

        for (Equipment equipment : ship.getEquipmentsByType(EquipmentType.GADGET)) {
            sellGadgetsListBox.getItems().addElement(equipment == null ? FREE_SLOT : equipment);
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

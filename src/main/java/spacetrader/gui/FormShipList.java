package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.*;
import spacetrader.game.*;
import spacetrader.game.enums.ShipType;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

import static spacetrader.game.quest.enums.EventName.ON_FORM_SHIP_LIST_SHOW;

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
        info(game.getShip().getType().castToInt());

        game.getQuestSystem().fireEvent(ON_FORM_SHIP_LIST_SHOW);
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formShipList");
        setText("Ship List");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(448, 270);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        buyButton0.setLocation(20, 8);
        buyButton0.setTabIndex(1);

        nameLabel0.setLocation(60, 12);

        infoButton0.setLocation(123, 8);
        infoButton0.setTabIndex(11);

        priceLabelValue0.setLocation(165, 12);

        priceLabelValue1.setLocation(165, 36);

        infoButton1.setLocation(123, 32);
        infoButton1.setTabIndex(12);

        nameLabel1.setLocation(60, 36);

        buyButton1.setLocation(20, 32);
        buyButton1.setTabIndex(2);

        priceLabelValue2.setLocation(165, 60);

        infoButton2.setLocation(123, 56);
        infoButton2.setTabIndex(13);

        nameLabel2.setLocation(60, 60);

        buyButton2.setLocation(20, 56);
        buyButton2.setTabIndex(3);

        priceLabelValue3.setLocation(165, 84);

        infoButton3.setLocation(123, 80);
        infoButton3.setTabIndex(14);

        nameLabel3.setLocation(60, 84);

        buyButton3.setLocation(20, 80);
        buyButton3.setTabIndex(4);

        priceLabelValue4.setLocation(165, 108);

        infoButton4.setLocation(123, 104);
        infoButton4.setTabIndex(15);

        nameLabel4.setLocation(60, 108);

        buyButton4.setLocation(20, 104);
        buyButton4.setTabIndex(5);

        priceLabelValue5.setLocation(165, 132);

        infoButton5.setLocation(123, 128);
        infoButton5.setTabIndex(16);

        nameLabel5.setLocation(60, 132);

        buyButton5.setLocation(20, 128);
        buyButton5.setTabIndex(6);

        priceLabelValue6.setLocation(165, 156);

        infoButton6.setLocation(123, 152);
        infoButton6.setTabIndex(17);

        nameLabel6.setLocation(60, 156);

        buyButton6.setLocation(20, 152);
        buyButton6.setTabIndex(7);

        priceLabelValue7.setLocation(165, 180);

        infoButton7.setLocation(123, 176);
        infoButton7.setTabIndex(18);

        nameLabel7.setLocation(60, 180);

        buyButton7.setLocation(20, 176);
        buyButton7.setTabIndex(8);

        priceLabelValue8.setLocation(165, 204);

        infoButton8.setLocation(123, 200);
        infoButton8.setTabIndex(19);

        nameLabel8.setLocation(60, 204);

        buyButton8.setLocation(20, 200);
        buyButton8.setTabIndex(9);

        priceLabelValue9.setLocation(165, 228);
        priceLabelValue9.setTabIndex(72);

        infoButton9.setLocation(123, 224);
        infoButton9.setTabIndex(20);

        nameLabel9.setLocation(60, 228);

        buyButton9.setLocation(20, 224);
        buyButton9.setTabIndex(10);

        Arrays.stream(priceLabels).forEach(label -> {
            label.setAutoSize(true);
            //label.setSize(64, 13);
            //label.setText("-888,888 cr.");
        });

        for (int i = 0; i < nameLabels.length; i++) {
            nameLabels[i].setAutoSize(true);
            //nameLabels[i].setSize(70, 13);
            nameLabels[i].setText(Strings.ShipNames[i]);
        }

        Arrays.stream(buyButtons).forEach(button -> {
            button.setAutoWidth(true);
            button.setControlBinding(ControlBinding.RIGHT);
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
            button.setAutoWidth(true);
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

        shipInfoPanel.setLocation(237, 2);
        shipInfoPanel.setSize(205, 247);
        shipInfoPanel.setTabStop(false);
        shipInfoPanel.setText("Ship Information");

        shipPictureBox.setBackground(Color.WHITE);
        shipPictureBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipPictureBox.setLocation(69, 25);
        shipPictureBox.setSize(66, 54);
        shipPictureBox.setTabStop(false);

        nameLabel.setAutoSize(true);
        nameLabel.setFont(FontCollection.bold825);
        nameLabel.setLocation(8, 97);
        //nameLabel.setSize(39, 13);
        nameLabel.setText("Name:");

        nameLabelValue.setAutoSize(true);
        nameLabelValue.setLocation(128, 96);
        //nameLabelValue.setSize(100, 13);
        //nameLabelValue.setText("Grasshopper");

        sizeLabel.setAutoSize(true);
        sizeLabel.setFont(FontCollection.bold825);
        sizeLabel.setLocation(8, 113);
        //sizeLabel.setSize(31, 13);
        sizeLabel.setText("Size:");

        sizeLabelValue.setAutoSize(true);
        sizeLabelValue.setLocation(128, 112);
        //sizeLabelValue.setSize(45, 13);
        //sizeLabelValue.setText("Medium");

        baysLabel.setAutoSize(true);
        baysLabel.setFont(FontCollection.bold825);
        baysLabel.setLocation(8, 129);
        //baysLabel.setSize(69, 13);
        baysLabel.setText("Cargo Bays:");

        baysLabelValue.setAutoSize(true);
        baysLabelValue.setLocation(128, 128);
        //baysLabelValue.setSize(17, 13);
        //baysLabelValue.setText("88");

        rangeLabel.setAutoSize(true);
        rangeLabel.setFont(FontCollection.bold825);
        rangeLabel.setLocation(8, 145);
        //rangeLabel.setSize(42, 13);
        rangeLabel.setText("Range:");

        rangeLabelValue.setAutoSize(true);
        rangeLabelValue.setLocation(128, 144);
        //rangeLabelValue.setSize(59, 13);
        //rangeLabelValue.setText("88 parsecs");

        hullLabel.setAutoSize(true);
        hullLabel.setFont(FontCollection.bold825);
        hullLabel.setLocation(8, 161);
        //hullLabel.setSize(73, 13);
        hullLabel.setText("Hull Strength");

        hullLabelValue.setAutoSize(true);
        hullLabelValue.setLocation(128, 160);
        //hullLabelValue.setSize(23, 13);
        //hullLabelValue.setText("888");

        weaponLabel.setAutoSize(true);
        weaponLabel.setFont(FontCollection.bold825);
        weaponLabel.setLocation(8, 177);
        //weaponLabel.setSize(81, 13);
        weaponLabel.setText("Weapon Slots:");

        weaponLabelValue.setAutoSize(true);
        weaponLabelValue.setLocation(128, 176);
        //weaponLabelValue.setSize(10, 13);
        //weaponLabelValue.setText("8");

        shieldLabel.setAutoSize(true);
        shieldLabel.setFont(FontCollection.bold825);
        shieldLabel.setLocation(8, 193);
        //shieldLabel.setSize(70, 13);
        shieldLabel.setText("Shield Slots:");

        shieldLabelValue.setAutoSize(true);
        shieldLabelValue.setLocation(128, 192);
        //shieldLabelValue.setSize(10, 13);
        //shieldLabelValue.setText("8");

        gadgetLabel.setAutoSize(true);
        gadgetLabel.setFont(FontCollection.bold825);
        gadgetLabel.setLocation(8, 209);
        //gadgetLabel.setSize(76, 13);
        gadgetLabel.setText("Gadget Slots:");

        gadgetLabelValue.setAutoSize(true);
        gadgetLabelValue.setLocation(128, 208);
        //gadgetLabelValue.setSize(10, 13);
        //gadgetLabelValue.setText("8");

        crewLabel.setAutoSize(true);
        crewLabel.setFont(FontCollection.bold825);
        crewLabel.setLocation(8, 225);
        //crewLabel.setSize(84, 13);
        crewLabel.setText("Crew Quarters:");

        crewLabelValue.setAutoSize(true);
        crewLabelValue.setLocation(128, 224);
        //crewLabelValue.setSize(10, 13);
        //crewLabelValue.setText("8");

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
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
            updateAll();
            game.getParentWindow().updateAll();
        }
    }

    private void info(int id) {
        ShipSpec spec = (id == ShipType.CUSTOM.castToInt()) ? game.getShip() : Consts.ShipSpecs[id];

        shipPictureBox.setImage(spec.getImage());
        nameLabelValue.setText(spec.getName());
        sizeLabelValue.setText(Strings.Sizes[spec.getSize().castToInt()]);
        baysLabelValue.setText(Functions.formatNumber(spec.getCargoBays()));
        rangeLabelValue.setText(Functions.plural(spec.getFuelTanks(), Strings.DistanceUnit));
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
            } else if (Consts.ShipSpecs[i].getType() == game.getShip().getType()) {
                priceLabels[i].setText(Strings.ShipBuyGotOne);
            } else {
                buyButtons[i].setVisible(true);
                prices[i] = Consts.ShipSpecs[i].getPrice() - game.getShip().getWorth(false);
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

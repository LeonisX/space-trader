package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.game.Commander;
import spacetrader.game.Consts;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.ShipType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

import static spacetrader.game.Strings.*;

class ShipyardPanel extends Panel {

    private final SpaceTrader mainWindow;
    private Commander commander;

    private Label shipsForSaleLabel = new Label();
    private Button designButton = new Button();
    private Button buyShipButton = new Button();
    private Label equipForSaleLabel = new Label();
    private Button tradeEquipmentButton = new Button();
    private Label escapePodLabel = new Label();
    private Button buyPodButton = new Button();

    ShipyardPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    void initializeComponent() {
        setName("shipyardPanel");
        setText("Shipyard");
        setSize(168, 168);
        setTabStop(false);
        
        suspendLayout();

        shipsForSaleLabel.setLocation(8, 16);
        shipsForSaleLabel.setSize(152, 13);
        shipsForSaleLabel.setTabIndex(20);
        //shipsForSaleLabel.setText("There are new ships for sale.");

        designButton.setFlatStyle(FlatStyle.FLAT);
        designButton.setLocation(8, 32);
        designButton.setSize(54, 22);
        designButton.setTabIndex(55);
        designButton.setText("Design");
        designButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                designButtonClick();
            }
        });

        buyShipButton.setFlatStyle(FlatStyle.FLAT);
        buyShipButton.setLocation(70, 32);
        buyShipButton.setSize(86, 22);
        buyShipButton.setTabIndex(52);
        buyShipButton.setText("View Ship Info");
        buyShipButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                buyShipButtonClick();
            }
        });

        equipForSaleLabel.setLocation(8, 69);
        equipForSaleLabel.setSize(152, 13);
        equipForSaleLabel.setTabIndex(21);
        //equipForSaleLabel.setText("There is equipment for sale.");

        tradeEquipmentButton.setFlatStyle(FlatStyle.FLAT);
        tradeEquipmentButton.setLocation(43, 85);
        tradeEquipmentButton.setSize(113, 22);
        tradeEquipmentButton.setTabIndex(53);
        tradeEquipmentButton.setText("Buy/Sell Equipment");
        tradeEquipmentButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                tradeEquipmentButtonClick();
            }
        });

        escapePodLabel.setLocation(8, 122);
        escapePodLabel.setSize(152, 26);
        escapePodLabel.setTabIndex(27);
        //escapePodLabel.setText("You can buy an escape pod for 2,000 cr.");

        buyPodButton.setFlatStyle(FlatStyle.FLAT);
        buyPodButton.setLocation(98, 138);
        buyPodButton.setSize(58, 22);
        buyPodButton.setTabIndex(54);
        buyPodButton.setText("Buy Pod");
        buyPodButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                buyPodButtonClick();
            }
        });

        getControls().addAll(shipsForSaleLabel, designButton, buyShipButton, equipForSaleLabel,
                tradeEquipmentButton, escapePodLabel, buyPodButton);

        ReflectionUtils.loadControlsData(this);
    }

    void update() {
        if (commander == null) {
            shipsForSaleLabel.setText("");
            equipForSaleLabel.setText("");
            escapePodLabel.setText("");
            buyPodButton.setVisible(false);
            buyShipButton.setVisible(false);
            designButton.setVisible(false);
            tradeEquipmentButton.setVisible(false);
        } else {
            boolean noTech = (commander.getCurrentSystem().getTechLevel().castToInt() < Consts.ShipSpecs[ShipType.FLEA
                    .castToInt()].getMinimumTechLevel().castToInt());

            shipsForSaleLabel.setText(noTech ? ShipyardShipNoSale : ShipyardShipForSale);
            buyShipButton.setVisible(true);
            designButton.setVisible(commander.getCurrentSystem().getShipyard() != null);

            equipForSaleLabel.setText(noTech ? ShipyardEquipNoSale : ShipyardEquipForSale);
            tradeEquipmentButton.setVisible(true);

            buyPodButton.setVisible(false);
            if (commander.getShip().getEscapePod()) {
                escapePodLabel.setText(ShipyardPodInstalled);
            } else if (noTech) {
                escapePodLabel.setText(ShipyardPodNoSale);
            } else if (commander.getCash() < 2000) {
                escapePodLabel.setText(ShipyardPodIF);
            } else {
                escapePodLabel.setText(ShipyardPodCost);
                buyPodButton.setVisible(true);
            }
        }
    }

    private void buyShipButtonClick() {
        new FormShipList().showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void designButtonClick() {
        new FormShipyard().showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void tradeEquipmentButtonClick() {
        new FormEquipment().showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void buyPodButtonClick() {
        if (GuiFacade.alert(AlertType.EquipmentEscapePod) == DialogResult.YES) {
            commander.setCash(commander.getCash() - 2000);
            commander.getShip().setEscapePod(true);
            mainWindow.updateAll();
        }
    }
}

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.Commander;
import spacetrader.game.Consts;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.ShipType;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

import static spacetrader.game.Strings.*;

class ShipyardPanel extends Panel {

    private final SpaceTrader mainWindow;
    private Commander commander;

    private Label shipsForSaleLabel;
    private Button designButton;
    private Button buyShipButton;
    private Label equipForSaleLabel;
    private Button tradeEquipmentButton;
    private Label escapePodLabel;
    private Button buyPodButton;

    ShipyardPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    void initializeComponent() {
        designButton = new Button();
        buyPodButton = new Button();
        escapePodLabel = new Label();
        tradeEquipmentButton = new Button();
        buyShipButton = new Button();
        equipForSaleLabel = new Label();
        shipsForSaleLabel = new Label();

        suspendLayout();

        getControls().add(shipsForSaleLabel);
        getControls().add(designButton);
        getControls().add(buyShipButton);
        getControls().add(equipForSaleLabel);
        getControls().add(tradeEquipmentButton);
        getControls().add(escapePodLabel);
        getControls().add(buyPodButton);

        setSize(new spacetrader.controls.Size(168, 168));
        setTabIndex(4);
        setTabStop(false);
        setText("Shipyard");

        shipsForSaleLabel.setLocation(new Point(8, 16));
        shipsForSaleLabel.setSize(new spacetrader.controls.Size(152, 13));
        shipsForSaleLabel.setTabIndex(20);
        //shipsForSaleLabel.setText("There are new ships for sale.");

        designButton.setFlatStyle(FlatStyle.FLAT);
        designButton.setLocation(new Point(8, 32));
        designButton.setSize(new spacetrader.controls.Size(54, 22));
        designButton.setTabIndex(55);
        designButton.setText("Design");
        designButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                designButtonClick();
            }
        });

        buyShipButton.setFlatStyle(FlatStyle.FLAT);
        buyShipButton.setLocation(new Point(70, 32));
        buyShipButton.setSize(new spacetrader.controls.Size(86, 22));
        buyShipButton.setTabIndex(52);
        buyShipButton.setText("View Ship Info");
        buyShipButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                buyShipButtonClick();
            }
        });

        equipForSaleLabel.setLocation(new Point(8, 69));
        equipForSaleLabel.setSize(new spacetrader.controls.Size(152, 13));
        equipForSaleLabel.setTabIndex(21);
        //equipForSaleLabel.setText("There is equipment for sale.");

        tradeEquipmentButton.setFlatStyle(FlatStyle.FLAT);
        tradeEquipmentButton.setLocation(new Point(43, 85));
        tradeEquipmentButton.setSize(new spacetrader.controls.Size(113, 22));
        tradeEquipmentButton.setTabIndex(53);
        tradeEquipmentButton.setText("Buy/Sell Equipment");
        tradeEquipmentButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                tradeEquipmentButtonClick();
            }
        });

        escapePodLabel.setLocation(new Point(8, 122));
        escapePodLabel.setSize(new spacetrader.controls.Size(152, 26));
        escapePodLabel.setTabIndex(27);
        //escapePodLabel.setText("You can buy an escape pod for 2,000 cr.");

        buyPodButton.setFlatStyle(FlatStyle.FLAT);
        buyPodButton.setLocation(new Point(98, 138));
        buyPodButton.setSize(new spacetrader.controls.Size(58, 22));
        buyPodButton.setTabIndex(54);
        buyPodButton.setText("Buy Pod");
        buyPodButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                buyPodButtonClick();
            }
        });
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

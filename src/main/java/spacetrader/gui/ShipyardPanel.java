package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Commander;
import spacetrader.game.Consts;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.ShipType;
import spacetrader.guifacade.GuiFacade;

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
        setText("Shipyard");
        setSize(193, 173);
        setTabStop(false);

        shipsForSaleLabel.setAutoSize(true);
        shipsForSaleLabel.setLocation(8, 20);
        //shipsForSaleLabel.setText("There are new ships for sale.");

        designButton.setAutoWidth(true);
        designButton.setLocation(8, 36);
        designButton.setSize(54, 22);
        designButton.setTabIndex(55);
        designButton.setText("Design");
        designButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                designButtonClick();
            }
        });

        buyShipButton.setAutoWidth(true);
        buyShipButton.setControlBinding(ControlBinding.RIGHT);
        buyShipButton.setLocation(95, 36);
        buyShipButton.setSize(86, 22);
        buyShipButton.setTabIndex(52);
        buyShipButton.setText("View Ship Info");
        buyShipButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                buyShipButtonClick();
            }
        });

        equipForSaleLabel.setAutoSize(true);
        equipForSaleLabel.setLocation(8, 73);
        //equipForSaleLabel.setText("There is equipment for sale.");

        tradeEquipmentButton.setAutoWidth(true);
        tradeEquipmentButton.setControlBinding(ControlBinding.RIGHT);
        tradeEquipmentButton.setLocation(68, 89);
        tradeEquipmentButton.setSize(113, 22);
        tradeEquipmentButton.setTabIndex(53);
        tradeEquipmentButton.setText("Buy/Sell Equipment");
        tradeEquipmentButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                tradeEquipmentButtonClick();
            }
        });

        escapePodLabel.setLocation(8, 126);
        escapePodLabel.setSize(182, 26);
        //escapePodLabel.setText("You can buy an escape pod for 2,000 cr.");

        buyPodButton.setAutoWidth(true);
        buyPodButton.setControlBinding(ControlBinding.RIGHT);
        buyPodButton.setLocation(123, 142);
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

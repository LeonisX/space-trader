package spacetrader.gui;

import spacetrader.controls.DialogResult;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.FlatStyle;
import spacetrader.controls.Panel;
import spacetrader.game.Commander;
import spacetrader.game.Consts;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.ShipType;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

class ShipyardPanel extends Panel {
    private final SpaceTrader mainWindow;
    private Commander commander;
    private spacetrader.controls.Button btnDesign;
    private spacetrader.controls.Button btnPod;
    private spacetrader.controls.Label lblEquipForSale;
    private spacetrader.controls.Label lblEscapePod;
    private spacetrader.controls.Button btnEquip;
    private spacetrader.controls.Button btnBuyShip;
    private spacetrader.controls.Label lblShipsForSale;

    ShipyardPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    void initializeComponent() {
        btnDesign = new spacetrader.controls.Button();
        btnPod = new spacetrader.controls.Button();
        lblEscapePod = new spacetrader.controls.Label();
        btnEquip = new spacetrader.controls.Button();
        btnBuyShip = new spacetrader.controls.Button();
        lblEquipForSale = new spacetrader.controls.Label();
        lblShipsForSale = new spacetrader.controls.Label();

        suspendLayout();

        controls.add(btnDesign);
        controls.add(btnPod);
        controls.add(lblEscapePod);
        controls.add(btnEquip);
        controls.add(btnBuyShip);
        controls.add(lblEquipForSale);
        controls.add(lblShipsForSale);
        setSize(new spacetrader.controls.Size(168, 168));
        setTabIndex(4);
        setTabStop(false);
        setText("Shipyard");
        //
        // btnDesign
        //
        btnDesign.setFlatStyle(FlatStyle.FLAT);
        btnDesign.setLocation(new Point(8, 32));
        btnDesign.setSize(new spacetrader.controls.Size(54, 22));
        btnDesign.setTabIndex(55);
        btnDesign.setText("Design");
        btnDesign.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnDesign_Click(sender, e);
            }
        });
        //
        // btnPod
        //
        btnPod.setFlatStyle(FlatStyle.FLAT);
        btnPod.setLocation(new Point(98, 138));
        btnPod.setSize(new spacetrader.controls.Size(58, 22));
        btnPod.setTabIndex(54);
        btnPod.setText("Buy Pod");
        btnPod.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPod_Click(sender, e);
            }
        });
        //
        // lblEscapePod
        //
        lblEscapePod.setLocation(new Point(8, 122));
        lblEscapePod.setSize(new spacetrader.controls.Size(152, 26));
        lblEscapePod.setTabIndex(27);
        lblEscapePod.setText("You can buy an escape pod for  2,000 cr.");
        //
        // btnEquip
        //
        btnEquip.setFlatStyle(FlatStyle.FLAT);
        btnEquip.setLocation(new Point(43, 85));
        btnEquip.setSize(new spacetrader.controls.Size(113, 22));
        btnEquip.setTabIndex(53);
        btnEquip.setText("Buy/Sell Equipment");
        btnEquip.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnEquip_Click(sender, e);
            }
        });
        //
        // btnBuyShip
        //
        btnBuyShip.setFlatStyle(FlatStyle.FLAT);
        btnBuyShip.setLocation(new Point(70, 32));
        btnBuyShip.setSize(new spacetrader.controls.Size(86, 22));
        btnBuyShip.setTabIndex(52);
        btnBuyShip.setText("View Ship Info");
        btnBuyShip.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnBuyShip_Click(sender, e);
            }
        });
        //
        // lblEquipForSale
        //
        lblEquipForSale.setLocation(new Point(8, 69));
        lblEquipForSale.setSize(new spacetrader.controls.Size(152, 13));
        lblEquipForSale.setTabIndex(21);
        lblEquipForSale.setText("There is equipment for sale.");
        //
        // lblShipsForSale
        //
        lblShipsForSale.setLocation(new Point(8, 16));
        lblShipsForSale.setSize(new spacetrader.controls.Size(152, 13));
        lblShipsForSale.setTabIndex(20);
        lblShipsForSale.setText("There are new ships for sale.");
    }

    void update() {
        if (commander == null) {
            lblShipsForSale.setText("");
            lblEquipForSale.setText("");
            lblEscapePod.setText("");
            btnPod.setVisible(false);
            btnBuyShip.setVisible(false);
            btnDesign.setVisible(false);
            btnEquip.setVisible(false);
        } else {
            boolean noTech = (commander.getCurrentSystem().getTechLevel().castToInt() < Consts.ShipSpecs[ShipType.Flea
                    .castToInt()].MinimumTechLevel().castToInt());

            lblShipsForSale.setText(noTech ? Strings.ShipyardShipNoSale : Strings.ShipyardShipForSale);
            btnBuyShip.setVisible(true);
            btnDesign.setVisible(commander.getCurrentSystem().shipyard() != null);

            lblEquipForSale.setText(noTech ? Strings.ShipyardEquipNoSale : Strings.ShipyardEquipForSale);
            btnEquip.setVisible(true);

            btnPod.setVisible(false);
            if (commander.getShip().getEscapePod())
                lblEscapePod.setText(Strings.ShipyardPodInstalled);
            else if (noTech)
                lblEscapePod.setText(Strings.ShipyardPodNoSale);
            else if (commander.getCash() < 2000)
                lblEscapePod.setText(Strings.ShipyardPodIF);
            else {
                lblEscapePod.setText(Strings.ShipyardPodCost);
                btnPod.setVisible(true);
            }
        }
    }

    private void btnBuyShip_Click(Object sender, spacetrader.controls.EventArgs e) {
        new FormShipList().showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void btnDesign_Click(Object sender, spacetrader.controls.EventArgs e) {
        new FormShipyard().showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void btnEquip_Click(Object sender, spacetrader.controls.EventArgs e) {
        new FormEquipment().showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void btnPod_Click(Object sender, spacetrader.controls.EventArgs e) {
        if (GuiFacade.alert(AlertType.EquipmentEscapePod) == DialogResult.YES) {
            commander.setCash(commander.getCash() - 2000);
            commander.getShip().setEscapePod(true);
            mainWindow.updateAll();
        }
    }
}

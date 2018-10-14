package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.Commander;
import spacetrader.game.Functions;
import spacetrader.game.Ship;
import spacetrader.game.Strings;

import java.awt.*;


class DockPanel extends Panel {

    private final SpaceTrader mainWindow;
    private Commander commander;
    private Button btnFuel;
    private Button btnRepair;
    private Label lblFuelCost;
    private Label lblFuelStatus;
    private Label lblHullStatus;
    private Label lblRepairCost;

    DockPanel(SpaceTrader mainWindow, String name) {
        this.mainWindow = mainWindow;
        setName(name);
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    void initializeComponent() {
        btnRepair = new spacetrader.controls.Button();
        btnFuel = new spacetrader.controls.Button();
        lblFuelStatus = new spacetrader.controls.Label();
        lblFuelCost = new spacetrader.controls.Label();
        lblHullStatus = new spacetrader.controls.Label();
        lblRepairCost = new spacetrader.controls.Label();

        controls.add(btnRepair);
        controls.add(btnFuel);
        controls.add(lblFuelStatus);
        controls.add(lblFuelCost);
        controls.add(lblHullStatus);
        controls.add(lblRepairCost);;
        setSize(new spacetrader.controls.Size(240, 90));
        setTabIndex(2);
        setTabStop(false);
        setText("Dock");
        //
        // btnRepair
        //
        btnRepair.setFlatStyle(FlatStyle.FLAT);
        btnRepair.setLocation(new Point(180, 56));
        btnRepair.setName("btnRepair");
        btnRepair.setSize(new spacetrader.controls.Size(48, 22));
        btnRepair.setTabIndex(5);
        btnRepair.setText("Repair");
        btnRepair.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnRepair_Click(sender, e);
            }
        });
        //
        // btnFuel
        //
        btnFuel.setFlatStyle(FlatStyle.FLAT);
        btnFuel.setLocation(new Point(192, 18));
        btnFuel.setName("btnFuel");
        btnFuel.setSize(new spacetrader.controls.Size(36, 22));
        btnFuel.setTabIndex(4);
        btnFuel.setText("Fuel");
        btnFuel.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnFuel_Click(sender, e);
            }
        });
        //
        // lblFuelStatus
        //
        lblFuelStatus.setLocation(new Point(8, 16));
        lblFuelStatus.setName("lblFuelStatus");
        lblFuelStatus.setSize(new spacetrader.controls.Size(162, 13));
        lblFuelStatus.setTabIndex(20);
        lblFuelStatus.setText("You have fuel to fly 88 parsecs.");
        //
        // lblFuelCost
        //
        lblFuelCost.setLocation(new Point(8, 31));
        lblFuelCost.setName("lblFuelCost");
        lblFuelCost.setSize(new spacetrader.controls.Size(121, 13));
        lblFuelCost.setTabIndex(19);
        lblFuelCost.setText("A full tank costs 888 cr.");
        //
        // lblHullStatus
        //
        lblHullStatus.setLocation(new Point(8, 52));
        lblHullStatus.setName("lblHullStatus");
        lblHullStatus.setSize(new spacetrader.controls.Size(152, 13));
        lblHullStatus.setTabIndex(18);
        lblHullStatus.setText("Your hull strength is at 888%.");
        //
        // lblRepairCost
        //
        lblRepairCost.setLocation(new Point(8, 67));
        lblRepairCost.setName("lblRepairCost");
        lblRepairCost.setSize(new spacetrader.controls.Size(150, 13));
        lblRepairCost.setTabIndex(19);
        lblRepairCost.setText("Full repairs will cost 8,888 cr.");

    }

    private void btnFuel_Click(Object sender, spacetrader.controls.EventArgs e) {
        FormBuyFuel form = new FormBuyFuel();
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            int toAdd = form.Amount() / commander.getShip().getFuelCost();
            commander.getShip().setFuel(commander.getShip().getFuel() + toAdd);
            commander.setCash(commander.getCash() - (toAdd * commander.getShip().getFuelCost()));
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    private void btnRepair_Click(Object sender, spacetrader.controls.EventArgs e) {
        FormBuyRepairs form = new FormBuyRepairs();
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            int toAdd = form.Amount() / commander.getShip().getRepairCost();
            commander.getShip().setHull(commander.getShip().getHull() + toAdd);
            commander.setCash(commander.getCash() - (toAdd * commander.getShip().getRepairCost()));
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    void Update() {
        if (commander == null) {
            lblFuelStatus.setText("");
            lblFuelCost.setText("");
            btnFuel.setVisible(false);
            lblHullStatus.setText("");
            lblRepairCost.setText("");
            btnRepair.setVisible(false);
        } else {
            Ship ship = commander.getShip();

            lblFuelStatus.setText(Functions.StringVars(Strings.DockFuelStatus, Functions.Multiples(ship.getFuel(),
                    "parsec")));
            int tanksEmpty = ship.FuelTanks() - ship.getFuel();
            lblFuelCost.setText(tanksEmpty > 0 ? Functions.StringVars(Strings.DockFuelCost, Functions
                    .formatMoney(tanksEmpty * ship.getFuelCost())) : Strings.DockFuelFull);
            btnFuel.setVisible(tanksEmpty > 0);

            lblHullStatus.setText(Functions.StringVars(Strings.DockHullStatus, Functions.formatNumber((int) Math
                    .floor((double) 100 * ship.getHull() / ship.HullStrength()))));
            int hullLoss = ship.HullStrength() - ship.getHull();
            lblRepairCost.setText(hullLoss > 0 ? Functions.StringVars(Strings.DockHullCost, Functions
                    .formatMoney(hullLoss * ship.getRepairCost())) : Strings.DockHullFull);
            btnRepair.setVisible(hullLoss > 0);
        }
    }
}

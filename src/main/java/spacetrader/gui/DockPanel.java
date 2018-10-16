package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.Commander;
import spacetrader.game.Ship;

import java.awt.*;

import static spacetrader.game.Functions.*;
import static spacetrader.game.Strings.*;

class DockPanel extends Panel {

    private final SpaceTrader mainWindow;
    private Commander commander;

    private Button fuelUpButton;
    private Button repairButton;
    private Label fuelStatusLabel;
    private Label fuelCostLabel;
    private Label hullStatusLabel;
    private Label repairCostLabel;

    DockPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    void initializeComponent() {
        repairButton = new Button();
        fuelUpButton = new Button();
        fuelStatusLabel = new Label();
        fuelCostLabel = new Label();
        hullStatusLabel = new Label();
        repairCostLabel = new Label();

        controls.add(repairButton);
        controls.add(fuelUpButton);
        controls.add(fuelStatusLabel);
        controls.add(fuelCostLabel);
        controls.add(hullStatusLabel);
        controls.add(repairCostLabel);

        setSize(new Size(240, 90));
        setTabIndex(2);
        setTabStop(false);
        setText("Dock");

        repairButton.setFlatStyle(FlatStyle.FLAT);
        repairButton.setLocation(new Point(180, 56));
        repairButton.setSize(new Size(48, 22));
        repairButton.setTabIndex(5);
        repairButton.setText("Repair");
        repairButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                repairButtonClick();
            }
        });

        fuelUpButton.setFlatStyle(FlatStyle.FLAT);
        fuelUpButton.setLocation(new Point(192, 18));
        fuelUpButton.setSize(new Size(36, 22));
        fuelUpButton.setTabIndex(4);
        fuelUpButton.setText("Fuel");
        fuelUpButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                fuelUpButtonClick();
            }
        });

        fuelStatusLabel.setLocation(new Point(8, 16));
        fuelStatusLabel.setSize(new Size(162, 13));
        fuelStatusLabel.setTabIndex(20);
        //fuelStatusLabel.setText("You have fuel to fly 88 parsecs.");

        fuelCostLabel.setLocation(new Point(8, 31));
        fuelCostLabel.setSize(new Size(121, 13));
        fuelCostLabel.setTabIndex(19);
        //fuelCostLabel.setText("A full tank costs 888 cr.");

        hullStatusLabel.setLocation(new Point(8, 52));
        hullStatusLabel.setSize(new Size(152, 13));
        hullStatusLabel.setTabIndex(18);
        //hullStatusLabel.setText("Your hull strength is at 888%.");

        repairCostLabel.setLocation(new Point(8, 67));
        repairCostLabel.setSize(new Size(150, 13));
        repairCostLabel.setTabIndex(19);
        //repairCostLabel.setText("Full repairs will cost 8,888 cr.");
    }

    private void fuelUpButtonClick() {
        FormBuyFuel form = new FormBuyFuel();
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            int toAdd = form.getAmount() / commander.getShip().getFuelCost();
            commander.getShip().setFuel(commander.getShip().getFuel() + toAdd);
            commander.setCash(commander.getCash() - (toAdd * commander.getShip().getFuelCost()));
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    private void repairButtonClick() {
        FormBuyRepairs form = new FormBuyRepairs();
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            int toAdd = form.getAmount() / commander.getShip().getRepairCost();
            commander.getShip().setHull(commander.getShip().getHull() + toAdd);
            commander.setCash(commander.getCash() - (toAdd * commander.getShip().getRepairCost()));
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    void update() {
        if (commander == null) {
            fuelStatusLabel.setText("");
            fuelCostLabel.setText("");
            fuelUpButton.setVisible(false);
            hullStatusLabel.setText("");
            repairCostLabel.setText("");
            repairButton.setVisible(false);
        } else {
            Ship ship = commander.getShip();

            fuelStatusLabel.setText(stringVars(DockFuelStatus, multiples(ship.getFuel(), DockParsec)));

            int tanksEmpty = ship.getFuelTanks() - ship.getFuel();
            fuelCostLabel.setText(tanksEmpty > 0
                    ? stringVars(DockFuelCost, formatMoney(tanksEmpty * ship.getFuelCost())) : DockFuelFull);
            fuelUpButton.setVisible(tanksEmpty > 0);

            hullStatusLabel.setText(stringVars(DockHullStatus,
                    formatNumber((int) Math.floor((double) 100 * ship.getHull() / ship.getHullStrength()))));

            int hullLoss = ship.getHullStrength() - ship.getHull();
            repairCostLabel.setText(hullLoss > 0
                    ? stringVars(DockHullCost, formatMoney(hullLoss * ship.getRepairCost())) : DockHullFull);
            repairButton.setVisible(hullLoss > 0);
        }
    }
}

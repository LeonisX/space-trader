package spacetrader.gui;

import static spacetrader.game.Functions.formatMoney;
import static spacetrader.game.Functions.formatNumber;
import static spacetrader.game.Functions.multiples;
import static spacetrader.game.Functions.stringVars;
import static spacetrader.game.Strings.DistanceUnit;
import static spacetrader.game.Strings.DockFuelCost;
import static spacetrader.game.Strings.DockFuelFull;
import static spacetrader.game.Strings.DockFuelStatus;
import static spacetrader.game.Strings.DockHullCost;
import static spacetrader.game.Strings.DockHullFull;
import static spacetrader.game.Strings.DockHullStatus;

import java.awt.Point;
import spacetrader.controls.Button;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.Size;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.game.Commander;
import spacetrader.game.GlobalAssets;
import spacetrader.game.Ship;
import spacetrader.util.ReflectionUtils;

class DockPanel extends Panel {

    private final SpaceTrader mainWindow;
    private Commander commander;

    private Button fuelUpButton = new Button();
    private Button repairButton = new Button();
    private Label fuelStatusLabel = new Label();
    private Label fuelCostLabel = new Label();
    private Label hullStatusLabel = new Label();
    private Label repairCostLabel = new Label();

    DockPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("dockPanel");
        setText("Dock");
        setSize(new Size(240, 90));
        setTabStop(false);

        fuelStatusLabel.setLocation(new Point(8, 16));
        fuelStatusLabel.setSize(new Size(162, 13));
        fuelStatusLabel.setTabIndex(20);
        //fuelStatusLabel.setText("You have fuel to fly 88 parsecs.");

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

        fuelCostLabel.setLocation(new Point(8, 31));
        fuelCostLabel.setSize(new Size(121, 13));
        fuelCostLabel.setTabIndex(19);
        //fuelCostLabel.setText("A full tank costs 888 cr.");

        hullStatusLabel.setLocation(new Point(8, 52));
        hullStatusLabel.setSize(new Size(152, 13));
        hullStatusLabel.setTabIndex(18);
        //hullStatusLabel.setText("Your hull strength is at 888%.");

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

        repairCostLabel.setLocation(new Point(8, 67));
        repairCostLabel.setSize(new Size(150, 13));
        repairCostLabel.setTabIndex(19);
        //repairCostLabel.setText("Full repairs will cost 8,888 cr.");

        getControls()
                .addAll(fuelStatusLabel, fuelUpButton, fuelCostLabel, hullStatusLabel, repairButton, repairCostLabel);

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
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

            fuelStatusLabel.setText(stringVars(DockFuelStatus, multiples(ship.getFuel(), DistanceUnit)));

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

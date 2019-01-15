package spacetrader.game.quest.containers;

import spacetrader.game.ShipSpec;

public class ShipSpecContainer {

    private ShipSpec shipSpec;
    private int hullStrength;

    public ShipSpecContainer(ShipSpec shipSpec) {
        this.shipSpec = shipSpec;
        this.hullStrength = shipSpec.getBaseHullStrength();
    }

    public ShipSpec getShipSpec() {
        return shipSpec;
    }

    public void setShipSpec(ShipSpec shipSpec) {
        this.shipSpec = shipSpec;
    }

    public int getHullStrength() {
        return hullStrength;
    }

    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
    }
}

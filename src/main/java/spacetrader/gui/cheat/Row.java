package spacetrader.gui.cheat;

public class Row {

    private final int systemId;
    private final String system;
    private final String description;
    private final String feature;

    public Row(int systemId, String system, String description, String feature) {
        this.systemId = systemId;
        this.system = system;
        this.description = description;
        this.feature = feature;
    }

    public int getSystemId() {
        return systemId;
    }

    public String getSystem() {
        return system;
    }

    public String getDescription() {
        return description;
    }

    public String getFeature() {
        return feature;
    }
}

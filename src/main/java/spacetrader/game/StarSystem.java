package spacetrader.game;

import spacetrader.game.enums.*;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StarSystem implements Serializable {

    private StarSystemId id;
    private int x;
    private int y;
    private Size size;
    private TechLevel techLevel;
    private PoliticalSystemType politicalSystemType;
    private SystemPressure systemPressure;
    private SpecialResource specialResource;
    private boolean questSystem = false;
    private int[] tradeItems = new int[10];
    private int countDown = 0;
    private boolean visited = false;
    private ShipyardId shipyardId = ShipyardId.NA;

    public StarSystem() {
        // need for tests
    }

    public StarSystem(StarSystemId id, int x, int y, Size size, TechLevel techLevel,
                      PoliticalSystemType politicalSystemType, SystemPressure systemPressure,
                      SpecialResource specialResource) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.size = size;
        this.techLevel = techLevel;
        this.politicalSystemType = politicalSystemType;
        this.systemPressure = systemPressure;
        this.specialResource = specialResource;

        initializeTradeItems();
    }

    public void initializeTradeItems() {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (!isItemTraded(Consts.TradeItems[i])) {
                tradeItems[i] = 0;
            } else {
                tradeItems[i] = (this.getSize().castToInt() + 1)
                        * (Functions.getRandom(9, 14) - Math.abs(Consts.TradeItems[i].getTechTopProduction().castToInt()
                        - this.getTechLevel().castToInt()));

                // Because of the enormous profits possible, there shouldn't be
                // too many robots or narcotics available.
                if (i >= TradeItemType.NARCOTICS.castToInt()) {
                    tradeItems[i] = ((tradeItems[i] * (5 - Game.getCurrentGame().getDifficultyId())) /
                            (6 - Game.getCurrentGame().getDifficultyId())) + 1;
                }

                if (this.getSpecialResource() == Consts.TradeItems[i].getResourceLowPrice()) {
                    tradeItems[i] = tradeItems[i] * 4 / 3;
                }

                if (this.getSpecialResource() == Consts.TradeItems[i].getResourceHighPrice()) {
                    tradeItems[i] = tradeItems[i] * 3 / 4;
                }

                if (this.getSystemPressure() == Consts.TradeItems[i].getPressurePriceHike()) {
                    tradeItems[i] = tradeItems[i] / 5;
                }

                tradeItems[i] = tradeItems[i] - Functions.getRandom(10) + Functions.getRandom(10);

                if (tradeItems[i] < 0) {
                    tradeItems[i] = 0;
                }
            }
        }
    }

    public boolean isItemTraded(TradeItem item) {
        return ((item.getType() != TradeItemType.NARCOTICS || getPoliticalSystem().isDrugsOk())
                && (item.getType() != TradeItemType.FIREARMS
                || getPoliticalSystem().isFirearmsOk()) && getTechLevel().castToInt() >= item
                .getTechProduction().castToInt());
    }

    public boolean itemUsed(TradeItem item) {
        return ((item.getType() != TradeItemType.NARCOTICS || getPoliticalSystem().isDrugsOk())
                && (item.getType() != TradeItemType.FIREARMS
                || getPoliticalSystem().isFirearmsOk()) && getTechLevel().castToInt() >= item
                .getTechUsage().castToInt());
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int value) {
        countDown = value;
    }

    public boolean destIsOk() {
        Commander comm = Game.getCurrentGame().getCommander();
        return this != comm.getCurrentSystem()
                && (getDistance() <= comm.getShip().getFuel() || Functions.wormholeExists(comm.getCurrentSystem(), this));
    }

    public int getDistance() {
        return Functions.distance(this, Game.getCurrentGame().getCommander().getCurrentSystem());
    }

    public StarSystemId getId() {
        return id;
    }

    public List<CrewMember> getMercenariesForHire() {
        Commander cmdr = Game.getCurrentGame().getCommander();

        return Game.getCurrentGame().getMercenaries().values().stream().filter(merc ->
                merc.isMercenary()
                        && merc.getCurrentSystem() == cmdr.getCurrentSystem()
                        && !cmdr.getShip().hasCrew(merc.getId())
        ).collect(Collectors.toList());
    }

    public String getName() {
        return Strings.SystemNames[id.castToInt()];
    }

    public PoliticalSystem getPoliticalSystem() {
        return Consts.PoliticalSystems[politicalSystemType.castToInt()];
    }

    public PoliticalSystemType getPoliticalSystemType() {
        return politicalSystemType;
    }

    public void setPoliticalSystemType(PoliticalSystemType value) {
        politicalSystemType = value;
    }

    public Shipyard getShipyard() {
        return (shipyardId == ShipyardId.NA) ? null : Consts.Shipyards[shipyardId.castToInt()];
    }

    public ShipyardId getShipyardId() {
        return shipyardId;
    }

    public void setShipyardId(ShipyardId value) {
        shipyardId = value;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public boolean isQuestSystem() {
        return questSystem;
    }

    public void setQuestSystem(boolean value) {
        questSystem = value;
    }

    public SpecialResource getSpecialResource() {
        return isVisited() ? specialResource : SpecialResource.NOTHING;
    }

    public SystemPressure getSystemPressure() {
        return systemPressure;
    }

    public void setSystemPressure(SystemPressure value) {
        systemPressure = value;
    }

    public TechLevel getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(TechLevel value) {
        techLevel = value;
    }

    public int[] getTradeItems() {
        return tradeItems;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean value) {
        visited = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int value) {
        x = value;
    }

    public int getY() {
        return y;
    }

    public void setY(int value) {
        y = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StarSystem)) return false;
        StarSystem that = (StarSystem) o;
        return x == that.x &&
                y == that.y &&
                countDown == that.countDown &&
                visited == that.visited &&
                id == that.id &&
                size == that.size &&
                techLevel == that.techLevel &&
                politicalSystemType == that.politicalSystemType &&
                systemPressure == that.systemPressure &&
                specialResource == that.specialResource &&
                questSystem == that.questSystem &&
                Arrays.equals(tradeItems, that.tradeItems) &&
                shipyardId == that.shipyardId;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, x, y, size, techLevel, politicalSystemType, systemPressure, specialResource, questSystem, countDown, visited, shipyardId);
        result = 31 * result + Arrays.hashCode(tradeItems);
        return result;
    }
}

package spacetrader.game;

import spacetrader.game.enums.*;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.*;

public class StarSystem implements Serializable {

    private StarSystemId id;
    private int x;
    private int y;
    private Size size;
    private TechLevel techLevel;
    private PoliticalSystemType politicalSystemType;
    private SystemPressure systemPressure;
    private SpecialResource specialResource;
    private SpecialEventType specialEventType = SpecialEventType.NA;
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
                    tradeItems[i] = ((tradeItems[i] * (5 - Game.getDifficultyId())) /
                            (6 - Game.getDifficultyId())) + 1;
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

    public boolean showSpecialButton() {
        Game game = Game.getCurrentGame();
        boolean show = false;

        switch (getSpecialEventType()) {
            case Artifact:
            case Dragonfly:
            case Experiment:
            //case Jarek:
                show = Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
                break;
            case ArtifactDelivery:
                show = Game.getShip().isArtifactOnBoard();
                break;
            case CargoForSale:
                show = Game.getShip().getFreeCargoBays() >= 3;
                break;
            case DragonflyBaratas:
                show = game.getQuestStatusDragonfly() > SpecialEvent.STATUS_DRAGONFLY_NOT_STARTED
                        && game.getQuestStatusDragonfly() < SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyDestroyed:
                show = game.getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyMelina:
                show = game.getQuestStatusDragonfly() > SpecialEvent.STATUS_DRAGONFLY_FLY_BARATAS
                        && game.getQuestStatusDragonfly() < SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyRegulas:
                show = game.getQuestStatusDragonfly() > SpecialEvent.STATUS_DRAGONFLY_FLY_MELINA
                        && game.getQuestStatusDragonfly() < SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyShield:
            case ExperimentFailed:
            case Gemulon:
            case GemulonFuel:
            case GemulonInvaded:
            //case Lottery:
            case ReactorLaser:
            //case PrincessQuantum:
            //case SculptureHiddenBays:
            case Skill:
            case SpaceMonster:
            case Tribble:
                show = true;
                break;
            case EraseRecord:
            /*case Wild:
                show = Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious;
                break;*/
            case ExperimentStopped:
                show = game.getQuestStatusExperiment() > SpecialEvent.STATUS_EXPERIMENT_NOT_STARTED
                        && game.getQuestStatusExperiment() < SpecialEvent.STATUS_EXPERIMENT_PERFORMED;
                break;
            case GemulonRescued:
                show = game.getQuestStatusGemulon() > SpecialEvent.STATUS_GEMULON_NOT_STARTED
                        && game.getQuestStatusGemulon() < SpecialEvent.STATUS_GEMULON_TOO_LATE;
                break;
            case Japori:
                show = game.getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_NOT_STARTED
                        && Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
                break;
            case JaporiDelivery:
                show = game.getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT;
                break;
            /*case JarekGetsOut:
                show = Game.getShip().isJarekOnBoard();
                break;*/
            case Moon:
                show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_NOT_STARTED
                        && Game.getCommander().getWorth() > SpecialEvent.MOON_COST * .8;
                break;
            case MoonRetirement:
                show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_BOUGHT;
                break;
            /*case Princess:
                show = Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
                        && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case PrincessCentauri:
                show = game.getQuestStatusPrincess() >= SpecialEvent.STATUS_PRINCESS_FLY_CENTAURI
                        && game.getQuestStatusPrincess() <= SpecialEvent.STATUS_PRINCESS_FLY_QONOS;
                break;
            case PrincessInthara:
                show = game.getQuestStatusPrincess() >= SpecialEvent.STATUS_PRINCESS_FLY_INTHARA
                        && game.getQuestStatusPrincess() <= SpecialEvent.STATUS_PRINCESS_FLY_QONOS;
                break;
            case PrincessQonos:
                show = game.getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_RESCUED
                        && !Game.getShip().isPrincessOnBoard();
                break;
            case PrincessReturned:
                show = Game.getShip().isPrincessOnBoard();
                break;*/
            case Reactor:
                show = game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_NOT_STARTED
                        && Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                        && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case ReactorDelivered:
                show = Game.getShip().isReactorOnBoard();
                break;
            case Scarab:
                show = game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_NOT_STARTED
                        && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case ScarabDestroyed:
            case ScarabUpgradeHull:
                show = game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DESTROYED;
                break;
            /*case Sculpture:
                show = game.getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_NOT_STARTED
                        && Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                        && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case SculptureDelivered:
                show = game.getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_IN_TRANSIT;
                break;*/
            case SpaceMonsterKilled:
                show = game.getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_DESTROYED;
                break;
            case TribbleBuyer:
                show = Game.getShip().getTribbles() > 0;
                break;
            /*case WildGetsOut:
                show = Game.getShip().isWildOnBoard();
                break;*/
        }

        return show;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int value) {
        countDown = value;
    }

    public boolean destIsOk() {
        Commander comm = Game.getCommander();
        return this != comm.getCurrentSystem()
                && (getDistance() <= comm.getShip().getFuel() || Functions.wormholeExists(comm.getCurrentSystem(), this));
    }

    public int getDistance() {
        return Functions.distance(this, Game.getCommander().getCurrentSystem());
    }

    public StarSystemId getId() {
        return id;
    }

    public List<CrewMember> getMercenariesForHire() {
        Commander cmdr = Game.getCommander();
        Collection<CrewMember> mercs = Game.getCurrentGame().getMercenaries().values();
        List<CrewMember> forHire = new ArrayList<>();

        for (CrewMember merc: mercs) {
            if (merc.getCurrentSystem() == cmdr.getCurrentSystem() && !cmdr.getShip().hasCrew(merc.getId())) {
                forHire.add(merc);
            }
        }

        return forHire;
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

    public SpecialEvent specialEvent() {
        return (specialEventType == SpecialEventType.NA ? null : Consts.SpecialEvents[specialEventType.castToInt()]);
    }

    public SpecialEventType getSpecialEventType() {
        return specialEventType;
    }

    public void setSpecialEventType(SpecialEventType value) {
        specialEventType = value;
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
                specialEventType == that.specialEventType &&
                Arrays.equals(tradeItems, that.tradeItems) &&
                shipyardId == that.shipyardId;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, x, y, size, techLevel, politicalSystemType, systemPressure, specialResource, specialEventType, countDown, visited, shipyardId);
        result = 31 * result + Arrays.hashCode(tradeItems);
        return result;
    }
}

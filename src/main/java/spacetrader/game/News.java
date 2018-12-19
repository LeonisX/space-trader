package spacetrader.game;

import spacetrader.game.enums.NewsEvent;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.SystemPressure;
import spacetrader.game.quest.enums.EventName;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class News implements Serializable {

    //TODO
    //static final long serialVersionUID = 110L;

    private ArrayList<Integer> newsEvents = new ArrayList<>(); // News for current system

    public void addEvent(int newEventId) {
        newsEvents.add(newEventId);
    }

    public void addEvent(NewsEvent newsEvent) {
        newsEvents.add(newsEvent.castToInt());
    }

    void addEventsOnArrival() {
        Game.getCurrentGame().getQuestSystem().fireEvent(EventName.ON_NEWS_ADD_EVENT_ON_ARRIVAL);
        if (Game.getCommander().getCurrentSystem().getSpecialEventType() != SpecialEventType.NA) {
            switch (Game.getCommander().getCurrentSystem().getSpecialEventType()) {
                case ArtifactDelivery:
                    if (Game.getCommander().getShip().isArtifactOnBoard()) {
                        addEvent(NewsEvent.ArtifactDelivery.castToInt());
                    }
                    break;
                case Dragonfly:
                    addEvent(NewsEvent.Dragonfly.castToInt());
                    break;
                case DragonflyBaratas:
                    if (Game.getCurrentGame().getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_BARATAS) {
                        addEvent(NewsEvent.DragonflyBaratas.castToInt());
                    }
                    break;
                case DragonflyDestroyed:
                    if (Game.getCurrentGame().getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_ZALKON) {
                        addEvent(NewsEvent.DragonflyZalkon.castToInt());
                    } else if (Game.getCurrentGame().getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_DESTROYED) {
                        addEvent(NewsEvent.DragonflyDestroyed.castToInt());
                    }
                    break;
                case DragonflyMelina:
                    if (Game.getCurrentGame().getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_MELINA) {
                        addEvent(NewsEvent.DragonflyMelina.castToInt());
                    }
                    break;
                case DragonflyRegulas:
                    if (Game.getCurrentGame().getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_REGULAS) {
                        addEvent(NewsEvent.DragonflyRegulas.castToInt());
                    }
                    break;
                case ExperimentFailed:
                    addEvent(NewsEvent.ExperimentFailed.castToInt());
                    break;
                case ExperimentStopped:
                    if (Game.getCurrentGame().getQuestStatusExperiment() > SpecialEvent.STATUS_EXPERIMENT_NOT_STARTED
                            && Game.getCurrentGame().getQuestStatusExperiment() < SpecialEvent.STATUS_EXPERIMENT_PERFORMED) {
                        addEvent(NewsEvent.ExperimentStopped.castToInt());
                    }
                    break;
                case Gemulon:
                    addEvent(NewsEvent.Gemulon.castToInt());
                    break;
                case GemulonRescued:
                    if (Game.getCurrentGame().getQuestStatusGemulon() > SpecialEvent.STATUS_GEMULON_NOT_STARTED) {
                        if (Game.getCurrentGame().getQuestStatusGemulon() < SpecialEvent.STATUS_GEMULON_TOO_LATE) {
                            addEvent(NewsEvent.GemulonRescued.castToInt());
                        } else {
                            addEvent(NewsEvent.GemulonInvaded.castToInt());
                        }
                    }
                    break;
                case Japori:
                    if (Game.getCurrentGame().getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_NOT_STARTED) {
                        addEvent(NewsEvent.Japori.castToInt());
                    }
                    break;
                case JaporiDelivery:
                    if (Game.getCurrentGame().getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT) {
                        addEvent(NewsEvent.JaporiDelivery.castToInt());
                    }
                    break;
                /*case JarekGetsOut:
                    if (commander.getShip().isJarekOnBoard()) {
                        addEvent(NewsEvent.JarekGetsOut);
                    }
                    break;*/
                /*case Princess:
                    addEvent(NewsEvent.Princess.castToInt());
                    break;
                case PrincessCentauri:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_CENTAURI) {
                        addEvent(NewsEvent.PrincessCentauri.castToInt());
                    }
                    break;
                case PrincessInthara:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_INTHARA) {
                        addEvent(NewsEvent.PrincessInthara.castToInt());
                    }
                    break;
                case PrincessQonos:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_QONOS) {
                        newsAddEvent(NewsEvent.PrincessQonos.castToInt());
                    } else if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_RESCUED) {
                        addEvent(NewsEvent.PrincessRescued.castToInt());
                    }
                    break;
                case PrincessReturned:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_RETURNED) {
                        addEvent(NewsEvent.PrincessReturned.castToInt());
                    }
                    break;*/
                case Scarab:
                    addEvent(NewsEvent.Scarab.castToInt());
                    break;
                case ScarabDestroyed:
                    if (Game.getCurrentGame().getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_HUNTING) {
                        addEvent(NewsEvent.ScarabHarass.castToInt());
                    } else if (Game.getCurrentGame().getQuestStatusScarab() >= SpecialEvent.STATUS_SCARAB_DESTROYED) {
                        addEvent(NewsEvent.ScarabDestroyed.castToInt());
                    }
                    break;
                case Sculpture:
                    addEvent(NewsEvent.SculptureStolen.castToInt());
                    break;
                case SculptureDelivered:
                    addEvent(NewsEvent.SculptureTracked.castToInt());
                    break;
                case SpaceMonsterKilled:
                    if (Game.getCurrentGame().getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_AT_ACAMAR) {
                        addEvent(NewsEvent.SpaceMonster.castToInt());
                    } else if (Game.getCurrentGame().getQuestStatusSpaceMonster() >= SpecialEvent.STATUS_SPACE_MONSTER_DESTROYED) {
                        addEvent(NewsEvent.SpaceMonsterKilled.castToInt());
                    }
                    break;
                /*case WildGetsOut:
                    if (Game.getCommander().getShip().isWildOnBoard()) {
                        addEvent(NewsEvent.WildGetsOut.castToInt());
                    }
                    break;*/
            }
        }
    }

    public void replaceLastAttackedEventWithDestroyedEvent() {
        int oldEvent = newsEvents.get(newsEvents.size() - 1);
        int newEvent = oldEvent + 1;

        //TODO need check????
        if (newsEvents.indexOf(oldEvent) >= 0) {
            newsEvents.remove(oldEvent);
        }
        newsEvents.add(newEvent);
    }

    void resetEvents() {
        newsEvents.clear();
    }

    String getNewspaperHead() {
        String[] heads = Strings.NewsMastheads[Game.getCommander().getCurrentSystem().getPoliticalSystemType().castToInt()];
        String head = heads[Game.getCommander().getCurrentSystem().getId().castToInt() % heads.length];

        return Functions.stringVars(head, Game.getCommander().getCurrentSystem().getName());
    }

    private String getNewspaperText(Integer newsEventId) {
        return (newsEventId < 1000) ? Strings.NewsEvent[newsEventId] : Game.getCurrentGame().getQuestSystem().getNewsTitle(newsEventId);
    }

    String getNewspapersText() {
        StarSystem curSys = Game.getCommander().getCurrentSystem();
        List<String> news = new ArrayList<>();

        // We're using the getRandom2 function so that the same number is
        // generated each time for the same "version" of the newspaper. -JAF
        Functions.randSeed(curSys.getId().castToInt(), Game.getCommander().getDays());

        for (Integer newsEventId : newsEvents) {
            news.add(Functions.stringVars(getNewspaperText(newsEventId), new String[]{Game.getCommander().getName(),
                    Game.getCommander().getCurrentSystem().getName(), Game.getCommander().getShip().getName()}));
        }

        if (curSys.getSystemPressure() != SystemPressure.NONE) {
            news.add(Strings.NewsPressureInternal[curSys.getSystemPressure().castToInt()]);
        }

        if (Game.getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScoreVillain) {
            String baseStr = Strings.NewsPoliceRecordPsychopath[Functions
                    .getRandom2(Strings.NewsPoliceRecordPsychopath.length)];
            news.add(Functions.stringVars(baseStr, Game.getCommander().getName(), curSys.getName()));
        } else if (Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreHero) {
            String baseStr = Strings.NewsPoliceRecordHero[Functions.getRandom2(Strings.NewsPoliceRecordHero.length)];
            news.add(Functions.stringVars(baseStr, Game.getCommander().getName(), curSys.getName()));
        }

        // and now, finally, useful news (if any)
        // base probability of a story showing up is (50 / MAXTECHLEVEL) * Current Tech Level
        // This is then modified by adding 10% for every level of play less than impossible
        boolean realNews = false;
        //TODO ???
        int minProbability = Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10 * (5 - Game.getDifficultyId());
        for (StarSystem starSystem : Game.getCurrentGame().getUniverse()) {
            if (starSystem.destIsOk() && starSystem != curSys) {
                // Special stories that always get shown: moon, millionaire, shipyard
                if (starSystem.getSpecialEventType() != SpecialEventType.NA) {
                    if (starSystem.getSpecialEventType() == SpecialEventType.Moon) {
                        news.add(Functions.stringVars(Strings.NewsMoonForSale, starSystem.getName()));
                    } else if (starSystem.getSpecialEventType() == SpecialEventType.TribbleBuyer) {
                        news.add(Functions.stringVars(Strings.NewsTribbleBuyer, starSystem.getName()));
                    }
                }
                if (starSystem.getShipyardId() != ShipyardId.NA) {
                    news.add(Functions.stringVars(Strings.NewsShipyard, starSystem.getName()));
                }

                // And not-always-shown stories
                if (starSystem.getSystemPressure() != SystemPressure.NONE
                        && Functions.getRandom2(100) <= Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10
                        * (5 - Game.getDifficultyId())) {
                    int index = Functions.getRandom2(Strings.NewsPressureExternal.length);
                    String baseStr = Strings.NewsPressureExternal[index];
                    String pressure = Strings.NewsPressureExternalPressures[starSystem.getSystemPressure().castToInt()];
                    news.add(Functions.stringVars(baseStr, pressure, starSystem.getName()));
                    realNews = true;
                }
            }
        }

        // if there's no useful news, we throw up at least one headline from our canned news list.
        if (!realNews) {
            String[] headlines = Strings.NewsHeadlines[curSys.getPoliticalSystemType().castToInt()];
            boolean[] shown = new boolean[headlines.length];

            int toShow = Functions.getRandom2(headlines.length);
            for (int i = 0; i <= toShow; i++) {
                int index = Functions.getRandom2(headlines.length);
                if (!shown[index]) {
                    news.add(headlines[index]);
                    shown[index] = true;
                }
            }
        }

        return news.stream().map(s -> "\u02FE " + s).collect(Collectors.joining(Strings.newline));
    }
}

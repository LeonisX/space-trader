package spacetrader.game;

import spacetrader.game.enums.NewsEvent;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.SystemPressure;
import spacetrader.game.quest.containers.NewsContainer;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS;
import static spacetrader.game.quest.enums.EventName.ON_NEWS_ADD_EVENT_ON_ARRIVAL;

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
        Game.getCurrentGame().getQuestSystem().fireEvent(ON_NEWS_ADD_EVENT_ON_ARRIVAL);
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

        NewsContainer newsContainer = new NewsContainer(news);

        for (StarSystem starSystem : Game.getCurrentGame().getUniverse()) {
            if (starSystem.destIsOk() && starSystem != curSys) {
                // Special stories that always get shown: moon, millionaire, shipyard

                newsContainer.setStarSystem(starSystem);

                if (starSystem.getSpecialEventType() == SpecialEventType.Moon) {
                    newsContainer.getNews().add(Strings.NewsMoonForSale);
                }

                if (starSystem.getShipyardId() != ShipyardId.NA) {
                    newsContainer.getNews().add(Strings.NewsShipyard);
                }

                Game.getCurrentGame().getQuestSystem().fireEvent(ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS, newsContainer);

                newsContainer.setNews(newsContainer.getNews().stream()
                        .map(n -> Functions.stringVars(n, starSystem.getName())).collect(Collectors.toList()));

                // And not-always-shown stories
                if (starSystem.getSystemPressure() != SystemPressure.NONE
                        && Functions.getRandom2(100) <= Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10
                        * (5 - Game.getDifficultyId())) {
                    int index = Functions.getRandom2(Strings.NewsPressureExternal.length);
                    String baseStr = Strings.NewsPressureExternal[index];
                    String pressure = Strings.NewsPressureExternalPressures[starSystem.getSystemPressure().castToInt()];
                    newsContainer.getNews().add(Functions.stringVars(baseStr, pressure, starSystem.getName()));
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
                    newsContainer.getNews().add(headlines[index]);
                    shown[index] = true;
                }
            }
        }

        return newsContainer.getNews().stream().map(s -> "\u02FE " + s).collect(Collectors.joining(Strings.newline));
    }
}

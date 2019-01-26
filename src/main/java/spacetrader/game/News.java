package spacetrader.game;

import spacetrader.game.enums.NewsEvent;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.enums.SystemPressure;
import spacetrader.game.quest.containers.NewsContainer;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS;

public class News implements Serializable {

    static final long serialVersionUID = 42541307686008683L;

    private Commander commander;

    private ArrayList<Integer> newsEvents = new ArrayList<>(); // News for current system

    public News(Game game) {
        this.commander = game.getCommander();
    }

    public void addEvent(int newEventId) {
        newsEvents.add(newEventId);
    }

    public void addEvent(NewsEvent newsEvent) {
        newsEvents.add(newsEvent.castToInt());
    }

    void resetEvents() {
        newsEvents.clear();
    }

    String getNewspaperHead() {
        String[] heads = Strings.NewsMastheads[commander.getCurrentSystem().getPoliticalSystemType().castToInt()];
        String head = heads[commander.getCurrentSystem().getId().castToInt() % heads.length];

        return Functions.stringVars(head, commander.getCurrentSystem().getName());
    }

    private String getNewspaperText(Integer newsEventId) {
        return (newsEventId < 1000) ? Strings.NewsEvent[newsEventId] : Game.getCurrentGame().getQuestSystem().getNewsTitle(newsEventId);
    }

    String getNewspapersText() {
        StarSystem curSys = commander.getCurrentSystem();
        List<String> news = new ArrayList<>();

        // We're using the getRandom2 function so that the same number is
        // generated each time for the same "version" of the newspaper. -JAF
        Functions.randSeed(curSys.getId().castToInt(), commander.getDays());

        for (Integer newsEventId : newsEvents) {
            news.add(Functions.stringVars(getNewspaperText(newsEventId), new String[]{commander.getName(),
                    commander.getCurrentSystem().getName(), commander.getShip().getName()}));
        }

        if (curSys.getSystemPressure() != SystemPressure.NONE) {
            news.add(Strings.NewsPressureInternal[curSys.getSystemPressure().castToInt()]);
        }

        if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreVillain) {
            String baseStr = Strings.NewsPoliceRecordPsychopath[Functions
                    .getRandom2(Strings.NewsPoliceRecordPsychopath.length)];
            news.add(Functions.stringVars(baseStr, commander.getName(), curSys.getName()));
        } else if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreHero) {
            String baseStr = Strings.NewsPoliceRecordHero[Functions.getRandom2(Strings.NewsPoliceRecordHero.length)];
            news.add(Functions.stringVars(baseStr, commander.getName(), curSys.getName()));
        }

        // and now, finally, useful news (if any)
        boolean realNews = false;
        NewsContainer newsContainer = new NewsContainer(news);

        for (StarSystem starSystem : Game.getCurrentGame().getUniverse()) {
            if (starSystem.destIsOk() && starSystem != curSys) {

                newsContainer.setStarSystem(starSystem);

                // Special stories that always get shown: moon, millionaire, shipyard
                if (starSystem.getShipyardId() != ShipyardId.NA) {
                    newsContainer.getNews().add(Strings.NewsShipyard);
                }

                Game.getCurrentGame().getQuestSystem().fireEvent(ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS, newsContainer);

                newsContainer.setNews(newsContainer.getNews().stream()
                        .map(n -> Functions.stringVars(n, starSystem.getName())).collect(Collectors.toList()));

                // And not-always-shown stories
                if (starSystem.getSystemPressure() != SystemPressure.NONE
                        && Functions.getRandom2(100) <= Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10
                        * (5 - Game.getCurrentGame().getDifficultyId())) {
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

    public ArrayList<Integer> getNewsEvents() {
        return newsEvents;
    }
}

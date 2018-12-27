package spacetrader.game.quest.containers;

import spacetrader.game.StarSystem;

import java.util.List;

public class NewsContainer {

    private List<String> news;
    private StarSystem starSystem;

    public NewsContainer(List<String> news) {
        this.news = news;
    }

    public List<String> getNews() {
        return news;
    }

    public void setNews(List<String> news) {
        this.news = news;
    }

    public StarSystem getStarSystem() {
        return starSystem;
    }

    public void setStarSystem(StarSystem starSystem) {
        this.starSystem = starSystem;
    }
}

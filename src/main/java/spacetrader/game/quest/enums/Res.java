package spacetrader.game.quest.enums;

public enum Res {

    Phases("Quest phases", "Phase"),
    Quests("Quest clues", "Quest"),
    Alerts("Alerts", "Alert"),
    News("News", "News"),
    Encounters("Encounters", "Encounter"),
    VeryRareEncounters("VeryRareEncounters", "VeryRareEncounter"),
    GameEndings("Game Endings", "GameEnding"),
    CrewNames("Crew Member Names", "CrewName"),
    SpecialCargo("Special Cargo Titles", "SpecialCargo"),
    CheatTitles("Cheat Titles", "Cheat");

    private String title;
    private String prefix;

    Res(String title, String prefix) {
        this.title = title;
        this.prefix = prefix;
    }

    public String getTitle() {
        return title;
    }

    public String getPrefix() {
        return prefix;
    }
}

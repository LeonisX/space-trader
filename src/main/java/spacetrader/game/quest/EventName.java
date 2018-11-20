package spacetrader.game.quest;

public enum EventName {

    AFTER_GAME_INITIALIZE,          // After full game initialization
    BEFORE_SPECIAL_BUTTON_SHOW,     // Before show Special button on System Info Panel
    SPECIAL_BUTTON_CLICKED,          // On Special button clicked
    ARRESTED,
    ESCAPE_WITH_POD,
    ON_GENERATE_CREW_MEMBER_LIST,
    ON_INCREMENT_DAYS,
    IS_CONSIDER_CHEAT, ON_DISPLAY_SPECIAL_CARGO, ON_GET_QUESTS_STRINGS, ON_ASSIGN_EVENTS_RANDOMLY, ON_ASSIGN_EVENTS_MANUAL, ON_NEWS_ADD_EVENT_ON_ARRIVAL
}

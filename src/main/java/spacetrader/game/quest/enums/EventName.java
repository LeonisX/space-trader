package spacetrader.game.quest.enums;

public enum EventName {

    // Initialization (Game Constructor)
    // Create QuestSystem
    ON_AFTER_GENERATE_UNIVERSE,                 // Princess.    Galvon must be a Monarchy.
    ON_ASSIGN_SYSTEM_EVENTS_MANUAL,             // Very Common. Manual assigns an event to a specific system.
    ON_ASSIGN_SYSTEM_CLOSEST_EVENTS_RANDOMLY,   // Common.      Assign an event at a distance of no more than 70 parsec
    ON_ASSIGN_SYSTEM_EVENTS_RANDOMLY,           // Very Common. Randomly assigns an event to a specific system. For example - start of event
    ON_GENERATE_CREW_MEMBER_LIST,               // Common.      Adds quest characters to the game.
    ON_AFTER_SHIP_SPECS_INITIALIZED,            // Common.      Supplements the list of ship specs. Including quest.
    ON_AFTER_CREATE_SHIP,                       // Common.      Unique quest ships are created at the beginning of the game.
    ON_AFTER_GAME_INITIALIZE,                   // Lottery.     Right after full game initialization. Add current system ID to quest

    // Main Flow. Special Button
    ON_BEFORE_SPECIAL_BUTTON_SHOW,              // All.         Before show Special button on System Info Panel
    ON_SPECIAL_BUTTON_CLICKED,                  // All.         On Special button clicked
    ON_SPECIAL_BUTTON_CLICKED_IS_CONFLICT,      // Wild.        Resolve potential conflict with other quests
    ON_AFTER_NEW_QUEST_STARTED,                 // Wild.        React on new quests (sculpture)

    // Status Windows
    ON_GET_QUESTS_STRINGS,                      // Any.         FormViewQuests. Display quests.
    ON_DISPLAY_SPECIAL_CARGO,                   // Common.      FormViewShip. Display special cargo (Sculpture, Reactor, Tribbles, ...)
    ON_DISPLAY_SHIP_EQUIPMENT,                  // Scarab.      FormViewShip. Show hardened hull
    ON_FORM_SHIP_LIST_SHOW,                     // Tribbles.    FormShipList. Show warning if Tribbles on board.

    //Ship cargo and worth
    IS_TRADE_SHIP,                              // Reactor. Before you sell / exchange ship
    ON_GET_BASE_WORTH,                          // Tribbles.    FormViewBank, Insurance, Escape With Pod, Arrested. Reduce the cost of the ship under certain conditions
    ON_GET_WORTH,                               // Moon.        Add Moon price to Commander worth. Common use.
    ON_GET_FILLED_CARGO_BAYS,                   // Reactor, Japori. Some goods take place in cargo bays


    ON_BEFORE_WARP,                             // Wild. It is possible to cancel the warp
    IS_TRAVEL,                                  // Experiment.
    ON_DETERMINE_RANDOM_ENCOUNTER,              // Wild. The ability to influence the generation of encounters. For example, add hordes of police
    ON_DETERMINE_VERY_RARE_ENCOUNTER,           // Very rare encounters
    ON_BEFORE_ENCOUNTER_GENERATE_OPPONENT,      // Wild. Ability to adjust the characteristics of the first member of the crew of the opponent ship
    ON_GENERATE_OPPONENT_SHIP_POLICE_TRIES,     // Wild. The police will try to hunt you down with better ships if you are
    // a villain, and they will try even harder when you are considered to be a psychopath (or are transporting Jonathan Wild)


    //TODO correct order of very rare encounters
    // Encounters
    ENCOUNTER_DETERMINE_NON_RANDOM_ENCOUNTER,   // Princess, Space Monster. Starts a predefined encounter with Scorpion.
    ON_ENCOUNTER_GENERATE_OPPONENT,
    ENCOUNTER_VERIFY_ATTACK,                    // Princess (Scorpion). Check before starting the attack. You can not attack Scorpion, if there are no disruptors on board
    ENCOUNTER_VERIFY_BOARD,
    ENCOUNTER_VERIFY_BRIBE,
    ENCOUNTER_VERIFY_FLEE,
    ENCOUNTER_GET_INTRODUCTORY_TEXT,            // Princess (Scorpion), Space Monster. Used to generate an introductory text of encounter
    ENCOUNTER_GET_INTRODUCTORY_ACTION,          //
    ENCOUNTER_GET_ENCOUNTER_SHIP_TEXT,          // Very rare encounters
    ENCOUNTER_GET_IMAGE_INDEX,                  // Very rare encounters, Space Monster - before opponent show
    ENCOUNTER_SHOW_ACTION_BUTTONS,              // All quest enemy ships
    ENCOUNTER_IS_EXECUTE_ATTACK_GET_WEAPONS,    // Scarab
    ENCOUNTER_IS_EXECUTE_ATTACK_PRIMARY_DAMAGE, // Reactor. Reactor affects damage levels
    ENCOUNTER_IS_EXECUTE_ATTACK_SECONDARY_DAMAGE,//Scarab
    ENCOUNTER_IS_DISABLEABLE,                   // Space Monster. Don't allow to disable
    ENCOUNTER_EXECUTE_ATTACK_KEEP_SPECIAL_SHIP, // Princess (Scorpion). The action that occurs after processing the effects of an attack. It is necessary that Scorpion was not accidentally destroyed.
    ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, // Princess (Scorpion). Action that occurs when an opponent is disabled
    ENCOUNTER_ON_VERIFY_SURRENDER,              // Princess (Scorpion), Artifact. Check whether there is any possibility to surrender. For example, if the Princess is on board, you cannot surrender.
    ENCOUNTER_ON_SURRENDER_IF_RAIDED,           // Wild. Ability to change the situation with robbery
    ENCOUNTER_ON_ROBBERY,                       // Princess (Scorpion), Sculpture, Reactor. Used to generate a list of items that were hidden in secret cargo bays.
    ENCOUNTER_GET_STEALABLE_CARGO,              // Princess (Scorpion), Sculpture. Used to generate a list of goods that will be stolen during an attack. Princess will take one cargo bay
    ENCOUNTER_ON_TRIBBLE_PICTURE_CLICK,         // Tribbles. On Tribble picture click
    ENCOUNTER_ON_ENCOUNTER_WON,                 // Space Monster. After winning in encounter

    // After Encounters
    IS_ILLEGAL_SPECIAL_CARGO,                   // Wild, Sculpture, Reactor. Determine whether there is an illegal special cargo on board
    ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS,       // Wild, Sculpture, Reactor. Text. An act committed because of an illegal special cargo (for example, arresting Wild).
    ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION,   // Wild, Sculpture, Reactor. Get illegal special cargo description
    ON_BEFORE_ARRESTED_CALCULATE_FINE,          // Wild. Correction of fine depending on some conditions
    ON_ARRESTED,                                // Jarek, Princess, ... Action that occurs when a player is arrested. He loses a lot.
    ON_ARRESTED_AND_SHIP_SOLD_FOR_DEBT,         // Tribbles. Upon arrest, if there is no money to pay the fine - the ship is sold for debt
    ON_ESCAPE_WITH_POD,                         // Jarek, Princess, ... Action that occurs when a player escapes with pod. He loses a lot.
    ON_INCREMENT_DAYS,                          // Jarek, Princess, ... The action that occurs after a certain number of days. For example, a passenger loses patience, or something explodes.
    ON_ARRIVAL,                                 // Reactor. Check in after arrival. For example, update the state of the reactor
    ON_NEWS_ADD_EVENT_ON_ARRIVAL,               // Jarek, Princess, .... Adds special news on arrival at the spaceport.
    ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS,     // Tribbles. Add news from nearest systems

    // Game ending
    ON_BEFORE_KILLED,                           // Reactor. Print a message before the ship is killed in combat.
    ON_BEFORE_GAME_END,                         // Princess. An event that occurs before the end of the game. For example, setting a special end game status
    ON_GAME_END_ALERT,                          // Princess. Logic to display the end of the game window (with a picture) for quests
    ON_GET_GAME_SCORE,                          // Princess. Used to calculate the final score in the game.

    // Cheats
    IS_CONSIDER_CHEAT,                          // Tribbles. General cheats
    IS_CONSIDER_STATUS_CHEAT,                   // Jarek, Princess, ... Change the status of the quest (for example, Jarek questStatus)
    ENCOUNTER_UPDATE_ENCOUNTER_TYPE, ENCOUNTER_MEET, ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, ENCOUNTER_GET_EXECUTE_ACTION_OPPONENT_FLEEING,
    ENCOUNTER_DRINK, ENCOUNTER_VERIFY_YIELD, IS_CONSIDER_STATUS_DEFAULT_CHEAT            // Jarek, Princess, ... See the status of all quests
}

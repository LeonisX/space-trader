package spacetrader.game.quest;

import spacetrader.game.GlobalAssets;
import spacetrader.game.Strings;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.stub.StringsBundle;
import spacetrader.util.Functions;

import java.util.stream.Stream;

enum Res {

    Phases("Quest phases", "Phase"),
    Quests("Quest clues", "Quest"),
    Alerts("Alerts", "Alert"),
    News("News", "News"),
    Encounters("Encounters", "Encounter"),
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

class I18n {

    static void echoQuestName(Class<? extends Quest> aClass) {
        System.out.println(String.format("\n\n## %s", splitCamelCase(aClass.getSimpleName())));
    }

    static void dumpPhases(Stream<SimpleValueEnum<QuestDialog>> phases) {

        System.out.println(String.format("\n# %s:", Res.Phases.getTitle()));
        phases.forEach(phase -> {
            System.out.println(getHeadTitleValue(Res.Phases.getPrefix(), phase.name(), phase.getValue().getTitle()));
            System.out.println(getHeadMessageValue(Res.Phases.getPrefix(), phase.name(), phase.getValue().getMessage()));
        });
    }

    static void dumpStrings(Res resource, Stream<SimpleValueEnum<String>> values) {
        System.out.println(String.format("\n# %s:", resource.getTitle()));
        values.forEach(pair -> System.out.println(getHead(resource.getPrefix(), pair.name()) + "=" + pair.getValue()));
    }

    static void dumpAlerts(Stream<SimpleValueEnum<AlertDialog>> alerts) {
        System.out.println(String.format("\n# %s:", Res.Alerts.getTitle()));
        alerts.forEach(alert -> {
            System.out.println(getHeadTitleValue(Res.Alerts.getPrefix(), alert.name(), alert.getValue().getTitle()));
            System.out.println(getHeadMessageValue(Res.Alerts.getPrefix(), alert.name(), alert.getValue().getMessage()));
            if (alert.getValue().getAccept() != null) {
                System.out.println(getHeadAcceptValue(Res.Alerts.getPrefix(), alert.name(), alert.getValue().getAccept()));
            }
        });
    }

    static void localizePhases(Stream<SimpleValueEnum<QuestDialog>> phases) {
        StringsBundle strings = GlobalAssets.getStrings();
        phases.forEach(p -> p.setValue(new QuestDialog(p.getValue().getPrice(), p.getValue().getMessageType(),
                strings.get(getHeadTitle(Res.Phases.getPrefix(), p.name())), strings.get(getHeadMessage(Res.Phases.getPrefix(), p.name())))));
    }

    static void localizeAlerts(Stream<SimpleValueEnum<AlertDialog>> alerts) {
        StringsBundle strings = GlobalAssets.getStrings();
        alerts.forEach(alert -> alert.setValue(new AlertDialog(
                strings.get(getHeadTitle(Res.Alerts.getPrefix(), alert.name())),
                strings.get(getHeadMessage(Res.Alerts.getPrefix(), alert.name())),
                strings.get(getHeadAccept(Res.Alerts.getPrefix(), alert.name()))))
        );
    }

    static void localizeStrings(Res resource, Stream<SimpleValueEnum<String>> values) {
        StringsBundle strings = GlobalAssets.getStrings();
        values.forEach(v -> v.setValue(Functions.detectPlural(Strings.pluralMap, strings.get(getHead(resource.getPrefix(), v.name())))));
    }

    private static String getHeadTitle(String prefix, String key) {
        return getHead(prefix, key, "Title");
    }

    private static String getHeadTitleValue(String prefix, String key, String value) {
        return getHeadTitle(prefix, key) + "=" + value;
    }

    private static String getHeadMessage(String prefix, String key) {
        return getHead(prefix, key, "Message");
    }

    private static String getHeadMessageValue(String prefix, String key, String value) {
        return getHeadMessage(prefix, key) + "=" + value;
    }

    private static String getHeadAccept(String prefix, String key) {
        return getHead(prefix, key, "Accept");
    }

    private static String getHeadAcceptValue(String prefix, String key, String value) {
        return getHeadAccept(prefix, key) + "=" + value;
    }

    private static String getHead(String prefix, String key, String tail) {
        return prefix + key + tail;
    }

    private static String getHead(String prefix, String key) {
        return prefix + key;
    }

    private static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                )," ");
    }
}

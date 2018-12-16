package spacetrader.game.quest;

import spacetrader.game.GlobalAssets;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.game.quest.enums.SimpleValueEnumWithPhase;
import spacetrader.stub.StringsBundle;

import java.util.stream.Stream;

public class I18n {

    static void dumpPhase(String name, String prefix, Stream<SimpleValueEnumWithPhase<QuestDialog>> phases) {
        System.out.println(String.format("\n# %s:", name));
        phases.forEach(phase -> {
            System.out.println(getDumpHeadTitleValue(prefix, phase.name(), phase.getValue().getTitle()));
            System.out.println(getDumpHeadMessageValue(prefix, phase.name(), phase.getValue().getMessage()));
        });
    }

    static String getDumpHeadTitleValue(String prefix, String key, String value) {
        return getDumpHeadTitle(prefix, key) + "=" + value;
    }

    static String getDumpHeadTitle(String prefix, String key) {
        return getDumpHead(prefix, key, "Title");
    }

    static String getDumpHeadMessageValue(String prefix, String key, String value) {
        return getDumpHeadMessage(prefix, key) + "=" + value;
    }

    static String getDumpHeadMessage(String prefix, String key) {
        return getDumpHead(prefix, key, "Message");
    }

    static String getDumpHead(String prefix, String key, String tail) {
        return prefix + key + tail;
    }

    static String getDumpHead(String prefix, String key) {
        return prefix + key;
    }

    static void localizePhase(String prefix, Stream<SimpleValueEnumWithPhase<QuestDialog>> phases) {
        StringsBundle strings = GlobalAssets.getStrings();
        phases.forEach(p -> p.setValue(new QuestDialog(p.getValue().getMessageType(),
                strings.get(getDumpHeadTitle(prefix, p.name())), strings.get(getDumpHeadMessage(prefix, p.name())))));
    }

    static void dumpHeadString(String name, String prefix, Stream<SimpleValueEnum<String>> values) {
        System.out.println(String.format("\n# %s:", name));
        values.forEach(pair -> System.out.println(getDumpHead(prefix, pair.name()) + "=" + pair.getValue()));
    }

    static void localizeHeadString(String prefix, Stream<SimpleValueEnum<String>> values) {
        StringsBundle strings = GlobalAssets.getStrings();
        values.forEach(v -> v.setValue(strings.get(getDumpHead(prefix, v.name()))));
    }

}



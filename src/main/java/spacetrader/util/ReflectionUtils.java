package spacetrader.util;

import spacetrader.controls.BaseComponent;
import spacetrader.controls.IName;
import spacetrader.game.GlobalAssets;
import spacetrader.game.Strings;
import spacetrader.stub.StringsBundle;
import spacetrader.stub.ValuesBundle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

public class ReflectionUtils {

    private static final String PLURAL_MAP = "pluralMap";

    public static void dumpStrings() {
        Strings strings = new Strings();
        try {
            Map<String, Object> fieldNamesAndValues = getFieldNamesAndValues(strings);
            fieldNamesAndValues.remove(PLURAL_MAP);
            fieldNamesAndValues.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> {
                String name = entry.getKey();
                Object object = entry.getValue();
                if (object instanceof String) {
                    System.out.println(name + "=" + object);
                } else if (object instanceof String[]) {
                    String[] array = (String[]) object;
                    for (int i = 0; i < array.length; i++) {
                        System.out.println(name + "[" + i + "]=" + array[i]);
                    }
                } else if (object instanceof String[][]) {
                    String[][] array = (String[][]) object;
                    for (int j = 0; j < array.length; j++) {
                        for (int i = 0; i < array[j].length; i++) {
                            System.out.println(name + "[" + j + "]" + "[" + i + "]=" + array[j][i]);
                        }
                    }
                } else {
                    throw new IllegalStateException("Unsupported class: " + object.getClass());
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static void loadStrings(StringsBundle stringsBundle) {
        Strings strings = new Strings();
        Map<String, String> pluralMap = new HashMap<>();
        try {
            Map<String, Object> fieldNamesAndValues = getFieldNamesAndValues(strings);
            fieldNamesAndValues.remove(PLURAL_MAP);
            fieldNamesAndValues.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> {
                String name = entry.getKey();
                Object object = entry.getValue();
                try {
                    Field field = (strings).getClass().getField(name);
                    if (object instanceof String) {
                        String value = stringsBundle.getString(name);
                        if (value != null) {
                            field.set(strings, detectPlural(value, pluralMap));
                        }
                    } else if (object instanceof String[]) {
                        String[] array = (String[]) object;
                        for (int i = 0; i < array.length; i++) {
                            String fieldName = name + "[" + i + "]";
                            String value = stringsBundle.getString(fieldName);
                            if (value != null) {
                                array[i] = detectPlural(value, pluralMap);
                            }
                        }
                    } else if (object instanceof String[][]) {
                        String[][] array = (String[][]) object;
                        for (int j = 0; j < array.length; j++) {
                            for (int i = 0; i < array[j].length; i++) {
                                String fieldName = name + "[" + j + "]" + "[" + i + "]";
                                String value = stringsBundle.getString(fieldName);
                                if (value != null) {
                                    array[j][i] = detectPlural(value, pluralMap);
                                }
                            }
                        }
                    } else {
                        throw new IllegalStateException("Unsupported class: " + object.getClass());
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            ((strings).getClass().getField(PLURAL_MAP)).set(strings, pluralMap);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static String detectPlural(String string, Map<String, String> pluralMap) {
        if (string.isEmpty()) {
            return string;
        }
        List<String> chunks = Functions.splitString(string);
        String singular = chunks.remove(0);
        for (int i = 0; i < chunks.size(); i++) {
            pluralMap.put(singular + (i + 2), chunks.get(i));
        }
        return singular;
    }

    public static void setAllComponentNames(Object obj) {
        try {
            getFieldNamesAndValues(obj).forEach((name, object) -> {
                if (object instanceof IName) {
                    IName iName = (IName) object;
                    if (null == iName.getName()) {
                        iName.setName(name);
                        setAllComponentNames(object);
                    }
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> getFieldNamesAndValues(final Object obj)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> c1 = obj.getClass();
        Map<String, Object> map = new HashMap<>();
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = field.get(obj);
            map.put(name, value);
        }
        return map;
    }

    public static void dumpControlsDimensions(Component component, String prefix) {
        component.getSize();
        component.getLocation();

        System.out.println(formatPropertyName(prefix) + ".x=" + component.getX());
        System.out.println(formatPropertyName(prefix) + ".y=" + component.getY());
        System.out.println(formatPropertyName(prefix) + ".width=" + component.getWidth());
        System.out.println(formatPropertyName(prefix) + ".height=" + component.getHeight());

        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                dumpControlsDimensions(child, prefix + "." + name);
            }
        }
    }

    private static String formatPropertyName(String prefix) {
        return prefix.replace(".JRootPane", "")
                .replace(".null.glassPane", "")
                .replace(".null.layeredPane", "")
                .replace(".null.contentPane", "")
                .replace(".null", "")
                .replace(".WinformJPanel", "");
    }

    public static void dumpControlsStrings(Component component, String prefix) {
        if (component instanceof AbstractButton) {
            print(formatPropertyName(prefix) + ".text", ((AbstractButton) component).getText());
        }
        if (component instanceof JLabel) {
            print(formatPropertyName(prefix) + ".text", ((JLabel) component).getText());
        }
        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder) {
            print(formatPropertyName(prefix) + ".title", ((TitledBorder) ((JPanel) component).getBorder()).getTitle());
        }
        if (component instanceof JFrame) {
            print(formatPropertyName(prefix) + ".title", ((JFrame) component).getTitle());
        }
        if (component instanceof JDialog) {
            print(formatPropertyName(prefix) + ".title", ((Dialog) component).getTitle());
        }

        if (component instanceof JMenu) {
            for (Component child : ((JMenu) component).getMenuComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                dumpControlsStrings(child, prefix + "." + name);
            }
        }

        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                dumpControlsStrings(child, prefix + "." + name);
            }
        }
    }

    private static void print(String key, String value) {
        value = (value == null) ? "" : value.replace("<HTML>", "").replace("</HTML>", "");
        if (!value.isEmpty() && !key.contains(".buyButton") && !key.contains(".sellButton") && !key.contains("LabelValue")) {
            System.out.println(key + "=" + value);
        } else {
            System.out.println("# " + key + "=" + value);
        }
    }

    public static void loadControlsData(BaseComponent component) {
        loadControlsDimensions(component);
        loadControlsStrings(component);
    }

    private static void loadControlsDimensions(BaseComponent component) {
        loadControlsDimensions(component.asSwingObject(), component.getName(), GlobalAssets.getDimensions());
    }

    private static void loadControlsDimensions(Component component, String prefix, ValuesBundle valuesBundle) {
        prefix = formatPropertyName(prefix);
        if (component.getName() != null && !component.getName().startsWith("null.")) {
            double scale = 1.0;
            //TODO delete
            if (valuesBundle.get(prefix + ".width") != null) {
                Dimension dimension = valuesBundle.getSize(prefix);
                dimension.setSize(dimension.getWidth() * scale, dimension.getHeight() * scale);
                component.setSize(dimension);
                if (!(component instanceof JFrame)) {
                    Point point = valuesBundle.getLocation(prefix);
                    point.setLocation(point.getX() * scale, point.getY() * scale);
                    component.setLocation(point);
                }
            } else {
                //System.out.println("!!!Control without dimensions: " + prefix);
            }
        }
        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                loadControlsDimensions(child, prefix + "." + name, valuesBundle);
            }
        }
    }

    private static void loadControlsStrings(BaseComponent component) {
        loadControlsStrings(component.asSwingObject(), component.getName(), GlobalAssets.getStrings());
    }

    public static void loadControlsStrings(Component component, String prefix, StringsBundle stringsBundle) {
        prefix = formatPropertyName(prefix);
        String title = stringsBundle.getTitle(prefix);
        String text = stringsBundle.getText(prefix);
        if (component instanceof JFrame && title != null && !title.isEmpty()) {
            ((JFrame) component).setTitle(title);
        }

        if (component instanceof AbstractButton && text != null && !text.isEmpty()) {
            ((AbstractButton) component).setText(text);
            resizeIfNeed(((AbstractButton) component));
        }

        if (component instanceof JLabel && text != null && !text.isEmpty()) {
            JLabel jLabel = (JLabel) component;
            getBaseComponent(jLabel).ifPresent(label -> ((spacetrader.controls.Label) label).setText(text));
            resizeIfNeed(((JLabel) component));
        }

        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder
                && title != null && !title.isEmpty()) {
            ((TitledBorder) ((JPanel) component).getBorder()).setTitle(title);
            resizeIfNeed(((JPanel) component));
        }

        if (component instanceof JDialog && title != null && !title.isEmpty()) {
            ((JDialog) component).setTitle(title);
        }

        if (component instanceof JMenu) {
            for (Component child : ((JMenu) component).getMenuComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                loadControlsStrings(child, prefix + "." + name, stringsBundle);
            }
        }

        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                loadControlsStrings(child, prefix + "." + name, stringsBundle);
            }
        }
    }

    private static void resizeIfNeed(JComponent component) {
        getBaseComponent(component).ifPresent(bc ->
                spacetrader.controls.Graphics.resizeIfNeed(bc.asSwingObject(), bc.isAutoSize(),
                        bc.isAutoWidth(), bc.isAutoHeight(), bc.getControlBinding())
        );
    }

    private static Optional<BaseComponent> getBaseComponent(JComponent component) {
        return Optional.ofNullable(component.getClientProperty("baseComponent")).map(o -> (BaseComponent) o);
    }


/*
    //TODO delete
    public static void dumpAllAlertStrings() {
        Arrays.stream(AlertType.values()).forEach(alertType -> {
            FormAlert formAlert = FormAlert.makeDialog(alertType, new String[]{});
            List<String> stringsList = new ArrayList<>();
            dumpControlsStrings2(formAlert.asSwingObject(), alertType.toString(), stringsList);
            // title, message, ok, cancel
            print3(stringsList.stream().filter(s -> s.contains("Title=\"")).findFirst().orElse(""));
            print3(stringsList.stream().filter(s -> s.contains("Message=\"")).findFirst().orElse(""));
            print3(stringsList.stream().filter(s -> s.contains("Accept=\"")).findFirst().orElse(""));
            print3(stringsList.stream().filter(s -> s.contains("cancel=\"")).findFirst().orElse(""));
        });
        System.out.println();
        System.out.println(method);
    }

    private static void print3(String value) {
        if (!value.isEmpty()) {
            System.out.print("\t\t");
            System.out.println(value);
        }
    }

    //TODO delete
    private static void dumpControlsStrings2(Component component, String prefix, List<String> stringsList) {
        if (component instanceof AbstractButton) {
            print2(formatPropertyName(prefix), ((AbstractButton) component).getText(), stringsList);
        }
        if (component instanceof JLabel) {
            print2(formatPropertyName(prefix), ((JLabel) component).getText(), stringsList);
        }
        if (component instanceof JDialog) {
            print2(formatPropertyName(prefix) + component.getName(), ((Dialog) component).getTitle(), stringsList);
        }
        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder) {
            print2(formatPropertyName(prefix) + component.getName(), ((TitledBorder) ((JPanel) component).getBorder()).getTitle(), stringsList);
        }
        if (component instanceof JFrame) {
            print2(formatPropertyName(prefix) + component.getName(), ((JFrame) component).getTitle(), stringsList);
        }

        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = *//*child.getName() == null ? child.getClass().getSimpleName() :*//* child.getName();
                dumpControlsStrings2(child, prefix + "." + name, stringsList);
            }
        }
    }

    //TODO delete
    private static void print2(String key, String value, List<String> stringsList) {
        String varName = "Alerts" + key.replace(".", "");
        value = value.replace("<HTML>", "").replace("</HTML>", "");
        String varValue = "\"" + value + "\"";
        if (value.equals("Ok")) {
            varName = "AlertsOk";
        }
        if (value.equals("No")) {
            varName = "AlertsNo";
        }
        if (value.equals("Yes")) {
            varName = "AlertsYes";
        }
        if (value.equals("cancel")) {
            varName = "AlertsCancel";
        }
            method = method.replace(varValue, varName);
            String result = ("public static String " + varName + "=\"" + value.replace("\"", "\\\"") + "\";");
            if (!value.isEmpty()) {
                stringsList.add(result);
            }
    }

    private static String method = "\n" +
            "    public static FormAlert makeDialog(AlertType type, String[] args) {\n" +
            "        switch (type) {\n" +
            "            case Alert:\n" +
            "                return (new FormAlert(\"^1\", \"^2\", \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case AntidoteOnBoard:\n" +
            "                return (new FormAlert(\"Antidote\", \"Ten of your cargo bays now contain antidote for the Japori system.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case AntidoteDestroyed:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Antidote Destroyed\",\n" +
            "                        \"The antidote for the Japori system has been destroyed with your ship. You should return to ^1 and get some more.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case AntidoteTaken:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Antidote Taken\",\n" +
            "                        \"The Space Corps removed the antidote for Japori from your ship and delivered it, fulfilling your assignment.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case AppStart:\n" +
            "                return (new FormAlert(\"Space Trader for Windows\", SPLASH_INDEX));\n" +
            "            case ArrivalBuyNewspaper:\n" +
            "                return (new FormAlert(\"Buy Newspaper?\", \"The local newspaper costs ^1. Do you wish to buy a copy?\",\n" +
            "                        \"Buy Newspaper\", DialogResult.YES, \"cancel\", DialogResult.NO, args));\n" +
            "            case ArrivalIFFuel:\n" +
            "                return (new FormAlert(\"No Full Tanks\", \"You do not have enough money to buy full tanks.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ArrivalIFFuelRepairs:\n" +
            "                return (new FormAlert(\"Not Enough Money\",\n" +
            "                        \"You don't have enough money to get a full tank or full hull repairs.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case ArrivalIFNewspaper:\n" +
            "                return (new FormAlert(\"Can't Afford it!\",\n" +
            "                        \"Sorry! A newspaper costs ^1 in this system. You don't have enough money!\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case ArrivalIFRepairs:\n" +
            "                return (new FormAlert(\"No Full Repairs\", \"You don't have enough money to get your hull fully repaired.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ArtifactLost:\n" +
            "                return (new FormAlert(\"Artifact Lost\", \"The alien artifact has been lost in the wreckage of your ship.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ArtifactRelinquished:\n" +
            "                return (new FormAlert(\"Artifact Relinquished\", \"The aliens take the artifact from you.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case CargoIF:\n" +
            "                return (new FormAlert(\"Not Enough Money\", \"You don't have enough money to spend on any of these goods.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case CargoNoEmptyBays:\n" +
            "                return (new FormAlert(\"No Empty Bays\", \"You don't have any empty cargo holds available at the moment\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case CargoNoneAvailable:\n" +
            "                return (new FormAlert(\"Nothing Available\", \"None of these goods are available.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case CargoNoneToSell:\n" +
            "                return (new FormAlert(\"None To ^1\", \"You have none of these goods in your cargo bays.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case CargoNotInterested:\n" +
            "                return (new FormAlert(\"Not Interested\", \"Nobody in this system is interested in buying these goods.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case CargoNotSold:\n" +
            "                return (new FormAlert(\"Not Available\", \"That item is not available in this system.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case ChartJump:\n" +
            "                return (new FormAlert(\"Use Singularity?\",\n" +
            "                        \"Do you wish to use the Portable Singularity to transport immediately to ^1?\", \"Use Singularity\",\n" +
            "                        DialogResult.YES, \"Don't use it\", DialogResult.NO, args));\n" +
            "            case ChartJumpCurrent:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Cannot Jump\",\n" +
            "                        \"You are tracking the system where you are currently located. It's useless to jump to your current location.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ChartJumpNoSystemSelected:\n" +
            "                return (new FormAlert(\"No System Selected\",\n" +
            "                        \"To use the Portable Singularity, track a system before clicking on this button.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ChartTrackSystem:\n" +
            "                return (new FormAlert(\"Track System?\", \"^1?\", \"Yes\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case ChartWormholeUnreachable:\n" +
            "                return (new FormAlert(\"Wormhole Unreachable\", \"The wormhole to ^1 is only accessible from ^2.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case Cheater:\n" +
            "                return (new FormAlert(\"Cheater!\", \"Cheaters never prosper!  (Well, not with that command, anyway.)\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case CrewFireMercenary:\n" +
            "                return (new FormAlert(\"Fire Mercenary\", \"Are you sure you wish to fire ^1?\", \"Yes\", DialogResult.YES, \"No\",\n" +
            "                        DialogResult.NO, args));\n" +
            "            case CrewNoQuarters:\n" +
            "                return (new FormAlert(\"No Quarters Available\", \"You do not have any crew quarters available for ^1.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case DebtNoBuy:\n" +
            "                return (new FormAlert(\"You Have A Debt\", \"You can't buy that as long as you have debts.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case DebtNone:\n" +
            "                return (new FormAlert(\"No Debt\", \"You have no debts.\", \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case DebtReminder:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Loan Notification\",\n" +
            "                        \"The Bank's  Loan Officer reminds you that your debt continues to accrue interest. You currently owe ^1.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case DebtTooLargeGrounded:\n" +
            "                return (new FormAlert(\"Large Debt\",\n" +
            "                        \"Your debt is too large.  You are not allowed to leave this system until your debt is lowered.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case DebtTooLargeLoan:\n" +
            "                return (new FormAlert(\"Debt Too High\", \"Your debt is too high to get another loan.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case DebtTooLargeTrade:\n" +
            "                return (new FormAlert(\"Large Debt\", \"Your debt is too large.  Nobody will trade with you.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case DebtWarning:\n" +
            "                return (new FormAlert(\"Warning: Large Debt\",\n" +
            "                        \"Your debt is getting too large. Reduce it quickly or your ship will be put on a chain!\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case Egg:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Egg\",\n" +
            "                        \"Congratulations! An eccentric Easter Bunny decides to exchange your trade goods for a special present!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterAliensSurrender:\n" +
            "                return (new FormAlert(\"Surrender\",\n" +
            "                        \"If you surrender to the aliens, they will take the artifact. Are you sure you wish to do that?\",\n" +
            "                        \"Yes\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterArrested:\n" +
            "                return (new FormAlert(\"Arrested\",\n" +
            "                        \"You are arrested and taken to the space station, where you are brought before a court of law.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterAttackCaptain:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Really Attack?\",\n" +
            "                        \"Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?\",\n" +
            "                        \"Really Attack\", DialogResult.YES, \"OK, I Won't\", DialogResult.NO, args));\n" +
            "            case EncounterAttackNoDisruptors:\n" +
            "                return (new FormAlert(\n" +
            "                        \"No Disabling Weapons\",\n" +
            "                        \"You have no disabling weapons! You would only be able to destroy your opponent, which would defeat the purpose of your quest.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterAttackNoLasers:\n" +
            "                return (new FormAlert(\"No Hull-Damaging Weapons\",\n" +
            "                        \"You only have disabling weapons, but your opponent cannot be disabled!\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case EncounterAttackNoWeapons:\n" +
            "                return (new FormAlert(\"No Weapons\", \"You can't attack without weapons!\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EncounterAttackPolice:\n" +
            "                return (new FormAlert(\"Attack Police\",\n" +
            "                        \"Are you sure you wish to attack the police? This will turn you into a criminal!\", \"Yes\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterAttackTrader:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Attack Trader\",\n" +
            "                        \"Are you sure you wish to attack the trader? This will immediately set your police record to dubious!\",\n" +
            "                        \"Yes\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterBothDestroyed:\n" +
            "                return (new FormAlert(\"Both Destroyed\", \"You and your opponent have managed to destroy each other.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterDisabledOpponent:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Opponent Disabled\",\n" +
            "                        \"You have disabled your opponent. Without life support they'll have to hibernate. You notify Space Corps, and they come and tow the ^1 to the planet, where the crew is revived and then arrested. ^2\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterDrinkContents:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Drink Contents?\",\n" +
            "                        \"You have come across an extremely rare bottle of Captain Marmoset's Amazing Skill Tonic! The \\\"use-by\\\" date is illegible, but might still be good.  Would you like to drink it?\",\n" +
            "                        \"Yes, Drink It\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterDumpAll:\n" +
            "                return (new FormAlert(\"Dump All?\",\n" +
            "                        \"You paid ^1 credits for these items. Are you sure you want to just dump them?\", \"Yes\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterDumpWarning:\n" +
            "                if (Game.getCurrentGame() != null) {\n" +
            "                    Game.getCurrentGame().setLitterWarning(true);\n" +
            "                }\n" +
            "                return (new FormAlert(\n" +
            "                        \"Space Littering\",\n" +
            "                        \"Dumping cargo in space is considered littering. If the police find your dumped goods and track them to you, this will influence your record. Do you really wish to dump?\",\n" +
            "                        \"Yes\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterEscaped:\n" +
            "                return (new FormAlert(\"Escaped\", \"You have managed to escape your opponent.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EncounterEscapedHit:\n" +
            "                return (new FormAlert(\"You Escaped\", \"You got hit, but still managed to escape.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case EncounterEscapePodActivated:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Escape Pod Activated\",\n" +
            "                        \"Just before the final demise of your ship, your escape pod gets activated and ejects you. After a few days, the Space Corps picks you up and drops you off at a nearby space port.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterLooting:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Looting\",\n" +
            "                        \"The pirates board your ship and transfer as much of your cargo to their own ship as their cargo bays can hold.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterMarieCeleste:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Engage Marie Celeste\",\n" +
            "                        \"The ship is empty: there is nothing in the ship's log, but the crew has vanished, leaving food on the tables and cargo in the holds. Do you wish to offload the cargo to your own holds? \",\n" +
            "                        \"Yes, Take Cargo\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterMarieCelesteNoBribe:\n" +
            "                return (new FormAlert(\"No Bribe\",\n" +
            "                        \"We'd love to take your money, but Space Command already knows you've got illegal goods onboard.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterOpponentEscaped:\n" +
            "                return (new FormAlert(\"Opponent Escaped\", \"Your opponent has managed to escape.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case EncounterPiratesBounty:\n" +
            "                return (new FormAlert(\"Bounty\", \"You ^1 the pirate ship^2 and earned a bounty of ^3.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPiratesExamineReactor:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Pirates Examine Reactor\",\n" +
            "                        \"The pirates poke around the Ion Reactor while trying to figure out if it's valuable. They finally conclude that the Reactor is worthless, not to mention dangerous, and leave it on your ship.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPiratesFindNoCargo:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Pirates Find No Cargo\",\n" +
            "                        \"The pirates are very angry that they find no cargo on your ship. To stop them from destroying you, you have no choice but to pay them an amount equal to 5% of your current worth - ^1.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPiratesSurrenderPrincess:\n" +
            "                return (new FormAlert(\n" +
            "                        \"You Have the Princess\",\n" +
            "                        \"Pirates are not nice people, and there's no telling what they might do to the Princess. Better to die fighting than give her up to them!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPiratesTakeSculpture:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Pirates Take Sculpture\",\n" +
            "                        \"As the pirates ransack your ship, they find the stolen sculpture. \\\"This is worth thousands!\\\" one pirate exclaims, as he stuffs it into his pack.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPoliceBribe:\n" +
            "                return (new FormAlert(\"Bribe\",\n" +
            "                        \"These police officers are willing to forego inspection for the amount of ^1.\", \"Offer Bribe\",\n" +
            "                        DialogResult.YES, \"Forget It\", DialogResult.NO, args));\n" +
            "            case EncounterPoliceBribeCant:\n" +
            "                return (new FormAlert(\"No Bribe\", \"These police officers can't be bribed.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EncounterPoliceBribeLowCash:\n" +
            "                return (new FormAlert(\"Not Enough Cash\", \"You don't have enough cash for a bribe.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case EncounterPoliceFine:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Caught\",\n" +
            "                        \"The police discovers illegal goods in your cargo holds. These goods impounded and you are fined ^1 credits.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPoliceNothingFound:\n" +
            "                return (new FormAlert(\"Nothing Found\",\n" +
            "                        \"The police find nothing illegal in your cargo holds, and apologize for the inconvenience.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPoliceNothingIllegal:\n" +
            "                return (new FormAlert(\n" +
            "                        \"You Have Nothing Illegal\",\n" +
            "                        \"Are you sure you want to do that? You are not carrying illegal goods, so you have nothing to fear!\",\n" +
            "                        \"Yes, I still want to\", DialogResult.YES, \"OK, I won't\", DialogResult.NO, args));\n" +
            "            case EncounterPoliceSubmit:\n" +
            "                return (new FormAlert(\"You Have Illegal Goods\",\n" +
            "                        \"Are you sure you want to let the police search you? You are carrying ^1! ^2\", \"Yes, let them\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterPoliceSurrender:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Surrender\",\n" +
            "                        \"^1If you surrender, you will spend some time in prison and will have to pay a hefty fine. ^2Are you sure you want to do that?\",\n" +
            "                        \"Yes\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EncounterPostMarie:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Contraband Removed\",\n" +
            "                        \"The Customs Police confiscated all of your illegal cargo, but since you were cooperative, you avoided stronger fines or penalties.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterPostMarieFlee:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Criminal Act!\",\n" +
            "                        \"Are you sure you want to do that? The Customs Police know you have engaged in criminal activity, and will report it!\",\n" +
            "                        \"Yes, I still want to\", DialogResult.YES, \"OK, I won't\", DialogResult.NO, args));\n" +
            "            case EncounterScoop:\n" +
            "                return (new FormAlert(\"Scoop Canister\",\n" +
            "                        \"A canister from the destroyed ship, labeled ^1, drifts within range of your scoops.\",\n" +
            "                        \"Pick It Up\", DialogResult.YES, \"Let It Go\", DialogResult.NO, args));\n" +
            "            case EncounterScoopNoRoom:\n" +
            "                return (new FormAlert(\n" +
            "                        \"No Room To Scoop\",\n" +
            "                        \"You don't have any room in your cargo holds. Do you wish to jettison goods to make room, or just let it go.\",\n" +
            "                        \"Make Room\", DialogResult.YES, \"Let it go\", DialogResult.NO, args));\n" +
            "            case EncounterScoopNoScoop:\n" +
            "                return (new FormAlert(\"No Scoop\",\n" +
            "                        \"You regret finding nothing in your holds that can be dumped, and let the canister go.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterSurrenderRefused:\n" +
            "                return (new FormAlert(\"To The Death!\", \"Surrender? Hah! We want your HEAD!\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EncounterTonicConsumedGood:\n" +
            "                return (new FormAlert(\"Tonic Consumed\",\n" +
            "                        \"Mmmmm. Captain Marmoset's Amazing Skill Tonic not only fills you with energy, but tastes like a fine single-malt.\"\n" +
            "                                + Strings.newline, \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterTonicConsumedStrange:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Tonic Consumed\",\n" +
            "                        \"While you don't know what it was supposed to taste like, you get the feeling that this dose of tonic was a bit off.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EncounterTradeCompleted:\n" +
            "                return (new FormAlert(\"Trade Completed\",\n" +
            "                        \"Thanks for ^1 the ^2. It's been a pleasure doing business with you.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EncounterYouLose:\n" +
            "                return (new FormAlert(\"You Lose\", \"Your ship has been destroyed by your opponent.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case EncounterYouWin:\n" +
            "                return (new FormAlert(\"You Win\", \"You have destroyed your opponent.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EquipmentAlreadyOwn:\n" +
            "                return (new FormAlert(\"You Already Have One\", \"It's not useful to buy more than one of this item.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentBuy:\n" +
            "                return (new FormAlert(\"Buy ^1\", \"Do you wish to buy this item for ^2 credits?\", \"Yes\", DialogResult.YES,\n" +
            "                        \"No\", DialogResult.NO, args));\n" +
            "            case EquipmentEscapePod:\n" +
            "                return (new FormAlert(\"Escape Pod\", \"Do you want to buy an escape pod for 2000 credits?\", \"Yes\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case EquipmentExtraBaysInUse:\n" +
            "                return (new FormAlert(\"Cargo Bays Full\",\n" +
            "                        \"The extra cargo bays are still filled with goods. You can only sell them when they're empty.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentFuelCompactor:\n" +
            "                return (new FormAlert(\"Fuel Compactor\", \"You now have a fuel compactor installed on your ship.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentHiddenCompartments:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Hidden Compartments\",\n" +
            "                        \"You now have hidden compartments equivalent to 5 extra cargo bays installed in your ship. Police won't find illegal cargo hidden in these compartments.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentIF:\n" +
            "                return (new FormAlert(\"Not Enough Money\", \"You don't have enough money to spend on this item.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentLightningShield:\n" +
            "                return (new FormAlert(\"Lightning Shield\", \"You now have one lightning shield installed on your ship.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentMorgansLaser:\n" +
            "                return (new FormAlert(\"Morgan's Laser\",\n" +
            "                        \"You now have Henry Morgan's special laser installed on your ship.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case EquipmentNotEnoughSlots:\n" +
            "                return (new FormAlert(\"Not Enough Slots\",\n" +
            "                        \"You have already filled all of your available slots for this type of item.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentQuantumDisruptor:\n" +
            "                return (new FormAlert(\"Quantum Disruptor\", \"You now have one quantum disruptor installed on your ship.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case EquipmentSell:\n" +
            "                return (new FormAlert(\"Sell Item\", \"Are you sure you want to sell this item?\", \"Yes\", DialogResult.YES,\n" +
            "                        \"No\", DialogResult.NO, args));\n" +
            "            case FileErrorOpen:\n" +
            "                return (new FormAlert(\"Error\", \"An error occurred while trying to open ^1.\" + Strings.newline\n" +
            "                        + Strings.newline + \"^2\", \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case FileErrorSave:\n" +
            "                return (new FormAlert(\"Error\", \"An error occurred while trying to save ^1.\" + Strings.newline\n" +
            "                        + Strings.newline + \"^2\", \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case FleaBuilt:\n" +
            "                return (new FormAlert(\"Flea Built\",\n" +
            "                        \"In 3 days and with 500 credits, you manage to convert your pod into a Flea.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case GameAbandonConfirm:\n" +
            "                return (new FormAlert(\"Are You Sure?\", \"Are you sure you want to abandon your current game?\", \"Yes\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case GameClearHighScores:\n" +
            "                return (new FormAlert(\"Clear High Scores\", \"Are you sure you wish to clear the high score table?\", \"Yes\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case GameEndBoughtMoon:\n" +
            "                return (new FormAlert(\"You Have Retired\", GameEndType.BoughtMoon.castToInt()));\n" +
            "            case GameEndBoughtMoonGirl:\n" +
            "                return (new FormAlert(\"You Have Retired with the Princess\", GameEndType.BoughtMoonGirl.castToInt()));\n" +
            "            case GameEndHighScoreAchieved:\n" +
            "                return (new FormAlert(\"Congratulations!\", \"You have made the high-score list!\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case GameEndHighScoreCheat:\n" +
            "                return (new FormAlert(\"Naughty, Naughty!\",\n" +
            "                        \"You would have made the high-score list if you weren't a Cheat!.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case GameEndHighScoreMissed:\n" +
            "                return (new FormAlert(\"Sorry\", \"Alas! This is not enough to enter the high-score list.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case GameEndKilled:\n" +
            "                return (new FormAlert(\"You Are Dead\", GameEndType.Killed.castToInt()));\n" +
            "            case GameEndRetired:\n" +
            "                return (new FormAlert(\"You Have Retired\", GameEndType.Retired.castToInt()));\n" +
            "            case GameEndScore:\n" +
            "                return (new FormAlert(\"Score\", \"You achieved a score of ^1.^2%.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case GameRetire:\n" +
            "                return (new FormAlert(\"Retire\", \"Are you sure you wish to retire?\", \"Yes\", DialogResult.YES, \"No\",\n" +
            "                        DialogResult.NO, args));\n" +
            "            case InsuranceNoEscapePod:\n" +
            "                return (new FormAlert(\"No Escape Pod\",\n" +
            "                        \"Insurance isn't useful for you, since you don't have an escape pod.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case InsurancePayoff:\n" +
            "                return (new FormAlert(\"Insurance\",\n" +
            "                        \"Since your ship was insured, the bank pays you the total worth of the destroyed ship.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case InsuranceStop:\n" +
            "                return (new FormAlert(\"Stop Insurance\",\n" +
            "                        \"Do you really wish to stop your insurance and lose your no-claim?\", \"Yes\", DialogResult.YES, \"No\",\n" +
            "                        DialogResult.NO, args));\n" +
            "            case JailConvicted:\n" +
            "                return (new FormAlert(\"Convicted\", \"You are convicted to ^1 in prison and a fine of ^2.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case JailFleaReceived:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Flea Received\",\n" +
            "                        \"When you leave prison, the police have left a second-hand Flea for you so you can continue your travels.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case JailHiddenCargoBaysRemoved:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Hidden Compartments Removed\",\n" +
            "                        \"When your ship is impounded, the police go over it with a fine-toothed comb. You hidden compartments are found and removed.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case JailIllegalGoodsImpounded:\n" +
            "                return (new FormAlert(\"Illegal Goods Impounded\",\n" +
            "                        \"The police also impound all of the illegal goods you have on board.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case JailInsuranceLost:\n" +
            "                return (new FormAlert(\"Insurance Lost\",\n" +
            "                        \"Since you cannot pay your insurance while you're in prison, it is retracted.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case JailMercenariesLeave:\n" +
            "                return (new FormAlert(\"Mercenaries Leave\", \"Any mercenaries who were traveling with you have left.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case JailShipSold:\n" +
            "                return (new FormAlert(\"Ship Sold\",\n" +
            "                        \"Because you don't have the credits to pay your fine, your ship is sold.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case JarekTakenHome:\n" +
            "                return (new FormAlert(\"Jarek Taken Home\",\n" +
            "                        \"The Space Corps decides to give ambassador Jarek a lift home to Devidia.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case LeavingIFInsurance:\n" +
            "                return (new FormAlert(\"Not Enough Money\", \"You don't have enough cash to pay for your insurance.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case LeavingIFMercenaries:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Pay Mercenaries\",\n" +
            "                        \"You don't have enough cash to pay your mercenaries to come with you on this trip. Fire them or make sure you have enough cash.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case LeavingIFWormholeTax:\n" +
            "                return (new FormAlert(\"Wormhole Tax\", \"You don't have enough money to pay for the wormhole tax.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case MeetCaptainAhab:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Meet Captain Ahab\",\n" +
            "                        \"Captain Ahab is in need of a spare shield for an upcoming mission. He offers to trade you some piloting lessons for your reflective shield. Do you wish to trade?\",\n" +
            "                        \"Yes, Trade Shield\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case MeetCaptainConrad:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Meet Captain Conrad\",\n" +
            "                        \"Captain Conrad is in need of a military laser. She offers to trade you some engineering training for your military laser. Do you wish to trade?\",\n" +
            "                        \"Yes, Trade Laser\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case MeetCaptainHuie:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Meet Captain Huie\",\n" +
            "                        \"Captain Huie is in need of a military laser. She offers to exchange some bargaining training for your military laser. Do you wish to trade?\",\n" +
            "                        \"Yes, Trade Laser\", DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case NewGameConfirm:\n" +
            "                return (new FormAlert(\"New Game\", \"Are you sure you wish to start a new game?\", \"Yes\", DialogResult.YES,\n" +
            "                        \"No\", DialogResult.NO, args));\n" +
            "            case NewGameMoreSkillPoints:\n" +
            "                return (new FormAlert(\"More Skill Points\", \"You haven't awarded all 20 skill points yet.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case OptionsNoGame:\n" +
            "                return (new FormAlert(\"No Game Active\",\n" +
            "                        \"You don't have a game open, so you can only change the default options.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case PreciousHidden:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Precious Cargo Hidden\",\n" +
            "                        \"You quickly hide ^1 in your hidden cargo bays before the pirates board your ship. This would never work with the police, but pirates are usually in more of a hurry.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case PrincessTakenHome:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Princess Taken Home\",\n" +
            "                        \"The Space Corps decides to give the Princess a ride home to Galvon since you obviously weren't up to the task.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorConfiscated:\n" +
            "                return (new FormAlert(\"Police Confiscate Reactor\",\n" +
            "                        \"The Police confiscate the Ion reactor as evidence of your dealings with unsavory characters.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorDestroyed:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Reactor Destroyed\",\n" +
            "                        \"The destruction of your ship was made much more spectacular by the added explosion of the Ion Reactor.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorOnBoard:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Reactor\",\n" +
            "                        \"Five of your cargo bays now contain the unstable Ion Reactor, and ten of your bays contain enriched fuel.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorMeltdown:\n" +
            "                return (new FormAlert(\"Reactor Meltdown!\",\n" +
            "                        \"Just as you approach the docking bay, the reactor explodes into a huge radioactive fireball!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorWarningFuel:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Reactor Warning\",\n" +
            "                        \"You notice the Ion Reactor has begun to consume fuel rapidly. In a single day, it has burned up nearly half a bay of fuel!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorWarningFuelGone:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Reactor Warning\",\n" +
            "                        \"The Ion Reactor is emitting a shrill whine, and it's shaking. The display indicates that it is suffering from fuel starvation.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ReactorWarningTemp:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Reactor Warning\",\n" +
            "                        \"The Ion Reactor is smoking and making loud noises. The display warns that the core is close to the melting temperature.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case RegistryError:\n" +
            "                return (new FormAlert(\"Error...\", \"Error accessing the Registry: ^1\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case SculptureConfiscated:\n" +
            "                return (new FormAlert(\"Police Confiscate Sculpture\",\n" +
            "                        \"The Police confiscate the stolen sculpture and return it to its rightful owner.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SculptureSaved:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Sculpture Saved\",\n" +
            "                        \"On your way to the escape pod, you grab the stolen sculpture. Oh well, at least you saved something.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyConfirm:\n" +
            "                return (new FormAlert(\"Buy New Ship\", \"Are you sure you wish to trade in your ^1 for a new ^2^3?\", \"Yes\",\n" +
            "                        DialogResult.YES, \"No\", DialogResult.NO, args));\n" +
            "            case ShipBuyCrewQuarters:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Too Many Crewmembers\",\n" +
            "                        \"The new ship you picked doesn't have enough quarters for all of your crewmembers. First you will have to fire one or more of them.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyIF:\n" +
            "                return (new FormAlert(\"Not Enough Money\", \"You don't have enough money to buy this ship.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyIFTransfer:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Not Enough Money\",\n" +
            "                        \"You won't have enough money to buy this ship and pay the cost to transfer all of your unique equipment. You should choose carefully which items you wish to transfer!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyNoSlots:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Can't Transfer Item\",\n" +
            "                        \"If you trade your ship in for a ^1, you won't be able to transfer your ^2 because the new ship has insufficient ^3 slots!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyNotAvailable:\n" +
            "                return (new FormAlert(\"Ship Not Available\", \"That type of ship is not available in the current system.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyNoTransfer:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Can't Transfer Item\",\n" +
            "                        \"Unfortunately, if you make this trade, you won't be able to afford to transfer your ^1 to the new ship!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyPassengerQuarters:\n" +
            "                return (new FormAlert(\"Passenger Needs Quarters\",\n" +
            "                        \"You must get a ship with enough crew quarters so that ^1 can stay on board.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyReactor:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Shipyard Engineer\",\n" +
            "                        \"Sorry! We can't take your ship as a trade-in. That Ion Reactor looks dangerous, and we have no way of removing it. Come back when you've gotten rid of it.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipBuyTransfer:\n" +
            "                return (new FormAlert(\"Transfer ^1\", \"I'll transfer your ^2 to your new ship for ^3 credits.\", \"Do it!\",\n" +
            "                        DialogResult.YES, \"No thanks\", DialogResult.NO, args));\n" +
            "            case ShipDesignIF:\n" +
            "                return (new FormAlert(\"Not Enough Money\", \"You don't have enough money to create this design.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case ShipDesignThanks:\n" +
            "                return (new FormAlert(\"Thank you!\", \"^1 thanks you for your business!\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case ShipHullUpgraded:\n" +
            "                return (new FormAlert(\"Hull Upgraded\", \"Technicians spend the day retrofitting the hull of your ship.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialCleanRecord:\n" +
            "                return (new FormAlert(\"Clean Record\", \"The hacker resets your police record to Clean.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialExperimentPerformed:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Experiment Performed\",\n" +
            "                        \"The galaxy is abuzz with news of a terrible malfunction in Dr. Fehler's laboratory. Evidently, he was not warned in time and he performed his experiment... with disastrous results!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialIF:\n" +
            "                return (new FormAlert(\"Not Enough Money\", \"You don't have enough cash to spend to accept this offer.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialMoonBought:\n" +
            "                return (new FormAlert(\"Moon Bought\", \"You bought a moon in the Utopia system. Go there to claim it.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialNoQuarters:\n" +
            "                return (new FormAlert(\"No Free Quarters\", \"There are currently no free crew quarters on your ship.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialNotEnoughBays:\n" +
            "                return (new FormAlert(\"Not Enough Bays\", \"You don't have enough empty cargo bays at the moment.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerConcernedJarek:\n" +
            "                return (new FormAlert(\"Ship's Comm.\",\n" +
            "                        \"Commander? Jarek here. Do you require any assitance in charting a course to Devidia?\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerConcernedPrincess:\n" +
            "                return (new FormAlert(\"Ship's Comm.\",\n" +
            "                        \"[Ziyal] Oh Captain? (giggles) Would it help if I got out and pushed?\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerConcernedWild:\n" +
            "                return (new FormAlert(\"Ship's Comm.\",\n" +
            "                        \"Bridge? This is Jonathan. Are we there yet? Heh, heh. Sorry, I couldn't resist.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerImpatientJarek:\n" +
            "                return (new FormAlert(\"Ship's Comm.\",\n" +
            "                        \"Captain! This is the Ambassador speaking. We should have been there by now?!\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerImpatientPrincess:\n" +
            "                return (new FormAlert(\"Ship's Comm.\",\n" +
            "                        \"Sir! Are you taking me home or merely taking the place of my previous captors?!\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerImpatientWild:\n" +
            "                return (new FormAlert(\"Ship's Comm.\", \"Commander! Wild here. What's taking us so long?!\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialPassengerOnBoard:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Passenger On Board\",\n" +
            "                        \"You have taken ^1 on board. While on board ^1 will lend you expertise, but may stop helping if the journey takes too long.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case SpecialSealedCanisters:\n" +
            "                return (new FormAlert(\"Sealed Canisters\",\n" +
            "                        \"You bought the sealed canisters and put them in your cargo bays.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case SpecialSkillIncrease:\n" +
            "                return (new FormAlert(\"Skill Increase\", \"The alien increases one of your skills. \", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case SpecialTimespaceFabricRip:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Timespace Fabric Rip\",\n" +
            "                        \"You have flown through a tear in the timespace continuum caused by Dr. Fehler's failed experiment. You may not have reached\"\n" +
            "                                + Strings.newline + \" your planned destination!\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case SpecialTrainingCompleted:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Training Completed\",\n" +
            "                        \"After a few hours of training with a top expert, you feel your abilities have improved significantly.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TravelArrival:\n" +
            "                return (new FormAlert(\"Arrival\", \"You arrive at your destination.\", \"Ok\", DialogResult.OK, null,\n" +
            "                        DialogResult.NONE, args));\n" +
            "            case TravelUneventfulTrip:\n" +
            "                return (new FormAlert(\"Uneventful Trip\", \"After an uneventful trip, you arrive at your destination.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesAllDied:\n" +
            "                return (new FormAlert(\n" +
            "                        \"All The Tribbles Died\",\n" +
            "                        \"The radiation from the Ion Reactor is deadly to Tribbles. All of the Tribbles on board your ship have died.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesAteFood:\n" +
            "                return (new FormAlert(\"Tribbles Ate Food\",\n" +
            "                        \"You find that, instead of food, some of your cargo bays contain only tribbles!\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesGone:\n" +
            "                return (new FormAlert(\"No More Tribbles\",\n" +
            "                        \"The alien uses his alien technology to beam over your whole collection of tribbles to his ship.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesHalfDied:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Half The Tribbles Died\",\n" +
            "                        \"The radiation from the Ion Reactor seems to be deadly to Tribbles. Half the Tribbles on board died.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesKilled:\n" +
            "                return (new FormAlert(\"Tribbles Killed\", \"Your tribbles all died in the explosion.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case TribblesMostDied:\n" +
            "                return (new FormAlert(\"Most Tribbles Died\",\n" +
            "                        \"You find that, instead of narcotics, some of your cargo bays contain only dead tribbles!\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesOwn:\n" +
            "                return (new FormAlert(\"A Tribble\", \"You are now the proud owner of a little, cute, furry tribble.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesRemoved:\n" +
            "                return (new FormAlert(\"Tribbles Removed\", \"The tribbles were sold with your ship.\", \"Ok\", DialogResult.OK,\n" +
            "                        null, DialogResult.NONE, args));\n" +
            "            case TribblesInspector:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Space Port Inspector\",\n" +
            "                        \"Our scan reports you have ^1 tribbles on board your ship. Tribbles are pests worse than locusts! You are running the risk of getting a hefty fine!\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesSqueek:\n" +
            "                return (new FormAlert(\"A Tribble\", \"Squeek!\", \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case TribblesTradeIn:\n" +
            "                return (new FormAlert(\n" +
            "                        \"You've Got Tribbles\",\n" +
            "                        \"Hm. I see you got a tribble infestation on your current ship. I'm sorry, but that severely reduces the trade-in price.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildArrested:\n" +
            "                return (new FormAlert(\"Wild Arrested\", \"Jonathan Wild is arrested, and taken away to stand trial.\", \"Ok\",\n" +
            "                        DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildChatsPirates:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Chats With Pirates\",\n" +
            "                        \"The Pirate Captain turns out to be an old associate of Jonathan Wild's. They talk about old times, and you get the feeling that Wild would switch ships if the Pirates had any quarters available.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildGoesPirates:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Goes With Pirates\",\n" +
            "                        \"The Pirate Captain turns out to be an old associate of Jonathan Wild's, and invites him to go to Kravat aboard the Pirate ship. Wild accepts the offer and thanks you for the ride.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildLeavesShip:\n" +
            "                return (new FormAlert(\"Wild Leaves Ship\", \"Jonathan Wild leaves your ship, and goes into hiding on ^1.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildSculpture:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Eyes Sculpture\",\n" +
            "                        \"Jonathan Wild sees the stolen sculpture. \\\"Wow, I only know of one of these left in the whole Universe!\\\" he exclaims, \\\"Geurge Locas must be beside himself with it being stolen.\\\" He seems very impressed with you, which makes you feel much better about the item your delivering.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildWontBoardLaser:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Won't Board Ship\",\n" +
            "                        \"Jonathan Wild isn't willing to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here.\"\n" +
            "                                + Strings.newline, \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildWontBoardReactor:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Won't Board Ship\",\n" +
            "                        \"Jonathan Wild doesn't like the looks of that Ion Reactor. He thinks it's too dangerous, and won't get on board.\",\n" +
            "                        \"Ok\", DialogResult.OK, null, DialogResult.NONE, args));\n" +
            "            case WildWontStayAboardLaser:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Won't Stay Aboard\",\n" +
            "                        \"Jonathan Wild isn't about to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here on ^1.\"\n" +
            "                                + Strings.newline, \"Say Goodbye to Wild\", DialogResult.OK, \"cancel\", DialogResult.CANCEL,\n" +
            "                        args));\n" +
            "            case WildWontStayAboardReactor:\n" +
            "                return (new FormAlert(\n" +
            "                        \"Wild Won't Stay Aboard\",\n" +
            "                        \"Jonathan Wild isn't willing to go with you if you bring that Reactor on board. He'd rather take his chances hiding out here on ^1.\"\n" +
            "                                + Strings.newline, \"Say Goodbye to Wild\", DialogResult.OK, \"cancel\", DialogResult.CANCEL,\n" +
            "                        args));\n" +
            "\n" +
            "            default:\n" +
            "                throw new IllegalArgumentException(\"Unknown AlertType: \" + type);\n" +
            "        }\n" +
            "    }";*/
}

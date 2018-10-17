package spacetrader.util;

import spacetrader.controls.IName;
import spacetrader.game.Strings;
import spacetrader.stub.ArrayList;
import spacetrader.stub.StringsMap;
import spacetrader.stub.ValuesMap;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {

    public static void dumpStrings() {
        Strings strings = new Strings();
        try {
            getFieldNamesAndValues(strings).entrySet().stream()
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

    public static void loadStrings(StringsMap stringsMap) {
        Strings strings = new Strings();
        try {
            getFieldNamesAndValues(strings).entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> {
                String name = entry.getKey();
                Object object = entry.getValue();
                try {
                    Field field = (strings).getClass().getField(name);
                    if (object instanceof String) {
                        String value = stringsMap.getString(name);
                        if (value != null) {
                            field.set(strings, value);
                        }
                    } else if (object instanceof String[]) {
                        String[] array = (String[]) object;
                        for (int i = 0; i < array.length; i++) {
                            String fieldName = name + "[" + i + "]";
                            String value = stringsMap.getString(fieldName);
                            if (value != null) {
                                array[i] = value;
                            }
                        }
                    } else if (object instanceof String[][]) {
                        String[][] array = (String[][]) object;
                        for (int j = 0; j < array.length; j++) {
                            for (int i = 0; i < array[j].length; i++) {
                                String fieldName = name + "[" + j + "]" + "[" + i + "]";
                                String value = stringsMap.getString(fieldName);
                                if (value != null) {
                                    array[j][i] = value;
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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    private static List<String> getFieldNames(final Object obj) throws IllegalArgumentException {
        Class<?> c1 = obj.getClass();
        List<String> list = new ArrayList<>();
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            list.add(name);
        }
        return list;
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

    public static String formatPropertyName(String prefix) {
        return prefix.replace(".JRootPane", "")
                .replace(".null.glassPane", "")
                .replace(".null.layeredPane", "")
                .replace(".null.contentPane", "")
                .replace(".null", "")
                .replace(".WinformJPanel", "");
    }

    public static void dumpControlsStrings(Component component, String prefix) {
        component.getSize();
        component.getLocation();

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
        //TODO button names
        if (!value.isEmpty() && !key.contains("btnBuyQty") && !key.contains("btnSellQty")) {
            System.out.println(key + "=" + value);
        } else {
            System.out.println("# " + key + "=" + value);
        }
    }

    public static void loadControlsDimensions(Component component, String prefix, ValuesMap valuesMap) {
        prefix = formatPropertyName(prefix);
        if (component.getName() != null && !component.getName().startsWith("null.")) {
            double scale = 1.0;
            //TODO delete
            if (valuesMap.get(prefix + ".width") != null) {
                Dimension dimension = valuesMap.getSize(prefix);
                dimension.setSize(dimension.getWidth() * scale, dimension.getHeight() * scale);
                component.setSize(dimension);
                if (!(component instanceof JFrame)) {
                    Point point = valuesMap.getLocation(prefix);
                    point.setLocation(point.getX() * scale, point.getY() * scale);
                    component.setLocation(point);
                }
            } else {
                System.out.println("!!!" + prefix);
            }
        }
        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                loadControlsDimensions(child, prefix + "." + name, valuesMap);
            }
        }
    }

    public static void loadControlsStrings(Component component, String prefix, StringsMap stringsMap) {
        prefix = formatPropertyName(prefix);
        if (component instanceof JFrame) {
            ((JFrame) component).setTitle(stringsMap.getTitle(prefix));
        }

        if (component instanceof AbstractButton) {
            ((AbstractButton) component).setText(stringsMap.getText(prefix));
        }

        if (component instanceof JLabel) {
            ((JLabel) component).setText(stringsMap.getText(prefix));
        }

        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder) {
            ((TitledBorder) ((JPanel) component).getBorder()).setTitle(stringsMap.getTitle(prefix));
        }

        if (component instanceof JMenu) {
            for (Component child : ((JMenu) component).getMenuComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                loadControlsStrings(child, prefix + "." + name, stringsMap);
            }
        }

        if (component instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) component).getComponents()) {
                String name = /*child.getName() == null ? child.getClass().getSimpleName() :*/ child.getName();
                loadControlsStrings(child, prefix + "." + name, stringsMap);
            }
        }
    }


}

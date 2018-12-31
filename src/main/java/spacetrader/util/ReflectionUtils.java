package spacetrader.util;

import spacetrader.controls.BaseComponent;
import spacetrader.controls.IName;
import spacetrader.game.GlobalAssets;
import spacetrader.game.Strings;
import spacetrader.stub.ObjectsBundle;
import spacetrader.stub.StringsBundle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
                            field.set(strings, Functions.detectPlural(pluralMap, value));
                        }
                    } else if (object instanceof String[]) {
                        String[] array = (String[]) object;
                        for (int i = 0; i < array.length; i++) {
                            String fieldName = name + "[" + i + "]";
                            String value = stringsBundle.getString(fieldName);
                            if (value != null) {
                                array[i] = Functions.detectPlural(pluralMap, value);
                            }
                        }
                    } else if (object instanceof String[][]) {
                        String[][] array = (String[][]) object;
                        for (int j = 0; j < array.length; j++) {
                            for (int i = 0; i < array[j].length; i++) {
                                String fieldName = name + "[" + j + "]" + "[" + i + "]";
                                String value = stringsBundle.getString(fieldName);
                                if (value != null) {
                                    array[j][i] = Functions.detectPlural(pluralMap, value);
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
                .replace(".WinformJPanel", "")
                .replace(".GTKFileChooser", "");
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

    private static void loadControlsDimensions(Component component, String prefix, ObjectsBundle objectsBundle) {
        prefix = formatPropertyName(prefix);
        if (component.getName() != null && !component.getName().startsWith("null.")) {
            double scale = 1.0;
            //TODO delete
            if (objectsBundle.get(prefix + ".width") != null) {
                Dimension dimension = objectsBundle.getSize(prefix);
                dimension.setSize(dimension.getWidth() * scale, dimension.getHeight() * scale);
                component.setSize(dimension);
                if (!(component instanceof JFrame)) {
                    Point point = objectsBundle.getLocation(prefix);
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
                loadControlsDimensions(child, prefix + "." + name, objectsBundle);
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
            if (!getBaseComponent(jLabel).isPresent()) {
                jLabel.setText(text);
            }
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
}

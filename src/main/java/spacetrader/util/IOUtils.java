package spacetrader.util;

import spacetrader.game.Consts;
import spacetrader.game.HighScoreRecord;
import spacetrader.game.enums.AlertType;
import spacetrader.gui.SpaceTrader;
import spacetrader.guifacade.GuiFacade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

public class IOUtils {


    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static void createDirectory(String path) {
        if (!new File(path).mkdir()) {
            System.out.println("Couldn't make dir " + path);
        }
    }

    public static String[] getFiles(String path, String filter) {
        if (!filter.startsWith("*.")) {
            new Error("unsupported format").printStackTrace();
        }

        final String suffix = filter.substring(2);

        File[] files = new File(path).listFiles((arg0, filename) -> filename.endsWith(suffix));

        if (files == null) {
            System.out.println("getFiles returns null!");
            return new String[0];
        }
        List<String> names = Lisp.map(files, File::getPath);

        return names.toArray(new String[0]);
    }


    @SuppressWarnings("unchecked")
    public static List<HighScoreRecord> getHighScores() {
        return readObjectFromFile(Consts.HighScoreFile, true)
                .map(o -> (List<HighScoreRecord>) o)
                .orElse(new ArrayList<>());
    }

    public static boolean writeObjectToFile(String fileName, Object object) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOut.writeObject(object);
            objectOut.close();
            return true;
        } catch (Exception ex) {
            //TODO
            ex.printStackTrace();
        }
        return false;
    }

    public static Optional<Object> readObjectFromFile(String fileName, boolean ignoreMissingFile) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object result = objectIn.readObject();
            fileIn.close();
            return Optional.ofNullable(result);
        } catch (Exception ex) {
            if (!ignoreMissingFile) {
                //TODO
                ex.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private static Preferences getPreferences() {
        return Preferences.userNodeForPackage(SpaceTrader.class);
    }

    public static String getRegistrySetting(String settingName, String defaultValue) {
        try {
            return getPreferences().get(settingName, defaultValue);
        } catch (Exception e) {
            GuiFacade.alert(AlertType.RegistryError, e.getMessage());
        }
        return defaultValue;
    }

    public static void setRegistrySetting(String settingName, String settingValue) {
        try {
            getPreferences().put(settingName, settingValue);
        } catch (NullPointerException ex) {
            GuiFacade.alert(AlertType.RegistryError, ex.getMessage());
        }
    }
}

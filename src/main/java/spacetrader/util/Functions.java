package spacetrader.util;

import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.BinaryFormatter;
import spacetrader.stub.RegistryKey;
import spacetrader.stub.SerializationException;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Functions {

    private final static long DEF_SEED_X = 521288629;
    private final static long DEF_SEED_Y = 362436069;
    private final static int MAX_WORD = 65535;
    private static Random rand = new Random();

    private static long seedX;
    private static long seedY;

    public static int distance(StarSystem a, StarSystem b) {
        return distance(a, b.getX(), b.getY());
    }

    public static int distance(StarSystem a, int x, int y) {
        return (int) Math.floor(Math.sqrt(Math.pow(a.getX() - x, 2) + Math.pow(a.getY() - y, 2)));
    }

    public static String formatNumber(int num) {
        return String.format("%,d", num);
    }

    public static String formatList(List<String> listItems) {
        return stringVars(Strings.ListStrings[listItems.size()], listItems);
    }

    public static String formatMoney(int num) {
        return String.format("%,d %s", num, Strings.CargoCredit);
    }

    public static String formatPercent(int num) {
        return String.format("%,d%%", num);
    }

    //TODO russian plural + gender
    //TODO rules :(
    //parsec, credit, click, unit, Unit, day, weapons,shields,gadgets[],
    //gadget slot, shield slot, weapon slot, cute, furry tribble, bay
    //парсек, кредит, клик юнит(единица), день, оружие-щиты-гаджеты
    //слот для гаджета слот для щита слот для оружия(вооружения), милый, пушистый триббл, отсек
    public static String multiples(int num, String unit) {
        return formatNumber(num) + " " + unit + (num == 1 ? "" : "s");
    }

    public static String stringVars(String toParse, String var1) {
        return stringVars(toParse, new String[]{var1});
    }

    public static String stringVars(String toParse, String var1, String var2) {
        return stringVars(toParse, new String[]{var1, var2});
    }

    public static String stringVars(String toParse, String[] vars) {
        return stringVars(toParse, Arrays.asList(vars));
    }

    public static String stringVars(String toParse, List<String> vars) {
        List<List<String>> splittedVars = vars.stream().map(s -> Arrays.stream(s.split("\\|"))
                .map(String::trim).filter(st -> !st.isEmpty()).collect(toList())).collect(toList());

        for (int i = splittedVars.size() - 1; i >= 0; i--) {

            for (int j = splittedVars.get(i).size() - 1; j >= 0; j--) {
                String s = String.join("", Collections.nCopies(j + 1, "\\^")) + (i + 1);
                toParse = toParse.replaceAll(s, splittedVars.get(i).get(j));
            }
        }

        return toParse;
    }

    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    public static int getRandom(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    // *************************************************************************
    // Pieter's new random functions, tweaked a bit by SjG
    // *************************************************************************
    public static int getRandom2(int max) {
        return (int) (rand() % max);
    }

    public static RegistryKey getRegistryKey() {
        return new RegistryKey(new File("registryKey.properties"));
    }

    public static boolean isInt(String toParse) {
        try {
            Integer.parseInt(toParse);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static long rand() {
        final int a = 18000;
        final int b = 30903;

        seedX = a * (seedX & MAX_WORD) + (seedX >> 16);
        seedY = b * (seedY & MAX_WORD) + (seedY >> 16);

        return ((seedX << 16) + (seedY & MAX_WORD));
    }

    public static int randomSkill() {
        return 1 + getRandom(5) + getRandom(6);
    }

    public static void randSeed(int seed1, int seed2) {
        if (seed1 > 0)
            seedX = seed1; /* use default seeds if parameter is 0 */
        else
            seedX = DEF_SEED_X;

        if (seed2 > 0)
            seedY = seed2;
        else
            seedY = DEF_SEED_Y;
    }

    public static HighScoreRecord[] getHighScores() {
        return readObjectFromFile(Consts.HighScoreFile, true)
                .map(o -> (HighScoreRecord[]) o)
                .orElse(new HighScoreRecord[3]);
    }

    public static void writeObjectToFile(String fileName, Object object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            objectOut.close();
        } catch (Exception ex) {
            //TODO
            ex.printStackTrace();
        }
    }

    private static Optional<Object> readObjectFromFile(String fileName, boolean ignoreMissingFile) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object result = objectIn.readObject();
            fileIn.close();
            return Optional.ofNullable(result);
        } catch (Exception ex) {
            if (!ignoreMissingFile) {
                ex.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public static Object loadFile(String fileName, boolean ignoreMissingFile) {
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(fileName);
            return (new BinaryFormatter()).deserialize(inStream);
        } catch (FileNotFoundException e) {
            if (!ignoreMissingFile)
                GuiFacade.alert(AlertType.FileErrorOpen, fileName, e.getMessage());
        } catch (IOException ex) {
            GuiFacade.alert(AlertType.FileErrorOpen, fileName, ex.getMessage());
        } catch (SerializationException ex) {
            GuiFacade.alert(AlertType.FileErrorOpen, fileName, Strings.FileFormatBad);
        } finally {
            if (inStream != null)
                try {
                    inStream.close();
                } catch (IOException e) {
                    Log.write("Can't close inStream");
                }
        }
        return null;
    }

    public static boolean saveFile(String fileName, Object toSerialize) {
        FileOutputStream outStream = null;

        try {
            new File(fileName).createNewFile();
            outStream = new FileOutputStream(fileName, false);
            (new BinaryFormatter()).serialize(outStream, toSerialize);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            GuiFacade.alert(AlertType.FileErrorSave, fileName, ex.getMessage());
        } finally {
            if (outStream != null)
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return false;
    }

    public static boolean wormholeExists(StarSystem a, StarSystem b) {
        return wormholeExists(a.getId().castToInt(), (b == null) ? -1 : b.getId().castToInt());
    }

    // *************************************************************************
    // Returns true if there exists a wormhole from a to b.
    // If b < 0, then return true if there exists a wormhole
    // at all from a.
    // *************************************************************************
    public static boolean wormholeExists(int a, int b) {
        int[] wormholes = Game.getCurrentGame().getWormholes();
        int i = Util.bruteSeek(wormholes, a);
        // int i = Array.IndexOf(wormholes, a);

        return (i >= 0 && (b < 0 || wormholes[(i + 1) % wormholes.length] == b));
    }

    //TODO wrom where???
    public static StarSystem wormholeTarget(int a) {
        int[] wormholes = Game.getCurrentGame().getWormholes();
        // int i = Array.IndexOf(wormholes, a);
        int i = Util.bruteSeek(wormholes, a);

        return (i >= 0 ? (Game.getCurrentGame().getUniverse()[wormholes[(i + 1) % wormholes.length]]) : null);
    }
}

/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron
 * Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
package spacetrader.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.BinaryFormatter;
import spacetrader.stub.RegistryKey;
import spacetrader.stub.SerializationException;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;
import spacetrader.util.Util;

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

    static int distance(StarSystem a, int x, int y) {
        return (int) Math.floor(Math.sqrt(Math.pow(a.getX() - x, 2) + Math.pow(a.getY() - y, 2)));
    }

    public static String formatNumber(int num) {
        return String.format("%,d", num);
    }

    static String formatList(List<String> listItems) {
        return stringVars(Strings.ListStrings[listItems.size()], listItems);
    }

    public static String formatMoney(int num) {
        return String.format("%,d %s", num, Strings.CargoCredit);
    }

    public static String formatPercent(int num) {
        return String.format("%,d%%", num);
    }

    //TODO plural
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
        String parsed = toParse;

        for (int i = 0; i < vars.size(); i++) {
            parsed = parsed.replaceAll("\\^" + (i + 1), vars.get(i));
        }

        return parsed;
    }

    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    static int getRandom(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    // *************************************************************************
    // Pieter's new random functions, tweaked a bit by SjG
    // *************************************************************************
    static int getRandom2(int max) {
        return (int) (rand() % max);
    }

    public static RegistryKey getRegistryKey() {
        return new RegistryKey(new File("registryKey.properties"));
    }

    static boolean isInt(String toParse) {
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

    static int randomSkill() {
        return 1 + getRandom(5) + getRandom(6);
    }

    static void randSeed(int seed1, int seed2) {
        if (seed1 > 0)
            seedX = seed1; /* use default seeds if parameter is 0 */
        else
            seedX = DEF_SEED_X;

        if (seed2 > 0)
            seedY = seed2;
        else
            seedY = DEF_SEED_Y;
    }

    @SuppressWarnings("unchecked")
    public static HighScoreRecord[] getHighScores() {
        Object obj = loadFile(Consts.HighScoreFile, true);
        if (obj == null) {
            return new HighScoreRecord[3];
        }

        return (HighScoreRecord[]) STSerializableObject.arrayListToArray((List<Hashtable>) obj, "HighScoreRecord");
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

    static boolean wormholeExists(StarSystem a, StarSystem b) {
        return wormholeExists(a.getId().castToInt(), b.getId().castToInt());
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

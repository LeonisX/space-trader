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

import spacetrader.controls.*;
import spacetrader.controls.Graphics;
import spacetrader.controls.Image;
import spacetrader.controls.Rectangle;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.Difficulty;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.BinaryFormatter;
import spacetrader.stub.RegistryKey;
import spacetrader.stub.SerializationException;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;
import spacetrader.util.Util;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Random;

public class Functions {

    private final static long DEFSEEDX = 521288629;
    private final static long DEFSEEDY = 362436069;
    private final static int MAX_WORD = 65535;
    private static Random rand = new Random();
    private static long SeedX = DEFSEEDX;
    private static long SeedY = DEFSEEDY;

    static int adjustSkillForDifficulty(int skill) {
        Difficulty diff = Game.getCurrentGame().getDifficulty();
        skill = diff.adjustSkill(skill);

        return skill;
    }

    public static String[] arrayListToStringArray(List<?> list) {
        String[] items = new String[list.size()];

        for (int i = 0; i < items.length; i++)
            items[i] = (String) list.get(i);

        return items;
    }

    public static int distance(StarSystem a, StarSystem b) {
        return (int) Math.floor(Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)));
    }

    static int distance(StarSystem a, int x, int y) {
        return (int) Math.floor(Math.sqrt(Math.pow(a.getX() - x, 2) + Math.pow(a.getY() - y, 2)));
    }

    private static void drawPartialImage(Graphics g, Image img, int start, int stop) {
        g.drawImage(img, 2 + start, 2, new Rectangle(start, 0, stop - start, img.getHeight()), GraphicsUnit.Pixel);
    }

    public static String formatNumber(int num) {
        // return String.format("{0:n0}", num);
        return String.format("%,d", num);
    }

    static String formatList(String[] listItems) {
        return stringVars(Strings.ListStrings[listItems.length], listItems);
    }

    public static String formatMoney(int num) {
        // return String.format("{0:n0} cr.", num);
        return String.format("%,d %s", num, Strings.CargoCredit);
    }

    public static String formatPercent(int num) {
        // return String.format("{0:n0}%", num);
        return String.format("%,d%%", num);
    }

    static int getColumnOfFirstNonWhitePixel(Image image, int direction) {
        Bitmap bitmap = new Bitmap(image);
        int step = direction < 0 ? -1 : 1;
        int col = step > 0 ? 0 : bitmap.getWidth() - 1;
        int stop = step > 0 ? bitmap.getWidth() : -1;

        for (; col != stop; col += step) {
            for (int row = 0; row < bitmap.getHeight(); row++) {
                if (bitmap.ToArgb(col, row) != 0)
                    return col;
            }
        }

        return -1;
    }

    public static HighScoreRecord[] getHighScores() {
        Object obj = loadFile(Consts.HighScoreFile, true);
        if (obj == null)
            return new HighScoreRecord[3];

        return (HighScoreRecord[]) STSerializableObject.ArrayListToArray((List<Hashtable>) obj, "HighScoreRecord");
    }

    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    static int getRandom(int min, int max) {
        // return rand.Next(min, max);
        return rand.nextInt(max - min) + min;
    }

    // *************************************************************************
    // Pieter's new random functions, tweaked a bit by SjG
    // *************************************************************************
    static int getRandom2(int max) {
        return (int) (rand() % max);
    }

    public static RegistryKey getRegistryKey() {
        File regfile = new File("registryKey.properties");

        return new RegistryKey(regfile);

        // return Registry.CurrentUser.OpenSubKey("Software",
        // true).CreateSubKey("FrenchFryz").CreateSubKey("SpaceTrader");
    }

    static boolean isInt(String toParse) {
        try {
            Integer.parseInt(toParse);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Object loadFile(String fileName, boolean ignoreMissingFile) {
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(fileName/* , FileMode.Open */);
            return (new BinaryFormatter()).Deserialize(inStream);
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
                    Log.write("Can't close instream... 231");
                }
        }

        return null;
    }

    public static String multiples(int num, String unit) {
        return formatNumber(num) + " " + unit + (num == 1 ? "" : "s");
    }

    public static void paintShipImage(Ship ship, Graphics graphics, Color backgroundColor) {
        int x = Consts.ShipImageOffsets[ship.Type().castToInt()].X;
        int width = Consts.ShipImageOffsets[ship.Type().castToInt()].Width;
        int startDamage = x + width - ship.getHull() * width / ship.getHullStrength();
        int startShield = x + width + 2
                - (ship.ShieldStrength() > 0 ? ship.ShieldCharge() * (width + 4) / ship.ShieldStrength() : 0);

        graphics.clear(backgroundColor);

        if (startDamage > x) {
            if (startShield > x)
                drawPartialImage(graphics, ship.ImageDamaged(), x, Math.min(startDamage, startShield));

            if (startShield < startDamage)
                drawPartialImage(graphics, ship.ImageDamagedWithShields(), startShield, startDamage);
        }

        if (startShield > startDamage)
            drawPartialImage(graphics, ship.Image(), startDamage, startShield);

        if (startShield < x + width + 2)
            drawPartialImage(graphics, ship.ImageWithShields(), startShield, x + width + 2);
    }

    private static long rand() {
        final int a = 18000;
        final int b = 30903;

        SeedX = a * (SeedX & MAX_WORD) + (SeedX >> 16);
        SeedY = b * (SeedY & MAX_WORD) + (SeedY >> 16);

        return ((SeedX << 16) + (SeedY & MAX_WORD));
    }

    static int randomSkill() {
        return 1 + getRandom(5) + getRandom(6);
    }

    static void randSeed(int seed1, int seed2) {
        if (seed1 > 0)
            SeedX = seed1; /* use default seeds if parameter is 0 */
        else
            SeedX = DEFSEEDX;

        if (seed2 > 0)
            SeedY = seed2;
        else
            SeedY = DEFSEEDY;
    }

    public static boolean saveFile(String fileName, Object toSerialize) {
        System.out.println(fileName);
        FileOutputStream outStream = null;
        boolean saveOk = false;

        try {
            new File(fileName).createNewFile();
            outStream = new FileOutputStream(fileName, false);
            (new BinaryFormatter()).Serialize(outStream, toSerialize);

            saveOk = true;
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

        return saveOk;
    }

    // TODO replace w/String.format?
    public static String stringVars(String toParse, String[] vars) {
        String parsed = toParse;

        for (int i = 0; i < vars.length; i++)
            parsed = parsed.replaceAll("\\^" + (i + 1), vars[i]);

        return parsed;
    }

    public static String stringVars(String toParse, String var1) {
        return stringVars(toParse, new String[]{var1});
    }

    public static String stringVars(String toParse, String var1, String var2) {
        return stringVars(toParse, new String[]{var1, var2});
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

    static boolean wormholeExists(StarSystem a, StarSystem b) {
        StarSystem[] universe = Game.getCurrentGame().getUniverse();
        int[] wormholes = Game.getCurrentGame().getWormholes();
        // int i = Array.IndexOf(wormholes, (int) a.getId);
        int i = Util.bruteSeek(wormholes, a.getId().castToInt());

        return (i >= 0 && (universe[wormholes[(i + 1) % wormholes.length]] == b));
    }

    public static StarSystem wormholeTarget(int a) {
        int[] wormholes = Game.getCurrentGame().getWormholes();
        // int i = Array.IndexOf(wormholes, a);
        int i = Util.bruteSeek(wormholes, a);

        return (i >= 0 ? (Game.getCurrentGame().getUniverse()[wormholes[(i + 1) % wormholes.length]]) : null);
    }
}

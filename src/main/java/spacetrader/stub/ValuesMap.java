package spacetrader.stub;

import spacetrader.controls.Size;

import java.awt.*;
import java.util.HashMap;

public class ValuesMap extends HashMap<String, Object> {

    public String getString(String key) {
        //System.out.println("getString: " + key);
        return this.get(key).toString();
    }

    public int getInt(String key) {
        //System.out.println("getInt: " + key);
        return Integer.valueOf(getString(key));
    }

    public double getDouble(String key) {
        //System.out.println("getDouble: " + key);
        return Double.valueOf(getString(key));
    }

    public Size getSize(String key) {
        //System.out.println("getSize: " + key);
        return new Size(getInt(key + ".width"), getInt(key + ".height"));
    }

    public Point getLocation(String key) {
        //System.out.println("getLocation: " + key);
        return new Point(getInt(key + ".x"), getInt(key + ".y"));
    }
}

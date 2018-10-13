package spacetrader.util;

import jwinforms.Size;

import java.awt.*;
import java.util.HashMap;

public class ValuesMap extends HashMap<String, Object> {

    public String getString(String key) {
        return this.get(key).toString();
    }

    public int getInt(String key) {
        return Integer.valueOf(getString(key));
    }

    public double getDouble(String key) {
        return Double.valueOf(getString(key));
    }

    public Size getSize(String key) {
        return new Size(getInt(key + ".width"), getInt(key + ".height"));
    }

    public Point getLocation(String key) {
        return new Point(getInt(key + ".x"), getInt(key + ".y"));
    }
}

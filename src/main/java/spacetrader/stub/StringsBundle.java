package spacetrader.stub;

import java.util.HashMap;

/**
 * This class resembles ResourceBundle from Java.
 * The same set of key-value String pairs and simple methods for accessing them.
 */
public class StringsBundle extends HashMap<String, String> {

    public String getString(String key) {
        //System.out.println("getString: " + key);
        return this.get(key);
    }

    public String getText(String key) {
        //System.out.println("getText: " + key);
        return this.get(key + ".text");
    }

    public String getTitle(String key) {
        //System.out.println("getTitle: " + key);
        return this.get(key + ".title");
    }

}

package spacetrader.stub;

import java.util.HashMap;

/**
 * This class resembles ResourceBundle from Java.
 * The same set of key-value String pairs and simple methods for accessing them.
 */
public class StringsBundle extends HashMap<String, String> {

    public String get(String... keys) {
        //System.out.println("getString: " + key);
        return super.get(String.join(".", keys));
    }

    public String getString(String... keys) {
        //System.out.println("getString: " + key);
        return this.get(String.join(".", keys));
    }

    public String getText(String... keys) {
        //System.out.println("getText: " + key);
        return this.get(String.join(".", keys) + ".text");
    }

    public String getTitle(String... keys) {
        //System.out.println("getTitle: " + key);
        return this.get(String.join(".", keys) + ".title");
    }

    public String getMessage(String... keys) {
        //System.out.println("getTitle: " + key);
        return this.get(String.join(".", keys) + ".message");
    }

}

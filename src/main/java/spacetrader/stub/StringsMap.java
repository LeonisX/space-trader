package spacetrader.stub;

import java.util.HashMap;

public class StringsMap extends HashMap<String, String> {

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

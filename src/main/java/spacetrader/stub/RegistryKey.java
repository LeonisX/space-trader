package spacetrader.stub;

import java.io.*;
import java.util.Properties;

public class RegistryKey {

    private final Properties properties = new Properties();
    private final File file;

    public RegistryKey(File regFile) {
        this.file = regFile;
        FileInputStream stream = null;
        try {
            regFile.createNewFile();
            stream = new FileInputStream(regFile);
            properties.load(stream);
        } catch (IOException e) {
            throw new Error("Can't create/load regfile.");
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void close() {
        FileOutputStream stream;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            properties.store(stream, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setValue(String settingName, String settingValue) {
        properties.setProperty(settingName, settingValue);

    }

    public Object getValue(String settingName) {
        return properties.getProperty(settingName);
    }

}

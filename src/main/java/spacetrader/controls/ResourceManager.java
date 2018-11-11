package spacetrader.controls;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

public class ResourceManager {

    private static final ClassLoader classLoader = ResourceManager.class.getClassLoader();
    final Properties properties = new Properties();
    private final String path;

    ResourceManager(URL resource, String path) {
        try {
            properties.load(new InputStreamReader(resource.openStream(), Charset.forName("utf-8")));
            this.path = path;
        } catch (Exception e) {
            throw new Error(e.getMessage() + ": trying to load url \"" + resource + "\"", e);
        }
    }

    //TODO simplify
    public ResourceManager(Class<?> className) {
        this(classLoader.getResource(classToPath(className) + className.getSimpleName() + ".properties"),
                classToPath(className));
    }

    private static String classToPath(Class<?> className) {
        String path = className.getCanonicalName().replace('.', '/');
        path = path.substring(0, path.lastIndexOf('/') + 1);
        return path;
    }

    public Object getObject(String key) {
        String objectType = properties.getProperty(key + ".type", null);

        if (objectType == null)
            throw new Error("No object type for: " + key);

        switch (objectType) {
            case "ImageListStreamer":
                // value is name of properties file with image names in it
                String streamFilename = properties.getProperty(key);
//			System.out.println(path + streamFilename);
                try {
                    return new ImageStreamResourceManager(classLoader.getResource(path + streamFilename), path).getStream();
                    //			return new ImageStreamResourceManager(classLoader.getResource(streamFilename)).getStream();
                } catch (NullPointerException e) {
                    throw new Error("NPE while seeking for " + streamFilename);
                }
            case "Image":
                String imageName = properties.getProperty(key);
                return getImage(imageName);
            default:
                throw new Error("Unknown object type " + objectType);
        }
    }

    public static Object getImage(String imageName) {
        return new Bitmap(classLoader.getResource(imageName.trim()));
    }
}

package jwinforms;

import java.net.URL;
import java.util.Properties;

public class ResourceManager
{
	protected final Properties properties = new Properties();
	private final String path;
	private static final ClassLoader classLoader = ResourceManager.class.getClassLoader();

	protected ResourceManager(URL resource, String path)
	{
		try
		{
			properties.load(resource.openStream());
			this.path = path;
		} catch (Exception e)
		{
			throw new Error(e.getMessage()+ ": trying to load url \"" + resource+"\"", e);
		}
	}

	public ResourceManager(Class<?> className)
	{
		this(classLoader.getResource(classToPath(className) + className.getSimpleName() + ".properties"),
				classToPath(className));
	}

	private static String classToPath(Class<?> className)
	{
		String path = className.getCanonicalName().replace('.', '/');
		path = path.substring(0, path.lastIndexOf('/') + 1);
//		System.out.println("classToPath " + path);
		return path;
	}

	public Object GetObject(String key)
	{
		String objectType = properties.getProperty(key + ".type", null);

		if (objectType == null)
			throw new Error("No object type for: " + key);

		if (objectType.equals("ImageListStreamer"))
		{
			// value is name of properties file with image names in it
			String streamFilename = properties.getProperty(key);
//			System.out.println(path + streamFilename);
			try
			{
				return new ImageStreamResourceManager(classLoader.getResource(path + streamFilename), path).getStream();
				//			return new ImageStreamResourceManager(classLoader.getResource(streamFilename)).getStream();
			} catch (NullPointerException e)
			{
				throw new Error("NPE while seeking for " + streamFilename );
			}
		} else if (objectType.equals("Image"))
		{
			String imageName = properties.getProperty(key);
			return getImage(imageName);
		} else
			throw new Error("Uknown object type " + objectType);
	}

	Object getImage(String imageName)
	{
		return new Bitmap(classLoader.getResource(imageName.trim()));
	}

	public String GetString(String key)
	{
		return properties.getProperty(key);
	}
}

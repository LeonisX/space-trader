package util;


import java.io.File;

public class Path
{
	public static String Combine(String baseDirectory, String subdir)
	{
		return baseDirectory + File.separator + subdir;
	}

	public static String DefaultExtension(String filename, String extension)
	{
		return GetExtension(filename) == null ? filename + extension : filename;
	}

	public static String RemoveExtension(String filename)
	{
		int sep = filename.lastIndexOf(File.separatorChar);
		int dot = filename.lastIndexOf('.');
		if (dot <= sep)
			return filename;
		else
			return filename.substring(0, dot);
	}

	public static String GetExtension(String filename)
	{
		int sep = filename.lastIndexOf(File.separatorChar);
		int dot = filename.lastIndexOf('.');
		if (dot <= sep)
			return null;
		return filename.substring(dot);
	}
}

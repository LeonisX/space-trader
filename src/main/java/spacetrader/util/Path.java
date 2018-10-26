package spacetrader.util;


import java.io.File;

public class Path {

    public static String combine(String baseDirectory, String subdir) {
        return baseDirectory + File.separator + subdir;
    }

    public static String defaultExtension(String filename, String extension) {
        return getExtension(filename) == null ? filename + extension : filename;
    }

    public static String removeExtension(String filename) {
        int sep = filename.lastIndexOf(File.separatorChar);
        int dot = filename.lastIndexOf('.');
        if (dot <= sep) {
            return filename;
        } else {
            return filename.substring(0, dot);
        }
    }

    public static String getExtension(String filename) {
        int sep = filename.lastIndexOf(File.separatorChar);
        int dot = filename.lastIndexOf('.');
        if (dot <= sep) {
            return null;
        }
        return filename.substring(dot);
    }
}

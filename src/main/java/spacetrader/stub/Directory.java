package spacetrader.stub;

import spacetrader.util.Lisp;

import java.io.File;
import java.util.List;

public class Directory {

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static void createDirectory(String path) {
        if (!new File(path).mkdir()) {
            System.out.println("Couldn't make dir " + path);
        }
    }

    public static String[] getFiles(String path, String filter) {
        if (!filter.startsWith("*.")) {
            new Error("unsupported format").printStackTrace();
        }

        final String suffix = filter.substring(2);

        File[] files = new File(path).listFiles((arg0, filename) -> filename.endsWith(suffix));

        if (files == null) {
            System.out.println("getFiles returns null!");
            return new String[0];
        }
        List<String> names = Lisp.map(files, File::getPath);

        return names.toArray(new String[0]);
    }
}

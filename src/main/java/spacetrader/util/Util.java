package spacetrader.util;

import java.util.Arrays;

public class Util {

    public static <T> boolean arrayContains(T[] array, T item) {
        for (T t : array) {
            if (t == item) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sorts an array that may contain null values.
     * <p>
     * Puts null values at the beginning, because that's what I think C#'s Array.Sort method does.
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        Arrays.sort(array, (o1, o2) -> {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            return o1.compareTo(o2);
        });
    }

    /**
     * Sorts an array that may contain null values.
     * <p>
     * Puts null values at the beginning, because that's what I think C#'s Array.Sort method does.
     */
    public static <T extends Comparable<T>> int compareTo(T o1, T o2) {
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        if (o1 == o2) {
            return 0;
        }
        return o1.compareTo(o2);
    }

    public static int bruteSeek(int[] array, int a) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                return i;
            }
        }
        return -1;
    }
}

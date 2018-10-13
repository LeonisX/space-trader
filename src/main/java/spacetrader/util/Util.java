package spacetrader.util;

import java.util.Arrays;
import java.util.Comparator;

public class Util
{
	public static <T> boolean ArrayContains(T[] array, T item)
	{
		for (T t : array)
			if (t == item)
				return true;
		return false;
	}

	/**
	 * Sorts an array that may contain null values.
	 *
	 * Puts null values at the begining, bacause that's what I think C#'s Array.Sort method does.
	 */
	public static <T extends Comparable<T>> void sort(T[] array)
	{
		Arrays.sort(array, new Comparator<T>()
		{
			public int compare(T o1, T o2)
			{
				if (o1 == o2)
					return 0;
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;
				return o1.compareTo(o2);
			}
		});
	}

	public static int BruteSeek(int[] array, int a)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == a)
				return i;
		}
		return -1;
	}

	public static String StringsJoin(String seperator, String[] values)
	{
		StringBuilder sb = new StringBuilder();
		for (String string : values)
		{
			sb.append(string);
			sb.append(seperator);
		}
		return sb.toString();
	}
}

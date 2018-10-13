package util;

import java.util.LinkedList;
import java.util.List;

public final class Lisp
{
	private Lisp()
	{
	}

	public static <To, From> List<To> map(Iterable<From> k,
			Convertor<To, From> conv)
	{
		LinkedList<To> fname = new LinkedList<To>();
		for (From from : k)
			fname.addLast(conv.convert(from));
		return fname;
	}

	public static <To, From> List<To> map(From[] k,
			Convertor<To, From> conv)
	{
		LinkedList<To> fname = new LinkedList<To>();
		for (From from : k)
			fname.addLast(conv.convert(from));
		return fname;
	}

	public static <T> List<T> filter(T[] k, Predicate<T> pred)
	{
		LinkedList<T> fname = new LinkedList<T>();
		for (T t : k)
			if (pred.consider(t))
				fname.addLast(t);
		return fname;
	}
	
	public static <T> List<T> filter(Iterable<T> k, Predicate<T> pred)
	{
		LinkedList<T> fname = new LinkedList<T>();
		for (T t : k)
			if (pred.consider(t))
				fname.addLast(t);
		return fname;
	}
}

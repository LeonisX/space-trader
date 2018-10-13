package spacetrader.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import spacetrader.enums.SpaceTraderEnum;

public class DWIM
{
	/**
	 * even if it's of the right type, I still take the value from the "canon"
	 * collections, because the input is likely to be from the persistence;
	 * AFAIK, even for enums, the value read from the serializer, while being
	 * equals() to the canon values, are not == to them. i.e.:
	 * <code>
	 * MyEnum val = MyEnum.value;
	 * storage.serialize(val);
	 * val = storage.deserialize();
	 *
	 * boolean equals = MyEnum.value(val); // true
	 * boolean same = MyEnum.value == val; // false.
	 * </code>
	 *
	 * @author Aviv Eyal, 2008
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SpaceTraderEnum> T dwim(Object ob, Class<T> cls)
	{
		int intvalue;

		if (ob instanceof Integer)
			intvalue = (Integer)ob;
		else if (ob instanceof SpaceTraderEnum)
			intvalue = ((SpaceTraderEnum)ob).CastToInt();
		else
			throw new Error("Unknown value: type is " + ob.getClass()
					+ " tostring is " + ob);

		try
		{
			return (T)getFromInt(cls).invoke(null, intvalue);
		} catch (Exception e)
		{
			throw new Error("dwim("+ob.getClass()+", "+cls+") ",e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends SpaceTraderEnum> T[] dwim(Object[] ob, Class<T> cls)
	{
		try
		{
			T[] arrayVal = (T[])Array.newInstance(cls, ob.length);
			for (int i = 0; i < ob.length; i++)
				arrayVal[i] = dwim(ob[i], cls);
			return arrayVal;
		} catch (Exception e)
		{
			throw new Error("dwim[]("+ob.getClass()+", "+cls+") ",e);
		}
	}

	private static Method getFromInt(Class<?> cls) throws Exception
	{
		return cls.getMethod("FromInt", int.class);
	}
}

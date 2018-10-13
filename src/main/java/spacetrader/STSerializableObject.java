/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
package spacetrader;

import java.util.List;

import spacetrader.enums.SpaceTraderEnum;
import spacetrader.stub.ArrayList;
import spacetrader.util.DWIM;
import spacetrader.util.Hashtable;

public abstract class STSerializableObject
{
	public STSerializableObject()
	{}

	protected STSerializableObject(Hashtable hash)
	{}

	/**
	 * Types currently supported:
	 * <ul>
	 * <li>CrewMember</li>
	 * <li>Gadget</li>
	 * <li>HighScoreRecord</li>
	 * <li>Shield</li>
	 * <li>StarSystem</li>
	 * <li>Weapon</li>
	 * </ul>
	 *
	 * If an array of a type not listed is converted using {@link #ArrayToArrayList(STSerializableObject[]) ArrayToArrayList}, the
	 * type needs to be added here.
	 */
	public static STSerializableObject[] ArrayListToArray(List<Hashtable> list, String typeName)
	{
		STSerializableObject[] array;

		SupportedTypesOfSomethingST type = SupportedTypesOfSomethingST.valueOf(typeName);

		if (list == null)
			return null;

		switch (type)
		{
		case CrewMember:
			array = new CrewMember[list.size()];
			break;
		case Gadget:
			array = new Gadget[list.size()];
			break;
		case HighScoreRecord:
			array = new HighScoreRecord[list.size()];
			break;
		case Shield:
			array = new Shield[list.size()];
			break;
		case StarSystem:
			array = new StarSystem[list.size()];
			break;
		case Weapon:
			array = new Weapon[list.size()];
			break;
		default:
			throw new RuntimeException("Unknown SuppType: " + type);
		}

		for (int index = 0; index < list.size(); index++)
		{
			Hashtable hash = list.get(index);
			STSerializableObject obj;
			if (hash == null)
			{
				obj = null;
			} else
			{
				switch (type)
				{
				case CrewMember:
					obj = new CrewMember(hash);
					break;
				case Gadget:
					obj = new Gadget(hash);
					break;
				case HighScoreRecord:
					obj = new HighScoreRecord(hash);
					break;
				case Shield:
					obj = new Shield(hash);
					break;
				case StarSystem:
					obj = new StarSystem(hash);
					break;
				case Weapon:
					obj = new Weapon(hash);
					break;
				default:
					throw new RuntimeException("Unknown SuppType: " + type);
				}
			}

			array[index] = obj;
		}

		return array;
	}

	private static enum SupportedTypesOfSomethingST
	{
		CrewMember, Gadget, HighScoreRecord, Shield, StarSystem, Weapon
	}

	@SuppressWarnings("cast")
	public static Integer[] ArrayListToIntArray(ArrayList<? extends SpaceTraderEnum> list)
	{
		Integer[] array = new Integer[list.size()];
		if (list.size() == 0)
			return array;

		{
			// Sometimes weird stuff happens when you mess with casts & generics.
			if ((Object)list.get(0) instanceof Integer)
				return list.toArray(array);
		}

		for (int index = 0; index < array.length; index++)
			array[index] = list.get(index).CastToInt();

		return array;
	}

	public static ArrayList<Hashtable> ArrayToArrayList(STSerializableObject[] array)
	{
		ArrayList<Hashtable> list = null;

		if (array != null)
		{
			list = new ArrayList<Hashtable>();

			for (STSerializableObject obj : array)
				list.add(obj == null ? null : obj.Serialize());
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static <U> U GetValueFromHash(Hashtable hash, String key, Class<U> requstedType)
	{
		if (!hash.containsKey(key))
			return null;

		Object object = hash.get(key);
		if (object instanceof SpaceTraderEnum)
			return (U)(Integer)((SpaceTraderEnum)object).CastToInt();
		else
			return (U)object;
	}

	@SuppressWarnings("unchecked")
	public static <U extends SpaceTraderEnum> U GetValueFromHash(Hashtable hash, String key, Class<U> requstedType)
	{
		if (!hash.containsKey(key))
			return null;

		Object object = hash.get(key);
		if (object instanceof Integer)
			return DWIM.dwim(object, requstedType);
		else
			return (U)object;
	}

	@SuppressWarnings("unchecked")
	public static <U, T extends U> U GetValueFromHash(Hashtable hash, String key, T defaultValue, Class<U> requstedType)
	{
		if (!hash.containsKey(key))
			return defaultValue;

		if (SpaceTraderEnum.class.isAssignableFrom(requstedType))
			return (U)DWIM.dwim(hash.get(key), (Class<? extends SpaceTraderEnum>)requstedType);
		else
			return (U)hash.get(key);
	}

	//TODO many of calls to this method then cast it back to the enum type; fix them to call generic form.
	public static int GetValueFromHash(Hashtable hash, String key, SpaceTraderEnum defaultValue,
			Class<Integer> requstedType)
	{
		if (!hash.containsKey(key))
			return defaultValue.CastToInt();

		Object saved = hash.get(key);
		if (saved instanceof Integer)
			return (Integer)saved;
		else
			//Assume its the enum
			return ((SpaceTraderEnum)saved).CastToInt();
	}

	@SuppressWarnings("unchecked")
	public static <T> T GetValueFromHash(Hashtable hash, String key, T defaultValue)
	{
		return GetValueFromHash(hash, key, defaultValue, (Class<T>)defaultValue.getClass());
	}

	public static int GetValueFromHash(Hashtable hash, String key, int defaultValue)
	{
		return hash.containsKey(key) ? (Integer)hash.get(key) : defaultValue;
	}

	public static boolean GetValueFromHash(Hashtable hash, String key, boolean defaultValue)
	{
		return hash.containsKey(key) ? (Boolean)hash.get(key) : defaultValue;
	}

	public Hashtable Serialize()
	{
		return new Hashtable();
	}
}

package spacetrader.game;

import spacetrader.game.enums.SpaceTraderEnum;
import spacetrader.stub.ArrayList;
import spacetrader.util.DWIM;
import spacetrader.util.Hashtable;

import java.util.List;

public abstract class STSerializableObject {

    public STSerializableObject() {
    }

    /**
     * Types currently supported:
     * <ul>
     * <li>CrewMember</li>
     * <li>Gadget</li>
     * <li>Shield</li>
     * <li>StarSystem</li>
     * <li>Weapon</li>
     * </ul>
     * <p>
     * If an array of a type not listed is converted using {@link #arrayToArrayList(STSerializableObject[]) arrayToArrayList}, the
     * type needs to be added here.
     */
    public static STSerializableObject[] arrayListToArray(List<Hashtable> list, String typeName) {
        STSerializableObject[] array;

        SupportedTypesOfSomethingST type = SupportedTypesOfSomethingST.valueOf(typeName);

        if (list == null) {
            return null;
        }

        switch (type) {
            case CrewMember:
                array = new CrewMember[list.size()];
                break;
            case Gadget:
                array = new Gadget[list.size()];
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

        for (int index = 0; index < list.size(); index++) {
            Hashtable hash = list.get(index);
            STSerializableObject obj;
            if (hash == null) {
                obj = null;
            } else {
                switch (type) {
                    case CrewMember:
                        obj = new CrewMember(hash);
                        break;
                    case Gadget:
                        obj = new Gadget(hash);
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

    private enum SupportedTypesOfSomethingST {
        CrewMember, Gadget, HighScoreRecord, Shield, StarSystem, Weapon
    }

    static Integer[] arrayListToIntArray(ArrayList<? extends SpaceTraderEnum> list) {
        Integer[] array = new Integer[list.size()];
        if (list.size() == 0) {
            return array;
        }

        {
            // Sometimes weird stuff happens when you mess with casts & generics.
            if ((Object) list.get(0) instanceof Integer)
                return list.toArray(array);
        }

        for (int index = 0; index < array.length; index++) {
            array[index] = list.get(index).castToInt();
        }

        return array;
    }

    static ArrayList<Hashtable> arrayToArrayList(STSerializableObject[] array) {
        ArrayList<Hashtable> list = null;

        if (array != null) {
            list = new ArrayList<>();

            for (STSerializableObject obj : array) {
                list.add(obj == null ? null : obj.serialize());
            }
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static <U> U getValueFromHash(Hashtable hash, String key, Class<U> requestedType) {
        if (!hash.containsKey(key)) {
            return null;
        }

        Object object = hash.get(key);
        if (object instanceof SpaceTraderEnum) {
            return (U) (Integer) ((SpaceTraderEnum) object).castToInt();
        } else {
            return (U) object;
        }
    }

    @SuppressWarnings("unchecked")
    public static <U extends SpaceTraderEnum> U getValueFromHash2(Hashtable hash, String key, Class<U> requestedType) {
        if (!hash.containsKey(key)) {
            return null;
        }

        Object object = hash.get(key);
        if (object instanceof Integer) {
            return DWIM.dwim(object, requestedType);
        } else {
            return (U) object;
        }
    }

    @SuppressWarnings("unchecked")
    public static <U, T extends U> U getValueFromHash(Hashtable hash, String key, T defaultValue, Class<U> requestedType) {
        if (!hash.containsKey(key)) {
            return defaultValue;
        }

        if (SpaceTraderEnum.class.isAssignableFrom(requestedType)) {
            return (U) DWIM.dwim(hash.get(key), (Class<? extends SpaceTraderEnum>) requestedType);
        } else {
            return (U) hash.get(key);
        }
    }

    //TODO many of calls to this method then cast it back to the enum type; fix them to call generic form.
    public static int getValueFromHash(Hashtable hash, String key, SpaceTraderEnum defaultValue,
                                       Class<Integer> requestedType) {
        if (!hash.containsKey(key)) {
            return defaultValue.castToInt();
        }

        Object saved = hash.get(key);
        if (saved instanceof Integer) {
            return (Integer) saved;
        } else {
            //Assume its the enum
            return ((SpaceTraderEnum) saved).castToInt();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValueFromHash(Hashtable hash, String key, T defaultValue) {
        return getValueFromHash(hash, key, defaultValue, (Class<T>) defaultValue.getClass());
    }

    public static int getValueFromHash(Hashtable hash, String key, int defaultValue) {
        return hash.containsKey(key) ? (Integer) hash.get(key) : defaultValue;
    }

    public static boolean getValueFromHash(Hashtable hash, String key, boolean defaultValue) {
        return hash.containsKey(key) ? (Boolean) hash.get(key) : defaultValue;
    }

    public Hashtable serialize() {
        return new Hashtable();
    }
}
package spacetrader.util;

import spacetrader.enums.SpaceTraderEnum;

import java.lang.reflect.Method;

public class DWIM {
    /**
     * even if it's of the right type, I still take the value from the "canon"
     * collections, because the input is likely to be from the persistence;
     * AFAIK, even for enums, the value read from the serializer, while being
     * equals() to the canon values, are not == to them. i.e.:
     * <code>
     * MyEnum val = MyEnum.value;
     * storage.serialize(val);
     * val = storage.deserialize();
     * <p>
     * boolean equals = MyEnum.value(val); // true
     * boolean same = MyEnum.value == val; // false.
     * </code>
     *
     * @author Aviv Eyal, 2008
     */
    @SuppressWarnings("unchecked")
    public static <T extends SpaceTraderEnum> T dwim(Object ob, Class<T> cls) {
        int intValue;

        if (ob instanceof Integer)
            intValue = (Integer) ob;
        else if (ob instanceof SpaceTraderEnum)
            intValue = ((SpaceTraderEnum) ob).castToInt();
        else
            throw new Error("Unknown value: type is " + ob.getClass()
                    + " toString is " + ob);

        try {
            return (T) getFromInt(cls).invoke(null, intValue);
        } catch (Exception e) {
            throw new Error("dwim(" + ob.getClass() + ", " + cls + ") ", e);
        }
    }

    private static Method getFromInt(Class<?> cls) throws Exception {
        return cls.getMethod("fromInt", int.class);
    }
}

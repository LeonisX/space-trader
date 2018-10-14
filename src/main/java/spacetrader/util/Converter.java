package spacetrader.util;

public interface Converter<To, From> {

    To convert(From f);

}

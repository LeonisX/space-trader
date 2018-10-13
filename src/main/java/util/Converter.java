package util;

public interface Converter<To, From> {

    To convert(From f);

}

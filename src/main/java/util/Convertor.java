package util;

public interface Convertor<To, From>
{
	To convert(From f);
}
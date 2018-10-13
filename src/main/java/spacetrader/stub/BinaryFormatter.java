package spacetrader.stub;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BinaryFormatter
{

	public Object Deserialize(FileInputStream inStream) throws SerializationException, IOException
	{
		try
		{
			return new ObjectInputStream(inStream).readObject();
		} catch (ClassNotFoundException e)
		{
			throw new SerializationException(e);
		}
	}

	public void Serialize(FileOutputStream outStream, Object toSerialize) throws IOException
	{
		new ObjectOutputStream(outStream).writeObject(toSerialize);
	}

}

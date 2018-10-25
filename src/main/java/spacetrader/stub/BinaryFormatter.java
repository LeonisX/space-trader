package spacetrader.stub;

import java.io.*;

public class BinaryFormatter {

    public Object deserialize(FileInputStream inStream) throws SerializationException, IOException {
        try {
            return new ObjectInputStream(inStream).readObject();
        } catch (ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }

    public void serialize(FileOutputStream outStream, Object toSerialize) throws IOException {
        new ObjectOutputStream(outStream).writeObject(toSerialize);
    }

}

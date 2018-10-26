package spacetrader.stub;

public class SerializationException extends Exception {

    public SerializationException() {
        super();
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(String message) {
        super(message);
    }

    SerializationException(Throwable cause) {
        super(cause);
    }
}
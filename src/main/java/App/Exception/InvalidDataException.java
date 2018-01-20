package App.Exception;

public class InvalidDataException extends Exception {

    public InvalidDataException (String info) {
        super(info);
    }

    public InvalidDataException(String info, Throwable throwable) {
        super(info, throwable);
    }
}

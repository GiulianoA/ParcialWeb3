package ar.edu.iua.project.parcial.exceptions;

public class InvalidListNameException extends Exception{
    public InvalidListNameException() {
        super();
    }

    public InvalidListNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidListNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidListNameException(String message) {
        super(message);
    }

    public InvalidListNameException(Throwable cause) {
        super(cause);
    }
}

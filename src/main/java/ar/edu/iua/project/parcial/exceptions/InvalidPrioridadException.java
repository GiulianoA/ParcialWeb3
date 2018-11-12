package ar.edu.iua.project.parcial.exceptions;

public class InvalidPrioridadException extends Exception{
	
	public InvalidPrioridadException() {
        super();
    }

    public InvalidPrioridadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidPrioridadException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPrioridadException(String message) {
        super(message);
    }

    public InvalidPrioridadException(Throwable cause) {
        super(cause);
    }
}

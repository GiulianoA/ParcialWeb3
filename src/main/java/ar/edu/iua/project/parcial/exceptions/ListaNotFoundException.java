package ar.edu.iua.project.parcial.exceptions;

public class ListaNotFoundException extends Exception{
    public ListaNotFoundException() {
        super();
    }

    public ListaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ListaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListaNotFoundException(String message) {
        super(message);
    }

    public ListaNotFoundException(Throwable cause) {
        super(cause);
    }
}

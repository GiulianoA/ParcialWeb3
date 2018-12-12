package ar.edu.iua.project.parcial.exceptions;

public class ListaDestinoInvalidaException extends Exception{

    public ListaDestinoInvalidaException() {
        super();
    }

    public ListaDestinoInvalidaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ListaDestinoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListaDestinoInvalidaException(String message) {
        super(message);
    }

    public ListaDestinoInvalidaException(Throwable cause) {
        super(cause);
    }
}



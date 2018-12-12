package ar.edu.iua.project.parcial.exceptions;

public class ListaYaExisteException extends Exception {
    public ListaYaExisteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ListaYaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListaYaExisteException(String message) {
        super(message);
    }

    public ListaYaExisteException(Throwable cause) {
        super(cause);
    }
}

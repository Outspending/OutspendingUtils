package me.outspending.spendingutils.Threading.Exceptions;

public class UnstableThreadException extends Exception {

    public UnstableThreadException(String message) {
        super(message);
    }

    public UnstableThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnstableThreadException(Throwable cause) {
        super(cause);
    }
}

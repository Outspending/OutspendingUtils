package me.outspending.spendingutils.Variables.Exceptions;

public class UnknownVariableException extends Exception {

    public UnknownVariableException(String message) {
        super(message);
    }

    public UnknownVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownVariableException(Throwable cause) {
        super(cause);
    }

    public UnknownVariableException() {
        super();
    }
}

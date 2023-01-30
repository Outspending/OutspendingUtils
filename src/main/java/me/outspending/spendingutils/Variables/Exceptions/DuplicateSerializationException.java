package me.outspending.spendingutils.Variables.Exceptions;

public class DuplicateSerializationException extends Exception {

    public DuplicateSerializationException(Class<?> clazz) {
        super("Serialization for class " + clazz.getName() + " already exists!");
    }
}

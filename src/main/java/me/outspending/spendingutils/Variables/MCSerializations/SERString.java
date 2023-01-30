package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERString implements Serialization<String> {

    private Class<?> string = String.class;

    @Override
    public Class<?> getClazz() {
        return string;
    }

    @Override
    public String serialize(String value) {
        return value;
    }

    @Override
    public String deserialize(String string) {
        return string;
    }
}

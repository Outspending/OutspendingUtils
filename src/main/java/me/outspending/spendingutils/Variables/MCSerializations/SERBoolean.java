package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERBoolean implements Serialization<Boolean> {

    private Class<?> aBoolean = Boolean.class;

    @Override
    public Class<?> getClazz() {
        return aBoolean;
    }

    @Override
    public String serialize(Boolean value) {
        return String.valueOf(value);
    }

    @Override
    public Boolean deserialize(String string) {
        return Boolean.parseBoolean(string);
    }
}

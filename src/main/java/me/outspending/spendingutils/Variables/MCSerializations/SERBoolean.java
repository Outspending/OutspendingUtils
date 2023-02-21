package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERBoolean implements Serialization<Boolean> {


    @Override
    public Class<Boolean> getClazz() {
        return Boolean.class;
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

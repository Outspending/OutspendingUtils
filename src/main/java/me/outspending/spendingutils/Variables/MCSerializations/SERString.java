package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERString implements Serialization<String> {

    @Override
    public Class<String> getClazz() {
        return String.class;
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

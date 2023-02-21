package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERLong implements Serialization<Long> {

    @Override
    public Class<Long> getClazz() {
        return Long.class;
    }

    @Override
    public String serialize(Long value) {
        return String.valueOf(value);
    }

    @Override
    public Long deserialize(String string) {
        return Long.parseLong(string);
    }
}

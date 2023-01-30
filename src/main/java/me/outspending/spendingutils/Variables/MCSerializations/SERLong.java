package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERLong implements Serialization<Long> {

    private Class<?> aLong = Long.class;

    @Override
    public Class<?> getClazz() {
        return aLong;
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

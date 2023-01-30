package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERInteger implements Serialization<Integer> {

    private Class<?> integer = Integer.class;

    @Override
    public Class<?> getClazz() {
        return integer;
    }

    @Override
    public String serialize(Integer value) {
        return String.valueOf(value);
    }

    @Override
    public Integer deserialize(String string) {
        return Integer.parseInt(string);
    }
}

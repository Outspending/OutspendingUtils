package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERDouble implements Serialization<Double> {

    private Class<?> aDouble = Double.class;

    @Override
    public Class<?> getClazz() {
        return aDouble;
    }

    @Override
    public String serialize(Double value) {
        return String.valueOf(value);
    }

    @Override
    public Double deserialize(String string) {
        return Double.parseDouble(string);
    }
}

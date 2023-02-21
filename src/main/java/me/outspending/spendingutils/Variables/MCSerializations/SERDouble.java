package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERDouble implements Serialization<Double> {

    @Override
    public Class<Double> getClazz() {
        return Double.class;
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

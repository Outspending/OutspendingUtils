package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERFloat implements Serialization<Float> {

    private Class<?> aFloat = Float.class;

    @Override
    public Class<?> getClazz() {
        return aFloat;
    }

    @Override
    public String serialize(Float value) {
        return String.valueOf(value);
    }

    @Override
    public Float deserialize(String string) {
        return Float.parseFloat(string);
    }
}

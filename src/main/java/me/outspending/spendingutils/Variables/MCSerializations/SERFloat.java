package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;

public class SERFloat implements Serialization<Float> {


    @Override
    public Class<Float> getClazz() {
        return Float.class;
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

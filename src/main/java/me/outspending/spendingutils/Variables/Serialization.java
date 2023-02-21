package me.outspending.spendingutils.Variables;

import javax.xml.stream.Location;

public interface Serialization<T> {


    public Class<T> getClazz();

    public String serialize(T value);
    public T deserialize(String string);
}

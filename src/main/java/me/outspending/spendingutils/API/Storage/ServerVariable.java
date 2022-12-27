package me.outspending.spendingutils.API.Storage;

public class ServerVariable<T> implements Variable, VariableData {

    private T value;
    private String name;

    public ServerVariable(String name, T value) {
        this.name = name;
        this.value = value;
        variables.put(name, this);
    }

    public void setValue(T value) {
        ((ServerVariable<T>) variables.get(name)).value = value;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public double getValueAsDouble() {
        return Double.parseDouble(value.toString());
    }

    public int getValueAsInt() {
        return Integer.parseInt(value.toString());
    }

    public String getValueAsString() {
        return value.toString();
    }

    public boolean getValueAsBoolean() {
        return Boolean.parseBoolean(value.toString());
    }

    public float getValueAsFloat() {
        return Float.parseFloat(value.toString());
    }

    public long getValueAsLong() {
        return Long.parseLong(value.toString());
    }

    public String getName() {
        return name;
    }

    // Public Methods

    public static ServerVariable<Object> createVariable(String name, Object value) {
        return new ServerVariable<>(name, value);
    }

    public static ServerVariable<Object> createOrGetVariable(String name, Object value) {
        if (variables.containsKey(name)) {
            return (ServerVariable<Object>) variables.get(name);
        }
        return new ServerVariable<>(name, value);
    }

    public static ServerVariable<?> getVariable(String name) {
        return (ServerVariable<?>) variables.get(name);
    }
}

package me.outspending.spendingutils.Variables;

public class ServerVariable<T> implements Variable {

    private final String name;
    private T value;

    public ServerVariable(String name, T value) {
        this.name = name;
        this.value = value;
        VariableStorage.variables.put(name, this);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        ((ServerVariable) VariableStorage.variables.get(name)).value = value;
    }
}

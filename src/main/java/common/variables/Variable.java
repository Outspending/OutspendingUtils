package common.variables;

public class Variable<T> implements VariableInformation {

    private T value;
    private final String name;

    public Variable(String name, T value) {
        this.name = name;
        this.value = value;
        variables.put(name, this);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        variables.get(name).value = value;
    }

    public String getName() {
        return name;
    }
}

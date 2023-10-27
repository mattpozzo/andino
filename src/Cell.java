package src;

public class Cell<T> {
    private T value;

    public Cell(T value) {
        setValue(value); // Utilizando el setter para centralizar la lógica de validación
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T newValue) {
        if (newValue instanceof Number || newValue instanceof String || newValue instanceof Boolean) {
            this.value = newValue;
        } else {
            throw new IllegalArgumentException("Invalid type for Cell: " + newValue.getClass().getName());
        }
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public boolean isNull() {
        return this.value == null;
    }
}
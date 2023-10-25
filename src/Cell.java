package src;

public class Cell<T> {
    private T value;

    public Cell(T value) {
        if (value instanceof Number || value instanceof String || value instanceof Boolean) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Invalid type for Cell: " + value.getClass().getName());
        }
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T newValue) {
        if (newValue instanceof Number || newValue instanceof String || newValue instanceof Boolean) {
            this.value = newValue;
        } else {
            throw new IllegalArgumentException("Invalid type for Cell: " + value.getClass().getName());
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

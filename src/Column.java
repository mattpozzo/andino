// Column.java
package src;
import java.util.ArrayList;

public class Column<T> implements Comparable<Column<T>>{
    private ArrayList<Cell<T>> cells = new ArrayList<>();

    public Column() {}

    public Column(T[] cellsElements) {
        for (int i=0; i < cellsElements.length; i++) {
            this.cells.add(new Cell<>(cellsElements[i]));
        }
    }

    public T getCellValue(int index) {
        return this.cells.get(index).getValue();
    }

    public int getSize() {
        return cells.size();
    }

    public void addCell() {
        this.cells.add(new Cell<>(null));
    }

    public void addCell(T cell) {
        if (!this.cells.isEmpty() && cell != null) {
            Object firstValue = this.getCellValue(0);
            int firstCount = 1;

            while (firstValue == null && firstCount < this.cells.size()) {
                firstValue = this.getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue != null && cell.getClass() != firstValue.getClass()) {
                throw new IllegalArgumentException("El tipo de dato no coincide con el tipo de datos de la columna");
            }
        }

        this.cells.add(new Cell<>(cell));
    }

    public void addCell(T cell, int index) {
        if (!this.cells.isEmpty() && cell != null) {
            Object firstValue = this.getCellValue(0);
            int firstCount = 1;

            while (firstValue == null && firstCount < this.cells.size()) {
                firstValue = this.getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue != null && cell.getClass() != firstValue.getClass()) {
                throw new IllegalArgumentException("El tipo de dato no coincide con el tipo de datos de la columna");
            }
        }

        this.cells.add(index, new Cell<>(cell));
    }

    public void setCell(T cell, int index) {
        if (!this.cells.isEmpty() && cell != null) {
            Object firstValue = this.getCellValue(0);
            int firstCount = 1;

            while (firstValue == null && firstCount < this.cells.size()) {
                firstValue = this.getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue != null && cell.getClass() != firstValue.getClass()) {
                throw new IllegalArgumentException("El tipo de dato no coincide con el tipo de datos de la columna");
            }
        }

        this.cells.set(index, new Cell<>(cell));
    }

    public void removeIndex(int index) {
        this.cells.remove(index);
    }

    public void removeCell(T cell) {
        this.cells.remove(new Cell<>(cell));
    }

    public void replaceNull(T defaultValue) {
        for (Cell<T> cell : cells) {
            if (cell.isNull()) {
                cell.setValue(defaultValue);
            }
        }
    }

    @Override
    public String toString() {
        String column = "[";
        for (Cell<T> cell : cells) {
            column += cell.getValue() + ", ";
        }
        column = column.substring(0, column.length() - 2);
        column += "]";
        return column;
    }

    public T[] toArray() {
        T[] array = (T[]) new Object[this.cells.size()];
        for (int i=0; i < this.cells.size(); i++) {
            array[i] = this.cells.get(i).getValue();
        }
        return array;
    }

    @Override
    public int compareTo(Column<T> o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
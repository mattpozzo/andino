// Column.java
package src;
import java.util.ArrayList;

public class Column<T> {
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

    public void addCell(T cell) {
        this.cells.add(new Cell<>(cell));
    }

    public void addCell(T cell, int index) {
        this.cells.add(index, new Cell<>(cell));
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

    public T[] toArray() {
        T[] array = (T[]) new Object[this.cells.size()];
        for (int i=0; i < this.cells.size(); i++) {
            array[i] = this.cells.get(i).getValue();
        }
        return array;
    }
}
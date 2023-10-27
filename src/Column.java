// Column.java
package src;
import java.util.ArrayList;

public class Column<T> {
    private ArrayList<Cell<T>> cells = new ArrayList<>(); // Inicializar cells
    private String tag;

    public Column(T[] cellsElements, String tag) {
        for (int i=0; i < cellsElements.length; i++) {
            this.cells.add(new Cell<T>(cellsElements[i]));
        }
        this.tag = tag;
    }

    public T getCellValue(int index) {
        return this.cells.get(index).getValue();
    }

    public int size() {
        return cells.size();
    }
    public String getTag() {
        return this.tag;
    }


    public void replaceNull(T defaultValue) {
        for (Cell<T> cell : cells) {
            if (cell.isNull()) {
                cell.setValue(defaultValue);
            }
        }
    }
}
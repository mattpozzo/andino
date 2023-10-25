package src;
import java.util.ArrayList;


public class Column<T> {
    private ArrayList<Cell<T>> cells;
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

    public String getTag() {
        return this.tag;
    }

    public void replaceNull() {

    }
}

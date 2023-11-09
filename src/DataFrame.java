// DataFrame.java
package src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataFrame {
    private Map<Object, Column<Object>> columns = new HashMap<>();
    private ArrayList<Object> headers = new ArrayList<>();
    private ArrayList<Object> indexes = new ArrayList<>();

    public void addColumn(Object header, Column<Object> column) {
        if (!(header instanceof String) && !(header instanceof Integer)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El header debe ser String o Integer.");
        }

        if (column.getSize() != this.indexes.size()) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El tamaño de la columna no coincide con la cantidad de filas.");
        }

        if (this.headers.contains(header)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El header ya existe.");
        }

        this.columns.put(header, column);
        this.headers.add(header);
    }

    public Column<Object> getColumn(Object header) {
        return this.columns.get(header);
    }

    public void addRow(Object[] row) {
        if (row.length != this.columns.size()) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El tamaño de la fila no coincide con la cantidad de columnas.");
        }

        for (int i = 0; i < row.length; i++) {
            this.columns.get(this.headers.get(i)).addCell(row[i]);
        }

        ArrayList<Integer> intIndexes = new ArrayList<>();

        for (Object index : this.indexes) {
            if (index instanceof Integer) {
                intIndexes.add((Integer) index);
            }
        }

        if (!intIndexes.isEmpty()) {
            int max = intIndexes.get(0);
            for (int i = 1; i < intIndexes.size(); i++) {
                if (intIndexes.get(i) > max) {
                    max = intIndexes.get(i);
                }
            }
            this.indexes.add(max + 1);
        } else {
            this.indexes.add(0);
        }        
    }

    public void addRow(Object[] row, Object index) {
        if (row.length != this.columns.size()) {
            throw new IllegalArgumentException("El tamaño de la fila no coincide con la cantidad de columnas.");
        }

        if (this.indexes.contains(index)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El índice ya existe.");
        }

        for (int i = 0; i < row.length; i++) {
            this.columns.get(this.headers.get(i)).addCell(row[i]);
        }

        this.indexes.add(index); 
    }

    public ArrayList<Object> getRow(Object index) {
        ArrayList<Object> row = new ArrayList<>();
        for (Object header : this.headers) {
            row.add(this.columns.get(header).getCellValue(this.indexes.indexOf(index)));
        }
        return row;
    }

    public ArrayList<Object> getHeaders() {
        return this.headers;
    }

    public void setHeader(Object oldHeader, Object newHeader) {
        if (!this.headers.contains(oldHeader)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException(String.format("El header '%s' no existe.", oldHeader.toString()));
        }

        if (this.headers.contains(newHeader)) {
            throw new IllegalArgumentException(String.format("El header '%s' ya existe.", newHeader.toString()));
        }

        if (!(newHeader instanceof String) && !(newHeader instanceof Integer)) {
            throw new IllegalArgumentException("El nuevo header debe ser String o Integer.");
        }

        Column<Object> column = this.columns.get(oldHeader);

        this.columns.remove(oldHeader);

        this.columns.put(newHeader, column);

        this.headers.set(this.headers.indexOf(oldHeader), newHeader);
    }

    public ArrayList<Object> getIndexes() {
        return this.indexes;
    }

    public void setIndex(Object oldIndex, Object newIndex) {
        if (!this.indexes.contains(oldIndex)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException(String.format("El índice '%s' no existe.", oldIndex.toString()));
        }

        if (this.indexes.contains(newIndex)) {
            throw new IllegalArgumentException(String.format("El índice '%s' ya existe.", newIndex.toString()));
        }

        if (!(newIndex instanceof String) && !(newIndex instanceof Integer)) {
            throw new IllegalArgumentException("El nuevo índice debe ser String o Integer.");
        }

        int index = this.indexes.indexOf(oldIndex);

        this.indexes.set(index, newIndex);
    }

    public static void main(String[] args) throws Exception {
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        for (Object header : df.getHeaders()) {
            Column<?> column = df.getColumn(header);
            System.out.print(header + ": ");
            for (int i = 0; i < column.getSize(); i++) {
                System.out.print(column.getCellValue(i) + " ");
            }
            System.out.println();
        }
    }

}
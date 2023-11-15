// DataFrame.java
package src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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

    public List<Object> getRow(Object index) {
        ArrayList<Object> row = new ArrayList<>();
        for (Object header : this.headers) {
            row.add(this.columns.get(header).getCellValue(this.indexes.indexOf(index)));
        }
        return row;
    }

    public List<Object> getHeaders() {
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

    public List<Object> getIndexes() {
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

    public Object[][] toArray() {
        Object[][] array = new Object[this.indexes.size()][this.headers.size()];
        for (int i = 0; i < this.indexes.size(); i++) {
            for (int j = 0; j < this.headers.size(); j++) {
                array[i][j] = this.columns.get(this.headers.get(j)).getCellValue(i);
            }
        }
        return array;
    } 

    public DataFrame sort(Object header, boolean ascending) {
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException(String.format("El header '%s' no existe.", header.toString()));
        }

        Column<Object> column = this.columns.get(header);

        Object[] sortedColumn = column.toArray();

        Arrays.sort(sortedColumn);

        if (!ascending) {
            Object[] reversedColumn = new Object[sortedColumn.length];
            for (int i = 0; i < sortedColumn.length; i++) {
                reversedColumn[i] = sortedColumn[sortedColumn.length - 1 - i];
            }
            sortedColumn = reversedColumn;
        }

        DataFrame sortedDf = new DataFrame();

        for (Object headerName : this.headers) {
            Column<Object> newColumn = new Column<>(new Object[0]);
            sortedDf.addColumn(headerName, newColumn);
        }

        for (Object value : sortedColumn) {
            for (Object headerName : this.headers) {
                sortedDf.columns.get(headerName).addCell(
                    this.columns.get(headerName).getCellValue(
                        Arrays.asList(column.toArray()).indexOf(value)
                    )
                );
            }
        }

        return sortedDf;
    }

    // TODO: sort con varios headers para ordenar por más de una columna

    public DataFrame filter(Predicate<Object> predicate) {
        // TODO: REVISAR QUE ESTO ESTÉ BIEN
        DataFrame filteredDf = new DataFrame();

        for (Object headerName : this.headers) {
            Column<Object> newColumn = new Column<>(new Object[0]);
            filteredDf.addColumn(headerName, newColumn);
        }

        for (Object index : this.indexes) {
            if (predicate.test(index)) {
                for (Object headerName : this.headers) {
                    filteredDf.columns.get(headerName).addCell(
                        this.columns.get(headerName).getCellValue(
                            this.indexes.indexOf(index)
                        )
                    );
                }
            }
        }

        return filteredDf;
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
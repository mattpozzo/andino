// DataFrame.java
package src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Random;

public class DataFrame {
    protected Map<Object, Column<Object>> columns = new HashMap<>();
    protected List<Object> headers = new ArrayList<>();
    protected List<Object> indexes = new ArrayList<>();

    public void addColumn(Object header, Column<Object> column) {
        if (this.indexes.isEmpty()) {
            for (int i = 0; i < column.getSize(); i++) {
                this.indexes.add(i);
            }
        } else if (column.getSize() != this.indexes.size()) {
            throw new IllegalArgumentException("El tamaño de la columna no coincide con la cantidad de filas.");
        }
        this.columns.put(header, column);
        this.headers.add(header);
    }

    public Column<Object> getColumn(Object header) {
        return this.columns.get(header);
    }

    public Map<Object, Column<Object>> getColumns() {
        return this.columns;
    }

    public void addRow(Object[] row) {
        if (row.length != this.columns.size()) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El tamaño de la fila no coincide con la cantidad de columnas.");
        }

        for (int i = 0; i < row.length; i++) {
            this.columns.get(this.headers.get(i)).addCell(row[i]);
        }

        List<Integer> intIndexes = new ArrayList<>();

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
        List<Object> row = new ArrayList<>();
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
            Column<Object> newColumn = new Column<>();
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

    public DataFrame filter(Object header, Predicate<Object> predicate) {
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException(String.format("El header '%s' no existe.", header.toString()));
        }
        
        Column<Object> column = this.columns.get(header);

        DataFrame filteredDf = new DataFrame();

        for (Object headerName : this.headers) {
            Column<Object> newColumn = new Column<>();
            filteredDf.addColumn(headerName, newColumn);
        }

        for (int i = 0; i < column.getSize(); i++) {
            if (predicate.test(column.getCellValue(i))) {
                for (Object headerName : this.headers) {
                    filteredDf.columns.get(headerName).addCell(
                        this.columns.get(headerName).getCellValue(i)
                    );
                }
            }
        }

        return filteredDf;
    }

    @Override
    public String toString() {
        String string = "   ";
        for (Object header : this.headers) {
            string += " | " + header;
        }
        string += "\n";
        for (int i = 0; i < this.indexes.size(); i++) {
            string += this.indexes.get(i);
            for (Object header : this.headers) {
                string += " | " + this.columns.get(header).getCellValue(i);
            }
            string += "\n";
        }
        return string;
    }

    public void deleteRow(Object index) {
        if (!this.indexes.contains(index)) {
            throw new IllegalArgumentException(String.format("El índice '%s' no existe.", index.toString()));
        }

        for (Object header : this.headers) {
            this.columns.get(header).removeIndex(this.indexes.indexOf(index));
        }

        this.indexes.remove(index);
    }

    public void deleteColumn(Object header) {
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException(String.format("El header '%s' no existe.", header.toString()));
        }

        this.columns.remove(header);

        this.headers.remove(header);
    }

    public DataFrame head(int limit) {
        DataFrame headDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();
            headDf.addColumn(header, newColumn);
        }

        for (int i = 0; i < limit; i++) {
            for (Object header : this.headers) {
                headDf.columns.get(header).addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }
        }

        return headDf;
    }

    public DataFrame tail(int limit) {
        DataFrame tailDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();
            tailDf.addColumn(header, newColumn);
        }

        for (int i = this.indexes.size() - limit; i < this.indexes.size(); i++) {
            for (Object header : this.headers) {
                tailDf.columns.get(header).addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }
        }

        return tailDf;
    }

    public DataFrame sample(float percent) {
        DataFrame sampleDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();
            sampleDf.addColumn(header, newColumn);
        }

        //
        Random random = new Random();
        int sampleSize = (int) (this.indexes.size() * percent);

        for (int i = 0; i < sampleSize; i++) {
            int randomIndex = random.nextInt(this.indexes.size());
            for (Object header : this.headers) {
                sampleDf.columns.get(header).addCell(
                    this.columns.get(header).getCellValue(randomIndex)
                );
            }
        }

        return sampleDf;
    }

    public DataFrame concat(DataFrame df) {
        // TODO: revisar que coincidan la cantidad de columnas y sus tipos de datos (no necesariamente el nombre de sus headers)
        DataFrame concatDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();
            concatDf.addColumn(header, newColumn);
        }

        for (int i = 0; i < this.indexes.size(); i++) {
            for (Object header : this.headers) {
                concatDf.columns.get(header).addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }
        }

        for (int i = 0; i < df.indexes.size(); i++) {
            for (Object header : df.headers) {
                concatDf.columns.get(header).addCell(
                    df.columns.get(header).getCellValue(i)
                );
            }
        }

        return concatDf;
    }

    public DataFrame slice(int start, int end) {
        DataFrame sliceDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();
            sliceDf.addColumn(header, newColumn);
        }

        for (int i = start; i < end; i++) {
            for (Object header : this.headers) {
                sliceDf.columns.get(header).addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }
        }

        return sliceDf;
    }

    public DataFrame slice(int start, int end, Object[] headers) {
        // TODO: revisar que esto ande bien
        if (headers.length == 0) {
            throw new IllegalArgumentException("Debe especificar al menos un header.");
        }

        if (headers.length > this.headers.size()) {
            throw new IllegalArgumentException("La cantidad de headers especificados es mayor a la cantidad de headers del DataFrame.");
        }

        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers especificados no existe.");
        }

        DataFrame sliceDf = new DataFrame();

        for (Object header : headers) {
            Column<Object> newColumn = new Column<>();
            sliceDf.addColumn(header, newColumn);
        }

        for (int i = start; i < end; i++) {
            for (Object header : headers) {
                sliceDf.columns.get(header).addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }
        }

        return sliceDf;
    }

    public GroupedDataFrame groupBy(Object[] headers) {
        if (headers.length == 0) {
            throw new IllegalArgumentException("Debe especificar al menos un header.");
        }

        if (headers.length > this.headers.size()) {
            throw new IllegalArgumentException("La cantidad de headers especificados es mayor a la cantidad de headers del DataFrame.");
        }

        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers especificados no existe.");
        }
        
        Map<String, List<Object>> groups = new HashMap<>();

        for (int i = 0; i < this.indexes.size(); i++) {
            String key = "";

            for (Object header : headers) {
                key += this.columns.get(header).getCellValue(i).toString() + ',';
            }

            key = key.substring(0, key.length() - 1);

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(this.indexes.get(i));
        }

        return new GroupedDataFrame(this, groups);
    }


}
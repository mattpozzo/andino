// DataFrame.java
package src;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Random;

public class DataFrame {
    protected Map<Object, Column<Object>> columns;
    protected List<Object> headers;
    protected List<Object> indexes;

    public DataFrame() {
        this.columns = new HashMap<>();
        this.headers = new ArrayList<>();
        this.indexes = new ArrayList<>();
    }

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

        Arrays.sort(sortedColumn, (a, b) -> {
            if (a == null) {
                return 1;
            } else if (b == null) {
                return -1;
            } else if (a instanceof String && b instanceof String) {
                return ((String) a).compareTo((String) b);
            } else if (a instanceof Integer && b instanceof Integer) {
                return ((Integer) a).compareTo((Integer) b);
            } else if (a instanceof Boolean && b instanceof Boolean) {
                return ((Boolean) a).compareTo((Boolean) b);
            } else {
                throw new IllegalArgumentException("No se puede comparar los valores de la columna.");
            }
        });

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

            for (Object value : sortedColumn) {
                newColumn.addCell(
                    this.columns.get(headerName).getCellValue(
                        Arrays.asList(column.toArray()).indexOf(value)
                    )
                );
            }

            sortedDf.addColumn(headerName, newColumn);
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

            for (int i = 0; i < column.getSize(); i++) {
                if (column.getCellValue(i) != null && predicate.test(column.getCellValue(i))) {
                    newColumn.addCell(
                        this.columns.get(headerName).getCellValue(i)
                    );
                }
            }

            filteredDf.addColumn(headerName, newColumn);
        }

        return filteredDf;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Define el ancho para las columnas y los índices
        int indexWidth = 6; // Ajusta este valor según tus necesidades
        int columnWidth = 12; // Ajusta este valor según tus necesidades
    
        // Encabezado de la tabla
        sb.append(String.format("%-" + indexWidth + "s", "Index"));
        for (Object header : this.headers) {
            sb.append(String.format("| %-" + columnWidth + "s", header));
        }
        sb.append("\n");
    
        // Datos de la tabla
        for (int i = 0; i < this.indexes.size(); i++) {
            sb.append(String.format("%-" + indexWidth + "s", this.indexes.get(i).toString())); // Asumiendo que los índices pueden ser de cualquier tipo
            for (Object header : this.headers) {
                Object value = this.columns.get(header).getCellValue(i);
                String formattedValue = value == null ? "N/A" : value.toString();
                sb.append(String.format("| %-" + columnWidth + "s", formattedValue));
            }
            sb.append("\n");
        }
    
        return sb.toString();
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

            for (int i = 0; i < limit; i++) {
                newColumn.addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }

            headDf.addColumn(header, newColumn);
        }

        return headDf;
    }

    public DataFrame tail(int limit) {
        DataFrame tailDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();

            for (int i = this.indexes.size() - limit; i < this.indexes.size(); i++) {
                newColumn.addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }

            tailDf.addColumn(header, newColumn);
        }

        return tailDf;
    }

    public DataFrame sample(float percent) {
        // TODO: validar valor de percent
        DataFrame sampleDf = new DataFrame();

        Random random = new Random();
        int sampleSize = (int) (this.indexes.size() * percent);

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();

            for (int i = 0; i < sampleSize; i++) {
                int randomIndex = random.nextInt(this.indexes.size());
                newColumn.addCell(
                    this.columns.get(header).getCellValue(randomIndex)
                );
            }
            
            sampleDf.addColumn(header, newColumn);
        }

        return sampleDf;
    }

    public DataFrame concat(DataFrame df) {
        if (this.headers.size() != df.headers.size()) {
            throw new IllegalArgumentException("La cantidad de columnas no coincide.");
        }

        if (!this.headers.containsAll(df.headers)) {
            throw new IllegalArgumentException("Los headers no coinciden.");
        }

        for (Object header : this.headers) {
            if (!this.columns.get(header).getCellValue(0).getClass().equals(df.columns.get(header).getCellValue(0).getClass())) {
                throw new IllegalArgumentException(String.format("El tipo de dato de la columna '%s' no coincide.", header.toString()));
            }
        }

        DataFrame concatDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();

            for (int i = 0; i < this.indexes.size(); i++) {
                newColumn.addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }

            for (int i = 0; i < df.indexes.size(); i++) {
                newColumn.addCell(
                    df.columns.get(header).getCellValue(i)
                );
            }

            concatDf.addColumn(header, newColumn);
        }

        return concatDf;
    }

    public DataFrame slice(int start, int end) {
        if (start < 0 || start >= this.indexes.size()) {
            throw new IllegalArgumentException("El índice de inicio está fuera de rango.");
        }

        if (end < 0 || end > this.indexes.size()) {
            throw new IllegalArgumentException("El índice de fin está fuera de rango.");
        }

        if (start >= end) {
            throw new IllegalArgumentException("El índice de inicio debe ser menor al índice de fin.");
        }

        DataFrame sliceDf = new DataFrame();

        for (Object header : this.headers) {
            Column<Object> newColumn = new Column<>();

            for (int i = start; i < end; i++) {
                newColumn.addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }

            sliceDf.addColumn(header, newColumn);
        }

        return sliceDf;
    }

    public DataFrame slice(int start, int end, Object[] headers) {
        if (start < 0 || start >= this.indexes.size()) {
            throw new IllegalArgumentException("El índice de inicio está fuera de rango.");
        }

        if (end < 0 || end > this.indexes.size()) {
            throw new IllegalArgumentException("El índice de fin está fuera de rango.");
        }

        if (start >= end) {
            throw new IllegalArgumentException("El índice de inicio debe ser menor al índice de fin.");
        }

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

            for (int i = start; i < end; i++) {
                newColumn.addCell(
                    this.columns.get(header).getCellValue(i)
                );
            }

            sliceDf.addColumn(header, newColumn);
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
                Object newKey = this.columns.get(header).getCellValue(i);
                if (newKey != null) {
                    key += newKey.toString() + ',';
                } else {
                    key += "N/A,";
                }
            }

            key = key.substring(0, key.length() - 1);

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(this.indexes.get(i));
        }

        return new GroupedDataFrame(this, groups);
    }

    public DataFrame copy() {
        DataFrame copiedDf = new DataFrame();
        
        for (Object header : this.headers) {
            copiedDf.headers.add(header); //
        }
        for (Object index : this.indexes) {
            copiedDf.indexes.add(index);
        }

        for (Map.Entry<Object, Column<Object>> entry : this.columns.entrySet()) {
            Column<Object> originalColumn = entry.getValue();
            Column<Object> copiedColumn = new Column<>();

            for (int i = 0; i < originalColumn.getSize(); i++) {
                copiedColumn.addCell(originalColumn.getCellValue(i));
            }

            copiedDf.columns.put(entry.getKey(), copiedColumn);
        }

        return copiedDf;
    }

    public void export(String filePath, char delimiter, boolean includeHeader) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            if (includeHeader) {
                for (int i = 0; i < headers.size(); i++) {
                    writer.print(headers.get(i));
                    if (i < headers.size() - 1) {
                        writer.print(delimiter);
                    }
                }
                writer.println();
            }

            for (int i = 0; i < indexes.size(); i++) {
                for (int j = 0; j < headers.size(); j++) {
                    writer.print(getColumn(headers.get(j)).getCellValue(i));
                    if (j < headers.size() - 1) {
                        writer.print(delimiter);
                    }
                }
                writer.println();
            }
        }
    }

    public void info() {
        // Ancho para cada columna en la salida
        int columnWidth = 15;
        int typeWidth = 15;
        int countWidth = 10;
    
        System.out.println("Información del DataFrame:");
        System.out.printf("%-" + columnWidth + "s%-"
                + typeWidth + "s%-"
                + countWidth + "s%-"
                + countWidth + "s%n",
                "Columna", "Tipo", "No Nulos", "Nulos");
    
        for (Object header : headers) {
            Column<Object> column = columns.get(header);
            int nonNullCount = 0;
            int nullCount = 0;
            String dataType = "Desconocido";
    
            for (int i = 0; i < column.getSize(); i++) {
                Object cellValue = column.getCellValue(i);
                if (cellValue != null) {
                    nonNullCount++;
                    if (dataType.equals("Desconocido")) {
                        dataType = cellValue.getClass().getSimpleName();
                    }
                } else {
                    nullCount++;
                }
            }
            System.out.printf("%-" + columnWidth + "s%-"
                    + typeWidth + "s%-"
                    + countWidth + "d%-"
                    + countWidth + "d%n",
                    header, dataType, nonNullCount, nullCount);
        }
    
        System.out.println("\n");
    }
    

}
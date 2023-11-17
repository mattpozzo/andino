package src;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GroupedDataFrame extends DataFrame {
    private Map<String, List<Object>> groups;

    public GroupedDataFrame(DataFrame df, Map<String, List<Object>> groups) {
        List<Object> groupsIndexes = groups.values().iterator().next();

        if (!df.getIndexes().containsAll(groupsIndexes)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("Los índices de los grupos no pertenecen al DataFrame");
        }

        this.columns = df.getColumns();
        this.headers = df.getHeaders();
        this.indexes = df.getIndexes();
        this.groups = groups;
    }

    public DataFrame sum() {

    }

    public DataFrame sum(Object header) {
        if (!this.headers.contains(header)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El header no pertenece al DataFrame");
        }
        
        if (!header.getClass().equals(Integer.class) && !header.getClass().equals(Double.class)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El header no es numérico");
        }

        DataFrame df = new DataFrame();

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            Column<Object> column = new Column<>();

            Number sum = 0;
            // TODO: revisar conversión Object -> Number (u otro numérico)
            for (Object index : groupIndexes) {
                sum += this.getColumn(header).getCellValue((int) index);
            }

            // TODO: meter arreglo [sum] en el primer parámetro
            df.addRow(, groupName);
        }

        return df;
    }

    public DataFrame sum(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame");
        }
        
        if (!Arrays.stream(headers).allMatch(header -> header.getClass().equals(Integer.class) || header.getClass().equals(Double.class))) {
            throw new IllegalArgumentException("Alguno de los headers no es numérico");
        }

        DataFrame df = new DataFrame();

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            Column<Object> column = new Column<>();

            for (Object index : groupIndexes) {
                Object[] values = new Object[headers.length];
                for (int i = 0; i < headers.length; i++) {
                    values[i] = this.getColumn(headers[i]).getCellValue((int) index);
                }
                column.addCell(values);
            }

            df.addColumn(groupName, column);
        }

        return df;
    }

    public DataFrame max() {

    }

    public DataFrame max(Object header) {

    }

    public DataFrame max(Object[] headers) {

    }

    public DataFrame min() {

    }

    public DataFrame min(Object header) {

    }

    public DataFrame min(Object[] headers) {

    }

    public DataFrame count() {

    }

    public DataFrame count(Object header) {

    }

    public DataFrame count(Object[] headers) {

    }

    public DataFrame mean() {

    }

    public DataFrame mean(Object header) {

    }

    public DataFrame mean(Object[] headers) {

    }

    public DataFrame variance() {

    }

    public DataFrame variance(Object header) {

    }

    public DataFrame variance(Object[] headers) {

    }

    public DataFrame stdDeviation() {

    }

    public DataFrame stdDeviation(Object header) {

    }

    public DataFrame stdDeviation(Object[] headers) {

    }
}

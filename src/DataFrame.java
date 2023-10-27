// DataFrame.java
package src;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataFrame {
    private Map<String, Column<?>> columns = new HashMap<>();

    public void addColumn(String tag, Column<?> column) {
        columns.put(tag, column);
    }
    public Column<?> getColumn(String tag) {
        return columns.get(tag);
    }
    public Set<String> getTags() {
        return columns.keySet();
    }

    public static void main(String[] args) throws Exception {
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        for (String tag : df.getTags()) {
            Column<?> column = df.getColumn(tag);
            System.out.print(tag + ": ");
            for (int i = 0; i < column.size(); i++) {
                System.out.print(column.getCellValue(i) + " ");
            }
            System.out.println();
        }
    }

}
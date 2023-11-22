package src;

import src.interfaces.DataReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader implements DataReader {
    private char separator;
    private boolean hasHeaders;

    public CsvReader(char separator, boolean hasHeaders) {
        this.separator = separator;
        this.hasHeaders = hasHeaders;
    }

    @Override
    public DataFrame read(String filePath) {
        DataFrame df = new DataFrame();
        List<Object[]> data = new ArrayList<>(); // Cambiado a Object[]
        List<String> headers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(Character.toString(separator), -1);
                Object[] convertedValues = new Object[values.length]; // Nuevo arreglo de tipo Object[]

                for (int i = 0; i < values.length; i++) {
                    if (values[i].isEmpty()) {
                        convertedValues[i] = null;
                    } else {
                        try {
                            convertedValues[i] = Integer.parseInt(values[i]);
                        } catch (NumberFormatException e) {
                            convertedValues[i] = values[i];
                        }
                    }
                }

                if (isFirstLine && hasHeaders) {
                    headers = Arrays.asList(values);
                    isFirstLine = false;
                } else {
                    data.add(convertedValues);
                }
            }

            for (int i = 0; i < headers.size(); i++) {
                List<Object> columnData = new ArrayList<>();
                for (Object[] row : data) {
                    columnData.add(row.length > i ? row[i] : null);
                }
                Column<Object> column = new Column<>(columnData.toArray(new Object[0]));
                df.addColumn(headers.get(i), column);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return df;
    }
}

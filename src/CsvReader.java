package src;

import src.interfaces.DataReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements DataReader {
    private char separator;
    private boolean hasHeaders;

    public CsvReader(char separator, boolean hasHeaders) {
        this.separator = separator;
        this.hasHeaders = hasHeaders;
    }

    public char getSeparator() {
        return this.separator;
    }

    public boolean getHasHeaders() {
        return this.hasHeaders;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setHasHeaders(boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    @Override
    public String toString() {
        return "CsvReader [separator=" + separator + ", hasHeaders=" + hasHeaders + "]";
    }

    @Override
    public DataFrame read(String filePath) {
        DataFrame df = new DataFrame();
        List<String[]> data = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(Character.toString(separator));
                if (isFirstLine && hasHeaders) {
                    for (String header : values) {
                        headers.add(header);
                    }
                    isFirstLine = false;
                } else {
                    data.add(values);
                }
            }

            if (hasHeaders) {
                for (int i = 0; i < headers.size(); i++) {
                    List<Object> columnData = new ArrayList<>();
                    for (String[] row : data) {
                        columnData.add(row[i]);
                    }
                    Column<Object> column = new Column<>(columnData.toArray(new String[0]));
                    df.addColumn(headers.get(i), column);
                }
            } else {
                for (int i = 0; i < data.get(0).length; i++) {
                    List<Object> columnData = new ArrayList<>();
                    for (String[] row : data) {
                        columnData.add(row[i]);
                    }
                    Column<Object> column = new Column<>(columnData.toArray(new String[0]));
                    df.addColumn("Column" + (i + 1), column);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return df;
    }
}
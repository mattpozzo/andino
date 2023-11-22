package src.tests.A01readCSV;

import src.CsvReader;
import src.DataFrame;

public class readCsvOk {
    public static void main(String[] args) {
        System.out.println("Test readCsv:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println(df.toString(3, 2));
        System.out.println(df.toString(10, 2));
        System.out.println(df.toString(3, 10));
    }
}

package src.tests.A01readCSV;

import src.CsvReader;
import src.DataFrame;

public class readCsvNotOk {
        public static void main(String[] args) {
        System.out.println("Test readCsv:");

        CsvReader reader = new CsvReader(',', false);
        // CsvReader reader = new CsvReader('.', true);
        // CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);
    }
}

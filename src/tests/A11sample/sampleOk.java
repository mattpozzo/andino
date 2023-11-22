package src.tests.A11sample;

import src.CsvReader;
import src.DataFrame;

public class sampleOk {
    public static void main(String[] args) {
        System.out.println("Test sample:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);
        
        System.out.println("Sample DF");
        System.out.println(df.sample(50));
    }
}

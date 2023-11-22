package src.tests.B01count;

import src.CsvReader;
import src.DataFrame;

public class count {
    public static void main(String[] args) {
        System.out.println("Test count:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Count Smoker");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).count());

        System.out.println("Count Occupation | Smoker");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).count());
    }
}

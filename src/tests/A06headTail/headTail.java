package src.tests.A06headTail;

import src.CsvReader;
import src.DataFrame;

public class headTail {
    public static void main(String[] args) {
        System.out.println("Test head/tail:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);
        
        System.out.println("\n" + //
                "Head 3");

        System.out.println(df.head(3));

        System.out.println("\n" + //
                "Tail 3");

        System.out.println(df.tail(3));
        
        System.out.println("\n" + //
                "Head 30");

        System.out.println(df.head(30));
    }
}

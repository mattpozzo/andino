package src.tests.A05slice;

import src.CsvReader;
import src.DataFrame;

public class sliceOk {
    public static void main(String[] args) {
        System.out.println("Test slice:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("\n" + //
                "Slice 2:5");

        System.out.println(df.slice(2, 5));

        System.out.println("\n" + //
                "Slice 2:5, Occupation | Weigth | Age");

        System.out.println(df.slice(2, 5, new Object[]{"Occupation", "Weight", "Age"}));
    }
}

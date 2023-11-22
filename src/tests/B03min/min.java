package src.tests.B03min;

import src.CsvReader;
import src.DataFrame;

public class min {
    public static void main(String[] args) {
        System.out.println("Test min:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Smoker: Min all (bugged: N/A,Various fields)");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).min());

        System.out.println("Smoker: Min age");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).min("Age"));

        System.out.println("Occupation,Smoker: Min Age & Weight");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).min(new Object[]{"Age", "Weight"}));
    }
}

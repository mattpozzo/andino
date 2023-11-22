package src.tests.B02max;

import src.CsvReader;
import src.DataFrame;

public class max {
    public static void main(String[] args) {
        System.out.println("Test max:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Smoker: Max all (bugged: N/A,Weight)");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).max());

        System.out.println("Smoker: Max age");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).max("Age"));

        System.out.println("Occupation,Smoker: Max Age & Weight");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).max(new Object[]{"Age", "Weight"}));
    }
}

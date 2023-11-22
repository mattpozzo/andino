package src.tests.B04sum;

import src.CsvReader;
import src.DataFrame;

public class sumNotOk {
    public static void main(String[] args) {
        System.out.println("Test sum:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Smoker: Sum all");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).sum());

        System.out.println("Smoker: Sum weight");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).sum("Occupation"));
        System.out.println(df.groupBy(new Object[]{"Smoker"}).sum("Weight"));

        System.out.println("Occupation,Smoker: Sum Age & Weight (bugged: N/A,Various fields)");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).sum(new Object[]{"Age", "Occupation"}));
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).sum(new Object[]{"Age", "Weight"}));
    }
}

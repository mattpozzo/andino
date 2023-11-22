package src.tests.B06variance;

import src.CsvReader;
import src.DataFrame;

public class varianceNotOk {
    public static void main(String[] args) {
        System.out.println("Test variance:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Smoker: Variance all");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).variance());

        System.out.println("Smoker: Variance weight");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).variance("Occupation"));
        System.out.println(df.groupBy(new Object[]{"Smoker"}).variance("Weight"));

        System.out.println("Occupation,Smoker: Variance Age & Weight (bugged)");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).variance(new Object[]{"Age", "Occupation"}));
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).variance(new Object[]{"Age", "Weight"}));
    }
}

package src.tests.B07std;

import src.CsvReader;
import src.DataFrame;

public class stdNotOk {
    public static void main(String[] args) {
        System.out.println("Test std:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Smoker: Standard deviation all");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).std());

        System.out.println("Smoker: Standard deviation weight");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).std("Occupation"));
        System.out.println(df.groupBy(new Object[]{"Smoker"}).std("Weight"));

        System.out.println("Occupation,Smoker: Standard deviation Age & Weight (bugged)");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).std(new Object[]{"Age", "Occupation"}));
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).std(new Object[]{"Age", "Weight"}));
    }
}

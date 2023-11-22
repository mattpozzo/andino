package src.tests.B05mean;

import src.CsvReader;
import src.DataFrame;

public class meanNotOk {
    public static void main(String[] args) {
        System.out.println("Test mean:");
        
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Smoker: Mean all");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).mean());

        System.out.println("Smoker: Mean weight");
        System.out.println(df.groupBy(new Object[]{"Smoker"}).mean("Occupation"));
        System.out.println(df.groupBy(new Object[]{"Smoker"}).mean("Weight"));

        System.out.println("Occupation,Smoker: Mean Age & Weight (bugged)");
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).mean(new Object[]{"Age", "Occupation"}));
        System.out.println(df.groupBy(new Object[]{"Occupation","Smoker"}).mean(new Object[]{"Age", "Weight"}));
    }
}

package src.tests.A10sort;

import src.CsvReader;
import src.DataFrame;

public class sortOk {
    public static void main(String[] args) {
        System.out.println("Test sort:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);
        
        System.out.println("Sort by Age (asc)");
        System.out.println(df.sort("Age", true));
        
        System.out.println("Sort by Name (desc)");
        System.out.println(df.sort("Name", false));
    }
}

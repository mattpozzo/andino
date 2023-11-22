package src.tests.A10sort;

import src.Column;
import src.CsvReader;
import src.DataFrame;

public class sortNotOk {
    public static void main(String[] args) {
        System.out.println("Test sort:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");
        df.addColumn("Registered", new Column<>(new Boolean[] {true, false, true, false, true, false, true}));

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);
        
        System.out.println("Sort by Occupation (desc)");
        DataFrame sortedDf1 = df.sort("Occupation", false);
        System.out.println(sortedDf1);
        
        System.out.println("Sort by Registered (asc)");
        DataFrame sortedDf2 = df.sort("Registered", true);
        System.out.println(sortedDf2);

        sortedDf2.getColumn("Age").replaceNull(18);
        
        System.out.println("Sort by Registered (asc) | Bonus replaceNull");
        System.out.println(sortedDf2);
    }
}

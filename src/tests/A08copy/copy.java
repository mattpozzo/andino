package src.tests.A08copy;

import src.Column;
import src.CsvReader;
import src.DataFrame;

public class copy {
    public static void main(String[] args) {
        System.out.println("\nTest copy:");
        try {
            // Crear una instancia de DataFrame
            System.out.println("Test head/tail:");

            CsvReader reader = new CsvReader(',', true);
            DataFrame df = reader.read("test.csv");

            System.out.println(df);

            // Hacer una copia del DataFrame
            DataFrame copiedDf = df.copy();
            df.addRow(new Object[] {"Luke", 30, "Architect", "true", 75}); // Agregar una fila al DataFrame original

            System.out.println(copiedDf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

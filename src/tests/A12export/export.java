package src.tests.A12export;

import src.CsvReader;
import src.DataFrame;

public class export {
    public static void main(String[] args) {
        // Test para export
        System.out.println("\nTest export:");
        try {
            CsvReader reader = new CsvReader(',', true);
            DataFrame df = reader.read("test.csv");
            
            // Exportar DataFrame a un archivo CSV
            df.export("output.csv", ',', true);
            System.out.println("DataFrame exportado correctamente a output.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

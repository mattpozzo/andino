package src.tests.A04readRowColCell;

import src.CsvReader;
import src.DataFrame;

public class readRowColCellOk {
    public static void main(String[] args) {
        System.out.println("Test reading row, column and cell:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Row 0:");
        System.out.println(df.getRow(0));

        System.out.println("\nRow 5:");
        System.out.println(df.getRow(5));

        System.out.println("\n" + //
                "Column Age:");
        System.out.println(df.getColumn("Age"));

        System.out.println("\n" + //
                "Column Occupation:");
        System.out.println(df.getColumn("Occupation"));

        System.out.println("\n" + //
                "Cell 0, Name:");
        System.out.println(df.getCell(0, "Name"));

        System.out.println("\n" + //
                "Cell 4, Occupation:");
        System.out.println(df.getCell(4, "Occupation"));
    }
}

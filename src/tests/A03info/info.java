package src.tests.A03info;

import src.Column;
import src.DataFrame;

public class info {
    public static void main(String[] args) {
        // Test para info
        System.out.println("\nTest info:");
        try {
            DataFrame df = new DataFrame();
            // Agregar datos al DataFrame
            Column<Object> column1 = new Column<>();
            column1.addCell("Data1");
            column1.addCell("Data2");
            df.addColumn("Column1", column1);

            Column<Object> column2 = new Column<>();
            column2.addCell(100);
            column2.addCell();
            df.addColumn("Column2", column2);

            Column<Object> column3 = new Column<>();
            column3.addCell();
            column3.addCell(true);
            df.addColumn("Column3", column3);

            System.out.println(df);
            
            df.info();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

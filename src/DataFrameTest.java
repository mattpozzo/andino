package src;

public class DataFrameTest {
    public static void main(String[] args) {
        // Crear una instancia de DataFrame para las pruebas
        DataFrame df = new DataFrame();

        // Test para addColumn
        System.out.println("Test addColumn:");
        try {
            Column<Object> column = new Column<>();
            column.addCell("Test1");
            column.addCell("Test2");
            df.addColumn("TestColumn", column);

            // Verificar si la columna se agregó correctamente
            boolean testResult = "Test1".equals(df.getColumn("TestColumn").getCellValue(0)) &&
                                 "Test2".equals(df.getColumn("TestColumn").getCellValue(1));
            System.out.println("Columna agregada correctamente: " + testResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test para addRow
        System.out.println("\nTest addRow:");
        try {
            Object[] row = {"Row1Col1"};


            df.addRow(row); 

            // Verificar si la fila se agregó correctamente
            boolean testResultRow = "Row1Col1".equals(df.getRow(0).get(0)) &&
                                    "Row1Col2".equals(df.getRow(0).get(1));
            System.out.println("Fila agregada correctamente: " + testResultRow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test para deleteRow
        System.out.println("\nTest deleteRow:");
        try {
            df.deleteRow(0);
            boolean testDeleteRow = !df.getIndexes().contains(0);
            System.out.println("Fila eliminada correctamente: " + testDeleteRow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test para deleteColumn
        System.out.println("\nTest deleteColumn:");
        try {
            df.deleteColumn("TestColumn");
            boolean testDeleteColumn = df.getColumn("TestColumn") == null;
            System.out.println("Columna eliminada correctamente: " + testDeleteColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ... Puedes agregar más pruebas para otros métodos aquí ...

        // Por ejemplo, para sort, filter, etc.
    }
}

package src.tests.A02addAndDelete;

import src.Column;
import src.DataFrame;

public class addAndDeleteOk {
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
        System.out.println(df);	

        // Test para addRow
        System.out.println("\nTest addRow:");
        try {
            Object[] row = {"Row1Col1"};


            df.addRow(row); 

            // Verificar si la fila se agregó correctamente
            boolean testResultRow = "Row1Col1".equals(df.getRow(2).get(0));
            System.out.println("Fila agregada correctamente: " + testResultRow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(df);	

        // Test para addColumn
        System.out.println("Test addColumn:");
        try {
            Column<Object> column = new Column<>();
            column.addCell(377);
            column.addCell(610);
            column.addCell(987);
            df.addColumn(1123, column);

            // Verificar si la columna se agregó correctamente
            boolean testResult = 377 == (int) (df.getColumn(1123).getCellValue(0)) &&
                                 610 == (int) (df.getColumn(1123).getCellValue(1)) &&
                                 987 == (int) (df.getColumn(1123).getCellValue(2));
            System.out.println("Columna agregada correctamente: " + testResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(df);	

        // Test para addRow
        System.out.println("\nTest addRow:");
        try {
            Object[] row = {"Row2Col1", 610 + 987};


            df.addRow(row, "FIB"); 

            // Verificar si la fila se agregó correctamente
            boolean testResultRow = "Row2Col1".equals(df.getRow("FIB").get(0));
            System.out.println("Fila agregada correctamente: " + testResultRow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(df);	

        // Test para deleteRow
        System.out.println("\nTest deleteRow:");
        try {
            df.deleteRow(1);
            boolean testDeleteRow = !df.getIndexes().contains(1);
            System.out.println("Fila eliminada correctamente: " + testDeleteRow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(df);	

        // Test para deleteColumn
        System.out.println("\nTest deleteColumn:");
        try {
            df.deleteColumn("TestColumn");
            boolean testDeleteColumn = df.getColumn("TestColumn") == null;
            System.out.println("Columna eliminada correctamente: " + testDeleteColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(df);

        System.out.println("\nTest setCell:");
        df.setCell(123, 2, 1123);
        System.out.println(df);
        System.out.println("\nTest setCell:");
        df.setCell(null, 2, 1123);
        System.out.println(df);
    }
}

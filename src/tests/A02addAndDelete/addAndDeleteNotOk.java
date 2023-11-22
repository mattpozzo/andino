package src.tests.A02addAndDelete;

import src.Column;
import src.DataFrame;

public class addAndDeleteNotOk {
    public static void main(String[] args) {
        // Crear una instancia de DataFrame para las pruebas
        DataFrame df = new DataFrame();

        // Test para addColumn
        System.out.println("Test addColumn:");
        Column<Object> column = new Column<>();
        column.addCell("Test1");
        column.addCell("Test2");
        df.addColumn("TestColumn", column);

        // Verificar si la columna se agregó correctamente
        boolean testResult = "Test1".equals(df.getColumn("TestColumn").getCellValue(0)) &&
                                "Test2".equals(df.getColumn("TestColumn").getCellValue(1));
        System.out.println("Columna agregada correctamente: " + testResult);
        System.out.println(df);	

        // Test para addRow
        System.out.println("\nTest addRow:");
        // Object[] row = {};
        // Object[] row = {"Row1Col1", "Row1Col2"};
        // Object[] row = {5};
        Object[] row = {"Row1Col1"};

        df.addRow(row); 

        // Verificar si la fila se agregó correctamente
        boolean testResultRow = "Row1Col1".equals(df.getRow(2).get(0));
        System.out.println("Fila agregada correctamente: " + testResultRow);
        System.out.println(df);	

        // Test para addColumn
        System.out.println("Test addColumn:");
        column = new Column<>();
        column.addCell(377);
        column.addCell(610);
        column.addCell(987);
        df.addColumn("TestColumn", column);
        // df.addColumn(1123, column);

        // Verificar si la columna se agregó correctamente
        testResult = 377 == (int) (df.getColumn(1123).getCellValue(0)) &&
                                610 == (int) (df.getColumn(1123).getCellValue(1)) &&
                                987 == (int) (df.getColumn(1123).getCellValue(2));
        System.out.println("Columna agregada correctamente: " + testResult);
        System.out.println(df);	

        // Test para addRow
        System.out.println("\nTest addRow:");
        Object[] row2 = {"Row2Col1"};
        // Object[] row2 = {"Row2Col1", "E"};
        // Object[] row2 = {"Row2Col1", 610 + 987};

        df.addRow(row2, 1); 
        // df.addRow(row2, "FIB"); 

        // Verificar si la fila se agregó correctamente
        testResultRow = "Row2Col1".equals(df.getRow("FIB").get(0));
        System.out.println("Fila agregada correctamente: " + testResultRow);
        System.out.println(df);	

        // Test para deleteRow
        System.out.println("\nTest deleteRow:");
        df.deleteRow(3);
        // df.deleteRow(1);
        boolean testDeleteRow = !df.getIndexes().contains(1);
        System.out.println("Fila eliminada correctamente: " + testDeleteRow);
        System.out.println(df);	

        // Test para deleteColumn
        System.out.println("\nTest deleteColumn:");
        df.deleteColumn("NoExisto");
        // df.deleteColumn("TestColumn");
        boolean testDeleteColumn = df.getColumn("TestColumn") == null;
        System.out.println("Columna eliminada correctamente: " + testDeleteColumn);
        System.out.println(df);

        // Test para setCell
        System.out.println("\nTest setCell:");
        df.setCell("Incorrecto", 2, 1123);
        // df.setCell(123, 1, 1123);
        // df.setCell(123, 2, "NoExisto");
        // df.setCell(123, 2, 1123);
        System.out.println(df);
    }
}

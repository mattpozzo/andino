package src;



public class DataFrameTest {
    // 21/11/23: HACER TESTS PARA TODOS LOS MÉTODOS DE DATAFRAME Y GROUPEDDATAFRAME
    public static void csvReader(String[] args) throws Exception {
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        df.info();

        DataFrame sortedDfByAge = df.sort("Age", true);

        System.out.println(sortedDfByAge);

        DataFrame filteredDf = df.filter("Occupation", occ -> occ.equals("Programmer"));

        System.out.println(filteredDf);
    }
    
    public static void addAndDelete(String[] args) {
        // Crear una instancia de DataFrame para las pruebas
        DataFrame df = new DataFrame();
        System.out.println(df);	

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
    }


    public static void main(String[] args) {
        System.out.println("\nTest copy:");
        try {
            // Crear una instancia de DataFrame
            DataFrame df = new DataFrame();

            // Agregar datos al DataFrame
            Column<Object> column1 = new Column<>();
            column1.addCell("Data1");
            column1.addCell("Data2");
            df.addColumn("Column1", column1);

            Column<Object> column2 = new Column<>();
            column2.addCell(100);
            column2.addCell(200);
            df.addColumn("Column2", column2);

            System.out.println(df);

            // Hacer una copia del DataFrame
            DataFrame copiedDf = df.copy();
            df.addRow(new Object[] {"Data3", 300});

            System.out.println(copiedDf);

            // Verificar si la copia es correcta (puedes hacer más comprobaciones aquí)
            boolean copyTestResult = copiedDf.getColumn("Column1").getCellValue(0).equals("Data1") &&
                                        copiedDf.getColumn("Column2").getCellValue(1).equals(200);
            System.out.println("Copia realizada correctamente: " + copyTestResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void export(String[] args) {
        // Test para export
        System.out.println("\nTest export:");
        try {
            DataFrame df = new DataFrame();
            // Agregar datos al DataFrame
            Column<Object> column1 = new Column<>();
            column1.addCell("Data1");
            column1.addCell("Data2");
            df.addColumn("Column1", column1);

            Column<Object> column2 = new Column<>();
            column2.addCell(100);
            column2.addCell(200);
            df.addColumn("Column2", column2);
            
            // Exportar DataFrame a un archivo CSV
            df.export("output.csv", ',', true);
            System.out.println("DataFrame exportado correctamente a output.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void info(String[] args) {

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
            
            df.info();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

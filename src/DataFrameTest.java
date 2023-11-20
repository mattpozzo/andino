package src;



public class DataFrameTest {

    public static void csvReader(String[] args) throws Exception {
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        for (Object header : df.getHeaders()) {
            Column<?> column = df.getColumn(header);
            System.out.print(header + ": ");
            for (int i = 0; i < column.getSize(); i++) {
                System.out.print(column.getCellValue(i) + " ");
            }
            System.out.println();
        }
    }

    public static void metodos(String[] args) {
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




    }


    public static void copy(String[] args) {
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

            // Hacer una copia del DataFrame
            DataFrame copiedDf = df.copy();

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
            column2.addCell(null);
            df.addColumn("Column2", column2);
            
            df.info();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

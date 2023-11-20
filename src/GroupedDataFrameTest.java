package src;

public class GroupedDataFrameTest {

    public static void main(String[] args) {
        // Crear un DataFrame con algunos datos
        DataFrame df = new DataFrame();

        // Agregar columnas al DataFrame
        df.addColumn("Name", new Column<>(new String[]{"Alice", "Bob", "Alice", "Bob"}));
        df.addColumn("Age", new Column<>(new Integer[]{25, 30, 22, 34}));
        df.addColumn("Salary", new Column<>(new Integer[]{50000, 70000, 52000, 75000}));

        // Imprimir el DataFrame original
        System.out.println("DataFrame Original:");
        System.out.println(df);

        // Agrupar el DataFrame por la columna "Name"
        GroupedDataFrame groupedDf = df.groupBy(new Object[]{"Name"});

        // Calcular la suma de la columna "Salary" para cada grupo
        DataFrame sumDf = groupedDf.sum("Salary");
        System.out.println("Suma de Salarios por Nombre:");
        System.out.println(sumDf);

        // Calcular el promedio de la columna "Age" para cada grupo
        DataFrame meanDf = groupedDf.mean("Age");
        System.out.println("Promedio de Edades por Nombre:");
        System.out.println(meanDf);
    }
}

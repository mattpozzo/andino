package src.tests.A09concat;

import src.CsvReader;
import src.DataFrame;

public class concatOk {
    public static void main(String[] args) {
        System.out.println("Test concat:");

        // Crear un DataFrame con los datos del archivo test.csv
        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        System.out.println("DF1:");
        System.out.println(df);

        // Crear un DataFrame con los datos del archivo test2.csv
        CsvReader reader2 = new CsvReader(',', true);
        DataFrame df2 = reader2.read("test2.csv");

        System.out.println("DF2:");
        System.out.println(df2);

        // Concatenar los dos DataFrames
        DataFrame df3 = df.concat(df2);

        // Imprimir el DataFrame para ver los resultados
        System.out.println("DF Concat:");
        System.out.println(df3);
    }
}

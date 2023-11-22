package src.tests.A07filter;

import src.CsvReader;
import src.DataFrame;

public class filterNotOk {
    public static void main(String[] args) {
        System.out.println("Test head/tail:");

        CsvReader reader = new CsvReader(',', true);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);

        System.out.println("Filter Country == Argentina");
        System.out.println(df.filter("Country", country -> country.equals("Argentina")));

        System.out.println("Filter Age == 30");
        System.out.println(df.filter("Age", age -> age.equals("30")));

        System.out.println("Filter Age > 30");
        System.out.println(df.filter("Age", age -> (int) age > 30));

        System.out.println("Filter Smoker");
        System.out.println(df.filter("Occupation", occ -> occ.equals("Programmer")));
    }
}

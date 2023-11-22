    public static void readCsvNotOK(String[] args) {
        System.out.println("Test readCsv:");

        CsvReader reader = new CsvReader(',', false);
        DataFrame df = reader.read("test.csv");

        // Imprimir el DataFrame para ver los resultados
        System.out.println(df);
    }
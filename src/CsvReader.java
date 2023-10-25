package src;

import src.interfaces.DataReader;

public class CsvReader implements DataReader {
    char separator;
    boolean hasHeaders;

    public CsvReader(char separator, boolean hasHeaders) {
        this.separator = separator;
        this.hasHeaders = hasHeaders;
    }

    public char getSeparator() {
        return this.separator;
    }

    public boolean getHasHeaders() {
        return this.hasHeaders;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setHasHeaders(boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    @Override
    public String toString() {
        return "CsvReader [separator=" + separator + ", hasHeaders=" + hasHeaders + "]";
    }

    public DataFrame read(String filePath) {
        // TODO: implementar después de definir DataFrame
        return null;
    }
}

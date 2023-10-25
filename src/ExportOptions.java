package src;

public class ExportOptions {
    private String path;
    private String format;
    private boolean hasHeaders;

    public ExportOptions(String path, String format, boolean hasHeaders) {
        this.path = path;
        this.format = format;
        this.hasHeaders = hasHeaders;
    }

    public String getPath() {
        return this.path;
    }

    public String getFormat() {
        return this.format;
    }

    public boolean getHasHeaders() {
        return this.hasHeaders;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setHasHeaders(boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    @Override
    public String toString() {
        return "ExportOptions [path=" + path + ", format=" + format + ", hasHeaders=" + hasHeaders + "]";
    }
}

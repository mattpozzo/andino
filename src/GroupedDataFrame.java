package src;

public class GroupedDataFrame extends DataFrame {
    private DataFrame df;
    private String[] columnNames;

    public GroupedDataFrame(DataFrame df, String[] columnNames) {
        this.df = df;
        this.columnNames = columnNames;
    }

    public DataFrame getDf() {
        return this.df;
    }

    public String[] getColumnNames() {
        return this.columnNames;
    }

    public void setDf(DataFrame df) {
        this.df = df;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    
}

package src;

import java.util.List;
import java.util.Map;

public class GroupedDataFrame extends DataFrame {
    private Map<String, List<Object>> groups;

    public GroupedDataFrame(DataFrame df, Map<String, List<Object>> groups) {
        this.columns = df.getColumns();
        this.headers = df.getHeaders();
        this.indexes = df.getIndexes();
        this.groups = groups;
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

package src;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GroupedDataFrame extends DataFrame {
    private Map<String, List<Object>> groups;

    public GroupedDataFrame(DataFrame df, Map<String, List<Object>> groups) {
        List<Object> groupsIndexes = groups.values().iterator().next();

        if (!df.getIndexes().containsAll(groupsIndexes)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("Los índices de los grupos no pertenecen al DataFrame");
        }

        this.columns = df.getColumns();
        this.headers = df.getHeaders();
        this.indexes = df.getIndexes();
        this.groups = groups;
    }

    public DataFrame sum() {
        DataFrame df = new DataFrame();

        for (Object header : this.headers) {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue.getClass().equals(Integer.class)) {
                Column<Object> column = new Column<>();
                df.addColumn("sum " + header.toString(), column);
            }
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Integer[] sums = new Integer[df.headers.size()];

            for (int i = 0; i < df.headers.size(); i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(4, header.length());
                
                for (Object index : groupIndexes) {
                    sums[i] = 0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        sums[i] += (int) cellValue;
                    }
                }
            }

            df.addRow(sums, groupName);
        }

        return df;
    }

    public DataFrame sum(Object header) {
        Object firstValue = this.getColumn(header).getCellValue(0);
        int firstCount = 1;

        while (firstValue == null) {
            firstValue = this.getColumn(header).getCellValue(firstCount);
            firstCount++;
        }

        if (!this.headers.contains(header)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("El header no pertenece al DataFrame.");
        }
        
        if (!firstValue.getClass().equals(Integer.class)) {
            // TODO: Definir clase para este tipo de excepción
            throw new IllegalArgumentException("La columna seleccionada no es numérica.");
        }

        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();

        df.addColumn("sum " + header.toString(), column);

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Integer[] sum = {0};

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);
                if (cellValue != null) {
                    sum[0] += (int) cellValue;
                }
            }

            df.addRow(sum, groupName);
        }

        return df;
    }

    public DataFrame sum(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame.");
        }
        
        if (!Arrays.stream(headers).allMatch(header -> {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            return firstValue.getClass().equals(Integer.class);
        })) {
            throw new IllegalArgumentException("Alguna de las columnas seleccionadas no es numérica.");
        }

        DataFrame df = new DataFrame();

        for (Object header : headers) {
            Column<Object> column = new Column<>();
            df.addColumn("sum " + header.toString(), column);
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Integer[] sums = new Integer[headers.length];

            for (int i = 0; i < headers.length; i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(4, header.length());

                for (Object index : groupIndexes) {
                    sums[i] = 0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        sums[i] += (int) cellValue;
                    }
                }
            }

            df.addRow(sums, groupName);
        }

        return df;
    }

    public DataFrame mean() {
        DataFrame df = new DataFrame();

        for (Object header : this.headers) {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue.getClass().equals(Integer.class)) {
                Column<Object> column = new Column<>();
                df.addColumn("mean " + header.toString(), column);
            }
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Double[] means = new Double[df.headers.size()];

            for (int i = 0; i < df.headers.size(); i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(5, header.length());

                for (Object index : groupIndexes) {
                    means[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        means[i] += (double) (int) cellValue;
                    }
                }
                means[i] /= groupIndexes.size();
            }

            df.addRow(means, groupName);
        }

        return df;
    }

    public DataFrame mean(Object header) {
        Object firstValue = this.getColumn(header).getCellValue(0);
        int firstCount = 1;

        while (firstValue == null) {
            firstValue = this.getColumn(header).getCellValue(firstCount);
            firstCount++;
        }

        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException("El header no pertenece al DataFrame.");
        }
        
        if (!firstValue.getClass().equals(Integer.class)) {
            throw new IllegalArgumentException("La columna seleccionada no es numérica.");
        }
    
        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();
    
        df.addColumn("mean " + header.toString(), column);
    
        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
    
            double sum = 0.0;
            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);
                if (cellValue instanceof Integer) {
                    sum += (Integer) cellValue;
                }
            }
            double mean = sum / groupIndexes.size();
    
            Object[] meanRow = new Object[]{mean};
            df.addRow(meanRow, groupName);
        }
    
        return df;
    }
    

    public DataFrame mean(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame.");
        }

        if (!Arrays.stream(headers).allMatch(header -> {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            return firstValue.getClass().equals(Integer.class);
        })) {
            throw new IllegalArgumentException("Alguna de las columnas seleccionadas no es numérica.");
        }

        DataFrame df = new DataFrame();

        for (Object header : headers) {
            Column<Object> column = new Column<>();
            df.addColumn("mean " + header.toString(), column);
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Double[] means = new Double[headers.length];

            for (int i = 0; i < headers.length; i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(5, header.length());

                for (Object index : groupIndexes) {
                    means[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        means[i] += (double) (int) cellValue;
                    }
                }
                means[i] /= groupIndexes.size();
            }

            df.addRow(means, groupName);
        }

        return df;
    }

    public DataFrame max() {
        DataFrame df = new DataFrame();

        for (Object header : this.headers) {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            Class<?> columnClass = firstValue.getClass();
            if (columnClass.equals(Integer.class) || columnClass.equals(String.class)) {
                Column<Object> column = new Column<>();
                df.addColumn("max " + header.toString(), column);
            }
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Object[] maxs = new Object[df.headers.size()];

            for (int i = 0; i < df.headers.size(); i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(4, header.length());

                Object firstValue = this.getColumn(originalHeader).getCellValue(0);
                int firstCount = 1;

                while (firstValue == null) {
                    firstValue = this.getColumn(originalHeader).getCellValue(firstCount);
                    firstCount++;
                }

                Class<?> columnClass = firstValue.getClass();
                maxs[i] = firstValue;

                for (Object index : groupIndexes) {
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);

                    if (cellValue != null) {
                        if (columnClass.equals(Integer.class)) {
                            if ((int) cellValue > (int) maxs[i]) {
                                maxs[i] = cellValue;
                            }
                        } else {
                            if (cellValue.toString().compareTo(maxs[i].toString()) > 0) {
                                maxs[i] = cellValue;
                            }
                        }
                    }
                }
            }

            df.addRow(maxs, groupName);
        }

        return df;
    }

    public DataFrame max(Object header) {
        // TODO: DESFALOPIZAR TRATAMIENTO DE NA, acá y en otros métodos (ver prints de df sumarizados con NAs)
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException("El header no pertenece al DataFrame.");
        }

        Object firstValue = this.getColumn(header).getCellValue(0);
        int firstCount = 1;

        while (firstValue == null) {
            firstValue = this.getColumn(header).getCellValue(firstCount);
            firstCount++;
        }

        Class<?> columnClass = firstValue.getClass();
        if (!columnClass.equals(Integer.class) && !columnClass.equals(String.class)) {
            throw new IllegalArgumentException("La columna seleccionada no es numérica o string.");
        }

        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();

        df.addColumn("max " + header.toString(), column);

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Object[] max = {firstValue};

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);

                if (cellValue != null) {
                    if (columnClass.equals(Integer.class)) {
                        if ((int) cellValue > (int) max[0]) {
                            max[0] = cellValue;
                        }
                    } else {
                        if (cellValue.toString().compareTo(max[0].toString()) > 0) {
                            max[0] = cellValue;
                        }
                    }
                }
            }

            df.addRow(max, groupName);
        }

        return df;
    }

    public DataFrame max(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame.");
        }

        if (!Arrays.stream(headers).allMatch(header -> {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            Class<?> columnClass = firstValue.getClass();
            return columnClass.equals(Integer.class) || columnClass.equals(String.class);
        })) {
            throw new IllegalArgumentException("Alguna de las columnas seleccionadas no es numérica o string.");
        }

        DataFrame df = new DataFrame();

        for (Object header : headers) {
            Column<Object> column = new Column<>();
            df.addColumn("max " + header.toString(), column);
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Object[] maxs = new Object[headers.length];

            for (int i = 0; i < headers.length; i++) {
                Object firstValue = this.getColumn(headers[i]).getCellValue(0);
                int firstCount = 1;

                while (firstValue == null) {
                    firstValue = this.getColumn(headers[i]).getCellValue(firstCount);
                    firstCount++;
                }

                Class<?> columnClass = firstValue.getClass();
                maxs[i] = firstValue;

                for (Object index : groupIndexes) {
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(headers[i]).getCellValue(cellIndex);

                    if (cellValue != null) {
                        if (columnClass.equals(Integer.class)) {
                            if ((int) cellValue > (int) maxs[i]) {
                                maxs[i] = cellValue;
                            }
                        } else {
                            if (cellValue.toString().compareTo(maxs[i].toString()) > 0) {
                                maxs[i] = cellValue;
                            }
                        }
                    }
                }
            }

            df.addRow(maxs, groupName);
        }

        return df;
    }

    public DataFrame min() {
        DataFrame df = new DataFrame();

        for (Object header : this.headers) {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            Class<?> columnClass = firstValue.getClass();
            if (columnClass.equals(Integer.class) || columnClass.equals(String.class)) {
                Column<Object> column = new Column<>();
                df.addColumn("min " + header.toString(), column);
            }
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Object[] mins = new Object[df.headers.size()];

            for (int i = 0; i < df.headers.size(); i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(4, header.length());

                Object firstValue = this.getColumn(originalHeader).getCellValue(0);
                int firstCount = 1;

                while (firstValue == null) {
                    firstValue = this.getColumn(originalHeader).getCellValue(firstCount);
                    firstCount++;
                }

                Class<?> columnClass = firstValue.getClass();
                mins[i] = firstValue;

                for (Object index : groupIndexes) {
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);

                    if (cellValue != null) {
                        if (columnClass.equals(Integer.class)) {
                            if ((int) cellValue < (int) mins[i]) {
                                mins[i] = cellValue;
                            }
                        } else {
                            if (cellValue.toString().compareTo(mins[i].toString()) < 0) {
                                mins[i] = cellValue;
                            }
                        }
                    }
                }
            }

            df.addRow(mins, groupName);
        }

        return df;
    }

    public DataFrame min(Object header) {
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException("El header no pertenece al DataFrame.");
        }

        Object firstValue = this.getColumn(header).getCellValue(0);
        int firstCount = 1;

        while (firstValue == null) {
            firstValue = this.getColumn(header).getCellValue(firstCount);
            firstCount++;
        }

        Class<?> columnClass = firstValue.getClass();
        if (!columnClass.equals(Integer.class) && !columnClass.equals(String.class)) {
            throw new IllegalArgumentException("La columna seleccionada no es numérica o string.");
        }

        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();

        df.addColumn("min " + header.toString(), column);

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Object[] min = {firstValue};

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);

                if (cellValue != null) {
                    if (columnClass.equals(Integer.class)) {
                        if ((int) cellValue < (int) min[0]) {
                            min[0] = cellValue;
                        }
                    } else {
                        if (cellValue.toString().compareTo(min[0].toString()) < 0) {
                            min[0] = cellValue;
                        }
                    }
                }
            }

            df.addRow(min, groupName);
        }

        return df;
    }

    public DataFrame min(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame.");
        }

        if (!Arrays.stream(headers).allMatch(header -> {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            Class<?> columnClass = firstValue.getClass();
            return columnClass.equals(Integer.class) || columnClass.equals(String.class);
        })) {
            throw new IllegalArgumentException("Alguna de las columnas seleccionadas no es numérica o string.");
        }

        DataFrame df = new DataFrame();

        for (Object header : headers) {
            Column<Object> column = new Column<>();
            df.addColumn("min " + header.toString(), column);
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Object[] mins = new Object[headers.length];

            for (int i = 0; i < headers.length; i++) {
                Object firstValue = this.getColumn(headers[i]).getCellValue(0);
                int firstCount = 1;

                while (firstValue == null) {
                    firstValue = this.getColumn(headers[i]).getCellValue(firstCount);
                    firstCount++;
                }

                Class<?> columnClass = firstValue.getClass();
                mins[i] = firstValue;

                for (Object index : groupIndexes) {
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(headers[i]).getCellValue(cellIndex);

                    if (cellValue != null) {
                        if (columnClass.equals(Integer.class)) {
                            if ((int) cellValue < (int) mins[i]) {
                                mins[i] = cellValue;
                            }
                        } else {
                            if (cellValue.toString().compareTo(mins[i].toString()) < 0) {
                                mins[i] = cellValue;
                            }
                        }
                    }
                }
            }

            df.addRow(mins, groupName);
        }

        return df;
    }

    public DataFrame variance() {
        DataFrame df = new DataFrame();

        for (Object header : this.headers) {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue.getClass().equals(Integer.class)) {
                Column<Object> column = new Column<>();
                df.addColumn("variance " + header.toString(), column);
            }
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();

            Double[] means = new Double[df.headers.size()];
            Double[] variances = new Double[df.headers.size()];

            for (int i = 0; i < df.headers.size(); i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(9, header.length());

                for (Object index : groupIndexes) {
                    means[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        means[i] += (double) (int) cellValue;
                    }
                }
                means[i] /= groupIndexes.size();

                for (Object index : groupIndexes) {
                    variances[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        variances[i] += Math.pow((double) ((int) cellValue) - means[i], 2);
                    }
                }
                variances[i] /= groupIndexes.size();
            }

            df.addRow(variances, groupName);
        }

        return df;
    }

    public DataFrame variance(Object header) {
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException("El header no pertenece al DataFrame.");
        }

        Object firstValue = this.getColumn(header).getCellValue(0);
        int firstCount = 1;

        while (firstValue == null) {
            firstValue = this.getColumn(header).getCellValue(firstCount);
            firstCount++;
        }

        if (!firstValue.getClass().equals(Integer.class)) {
            throw new IllegalArgumentException("La columna seleccionada no es numérica.");
        }

        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();

        df.addColumn("variance " + header.toString(), column);

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            
            Double mean = 0.0;
            Double[] variance = {0.0};

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);
                if (cellValue != null) {
                    mean += (double) (int) cellValue;
                }
            }
            mean /= groupIndexes.size();

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);
                if (cellValue != null) {
                    variance[0] += Math.pow((double) ((int) cellValue) - mean, 2);
                }
            }
            variance[0] /= groupIndexes.size();

            df.addRow(variance, groupName);
        }

        return df;
    }

    public DataFrame variance(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame.");
        }

        if (!Arrays.stream(headers).allMatch(header -> {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            return firstValue.getClass().equals(Integer.class);
        })) {
            throw new IllegalArgumentException("Alguna de las columnas seleccionadas no es numérica.");
        }

        DataFrame df = new DataFrame();

        for (Object header : headers) {
            Column<Object> column = new Column<>();
            df.addColumn("variance " + header.toString(), column);
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            
            Double[] means = new Double[headers.length];
            Double[] variances = new Double[headers.length];

            for (int i = 0; i < headers.length; i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(9, header.length());

                for (Object index : groupIndexes) {
                    means[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(headers[i]).getCellValue(cellIndex);
                    if (cellValue != null) {
                        means[i] += (double) (int) cellValue;
                    }
                }
                means[i] /= groupIndexes.size();

                for (Object index : groupIndexes) {
                    variances[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        variances[i] += Math.pow((double) ((int) cellValue) - means[i], 2);
                    }
                }
                variances[i] /= groupIndexes.size();
            }

            df.addRow(variances, groupName);
        }

        return df;
    }

    public DataFrame std() {
        DataFrame df = new DataFrame();

        for (Object header : this.headers) {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            if (firstValue.getClass().equals(Integer.class)) {
                Column<Object> column = new Column<>();
                df.addColumn("std " + header.toString(), column);
            }
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            
            Double[] means = new Double[df.headers.size()];
            Double[] stds = new Double[df.headers.size()];

            for (int i = 0; i < df.headers.size(); i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(4, header.length());

                for (Object index : groupIndexes) {
                    means[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        means[i] += (double) (int) cellValue;
                    }
                }
                means[i] /= groupIndexes.size();

                for (Object index : groupIndexes) {
                    stds[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        stds[i] += Math.pow((double) ((int) cellValue) - means[i], 2);
                    }
                }
                stds[i] /= groupIndexes.size();
                stds[i] = Math.sqrt(stds[i]);
            }

            df.addRow(stds, groupName);
        }

        return df;
    }

    public DataFrame std(Object header) {
        if (!this.headers.contains(header)) {
            throw new IllegalArgumentException("El header no pertenece al DataFrame.");
        }

        Object firstValue = this.getColumn(header).getCellValue(0);
        int firstCount = 1;

        while (firstValue == null) {
            firstValue = this.getColumn(header).getCellValue(firstCount);
            firstCount++;
        }

        if (!firstValue.getClass().equals(Integer.class)) {
            throw new IllegalArgumentException("La columna seleccionada no es numérica.");
        }

        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();

        df.addColumn("std " + header.toString(), column);

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            
            Double mean = 0.0;
            Double[] std = {0.0};

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);
                if (cellValue != null) {
                    mean += (double) (int) cellValue;
                }
            }
            mean /= groupIndexes.size();

            for (Object index : groupIndexes) {
                int cellIndex = this.indexes.indexOf(index);
                Object cellValue = this.getColumn(header).getCellValue(cellIndex);
                if (cellValue != null) {
                    std[0] += Math.pow((double) ((int) cellValue) - mean, 2);
                }
            }
            std[0] /= groupIndexes.size();
            std[0] = Math.sqrt(std[0]);

            df.addRow(std, groupName);
        }

        return df;
    }

    public DataFrame std(Object[] headers) {
        if (!this.headers.containsAll(Arrays.asList(headers))) {
            throw new IllegalArgumentException("Alguno de los headers no pertenece al DataFrame.");
        }

        if (!Arrays.stream(headers).allMatch(header -> {
            Object firstValue = this.getColumn(header).getCellValue(0);
            int firstCount = 1;

            while (firstValue == null) {
                firstValue = this.getColumn(header).getCellValue(firstCount);
                firstCount++;
            }

            return firstValue.getClass().equals(Integer.class);
        })) {
            throw new IllegalArgumentException("Alguna de las columnas seleccionadas no es numérica.");
        }

        DataFrame df = new DataFrame();

        for (Object header : headers) {
            Column<Object> column = new Column<>();
            df.addColumn("std " + header.toString(), column);
        }

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            
            Double[] means = new Double[headers.length];
            Double[] stds = new Double[headers.length];

            for (int i = 0; i < headers.length; i++) {
                String header = df.headers.get(i).toString();
                String originalHeader = header.substring(4, header.length());

                for (Object index : groupIndexes) {
                    means[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(headers[i]).getCellValue(cellIndex);
                    if (cellValue != null) {
                        means[i] += (double) (int) cellValue;
                    }
                }
                means[i] /= groupIndexes.size();

                for (Object index : groupIndexes) {
                    stds[i] = 0.0;
                    int cellIndex = this.indexes.indexOf(index);
                    Object cellValue = this.getColumn(originalHeader).getCellValue(cellIndex);
                    if (cellValue != null) {
                        stds[i] += Math.pow((double) ((int) cellValue) - means[i], 2);
                    }
                }
                stds[i] /= groupIndexes.size();
                stds[i] = Math.sqrt(stds[i]);
            }

            df.addRow(stds, groupName);
        }

        return df;
    }

    public DataFrame count() {
        DataFrame df = new DataFrame();
        Column<Object> column = new Column<>();
        df.addColumn("count", column);

        for (Map.Entry<String, List<Object>> group : this.groups.entrySet()) {
            String groupName = group.getKey();
            List<Object> groupIndexes = group.getValue();
            Integer[] count = {groupIndexes.size()};

            df.addRow(count, groupName);
        }

        return df;
    }
}
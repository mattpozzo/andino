package src.interfaces;

import src.DataFrame;

public interface DataReader {
    abstract DataFrame read(String filePath);
}

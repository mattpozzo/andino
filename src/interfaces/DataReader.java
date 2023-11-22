package src.interfaces;

import src.DataFrame;

public interface DataReader {
    public abstract DataFrame read(String filePath);
}

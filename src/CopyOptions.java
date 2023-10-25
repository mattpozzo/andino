package src;

public class CopyOptions {
    private boolean deep;

    public CopyOptions(boolean deep) {
        this.deep = deep;
    }

    public boolean getDeep() {
        return this.deep;
    }

    public void setDeep(boolean deep) {
        this.deep = deep;
    }

    @Override
    public String toString() {
        return "CopyOptions [deep=" + deep + "]";
    }
}

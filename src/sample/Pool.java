package sample;

public class Pool {
    private int poolSize;
    public Pool(int poolSize)
    {
        setPoolSize(poolSize);
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}

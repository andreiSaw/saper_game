package sample;

import javax.swing.*;
import java.awt.*;

public class Pool {
    private int poolSize=-1;
    private Cell[][] cells;
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
    private JPanel makeGrid()
    {
        JPanel panel = new JPanel(new GridLayout(poolSize,poolSize));
        cells = new Cell[poolSize][poolSize];
        for(int i = 0; i< poolSize; i++){
            for(int j = 0; j<poolSize; j++){
                cells[i][j] = new Cell(this);
                cells[i][j].setId(getID());
                panel.add(cells[i][j].getButton());
            }
        }
        return panel;
    }
}

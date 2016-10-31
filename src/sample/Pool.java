package sample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pool {
    private int poolSize = -1;
    private int limit;
    private int cellID = 0;
    private Cell[][] cells;
    private JFrame frame;
    private Player player;


    /**
     * Constructor
     *
     * @param frame
     * @param poolSize
     * @param player
     */
    public Pool(JFrame frame, int poolSize, Player player) {
        setPoolSize(poolSize);
        this.player = player;
        setFrame(frame);
        frame.setContentPane(makeGrid());
        limit = poolSize - 2;
        setMinesLocation();
        setCellValues();
        frame.pack();
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    /**
     * Method creates a JPanel to append the existing JFrame, with cells and mines
     *
     * @return panel
     */
    private JPanel makeGrid() {
        JPanel panel = new JPanel(new GridLayout(poolSize, poolSize));
        cells = new Cell[poolSize][poolSize];
        for (int i = 0; i < poolSize; i++) {
            for (int j = 0; j < poolSize; j++) {
                cells[i][j] = new Cell(this, player);
                cells[i][j].setCellID(getID());
                panel.add(cells[i][j].getButton());
            }
        }
        return panel;
    }

    /**
     * @return
     */
    public int getID() {
        int id = cellID;
        cellID++;
        return id;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }


    /**
     *
     */
    private void setMinesLocation() {
        ArrayList<Integer> loc = generateMinesLocation(poolSize);
        for (int i : loc) {
            getCell(i).setVal(-1);
        }
    }

    /**
     * Choose random places for mines
     *
     * @param q
     * @return
     */
    public ArrayList<Integer> generateMinesLocation(int q) {
        ArrayList<Integer> loc = new ArrayList<>();
        int random;
        for (int i = 0; i < q; ) {
            random = (int) (Math.random() * (poolSize * poolSize));
            if (!loc.contains(random)) {
                loc.add(random);
                i++;
            }
        }
        return loc;
    }

    /**
     * @param id
     * @return
     */
    public Cell getCell(int id) {
        for (Cell[] a : cells) {
            for (Cell b : a) {
                if (b.getCellID() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * if player failed
     */
    public void fail() {
        for (Cell[] a : cells) {
            for (Cell b : a) {
                b.reveal();
            }
        }
    }


    /**
     * This method count number of mines around particular cell and set its value
     */
    public void setCellValues() {
        for (int i = 0; i < poolSize; i++) {
            for (int j = 0; j < poolSize; j++) {
                if (cells[i][j].getVal() != -1) {
                    if (j >= 1 && cells[i][j - 1].getVal() == -1) cells[i][j].incrementVal();
                    if (j <= limit && cells[i][j + 1].getVal() == -1) cells[i][j].incrementVal();
                    if (i >= 1 && cells[i - 1][j].getVal() == -1) cells[i][j].incrementVal();
                    if (i <= limit && cells[i + 1][j].getVal() == -1) cells[i][j].incrementVal();
                    if (i >= 1 && j >= 1 && cells[i - 1][j - 1].getVal() == -1) cells[i][j].incrementVal();
                    if (i <= limit && j <= limit && cells[i + 1][j + 1].getVal() == -1) cells[i][j].incrementVal();
                    if (i >= 1 && j <= limit && cells[i - 1][j + 1].getVal() == -1) cells[i][j].incrementVal();
                    if (i <= limit && j >= 1 && cells[i + 1][j - 1].getVal() == -1) cells[i][j].incrementVal();
                }
            }
        }
    }

    /**
     * This method starts chain reaction. When user click on particular cell, if cell is empty (value = 0) this
     * method look for other empty cells next to activated one. If finds one, it call checkCell and in effect,
     * start next scan on its closest area
     */
    public void scanForEmptyCells() {
        for (int i = 0; i < poolSize; i++) {
            for (int j = 0; j < poolSize; j++) {
                if (cells[i][j].isChecked()) {
                    if (j >= 1 && cells[i][j - 1].isEmpty()) cells[i][j - 1].checkCell();
                    if (j <= limit && cells[i][j + 1].isEmpty()) cells[i][j + 1].checkCell();
                    if (i >= 1 && cells[i - 1][j].isEmpty()) cells[i - 1][j].checkCell();
                    if (i <= limit && cells[i + 1][j].isEmpty()) cells[i + 1][j].checkCell();
                    if (i >= 1 && j >= 1 && cells[i - 1][j - 1].isEmpty()) cells[i - 1][j - 1].checkCell();
                    if (i <= limit && j <= limit && cells[i + 1][j + 1].isEmpty()) cells[i + 1][j + 1].checkCell();
                    if (i >= 1 && j <= limit && cells[i - 1][j + 1].isEmpty()) cells[i - 1][j + 1].checkCell();
                    if (i <= limit && j >= 1 && cells[i + 1][j - 1].isEmpty()) cells[i + 1][j - 1].checkCell();
                }
            }
        }
    }
}

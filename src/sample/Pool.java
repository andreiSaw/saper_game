package sample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Pool is where all cells are placed
 */
public class Pool {
    /**
     * The size of pool
     */
    private int poolSize = -1;
    private int limit;
    private int cellID = 0;
    /**
     * Inner conception
     */
    private Cell[][] cells;
    private JFrame frame;
    private Player player;
    private int qCells;
    private int qMines;
    private SaperDemo mainGame;


    /**
     * Constructor
     *
     * @param frame
     * @param poolSize
     * @param player
     */
    public Pool(JFrame frame, int poolSize, Player player, SaperDemo g) {
        setPoolSize(poolSize);
        setMainGame(g);
        setqCells(poolSize * poolSize);
        setqMines(2 * poolSize);
        this.setPlayer(player);
        setFrame(frame);
        frame.setContentPane(makeGrid());
        setLimit(poolSize - 2);
        setMinesLocation();
        setCellValues();
        frame.pack();
    }

    /*
        public Pool(Pool sourcePool) {
            setPoolSize(sourcePool.getPoolSize());
            setMainGame(sourcePool.getMainGame());
            setqCells(sourcePool.getqCells());
            setqMines(sourcePool.getqMines());
            setPlayer(new Player(sourcePool.getPlayer()));
            setFrame(sourcePool.getFrame());
            setLimit(sourcePool.getLimit());
            cells=new Cell[poolSize][poolSize];
            copyCells(sourcePool.getCells());
            setCellID(sourcePool.getCellID());
            frame.pack();
        }
    */
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
                cells[i][j] = new Cell(this);
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
        int id = getCellID();
        setCellID(getCellID() + 1);
        return id;
    }

    protected Cell[][] getCells() {
        return cells;
    }

    /**
     * JFrame
     */
    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }


    /**
     * http://codereview.stackexchange.com/questions/88636/beginner-minesweeper-game
     */
    private void setMinesLocation() {
        ArrayList<Integer> loc = generateMinesLocation(getqMines());
        for (int i : loc) {
            getCell(i).setVal(-1);
        }
    }

    /**
     * Choose random places for mines
     * <p>
     * http://codereview.stackexchange.com/questions/88636/beginner-minesweeper-game
     *
     * @param q int represents quanity of mines
     * @return list of mines location
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
     * Method returns cell's instance by his ID
     *
     * @param id cellID
     * @return cell's instance
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
        getMainGame().finishAndShow("lose");
    }

    /**
     * Method opens whole Poll
     */
    public void revealPool() {
        for (Cell[] a : cells) {
            for (Cell b : a) {
                b.reveal();
            }
        }
    }


    /**
     * This method count number of mines around particular cell and set its value
     * <p>
     * http://codereview.stackexchange.com/questions/88636/beginner-minesweeper-game
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
    protected void scanCells(Point p) {
        if (cells[p.x][p.y].isChecked()) {
            return;
        }
        cells[p.x][p.y].reveal();
        incrementScore(cells[p.x][p.y].getVal() + 1);

        if (cells[p.x][p.y].getVal() != 0) {
            return;
        }

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                Point p1 = new Point(p.x + i, p.y + j);
                if (inLimit(p1))
                    scanCells(p1);
            }
        }
    }

    /**
     * @param p
     * @return
     */
    private boolean inLimit(Point p) {
        return (p.x >= 0 && p.x < poolSize && p.y >= 0 && p.y < poolSize);
    }

    /**
     * @param id
     * @return
     */
    public Point getCellLoc(int id) {
        int startId = cells[0][0].getCellID();
        int i, j;
        int dif = id - startId;
        i = dif / poolSize;
        j = dif % poolSize;
        return new Point(i, j);
    }

    /**
     * @param qCells
     */
    public void decremqCells(int qCells) {
        this.setqCells(this.getqCells() - qCells);
    }

    /**
     * @param val
     */
    public void incrementScore(int val) {
        player.incrementScore(val);
    }

    public void setMainGame(SaperDemo mainGame) {
        this.mainGame = mainGame;
    }

    public void disablePool() {
        for (Cell[] a : cells) {
            for (Cell b : a) {
                b.disable();
            }
        }
    }

    public boolean checkQ() {
        return (this.getqMines() == this.getqCells());
    }

    public void finishGame() {
        getMainGame().finishAndShow("win");
    }

    /**
     * int represents Quanity of cells on Pool
     */
    public int getqCells() {
        return qCells;
    }

    /**
     * Represents SaperDemo instance
     */
    public SaperDemo getMainGame() {
        return mainGame;
    }

    public void setqCells(int qCells) {
        this.qCells = qCells;
    }

    /**
     * int represents Quanity of mines on Pool
     */
    public int getqMines() {
        return qMines;
    }

    public void setqMines(int qMines) {
        this.qMines = qMines;
    }

    /**
     * Player
     */
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Inner bound
     */
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
/*
    public void copyCells(Cell[][] sourceCells) {
        for (int i = 0; i < getPoolSize(); i++) {
            for (int j = 0; j < getPoolSize(); j++) {
                cells[i][j] = new Cell(sourceCells[i][j]);
            }
        }
    }
    */

    /**
     * inner conception of enumeration
     */
    public int getCellID() {
        return cellID;
    }

    public void setCellID(int cellID) {
        this.cellID = cellID;
    }
}

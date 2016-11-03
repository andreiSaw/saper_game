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
        qCells = poolSize * poolSize;
        qMines = 2*poolSize;
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
     * http://codereview.stackexchange.com/questions/88636/beginner-minesweeper-game
     */
    private void setMinesLocation() {
        ArrayList<Integer> loc = generateMinesLocation(qMines);
        for (int i : loc) {
            getCell(i).setVal(-1);
        }
    }

    /**
     * Choose random places for mines
     * <p>
     * http://codereview.stackexchange.com/questions/88636/beginner-minesweeper-game
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
        revealPool();
        JOptionPane.showMessageDialog(frame,
                "You lose! Your score " + player.getScore());
        mainGame.finishgame();
    }

    /**
     *
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
        cells[p.x][p.y].setChecked(true);
        cells[p.x][p.y].reveal();
        incrementScore(1);
        decremqCells(1);

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
        this.qCells -= qCells;
        if (this.qMines == this.qCells) {
            JOptionPane.showMessageDialog(frame,
                    "You win! Your score " + player.getScore());
            mainGame.finishgame();
        }
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
}

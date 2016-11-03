package sample;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell implements ActionListener {
    public Cell(Pool pool, Player player) {
        setPool(pool);
        //setPlayer(player);
        button = new JButton("o");
        button.setForeground(Color.BLUE);
        button.addActionListener(this);
        setChecked(false);
        int btnSize = 50;
        button.setPreferredSize(new Dimension(btnSize, btnSize));
        button.setMargin(new Insets(0, 0, 0, 0));
    }

    public void disable() {
        button.removeActionListener(this);
    }

    private int cellID;
    /**
     * value stands -1 for mine, 0 stands for 0 mines, etc.
     */
    private int val;
    /**
     * true for revealed by player cell, false otherwise
     */
    private boolean isChecked;
    /**
     * internal conception
     */
    private JButton button;
    private Pool pool;
    //private Player player;

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        checkCell();
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getCellID() {
        return cellID;
    }

    public void setCellID(int cellID) {
        this.cellID = cellID;
    }

    /**
     *
     */
    public void incrementVal() {
        val++;
    }

    /**
     *
     */
    public int displayValue() {
        if (val == -1) {
            button.setText("!");
            button.setForeground(Color.RED);
        } else if (val != 0) {
            button.setText(String.valueOf(val));
            button.setForeground(Color.BLUE);
        } else {
            button.setText("");
        }
        return val;
    }

    /**
     *
     */
    public void checkCell() {
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        if (val == 0) {
            pool.scanCells(pool.getCellLoc(getCellID()));
            return;
        } else if (val == -1) {
            pool.fail();
        } else {
            pool.incrementScore(val + 1);
        }
        reveal();
        if (pool.checkQ()) {
            pool.finishGame();
        }
    }

    /**
     *
     */
    public int reveal() {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        pool.decremqCells(1);

        setChecked(true);
        disable();
        return displayValue();
    }
}
package sample;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell implements ActionListener {
    private int btnSize = 50;
    private int inset=0;
    /**
     * @param pool
     */
    public Cell(Pool pool) {
        setPool(pool);
        button = new JButton("o");
        button.setForeground(Color.BLUE);
        button.addActionListener(this);
        setChecked(false);

        button.setPreferredSize(new Dimension(getBtnSize(), getBtnSize()));
        button.setMargin(new Insets(getInset(), getInset(), getInset(), getInset()));
    }
    /*
    public Cell(Cell sourceCell)
    {
        Pool clonnedPool=new Pool(sourceCell.getPool());
        setPool(clonnedPool);
        button=new JButton(String.valueOf(sourceCell.getVal()));
        button.setForeground(sourceCell.button.getForeground());
        button.addActionListener(this);
        setChecked(sourceCell.isChecked());
        int btnsize=sourceCell.getBtnSize();
        button.setPreferredSize(new Dimension(btnsize,btnsize));
        int inset=sourceCell.getInset();
        button.setMargin(new Insets(inset, inset, inset, inset));
    }
*/
    /**
     * Makes cell inaccessible
     */
    public void disable() {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
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
    /**
     * Game pool
     */
    private Pool pool;

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
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
     * Increment cell value
     */
    public void incrementVal() {
        val++;
    }

    /**
     * Directly shows cell's value
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
     * Method checking cell and neighbours
     */
    public void checkCell() {
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
     * Method makes cell inaccessible and show value of cell
     */
    public int reveal() {
        pool.decremqCells(1);

        setChecked(true);
        disable();
        return displayValue();
    }

    public int getBtnSize() {
        return btnSize;
    }

    public int getInset() {
        return inset;
    }
}
package sample;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell implements ActionListener {
    public Cell(Pool pool) {
        setPool(pool);
        button = new JButton();
        button.addActionListener(this);
        setChecked(false);
        button.setPreferredSize(new Dimension(20, 20));
        button.setMargin(new Insets(0, 0, 0, 0));
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

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

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
}

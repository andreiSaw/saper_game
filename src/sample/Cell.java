package sample;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell implements ActionListener {
    public Cell(Pool pool,Player player) {
        setPool(pool);
        setPlayer(player);
        button = new JButton(".");
        button.setForeground(Color.BLUE);
        button.addActionListener(this);
        setChecked(false);
        button.setPreferredSize(new Dimension(25, 25));
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
    private Player player;

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        checkCell();
        button.setForeground(Color.RED);
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

    public void incrementVal() {
        val++;
    }

    public void displayValue() {
        if (val == -1) {
            button.setText("\u2600");
           // button.setBackground(Color.RED);

        } else if (val != 0) {
            //button.setForeground(Color.BLUE);
            button.setText(String.valueOf(val));
        }
    }

    public void checkCell() {
        button.setEnabled(false);
        displayValue();
        isChecked = true;
        if (val == 0) {
            pool.scanForEmptyCells();
            player.incrementScore(val);
        }
        if (val == -1) {
            pool.fail();
        }
    }

    public void reveal() {
        displayValue();
        button.setEnabled(false);
    }

    public boolean isEmpty() {
        return !isChecked() && val == 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

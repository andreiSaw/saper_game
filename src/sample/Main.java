package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;

public class Main {
    static JFrame frame;
    static Game game;
    static Random rnd = new Random();

    public static void main(String[] args) {
        frame = new JFrame("Saper Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("Start game");
        button.addActionListener(buttonListener);
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
    }

    public static ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = (String) JOptionPane.showInputDialog(frame, "Hello\nWhat's ur name?:");
            int poolsize = 10 + rnd.nextInt(10);
            game = new Game(s,poolsize );

        }
    };
}

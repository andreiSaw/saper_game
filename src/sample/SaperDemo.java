package sample;

import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Main class, the entry point here
 */
public class SaperDemo implements ChangeListener, ActionListener {
    Pool pool;
    Player player;
    static JFrame frame;
    private static Random rnd = new Random();

    /**
     * Creates menuBar for the app
     *
     * @return JMenuBar filled with menuItems
     */
    public JMenuBar createJMenuBar() {
        JMenuBar mainMenuBar;
        JMenu menu1;
        JMenuItem startGameItem, finishGameItem;
        mainMenuBar = new JMenuBar();
        menu1 = new JMenu("Main Menu");
        mainMenuBar.add(menu1);
        // Creating the MenuItems
        startGameItem = new JMenuItem("Start New Game");
        startGameItem.addActionListener(this);
        menu1.add(startGameItem);

        finishGameItem = new JMenuItem("Finish that Game");
        finishGameItem.addActionListener(this);
        menu1.add(finishGameItem);

        return mainMenuBar;
    }

    /**
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        String s = source.getText();
        if (s.contains("Start")) {
            startnewgame();
        } else {
            finishgame();
        }
    }

    /**
     *
     */
    public void startnewgame() {
        String s1 = JOptionPane.showInputDialog(frame, "Hello\nWhat's ur name?:");
        int poolsize = 10 + rnd.nextInt(10);

        player = new Player(s1);
        pool = new Pool(frame, poolsize, player, this);
    }

    /**
     *
     */
    public void finishgame() {
        if (pool != null) {
            pool.revealPool();
        }
    }

    /**
     * @param o Object
     * @return String the class name, no package info
     */

    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1); // Returns only Class name
    }

    /**
     * The entry point
     *
     * @param args
     */
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(SaperDemo::createGUI);
    }

    /**
     * Create and set up the window
     */
    private static void createGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Saper Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SaperDemo app = new SaperDemo();
        frame.setJMenuBar(app.createJMenuBar());
        frame.setSize(500, 300);

        //frame.pack();
        frame.setVisible(true);
    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        finishgame();
    }
}

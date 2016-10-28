package sample;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class MenuDemo implements ActionListener {

    JTextArea jtAreaOutput;
    JScrollPane jspPane;
   static JFrame frame;
    private static Random rnd=new Random();
    static Game game;

    public JMenuBar createJMenuBar() {
        JMenuBar mainMenuBar;
        JMenu menu1;
        JMenuItem startGameItem, finishGameItem;
        mainMenuBar = new JMenuBar();
        menu1 = new JMenu("Menu 1");
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

    public Container createContentPane() {
        // Create the content-pane-to-be.
        JPanel jplContentPane = new JPanel(new BorderLayout());
        jplContentPane.setLayout(new BorderLayout());// Can do it either way
        // to set layout
        jplContentPane.setOpaque(true);
        // Create a scrolled text area.
        jtAreaOutput = new JTextArea(5, 30);
        jtAreaOutput.setEditable(false);
        jspPane = new JScrollPane(jtAreaOutput);
        // Add the text area to the content pane.
        jplContentPane.add(jspPane, BorderLayout.CENTER);
        return jplContentPane;
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        String s =  source.getText();
        if(s.contains("Start"))
        {
            String s1 = JOptionPane.showInputDialog(frame, "Hello\nWhat's ur name?:");
            int poolsize = 10 + rnd.nextInt(10);
            game = new Game(s1, poolsize);
        }
        jtAreaOutput.append(s + "\n");
        jtAreaOutput.setCaretPosition(jtAreaOutput.getDocument()
                .getLength());
    }

    // Returns the class name, no package info
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1); // Returns only Class name
    }
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        // Create and set up the window.
        frame = new JFrame("Saper Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuDemo app = new MenuDemo();
        frame.setJMenuBar(app.createJMenuBar());
        frame.setContentPane(app.createContentPane());
        frame.setSize(500, 300);
        frame.pack();
        frame.setVisible(true);
    }
}

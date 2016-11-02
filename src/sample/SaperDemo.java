package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;


/**
 * Main class, the entry point here
 */
public class SaperDemo implements ChangeListener, ActionListener {
    Pool pool;
    Player player;
    static JFrame frame;
    private static Random rnd = new Random();
    private final String filePath = "res.json";

    /**
     * Creates menuBar for the app
     *
     * @return JMenuBar filled with menuItems
     */
    public JMenuBar createJMenuBar() {
        JMenuBar mainMenuBar;
        JMenu menu1;
        JMenuItem startGameItem, finishGameItem, topScoreItem;
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

        topScoreItem = new JMenuItem("Top Scores");
        topScoreItem.addActionListener(this);
        menu1.add(topScoreItem);

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
        } else if (s.contains("Top")) {
            showScores();
        } else {
            finishgame();
        }
    }

    /**
     * http://stackoverflow.com/questions/18831948/how-parsing-jsonarray-in-java-with-json-simple
     *
     * @return
     */
    private JSONArray parseObjectsFromFile() {
        JSONParser parser = new JSONParser();

        try {
            if (!checkIfFileEx()) {
                return new JSONArray();
            }
            Object obj = parser.parse(new FileReader(filePath));

            JSONArray jsonArray = (JSONArray) obj;

            return jsonArray;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
    private void showScores() {
        // JSONArray jsonArray=  parseObjectsFromFile();

    }

    /**
     *
     */
    private void appendScore() {
        JSONArray jsonArray = parseObjectsFromFile();
        JSONObject resultObj = new JSONObject();
        resultObj.put("name", player.getName());
        resultObj.put("score", player.getScore());
        jsonArray.add(resultObj);
        try {
            checkIfFileEx();
            FileWriter file = new FileWriter(filePath);
            file.write(String.valueOf(jsonArray));
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfFileEx() {
        File f = new File(filePath);
        if ((!f.exists())||f.length()==0) {
            try {
                f.createNewFile();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
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
            pool.disablePool();
            appendScore();
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

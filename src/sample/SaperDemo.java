package sample;

import com.oracle.javafx.jmx.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


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
        JMenu menu1, menu2;
        JMenuItem startGameItem, finishGameItem, topScoreItem, resetScoresItems;
        mainMenuBar = new JMenuBar();
        menu1 = new JMenu("Main Menu");
        menu2 = new JMenu("Score");
        mainMenuBar.add(menu1);
        mainMenuBar.add(menu2);

        // Creating the MenuItems
        startGameItem = new JMenuItem("Start New Game");
        startGameItem.addActionListener(this);
        menu1.add(startGameItem);

        finishGameItem = new JMenuItem("Finish that Game");
        finishGameItem.addActionListener(this);
        menu1.add(finishGameItem);

        topScoreItem = new JMenuItem("Top scores");
        topScoreItem.addActionListener(this);
        menu2.add(topScoreItem);

        resetScoresItems = new JMenuItem("Reset scores");
        resetScoresItems.addActionListener(this);
        menu2.add(resetScoresItems);

        return mainMenuBar;
    }

    /**
     * Method to listen actions with menuBar
     *
     * @param e standard
     */
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        String s = source.getText();

        if (s.contains("Start")) {
            startnewgame();
        } else if (s.contains("Top")) {
            showScores();
        } else if (s.contains("Finish")) {
            finishAndShow("finished");
        } else if (s.contains("Reset")) {
            resetScores();
        }
    }

    /**
     * Deletes all score from scoreFile
     */
    private void resetScores() {
        try {
            checkIfFileEx();
            FileWriter file = new FileWriter(filePath);
            file.write(String.valueOf(new JSONArray()));
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        JOptionPane.showMessageDialog(frame, "Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method parse jsonObjects from file
     * http://stackoverflow.com/questions/18831948/how-parsing-jsonarray-in-java-with-json-simple
     *
     * @return parsed array of jsonObjects
     */
    private JSONArray parseObjectsFromFile() {
        JSONParser parser = new JSONParser();

        try {
            if (!checkIfFileEx()) {
                return new JSONArray();
            }
            Object obj = parser.parse(new FileReader(filePath));

            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            createNewFile(new File(filePath));
            return new JSONArray();
        }
    }

    /**
     * Method provides user top scores
     */
    private void showScores() {
        JOptionPane.showMessageDialog(frame, new JScrollPane(getScoreTable()), "Top scores", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @return
     */
    private JTable getScoreTable() {
        JSONArray jsonArray = parseObjectsFromFile();
        int n = jsonArray.size();
        Object[][] data = new Object[n][2];
        Object[] colNames;
        colNames = new Object[]{"Name", "Score"};
        for (int i = 0; i < n; i++) {
            JSONObject jsonObject =
                    (JSONObject) jsonArray.get(i);
            data[i][0] = jsonObject.get("name");
            data[i][1] = jsonObject.get("score");
        }
        return new JTable(data, colNames);
    }

    /**
     * Method appends a scoreFile with new entries
     */
    private void appendScore() {
        JSONArray jsonArray = parseObjectsFromFile();
        JSONObject resultObj = new JSONObject();
        resultObj.put("name", player.getName());
        resultObj.put("score", player.getScore());

        jsonArray.add(resultObj);
        jsonArray = sortJsonArray(jsonArray);
        int maxTop = 10;
        while (jsonArray.size() >= maxTop) {
            jsonArray.remove(0);
        }

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

    /**
     * @return true if file exists, false - otherwise
     */
    private boolean checkIfFileEx() {
        File f = new File(filePath);
        if ((!f.exists()) || f.length() == 0) {
            createNewFile(f);
        }
        return true;
    }

    private void createNewFile(File f) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sorts JSONArray
     *
     * @param jsonArray source arr
     * @return sorted arr
     */
    private JSONArray sortJsonArray(JSONArray jsonArray) {

        JSONArray sortedJsonArray = new JSONArray();

        List<JSONObject> jsonValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonValues.add((JSONObject) jsonArray.get(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {

            private static final String KEY_NAME = "score";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                int valA = 0;
                int valB = 0;

                try {

                    valA = Integer.parseInt(String.valueOf(a.get(KEY_NAME)));
                    valB = Integer.parseInt(String.valueOf(b.get(KEY_NAME)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return Integer.compare(valA, valB);
            }
        });

        for (int i = 0; i < jsonArray.size(); i++) {
            sortedJsonArray.add(jsonValues.get(i));
        }
        return sortedJsonArray;
    }

    /**
     * Method to start a new game
     */
    public void startnewgame() {
        String s1 = JOptionPane.showInputDialog(frame, "Hello\nWhat's ur name?:");
        int poolsize = 10 + rnd.nextInt(10);

        player = new Player(s1);
        pool = new Pool(frame, poolsize, player, this);
    }

    public void startnewgameLastPool() {
        String s1 = JOptionPane.showInputDialog(frame, "Hello\nWhat's ur name?:");

        player = new Player(s1);
        int poolSize=pool.getPoolSize();
        pool = new Pool(frame,poolSize,player,this);

    }


    /**
     * Method to finish current game
     */
    public void finishgame() {
        if (pool != null) {
            pool.revealPool();
            pool.disablePool();
            appendScore();
        }
    }

    /**
     * The entry point
     *
     * @param args standard
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

    protected void showFinal(String res) {
        String[] choices = {"Start new Game", "Re-run that Game", "Finish"};
        int response = JOptionPane.showOptionDialog(frame, new JScrollPane(getScoreTable()),
                String.format("You %s! Your score %d", res, player.getScore()), JOptionPane.YES_NO_OPTION
                , JOptionPane.PLAIN_MESSAGE, null, choices, "None of your business");
        switch (response) {
            case 0:
                startnewgame();
                break;
            case 1:
                startnewgameLastPool();
                break;
            case 2:
                finishgame();
                break;
        }
    }
    protected void finishAndShow(String res)
    {
        if (pool==null||pool.getqCells()<=0)
        {
            return;
        }
        Thread t1 = new Thread(() -> {
            finishgame();
        });
        Thread t2 = new Thread(() -> {
            showFinal(res);
        });
        //to run in the main thread not different
        t1.run();
        t2.run();
    }
}

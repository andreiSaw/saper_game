package sample;

public class Player implements java.io.Serializable {
    private String name;
    private int score;

    public Player(String name) {
        setName(name);
        setScore(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore(int x) {
        score += x;
    }

    @Override
    public String toString() {
        return String.format("%s has %d", getName(),getScore());
    }
}

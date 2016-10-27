package sample;


public class Game {
    private Player player;
    private Pool pool;

    public Game(String playerName, int poolSize) {
        setPlayer(new Player(playerName));
        setPool(new Pool(poolSize));
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

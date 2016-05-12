package knof.model;

public class Challenge {
    public final int turnTime;
    public final String player;
    public final String game;
    public final int id;
    public Server server;

    public Challenge(int turnTime, String player, String game, int id, Server server) {
        this.turnTime = turnTime;
        this.player = player;
        this.game = game;
        this.id = id;
        this.server = server;
    }
}

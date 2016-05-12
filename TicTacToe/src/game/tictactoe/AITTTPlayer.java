package game.tictactoe;

import knof.connection.Connection;
import knof.model.game.AIPlayer;
import knof.model.game.Game;

/**
 * Created by Thijs on 10/05/2016.
 */
public class AITTTPlayer extends AIPlayer {
    public AITTTPlayer(String name, String side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    @Override
    protected int calculateMove() {
        return 0;
    }
}

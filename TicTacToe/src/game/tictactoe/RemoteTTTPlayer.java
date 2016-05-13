package game.tictactoe;

import knof.connection.Connection;
import knof.gamelogic.Side;
import knof.model.game.DummyPlayer;

/**
 * Created by Thijs on 13/05/2016.
 */
public class RemoteTTTPlayer extends DummyPlayer {

    public RemoteTTTPlayer(String name, Side side, Connection connection) {
        super(name, side, connection);
    }

    @Override
    public void setTurn() {

    }
}

package guess;

import knof.connection.Connection;
import knof.gamelogic.Side;
import knof.model.RemotePlayer;

/**
 * Created by Henk Dieter Oordt on 3-5-2016.
 */
public class RemoteGuessPlayer extends RemotePlayer {
    public RemoteGuessPlayer(String name, Side side, Connection connection) {
        super(name, side, connection);
    }

    @Override
    public void setTurn() {

    }
}

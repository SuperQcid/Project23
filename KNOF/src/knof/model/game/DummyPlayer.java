package knof.model.game;

import knof.connection.Connection;
import knof.gamelogic.Side;

/**
 * Created by Henk Dieter Oordt on 3-5-2016.
 */
public abstract class DummyPlayer extends Player {
    public DummyPlayer(String name, Side side, Connection connection) {
        super(name, side, connection);
    }
}

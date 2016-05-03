package knof.model;

import knof.connection.Connection;

/**
 * Created by Henk Dieter Oordt on 3-5-2016.
 */
public abstract class DummyPlayer extends Player {
    public DummyPlayer(String name, String side, Connection connection) {
        super(name, side, connection);
    }
}

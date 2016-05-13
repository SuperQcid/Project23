package knof.model.game;

import knof.connection.Connection;
import knof.gamelogic.Side;

/**
 * Created by Henk Dieter Oordt on 3-5-2016.
 */
public abstract class LocalPlayer extends Player {
    /**
     * Game, used to get the current representation in order to calculate a move
     */
    protected Game game;

    public LocalPlayer(String name, Side side, Connection connection, Game game) {
        super(name, side, connection);
        this.game = game;
    }
}

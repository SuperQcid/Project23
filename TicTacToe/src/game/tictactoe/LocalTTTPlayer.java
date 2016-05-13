package game.tictactoe;

import knof.connection.Connection;
import knof.gamelogic.Side;
import knof.model.game.Game;
import knof.model.game.LocalPlayer;

/**
 * Created by Thijs on 13/05/2016.
 */
public class LocalTTTPlayer extends LocalPlayer {

    public LocalTTTPlayer(String name, Side side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    @Override
    public void setTurn() {

    }
}

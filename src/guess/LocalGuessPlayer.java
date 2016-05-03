package guess;

import knof.connection.Connection;
import knof.gamelogic.Move;
import knof.gamelogic.Side;
import knof.model.LocalPlayer;
import knof.model.Player;

/**
 * Created by Henk Dieter Oordt on 28-4-2016.
 */
public class LocalGuessPlayer extends LocalPlayer {
    public LocalGuessPlayer(String playerName, Side side, Connection connection){
        super(playerName, side, connection);
    }

    @Override
    public void setTurn() {
        this.turn = true;
    }
}

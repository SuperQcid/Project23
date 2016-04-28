package guess;

import knof.connection.Connection;
import knof.gamelogic.Move;
import knof.gamelogic.Side;
import knof.model.Player;

/**
 * Created by Henk Dieter Oordt on 28-4-2016.
 */
public class GuessPlayer extends Player {
    public GuessPlayer(String playerName, Side side, Connection connection){
        super(playerName, side, connection);
    }

    @Override
    public void doMove() {
        this.sendMove(new Move(1,1, side));//TODO get the actual move
    }
}

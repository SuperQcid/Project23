package guess;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.Move;
import knof.gamelogic.Side;
import knof.model.Game;
import knof.model.Player;

/**
 * Created by Henk Dieter Oordt on 25-4-2016.
 */
public class GuessGame extends Game {

    public GuessGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        super(playerOneName, playerTwoName, playerOneIsLocal, 10, 1, connection);
    }

    @Override
    protected void initBoard() {
        board = new GuessBoard(WIDTH, HEIGHT);
    }

    @Override
    protected Player initPlayer(String playerName, Side side, Connection connection) {
        return new GuessPlayer(playerName, side, connection);
    }

    @Override
    protected boolean move(int move, Side side) {
        return board.place(new Move(move, HEIGHT, side));
    }

    @Override
    protected GameController initGameController() {
        System.out.println("KOEKKOEKEKOKEOK INITIALIZING GAME CONTROLLER");
        return new GuessGameController();
    }
}

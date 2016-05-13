package game.tictactoe;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.Piece;
import knof.gamelogic.Side;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.LocalPlayer;

/**
 * Created by Thijs on 10/05/2016.
 */
public class TicTacToeGame extends Game {

    TicTacToeBoard board;
    private final String LOCAL_SIDE_NAME = "X";
    private final String REMOTE_SIDE_NAME = "O";

    public TicTacToeGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        super(playerOneName, playerTwoName, playerOneIsLocal, connection);
        board = new TicTacToeBoard(this);
    }

    @Override
    protected LocalPlayer initLocalPlayer(String playerName, Connection connection) {
        Side side = new Side(LOCAL_SIDE_NAME);
        return new LocalTTTPlayer(playerName, side, connection, this);
    }

    @Override
    protected DummyPlayer initRemotePlayer(String playerName, Connection connection) {
        Side side = new Side(REMOTE_SIDE_NAME);
        return new RemoteTTTPlayer(playerName, side, connection);
    }

    @Override
    protected boolean move(int move, Side side) {
        return board.place(move, new Piece(this.getSidePlayer(side)));
    }

    @Override
    protected GameController initGameController() {
        return new TicTacToeGameController();
    }
}

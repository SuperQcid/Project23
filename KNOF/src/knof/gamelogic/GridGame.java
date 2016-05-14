package knof.gamelogic;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.Board;
import knof.gamelogic.Piece;
import knof.gamelogic.Side;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.HumanPlayer;
import knof.model.game.LocalPlayer;

public abstract class GridGame<B extends Board> extends Game {
    public  final B board;

    public GridGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        super(playerOneName, playerTwoName, playerOneIsLocal, connection);
        this.board = createBoard();
    }

    public abstract B createBoard();

    @Override
    protected LocalPlayer initLocalPlayer(String playerName, Connection connection, Side side) {
        return new HumanPlayer(playerName, side, connection, this);
    }

    @Override
    protected DummyPlayer initRemotePlayer(String playerName, Connection connection, Side side) {
        return new DummyPlayer(playerName, side, connection);
    }

    @Override
    protected boolean move(int move, Side side) {
        return board.place(move, new Piece(this.getSidePlayer(side)));
    }

    @Override
    protected abstract GameController initGameController();

    @Override
    protected abstract Side getSide1();

    @Override
    protected abstract Side getSide2();
}

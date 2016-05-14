package knof.gamelogic;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.model.game.Side;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.HumanPlayer;
import knof.model.game.LocalPlayer;

public abstract class GridGame<B extends Board> extends Game {
    private B board;

    public GridGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        super(playerOneName, playerTwoName, playerOneIsLocal, connection);
        this.board = createBoard();
    }

    public abstract B createBoard();

    @Override
    protected boolean move(int move, Side side) {
        return board.place(move, new Piece(this.getSidePlayer(side)));
    }

    @Override
    protected abstract GameController initGameController();

    @Override
    public abstract Side getSide1();

    public B getBoard() {
        return board;
    }
}

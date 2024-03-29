package knof.gamelogic;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.model.game.Side;
import knof.model.game.Game;

public abstract class GridGame<B extends Board> extends Game {
    private B board;

    public GridGame(Connection connection) {
        super(connection);
        this.board = createBoard();
    }

    public abstract B createBoard();

    @Override
    protected boolean move(int move, Side side) {
        return board.place(move, side);
    }

    @Override
    protected abstract GameController initGameController();

    @Override
    public abstract Side getSide1();

    public B getBoard() {
        return board;
    }
}

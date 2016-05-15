package knof.gamelogic;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.model.game.Side;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.HumanPlayer;
import knof.model.game.LocalPlayer;

public abstract class GridGame<B extends Board> extends Game {
    public final B board;

    public GridGame( Connection connection) {
        super(connection);
        this.board = createBoard();
    }

    public abstract B createBoard();

    @Override
    protected boolean move(int move, Side side) {
        return board.place(move, new Piece(side));
    }

    @Override
    protected abstract GameController initGameController();

    @Override
    public abstract Side getSide1();

    @Override
    public abstract Side getSide2();
}
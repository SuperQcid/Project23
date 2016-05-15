package knof.gamelogic;

import knof.connection.Connection;
import knof.model.game.HumanPlayer;
import knof.model.game.Side;

public class HumanGridGamePlayer<B extends Board> extends HumanPlayer {
    private final B board;
    private final boolean preventInvalid;

    public HumanGridGamePlayer(String name, Side side, Connection connection, GridGame<B> game, boolean preventInvalid) {
        super(name, side, connection, game);
        this.board = game.getBoard();
        this.preventInvalid = preventInvalid;
    }

    @Override
    public void receiveMove(int move) {
        if(!this.board.isValid(move, new Piece(this.side))) {
            System.out.println("WARNING: Invalid move received from controller");
            if(preventInvalid) return;
        }
        super.receiveMove(move);
    }
}

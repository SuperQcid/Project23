package game.reversi;

import knof.gamelogic.Board;
import knof.model.game.Game;
import knof.model.game.Side;

public class ReversiBoard extends Board {
    public ReversiBoard(Game game) {
        super(8, 8, game);
    }

    @Override
    public Board clone() {
        return null;
    }

    @Override
    public int getScore(Side side) {
        return 0;
    }
}

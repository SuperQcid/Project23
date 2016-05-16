package knof.ai;

import knof.gamelogic.Board;
import knof.model.game.Side;

import java.util.List;

/**
 * Created by Kevin on 5/16/2016.
 */
public class HeuristicAI {

    public Board.Pos getHeuristicMove(Board board, Side side) {
        List<Board.Pos> validMoves = board.getValidPositions(side);
        if (validMoves == null || validMoves.isEmpty()) {
            return null;
        }
        int bestValue = Integer.MIN_VALUE;
        Board.Pos bestPos = null;
        for (Board.Pos pos : validMoves) {
            if (board.getFieldValue(pos.x, pos.y) > bestValue) {
                bestValue = board.getFieldValue(pos.x, pos.y);
                bestPos = pos;
            }
        }

        return bestPos;
    }
}

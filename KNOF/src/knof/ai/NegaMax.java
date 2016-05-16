package knof.ai;

import knof.gamelogic.Board;
import knof.model.game.Side;

import java.util.List;

/**
 * Created by Kevin on 5/16/2016.
 */
public class NegaMax {

    private Side localSide;
    private Board scratchBoard;
    private int depth;

    public NegaMax(Board board, int maxDepth) {
        this.scratchBoard = board;
        this.depth = maxDepth;
    }


    /*

    */
    public NegaMax.BestMove negaMax(Board board, int depth, Side side) {
        int bestValue = Integer.MIN_VALUE;
        List<Board.Pos> validPositions = board.getValidPositions(side);
        if (depth == 0 || validPositions == null || validPositions.isEmpty()) {
            return null;
        }
        for (Board.Pos pos: validPositions) {
            Board clonedBoard = board.clone();

        }
        return null;
    }

    public static class BestMove {
        public int value;
        public Side side;
        public int x;
        public int y;
        public BestMove(Side side, int value, int x, int y) {
            this.side = side;
            this.value = value;
            this.x = x;
            this.y = y;
        }
    }

    public Board.Pos getBestScoringMoveWithoutRecursion(Board board, Side side) {
        List<Board.Pos> validMoves = board.getValidPositions(side);
        if (validMoves == null || validMoves.isEmpty()) {
            return null;
        }
        int bestValue = Integer.MIN_VALUE;
        Board.Pos bestPos = null;
        for(Board.Pos pos : validMoves) {
            if (board.getFieldValue(pos.x, pos.y) > bestValue) {
                bestValue = board.getFieldValue(pos.x, pos.y);
                bestPos = pos;
            }
        }

        return bestPos;
    }

}

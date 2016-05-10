package knof.ai;

import javafx.geometry.Side;
import knof.gamelogic.Board;

import java.util.List;

/**
 * Created by Thijs on 10/05/2016.
 */

public class BackTracker {
    private Board board;
    private int depth;

    public BackTracker(Board board, int depth) {
        this.board = board;
        this.depth = depth;
    }

    @Override
    public Move getMove(Side side) {
        return this.getBestMove(side, this.board, this.depth).move;
    }

    /**
     *
     * @param side The side for which the move is being calculated
     * @param board The board for which the move is being calculated
     * @param depth The amount of depth the recursive function is allowed to go
     * @return The best move the functions are able to calculate
     */
    public BestMove getBestMove(Side side, Board board, int depth) {
        if(depth==0) {
            return getBestScoringMove(board, side);
        }
        else {
            return getBestMoveRecursive(board, side, depth);
        }
    }

    /**
     *
     * @param side The side for which the move is being calculated
     * @param board The board for which the move is being calculated
     * @param depth The amount of depth the function is allowed to go
     * @return The best move the function is able to calculate
     */
    private BestMove getBestMoveRecursive(Board board, Side side, int depth) {
        List<Move> legalMoves = board.getLegalMoves(side);
        BestMove bm = null;
        for(Move move: legalMoves) {
            Board newState = board.clone();
            newState.place(move);

            Side nextTurn = newState.getNextTurn(side);
            BestMove newBM;

            if(!newState.getLegalMoves(nextTurn).isEmpty()) {
                newBM = getBestMove(nextTurn, newState, depth - 1);

            }
            else {
                newBM = new BestMove(move, newState);
            }

            if (bm == null || newBM.endState.getScore(side) > bm.endState.getScore(side)) {
                bm = new BestMove(move, newBM.endState);
            }

        }
        return bm;
    }

    /**
     * Get move that results in the highest score for side, return null if no more moves can be made
     * @param board
     * @param side
     * @return best move
     */
    private BestMove getBestScoringMove(Board board, Side side) {
        List<Move> legalMoves = board.getLegalMoves(side);
        if(legalMoves.isEmpty()) return null;
        BestMove bm = null;
        for(Move move: legalMoves) {
            Board newState = board.clone();
            newState.place(move);

            int score = newState.getScore(side);
            if(bm==null || score > bm.endState.getScore(side)) {
                bm = new BestMove(move, newState);
            }
        }
        return bm;
    }

    public static class BestMove {
        public Board endState;
        public final Move move;

        public BestMove(Move move, Board endState) {
            this.move = move;
            this.endState = endState;

        }
    }
}

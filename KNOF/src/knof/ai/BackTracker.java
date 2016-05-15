package knof.ai;

import knof.gamelogic.Board;
import knof.gamelogic.Piece;
import knof.model.game.Side;

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
        List<Board.Pos> legalMoves = board.getValidPositions(side);
        BestMove bestMove = null;
        for(Board.Pos move: legalMoves) {
            Board clonedBoard = board.clone();
            clonedBoard.place(move.toInt(), side);

            Side nextSide = clonedBoard.getNextSide();
            BestMove newBestMove;

            if(!clonedBoard.getValidPositions(new Piece(nextSide)).isEmpty()) {
                newBestMove = getBestMove(nextSide, clonedBoard, depth - 1);

            }
            else {
                newBestMove = new BestMove(move, clonedBoard);
            }

            if (bestMove == null || newBestMove.endState.getScore(side) > bestMove.endState.getScore(side)) {
                bestMove = new BestMove(move, newBestMove.endState);
            }
        }
        return bestMove;
    }

    /**
     * Get move that results in the highest score for side, return null if no more moves can be made
     * @param board
     * @param side
     * @return best move
     */
    private BestMove getBestScoringMove(Board board, Side side) {
        List<Board.Pos> legalPositions = board.getValidPositions(new Piece(side));
        if(legalPositions.isEmpty()) return null;
        BestMove bestMove = null;

        for(Board.Pos position: legalPositions) {
            Board newState = board.clone();
            newState.place(position.toInt(), side);

            int score = newState.getScore(side);
            if(bestMove==null || score > bestMove.endState.getScore(side)) {
                bestMove = new BestMove(position, newState);
            }
        }
        return bestMove;
    }

    public static class BestMove {
        public Board endState;
        public final Board.Pos position;

        public BestMove(Board.Pos position, Board endState) {
            this.position = position;
            this.endState = endState;

        }
    }
}

package game.reversi;

import knof.gamelogic.Board;
import knof.model.game.Side;

import java.util.List;

/**
 * Created by Kevin on 5/16/2016.
 */
public class HeuristicThread extends Thread {

    private ReversiBoard board;
    private int depth;
    private Side side;
    private Side side1;
    private Side side2;
    private Board.Pos thisPos;
    public HeuristicBacktracker.Result best;



    public HeuristicThread(ReversiBoard board, int depth, Side side, Side side1, Side side2, Board.Pos thisPos) {
        this.board = board;
        this.depth = depth;
        this.side = side;
        this.thisPos = thisPos;
        this.best = new HeuristicBacktracker.Result(thisPos, board.getFieldValue(thisPos.x, thisPos.y));
    }



    public void run() {
        List<Board.Pos> validPositions = board.getValidPositions(side);
        HeuristicBacktracker.Result best = new HeuristicBacktracker.Result(thisPos, board.getFieldValue(thisPos.x, thisPos.y));
        if (depth < 1 ||validPositions == null || validPositions.isEmpty()) {
            this.best = best;
        } else {
            int base_v = Integer.MIN_VALUE;
            try {
                for (Board.Pos pos : validPositions) {
                    ReversiBoard newBoard = board.clone();
                    HeuristicThread thread = new HeuristicThread(newBoard, depth-1, otherSide(side), side1, side2, pos);
                    thread.start();
                    thread.join();
                    int v = - best.getValue();
                    if (v > base_v) {
                        this.best = best;
                        base_v = v;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Side otherSide(Side side) {
        if (side.equals(side1)) {
            return side2;
        } else {
            return side1;
        }
    }


}

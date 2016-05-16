package game.reversi;

import knof.connection.Connection;
import knof.gamelogic.Board;
import knof.model.game.AIPlayer;
import knof.model.game.Game;
import knof.model.game.Side;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * Created by Henk Dieter Oordt on 16-5-2016.
 */
public class ReversiCornerPlayer extends AIPlayer {
    public ReversiCornerPlayer(String name, Side side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    /**
     * Always returns the move that is closest to a corner
     * @return move
     */
    @Override
    protected int calculateMove() {
        ReversiGame reversiGame = (ReversiGame) game;
        ReversiBoard reversiBoard = reversiGame.getBoard();
        List<Board.Pos> validPositions = reversiBoard.getValidPositions(side);
        Double leftUpperDistance = new Double(Double.MIN_VALUE),
                rightUpperDistance = new Double(Double.MIN_VALUE),
                leftLowerDistance = new Double(Double.MIN_VALUE),
                rightLowerDistance = new Double(Double.MIN_VALUE);

        Board.Pos leftUpper = getPositionClosestToCorner(validPositions, reversiBoard.pos(0,0), leftUpperDistance);
        Board.Pos rightUpper = getPositionClosestToCorner(validPositions, reversiBoard.pos(0,reversiBoard.width-1), rightUpperDistance);
        Board.Pos leftLower = getPositionClosestToCorner(validPositions, reversiBoard.pos(reversiBoard.height-1,0), leftLowerDistance);
        Board.Pos rightLower = getPositionClosestToCorner(validPositions, reversiBoard.pos(reversiBoard.height-1,reversiBoard.width-1), rightLowerDistance);

        switch (minIndex(leftUpperDistance, rightUpperDistance, leftLowerDistance, rightLowerDistance)){
            case 0:
                return leftUpper.toInt();
            case 1:
                return rightUpper.toInt();
            case 2:
                return leftLower.toInt();
            default:
                return rightLower.toInt();
        }
    }

    private Board.Pos getPositionClosestToCorner(List<Board.Pos> posList, Board.Pos corner, Double minDist){
        if(posList.isEmpty()) {
            minDist = Double.MAX_VALUE;
            return null;
        }
        Board.Pos closestPos = posList.get(0);
        minDist = sqrt(abs(corner.x - closestPos.x) + abs(corner.y - closestPos.y));
        for(int i = 1; i < posList.size(); i++){
            Board.Pos currentPos = posList.get(i);
            double currentDist = sqrt(abs(corner.x - currentPos.x) + abs(corner.y - currentPos.y));
            if(minDist.doubleValue() > currentDist){
                minDist = currentDist;
                closestPos = currentPos;
            }
        }
        return closestPos;
    }

    private int minIndex(Double... val){
        Double min = Double.MAX_VALUE;
        int idx = -1;
        for(int i = 0; i < val.length; i++){
            if(val[i] < min){
                idx = i;
                min = val[i];
            }
        }
        return idx;
    }
}

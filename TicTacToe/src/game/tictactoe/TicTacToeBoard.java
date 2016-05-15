package game.tictactoe;


import knof.gamelogic.Board;
import knof.model.game.Player;
import knof.model.game.Side;

/**
 * Created by Thijs on 04/04/2016.
 */
public class TicTacToeBoard extends Board {

    public TicTacToeBoard(TicTacToeGame tttGame) {
        super(3, 3, tttGame);
    }

    @Override
    public Board clone() {
        return null;
    }

    @Override
    public int getScore(Side side) {

        Side opponentSide = getNextSide();

        if(isAWin(side)){
            return 1;
        }
        if(isAWin(opponentSide)){
            return -1;
        }

        return 0;
    }

    /**
     * Check if someone has won already
     * @param side Side to check
     * @return true if the side wins
     */
    public boolean isAWin(Side side){
        // Horizontal Win
        if(getPieceAtPosition(0).getSide().equals(side) && getPieceAtPosition(1).getSide().equals(side) && getPieceAtPosition(2).getSide().equals(side)){ return true; }
        else if (getPieceAtPosition(3).getSide().equals(side) && getPieceAtPosition(4).getSide().equals(side) && getPieceAtPosition(5).getSide().equals(side)){ return true; }
        else if (getPieceAtPosition(6).getSide().equals(side) && getPieceAtPosition(7).getSide().equals(side) && getPieceAtPosition(8).getSide().equals(side)){ return true; }

        // Vertical Win
        if(getPieceAtPosition(0).getSide().equals(side) && getPieceAtPosition(3).getSide().equals(side) && getPieceAtPosition(6).getSide().equals(side)){ return true; }
        else if (getPieceAtPosition(1).getSide().equals(side) && getPieceAtPosition(4).getSide().equals(side) && getPieceAtPosition(7).getSide().equals(side)){ return true; }
        else if (getPieceAtPosition(2).getSide().equals(side) && getPieceAtPosition(5).getSide().equals(side) && getPieceAtPosition(8).getSide().equals(side)){ return true; }

        // Diagonal win
        if(getPieceAtPosition(0).getSide().equals(side) && getPieceAtPosition(4).getSide().equals(side) && getPieceAtPosition(8).getSide().equals(side)){ return true; }
        else if (getPieceAtPosition(2).getSide().equals(side) && getPieceAtPosition(4).getSide().equals(side) && getPieceAtPosition(6).getSide().equals(side)){ return true; }

        return false;
    }

    @Override
    public Side getWinningSide(){
        int scoreSide1 = getScore(game.getSide1());
        int scoreSide2 = getScore(game.getSide2());
        if(scoreSide1 > scoreSide2){
            return game.getSide1();
        } else if (scoreSide1 < scoreSide2){
            return game.getSide2();
        } else {
            return null;
        }
    }
}

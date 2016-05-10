package game.tictactoe;

import gameframework.gamelogic.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thijs on 04/04/2016.
 */
public class TicTacToeBoard extends Board {

    static final int WIDTH = 3;
    static final int HEIGHT = 3;

    /**
     * Call the constructor of TicTacToeBoard and set setup to true
     */
    public TicTacToeBoard() {
        this(true);
    }

    /**
     * Create new TicTacToe board
     * @param setup boolean
     */
    public TicTacToeBoard(boolean setup) {
        super(WIDTH, HEIGHT);
        this.clear();
    }

    /**
     * Create new TicTacToe board according to array
     * @param board array of Sides
     */
    public TicTacToeBoard(Side[][] board) {
        super(WIDTH, HEIGHT);
        this.clear();
        this.board = new Side[WIDTH][HEIGHT];

        for(int x=0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.board[x][y] = board[x][y];
            }
        }
    }

    /**
     * Return all moves the specified side can make (empty fields)
     * @param side side to check moves for
     * @return a list of possible moves
     */
    public List<Move> getLegalMoves(Side side) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        if(isAWin(Side.PLAYERONE) || isAWin(Side.PLAYERTWO)) {
            return legalMoves;
        }

        // For each position in the board..
        for(int x=0; x < width; x++) {
            for(int y=0; y < height; y++) {
                Pos pos = new Pos(x, y);

                // .. check if empty and add to legal moves
                if(this.board[pos.x][pos.y] == Side.EMPTY) {
                    legalMoves.add(new Move(pos, side));
                }
            }
        }
        return legalMoves;
    }

    /**
     * Return score for TicTacToe
     * Side wins: score > 0
     * Side loses: score < 0
     * Tie/game not yet finished: score == 0
     * @param side
     * @return
     */
    @Override
    public int getScore(Side side) {
        Side otherSide = side==Side.PLAYERONE?Side.PLAYERTWO:Side.PLAYERONE;

        if(isAWin(side)){
            return 1;
        }
        if(isAWin(otherSide)){
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
        if(this.board[0][0] == side && this.board[0][1] == side && this.board[0][2] == side){ return true; }
        else if (this.board[1][0] == side && this.board[1][1] == side && this.board[1][2] == side){ return true; }
        else if (this.board[2][0] == side && this.board[2][1] == side && this.board[2][2] == side){ return true; }

        // Vertical Win
        if(this.board[0][0] == side && this.board[1][0] == side && this.board[2][0] == side){ return true; }
        else if (this.board[0][1] == side && this.board[1][1] == side && this.board[2][1] == side){ return true; }
        else if (this.board[0][2] == side && this.board[1][2] == side && this.board[2][2] == side){ return true; }

        // Diagonally win
        if(this.board[1][1] == side){
            if(this.board[0][0] == side && this.board[2][2] == side){ return true; }
            else if(this.board[2][0] == side && this.board[0][2] == side){ return true; }
        }

        return false;
    }

    @Override
    public Board clone() {
        return new TicTacToeBoard(this.board);
    }
}

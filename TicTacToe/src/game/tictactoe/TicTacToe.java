package game.tictactoe;

import gameframework.IMoveProvider;
import gameframework.ai.GenericBackTracker;
import gameframework.gamelogic.Move;
import gameframework.gamelogic.Pos;
import gameframework.gamelogic.Side;
import gameframework.moveproviders.RandomMoveProvider;
import gameframework.plugin.Plugin;
import gameframework.ui.Grid;
import gameframework.ui.Pieces;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thijs on 31/03/2016.
 */
public class TicTacToe extends Plugin {

    private TicTacToeBoard board;
    private Grid grid = new Grid();
    private Side side;
    private IMoveProvider provider;

    /**
     * Create a new Tic Tac Toe game
     * @param side Side object
     */
    public TicTacToe(Side side) {
        board = new TicTacToeBoard();

        // Setup grid
        grid.setSide(side);
        grid.pieceType = Pieces.XO;
        grid.backgroundColor = Color.BEIGE;
        grid.colorPlayerOne = Color.DARKRED;
        grid.colorPlayerTwo = Color.BLACK;
        grid.board = board;
        this.side = side;
        this.setMoveProvider(new RandomMoveProvider(this.board));
    }

    /**
     * Create a new Tic Tac Toe game for the first player
     */
    public TicTacToe() {
        this(Side.PLAYERONE);
    }

    @Override
    public boolean doMove(Move move) {
        return board.place(move);
    }

    @Override
    public Move getMove(Side side) {
        return provider.getMove(side);
    }

    @Override
    public List<Pos> getLegalMoves() {
        ArrayList<Pos> legalMoves = new ArrayList<>();
        for (Move move : board.getLegalMoves(this.side)) {
            legalMoves.add(move.position);
        }
        return legalMoves;
    }

    @Override
    public String boardToString() {
        return board.toString();
    }

    @Override
    public void setMoveProvider(IMoveProvider provider) {
        this.provider = provider;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public IMoveProvider makeAI() {
        return new GenericBackTracker(board, 10);
    }
}
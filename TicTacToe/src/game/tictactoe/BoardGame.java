package game.tictactoe;

import javafx.scene.canvas.Canvas;
import knof.gamelogic.Board;

public class BoardGame extends Canvas {
	
	private TicTacToeGame game;
	
	public void setGame(TicTacToeGame game) {
		this.game = game;
	}
	
	/**
	 * Gets the row the canvas X coordinate is in.
	 * @param x
	 * @return row
	 */
	private int getRow(Double x) {
		return (int) Math.floor(x.intValue() / getCellWidth());
	}
	
	/**
	 * Gets the column the canvas Y coordinate is in.
	 * @param y
	 * @return col
	 */
	private int getCol(Double y) {
		return (int) Math.floor(y.intValue() / getCellHeight());
	}   
	
    /**
     * Gets the X coordinate of board row on canvas.
     * @param row
     * @return x
     */
    private double getRowX(int row) {
    	return getCellWidth() * row;
    }
    
    /**
     * Gets the Y coordinate of board column on canvas.
     * @param col
     * @return y
     */
    private double getColY(int col) {
    	return getCellHeight() * col;
    }
    
    /**
     * Gets the width of a single cell.
     * @return width
     */
    private double getCellWidth() {
    	return getWidth() / game.board.width;
    }
    
    /**
     * gets the height of a single cell.
     * @return height
     */
    private double getCellHeight() {
    	return getHeight() / game.board.height;
    }
}

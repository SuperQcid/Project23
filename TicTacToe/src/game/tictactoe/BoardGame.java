package game.tictactoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import knof.gamelogic.Piece;
import knof.gamelogic.Board;
import knof.gamelogic.Board.Pos;

public class BoardGame extends Canvas {
	
	private TicTacToeGame game;
	public Color colorPlayerOne;
	public Color colorPlayerTwo;
	public Pieces pieceType;
    public double piecePadding = 4;
    public double pieceLineWidth = 10;
    public double gridLineWidth = 1;
	
	public void setGame(TicTacToeGame game) {
		this.game = game;
	}
	
    /**
     * Draws a piece in the given position based on the board.
     * @param move 
     */
	private void drawPiece(Pos pos, Piece piece) {
    	GraphicsContext gc = getGraphicsContext2D();
    	switch(piece.getSide()) {
			case "BLACK":
				gc.setFill(colorPlayerOne);
				gc.setStroke(colorPlayerOne);
				break;
			case "WHITE":
				gc.setFill(colorPlayerTwo);
				gc.setStroke(colorPlayerTwo);
				break;
			default:
				return;
    	}
    	switch(pieceType) {
			case CIRCLES:
				gc.fillOval(
					getRowX(pos.x) + piecePadding,
					getColY(pos.y) + piecePadding,
					getCellWidth() - 2 * piecePadding,
					getCellHeight() - 2 * piecePadding
				);
				break;
			case SQAURES:
				gc.fillRect(
					getRowX(pos.x) + piecePadding,
					getColY(pos.y) + piecePadding,
					getCellWidth() - 2 * piecePadding,
					getCellHeight() - 2 * piecePadding
				);
				break;
			case XO:
				gc.setLineWidth(pieceLineWidth);
				double padding = pieceLineWidth / 2 + piecePadding;
				switch(piece.getSide()) {
					case "BLACK":
						gc.strokeLine(
							getRowX(pos.x) + padding,
							getColY(pos.y) + padding,
							getRowX(pos.x) + getCellWidth() - padding, 
							getColY(pos.y) + getCellHeight() - padding
						);
						gc.strokeLine(
							getRowX(pos.x) + padding,
							getColY(pos.y) + getCellHeight() - padding,
							getRowX(pos.x) + getCellWidth() - padding,
							getColY(pos.y) + padding
						);
						break;
					case "WHITE":
						gc.strokeOval(
							getRowX(pos.x) + padding,
							getColY(pos.y) + padding,
							getCellWidth() - 2 * padding,
							getCellHeight() - 2 * padding
						);
						break;
					default:
						return;
				}
				break;
    	}
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
    
    public enum Pieces {
    	SQAURES, CIRCLES, XO
    }
}

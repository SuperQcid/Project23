package game.tictactoe;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import knof.gamelogic.Piece;
import knof.model.game.HumanPlayer;
import knof.model.game.Player;
import knof.gamelogic.Board.Pos;

public class BoardGame extends Canvas {
	
	public TicTacToeGame game;
   	private GraphicsContext gc = getGraphicsContext2D();
	public Color colorPlayerOne;
	public Color colorPlayerTwo;
	public Color backgroundColor;
	public Pieces pieceType;
    public double piecePadding = 4;
    public double pieceLineWidth = 10;
    public double gridLineWidth = 1;
    
    public BoardGame() {
    	Player localPlayer = game.getLocalPlayer();
    	if(localPlayer instanceof HumanPlayer) {
    		this.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent cursor) {
						Pos pos = getPos(cursor.getX(), cursor.getY());
						((HumanPlayer) localPlayer).receiveMove(pos.toInt());
					}
				}
			);
    	}
    	drawBoard();
    }
	
    /*
	public void setGame(TicTacToeGame game) {
		this.game = game;
		game.addListener(
			new InvalidationListener() {
				@Override
				public void invalidated(Observable arg0) {
					drawBoard();
				}
			}
		);
	}
	*/
	
	/**
	 * Clears and draws the board.
	 */
	public void drawBoard() {
		clear();
    	gc.setFill(backgroundColor);
    	gc.setStroke(Color.BLACK);
    	gc.fillRect(0, 0, getWidth(), getHeight());
    	gc.setLineWidth(gridLineWidth);
    	// Horizontal lines
    	for(int x = 0; x <= game.board.width; x++) {
    		double rowX = getRowX(x);
    		gc.strokeLine(rowX, 0, rowX, getHeight());
    	}
    	// Vertical lines
    	for(int y = 0; y <= game.board.height; y++) {
    		double colY = getColY(y);
    		gc.strokeLine(0, colY, getWidth(), colY);    		
    	}
    	// Pieces
    	for(int y = 0; y < game.board.height; y++) {
    		for(int x = 0; x < game.board.width; x++) {
    			Pos pos = game.board.new Pos(x, y);
    			Piece piece = game.board.getPieceAtPosition(pos.toInt());
    			drawPiece(pos, piece);
    		}
    	}
	}
	
    /**
     * Draws a piece in the given position based on the board.
     * @param move 
     */
	private void drawPiece(Pos pos, Piece piece) {
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
	 * Gets the position in the grid from screen coordinates
	 * @param x x-coord
	 * @param y y-coord
	 * @return board position
	 */
	private Pos getPos(double x, double y) {
		return game.board.new Pos(getRow(x), getCol(y));
	}
	
    /**
     * Clears the canvas.
     */
    private void clear() {
    	gc.clearRect(0, 0, getWidth(), getHeight());
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

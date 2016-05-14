package game.tictactoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import knof.gamelogic.Board;
import knof.gamelogic.Piece;
import knof.model.game.HumanPlayer;
import knof.model.game.Player;
import knof.gamelogic.Board.Pos;

import java.util.HashMap;

public class BoardGame extends Canvas {

	private HashMap<String, PieceRenderer> pieceRenderers = new HashMap<>();
	
	public Board board;
   	private GraphicsContext gc = getGraphicsContext2D();
	public Color colorPlayerOne;
	public Color colorPlayerTwo;
	public Color backgroundColor;
    public double piecePadding = 4;
    public double pieceLineWidth = 10;
    public double gridLineWidth = 1;

    public BoardGame() {
		pieceRenderers.put("X", new PieceRenderer(Color.RED, pieceLineWidth, piecePadding){

			@Override
			public void renderShape(GraphicsContext gc, BoardGame boardGame, Pos pos) {
				double padding = this.lineWidth / 2 + this.piecePadding;
				gc.strokeLine(
						getRowX(pos.x) + padding,
						getColY(pos.y) + padding,
						getRowX(pos.x) + boardGame.getCellWidth() - padding,
						getColY(pos.y) + boardGame.getCellHeight() - padding
				);
				gc.strokeLine(
						getRowX(pos.x) + padding,
						getColY(pos.y) + boardGame.getCellHeight() - padding,
						getRowX(pos.x) + boardGame.getCellWidth() - padding,
						getColY(pos.y) + padding
				);
			}
		});

		pieceRenderers.put("O", new PieceRenderer(Color.BLUE, pieceLineWidth, piecePadding){

			@Override
			public void renderShape(GraphicsContext gc, BoardGame boardGame, Pos pos) {
				double padding = this.lineWidth / 2 + this.piecePadding;
				gc.strokeOval(
						getRowX(pos.x) + padding,
						getColY(pos.y) + padding,
						getCellWidth() - 2 * padding,
						getCellHeight() - 2 * padding
				);
			}
		});
    }

	public void setGame(TicTacToeGame game) {
		this.board = game.board;
		Player localPlayer = game.getLocalPlayer();
		if(localPlayer instanceof HumanPlayer) {
			this.addEventHandler(MouseEvent.MOUSE_CLICKED,
					cursor -> {
						System.out.println(localPlayer.getName() + " clicked the board at " + cursor.getX() + "," + cursor.getY());
						Pos pos = getPos(cursor.getX(), cursor.getY());
						((HumanPlayer) localPlayer).receiveMove(pos.toInt());
					}
			);
		}
		drawBoard();
	}

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
    	for(int x = 0; x <= board.width; x++) {
    		double rowX = getRowX(x);
    		gc.strokeLine(rowX, 0, rowX, getHeight());
    	}
    	// Vertical lines
    	for(int y = 0; y <= board.height; y++) {
    		double colY = getColY(y);
    		gc.strokeLine(0, colY, getWidth(), colY);    		
    	}

    	// Pieces
    	for(int y = 0; y < board.height; y++) {
    		for(int x = 0; x < board.width; x++) {
    			Pos pos = board.new Pos(x, y);
    			Piece piece = board.getPieceAtPosition(pos.toInt());
				if (piece == null) {
					continue;
				}
    			drawPiece(pos, piece);
    		}
    	}
	}
	
    /**
     * Draws a piece in the given position based on the board.
     * @param
     */
	private void drawPiece(Pos pos, Piece piece) {
		PieceRenderer renderer = pieceRenderers.get(piece.getSide().getName());
		renderer.render(gc, this, pos);
	}
	
	/**
	 * Gets the position in the grid from screen coordinates
	 * @param x x-coord
	 * @param y y-coord
	 * @return board position
	 */
	private Pos getPos(double x, double y) {
		return board.pos(getRow(x), getCol(y));
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
    	return getWidth() / board.width;
    }
    
    /**
     * gets the height of a single cell.
     * @return height
     */
    private double getCellHeight() {
    	return getHeight() / board.height;
    }
}

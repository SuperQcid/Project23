package knof.gamelogic;

import knof.model.game.Game;
import knof.model.game.Player;
import knof.model.game.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the board.
 * The board is an array of size 'width' times 'height'.
 * Pieces on the board are stored as Piece.
 */
public abstract class Board {
	public final int width;
	public final int height;
	protected Piece[] board;
	protected Side previousSide;
	protected Game game;

	public Board(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		this.board = new Piece[width*height];
		this.game = game;
		this.previousSide = game.getSide1();
	}

	public Piece getPieceAtPosition(int position){
		if(board != null) {
			return this.board[position];
		}
		return null;
	}

	/**
	 * Get if the given index is a valid position on the board
	 * @param index index to check validity for
	 * @return if the index is valid
	 */
	public boolean validIndex(int index) {
		return (index>=0 && index<board.length);
	}

	/**
	 * Check if the cell at the given index is empty
	 * @param index index to check
	 * @return whether the cell at the index is empty
	 */
	public boolean isEmpty(int index) {
		return board[index] == null;
	}

	public abstract Board clone();

	public abstract int getScore(Side side);

	public abstract Side getWinningSide();

	public Player getWinningPlayer(){
		return game.getSidePlayer(getWinningSide());
	}

	public Side getNextSide(){
		return previousSide==game.getSide1()?game.getSide2():game.getSide1();
	}

	/**
	 * Check if a given piece can be placed on a certain index.
	 * You may override this method with an optional super call when implementing
	 * @param index position to check for
	 * @param piece piece to check for
	 * @return whether a piece can be placed there
	 */
	public boolean isValid(int index, Piece piece) {
		return validIndex(index) && isEmpty(index);
	}

	public List<Pos> getValidPositions(Piece piece) {
		ArrayList<Pos> positions = new ArrayList<>(board.length/2);
		for (int idx=0; idx<board.length; idx++) {
			if(this.isValid(idx, piece)) {
				positions.add(new Pos(idx));
			}
		}
		return positions;
	}

	public List<Pos> getValidPositions() {
		ArrayList<Pos> positions = new ArrayList<>(board.length/2);
		for (int idx=0; idx<board.length; idx++) {
			if(this.isValid(idx, null)) {
				positions.add(new Pos(idx));
			}
		}
		return positions;
	}


	/**
	 * Place a piece at a certain index if allowed
	 * You should override this method for custom place behaviour
	 * @param index index to place on
	 * @param piece piece to place
	 * @return success value
	 */
	public boolean place(int index, Piece piece) {
		if(isValid(index, piece)) {
			previousSide = piece.getSide();
			this.board[index] = piece;
			return true;
		}
		return false;
	}

	/**
	 * Get piece at index
	 * @param index
	 * @return piece
	 */
	public Piece getPiece(int index) {
		return board[index];
	}

	public Pos pos(int i) {
		return new Pos(i);
	}

	public Pos pos(int x, int y) {
		return new Pos(x, y);
	}

	public boolean validPos(Pos p) {
		return this.validIndex(p.toInt());
	}

	/**
	 * Position object containing an x and y coordinate representing a space on the board.
	 */
	public class Pos {

		public int x;
		public int y;

		/**
		 * Create pos from integer
		 * @param i
		 */
		public Pos(int i) {
			this.x = i % width;
			this.y = i / width;
		}

		/**
		 * Make a new position object
		 * @param x
		 * @param y
		 */
		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Move the pos one coordinate in a Direction.
		 * @param dir
		 * @return pos
		 */
		public Pos add(Direction dir) {
			return new Pos(this.x+dir.dx, this.y+dir.dy);
		}

		public String toString() {
			return "[" + x + ", " + y + "]";
		}

		/**
		 * Check if an object is an instance of Pos
		 * @return true if the provided object is equal
		 */
		public boolean equals(Object other) {
			if(other instanceof Pos) {
				return ((Pos) other).x == this.x && ((Pos) other).y == this.y;
			}
			return false;
		}

		/**
		 * Get the hashcode of a position
		 * @return a hashcode of the position
		 */
		public int hashCode() {
			return this.x << 16 + this.y;
		}

		/**
		 * Convert this to an int
		 * @return int
		 */
		public int toInt() {
			return width * y + x;
		}
	}
}
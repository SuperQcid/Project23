package knof.gamelogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the board.
 * The board is a multidimensional array of size 'width' and 'height'.
 * Pieces on the board are stored as Side.
 */
public abstract class Board {

	protected Side[][] board;
	public final int height;
	public final int width;
	
	public Board(int width, int height) {
		board = new Side[width][height];
		this.height = height;
		this.width = width;
		clear();
	}
	
	/**
	 * Converts the board to a string.
	 * @return a string representation of the object.
	 */
	public String toString() {
		String string = "";
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				string += get(new Pos(x, y)).character; 
			}
			string += '\n';
		}
		return string;
	}

	/**
	 * Places a piece on the board.
	 * @param move
	 * @return success
	 */
	public boolean place(Move move) {
		return place(move.side, move.position);
	}
	
	/**
	 * Places a piece on the board. 
	 * @param side
	 * @param pos
	 * @return success
	 */
	public boolean place(Side side, Pos pos) {
		if(valid(pos) && board[pos.x][pos.y] == Side.EMPTY) {
			board[pos.x][pos.y] = side;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Force places a piece on the board.
	 * Overwrites other pieces that might be in this spot.
	 * @param side
	 * @param pos
	 * @return boolean
	 */
	public boolean placeForce(Side side, Pos pos) {
		if(valid(pos)) {
			board[pos.x][pos.y] = side;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Clears board at (x,y).
	 * @param pos
	 * @return boolean
	 */
	public boolean clear(Pos pos) {
		if(valid(pos)) {
			board[pos.x][pos.y] = Side.EMPTY;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Clears the whole board.
	 */
	public void clear() {
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				board[x][y] = Side.EMPTY;
			}
		}
	}
	
	/**
	 * Returns the piece at (x,y).
	 * @param pos
	 * @return Side
	 */
	public Side get(Pos pos) {
		if(valid(pos)) return board[pos.x][pos.y];
		else return null;
	}
	
	/**
	 * Returns the board.
	 * @return Side[][]
	 */
	public Side[][] get() {
		return board;
	}
	
	/**
	 * Gets an adjacent Position of a Position on the board.
	 * Returns null if Position is invalid.
	 * @param direction
	 * @param pos
	 * @return side
	 */
	public Side getAdjacent(Direction direction, Pos pos) {
		pos.x += direction.dx;
		pos.y += direction.dy;
		if(valid(pos)) return board[pos.x][pos.y];
		else return null;
	}
	
	/**
	 * Checks if board position is valid.
	 * @param pos
	 * @return boolean
	 */
	public boolean valid(Pos pos) {
		if(pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height) return true;
		else return false;
	}

	/**
	 * Return legal moves for board.
	 * Can be used for highlighting legal move in the interface or for making a generic AI.
	 * @param side to check moves for
	 * @return list of legal moves
	 */
	public abstract List<Move> getLegalMoves(Side side);
	
	/**
	 * Return legal moves for the board in positions.
	 * Kind of like a type changed version of getLegalMoves().
	 * @param side side to check moves for
	 * @return list of legal positions
	 */
	public List<Pos> getLegalPositions(Side side) {
		List<Move> legalMoves = getLegalMoves(side);
		List<Pos> legalPositions = new ArrayList<>();
		for(Move move : legalMoves) {
			legalPositions.add(move.position);
		}
		return legalPositions;
	}

	/**
	 * Returns a score that can be used by AIs.
	 * Example: TicTacToe backtracking
	 *     TIE: 0
	 *     WIN: MAXINT
	 *     LOSS: MININT
	 *     Make moves until getLegalMoves() returns empty list, score is final score
	 * @param side
	 * @return score
	 */
	public abstract int getScore(Side side);

	/**
	 * Clone for AI
	 * @return clone of board
	 */
	public abstract Board clone();

	/**
	 * Get the next turn.
	 * @param lastTurn
	 * @return next turn
	 */
	public Side getNextTurn(Side lastTurn) {
		return lastTurn==Side.PLAYERONE?Side.PLAYERTWO:Side.PLAYERONE;
	}
}
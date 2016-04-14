package knof.gamelogic;

/**
 * A Move object containing a Position and the Side which owns the move.
 */
public class Move {
	public final Pos position;
	public final Side side;

	/**
	 * Make new move with x and y coordinate
	 * @param x
	 * @param y
	 * @param side
     */
	public Move(int x, int y, Side side){
		this.position = new Pos(x, y);
		this.side = side;
	}

	/**
	 * Make new Move with a position object
	 * @param position
	 * @param side
     */
	public Move(Pos position, Side side) {
		this.position = position;
		this.side = side;
	}
	
	/**
	 * Get a string representation of the position
	 * @return a string representation of the position
	 */
	public String toString() {
		return "["+position.x+", "+position.y+"]";
	}

	/**
	 * Check if an object is an instance of Move
	 * @return true if the provided object is equal
	 */
	public boolean equals(Object other) {
		if(other instanceof Move) {
			return ((Move) other).side == this.side && ((Move) other).position.equals(this.position);
		}
		return false;
	}

	/**
	 * Returns a hashCode of the position
	 * @return int hashcode of the position
	 */
	public int hashCode() {
		int posHash = position.hashCode();
		int xh = (posHash & 0x0FFF0000) << 4;
		int yh = (posHash & 0x00000FFF) << 8;
		int sh = this.side.id;
		return xh | yh | sh;
	}


}

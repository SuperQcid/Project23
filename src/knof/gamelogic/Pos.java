package knof.gamelogic;

/**
 * Position object containing an x and y coordinate representing a space on the board.
 */
public class Pos {
	
	public int x;
	public int y;

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
}

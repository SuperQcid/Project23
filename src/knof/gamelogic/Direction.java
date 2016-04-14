package knof.gamelogic;

/**
 * Enum containing definitions for directions.
 */
public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NORTHEAST(1, -1),
    NORTHWEST(-1, -1),
    SOUTHEAST(1, 1),
    SOUTHWEST(-1, 1);

    public final int dy;
    public final int dx;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

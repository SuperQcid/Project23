package knof.gamelogic;

/**
 * Enum containing predefined names for chars on a Board.
 */
public enum Side {
    EMPTY(0, '.'),
    PLAYERONE(1, '1'),
    PLAYERTWO(2, '2'),
    UNKNOWN(3, '?');

    public final int id;
    public final char character;

    /**
     * Make a new side
     * @param id
     * @param character
     */
    Side(int id, char character) {
        this.id = id;
        this.character = character;
    }
}
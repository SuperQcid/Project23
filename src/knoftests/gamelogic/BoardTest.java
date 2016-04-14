package knoftests.gamelogic;

import org.junit.Test;
import knof.gamelogic.Board;
import knof.gamelogic.Direction;
import knof.gamelogic.Move;
import knof.gamelogic.Pos;
import knof.gamelogic.Side;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board board = new Board(2, 2) {
        @Override
        public List<Move> getLegalMoves(Side side) {
            return new ArrayList<>();
        }

        @Override
        public int getScore(Side side) {
            return 0;
        }

        @Override
        public Board clone() {
            return null;
        }
    };

    @Test
    public void validTest() {
        assertEquals(board.valid(new Pos(-1, -1)), false);
        assertEquals(board.valid(new Pos(0, 0)), true);
    }

    @Test
    public void placeTest() {
        assertEquals(board.place(Side.PLAYERONE, new Pos(3, 3)), false);
        assertEquals(board.place(Side.PLAYERONE, new Pos(1, 1)), true);
        assertEquals(board.place(Side.PLAYERTWO, new Pos(1, 1)), false);
    }

    @Test
    public void placeForceTest() {
        Pos pos = new Pos(1, 1);
        assertEquals(board.place(Side.PLAYERONE, pos), true);
        assertEquals(board.placeForce(Side.PLAYERTWO, pos), true);
    }

    @Test
    public void getTest() {
        Pos pos = new Pos(1, 1);
        board.place(Side.PLAYERTWO, pos);
        assertEquals(board.get(pos), Side.PLAYERTWO);
        Side[][] secondBoard = new Side[2][2];
        secondBoard[0][0] = Side.EMPTY;
        secondBoard[0][1] = Side.EMPTY;
        secondBoard[1][0] = Side.EMPTY;
        secondBoard[1][1] = Side.PLAYERTWO;
        assertArrayEquals(board.get(), secondBoard);
    }

    @Test
    public void clearTest() {
        Pos pos = new Pos(1, 1);
        board.place(Side.PLAYERTWO, pos);
        assertEquals(board.clear(pos), true);
        assertEquals(board.get(pos), Side.EMPTY);
        board.place(Side.PLAYERTWO, pos);
        Side[][] secondBoard = new Side[2][2];
        secondBoard[0][0] = Side.EMPTY;
        secondBoard[0][1] = Side.EMPTY;
        secondBoard[1][0] = Side.EMPTY;
        secondBoard[1][1] = Side.EMPTY;
        board.clear();
        assertArrayEquals(board.get(), secondBoard);
    }

    @Test
    public void getAdjacentTest() {
        board.place(Side.PLAYERTWO, new Pos(1, 1));
        assertEquals(board.getAdjacent(Direction.SOUTHEAST, new Pos(0, 0)), Side.PLAYERTWO);
    }

    @Test
    public void toStringTest() {
        board.place(Side.PLAYERTWO, new Pos(0, 0));
        board.place(Side.PLAYERONE, new Pos(1, 0));
        board.place(Side.UNKNOWN, new Pos(1, 1));
        assertEquals(board.toString(), "21\n.?\n");
    }
}
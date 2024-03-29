package knoftests.gamelogic;

import junit.framework.TestCase;
import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.Board;
import knof.model.game.Game;
import knof.model.game.Side;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class BoardTest  extends TestCase {

    /**
     * Tests that a clone of a board equals the original one, but isn't the same instance
     */
    @Test
    public void testClone(){
        PrintWriter output = new PrintWriter(System.out);
        BufferedReader reader = new BufferedReader(new StringReader(""));
        Board board = new TestBoard(10, 10, new Game(new Connection(reader, output)) {
            @Override
            protected boolean move(int move, Side side) {
                return false;
            }

            @Override
            protected GameController initGameController() {
                return null;
            }

            @Override
            public Side getSide1() {
                return null;
            }

            @Override
            public Side getSide2() {
                return null;
            }
        }) {
            @Override
            public int getScore(Side side) {
                return 0;
            }

            @Override
            public Side getWinningSide() {
                return null;
            }
        };

        Board clone = board.clone();
        assertFalse(clone == board);
        assertTrue(clone.equals(board));
    }

    private class TestBoard extends Board implements Serializable {

        public TestBoard(int width, int height, Game game) {
            super(width, height, game);
        }

        /**
         * Copy constructor
         * @param board
         */
        public TestBoard(TestBoard board){
            super(board);
        }

        @Override
        public Board clone() {
            return new TestBoard(this);
        }

        @Override
        public int getScore(Side side) {
            return 0;
        }

        @Override
        public Side getWinningSide() {
            return null;
        }

        public boolean equals(Object o){
            if (o instanceof TestBoard){
                TestBoard other = (TestBoard) o;
                if(Arrays.equals(other.board, this.board)){
                    if (other.height == this.height)
                        if (other.width == this.width)
                            if (other.previousSide != null) {
                                if (this.previousSide != null) {
                                    return other.previousSide.equals(this.previousSide);
                                }
                            } else {
                                return this.previousSide == null;
                            }
                }
            }
            return false;
        }
    }

//    private Board board = new Board(2, 2) {
//        @Override
//        public List<Move> getLegalMoves(Side side) {
//            return new ArrayList<>();
//        }
//
//        @Override
//        public int getScore(Side side) {
//            return 0;
//        }
//
//        @Override
//        public Board clone() {
//            return null;
//        }
//    };
//
//    @Test
//    public void validTest() {
//        assertEquals(board.valid(new Pos(-1, -1)), false);
//        assertEquals(board.valid(new Pos(0, 0)), true);
//    }
//
//    @Test
//    public void placeTest() {
//        assertEquals(board.place(Side.PLAYERONE, new Pos(3, 3)), false);
//        assertEquals(board.place(Side.PLAYERONE, new Pos(1, 1)), true);
//        assertEquals(board.place(Side.PLAYERTWO, new Pos(1, 1)), false);
//    }
//
//    @Test
//    public void placeForceTest() {
//        Pos pos = new Pos(1, 1);
//        assertEquals(board.place(Side.PLAYERONE, pos), true);
//        assertEquals(board.placeForce(Side.PLAYERTWO, pos), true);
//    }
//
//    @Test
//    public void getTest() {
//        Pos pos = new Pos(1, 1);
//        board.place(Side.PLAYERTWO, pos);
//        assertEquals(board.get(pos), Side.PLAYERTWO);
//        Side[][] secondBoard = new Side[2][2];
//        secondBoard[0][0] = Side.EMPTY;
//        secondBoard[0][1] = Side.EMPTY;
//        secondBoard[1][0] = Side.EMPTY;
//        secondBoard[1][1] = Side.PLAYERTWO;
//        assertArrayEquals(board.get(), secondBoard);
//    }
//
//    @Test
//    public void clearTest() {
//        Pos pos = new Pos(1, 1);
//        board.place(Side.PLAYERTWO, pos);
//        assertEquals(board.clear(pos), true);
//        assertEquals(board.get(pos), Side.EMPTY);
//        board.place(Side.PLAYERTWO, pos);
//        Side[][] secondBoard = new Side[2][2];
//        secondBoard[0][0] = Side.EMPTY;
//        secondBoard[0][1] = Side.EMPTY;
//        secondBoard[1][0] = Side.EMPTY;
//        secondBoard[1][1] = Side.EMPTY;
//        board.clear();
//        assertArrayEquals(board.get(), secondBoard);
//    }
//
//    @Test
//    public void getAdjacentTest() {
//        board.place(Side.PLAYERTWO, new Pos(1, 1));
//        assertEquals(board.getAdjacent(Direction.SOUTHEAST, new Pos(0, 0)), Side.PLAYERTWO);
//    }
//
//    @Test
//    public void toStringTest() {
//        board.place(Side.PLAYERTWO, new Pos(0, 0));
//        board.place(Side.PLAYERONE, new Pos(1, 0));
//        board.place(Side.UNKNOWN, new Pos(1, 1));
//        assertEquals(board.toString(), "21\n.?\n");
//    }
}
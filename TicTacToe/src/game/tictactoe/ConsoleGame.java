package game.tictactoe;

import gameframework.IMoveProvider;
import gameframework.gamelogic.Move;
import gameframework.gamelogic.Pos;
import gameframework.gamelogic.Side;

import java.util.Scanner;

/**
 * Created by Thijs on 12/04/2016.
 */
public class ConsoleGame implements IMoveProvider{
    TicTacToe game;
    TicTacToe otherGame;

    /**
     * Create a new console game and start the game
     * @param args
     */
    public static void main(String[] args) {
        ConsoleGame consoleGame = new ConsoleGame();
        consoleGame.start();
    }

    /**
     * Create a new console game
     */
    public ConsoleGame() {
        this.game = new TicTacToe(Side.PLAYERONE);
        this.game.setMoveProvider(this);
        this.otherGame = new TicTacToe(Side.PLAYERTWO);
    }

    /**
     * Start the console game and run console logic
     */
    public void start() {
        while (true) {
            System.out.println(otherGame.boardToString());
            System.out.println("---- Valid moves ----");
            for(Pos pos: game.getLegalMoves()) {
                System.out.println(pos);
            }
            Move move = this.game.getMove(Side.PLAYERONE);
            if(game.getLegalMoves().contains(move.position)) {

                game.doMove(move);
                otherGame.doMove(move);

                System.out.println(game.boardToString());

                if(!game.getLegalMoves().isEmpty()) {
                    Move otherMove = otherGame.getMove(Side.PLAYERTWO);

                    System.out.println("Move: " + otherMove);
                    game.doMove(otherMove);
                    otherGame.doMove(otherMove);
                }
            }
            else {
                System.out.println("Invalid move, try again!");
            }
        }
    }

    @Override
    public Move getMove(Side side) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int y = in.nextInt();
        return new Move(x, y, side);
    }
}
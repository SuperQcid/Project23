package game.tictactoe;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.GridGame;
import knof.model.game.Side;

/**
 * Created by Thijs on 10/05/2016.
 */
public class TicTacToeGame extends GridGame<TicTacToeBoard> {

    private final static Side X = new Side("X");
    private final static Side O = new Side("O");

    public TicTacToeGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        super(playerOneName, playerTwoName, playerOneIsLocal, connection);
    }

    @Override
    public TicTacToeBoard createBoard() {
        return new TicTacToeBoard(this);
    }

    @Override
    protected GameController initGameController() {
    	TicTacToeGameController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader();
            ClassLoader cl = TicTacToe.class.getClassLoader();
            loader.setClassLoader(cl);
			Parent loaded = loader.load(getClass().getResource("TicTacToeGameController.fxml").openStream());
			controller = loader.getController();
            controller.setGame(this);
			Stage stage = new Stage();
			stage.setScene(new Scene(loaded));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return controller;
    }

    @Override
    protected Side getSide1() {
        return TicTacToeGame.X;
    }

    @Override
    protected Side getSide2() {
        return TicTacToeGame.O;
    }

}

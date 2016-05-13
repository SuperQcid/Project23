package game.tictactoe;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.Piece;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.LocalPlayer;

/**
 * Created by Thijs on 10/05/2016.
 */
public class TicTacToeGame extends Game {

    TicTacToeBoard board;

    public TicTacToeGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        super(playerOneName, playerTwoName, playerOneIsLocal, connection);
        board = new TicTacToeBoard(this);
    }

    @Override
    protected LocalPlayer initLocalPlayer(String playerName, String side, Connection connection) {
        return null;
    }

    @Override
    protected DummyPlayer initRemotePlayer(String playerName, String side, Connection connection) {
        return null;
    }

    @Override
    protected boolean move(int move, String side) {
        return board.place(move, new Piece(this.getSidePlayer(side)));
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
}

package game.tictactoe;

import game.tictactoe.BoardGame.Pieces;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import knof.controllers.GameController;
import knof.model.game.Game;
import knof.model.game.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class TicTacToeGameController extends GameController implements Initializable{

	@FXML
	public BoardGame boardGame;

	@Override
	public void update(Game game) {
		boardGame.game = (TicTacToeGame) game;
		boardGame.drawBoard();
	}

	public void setGame(TicTacToeGame game) {
		boardGame.setGame(game);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boardGame.backgroundColor = Color.GREY;
		boardGame.colorPlayerOne = Color.RED;
		boardGame.colorPlayerTwo = Color.BLUE;
		boardGame.pieceType = Pieces.XO;
	}
}

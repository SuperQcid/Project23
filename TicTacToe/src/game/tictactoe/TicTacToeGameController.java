package game.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import knof.controllers.GameController;
import knof.model.game.Game;

public class TicTacToeGameController extends GameController{

	@FXML
	public BoardGame boardGame;

	@Override
	public void update(Game game) {
		boardGame.board = ((TicTacToeGame) game).board;
		boardGame.drawBoard();
	}

	public void setGame(TicTacToeGame game) {
		boardGame.setGame(game);
	}

	@FXML
	public void initialize() {
		boardGame.backgroundColor = Color.GREY;
		boardGame.colorPlayerOne = Color.RED;
		boardGame.colorPlayerTwo = Color.BLUE;
	}
}

package game.tictactoe;

import javafx.fxml.FXML;
import knof.controllers.GameController;
import knof.model.game.Game;

public class TicTacToeGameController extends GameController {

	@FXML
	public BoardGame boardGame;

	@Override
	public void update(Game game) {
		boardGame.game = (TicTacToeGame) game;
		boardGame.drawBoard();
	}
	
}

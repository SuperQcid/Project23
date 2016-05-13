package game.tictactoe;

import game.tictactoe.BoardGame.Pieces;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import knof.controllers.GameController;
import knof.model.game.Game;
import knof.model.game.Player;

public class TicTacToeGameController extends GameController {

	@FXML
	public BoardGame boardGame;
	
	public void init() {
		boardGame.backgroundColor = Color.GREY;
		boardGame.colorPlayerOne = Color.RED;
		boardGame.colorPlayerTwo = Color.BLUE;
		boardGame.pieceType = Pieces.XO;
	}

	@Override
	public void update(Game game) {
		boardGame.game = (TicTacToeGame) game;
		boardGame.drawBoard();
	}

	public void setGame(TicTacToeGame game) {
		boardGame.setGame(game);
	}
}

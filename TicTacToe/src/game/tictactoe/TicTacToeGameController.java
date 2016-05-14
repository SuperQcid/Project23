package game.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import knof.controllers.GameController;
import knof.gamelogic.Board;
import knof.gamelogic.controllers.BoardGame;
import knof.gamelogic.controllers.PieceRenderer;
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

		double piecePadding = 4;
		double pieceLineWidth = 10;

		boardGame.addPieceRenderer("X", new PieceRenderer(Color.RED, pieceLineWidth, piecePadding) {

			@Override
			public void renderShape(GraphicsContext gc, BoardGame boardGame, Board.Pos pos) {
				double padding = this.lineWidth / 2 + this.piecePadding;
				gc.strokeLine(
						boardGame.getRowX(pos.x) + padding,
						boardGame.getColY(pos.y) + padding,
						boardGame.getRowX(pos.x) + boardGame.getCellWidth() - padding,
						boardGame.getColY(pos.y) + boardGame.getCellHeight() - padding
				);
				gc.strokeLine(
						boardGame.getRowX(pos.x) + padding,
						boardGame.getColY(pos.y) + boardGame.getCellHeight() - padding,
						boardGame.getRowX(pos.x) + boardGame.getCellWidth() - padding,
						boardGame.getColY(pos.y) + padding
				);
			}
		});


		boardGame.addPieceRenderer("O", new PieceRenderer(Color.BLUE, pieceLineWidth, piecePadding) {

			@Override
			public void renderShape(GraphicsContext gc, BoardGame boardGame, Board.Pos pos) {
				double padding = this.lineWidth / 2 + this.piecePadding;
				gc.strokeOval(
						boardGame.getRowX(pos.x) + padding,
						boardGame.getColY(pos.y) + padding,
						boardGame.getCellWidth() - 2 * padding,
						boardGame.getCellHeight() - 2 * padding
				);
			}
		});
	}
}

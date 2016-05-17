package game.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import knof.controllers.BoardGameController;
import knof.controllers.GameController;
import knof.event.events.GameResultEvent;
import knof.gamelogic.Board;
import knof.gamelogic.GridGame;
import knof.gamelogic.controllers.BoardGame;
import knof.gamelogic.controllers.PieceMiniatureCanvas;
import knof.gamelogic.controllers.PieceMiniatureRenderer;
import knof.gamelogic.controllers.PieceRenderer;
import knof.model.game.Game;
import knof.model.game.Side;

public class TicTacToeGameController extends BoardGameController {
	@FXML
	public StackPane pane;

	@FXML
	public BoardGame boardGame;

	@FXML
	public VBox resultOverlay;

	@FXML
	public Label resultMessage, p1Score, p2Score;

	@Override
	public void update() {
		boardGame.drawBoard();
	}

	@Override
	public void close() {
		pane.getScene().getWindow().hide();
	}

	public void setGame(Game game) {
		super.setGame(game);
		boardGame.setGame((GridGame) game);
	}

	@Override
	public void showResult() {
		GameResultEvent result = this.game.result.get();
		if(result==null) return;

		Side side1 = this.game.getSide1();
		Side side2 = this.game.getSide2();

		String p1Name = this.game.getSidePlayer(side1).getName();
		String p2Name = this.game.getSidePlayer(side2).getName();

		resultMessage.setText(result.getMessage());
		p1Score.setText(String.format("%s (%s): %d", p1Name, side1, result.playerOneScore));
		p2Score.setText(String.format("%s (%s): %d", p2Name, side2, result.playerTwoScore));
		resultOverlay.setVisible(true);
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

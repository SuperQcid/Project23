package game.reversi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import knof.controllers.BoardGameController;
import knof.event.events.GameResultEvent;
import knof.gamelogic.GridGame;
import knof.gamelogic.controllers.BoardGame;
import knof.model.game.Game;
import knof.model.game.Side;

public class ReversiGameController extends BoardGameController {
    @FXML
    public StackPane pane;

    @FXML
    public BoardGame boardGame;

    @FXML
    public VBox resultOverlay;

    @FXML
    public Label resultMessage, p1Score, p2Score;

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

    @Override
    public void update() {
        boardGame.drawBoard();
    }

    @Override
    public void close() {
        pane.getScene().getWindow().hide();
    }

    @FXML
    public void initialize() {
        boardGame.backgroundColor = new Color(0.0, 0.6, 0.0, 1.0);

        double piecePadding = 4;
        double pieceLineWidth = 10;

        boardGame.addPieceRenderer("BLACK", new CirclePieceRenderer(Color.BLACK, pieceLineWidth, piecePadding));
        boardGame.addPieceRenderer("WHITE", new CirclePieceRenderer(Color.WHITE, pieceLineWidth, piecePadding));
    }

}

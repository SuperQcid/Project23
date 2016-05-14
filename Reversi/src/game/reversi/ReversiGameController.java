package game.reversi;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import knof.controllers.GameController;
import knof.gamelogic.controllers.BoardGame;
import knof.model.game.Game;

public class ReversiGameController extends GameController {
    @FXML
    public BoardGame boardGame;

    private ReversiGame game;

    public void setGame(ReversiGame game) {
        this.game = game;
    }

    @Override
    public void update(Game game) {
        System.err.println("Update called");
    }

    @FXML
    public void initialize() {
        boardGame.backgroundColor = new Color(0.0, 0.7, 0.0, 0.0);

        double piecePadding = 4;
        double pieceLineWidth = 10;

        boardGame.addPieceRenderer("BLACK", new CirclePieceRenderer(Color.BLACK, pieceLineWidth, piecePadding));
        boardGame.addPieceRenderer("WHITE", new CirclePieceRenderer(Color.WHITE, pieceLineWidth, piecePadding));
    }

}

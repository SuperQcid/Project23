package knof.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import knof.gamelogic.controllers.PieceMiniatureCanvas;
import knof.gamelogic.controllers.PieceMiniatureRenderer;
import knof.model.game.Game;

/**
 * Created by Henk Dieter Oordt on 15-5-2016.
 */
public abstract class BoardGameController extends GameController {

    @FXML
    public PieceMiniatureCanvas leftPieceMiniatureCanvas;

    @FXML
    public PieceMiniatureCanvas rightPieceMiniatureCanvas;


    public void drawMiniatures(Game game, PieceMiniatureRenderer pmr){
        leftPieceMiniatureCanvas.init(pmr);
        rightPieceMiniatureCanvas.init(pmr);

        if(game.getSide1().equals(game.getLocalPlayer().getSide())){
            leftPieceMiniatureCanvas.drawMiniature(true);
            rightPieceMiniatureCanvas.drawMiniature(false);
        } else {
            rightPieceMiniatureCanvas.drawMiniature(true);
            leftPieceMiniatureCanvas.drawMiniature(false);
        }
    }
}

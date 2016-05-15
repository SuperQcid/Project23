package knof.gamelogic.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

/**
 * Created by Henk Dieter Oordt on 15-5-2016.
 */
public class PieceMiniatureCanvas extends Canvas {
    private PieceMiniatureRenderer miniatureRenderer;


    public final void drawMiniature(boolean playerOne){
        if(playerOne)
            miniatureRenderer.renderPlayerOneMiniature(getGraphicsContext2D());
        else
            miniatureRenderer.renderPlayerTwoMiniature(getGraphicsContext2D());
    }

    public final void init(PieceMiniatureRenderer miniatureRenderer){
        this.miniatureRenderer = miniatureRenderer;
    }

}

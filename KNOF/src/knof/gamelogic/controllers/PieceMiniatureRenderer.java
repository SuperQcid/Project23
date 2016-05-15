package knof.gamelogic.controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Henk Dieter Oordt on 15-5-2016.
 */
public abstract class PieceMiniatureRenderer extends Canvas {

    public abstract void renderPlayerOneMiniature(GraphicsContext gc);

    public abstract void renderPlayerTwoMiniature(GraphicsContext gc);

}

package game.reversi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import knof.gamelogic.controllers.PieceMiniatureRenderer;

/**
 * Created by Henk Dieter Oordt on 15-5-2016.
 */
public class ReversiPieceMiniatureRenderer extends PieceMiniatureRenderer {
    @Override
    public void renderPlayerOneMiniature(GraphicsContext gc) {
        gc.strokeOval(1, 1, 28 ,28);
        gc.setFill(Color.WHITE);
        gc.fillOval(2,2, 26,26);
    }

    @Override
    public void renderPlayerTwoMiniature(GraphicsContext gc) {
        gc.fillOval(1, 1, 28, 28);
    }
}

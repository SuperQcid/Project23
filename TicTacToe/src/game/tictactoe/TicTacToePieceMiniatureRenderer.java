package game.tictactoe;

import javafx.scene.canvas.GraphicsContext;
import knof.gamelogic.controllers.PieceMiniatureRenderer;

/**
 * Created by Henk Dieter Oordt on 15-5-2016.
 */
public class TicTacToePieceMiniatureRenderer extends PieceMiniatureRenderer {
    @Override
    public void renderPlayerOneMiniature(GraphicsContext gc) {
        gc.strokeOval(1, 1, 28 ,28);
    }

    @Override
    public void renderPlayerTwoMiniature(GraphicsContext gc) {
        gc.strokeLine(3, 3, 27, 27);
        gc.strokeLine(3, 27, 27, 3);
    }
}

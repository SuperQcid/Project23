package game.reversi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import knof.gamelogic.Board;
import knof.gamelogic.controllers.BoardGame;
import knof.gamelogic.controllers.PieceRenderer;

public class CirclePieceRenderer extends PieceRenderer {
    public CirclePieceRenderer(Color color, double lineWidth, double piecePadding) {
        super(color, lineWidth, piecePadding);
    }

    @Override
    public void renderShape(GraphicsContext gc, BoardGame boardGame, Board.Pos pos) {
        gc.fillOval(
                boardGame.getRowX(pos.x) + piecePadding,
                boardGame.getColY(pos.y) + piecePadding,
                boardGame.getCellWidth() - 2 * piecePadding,
                boardGame.getCellHeight() - 2 * piecePadding
        );
    }
}

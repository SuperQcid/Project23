package game.reversi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import knof.connection.Connection;
import knof.controllers.GameController;
import knof.gamelogic.GridGame;
import knof.model.game.Side;

import java.io.IOException;

public class ReversiGame extends GridGame<ReversiBoard> {
    public static final Side BLACK = new Side("BLACK");
    public static final Side WHITE = new Side("WHITE");

    public ReversiGame(Connection connection) {
        super(connection);
    }

    @Override
    public ReversiBoard createBoard() {
        return new ReversiBoard(this);
    }

    @Override
    protected GameController initGameController() {
        ReversiGameController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            ClassLoader cl = Reversi.class.getClassLoader();
            loader.setClassLoader(cl);
            Parent loaded = loader.load(getClass().getResource("ReversiGameController.fxml").openStream());
            controller = loader.getController();
            controller.setGame(this);
            controller.drawMiniatures(this, new ReversiPieceMiniatureRenderer());
            Stage stage = new Stage();
            //This next line here gives problems when playing locally
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loaded));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    @Override
    public Side getSide1() {
        return BLACK;
    }

    @Override
    public Side getSide2() {
        return WHITE;
    }
}

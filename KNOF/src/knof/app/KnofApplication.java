package knof.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.connection.Connection;
import knof.model.game.Game;
import knof.plugin.Plugin;
import knof.plugin.PluginLoader;

import java.util.HashMap;

public class KnofApplication extends Application {

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		primaryStage.setScene(new Scene(loader.load(getClass().getResource("../controllers/ConnectionController.fxml").openStream())));
		primaryStage.setTitle("Connect to server");
		primaryStage.show();
	}
}
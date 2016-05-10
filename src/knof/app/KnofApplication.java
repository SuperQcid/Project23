package knof.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.plugin.Plugin;
import knof.plugin.PluginLoader;

import java.util.HashMap;

public class KnofApplication extends Application {

	private static HashMap<String, Plugin> pluginList;

	public static void main(String[] args){
		pluginList = new PluginLoader().InitializePlugins();
		launch(args);
	}

	public static Plugin getPlugin(String name){
		return pluginList.get(name);
	}
	
	public static HashMap<String, Plugin> getPluginList() {
		return pluginList;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		primaryStage.setScene(new Scene(loader.load(getClass().getResource("../controllers/ConnectionController.fxml").openStream())));
		primaryStage.setTitle("Connect to server");
		primaryStage.show();
	}
}
package knof.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import knof.controllers.listcell.ChallengeCell;
import knof.controllers.listcell.GameCell;
import knof.controllers.listcell.PlayerCell;
import knof.model.Challenge;
import knof.model.Server;

import java.io.IOException;

public class ServerController {

	@FXML
	public ListView<String> gameList;

	@FXML
	public ListView<String> playerList;

	@FXML
	public ListView<Challenge> challengeList;

    @FXML
    public MenuItem menuItemConnect;

    @FXML
    public MenuItem menuItemConnectNewWindow;

    @FXML
    public MenuItem menuItemClose;

    public GameController currentGameController;

    public void setServer(Server server) {
    	this.playerList.setCellFactory((ListView<String> param) -> new PlayerCell(server));
        this.challengeList.setCellFactory((ListView<Challenge> param) -> new ChallengeCell());
        this.gameList.setCellFactory((ListView<String> param) -> new GameCell());

        /*
        this.gameList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

        this.gameList.setItems(server.games);

        //this might be a crappy way of adding a listener...
        this.gameList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				server.onGameClicked(gameList.getSelectionModel().getSelectedItem());
                server.currentGame.addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        newValue.addListener(observable1 -> {
                            if(observable1 instanceof Game){
                                Game game = (Game) observable1;
                                if(game.getLatestEvent() instanceof MatchEvent){
                                    currentGameController = game.createGameController();
                                }
                            }
                        });
                    }
                });
			}
        });
        */

        this.gameList.setItems(server.games);

        this.playerList.setItems(server.players);
        this.challengeList.setItems(server.challenges);
    }

    public void connect(ActionEvent event) throws IOException {
        if(event.getSource() instanceof MenuItem){
            MenuItem mi = (MenuItem) event.getSource();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Scene scene = new Scene(loader.load(getClass().getResource("ConnectionController.fxml").openStream()));
            if(mi.getText().equals("Connect to server...")) {
                Stage stage = (Stage) challengeList.getScene().getWindow();
                ConnectionController cc = loader.getController();
                cc.setNewWindow(false);
                cc.setServerController(this);
                cc.setServerControllerStage(stage);
            }
            primaryStage.setScene(scene);
            primaryStage.setTitle("Connect to server");
            primaryStage.show();
        }

    }

    public void close(){
        Platform.exit();
    }
}


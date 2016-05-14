package knof.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import knof.command.Command;
import knof.controllers.listcell.ChallengeCell;
import knof.controllers.listcell.GameCell;
import knof.controllers.listcell.PlayerCell;
import knof.model.Challenge;
import knof.model.GameEntry;
import knof.model.Server;

import java.io.IOException;

public class ServerController {

	@FXML
	public ListView<GameEntry> gameList;

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

    @FXML
    public Label labelPlayerName;

    public GameController currentGameController;

    public Server server;

    @FXML
    public void initialize() {
        this.playerList.setCellFactory((ListView<String> param) -> new PlayerCell(server));
        this.challengeList.setCellFactory((ListView<Challenge> param) -> new ChallengeCell());
        this.gameList.setCellFactory((ListView<GameEntry> param) -> new GameCell());
    }

    public void setServer(Server server) {
        if(this.server != null){
            this.server.connection.sendCommand(Command.LOGOUT);
        }
        this.server = server;
        this.labelPlayerName.setText(server.playerName);

        this.server.currentGame.addListener((observable, oldValue, newGame) -> {
            if(newGame!=null) {
                newGame.createGameController();
            }
        });

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


package knof.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import knof.controllers.listcell.ChallengeCell;
import knof.controllers.listcell.GameCell;
import knof.controllers.listcell.PlayerCell;
import knof.model.Challenge;
import knof.model.Server;

public class ServerController {

	@FXML
	public ListView<String> gameList;

	@FXML
	public ListView<String> playerList;

	@FXML
	public ListView<Challenge> challengeList;


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

    private void populateAIDropdown(){

    }
}


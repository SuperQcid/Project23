package knof.controllers.listcell.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.Set;

public class GameController extends ListCellController {

	@FXML
	public ChoiceBox<String> ai;

	public GameController() {
		// TODO
		// Load available AIs from plugin
		// Add available AIs to ChoiceBox ai.
	}

    @FXML
    public void onButton(ActionEvent e) {
        loadingSign.setVisible(true);
        button.setVisible(false);
        button.setDisable(true);
        ai.setDisable(true);
        // TODO
        // Launch the game with selected AI.
    }

    @Override
    public String getViewName() {
    	return "GameListCell";
    }

    public void populateAIChoiceBox(Set<String> playerTypes){
        ObservableList<String> ais  = FXCollections.observableArrayList(playerTypes);
        ai.setItems(ais);
        ai.getSelectionModel().selectFirst();
    }
}

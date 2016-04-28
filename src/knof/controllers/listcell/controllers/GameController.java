package knof.controllers.listcell.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameController extends ListCellController {
    
    @FXML
    public void onButton(ActionEvent e) {
        loadingSign.setVisible(true);
        button.setVisible(false);
        button.setDisable(true);
    }
    
    @Override
    public String getViewName() {
    	return "GameListCell";
    }
}

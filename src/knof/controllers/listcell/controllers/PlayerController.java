package knof.controllers.listcell.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PlayerController extends ListCellController {
    
    @FXML
    public void onButton(ActionEvent e) {
        System.out.println("CHALLENGE!!!");
        loadingSign.setVisible(true);
        button.setVisible(false);
        button.setDisable(true);
    }
}

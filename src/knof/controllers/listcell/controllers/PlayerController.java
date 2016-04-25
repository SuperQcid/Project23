package knof.controllers.listcell.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class PlayerController implements ListCellController {
    @FXML
    public Label playerName;
    
    @FXML
    public Button challenge;
    
    @FXML
    public ProgressIndicator loadingSign;
    
    @FXML
    public void challenge() {
        System.out.println("CHALLENGE!!!");
        loadingSign.setVisible(true);
        challenge.setVisible(false);
        challenge.setDisable(true);
    }
}

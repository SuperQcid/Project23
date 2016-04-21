package knof.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChallengeController {
    @FXML
    public Label playerName;

    @FXML
    public void challenge() {
        System.out.println("CHALLENGE!!!");
    }
}

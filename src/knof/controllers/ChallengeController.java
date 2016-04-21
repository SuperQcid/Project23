package knof.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import knof.model.Server;


public class ChallengeController {

    private Server server;

    @FXML
    public Label challengeName;

    @FXML
    public void challenge() {
        System.out.println("CHALLENGE!!!");
    }

    public void onChallengeAccept(ActionEvent e){

    }

    public void setServer(Server server) {
        this.server = server;
    }


}

package knof.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import knof.command.Command;
import knof.model.Server;


public class ChallengeController {

    private int challengeID;
    private Server server;

    @FXML
    public Label challengeName;

    @FXML
    public void onChallengeAccept(ActionEvent e){
        server.connection.sendCommand(Command.CHALLENGE_ACCEPT,challengeID);
    }

    public void setChallengeID(int id) {
        challengeID = id;
    }

    public void setServer(Server server) {
        this.server = server;
    }

}
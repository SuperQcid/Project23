package knof.controllers.listcell.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import knof.command.Command;
import knof.model.Server;

public class ChallengeController extends ListCellController{

    private int challengeID;
    private Server server;

    @FXML
    public void onButton(ActionEvent e){
        server.connection.sendCommand(Command.CHALLENGE_ACCEPT,challengeID);
        server.challenges.removeIf(challenge -> challenge.id == challengeID);
    }

    public void setChallengeID(int id) {
        challengeID = id;
    }

    public void setServer(Server server) {
        this.server = server;
    }

}
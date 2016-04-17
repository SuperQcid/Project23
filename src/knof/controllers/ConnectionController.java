package knof.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectionController {

    @FXML
    TextField hostName;

    @FXML
    TextField portNumber;

    @FXML
    TextField userName;

    @FXML
    public void connect(ActionEvent event) {
        //TODO: Try to make a connection and only open window when successful
        //TODO: When that is done, stop window from freezing

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            stage.setScene(new Scene(loader.load(getClass().getResource("../controllers/ServerController.fxml").openStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(hostName.getText()+":"+portNumber.getText());
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}

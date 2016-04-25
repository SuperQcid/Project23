package knof.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import knof.command.Command;
import knof.connection.Connection;
import knof.event.events.StatusEvent;
import knof.model.Server;

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
        //TODO: Stop window from freezing
        //TODO: Find better way to display errors
        String host = hostName.getText();
        int port = Integer.parseInt(portNumber.getText());
        String user = userName.getText();

        Connection connection;
        try {
            connection = new Connection(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        connection.sendCommandWithCallBack((StatusEvent status)->{
            if(status instanceof StatusEvent.Error) {
                System.err.println(((StatusEvent.Error) status).reason);
                return;
            }

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            try {
                stage.setScene(new Scene(loader.load(getClass().getResource("ServerController.fxml").openStream())));
                stage.setTitle(hostName.getText() + ":" + portNumber.getText());

                ServerController serverController = loader.getController();
                serverController.setServer(new Server(connection, user));

                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, Command.LOGIN, user);
    }
}

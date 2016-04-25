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

import javax.swing.*;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class ConnectionController {

    @FXML
    TextField hostName;

    @FXML
    TextField portNumber;

    @FXML
    TextField userName;

    @FXML
    public void connect(ActionEvent event) {
        String host;
        int port;
        String user;

        try {
            host = hostName.getText();
            port = Integer.parseInt(portNumber.getText());
            user = userName.getText();

        } catch (Exception e){
            showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection;
        try {
            connection = new Connection(host, port);
        } catch (IOException e) {
            showMessageDialog(null, "Could not connect to any server. Please check your input fields.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("komt de error dan:");
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
                stage.setScene(new Scene(loader.load(getClass().getResource("../controllers/ServerController.fxml").openStream())));
                stage.setTitle(hostName.getText() + ":" + portNumber.getText());

                ServerController serverController = loader.getController();
                serverController.setServer(new Server(connection));

                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, Command.LOGIN, user);
    }
}

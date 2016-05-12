package knof.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import knof.command.Command;
import knof.connection.Connection;
import knof.event.events.StatusEvent;
import knof.model.Server;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ConnectionController {
    private boolean newWindow = true;
    private final int PORT_NUMBER_LENGTH = 5;

    @FXML
    TextField hostName;

    @FXML
    TextField portNumber;

    @FXML
    TextField userName;

    @FXML
    GridPane gridPane;

    @FXML
    Button connectButton;

    private ServerController serverController;
    private Stage serverControllerStage;

    @FXML
    public void connect(ActionEvent event) {
        if(event.getSource() instanceof Button) {
            ((Button) event.getSource()).setDisable(true);
        }
        String host;
        int port;
        String user;

        try {

            host = hostName.getText();
            user = userName.getText();

            if (host.equals("") || user.equals("")) {
                throw new IllegalArgumentException(host + ", " + user);
            }

            port = Integer.parseInt(portNumber.getText());

        } catch (NumberFormatException nfe) {
            createDialogPane("Invalid Port Number!");
            return;
        } catch (IllegalArgumentException iae) {
            createDialogPane("Please fill out all the form fields");
            return;
        }

        Connection connection;
        try {
            connection = new Connection(host, port);
        } catch (IOException e) {
            if (e instanceof UnknownHostException) {
                createDialogPane("Unknown host!");
            } else {
                e.printStackTrace();
            }
            return;
        }
        connection.setPlayerName(user);
        connection.sendCommandWithCallBackLater((StatusEvent status) -> {
            if (status instanceof StatusEvent.Error) {
                System.err.println(((StatusEvent.Error) status).reason);
                createDialogPane("Username taken! Please use a different one.");
                return;
            }
            if(newWindow) {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                try {

                    stage.setScene(new Scene(loader.load(getClass().getResource("ServerController.fxml").openStream())));
                    stage.setTitle(hostName.getText() + ":" + portNumber.getText());

                    serverController = loader.getController();
                    serverController.setServer(new Server(connection, user));
                    stage.show();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                serverController.setServer(new Server(connection, user));
                serverControllerStage.setTitle(hostName.getText() + ":" + portNumber.getText());

                newWindow = true;
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }, Command.LOGIN, user);
    }

    public void setNewWindow(boolean newWindow){
        this.newWindow = newWindow;
    }

    public void setServerController(ServerController serverController){
        this.serverController = serverController;
    }

    public void setServerControllerStage(Stage serverControllerStage){
        this.serverControllerStage = serverControllerStage;
    }

    private void createDialogPane(String content) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait()
                .filter(response -> response == (ButtonType.OK))
                .ifPresent(response -> dialog.close());
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     */
    @FXML
    public void initialize() {

        //gridPane.getChildren().remove(portNumber);
        //portNumber = new NumberTextField();
        //gridPane.add(portNumber,1,1);

        portNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (Integer.parseInt(newValue) > 65535) {
                    portNumber.setText("65535");
                }
            } catch (NumberFormatException nfe){}
        });

        /**
         * Add a listener to the text field size to prevent it going over the largest value.
         * Every time the value is changed, it checks whether the character count > PORT_NUMBER_LENGTH
         * If it is, the textvalue is set to the text minus the overflowing characters.
         */
        portNumber.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > PORT_NUMBER_LENGTH) {
                portNumber.setText(portNumber.getText().substring(0, PORT_NUMBER_LENGTH));
            }
        });

        /*
            Add enter functionality to the text fields

         */
        hostName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                connectButton.fire();
            }
        });

        userName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                connectButton.fire();
            }
        });

        portNumber.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                connectButton.fire();
            }
        });

    }
}

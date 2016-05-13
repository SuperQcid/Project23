package knof.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import knof.command.Command;
import knof.connection.Connection;
import knof.event.events.StatusEvent;
import knof.model.Server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static knof.util.DialogHelper.createDialogPane;

public class ConnectionController {
    private boolean newWindow = true;
    private final int PORT_NUMBER_LENGTH = 5;

    private final boolean DEBUG = true;

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
        Button connect = (Button) event.getSource();
        connect.setDisable(true);
        new Thread(()->{
            doConnect(event, connect);
        }).start();
    }

    private void doConnect(ActionEvent event, Button connect) {
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
            createDialogPane("ERROR","Invalid Port Number!");
            connect.setDisable(false);
            return;
        } catch (IllegalArgumentException iae) {
            createDialogPane("ERROR","Please fill out all the form fields");
            connect.setDisable(false);
            return;
        }

        Connection connection;
        try {
            connection = new Connection(host, port);

            connection.setPlayerName(user);
            connection.sendCommandWithCallBackLater((StatusEvent status) -> {
                if (status instanceof StatusEvent.Error) {
                    System.err.println(((StatusEvent.Error) status).reason);
                    createDialogPane("ERROR","Username taken! Please use a different one.");
                    connect.setDisable(false);
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
                        connect.setDisable(false);
                    }
                } else {
                    serverController.setServer(new Server(connection, user));
                    serverControllerStage.setTitle(hostName.getText() + ":" + portNumber.getText());

                    newWindow = true;
                }
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }, Command.LOGIN, user);

        } catch (UnknownHostException e) {
            createDialogPane("ERROR","Unknown host!");
            enableButton(connect);
        } catch (ConnectException e) {
            createDialogPane("ERROR",e.getMessage());
            enableButton(connect);
        } catch (SocketException e){
            createDialogPane("ERROR","Invalid server");
            enableButton(connect);
        } catch (IOException e) {
            e.printStackTrace();
            enableButton(connect);
        }
    }

    private void enableButton(Button connect){
        Platform.runLater(()->{
            connect.setDisable(false);
        });
    }

    public void setNewWindow(boolean newWindow) {
        this.newWindow = newWindow;
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    public void setServerControllerStage(Stage serverControllerStage) {
        this.serverControllerStage = serverControllerStage;
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
                if (!newValue.equals("") && Integer.parseInt(newValue) > 65535) {
                    portNumber.setText("65535");
                }
            } catch (NumberFormatException nfe){
                nfe.printStackTrace();
            }
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

        if (DEBUG) {

            String hostname = "";
            try {
                InetAddress address;
                address = InetAddress.getLocalHost();
                hostname = address.getHostName();
            } catch (Exception e) {
                e.printStackTrace();
                hostname = "User";
            }

            portNumber.setText("7789");
            hostName.setText("samaranthlynx.nl");
            userName.setText(hostname+"-" + System.currentTimeMillis());
        }

    }
}

package knof.controllers;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import knof.command.Command;
import knof.connection.Connection;
import knof.event.events.StatusEvent;
import knof.model.Server;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable {

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
    public void connect(ActionEvent event) {
        String host;
        int port;
        String user;

        try {

            host = hostName.getText();
            user = userName.getText();

            if (host.equals("") || user.equals("")) {
                throw new InvalidArgumentException(new String[]{host, user});
            }

            port = Integer.parseInt(portNumber.getText());

        } catch (NumberFormatException nfe) {
            createDialogPane("Invalid Port Number!");
            return;
        } catch (InvalidArgumentException iae) {
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
                System.out.println("komt de error dan:");
                e.printStackTrace();
            }
            return;
        }

        connection.sendCommandWithCallBackLater((StatusEvent status) -> {
            if (status instanceof StatusEvent.Error) {
                System.err.println(((StatusEvent.Error) status).reason);
                return;
            }

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            try {
                stage.setScene(new Scene(loader.load(getClass().getResource("ServerController.fxml").openStream())));
                stage.setTitle(hostName.getText() + ":" + portNumber.getText());

                ServerController serverController = loader.getController();
                serverController.setServer(new Server(connection));

                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, Command.LOGIN, user);
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
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gridPane.getChildren().remove(portNumber);
        portNumber = new NumberTextField();
        gridPane.add(portNumber,1,1);

        portNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if(Integer.parseInt(newValue) > 65535){
                portNumber.setText("65535");
            }
        });

        /**
         * Add a listener to the text field size to prevent it going over the largest value.
         * Every time the value is changed, it checks whether the character count > PORT_NUMBER_LENGTH
         * If it is, the textvalue is set to the text minus the overflowing characters.
         */
        portNumber.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > PORT_NUMBER_LENGTH) {
                portNumber.setText(portNumber.getText().substring(0,PORT_NUMBER_LENGTH));
            }
        });
    }

    /**
     * Textfield which allows only numeric values
     */
    private class NumberTextField extends TextField {

        @Override
        public void replaceText(int start, int end, String text) {
            if (validate(text)) {
                super.replaceText(start, end, text);
            }
        }

        @Override
        public void replaceSelection(String text) {
            if (validate(text)) {
                super.replaceSelection(text);
            }
        }

        /**
         * Validates input, only allowing values [0-9]
         * @param text Input
         * @return valid/invalid
         */
        private boolean validate(String text) {
            return text.matches("[0-9]*");
        }
    }
}

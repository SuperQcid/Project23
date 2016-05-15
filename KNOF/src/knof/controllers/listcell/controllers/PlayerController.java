package knof.controllers.listcell.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.controllers.popup.ChallengePopupController;
import knof.model.Server;

public class PlayerController extends ListCellController {

    public Server server;
	protected Parent loaded;
    
    @FXML
    public void onButton(ActionEvent e) {
        System.out.println("CHALLENGE!!!");
        loadingSign.setVisible(true);
        button.setVisible(false);
        button.setDisable(true);
        if(isServerSet()){
			try {
				FXMLLoader loader = new FXMLLoader();
				loaded = loader.load(getClass().getResource("../../popup/ChallengePopupController.fxml").openStream());
				ChallengePopupController controller = loader.getController();
				controller.setServer(server);
				controller.player = cell.getText();
				Stage stage = new Stage();
				stage.setOnCloseRequest((value)->{
					button.setDisable(false);
					loadingSign.setVisible(false);
					button.setVisible(true);
				});
				stage.setScene(new Scene(loaded));
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }

    public void setServer(Server server){
    	this.server = server;
    }
    
    public boolean isServerSet(){
		return (server != null);
	}

	public String getViewName() {
		return "ListCell";
	}
}

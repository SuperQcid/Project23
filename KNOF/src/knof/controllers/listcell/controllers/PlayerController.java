package knof.controllers.listcell.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import knof.controllers.popup.ChallengePopupController;
import knof.model.Server;

public class PlayerController extends ListCellController {

    public Server server;
	protected Parent loaded;
	private ListView<String> listView;

	@FXML
    public void onButton(ActionEvent e) {
        System.out.println("CHALLENGE!!!");
        //button.setDisable(true);
		//listView.setDisable(true);
        if(isServerSet()){
			try {
				FXMLLoader loader = new FXMLLoader();
				loaded = loader.load(getClass().getResource("../../popup/ChallengePopupController.fxml").openStream());
				ChallengePopupController controller = loader.getController();
				controller.setServer(server);
				controller.player = cell.getText();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setOnCloseRequest((value)->{
					//button.setDisable(false);
					//listView.setDisable(false);
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
		this.server.currentChallenge.addListener((observable, oldValue, newValue) -> {
			loadingSign.setVisible(newValue.equals(this.cell.getText()));
			button.setVisible(!newValue.equals(this.cell.getText()));
		});
    }
    
    public boolean isServerSet(){
		return (server != null);
	}

	public String getViewName() {
		return "ListCell";
	}

	public void setListView(ListView<String> listView) {
		this.listView = listView;
	}
}

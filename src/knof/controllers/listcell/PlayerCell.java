package knof.controllers.listcell;

import javafx.application.Platform;
import knof.controllers.listcell.controllers.ListCellController;
import knof.controllers.listcell.controllers.PlayerController;
import knof.model.Server;

public class PlayerCell extends Cell<String> {

	public Server server;

	private PlayerController controller = new PlayerController();

	public PlayerCell(Server server) {
		super();
		this.server = server;
	}

	@Override
	public void cell(String item) {
		if (!controller.isServerSet()) {
			controller.setServer(this.server);
		}
		Platform.runLater(() -> {
			controller.cell.setText(item);
			controller.button.setText("Challenge");
		});

	}

	@Override
	public ListCellController getController() {
		return controller;
	}

}

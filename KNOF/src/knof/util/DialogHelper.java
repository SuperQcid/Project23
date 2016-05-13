package knof.util;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * Created by kevin on 5/13/16.
 */
public class DialogHelper {

    public static void createDialogPane(String title, String content) {
        Platform.runLater(()->{
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.setContentText(content);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait()
                    .filter(response -> response == (ButtonType.OK))
                    .ifPresent(response -> dialog.close());
        });
    }
}

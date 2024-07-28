package start.notatki.moje.mojenotatki.utils;

import javafx.scene.control.Alert;

public class CustomAlert extends Alert {

    public CustomAlert(String message) {
        super(AlertType.ERROR);
        this.setTitle("Error");
        this.setHeaderText(message);
    }
}

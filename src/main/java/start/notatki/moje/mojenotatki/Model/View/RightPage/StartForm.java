package start.notatki.moje.mojenotatki.Model.View.RightPage;


import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartForm extends VBox {

    Label lblWelcome = new Label("Welcome to your Notes App");

    public StartForm() {
        this.getStyleClass().add("this-startForm");
        loadForm();
    }

    public void loadForm() {
        this.getChildren().add(lblWelcome);
        lblWelcome.getStyleClass().add("lblWelcome");
    }
}

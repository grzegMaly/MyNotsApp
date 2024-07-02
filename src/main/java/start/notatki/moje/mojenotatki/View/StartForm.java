package start.notatki.moje.mojenotatki.View;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class StartForm extends VBox {

    Label lblWelcome = new Label("Welcome to your Notes App");

    public StartForm() {

        this.getChildren().add(lblWelcome);
        this.setAlignment(Pos.CENTER);
        lblWelcome.textFillProperty().set(Color.web("#bbbbbb"));
        lblWelcome.setFont(Font.font("System", FontPosture.ITALIC, 20));

    }
}

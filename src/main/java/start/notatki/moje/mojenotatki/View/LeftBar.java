package start.notatki.moje.mojenotatki.View;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LeftBar extends VBox {

    private final Label newNoteLbl = new Label("New Note");
    private final Button plusButton = new Button("+");

    public LeftBar() {

        loadAddingNotes();
        bindButtons();
    }


    private void loadAddingNotes() {

        HBox labelHBox = new HBox(newNoteLbl);
        HBox hBox = new HBox(labelHBox, plusButton);
        newNoteLbl.setAlignment(Pos.CENTER);
        newNoteLbl.setPadding(new Insets(10));

        plusButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");

        labelHBox.setStyle("-fx-background-color: rgb(0,81,255)");
        hBox.setMinHeight(200);
        hBox.setMinHeight(40);

        this.getChildren().add(hBox);
    }

    private void bindButtons() {
    }
}

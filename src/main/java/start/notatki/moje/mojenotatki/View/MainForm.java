package start.notatki.moje.mojenotatki.View;


import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MainForm extends GridPane {

    public MainForm() {

        Label lblTitle = new Label("Tile");
        TextField tfTile = new TextField();

        Label lblType = new Label("Type");

        TextArea taContent = new TextArea();

        this.setGridLinesVisible(true);

        this.add(lblTitle, 1, 1);
        this.add(tfTile, 1, 2);
    }
}

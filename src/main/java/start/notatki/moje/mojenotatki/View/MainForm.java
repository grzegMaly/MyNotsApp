package start.notatki.moje.mojenotatki.View;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainForm extends StartForm {

    private final Button btnSave = new Button("Save");
    private final Button btnCancel = new Button("Cancel");
    private final ButtonBar btnBar = new ButtonBar();

    private final Label lblTitle = new Label("Title");
    private final TextField pfTitle = new TextField();
    private final ChoiceBox<String> cbType =
            new ChoiceBox<>(FXCollections.observableArrayList("", "Regular Note", "Plan Note"));
    TextArea taContent = new TextArea();


    GridPane gp = new GridPane();

    public MainForm() {

        this.setAlignment(null);

        styleButtons();
        this.getChildren().clear();
        this.getChildren().add(gp);
//        gp.add(btnBar, 0, 0);
        gp.add(lblTitle, 0, 0);
        gp.add(btnBar, 3, 0);
        gp.add(taContent, 0, 3);
        gp.setGridLinesVisible(true);
    }

    private void styleButtons() {

        btnBar.getButtons().addAll(btnCancel, btnSave);
        btnBar.setPadding(new Insets(5));

        for (var button : btnBar.getButtons()) {

            Button button1 = (Button) button;

            button1.textFillProperty().set(Color.web("#bbbbbb"));

            button1.setBackground(
                    new Background(
                            new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)
                    )
            );
            button1.setBorder(
                    new Border(
                            new BorderStroke(Color.TRANSPARENT,
                                    BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)
                    )
            );
        }
    }
}

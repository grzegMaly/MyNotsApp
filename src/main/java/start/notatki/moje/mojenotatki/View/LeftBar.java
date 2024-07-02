package start.notatki.moje.mojenotatki.View;


import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.List;
import java.util.stream.Collectors;


public class LeftBar extends VBox {

    private final VBox btnVBox = new VBox();
    private final Button plusButton = new Button("New Note");
    private final MainScene root;

    public LeftBar(MainScene root) {

        this.root = root;

        btnVBox.getChildren().add(plusButton);

        loadAddingNotes();
        loadButtonEvents();
        bindButtons();
    }

    private void loadButtonEvents() {

        plusButton.setOnMouseClicked(evt -> {
                    root.setMainForm(new MainForm());
                    root.loadMainScene();
                }
        );
    }

    public VBox getBtnVBox() {
        return btnVBox;
    }

    public List<Button> getButtons() {
        return getBtnVBox().getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .collect(Collectors.toList());
    }

    private void loadAddingNotes() {

        this.setPadding(new Insets(10));

        DoubleBinding sizeLeft = this.widthProperty().subtract(this.getPadding().getLeft() +
                this.getPadding().getRight());
        btnVBox.minWidthProperty().bind(sizeLeft.multiply(1));

        for (Node button : btnVBox.getChildren()) {
            if (button instanceof Button btn) {
                btn.minWidthProperty().bind(btnVBox.minWidthProperty());
            }
        }

        plusButton.textFillProperty().set(Color.web("#bbbbbb"));
        plusButton.setFont(Font.font("System", FontPosture.REGULAR, 25));
        plusButton.setBackground(
                new Background(
                        new BackgroundFill(Color.web("#484848"), new CornerRadii(15), Insets.EMPTY)
                )
        );

        plusButton.setOnMouseEntered(event -> {
            plusButton.setStyle("-fx-background-color: #606060; -fx-text-fill: #d4d4d4;");
        });

        plusButton.setOnMouseExited(event -> {
            plusButton.setStyle("-fx-background-color: #484848; -fx-text-fill: #bbbbbb;");
        });


        this.getChildren().add(btnVBox);
    }

    private void bindButtons() {
    }
}

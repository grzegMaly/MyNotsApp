package start.notatki.moje.mojenotatki.Model.View;


import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import start.notatki.moje.mojenotatki.utils.DirectoryUtils;


public class LeftBar extends VBox {

    private final VBox btnVBox = new VBox();
    private final Button plusButton = new Button("New Note");
    private final Button listButton = new Button("List Notes");
    private final Button notedDirectory = new Button("Notes Directory");

    private final MainScene mainScene;

    public LeftBar(MainScene mainScene) {

        this.mainScene = mainScene;
        this.getChildren().add(btnVBox);
        loadButtons();
    }

    private void loadButtons() {

        btnVBox.getStyleClass().add("btn-VBox");
        btnVBox.getChildren().addAll(plusButton, listButton, notedDirectory);

        this.getStyleClass().add("this-leftBar");
        DoubleBinding sizeLeft =
                this.minWidthProperty().subtract(this.getPadding().getLeft() +
                        this.getPadding().getRight());
        btnVBox.minWidthProperty().bind(sizeLeft.multiply(1));

        btnVBox.getChildren().forEach(b -> {
            if (b instanceof Button btn) {
                btn.getStyleClass().add("lb-button");
                btn.minWidthProperty().bind(btnVBox.minWidthProperty());
            }
        });
        notedDirectory.setWrapText(true);

        loadButtonEvents();
    }

    private void loadButtonEvents() {

        plusButton.setOnMouseClicked(evt -> {
            mainScene.useMainForm(true);
        });
        listButton.setOnMouseClicked(evt -> {
            mainScene.useNotesList(true);
            mainScene.getNotesList().reloadNotes();
        });
        notedDirectory.setOnMouseClicked(evt -> DirectoryUtils.changeOutputDirectory());
    }
}

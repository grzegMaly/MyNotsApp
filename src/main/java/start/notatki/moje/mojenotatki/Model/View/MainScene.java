package start.notatki.moje.mojenotatki.Model.View;


import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.*;
import start.notatki.moje.mojenotatki.Model.View.RightPage.MainForm;
import start.notatki.moje.mojenotatki.Model.View.RightPage.NotesList;
import start.notatki.moje.mojenotatki.Model.View.RightPage.StartForm;


public class MainScene extends HBox {

    private final StartForm startForm = new StartForm();
    private final LeftBar leftBar = new LeftBar(this);
    private final MainForm mainForm = new MainForm(this);
    private final NotesList notesList = new NotesList(this);

    StackPane stackPane = new StackPane();

    public MainScene() {

        this.getStyleClass().add("main-scene");

        loadStartScene();
        loadLeftBar();
        loadMainForm();
        loadNotesList();

        this.getChildren().addAll(leftBar, stackPane);
    }

    public void useMainForm(Boolean value) {
        startForm.setVisible(!value);
        notesList.setVisible(!value);
        mainForm.setVisible(value);
    }

    public void useNotesList(Boolean value) {
        notesList.setVisible(value);
        startForm.setVisible(!value);
        mainForm.setVisible(!value);
    }


    private void loadStartScene() {

        startForm.getStyleClass().add("stack-pane");

        DoubleBinding availableWidth = this.minWidthProperty()
                .subtract(startForm.getPadding().getLeft() +
                        startForm.getPadding().getRight() + startForm.getSpacing());

        stackPane.setMinWidth(availableWidth.get());

        leftBar.minWidthProperty().bind(availableWidth.multiply(0.25));
        startForm.minWidthProperty().bind(availableWidth.multiply(0.75));

        stackPane.getChildren().addAll(notesList, mainForm, startForm);
    }

    private void loadLeftBar() {

        leftBar.getStyleClass().add("left-bar");
    }

    public void loadMainForm() {

        mainForm.getStyleClass().add("main-form");
        mainForm.minWidthProperty().bind(startForm.minWidthProperty());
        mainForm.minHeightProperty().bind(startForm.minHeightProperty());
    }

    public void loadNotesList() {

        notesList.getStyleClass().add("main-form");
        notesList.minWidthProperty().bind(mainForm.minWidthProperty());
        notesList.minHeightProperty().bind(mainForm.minHeightProperty());
    }
}

package start.notatki.moje.mojenotatki.Model.View;


import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.*;
import start.notatki.moje.mojenotatki.Model.View.RightPage.MainForm;
import start.notatki.moje.mojenotatki.Model.View.RightPage.StartForm;


public class MainScene extends HBox {

    private final StartForm startForm = new StartForm();
    private final LeftBar leftBar = new LeftBar(this);
    private final MainForm mainForm = new MainForm(this);

    StackPane stackPane = new StackPane();

    public MainScene() {

        this.getStyleClass().add("main-scene");

        loadStartScene();
        loadLeftBar();
        loadMainForm();

        this.getChildren().addAll(leftBar, stackPane);
    }

    public void useMainForm() {
        startForm.setVisible(false);
        mainForm.setVisible(true);
    }


    private void loadStartScene() {

        startForm.getStyleClass().add("stack-pane");

        DoubleBinding availableWidth = this.minWidthProperty()
                .subtract(startForm.getPadding().getLeft() +
                        startForm.getPadding().getRight() + startForm.getSpacing());

        stackPane.setMinWidth(availableWidth.get());

        leftBar.minWidthProperty().bind(availableWidth.multiply(0.25));
        startForm.minWidthProperty().bind(availableWidth.multiply(0.75));

        mainForm.setVisible(false);
        stackPane.getChildren().addAll(startForm, mainForm);
    }

    private void loadLeftBar() {

        leftBar.getStyleClass().add("left-bar");
    }

    public void loadMainForm() {

        mainForm.getStyleClass().add("main-form");
        mainForm.minWidthProperty().bind(startForm.minWidthProperty());
        mainForm.minHeightProperty().bind(startForm.minHeightProperty());
    }
}

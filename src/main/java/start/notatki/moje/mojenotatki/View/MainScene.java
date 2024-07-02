package start.notatki.moje.mojenotatki.View;


import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.*;


public class MainScene extends HBox {

    private final LeftBar leftBar = new LeftBar(this);
    private final StartForm startForm = new StartForm();
    private MainForm mainForm;
    HBox hBox = new HBox();

    public MainScene() {

        this.getStyleClass().add("main-scene");

        loadStartScene();
        loadLeftBar();
        loadWelcomePage();

    }

    public void setMainForm(MainForm mainForm) {

        this.mainForm = mainForm;
    }

    private void loadStartScene() {

        hBox.setMinWidth(this.getMinWidth());
        hBox.setMinHeight(this.getMinHeight());
        hBox.getStyleClass().add("hbox");


        DoubleBinding availableWidth = this.minWidthProperty().subtract(
                hBox.getPadding().getLeft() + hBox.getPadding().getRight() + hBox.getSpacing());

        leftBar.minWidthProperty().bind(availableWidth.multiply(0.25));
        startForm.minWidthProperty().bind(availableWidth.multiply(0.75));

        hBox.getChildren().addAll(leftBar, startForm);
        this.getChildren().add(hBox);
    }

    private void loadWelcomePage() {

        startForm.getStyleClass().add("welcome-page");
    }

    private void loadLeftBar() {

        leftBar.getStyleClass().add("left-bar");
    }

    public void loadMainScene() {


        mainForm.getStyleClass().add("main-form");
        hBox.getChildren().set(1, mainForm);
    }
}

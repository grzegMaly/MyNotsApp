package start.notatki.moje.mojenotatki;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.LoadStyles;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class CustomDirectoryDialog extends Stage {

    private final String defaultPath = Path.of(".").toAbsolutePath().normalize() + File.separator + "Notes";

    private final Label lbInfoText = new Label();
    private final TextField tfPath = new TextField(defaultPath);

    private final Button explorerButton = new Button("...");

    private final Button okButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");
    private final ButtonBar btnBar = new ButtonBar();
    HBox selectingBox = new HBox();
    VBox dialogScene = new VBox();

    Optional<String> result = Optional.empty();

    public CustomDirectoryDialog(Stage owner) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);

        loadButtons();
        loadStyles();
        loadContent();
    }

    private void loadButtons() {

        explorerButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Output Directory");
            File selectedDirectory = directoryChooser.showDialog(this);
            if (selectedDirectory != null && selectedDirectory.isDirectory()) {
                tfPath.setText(selectedDirectory.getAbsolutePath());
            }
        });

        okButton.setOnAction(e -> {
            result = Optional.of(tfPath.getText());
            close();
        });

        cancelButton.setOnAction(e -> {
            result = Optional.empty();
            close();
        });
    }

    private void loadStyles() {

        selectingBox.minWidthProperty().bind(dialogScene.minWidthProperty());
        tfPath.minHeightProperty().bind(selectingBox.heightProperty());
        explorerButton.maxHeightProperty().bind(selectingBox.heightProperty());

        ButtonBar.setButtonData(okButton, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(cancelButton, ButtonBar.ButtonData.CANCEL_CLOSE);
        btnBar.getButtons().addAll(okButton, cancelButton);

        for (var button : btnBar.getButtons()) {

            Button button1 = (Button) button;
            button1.getStyleClass().add("button");
        }

        HBox.setHgrow(tfPath, Priority.ALWAYS);
        VBox.setVgrow(tfPath, Priority.ALWAYS);
        tfPath.setMaxWidth(Double.MAX_VALUE);

        btnBar.getStyleClass().add("btnBar");
        tfPath.getStyleClass().add("tfPath");
        explorerButton.getStyleClass().add("explorerButton");
        lbInfoText.getStyleClass().add("lbInfoText");
        selectingBox.getStyleClass().add("selectingBox");
        dialogScene.getStyleClass().add("dialogScene");
    }

    private void loadContent() {

        selectingBox.getChildren().addAll(tfPath, explorerButton);

        Separator separator1 = new Separator();
        Separator separator2 = new Separator();

        dialogScene.getChildren().addAll(lbInfoText, separator1, selectingBox, separator2, btnBar);

        Scene scene = new Scene(dialogScene);
        LoadStyles.loadCustomDirectoryStyles(scene);
        setResizable(false);
        setScene(scene);
    }

    public void setHeader(String headerText) {
        lbInfoText.setText(headerText);
    }

    public Optional<String> showAndWaitForResult() {
        showAndWait();
        return result;
    }
}

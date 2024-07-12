package start.notatki.moje.mojenotatki;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.BaseConfig;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Config.LoadStyles;
import start.notatki.moje.mojenotatki.Model.View.MainScene;

import java.io.File;
import java.util.Objects;
import java.util.Optional;


public class MyNotesApp extends Application {
    @Override
    public void start(Stage primaryStage) {

        System.out.println(FilesManager.checkNotesDirectoryExistence());

        Scene scene = loadScene(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Your Favorite Notes");

        primaryStage.setOnShowing(event -> checkOrSetOutputDirectory(primaryStage));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Scene loadScene(Stage primaryStage) {

        String iconPath = BaseConfig.getIconPath();
        Image icon = null;

        try {
            icon = new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm());
        } catch (Exception ignored) {
            // Ignore
        }

        if (icon != null && !icon.isError()) {
            primaryStage.getIcons().add(icon);
        }

        MainScene mainScene = new MainScene();
        Scene scene = new Scene(mainScene);

        LoadStyles.loadMainStyles(scene);

        return scene;
    }

    private void checkOrSetOutputDirectory(Stage primaryStage) {

        if (FilesManager.checkNotesDirectoryExistence()) {
            return;
        }

        CustomDirectoryDialog dialog = new CustomDirectoryDialog(primaryStage);
        dialog.setTitle("Choosing Directory");
        dialog.setHeader("Output directory not set. Choose:");

        Optional<String> result = dialog.showAndWaitForResult();
        result.ifPresent(directoryPath -> FilesManager.setConfigKey("notesDirectory", directoryPath));
    }

    private void showAlertAndExit(Stage primaryStage) {

        Alert alert = new Alert(Alert.AlertType.ERROR, "No Output Directory selected.", ButtonType.OK);
        alert.showAndWait();
        primaryStage.close();
    }
}

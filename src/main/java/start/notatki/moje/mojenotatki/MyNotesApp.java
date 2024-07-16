package start.notatki.moje.mojenotatki;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.BaseConfig;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Config.LoadStyles;
import start.notatki.moje.mojenotatki.Model.View.MainScene;

import java.util.Objects;
import java.util.Optional;


public class MyNotesApp extends Application {

    private static Stage stage;
    private void setStage(Stage stage) {
        MyNotesApp.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) {

        setStage(primaryStage);

        Scene scene = loadScene();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Your Favorite Notes");

        stage.setOnShowing(event -> {

            if (FilesManager.checkNotesDirectoryExistence()) {
                return;
            }

            checkOrSetOutputDirectory();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Scene loadScene() {

        String iconPath = BaseConfig.getIconPath();
        Image icon = null;

        try {
            icon = new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm());
        } catch (Exception exc) {
            FilesManager.registerException(exc);
        }

        if (icon != null && !icon.isError()) {
            stage.getIcons().add(icon);
        }

        MainScene mainScene = new MainScene();
        Scene scene = new Scene(mainScene);

        LoadStyles.loadMainStyles(scene);

        return scene;
    }

    public static void checkOrSetOutputDirectory() {

        CustomDirectoryDialog dialog = new CustomDirectoryDialog(stage);
        dialog.setTitle("Choosing Directory");
        dialog.setHeader("Output directory not set. Choose:");

        Optional<String> result = dialog.showAndWaitForResult();
        result.ifPresent(directoryPath -> FilesManager.setConfigKey("notesDirectory", directoryPath));
    }
}

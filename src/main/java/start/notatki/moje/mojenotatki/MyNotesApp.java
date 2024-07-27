package start.notatki.moje.mojenotatki;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.BaseConfig;
import start.notatki.moje.mojenotatki.Config.LoadStyles;
import start.notatki.moje.mojenotatki.Model.View.MainScene;
import start.notatki.moje.mojenotatki.utils.DirectoryUtils;

import java.util.concurrent.*;

public class MyNotesApp extends Application {

    private static Stage primaryStage;
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        stage.setTitle("Your Favorite Notes");

        Callable<Scene> callableSceneLoader = () -> loadScene(stage);
        Future<Scene> result = executor.submit(callableSceneLoader);

        executor.submit(() -> {
            try {
                Scene scene = result.get();
                Platform.runLater(() -> {
                    if (scene != null) {
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    } else {
                        showErrorDialog();
                    }
                });
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        stage.setOnShowing(event -> DirectoryUtils.checkOrSetOutputDirectory());
    }

    private Scene loadScene(Stage stage) {

        executor.submit(() -> {
            Image iconResult = BaseConfig.getIcon();
            if (iconResult != null && !iconResult.isError()) {
                Platform.runLater(() -> stage.getIcons().add(iconResult));
            }
        });

        MainScene mainScene = new MainScene();
        Scene scene = new Scene(mainScene);

        return LoadStyles.loadMainStyles(scene) ? scene : null;
    }

    private void showErrorDialog() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Application Had Problem to Run");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

    @Override
    public void stop() throws Exception {
        executor.shutdown();
    }
}

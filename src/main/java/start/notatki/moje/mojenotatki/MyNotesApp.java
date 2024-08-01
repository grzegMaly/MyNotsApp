package start.notatki.moje.mojenotatki;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.BaseConfig;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Config.LoadStyles;
import start.notatki.moje.mojenotatki.Model.View.MainScene;
import start.notatki.moje.mojenotatki.utils.CustomAlert;
import start.notatki.moje.mojenotatki.utils.DirectoryUtils;
import start.notatki.moje.mojenotatki.utils.ExecutorServiceManager;

import java.util.concurrent.*;

public class MyNotesApp extends Application {

    private static Stage primaryStage;
    MainScene mainScene = new MainScene();
    private final ExecutorService executor =
            ExecutorServiceManager.createCachedThreadPool(this.getClass().getSimpleName());

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

        /*CompletableFuture<Boolean> directoryCheck = DirectoryUtils.checkOrSetOutputDirectory();
        directoryCheck.thenAccept(directoryExists -> {
            if (!directoryExists) {
                Platform.runLater(this::showErrorDialog);
            } else {

            }
        });*/

        Future<Scene> result = executor.submit(() -> loadScene(stage));

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


        stage.setOnShowing(event -> {
            DirectoryUtils.checkOrSetOutputDirectory().thenAccept(success -> {
                //Do nothing
            });
        });
    }

    private Scene loadScene(Stage stage) {

        CountDownLatch latch = new CountDownLatch(1);

        executor.submit(() -> {
            Image iconResult = BaseConfig.getIcon();
            if (iconResult != null && !iconResult.isError()) {
                Platform.runLater(() -> stage.getIcons().add(iconResult));
            }
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            FilesManager.registerException(e);
            Thread.currentThread().interrupt();
            showErrorDialog();
            return null;
        }

        mainScene.loadPage();
        Scene scene = new Scene(mainScene);

        return LoadStyles.loadMainStyles(scene) ? scene : null;
    }

    private void showErrorDialog() {

        Alert alert = new CustomAlert("Application Had Problem to Run");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

    @Override
    public void stop() throws Exception {
        ExecutorServiceManager.shutdownAll();
        super.stop();
    }
}

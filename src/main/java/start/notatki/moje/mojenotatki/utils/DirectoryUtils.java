package start.notatki.moje.mojenotatki.utils;

import javafx.application.Platform;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.View.CustomDirectoryDialog;
import start.notatki.moje.mojenotatki.MyNotesApp;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DirectoryUtils {

    private static final  Stage stage = MyNotesApp.getPrimaryStage();

    private CompletableFuture<Boolean> getOutputDirectoryExistenceResult() {
        return FilesManager.checkNotesDirectoryExistence();
    }

    public static CompletableFuture<Boolean> checkOrSetOutputDirectory() {

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        Thread thread = new Thread(() -> FilesManager.checkNotesDirectoryExistence().thenAccept(exists -> {
            if (!exists) {
                Platform.runLater(() -> {
                    CustomDirectoryDialog dialog = new CustomDirectoryDialog(stage);
                    dialog.setTitle("Choosing Directory");
                    dialog.setHeader("Output directory not set. Choose:");

                    Optional<String> result = dialog.showAndWaitForResult();
                    result.ifPresent(directoryPath -> {
                        FilesManager.setConfigKey("notesDirectory", directoryPath);
                        completableFuture.complete(true);
                    });
                    if (result.isEmpty()) {
                        completableFuture.complete(false);
                    }
                });
            } else {
                completableFuture.complete(true);
            }
        }).exceptionally(ex -> {
            completableFuture.completeExceptionally(ex);
            return null;
        }));
        thread.start();

        return completableFuture;
    }

    public static void changeOutputDirectory() {

        try {
            if (checkOrSetOutputDirectory().get()) {
                Thread task = new Thread(() -> Platform.runLater(() -> {

                    CustomDirectoryDialog dialog = new CustomDirectoryDialog(stage);
                    dialog.setTitle("Choosing new directory");
                    dialog.setHeader("Set new output directory");

                    Optional<String> result = dialog.showAndWaitForResult();
                    result.ifPresent(directoryPath -> {
                        FilesManager.moveToNewLocation(directoryPath);
                        FilesManager.setConfigKey("notesDirectory", directoryPath);
                    });
                }));

                task.start();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

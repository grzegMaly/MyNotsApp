package start.notatki.moje.mojenotatki.utils;

import javafx.application.Platform;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.View.CustomDirectoryDialog;
import start.notatki.moje.mojenotatki.MyNotesApp;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DirectoryUtils {

    private static final Stage stage = MyNotesApp.getPrimaryStage();
    public static CompletableFuture<Boolean> checkOrSetOutputDirectory() {

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        return FilesManager.checkNotesDirectoryExistence()
                .thenCompose(exists -> {
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
                        return completableFuture;
                    } else {
                        return CompletableFuture.completedFuture(true);
                    }
                }).exceptionally(ex -> {
                    FilesManager.registerException((RuntimeException) ex);
                    return null;
                });
    }

    public static void changeOutputDirectory() {

        FilesManager.checkNotesDirectoryExistence()
                .thenAccept(exists -> Platform.runLater(() -> {
                    CustomDirectoryDialog dialog = new CustomDirectoryDialog(stage);
                    dialog.setTitle("Choosing new directory");

                    if (exists) {
                        dialog.setHeader("Set new output directory");
                    } else {
                        dialog.setHeader("Output directory not set. Choose:");
                    }

                    Optional<String> result = dialog.showAndWaitForResult();
                    result.ifPresent(directoryPath -> {
                        if (exists) {
                            FilesManager.moveToNewLocation(directoryPath);
                        }
                        FilesManager.setConfigKey("notesDirectory", directoryPath);
                    });
                }));
    }
}

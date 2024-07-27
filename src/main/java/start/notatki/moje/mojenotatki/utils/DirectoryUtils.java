package start.notatki.moje.mojenotatki.utils;

import javafx.application.Platform;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.View.CustomDirectoryDialog;
import start.notatki.moje.mojenotatki.MyNotesApp;

import java.util.Optional;

public class DirectoryUtils {

    private static final  Stage stage = MyNotesApp.getPrimaryStage();

    public static void checkOrSetOutputDirectory() {

        Thread thread = new Thread(() -> {

            if (!FilesManager.checkNotesDirectoryExistence()) {
                Platform.runLater(() -> {
                    CustomDirectoryDialog dialog = new CustomDirectoryDialog(stage);
                    dialog.setTitle("Choosing Directory");
                    dialog.setHeader("Output directory not set. Choose:");

                    Optional<String> result = dialog.showAndWaitForResult();
                    result.ifPresent(directoryPath -> FilesManager.setConfigKey("notesDirectory", directoryPath));
                });
            }
        });

        thread.start();
    }
}

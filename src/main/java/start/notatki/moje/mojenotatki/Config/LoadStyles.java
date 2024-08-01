package start.notatki.moje.mojenotatki.Config;

import javafx.scene.Scene;

import java.net.URL;
import java.util.Objects;

public class LoadStyles {

    public static boolean loadMainStyles(Scene scene) {

        return addStyleSheets(scene, BaseConfig.getMainScenePath())
                && addStyleSheets(scene, BaseConfig.getStartFormPath())
                && addStyleSheets(scene, BaseConfig.getLeftBarPath())
                && addStyleSheets(scene, BaseConfig.getMainFormPath())
                && addStyleSheets(scene, BaseConfig.getListNotesPath());
    }

    public static boolean loadCustomDirectoryStyles(Scene scene) {
        return addStyleSheets(scene, BaseConfig.getCustomDirectoryDialog());
    }

    public static boolean loadNoteDetailsDialogStyles(Scene scene) {
        return addStyleSheets(scene, BaseConfig.getListNotesPath());
    }

    private static boolean addStyleSheets(Scene scene, String path) {

        try {
            URL styleResource = LoadStyles.class.getResource(path);
            if (styleResource == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            String styleSheet = styleResource.toExternalForm();
            scene.getStylesheets().add(styleSheet);
            return true;
        } catch (Exception e) {
            //TODO: Add global exception handler
            handleException(e);
            return false;
        }
    }

    private static void handleException(Exception e) {
        FilesManager.registerException(e);
    }
}

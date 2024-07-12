package start.notatki.moje.mojenotatki.Config;

import javafx.scene.Scene;

import java.util.Objects;

public class LoadStyles {
    public static void loadMainStyles(Scene scene) {


        scene.getStylesheets().add(Objects.requireNonNull(LoadStyles.class.getResource(BaseConfig.getMainScenePath())).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(LoadStyles.class.getResource(BaseConfig.getStartFormPath())).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(LoadStyles.class.getResource(BaseConfig.getLeftBarPath())).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(LoadStyles.class.getResource(BaseConfig.getMainFormPath())).toExternalForm());
    }

    public static void loadCustomDirectoryStyles(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(LoadStyles.class.getResource(BaseConfig.getCustomDirectoryDialog())).toExternalForm());
    }
}

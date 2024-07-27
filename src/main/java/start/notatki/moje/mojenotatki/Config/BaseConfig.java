package start.notatki.moje.mojenotatki.Config;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class BaseConfig {

    private static final String PROPERTIES_FILE = "/stylePaths.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = BaseConfig.class.getResourceAsStream(PROPERTIES_FILE)) {

            properties.load(input);
        } catch (IOException ex) {
            FilesManager.registerException(ex);
        }
    }

    private static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getIconPath() {
        return getProperty("icon");
    }

    public static String getLeftBarPath() {
        return getProperty("leftBar");
    }

    public static String getMainFormPath() {
        return getProperty("mainForm");
    }

    public static String getMainScenePath() {
        return getProperty("mainScene");
    }

    public static String getStartFormPath() {
        return getProperty("startForm");
    }

    public static String getListNotesPath() {
        return getProperty("listNotes");
    }

    public static String getCustomDirectoryDialog() {
        return getProperty("customDirectoryDialog");
    }

    public static void checkOrSetOutputDirectory(Stage stage) {

    }

    public static Image getIcon() {

        String iconPath = getIconPath();
        Image image = null;

        try {

            URL resource = BaseConfig.class.getResource(iconPath);
            if (resource == null) {
                throw new IllegalAccessException("Icon resource not found: " + iconPath);
            }
            image = new Image(resource.toExternalForm());
        } catch (IllegalStateException | NullPointerException exc) {
            FilesManager.registerException(exc);
            System.err.println("Error loading icon: " + iconPath);
        } catch (Exception exc) {
            FilesManager.registerException(exc);
            System.err.println("Unexpected error loading icon: " + iconPath);
        }

        return image;
    }
}

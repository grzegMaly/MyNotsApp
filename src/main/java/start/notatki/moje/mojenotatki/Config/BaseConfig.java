package start.notatki.moje.mojenotatki.Config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
}

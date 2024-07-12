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

            //Fixme: Custom Exception
            ex.printStackTrace();
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

    public static String getOutputDirectory() {
        return getProperty("outputDirectory");
    }

    public static String getCustomDirectoryDialog() {
        return getProperty("customDirectoryDialog");
    }

    public static void setOutputDirectory(String absolutePath) {

        Path path = Path.of(absolutePath);
        if (Files.exists(path) && Files.isDirectory(path)) {
            properties.setProperty("path", path.toAbsolutePath().toString());

            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PROPERTIES_FILE))) {
                properties.store(writer, "Updated output directory");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

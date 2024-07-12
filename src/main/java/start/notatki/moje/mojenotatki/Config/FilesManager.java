package start.notatki.moje.mojenotatki.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class FilesManager {

    private static final String PROPERTIES_FILE = "/configFiles.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = FilesManager.class.getResourceAsStream(PROPERTIES_FILE)) {

            properties.load(input);
        } catch (IOException ex) {

            //Fixme: Custom Exception
            ex.printStackTrace();
        }
    }

    private static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static Path getConfigFile() {
        String configDir = getProperty("configFile.dir");
        String configFile = getProperty("configFile.fileName");
        return Paths.get(Path.of("").toAbsolutePath().toString(), configDir, configFile).normalize();
    }

    public static String getSaveNotesPath() {

        return findValueInConfigFile(getConfigFile(), "notesDirectory");
    }

    public static boolean checkNotesDirectoryExistence() {

        Path path = getConfigFile();

        String notesDirectoryPath = findValueInConfigFile(path, "notesDirectory");

        if (notesDirectoryPath != null) {
            Path notesDirectory = Path.of(notesDirectoryPath);
            return Files.exists(notesDirectory) && Files.isDirectory(notesDirectory);
        }

        return false;
    }

    private static String findValueInConfigFile(Path configFilePath, String key) {

        try (BufferedReader reader = Files.newBufferedReader(configFilePath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key)) {
                    String[] values = line.split("=");
                    if (values.length > 1) {
                        return values[1].trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setConfigKey(String key, String value) {

        Path path = getConfigFile();

        StringBuilder lines = new StringBuilder();
        boolean keyExists = false;

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(key)) {
                    lines.append(key).append("=").append(value).append(System.lineSeparator());
                    keyExists = true;
                } else {
                    lines.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!keyExists) {
            lines.append(key).append("=").append(value).append(System.lineSeparator());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(lines.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

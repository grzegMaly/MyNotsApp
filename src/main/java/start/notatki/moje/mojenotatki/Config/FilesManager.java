package start.notatki.moje.mojenotatki.Config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.RegularNote;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class FilesManager {

    private static final String PROPERTIES_FILE = "/configFiles.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = FilesManager.class.getResourceAsStream(PROPERTIES_FILE)) {

            properties.load(input);
        } catch (IOException ex) {
            //Ignore
        }
    }

    private static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static Path getPath(String... elements) {
        return Paths.get(Path.of("").toAbsolutePath().toString(), elements);
    }

    private static Path getConfigFilePath() {

        String configDir = getProperty("configFile.dir");
        String configFile = getProperty("configFile.fileName");

        return getPath(configDir, configFile);
    }

    public static String getSaveNotesPath() {

        return findValueInFile(getConfigFilePath(), "notesDirectory");
    }

    public static boolean checkNotesDirectoryExistence() {

        Path path = getConfigFilePath();

        String notesDirectoryPath = findValueInFile(path, "notesDirectory");

        if (notesDirectoryPath != null) {
            Path notesDirectory = Path.of(notesDirectoryPath);
            return Files.exists(notesDirectory) && Files.isDirectory(notesDirectory);
        }

        return false;
    }

    private static String findValueInFile(Path configFilePath, String key) {

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
            FilesManager.registerException(e);
        }

        return null;
    }

    public static void setConfigKey(String key, String value) {

        Path path = getConfigFilePath();

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
            FilesManager.registerException(e);
        }

        if (!keyExists) {
            lines.append(key).append("=").append(value).append(System.lineSeparator());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(lines.toString());
        } catch (IOException e) {
            FilesManager.registerException(e);
        }

        try {
            Files.createDirectories(Path.of(value));
        } catch (IOException e) {
            FilesManager.registerException(e);
        }
    }

    public static void log(String exc) {

        String logsDir = getProperty("logs.dir");
        String logsFile = getProperty("logs.fileName");

        Path path = getPath(logsDir, logsFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write("Info: " + exc);
            writer.newLine();
        } catch (IOException e) {
            //Ignore
        }
    }

    public static void registerException(Exception exc) {

        String logsDir = getProperty("logs.dir");
        String logsFile = getProperty("logs.fileName");

        Path path = getPath(logsDir, logsFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write("Exception: " + exc.getClass().getName());
            writer.newLine();
            writer.write("Message: " + exc.getMessage());
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            //Ignore
        }
    }

    public static ObservableList<BaseNote> loadNotes() {

        String path = getSaveNotesPath();
        List<BaseNote> notes = new ArrayList<>();

        try (Stream<Path> files = Files.list(Path.of(path))) {

            List<Path> filePaths = files
                    .filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .toList();

            for (Path filePath : filePaths) {
                try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                    String title = reader.readLine();
                    String noteType = reader.readLine();
                    LocalDate createdDate = LocalDate.parse(reader.readLine());
                    String categoryPriority = reader.readLine();
                    LocalDate deadline = noteType.equals("Deadline Note") ? LocalDate.parse(reader.readLine()) : null;

                    StringBuilder contentBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.equals("Content:")) {
                            break;
                        }
                    }
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line).append(System.lineSeparator());
                    }

                    String content = contentBuilder.toString().trim();

                    if (noteType.equals("Regular Note")) {
                        BaseNote note = new RegularNote(title, content, noteType, categoryPriority);
                        note.setCreatedDate(createdDate);
                        notes.add(note);
                    } else if (noteType.equals("Deadline Note")) {
                        BaseNote note = new DeadlineNote(title, content, noteType, categoryPriority, deadline);
                        note.setCreatedDate(createdDate);
                        notes.add(note);
                    }
                }
            }

        } catch (IOException e) {
            FilesManager.registerException(e);
            return null;
        }
        notes.removeIf(Objects::isNull);
        return FXCollections.observableArrayList(notes);
    }
}

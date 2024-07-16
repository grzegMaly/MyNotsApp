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
import java.util.stream.Collectors;
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
        List<List<String>> stringNotes = new ArrayList<>();

        try (Stream<Path> files = Files.list(Path.of(path))) {

            Set<String> fileNames = files
                    .filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .map(f -> f.getFileName().toString())
                    .collect(Collectors.toSet());

            for (String filesName : fileNames) {
                stringNotes.add(Files.readAllLines(Path.of(path, filesName)));
            }

        } catch (IOException e) {
            FilesManager.registerException(e);
            return null;
        }

        List<BaseNote> notes = stringNotes.stream()
                .map(n -> {
                    if (n.get(1).equals("Regular Note")) {
                        BaseNote note = new RegularNote(n.get(0), n.get(n.size() - 1), n.get(1), n.get(3));
                        note.setCreatedDate(LocalDate.parse(n.get(2)));
                        return note;
                    } else if (n.get(1).equals("Deadline Note")) {
                        BaseNote note = new DeadlineNote(n.get(0), n.get(n.size() - 1), n.get(1), n.get(3), LocalDate.parse(n.get(4)));
                        note.setCreatedDate(LocalDate.parse(n.get(2)));
                        return note;
                    }
                    return null;
                })
                .collect(Collectors.toList());
        notes.removeIf(Objects::isNull);
        return FXCollections.observableArrayList(notes);
    }
}

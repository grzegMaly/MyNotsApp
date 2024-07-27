package start.notatki.moje.mojenotatki.Model.Request;

import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.DeadlineNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.RegularNoteRequest;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.utils.DirectoryUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NoteRequestModel {

    private Path path;
    private BaseNote note;
    private final String extension = ".txt";
    private final StringBuilder sb = new StringBuilder();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public boolean toSave(BaseNoteRequest req) {

        Future<Boolean> future = executor.submit(() -> {
            DirectoryUtils.checkOrSetOutputDirectory();
            return FilesManager.checkNotesDirectoryExistence();
        });

        boolean directoryExists;
        try {
            directoryExists = future.get();
        } catch (ExecutionException | InterruptedException e) {
            FilesManager.registerException(e);
            return false;
        }

        if (!directoryExists) {
            return false;
        }

        note = req.getOriginalInstance();

        if (note != null) {
            if (note.getTitle().equals(req.getTitle())) {
                path = Paths.get(FilesManager.getSaveNotesPath(), note.getTitle() + extension);
            } else {
                path = Paths.get(FilesManager.getSaveNotesPath(), req.getTitle() + extension);
                deleteFile(Paths.get(FilesManager.getSaveNotesPath(), note.getTitle() + extension));
            }
            convertContent(req, note);
        } else {
            path = Paths.get(FilesManager.getSaveNotesPath(), req.getTitle() + extension);
            convertContent(req, null);
        }
        return save(path);
    }

    private boolean save(Path path) {

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            FilesManager.registerException(e);
        }

        sb.setLength(0);
        return true;
    }

    public void deleteFile(BaseNote note) {
        String title = note.getTitle() + extension;
        Path path = Paths.get(FilesManager.getSaveNotesPath(), title);
        deleteFile(path);
    }

    private void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            FilesManager.registerException(e);
        }
    }

    private void convertContent(BaseNoteRequest req, BaseNote originalInstance) {

        sb.append(req.getTitle()).append("\n");
        sb.append(req.getNoteType()).append("\n");

        if (originalInstance != null) {
            sb.append(originalInstance.getCreatedDate()).append("\n");
        } else {
            sb.append(req.getNote().getCreatedDate()).append("\n");
        }

        if (req instanceof DeadlineNoteRequest r) {
            sb.append(r.getPriority()).append("\n");
            sb.append(r.getDeadline()).append("\n");
        } else if (req instanceof RegularNoteRequest r) {
            sb.append(r.getCategory()).append("\n");
        }

        sb.append("\n");
        sb.append("Content:\n");
        sb.append(req.getContent());
    }
}

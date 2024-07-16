package start.notatki.moje.mojenotatki.Model.Request;

import javafx.util.Pair;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.DeadlineNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.RegularNoteRequest;
import start.notatki.moje.mojenotatki.MyNotesApp;
import start.notatki.moje.mojenotatki.Note.BaseNote;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NoteRequestModel {

    private final StringBuilder sb = new StringBuilder();
    private Path path;
    private BaseNote note;

    public boolean toSave(BaseNoteRequest req) {

        note = req.getOriginalInstance();
        String extension = ".txt";

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
        }

        convertContent(req, null);
        return save(path);
    }

    private boolean save(Path path) {

        if (!FilesManager.checkNotesDirectoryExistence()) {
            MyNotesApp.checkOrSetOutputDirectory();
            System.out.println(FilesManager.checkNotesDirectoryExistence());
        }

        if (!FilesManager.checkNotesDirectoryExistence()) {
            return false;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            FilesManager.registerException(e);
        }

        sb.setLength(0);
        return true;
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
        sb.append(req.getContent());
    }
}

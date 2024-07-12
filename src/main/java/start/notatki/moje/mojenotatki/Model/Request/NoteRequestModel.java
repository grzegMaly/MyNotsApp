package start.notatki.moje.mojenotatki.Model.Request;

import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.DeadlineNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.RegularNoteRequest;
import start.notatki.moje.mojenotatki.Note.BaseNote;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NoteRequestModel {

    private final StringBuilder sb = new StringBuilder();
    BaseNote note;

    public void save(BaseNoteRequest req) {

        if (!FilesManager.checkNotesDirectoryExistence()) {
            return;
        }

        String title = req.getTitle() + ".txt";
        Path path = Paths.get(FilesManager.getSaveNotesPath(), title);

        convertContent(req);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sb.setLength(0);
    }

    private void convertContent(BaseNoteRequest req) {

        note = req.getNote();
        sb.append(note.getTitle()).append("\n");
        sb.append(note.getNoteType()).append("\n");
        sb.append(note.getCreatedDate()).append("\n");

        if (req instanceof DeadlineNoteRequest r) {
            sb.append(r.getPriority()).append("\n");
            sb.append(r.getDeadline()).append("\n");
        } else if (req instanceof RegularNoteRequest r) {
            sb.append(r.getCategory()).append("\n");
        }

        sb.append("\n".repeat(3));
        sb.append(note.getContent());
    }
}

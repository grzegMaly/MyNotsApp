package start.notatki.moje.mojenotatki.Note;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class BaseNote {

    private String title;
    private String content;
    private String noteType;
    private final LocalDate createdDate = LocalDate.now();

    public BaseNote(String title, String content, String noteType) {
        this.title = title;
        this.content = content;
        this.noteType = noteType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoteType() {
        return noteType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}

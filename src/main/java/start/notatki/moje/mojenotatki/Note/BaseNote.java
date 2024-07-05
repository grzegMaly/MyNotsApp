package start.notatki.moje.mojenotatki.Note;

import java.time.LocalDateTime;

public abstract class BaseNote {

    private String title;
    private String content;
    private String noteType;
    private final LocalDateTime createdDate = LocalDateTime.now();

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoteType() {
        return noteType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}

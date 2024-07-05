package start.notatki.moje.mojenotatki.Note;

import java.time.LocalDateTime;

public abstract class BaseNote {

    private String title;
    private String content;
    private final LocalDateTime createdDate = LocalDateTime.now();


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}

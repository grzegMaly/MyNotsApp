package start.notatki.moje.mojenotatki.Note;

import java.time.LocalDateTime;

public abstract class BaseNote {

    private String title;
    private String content;
    private final LocalDateTime createdDate = LocalDateTime.now();

    public BaseNote(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

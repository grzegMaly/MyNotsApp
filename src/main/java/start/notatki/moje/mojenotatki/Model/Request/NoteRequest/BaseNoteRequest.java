package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;


import start.notatki.moje.mojenotatki.Note.BaseNote;

public abstract class BaseNoteRequest {

    private final String title;
    private final String noteType;
    private final String content;

    public BaseNoteRequest(String title, String noteType, String content) {
        this.title = title;
        this.noteType = noteType;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteType() {
        return noteType;
    }

    public String getContent() {
        return content;
    }

    public abstract BaseNote getNote();

    @Override
    public String toString() {
        return "BaseNoteRequest{" +
                "title='" + title + '\'' +
                ", noteType='" + noteType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

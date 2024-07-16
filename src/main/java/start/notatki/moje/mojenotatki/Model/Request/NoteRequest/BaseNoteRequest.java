package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;


import start.notatki.moje.mojenotatki.Note.BaseNote;

public abstract class BaseNoteRequest {

    private String title;
    private String noteType;
    private String content;
    protected BaseNote note = null;

    public BaseNoteRequest(String title, String noteType, String content) {
        this.title = title;
        this.noteType = noteType;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BaseNote getOriginalInstance() {
        return note;
    }

    public void setNote(BaseNote note) {
        this.note = note;
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

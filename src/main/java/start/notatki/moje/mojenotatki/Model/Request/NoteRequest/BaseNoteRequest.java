package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;


public abstract class BaseNoteRequest {

    private final String title;
    private final String noteType;
    private final String content;

    public BaseNoteRequest(String title, String noteType, String content) {
        this.title = title;
        this.noteType = noteType;
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseNoteRequest{" +
                "title='" + title + '\'' +
                ", noteType='" + noteType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

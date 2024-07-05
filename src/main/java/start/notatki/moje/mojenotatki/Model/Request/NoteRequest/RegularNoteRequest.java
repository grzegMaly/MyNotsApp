package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;

public class RegularNoteRequest extends BaseNoteRequest {

    private final String category;

    public RegularNoteRequest(String title, String noteType, String content, String category) {
        super(title, noteType, content);
        this.category = category;
    }

    @Override
    public String toString() {
        return "RegularNoteRequest{" +
                "category='" + category + '\'' +
                "} " + super.toString();
    }
}

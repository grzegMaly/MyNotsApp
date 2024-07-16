package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;

import start.notatki.moje.mojenotatki.Note.RegularNote;

public class RegularNoteRequest extends BaseNoteRequest {

    private String category;

    public RegularNoteRequest(String title, String noteType, String content, String category) {
        super(title, noteType, content);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public RegularNote getNote() {

        return new RegularNote(
                this.getTitle(),
                this.getContent(),
                this.getNoteType(),
                category
        );
    }

    @Override
    public String toString() {
        return "RegularNoteRequest{" +
                "category='" + category + '\'' +
                "} " + super.toString();
    }
}

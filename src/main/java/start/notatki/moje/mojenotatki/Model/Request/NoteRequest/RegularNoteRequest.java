package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;

import start.notatki.moje.mojenotatki.Note.RegularNote;

public class RegularNoteRequest extends BaseNoteRequest {

    private final String category;

    public RegularNoteRequest(String title, String noteType, String content, String category) {
        super(title, noteType, content);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public RegularNote getNote() {
        return new RegularNote(
                this.getTitle(),
                this.getContent(),
                this.category,
                RegularNote.Category.valueOf(category.toUpperCase())
        );
    }

    @Override
    public String toString() {
        return "RegularNoteRequest{" +
                "category='" + category + '\'' +
                "} " + super.toString();
    }
}

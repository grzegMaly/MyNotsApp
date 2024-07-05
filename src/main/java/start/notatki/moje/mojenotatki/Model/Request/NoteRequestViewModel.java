package start.notatki.moje.mojenotatki.Model.Request;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NoteRequestViewModel {

    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty noteType = new SimpleStringProperty("");
    private final StringProperty categoryPriority = new SimpleStringProperty("");
    private final StringProperty content = new SimpleStringProperty("");
    private Boolean regularNote = true;

    NoteRequestConverter converter = new NoteRequestConverter();

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getNoteType() {
        return noteType.get();
    }

    public StringProperty noteTypeProperty() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType.set(noteType);
        if (!noteType.equals("Regular Note")) {
            regularNote = false;
        }
    }

    public String getCategoryPriority() {
        return categoryPriority.get();
    }

    public StringProperty categoryPriorityProperty() {
        return categoryPriority;
    }

    public void setCategoryPriority(String categoryPriority) {
        this.categoryPriority.set(categoryPriority);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void save() {
        NoteRequest data = converter.toNoteRequest(this, regularNote);
    }
}

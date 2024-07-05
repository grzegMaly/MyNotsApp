package start.notatki.moje.mojenotatki.Model.Request;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;

public class NoteRequestViewModel {

    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty noteType = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private final StringProperty priority = new SimpleStringProperty("");
    private final StringProperty deadlineDate = new SimpleStringProperty("");
    private final StringProperty content = new SimpleStringProperty("");
    private Boolean regularNote = true;

    NoteRequestConverter converter = new NoteRequestConverter();
    NoteRequestModel model = new NoteRequestModel();

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
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public String getDeadlineDate() {
        return deadlineDate.get();
    }

    public StringProperty deadlineDateProperty() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate.set(deadlineDate);
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
        BaseNoteRequest data = converter.toNoteRequest(this, regularNote);
        model.save(data);
        reset();
    }

    public void reset() {

        this.title.set("");
        this.content.set("");
    }

    public void isRegularNote(boolean value) {
        this.regularNote = value;
    }
}

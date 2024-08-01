package start.notatki.moje.mojenotatki.Model.Request;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Note.BaseNote;

public class NoteRequestViewModel {

    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty noteType = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private final StringProperty priority = new SimpleStringProperty("");
    private final StringProperty deadlineDate = new SimpleStringProperty("");
    private final StringProperty content = new SimpleStringProperty("");

    private final SimpleObjectProperty<BaseNote> originalNote = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<BaseNoteRequest> editedNoteRequest = new SimpleObjectProperty<>();


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

    public BaseNote getOriginalNote() {
        return originalNote.get();
    }

    public void setOriginalNote(BaseNote originalNote) {
        this.originalNote.set(originalNote);
        this.editedNoteRequest.set(converter.toNoteRequest(originalNote));
    }

    public BaseNoteRequest getEditedNoteRequest() {
        return editedNoteRequest.get();
    }

    public void setEditedNoteRequest(BaseNoteRequest editedNoteRequest) {
        this.editedNoteRequest.set(editedNoteRequest);
    }

    public void save() {
        BaseNoteRequest data = converter.toNoteRequest(this);

        model.toSave(data).thenAccept(result -> {
            if (result) {
                Platform.runLater(this::reset);
            }
        }).exceptionally(ex -> {
            FilesManager.registerException((Exception) ex);
            return null;
        });
    }

    public void update(BaseNoteRequest data) {

        model.toSave(data).thenAccept(result -> {
            if (result) {
                Platform.runLater(this::reset);
            }
        }).exceptionally(ex -> {
            FilesManager.registerException((Exception) ex);
            return null;
        });
    }

    public void reset() {

        this.title.set("");
        this.content.set("");
    }
}

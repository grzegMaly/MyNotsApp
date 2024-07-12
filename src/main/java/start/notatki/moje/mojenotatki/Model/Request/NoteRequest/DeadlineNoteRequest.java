package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;

import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;

import java.time.LocalDate;

public class DeadlineNoteRequest extends BaseNoteRequest {

    private final String priority;
    private final String deadline;

    public DeadlineNoteRequest(String title, String noteType, String content, String priority, String deadline) {
        super(title, noteType, content);
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public DeadlineNote getNote() {
        return new DeadlineNote(
                this.getTitle(),
                this.getContent(),
                this.getNoteType(),
                DeadlineNote.Priority.valueOf(priority.toUpperCase()),
                LocalDate.parse(deadline)
        );
    }

    @Override
    public String toString() {
        return "DeadlineNoteRequest{" +
                "priority='" + priority + '\'' +
                ", deadline='" + deadline + '\'' +
                "} " + super.toString();
    }
}

package start.notatki.moje.mojenotatki.Note;


import java.time.LocalDateTime;

public class DeadlineNote extends BaseNote {

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    private LocalDateTime deadline;
    private Priority priority;

    public DeadlineNote(String title, String content, LocalDateTime deadline) {
        this(title, content, deadline, null);
    }

    public DeadlineNote(String title, String content, LocalDateTime deadline, Priority priority) {
        super(title, content);
        this.deadline = deadline;
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

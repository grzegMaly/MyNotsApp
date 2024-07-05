package start.notatki.moje.mojenotatki.Note;


import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class DeadlineNote extends BaseNote {

    public enum Priority {
        HIGH, MEDIUM, LOW;

        public static List<String> getNames() {
            return EnumSet.allOf(Priority.class)
                    .stream()
                    .map(s -> s.name().charAt(0) + s.name().substring(1).toLowerCase())
                    .collect(Collectors.toList());
        }
    }

    private Priority priority;
    private LocalDateTime deadline;

    public DeadlineNote(String priority, LocalDateTime deadline) {
        this.priority = Priority.valueOf(priority);
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = Priority.valueOf(priority.toUpperCase());
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}

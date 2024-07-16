package start.notatki.moje.mojenotatki.Note;


import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class DeadlineNote extends BaseNote {

    public enum Priority {
        HIGH, MEDIUM, LOW;

        public static List<String> getNames() {
            return EnumSet.allOf(Priority.class)
                    .stream()
                    .map(BaseNote::convertEnumToString)
                    .collect(Collectors.toList());
        }
    }

    private Priority priority;
    private LocalDate deadline;

    public DeadlineNote(String title, String content, String noteType, String priority, LocalDate deadline) {
        super(title, content, noteType);
        this.priority = convertStringToEnum(Priority.class, priority);
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = Priority.valueOf(priority.toUpperCase());
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String getEnumValueName() {
        return BaseNote.convertEnumToString(priority);
    }
}

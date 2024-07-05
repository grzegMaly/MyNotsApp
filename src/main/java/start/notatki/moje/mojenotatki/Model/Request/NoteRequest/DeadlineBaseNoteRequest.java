package start.notatki.moje.mojenotatki.Model.Request.NoteRequest;

public class DeadlineBaseNoteRequest extends BaseNoteRequest {

    private final String priority;
    private final String deadline;

    public DeadlineBaseNoteRequest(String title, String noteType, String content, String priority, String deadline) {
        super(title, noteType, content);
        this.priority = priority;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "DeadlineBaseNoteRequest{" +
                "priority='" + priority + '\'' +
                ", deadline='" + deadline + '\'' +
                "} " + super.toString();
    }
}

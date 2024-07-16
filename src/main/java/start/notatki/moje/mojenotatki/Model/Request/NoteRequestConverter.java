package start.notatki.moje.mojenotatki.Model.Request;

import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.DeadlineNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.RegularNoteRequest;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.RegularNote;

public class NoteRequestConverter {

    public BaseNoteRequest toNoteRequest(NoteRequestViewModel viewModel) {

        if (viewModel.getNoteType().equals(BaseNote.convertEnumToString(BaseNote.NoteType.REGULAR_NOTE))) {
            return new RegularNoteRequest(
                    viewModel.getTitle(),
                    viewModel.getNoteType(),
                    viewModel.getContent(),
                    viewModel.getCategory()
            );
        } else if (viewModel.getNoteType().equals(BaseNote.convertEnumToString(BaseNote.NoteType.DEADLINE_NOTE))) {
            return new DeadlineNoteRequest(
                    viewModel.getTitle(),
                    viewModel.getNoteType(),
                    viewModel.getContent(),
                    viewModel.getPriority(),
                    viewModel.getDeadlineDate()
            );
        }

        return null;
    }

    public BaseNoteRequest toNoteRequest(BaseNote note) {

        if (note instanceof RegularNote regularNote) {
            return new RegularNoteRequest(
                    regularNote.getTitle(),
                    regularNote.getNoteType(),
                    regularNote.getContent(),
                    BaseNote.convertEnumToString(regularNote.getCategory())
            );
        } else if (note instanceof DeadlineNote deadlineNote){
            return new DeadlineNoteRequest(
                    deadlineNote.getTitle(),
                    deadlineNote.getNoteType(),
                    deadlineNote.getContent(),
                    BaseNote.convertEnumToString(deadlineNote.getPriority()),
                    deadlineNote.getDeadline().toString()
            );
        }
        return null;
    }
}

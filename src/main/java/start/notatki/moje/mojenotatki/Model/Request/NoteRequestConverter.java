package start.notatki.moje.mojenotatki.Model.Request;

import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.DeadlineNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.RegularNoteRequest;

public class NoteRequestConverter {

    public BaseNoteRequest toNoteRequest(NoteRequestViewModel viewModel, Boolean regularNote) {

        if (regularNote) {
            return new RegularNoteRequest(
                    viewModel.getTitle(),
                    viewModel.getNoteType(),
                    viewModel.getContent(),
                    viewModel.getCategory()
            );
        } else {
            return new DeadlineNoteRequest(
                    viewModel.getTitle(),
                    viewModel.getNoteType(),
                    viewModel.getContent(),
                    viewModel.getPriority(),
                    viewModel.getDeadlineDate()
            );
        }
    }
}

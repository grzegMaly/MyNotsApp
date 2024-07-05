package start.notatki.moje.mojenotatki.Model.Request;

import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;

public class NoteRequestModel {

    public void save(BaseNoteRequest req) {
        System.out.println("Saving " + req);
    }
}

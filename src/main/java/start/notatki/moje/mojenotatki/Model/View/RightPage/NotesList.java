package start.notatki.moje.mojenotatki.Model.View.RightPage;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequestViewModel;
import start.notatki.moje.mojenotatki.Model.View.MainScene;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.RegularNote;
import start.notatki.moje.mojenotatki.NoteDetailsDialog;

import java.util.ArrayList;
import java.util.List;


public class NotesList extends VBox {

    private final MainScene mainScene;
    ObservableList<BaseNote> notes;
    private final Button cancelButton = new Button("Cancel");
    private final Button reloadButton = new Button("Reload");
    private final HBox btnBar = new HBox();

    private final TableView<BaseNote> tblNotes = new TableView<>();
    private final TableColumn<BaseNote, String> colTitle = new TableColumn<>("Title");
    private final TableColumn<BaseNote, String> colType = new TableColumn<>("Type");
    private final TableColumn<BaseNote, String> colCategoryPriority = new TableColumn<>("Category/Priority");
    private final TableColumn<BaseNote, String> colDeadline = new TableColumn<>("Deadline");


    public NotesList(MainScene mainScene) {

        this.getStyleClass().add("thisNotesList");

        this.mainScene = mainScene;
        loadTableView();
        loadNotes();
        loadElements();
        this.getChildren().addAll(btnBar, tblNotes);
    }

    private void loadElements() {

        btnBar.getChildren().addAll(reloadButton, cancelButton);
        btnBar.getStyleClass().add("lnBtnBar");

        ButtonBar.setButtonData(cancelButton, ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonBar.setButtonData(reloadButton, ButtonBar.ButtonData.OTHER);

        for (var button : btnBar.getChildren()) {

            Button button1 = (Button) button;

            button1.getStyleClass().add("ln-button");
        }

        tblNotes.setRowFactory(evt -> {
            TableRow<BaseNote> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BaseNote rowData = row.getItem();
                    NoteDetailsDialog.getInstance().show(rowData, this);
                }
            });
            return row;
        });
    }

    private void loadNotes() {

        notes = FilesManager.loadNotes();
        assert notes != null;
        tblNotes.getItems().addAll(notes);
    }

    private void loadTableView() {

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colType.setCellValueFactory(new PropertyValueFactory<>("noteType"));

        colCategoryPriority.setCellValueFactory(cellData -> {
            BaseNote note = cellData.getValue();

            if (note instanceof RegularNote n) {
                return new ReadOnlyStringWrapper(BaseNote.convertEnumToString(n.getCategory()));
            } else if (note instanceof DeadlineNote n) {
                return new ReadOnlyStringWrapper(BaseNote.convertEnumToString(n.getPriority()));
            }

            return new ReadOnlyStringWrapper("");
        });

        colDeadline.setCellValueFactory(cellData -> {
            BaseNote note = cellData.getValue();
            if (note instanceof DeadlineNote n) {
                return new ReadOnlyStringWrapper(n.getDeadline().toString());
            }

            return new ReadOnlyStringWrapper("-");
        });

        tblNotes.getColumns().addAll(colTitle, colType, colCategoryPriority, colDeadline);
        tblNotes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        reloadButton.setOnAction(event -> reloadNotes());
    }

    public void reloadNotes() {
        tblNotes.getItems().clear();
        loadNotes();
    }
}

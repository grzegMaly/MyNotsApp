package start.notatki.moje.mojenotatki.Model.View.RightPage;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequestModel;
import start.notatki.moje.mojenotatki.Model.View.MainScene;
import start.notatki.moje.mojenotatki.Model.View.Page;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.RegularNote;
import start.notatki.moje.mojenotatki.NoteDetailsDialog;
import start.notatki.moje.mojenotatki.utils.ExecutorServiceManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class NotesList extends VBox implements Page {

    private ObservableList<BaseNote> notes;
    private final MainScene mainScene;
    private final Button cancelButton = new Button("Cancel");
    private final Button reloadButton = new Button("Reload");
    private final HBox btnBar = new HBox();

    private final TableView<BaseNote> tblNotes = new TableView<>();
    private final TableColumn<BaseNote, String> colTitle = new TableColumn<>("Title");
    private final TableColumn<BaseNote, String> colType = new TableColumn<>("Type");
    private final TableColumn<BaseNote, String> colCategoryPriority = new TableColumn<>("Category/Priority");
    private final TableColumn<BaseNote, String> colDeadline = new TableColumn<>("Deadline");

    private final NoteRequestModel model = new NoteRequestModel();
    private final ExecutorService executor =
            ExecutorServiceManager.createCachedThreadPool(this.getClass().getSimpleName());

    public NotesList(MainScene mainScene) {
        this.mainScene = mainScene;
    }

    @Override
    public void loadPage() {

        CountDownLatch latch = new CountDownLatch(3);

        executor.submit(() -> {
            this.getStyleClass().add("thisNotesList");
            latch.countDown();
        });

        executor.submit(() -> {
            loadElements();
            latch.countDown();
        });

        executor.submit(() -> {
            loadTableView();
            latch.countDown();
        });

        try {
            latch.await();
            Platform.runLater(() -> this.getChildren().addAll(btnBar, tblNotes));
        } catch (InterruptedException e) {
            FilesManager.registerException(e);
            Thread.currentThread().interrupt();
        }
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
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!row.isEmpty())) {
                    BaseNote rowData = row.getItem();
                    NoteDetailsDialog.getInstance().show(rowData, this);
                } else if (event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                    showContextMenu(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    private void showContextMenu(TableRow<BaseNote> row, double sceneX, double sceneY) {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");

        deleteItem.setOnAction(evt -> {
            BaseNote note = row.getItem();
            notes.remove(note);
            tblNotes.getItems().remove(note);
            model.deleteFile(note);
        });

        contextMenu.getItems().add(deleteItem);
        contextMenu.show(row, sceneX, sceneY);
    }

    public void loadNotes() {

        notes = FilesManager.loadNotes();
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

        tblNotes.getColumns().addAll(List.of(colTitle, colType, colCategoryPriority, colDeadline));
        tblNotes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        reloadButton.setOnAction(event -> reloadNotes());
        cancelButton.setOnAction(event -> cancel());
    }

    public void reloadNotes() {
        tblNotes.getItems().clear();
        loadNotes();
    }

    public void cancel() {
        mainScene.useNotesList(false);
    }
}

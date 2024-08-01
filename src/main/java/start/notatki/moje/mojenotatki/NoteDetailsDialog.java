package start.notatki.moje.mojenotatki;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Config.LoadStyles;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequestConverter;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequestViewModel;
import start.notatki.moje.mojenotatki.Model.View.Page;
import start.notatki.moje.mojenotatki.Model.View.RightPage.NotesList;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.RegularNote;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NoteDetailsDialog extends Stage implements Page {

    private VBox vBox;

    private HBox hbTitle;
    private Label lblTitle1;
    private Label lblTitle2;
    private TextField tfTitle;
    private StackPane spTitle;

    private HBox hbType;
    private Label lblType1;
    private Label lblType2;
    private ComboBox<String> cbType;
    private StackPane spType;

    private Label lblCreatedDate;

    private HBox hbCategoryPriority;
    private Label lblCategoryPriority1;
    private Label lblCategoryPriority2;
    private ComboBox<String> cbCategoryPriority;
    private StackPane spCategoryPriority;

    private HBox hbDeadline;
    private Label lblDeadline1;
    private Label lblDeadline2;
    private DatePicker dpDeadline;
    private StackPane spDeadline;

    private TextArea taContent;

    private Button editButton;
    private Button saveButton;
    private Button cancelButton;

    private static final NoteDetailsDialog dialog = new NoteDetailsDialog();

    private final ObservableList<String> noteTypes = FXCollections.observableArrayList(BaseNote.NoteType.getNames());
    private final ObservableList<String> categories = FXCollections.observableArrayList(RegularNote.Category.getNames());
    private final ObservableList<String> priorities = FXCollections.observableArrayList(DeadlineNote.Priority.getNames());

    private NotesList notesList;
    private NoteRequestViewModel viewModel;
    private BaseNote note;
    private boolean loaded = false;

    private NoteDetailsDialog() {

    }

    public static NoteDetailsDialog getInstance() {
        return dialog;
    }

    @Override
    public void loadPage() {

        initializeComponents();
        Scene scene = new Scene(vBox);
        LoadStyles.loadNoteDetailsDialogStyles(scene);
        this.setScene(scene);
        this.setResizable(false);
        this.setTitle("Note Details");
    }

    public void setNote(BaseNote note) {
        this.note = note;
    }

    public void setNotesList(NotesList notesList) {
        this.notesList = notesList;
    }

    private void initialCheckBoxes(BaseNote note) {

        cbType.setValue(note.getNoteType());
        cbType.setOnAction(evt -> updateCategoryPriority(note));
        updateCategoryPriority(note);
    }

    private void initializeComponents() {
        vBox = new VBox();
        HBox buttons = new HBox();
        loadButtonsAndEvents(buttons);

        hbTitle = new HBox();
        spTitle = new StackPane();
        lblTitle1 = new Label();
        lblTitle2 = new Label();
        tfTitle = new TextField();
        spTitle.getChildren().addAll(tfTitle, lblTitle2);
        spTitle.setAlignment(Pos.CENTER_LEFT);
        hbTitle.getChildren().addAll(lblTitle1, spTitle);

        hbType = new HBox();
        spType = new StackPane();
        lblType1 = new Label();
        lblType2 = new Label();
        cbType = new ComboBox<>();
        cbType.getItems().addAll(noteTypes);
        spType.getChildren().addAll(cbType, lblType2);
        spType.setAlignment(Pos.CENTER_LEFT);
        hbType.getChildren().addAll(lblType1, spType);

        hbCategoryPriority = new HBox();
        spCategoryPriority = new StackPane();
        lblCategoryPriority1 = new Label();
        lblCategoryPriority2 = new Label();
        cbCategoryPriority = new ComboBox<>();
        spCategoryPriority.getChildren().addAll(cbCategoryPriority, lblCategoryPriority2);
        spCategoryPriority.setAlignment(Pos.CENTER_LEFT);
        hbCategoryPriority.getChildren().addAll(lblCategoryPriority1, spCategoryPriority);

        hbDeadline = new HBox();
        spDeadline = new StackPane();
        lblDeadline1 = new Label();
        lblDeadline2 = new Label();
        dpDeadline = new DatePicker();
        spDeadline.getChildren().addAll(dpDeadline, lblDeadline2);
        spDeadline.setAlignment(Pos.CENTER_LEFT);
        hbDeadline.getChildren().addAll(lblDeadline1, spDeadline);


        lblCreatedDate = new Label();
        taContent = new TextArea();
        taContent.setWrapText(true);
        taContent.setEditable(false);
        taContent.setMinHeight(350);

        vBox.getStyleClass().add("ndd-VBox");
        taContent.getStyleClass().add("ndd-taContent");

        lblTitle1.getStyleClass().add("ndd-label");
        lblTitle2.getStyleClass().add("ndd-label");
        tfTitle.getStyleClass().add("ndd-tfTitle");
        lblType1.getStyleClass().add("ndd-label");
        lblType2.getStyleClass().add("ndd-label");
        cbType.getStyleClass().add("ndd-cbNoteType");
        lblCreatedDate.getStyleClass().add("ndd-label");
        lblDeadline1.getStyleClass().add("ndd-label");
        lblDeadline2.getStyleClass().add("ndd-label");
        lblCategoryPriority1.getStyleClass().add("ndd-label");
        lblCategoryPriority2.getStyleClass().add("ndd-label");
        cbCategoryPriority.getStyleClass().add("ndd-cbNoteType");

        vBox.getChildren().addAll(buttons, hbTitle, hbType, lblCreatedDate, hbCategoryPriority, hbDeadline, taContent);
    }

    private void loadButtonsAndEvents(HBox buttons) {

        editButton = new Button("Edit");
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        editButton.getStyleClass().add("ndd-Button");
        saveButton.getStyleClass().add("ndd-Button");
        cancelButton.getStyleClass().add("ndd-CancelButton");

        buttons.getStyleClass().add("ndd-Buttons");
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttons.getChildren().addAll(editButton, saveButton, spacer, cancelButton);

        editButton.setOnAction(evt -> editVisibility(true));

        saveButton.setOnAction(evt -> saveChanges());

        cancelButton.setOnAction(evt -> close());

    }

    private void updateCategoryPriority(BaseNote note) {

        cbCategoryPriority.getItems().clear();

        if (cbType.getValue().equals(BaseNote.NoteType.REGULAR_NOTE.getName())) {
            cbCategoryPriority.getItems().addAll(categories);

            if (note instanceof RegularNote regularNote) {
                cbCategoryPriority.setValue(BaseNote.convertEnumToString(regularNote.getCategory()));
            } else {
                cbCategoryPriority.setValue(categories.get(0));
            }

            lblDeadline1.setVisible(false);
            spDeadline.setVisible(false);
        } else if (cbType.getValue().equals(BaseNote.NoteType.DEADLINE_NOTE.getName())) {
            cbCategoryPriority.getItems().addAll(priorities);

            if (note instanceof DeadlineNote deadlineNote) {
                cbCategoryPriority.setValue(BaseNote.convertEnumToString(deadlineNote.getPriority()));
            } else {
                cbCategoryPriority.setValue(priorities.get(0));
            }

            if (dpDeadline.getValue() == null) {
                dpDeadline.setValue(LocalDate.now());
            }

            lblDeadline1.setVisible(true);
            spDeadline.setVisible(true);
        }
    }

    private void saveChanges() {

        viewModel.setTitle(!tfTitle.getText().isEmpty()
                && !tfTitle.getText().isBlank() ? tfTitle.getText() : note.getTitle());

        viewModel.setContent(!taContent.getText().isEmpty()
                && !taContent.getText().isBlank() ? taContent.getText() : note.getContent());

        viewModel.setNoteType(cbType.getValue());
        viewModel.setCategory(cbCategoryPriority.getValue());
        viewModel.setPriority(cbCategoryPriority.getValue());


        if (cbType.getValue().equals(BaseNote.NoteType.DEADLINE_NOTE.getName())) {

            DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (viewModel.deadlineDateProperty().get() == null || viewModel.deadlineDateProperty().get().isEmpty()) {
                String saveDate = LocalDate.now().format(saveFormatter);
                viewModel.setDeadlineDate(saveDate);
            }

            dpDeadline.valueProperty().addListener((obs2, oldDate, newDate) -> {
                if (newDate != null) {
                    String saveDate = newDate.format(saveFormatter);
                    viewModel.setDeadlineDate(saveDate);
                }
            });
        }

        NoteRequestConverter converter = new NoteRequestConverter();
        BaseNoteRequest noteRequest = converter.toNoteRequest(viewModel);
        noteRequest.setNote(note);
        viewModel.update(noteRequest);
        notesList.reloadNotes();
    }

    private void editVisibility(Boolean value) {
        lblTitle2.setVisible(!value);
        tfTitle.setVisible(value);

        lblType2.setVisible(!value);
        cbType.setVisible(value);

        lblCategoryPriority2.setVisible(!value);
        cbCategoryPriority.setVisible(value);

        lblDeadline2.setVisible(!value);
        dpDeadline.setVisible(value);

        saveButton.setVisible(value);
        taContent.setEditable(value);
    }

    public void show(BaseNote note, NotesList notesList) {

        if (!loaded) {
            loadPage();
            loaded = !loaded;
        }

        setNote(note);
        setNotesList(notesList);
        initialCheckBoxes(note);

        viewModel = new NoteRequestViewModel();
        viewModel.setOriginalNote(note);

        lblTitle1.setText("Title: ");
        lblTitle2.setText(note.getTitle());

        lblType1.setText("Type: ");
        lblType2.setText(note.getNoteType());

        lblCreatedDate.setText("Created Date: " + note.getCreatedDate().toString());

        if (note instanceof RegularNote regularNote) {
            lblCategoryPriority1.setText("Category: ");
            lblCategoryPriority2.setText(regularNote.getCategory().name());
        } else if (note instanceof DeadlineNote deadlineNote) {
            lblCategoryPriority1.setText("Priority: ");
            lblCategoryPriority2.setText(deadlineNote.getPriority().name());
            lblDeadline1.setText("Deadline: ");
            lblDeadline2.setText(deadlineNote.getDeadline().toString());
            dpDeadline.setValue(deadlineNote.getDeadline());
        }

        taContent.setText(note.getContent());

        if (!this.isShowing()) {
            this.show();
            editVisibility(false);
        }
    }
}
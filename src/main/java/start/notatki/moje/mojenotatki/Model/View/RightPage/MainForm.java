package start.notatki.moje.mojenotatki.Model.View.RightPage;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequestViewModel;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.RegularNote;
import start.notatki.moje.mojenotatki.Model.View.MainScene;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainForm extends GridPane {

    private final GridPane gp = new GridPane();


    private final ButtonBar btnBar = new ButtonBar();
    private final Button btnSave = new Button("Save");
    private final Button btnCancel = new Button("Cancel");


    private final Label lblTitle = new Label("Title:");
    private final TextField tfTitle = new TextField();


    private final StackPane boxes = new StackPane();
    private final Label lblType = new Label("Note Type:");
    private ComboBox<String> cbType;
    private ComboBox<String> cbCategory;
    private ComboBox<String> cbPriorities;

    private final TextArea taContent = new TextArea();

    private final Label lblDeadline = new Label("Deadline:");
    private final DatePicker datePicker = new DatePicker(LocalDate.now());

    private final NoteRequestViewModel viewModel = new NoteRequestViewModel();

    private final MainScene mainScene;


    public MainForm(MainScene mainScene) {

        this.mainScene = mainScene;

        initialCheckBoxes();
        initialDatePicker();
        loadForm();
        bindViewModel();
    }

    private void initialCheckBoxes() {

        cbType = initializeType();
        cbCategory = initializeCategories();
        cbPriorities = initializeNotePriorities();
    }

    private ComboBox<String> initializeType() {

        ObservableList<String> types = FXCollections.observableArrayList(BaseNote.NoteType.getNames());

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().addAll(types);
        temp.setValue(types.get(0));

        return temp;
    }

    private ComboBox<String> initializeCategories() {

        String CATEGORY = "Category";

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().add(CATEGORY);
        temp.getItems().addAll(FXCollections.observableArrayList(RegularNote.Category.getNames()));
        temp.setValue(CATEGORY);

        return temp;
    }

    private ComboBox<String> initializeNotePriorities() {

        String PRIORITY = "Priority";

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().add(PRIORITY);
        temp.getItems().addAll(FXCollections.observableArrayList(DeadlineNote.Priority.getNames()));
        temp.setValue(PRIORITY);

        return temp;
    }

    private void initialDatePicker() {

        final Callback<DatePicker, DateCell> dayCellFactory = this::createDateCell;
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private DateCell createDateCell(DatePicker datePicker) {
        return new DateCell() {

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }

    public void loadForm() {

        this.getStyleClass().add("thisMainForm");
        this.getChildren().add(gp);

        boxes.getChildren().addAll(cbPriorities, cbCategory);

        styleAndLoadButtons();
        styleOtherElements();
        completeGridPane();
        deadlineVisibility(true);
    }

    private void styleAndLoadButtons() {

        btnBar.getButtons().addAll(btnCancel, btnSave);
        btnBar.getStyleClass().add("btnBar");

        btnSave.setOnAction(this::save);
        btnCancel.setOnAction(this::cancel);

        for (var button : btnBar.getButtons()) {

            Button button1 = (Button) button;

            button1.getStyleClass().add("gp-button");
        }
    }

    private void styleOtherElements() {

        lblTitle.getStyleClass().add("lblTitle");
        lblType.getStyleClass().add("lblType");
        lblDeadline.getStyleClass().add("lblDeadline");
        taContent.getStyleClass().add("taContent");
        taContent.setWrapText(true);
    }

    private void completeGridPane() {

        cbType.getStyleClass().add("cbType");
        cbCategory.getStyleClass().add("cbNoteType");
        cbPriorities.getStyleClass().add("cbNotePriorities");
        tfTitle.getStyleClass().add("tfTitle");
        gp.getStyleClass().add("gp");
        gp.maxWidthProperty().bind(Bindings.createDoubleBinding(() ->
                        this.getWidth() - this.getPadding().getLeft() - this.getPadding().getRight(),
                this.widthProperty(), this.paddingProperty()));


        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();

        col0.setPercentWidth(25);
        col1.setPercentWidth(20);
        col2.setPercentWidth(20);
        col3.setPercentWidth(20);
        col4.setPercentWidth(15);

        gp.getColumnConstraints().addAll(col0, col1, col2, col3, col4);

        cbType.setMaxWidth(Double.MAX_VALUE);
        cbCategory.setMaxWidth(Double.MAX_VALUE);
        cbPriorities.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(cbType, Priority.ALWAYS);
        GridPane.setHgrow(cbCategory, Priority.ALWAYS);
        GridPane.setHgrow(cbPriorities, Priority.ALWAYS);

        RowConstraints row0 = new RowConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();
        RowConstraints row5 = new RowConstraints();

        row0.setPrefHeight(30);
        row1.setPrefHeight(30);
        row2.setPrefHeight(30);
        row3.setPrefHeight(30);
        row4.setPrefHeight(30);
        row5.setPrefHeight(350);

        gp.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5);

        gp.add(btnBar, 3, 0);
        GridPane.setColumnSpan(btnBar, 2);
        GridPane.setHalignment(btnBar, HPos.RIGHT);

        gp.add(lblTitle, 0, 2);
        gp.add(tfTitle, 1, 2);
        GridPane.setColumnSpan(tfTitle, 2);

        gp.add(lblType, 0, 3);
        gp.add(cbType, 1, 3);

        gp.add(boxes, 2, 3);

        gp.add(lblDeadline, 3, 2);
        GridPane.setColumnSpan(lblDeadline, 2);
        gp.add(datePicker, 3, 3);
        GridPane.setColumnSpan(datePicker, 2);
        GridPane.setHalignment(datePicker, HPos.LEFT);
        GridPane.setValignment(datePicker, VPos.TOP);

        gp.add(taContent, 0, 5);
        GridPane.setColumnSpan(taContent, 3);
        taContent.setMinHeight(350);

        manageComboBoxes();
    }

    private void manageComboBoxes() {

        cbType.setOnAction(evt -> {
            if (cbType.getValue().equals(BaseNote.NoteType.REGULAR_NOTE.getName())) {
                deadlineVisibility(true);
            } else if (cbType.getValue().equals(BaseNote.NoteType.DEADLINE_NOTE.getName())) {
                deadlineVisibility(false);
            }
        });
    }

    private void deadlineVisibility(Boolean value) {
        cbCategory.setVisible(value);
        lblDeadline.setVisible(!value);
        datePicker.setVisible(!value);
    }

    private void bindViewModel() {

        tfTitle.textProperty().bindBidirectional(viewModel.titleProperty());
        taContent.textProperty().bindBidirectional(viewModel.contentProperty());

        viewModel.noteTypeProperty().bindBidirectional(cbType.valueProperty());
        viewModel.categoryProperty().bindBidirectional(cbCategory.valueProperty());
        viewModel.priorityProperty().bindBidirectional(cbPriorities.valueProperty());

        DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (viewModel.deadlineDateProperty().get() == null || viewModel.deadlineDateProperty().get().isEmpty()) {
            String saveDate = LocalDate.now().format(saveFormatter);
            viewModel.setDeadlineDate(saveDate);
        }

        datePicker.valueProperty().addListener((obs2, oldDate, newDate) -> {
            if (newDate != null) {

                String saveDate = newDate.format(saveFormatter);
                viewModel.setDeadlineDate(saveDate);
            }
        });
    }

    private void save(ActionEvent e) {

        if (!validateElements()) {
            addListeners();
            return;
        }
        viewModel.save();
    }

    private boolean validateElements() {

        boolean flag = true;

        if (taContent.getText().isEmpty() || taContent.getText().isBlank()) {
            taContent.getStyleClass().add("badElement");
            flag = false;
        }

        if (tfTitle.getText().isEmpty() || tfTitle.getText().isBlank()) {
            tfTitle.getStyleClass().add("badElement");
            flag = false;
        }

        if (cbType.getValue().equals(BaseNote.NoteType.REGULAR_NOTE.getName())) {
            if (cbCategory.getValue().equals("Category")) {
                cbCategory.getStyleClass().add("badElement");
                flag = false;
            }
        } else if (cbType.getValue().equals(BaseNote.NoteType.DEADLINE_NOTE.getName())) {
            if (cbPriorities.getValue().equals("Priority")) {
                cbPriorities.getStyleClass().add("badElement");
                flag = false;
            }
        }
        return flag;
    }

    private void addListeners() {

        tfTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.isBlank()) {
                tfTitle.getStyleClass().remove("badElement");
            }
        });

        taContent.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.isBlank()) {
                taContent.getStyleClass().remove("badElement");
            }
        });

        cbType.valueProperty().addListener((observable, oldValue, newValue) -> {
            cbType.getStyleClass().remove("badElement");
        });

        cbCategory.valueProperty().addListener((observable, oldValue, newValue) -> {
            cbCategory.getStyleClass().remove("badElement");
        });

        cbPriorities.valueProperty().addListener((observable, oldValue, newValue) -> {
            cbPriorities.getStyleClass().remove("badElement");
        });
    }

    private void cancel(ActionEvent e) {

        mainScene.useMainForm(false);
        viewModel.reset();
    }
}
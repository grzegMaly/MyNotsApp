package start.notatki.moje.mojenotatki.Model.View.RightPage;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequestViewModel;
import start.notatki.moje.mojenotatki.Note.DeadlineNote;
import start.notatki.moje.mojenotatki.Note.Note;
import start.notatki.moje.mojenotatki.Model.View.MainScene;

import java.time.LocalDate;

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
    }

    private void initialCheckBoxes() {

        cbType = initializeType();
        cbCategory = initializeCategories();
        cbPriorities = initializeNotePriorities();
    }

    private ComboBox<String> initializeType() {

        String TYPE = "Regular Note";

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().add(TYPE);
        temp.getItems().add( "Plan Note");
        temp.setValue(TYPE);

        return temp;
    }

    private ComboBox<String> initializeCategories() {

        String CATEGORY = "Category";

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().add(CATEGORY);
        temp.getItems().addAll(FXCollections.observableArrayList(Note.Category.getNames()));
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
            if (cbType.getValue().equals("Regular Note")) {
                deadlineVisibility(true);
            } else if (cbType.getValue().equals("Plan Note")) {
                deadlineVisibility(false);
            }
        });
    }

    private void deadlineVisibility(Boolean value) {
        cbCategory.setVisible(value);
        lblDeadline.setVisible(!value);
        datePicker.setVisible(!value);
    }

    private void save(ActionEvent e) {
        viewModel.save();
    }

    private void cancel(ActionEvent e) {

        mainScene.useMainForm(false);
        viewModel.reset();
    }
}

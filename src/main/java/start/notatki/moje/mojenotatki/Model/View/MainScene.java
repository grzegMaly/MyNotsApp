package start.notatki.moje.mojenotatki.Model.View;


import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.*;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.View.RightPage.MainForm;
import start.notatki.moje.mojenotatki.Model.View.RightPage.NotesList;
import start.notatki.moje.mojenotatki.Model.View.RightPage.StartForm;
import start.notatki.moje.mojenotatki.utils.ExecutorServiceManager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class MainScene extends HBox implements Page {

    private final StartForm startForm = new StartForm();
    private final LeftBar leftBar = new LeftBar(this);
    private final MainForm mainForm = new MainForm(this);
    private final NotesList notesList = new NotesList(this);
    private final ExecutorService executor =
            ExecutorServiceManager.createCachedThreadPool(this.getClass().getSimpleName());

    StackPane stackPane = new StackPane();

    public MainScene() {
    }

    @Override
    public void loadPage() {

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch restLatch = new CountDownLatch(1);

        executor.submit(() -> {
            this.getStyleClass().add("main-scene");
            startLatch.countDown();
        });

        try {
            startLatch.await();

            Future<?> leftBarFuture = executor.submit(this::loadLeftBar);
            Future<?> startSceneFuture = executor.submit(this::loadStartScene);

            leftBarFuture.get();
            startSceneFuture.get();
            restLatch.countDown();

        } catch (InterruptedException | ExecutionException e) {
            FilesManager.registerException(e);
            Thread.currentThread().interrupt();
        }

        try {
            restLatch.await();

            Future<?> mainFormFuture = executor.submit(this::loadMainForm);
            Future<?> notesListFuture = executor.submit(this::loadNotesList);

            mainFormFuture.get();
            notesListFuture.get();

        } catch (InterruptedException | ExecutionException e) {
            FilesManager.registerException(e);
            Thread.currentThread().interrupt();
        }

        this.getChildren().addAll(leftBar, stackPane);
    }

    public NotesList getNotesList() {
        return notesList;
    }

    public void useMainForm(Boolean value) {

        startForm.setVisible(!value);
        notesList.setVisible(!value);
        mainForm.setVisible(value);
    }

    public void useNotesList(Boolean value) {

        startForm.setVisible(!value);
        mainForm.setVisible(!value);
        notesList.setVisible(value);
    }

    private void loadLeftBar() {

        leftBar.getStyleClass().add("left-bar");
    }

    private void loadStartScene() {

        startForm.getStyleClass().add("stack-pane");

        DoubleBinding availableWidth = this.minWidthProperty()
                .subtract(startForm.getPadding().getLeft() +
                        startForm.getPadding().getRight() + startForm.getSpacing());

        stackPane.setMinWidth(availableWidth.get());

        leftBar.minWidthProperty().bind(availableWidth.multiply(0.25));
        startForm.minWidthProperty().bind(availableWidth.multiply(0.75));

        stackPane.getChildren().addAll(notesList, mainForm, startForm);
    }

    public void loadMainForm() {

        mainForm.getStyleClass().add("main-form");
        mainForm.loadPage();
        mainForm.minWidthProperty().bind(startForm.minWidthProperty());
        mainForm.minHeightProperty().bind(startForm.minHeightProperty());
    }

    public void loadNotesList() {

        notesList.getStyleClass().add("main-form");
        notesList.loadPage();
        notesList.minWidthProperty().bind(mainForm.minWidthProperty());
        notesList.minHeightProperty().bind(mainForm.minHeightProperty());
    }
}

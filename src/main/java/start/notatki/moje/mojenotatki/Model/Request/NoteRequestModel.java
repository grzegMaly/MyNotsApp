package start.notatki.moje.mojenotatki.Model.Request;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import start.notatki.moje.mojenotatki.Config.FilesManager;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.BaseNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.DeadlineNoteRequest;
import start.notatki.moje.mojenotatki.Model.Request.NoteRequest.RegularNoteRequest;
import start.notatki.moje.mojenotatki.Note.BaseNote;
import start.notatki.moje.mojenotatki.utils.CustomAlert;
import start.notatki.moje.mojenotatki.utils.DirectoryUtils;
import start.notatki.moje.mojenotatki.utils.ExecutorServiceManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.*;

public class NoteRequestModel {

    private Path path;
    private BaseNote note;
    private final String extension = ".txt";
    private final StringBuilder sb = new StringBuilder();
    private final ExecutorService executor = ExecutorServiceManager.createCachedThreadPool(this.getClass().getSimpleName());
    private final CountDownLatch latch = new CountDownLatch(1);

    private CompletableFuture<Boolean> checkOrSetDirectory() {

        return DirectoryUtils.checkOrSetOutputDirectory()
                .thenCompose(exists -> {
                    if (exists) {
                        return CompletableFuture.completedFuture(true);
                    } else {
                        return FilesManager.checkNotesDirectoryExistence();
                    }
                });
    }

    public CompletableFuture<Boolean> toSave(BaseNoteRequest req) {

        note = req.getOriginalInstance();

        return checkOrSetDirectory().thenCompose(directoryExists -> {
            if (!directoryExists) {
                return CompletableFuture.completedFuture(false);
            }

            return handleRenameIfNeeded(req).thenCompose(renameSuccess -> {
                if (!renameSuccess) {
                    return CompletableFuture.completedFuture(false);
                }

                return handleSave(req);
            });
        }).exceptionally(ex -> {
//            FilesManager.registerException(ex);
            return false;
        });

    }

    private CompletableFuture<Boolean> handleRenameIfNeeded(BaseNoteRequest req) {

        return CompletableFuture.supplyAsync(() -> {
            if (note != null) {
                if (note.getTitle().equals(req.getTitle())) {
                    path = Paths.get(FilesManager.getSaveNotesPath(), note.getTitle() + extension);
                    return true;
                } else {
                    Path oldPath = Paths.get(FilesManager.getSaveNotesPath(), note.getTitle() + extension);
                    path = Paths.get(FilesManager.getSaveNotesPath(), req.getTitle() + extension);
                    return renameFile(oldPath, path);
                }
            } else {
                path = Paths.get(FilesManager.getSaveNotesPath(), req.getTitle() + extension);
                return true;
            }
        }, executor);
    }

    private boolean renameFile(Path oldPath, Path newPath) {
        try {
            Files.move(oldPath, newPath);
            return true;
        } catch (IOException e) {
            FilesManager.registerException(e);
            Platform.runLater(() -> showErrorDialog("Failed to rename the file."));
            return false;
        }
    }

    private CompletableFuture<Boolean> handleSave(BaseNoteRequest req) {

        return CompletableFuture.runAsync(() -> {
            synchronized (sb) {
                convertContent(req, note);
                latch.countDown();
            }
        }, executor).thenCompose(v -> CompletableFuture.supplyAsync(() -> {
            try {
                latch.await();
                synchronized (sb) {
                    return save(path);
                }
            } catch (InterruptedException e) {
                FilesManager.registerException(e);
                Thread.currentThread().interrupt();
                return false;
            }
        }, executor));
    }

    private boolean save(Path path) {

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            FilesManager.registerException(e);
            Platform.runLater(() -> showErrorDialog("Failed to save the file."));
            return false;
        }

        sb.setLength(0);
        return true;
    }

    public void deleteFile(BaseNote note) {
        String title = note.getTitle() + extension;
        Path path = Paths.get(FilesManager.getSaveNotesPath(), title);
        deleteFile(path);
    }

    private void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            FilesManager.registerException(e);
        }
    }

    private boolean getFutureResult(Future<?> future) {
        try {
            future.get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            FilesManager.registerException(e);
            return false;
        }
    }

    private void convertContent(BaseNoteRequest req, BaseNote originalInstance) {

        sb.append(req.getTitle()).append("\n");
        sb.append(req.getNoteType()).append("\n");

        if (originalInstance != null) {
            sb.append(originalInstance.getCreatedDate()).append("\n");
        } else {
            sb.append(req.getNote().getCreatedDate()).append("\n");
        }

        if (req instanceof DeadlineNoteRequest r) {
            sb.append(r.getPriority()).append("\n");
            sb.append(r.getDeadline()).append("\n");
        } else if (req instanceof RegularNoteRequest r) {
            sb.append(r.getCategory()).append("\n");
        }

        sb.append("\n");
        sb.append("Content:\n");
        sb.append(req.getContent());
    }

    private void showErrorDialog(String message) {
        Alert alert = new CustomAlert(message);
        alert.showAndWait();
    }
}

package start.notatki.moje.mojenotatki;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.Model.View.MainScene;



public class MyNotesApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainScene mainScene = new MainScene();
        Scene scene = new Scene(mainScene);
        scene.getStylesheets().add(getClass().getResource("/styles/mainScene.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/styles/startForm.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/styles/leftBar.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/styles/mainForm.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Your Favorite Notes");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

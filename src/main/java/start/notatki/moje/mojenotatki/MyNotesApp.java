package start.notatki.moje.mojenotatki;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import start.notatki.moje.mojenotatki.View.MainScene;

public class MyNotesApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainScene mainScene = new MainScene(1024,720);
        Scene scene = new Scene(mainScene);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Your Favorite Notes");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

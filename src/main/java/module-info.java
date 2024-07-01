module start.notatki.moje.mojenotatki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports start.notatki.moje.mojenotatki to javafx.graphics;
    exports start.notatki.moje.mojenotatki.View to javafx.graphics;
}
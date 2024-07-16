module start.notatki.moje.mojenotatki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;
    requires java.sql;

    opens start.notatki.moje.mojenotatki.Note to javafx.base;
    exports start.notatki.moje.mojenotatki to javafx.graphics;
    exports start.notatki.moje.mojenotatki.Model.View.RightPage to javafx.graphics;
    exports start.notatki.moje.mojenotatki.Model.View to javafx.graphics;
}
module start.notatki.moje.mojenotatki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;
    requires java.sql;
    requires javafx.graphics;

    exports start.notatki.moje.mojenotatki;
    exports start.notatki.moje.mojenotatki.Model.View.RightPage;
    exports start.notatki.moje.mojenotatki.Model.View;
    exports start.notatki.moje.mojenotatki.Note;

    opens start.notatki.moje.mojenotatki.Note to javafx.base;
}
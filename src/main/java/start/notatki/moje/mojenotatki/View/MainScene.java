package start.notatki.moje.mojenotatki.View;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainScene extends HBox {

    LeftBar leftBar = new LeftBar();
    MainForm mainForm = new MainForm();

    public MainScene(double width, double height) {

        this.setWidth(width);
        this.setHeight(height);

        loadMainScene();
        loadLeftBar();
        loadMainForm();

    }

    private void loadMainScene() {

        HBox hBox = new HBox(leftBar, mainForm);

        hBox.setPadding(new Insets(40));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(20);
        hBox.setBorder(new Border(
                new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT)
        ));
        hBox.setMinWidth(this.getWidth());
        hBox.setMinHeight(this.getHeight());


        hBox.setBackground(
                new Background(
                        new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)
                )
        );
        this.getChildren().add(hBox);
    }

    private void loadLeftBar() {

        leftBar.setMinWidth(250);

        leftBar.setBackground(new Background(
                new BackgroundFill(Color.grayRgb(90), new CornerRadii(15), Insets.EMPTY)
        ));

        leftBar.setBorder(
                new Border(
                        new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID,
                                new CornerRadii(15), BorderWidths.DEFAULT)
                )
        );
    }

    private void loadMainForm() {

        mainForm.setBorder(
                new Border(
                        new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY, BorderWidths.DEFAULT)
                )
        );

        mainForm.setBackground();
    }
}

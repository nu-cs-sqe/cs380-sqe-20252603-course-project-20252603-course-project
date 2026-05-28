
package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private final int windowWidth = 480;
    private final int windowHeight = 640;

    public final void start(final Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, windowWidth, windowHeight);
        stage.setTitle("Risk");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}

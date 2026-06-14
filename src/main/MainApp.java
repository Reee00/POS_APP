package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.MainController;

/**
 * Main entry point for Ahmad Renaldy POS System.
 * Launches the JavaFX application with the dark developer dashboard theme.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        MainController controller = new MainController();
        BorderPane root = controller.getRoot();

        Scene scene = new Scene(root, 1280, 760);
        scene.getStylesheets().add(
            getClass().getResource("/util/renaldy.css").toExternalForm()
        );

        stage.setTitle("POS System — Ahmad Renaldy");
        stage.setMinWidth(1024);
        stage.setMinHeight(640);
        stage.setScene(scene);
        stage.show();

        // Show dashboard on start
        controller.showDashboard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

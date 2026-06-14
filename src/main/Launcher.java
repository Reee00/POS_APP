package main;

/**
 * Launcher class to bypass JavaFX runtime modules missing error.
 * This class should not extend javafx.application.Application.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}

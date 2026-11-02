package co.edu.uniquindio.poo.proyectocine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var recurso = App.class.getResource("MainView.fxml");
        if (recurso == null) {
            throw new IllegalStateException("No se encontró MainView.fxml");
        }
        stage.setScene(new Scene(FXMLLoader.load(recurso), 1280, 850));
        stage.setTitle("CinemaUQ");
        stage.setMinWidth(980);
        stage.setMinHeight(680);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

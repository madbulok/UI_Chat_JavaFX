package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final int MAX_WIDTH = 560;
    private final int MAX_HEIGHT = 275;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Чат");
        primaryStage.setScene(new Scene(root, MAX_WIDTH, MAX_HEIGHT));
        primaryStage.setMinWidth(MAX_WIDTH);
        primaryStage.setMaxWidth(MAX_WIDTH);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    Stage stage;

    public Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("window.fxml"));
        Parent root = loader.load();
        stage.setTitle("VKit v.2.0");
        stage.setScene(new Scene(root, 355, 280));
        stage.setResizable(false);
        stage.getIcons().add(new Image("res/logo.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

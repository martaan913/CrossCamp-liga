package com.example.crosscampliga;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainScene extends Application {
    public void start(Stage primaryStage) throws Exception {
        MainSceneController controller = new MainSceneController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
//        scene.getStylesheets().add(getClass().getResource("style/main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("CrossCamp - Liga");
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}

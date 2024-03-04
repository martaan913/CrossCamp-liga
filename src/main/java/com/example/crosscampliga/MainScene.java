package com.example.crosscampliga;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainScene extends Application {
    public void start(Stage primaryStage) throws Exception {
        // Inicializácia pre MainSceneController
        MainSceneController controller = new MainSceneController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        // Inicializácia pre ScoreBoardSceneController
        ScoreBoardSceneController controllerScoreBoard = new ScoreBoardSceneController();
        FXMLLoader loaderScoreBoard = new FXMLLoader(getClass().getResource("ScoreBoardScene.fxml"));
        loaderScoreBoard.setController(controllerScoreBoard);
        Parent parentScoreBoard = loaderScoreBoard.load();
        Scene sceneScoreBoard = new Scene(parentScoreBoard);

        // Nastavenie referencie v MainSceneController
        controller.setScoreBoardSceneController(controllerScoreBoard);

        // Priradenie štýlov
        String mainCss = this.getClass().getResource("/style/mainStyle.css").toExternalForm();
        String scoreBoardCss = this.getClass().getResource("/style/scoreBoardStyle.css").toExternalForm();
        String tableViewCss = this.getClass().getResource("/style/table-view.css").toExternalForm();
        scene.getStylesheets().add(mainCss);
        scene.getStylesheets().add(tableViewCss);
        sceneScoreBoard.getStylesheets().add(scoreBoardCss);
        sceneScoreBoard.getStylesheets().add(tableViewCss);
        Font.loadFont("file:src/main/resources/font/SevenSegment.ttf",10 );

        // Vytvorenie a zobrazenie hlavného a vedľajšieho okna
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(sceneScoreBoard);
        secondaryStage.setTitle("CrossCamp - Liga");
        secondaryStage.setResizable(true);
        secondaryStage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/images/logo.png"));
        secondaryStage.show();

        primaryStage.setScene(scene);
        primaryStage.setTitle("CrossCamp - Liga");
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/images/logo.png"));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}

package com.example.crosscampliga;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainScene extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    public void start(Stage primaryStage) throws Exception {
        ObservableList<Screen> screens = Screen.getScreens();//Get list of Screens

        // initialize for MainSceneController
        MainSceneController controller = new MainSceneController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        // initialize ScoreBoardSceneController
        ScoreBoardSceneController controllerScoreBoard = new ScoreBoardSceneController();
        FXMLLoader loaderScoreBoard = new FXMLLoader(getClass().getResource("ScoreBoardScene.fxml"));
        loaderScoreBoard.setController(controllerScoreBoard);
        Parent parentScoreBoard = loaderScoreBoard.load();
        Scene sceneScoreBoard = new Scene(parentScoreBoard);

        // setting up reference for scoreBoardController
        controller.setScoreBoardSceneController(controllerScoreBoard);

        // adding css
        String mainCss = this.getClass().getResource("/style/mainStyle.css").toExternalForm();
        String scoreBoardCss = this.getClass().getResource("/style/scoreBoardStyle.css").toExternalForm();
        String textFieldCss = this.getClass().getResource("/style/textFieldStyle.css").toExternalForm();
        String listViewCss = this.getClass().getResource("/style/listViewStyle.css").toExternalForm();
        String choiceBoxCss = this.getClass().getResource("/style/choiceBoxStyle.css").toExternalForm();
        scene.getStylesheets().add(mainCss);
        scene.getStylesheets().add(textFieldCss);
        scene.getStylesheets().add(listViewCss);
        scene.getStylesheets().add(choiceBoxCss);
        sceneScoreBoard.getStylesheets().add(scoreBoardCss);
//        sceneScoreBoard.getStylesheets().add(tableViewCss);
        Font.loadFont("file:src/main/resources/font/SevenSegment.ttf",10 );

        // setting up scene for scoreBoard
        Stage secondaryStage = new Stage();
        if (screens.size() > 1) {
            Rectangle2D bounds = screens.get(1).getVisualBounds();
            secondaryStage.setX(bounds.getMinX());
            secondaryStage.setY(bounds.getMinY());
        }
        secondaryStage.setScene(sceneScoreBoard);
        secondaryStage.setTitle("CrossCamp - Liga");
        secondaryStage.setResizable(true);
        secondaryStage.initStyle(StageStyle.TRANSPARENT);
        secondaryStage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/images/logo.png"));
        secondaryStage.show();

        //making borderless ScoreBoard movable
        parentScoreBoard.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parentScoreBoard.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondaryStage.setX(event.getScreenX() - xOffset);
                secondaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        // setting up scene for scoreBoard
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

package com.example.crosscampliga;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ScoreBoardSceneController {

    @FXML
    private Label firstTeamLabel;

    @FXML
    private Label firstTeamScoreLabel;

    @FXML
    private Label secondTeamScoreLabel;

    @FXML
    private Label text;

    @FXML
    private Label timeLeftLabel;

    @FXML
    private void initialize(){
//        text.setFont(Font.loadFont("file:src/main/resources/font/SevenSegment.ttf", text.getFont().getSize()));
    }

    private Timeline timeline;
    private int secondsRemaining;
    public void startCountdown() {
        if (timeline != null) {
            timeline.stop();
        }

        // Vytvorenie nového timeline pre odpočítavanie sekúnd
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining--;
            if (secondsRemaining <= 0) {
                timeline.stop();
            }
            updateTimerLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Metóda na aktualizáciu zobrazenia času v labeli
    private void updateTimerLabel() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timeLeftLabel.setText(timeString);
    }

    // Metóda na zastavenie odpočítavania
    public void stopCountdown() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void setTime(int min, int sec){
        secondsRemaining = min * 60 + sec;
        updateTimerLabel();
    }

    public void startGame(){
        firstTeamScoreLabel.setText(String.valueOf(0));
        secondTeamScoreLabel.setText(String.valueOf(0));

    }

    public void firstTeamGoal(){
        firstTeamScoreLabel.setText(String.valueOf(Integer.parseInt(firstTeamScoreLabel.getText()) + 1));
    }

    public void removeFirstTeamGoal(){
        firstTeamScoreLabel.setText(String.valueOf(Integer.parseInt(firstTeamScoreLabel.getText()) - 1));
    }

    public void secondTeamGoal(){
        secondTeamScoreLabel.setText(String.valueOf(Integer.parseInt(secondTeamScoreLabel.getText()) + 1));
    }

    public void removeSecondTeamGoal(){
        secondTeamScoreLabel.setText(String.valueOf(Integer.parseInt(firstTeamScoreLabel.getText()) - 1));
    }
}

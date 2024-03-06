package com.example.crosscampliga;

import com.example.crosscampliga.storage.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardSceneController {

    @FXML
    private Label firstTeamLabel;

    @FXML
    private Label secondTeamLabel;

    @FXML
    private Label firstTeamScoreLabel;

    @FXML
    private Label secondTeamScoreLabel;

    @FXML
    private Label text;

    @FXML
    private Label timeLeftLabel;

    @FXML
    private GridPane firstTeamShootersGridPane;

    @FXML GridPane secondTeamShootersGridPane;

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
                playSound();
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
        secondTeamScoreLabel.setText(String.valueOf(Integer.parseInt(secondTeamScoreLabel.getText()) - 1));
    }

    public String getFirstTeamScore(){
        return firstTeamScoreLabel.getText();
    }

    public void setFirstTeamScore(String score){
        firstTeamScoreLabel.setText(score);
    }

    public String getSecondTeamScore(){
        return secondTeamScoreLabel.getText();
    }

    public void setSecondTeamScore(String score){
        secondTeamScoreLabel.setText(score);
    }

    public String getFirstTeamName(){
        return firstTeamLabel.getText();
    }

    public void setFirstTeamLabel(String name){
        firstTeamLabel.setText(name);
    }

    public String getSecondTeamName(){
        return secondTeamLabel.getText();
    }

    public void setSecondTeamLabel(String name){
        secondTeamLabel.setText(name);
    }

    public List<String> getFirstTeamShooters() {
        ObservableList<Node> children = firstTeamShootersGridPane.getChildren();
        List<String> shooters = new ArrayList<>();

        for (Node node : children) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String text = label.getText();
                shooters.add(text);
            }
        }

        return shooters;
    }

    public void setFirstTeamShootersGridPane(List<String> shooters){
        secondTeamShootersGridPane.getChildren().clear();
        for(int i = 0; i < shooters.size(); i++){
            Label label = new Label(shooters.get(i));
            label.setFont(new Font(24));
            label.setAlignment(Pos.CENTER_RIGHT);
            secondTeamShootersGridPane.addRow(i, label);
        }
    }

    public void removeFirstTeamShooter(){
        firstTeamShootersGridPane.getChildren().remove(firstTeamShootersGridPane.getRowCount() - 1);
    }

    public void addFirstTeamShooter(Player player){
        Label label = new Label(player.getName());
        label.setFont(new Font(24));
        int rowCount = firstTeamShootersGridPane.getRowCount();
        // Pridanie nového prvku na vrch
        firstTeamShootersGridPane.addRow(rowCount, label);
    }

    public List<String> getSecondTeamShooters() {
        ObservableList<Node> children = secondTeamShootersGridPane.getChildren();
        List<String> shooters = new ArrayList<>();

        for (Node node : children) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String text = label.getText();
                shooters.add(text);
            }
        }

        return shooters;
    }

    public void setSecondTeamShootersGridPane(List<String> shooters){
        firstTeamShootersGridPane.getChildren().clear();
        for(int i = 0; i < shooters.size(); i++){
            Label label = new Label(shooters.get(i));
            label.setFont(new Font(24));
            firstTeamShootersGridPane.addRow(i, label);
        }
    }

    public void removeSecondTeamShooter(){
        secondTeamShootersGridPane.getChildren().remove(secondTeamShootersGridPane.getRowCount() - 1);
    }

    public void addSecondTeamShooter(Player player){
        Label label = new Label(player.getName());
        label.setFont(new Font(24));
        GridPane.setHalignment(label, HPos.RIGHT);
        int rowCount = secondTeamShootersGridPane.getRowCount();
        secondTeamShootersGridPane.addRow(rowCount, label);
    }

    public void playSound() {
        String soundPath = "src/main/resources/sound/airHorn.mp3";
        Media sound = new Media(new File(soundPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        System.out.println("koniec zapasu");

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        });

        mediaPlayer.play();
    }
}
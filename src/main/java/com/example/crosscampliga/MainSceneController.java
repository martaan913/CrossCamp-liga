package com.example.crosscampliga;

import com.example.crosscampliga.storage.DaoFactory;
import com.example.crosscampliga.storage.Player;
import com.example.crosscampliga.storage.PlayerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainSceneController {

    @FXML
    private Button addGoalFirstGoalieButton;

    @FXML
    private Button addFirstTeamGoal;
    @FXML
    private Button addSecondTeamButton;
    @FXML
    private Button startMatchButton;
    @FXML
    private Button halfTimeButton;
    @FXML
    private Button setTimeButton;
    @FXML
    private TextField timeTextField;
    @FXML
    private Button stopTimeButton;
    @FXML
    private TextField firstTeamNameTextField;
    @FXML
    private Button setFirstTeamNameButton;
    @FXML
    private TextField secondTeamNameTextField;
    @FXML
    private Button setSecondTeamNameButton;

    @FXML
    private Button removeFirstTeamGoalButton;
    @FXML
    private Button removeSecondTeamGoalButton;

    @FXML
    private Button addGoalSecondGoalieButton;

    @FXML
    private Button addPlayerButton;

    @FXML
    private Button openScoreBoardButton;

    @FXML
    private ListView<Player> goalAssistListView;

    @FXML
    private TextField goalAssistTextField;

    @FXML
    private ListView<Player> goalShooterListView;

    @FXML
    private TextField goalShooterTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<String> positionChoiceBox;


    @FXML
    private Button refreshTableButton;

    @FXML
    private Button addFirstGoalieButton;
    @FXML
    private Button addFirstGoalieSaveButton;
    @FXML
    private Label firstGoalieNameLabel;

    @FXML
    private Button addSecondGoalieButton;
    @FXML
    private Button addSecondGoalieSaveButton;
    @FXML
    private Label secondGoalieNameLabel;

    @FXML
    private ChoiceBox<Player> goalieChoiceBox;



    PlayerDao playerDao = DaoFactory.INSTANCE.getPlayerDao();
    private ObservableList<Player> playersModel;
    private Player selectedShooter;
    private Player selectedAssist;
    private ObservableList<Player> playersAssist;
    private ObservableList<Player> goaliesModel;
    private Player firstGoalie;
    private Player secondGoalie;
    private ScoreBoardSceneController scoreBoardSceneController;

    @FXML
    private void initialize(){
        positionChoiceBox.getItems().addAll("Hrac", "Brankar");

        List<Player> players = playerDao.getAll();
        playersModel = FXCollections.observableList(players);
        goalShooterListView.setItems(playersModel);
        goalAssistListView.setItems(playersModel);

        goalShooterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Player> playersFiltered = playerDao.getByNameLike(newValue);
            ObservableList<Player> observablePlayersFiltered = FXCollections.observableArrayList(playersFiltered);
            goalShooterListView.setItems(observablePlayersFiltered);
        });

        goalAssistTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Player> playersFiltered = playerDao.getByNameLike(newValue);
            ObservableList<Player> observablePlayersFiltered = FXCollections.observableArrayList(playersFiltered);
            goalAssistListView.setItems(observablePlayersFiltered);
        });

        goalShooterListView.setCellFactory(param -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player shooter, boolean empty) {
                super.updateItem(shooter, empty);

                if (empty || shooter == null) {
                    setText(null);
                } else {
                    setText(shooter.toString());
                    setFont(new Font(18));
                    setOnMouseClicked(mouseEvent -> handleShooterListClick(mouseEvent));
                }
            }
        });

        goalAssistListView.setCellFactory(param -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player shooter, boolean empty) {
                super.updateItem(shooter, empty);

                if (empty || shooter == null) {
                    setText(null);
                } else {
                    setText(shooter.toString());
                    setFont(new Font(18));
                    setOnMouseClicked(mouseEvent -> handleAssistListClick(mouseEvent));
                }
            }
        });

        List<Player> goalies = playerDao.getAllGoalies();
        goaliesModel = FXCollections.observableList(goalies);
        goalieChoiceBox.setItems(goaliesModel);

        timeTextField.setText("4:00");
    }

    @FXML
    void onAddPlayer(ActionEvent event) {
        if (Objects.equals(nameTextField.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Zadaj meno");

            alert.showAndWait();

            return;
        }
        if (positionChoiceBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Zadaj poziciu");

            alert.showAndWait();

            return;
        }

        Player player = new Player();
        player.setName(nameTextField.getText());
        player.setPosition(positionChoiceBox.getValue());
        playerDao.add(player);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Hráč bol pridaný");

        alert.showAndWait();

        goaliesModel = FXCollections.observableArrayList(
                playerDao.getAllGoalies()
        );
        goalieChoiceBox.setItems(goaliesModel);

        List<Player> players = playerDao.getAll();
        playersModel = FXCollections.observableList(players);
        goalShooterListView.setItems(playersModel);
        goalAssistListView.setItems(playersModel);
    }

    @FXML
    void onAddGoalFirstGoalie(ActionEvent event) {
        if (selectedShooter == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Zle oznaceny strelec!");

            alert.showAndWait();

            return;
        }

        if (selectedAssist != null && selectedShooter.getId() == selectedAssist.getId()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Strelec a nahravac nemoze byt rovnaky clovek!");

            alert.showAndWait();

            return;
        }

        if(firstGoalie == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Vyber prveho brankara!");

            alert.showAndWait();

            return;
        }

        selectedShooter.setGoals(selectedShooter.getGoals() + 1);
        playerDao.add(selectedShooter);
        if (selectedAssist != null) {
            selectedAssist.setAssists(selectedAssist.getAssists() + 1);
            playerDao.add(selectedAssist);
        }
        firstGoalie.setFailedSaves(firstGoalie.getFailedSaves() + 1);
        playerDao.add(firstGoalie);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText(selectedShooter + " " + selectedAssist);

        alert.showAndWait();

        scoreBoardSceneController.firstTeamGoal();

        scoreBoardSceneController.addFirstTeamShooter(selectedShooter);

        selectedShooter = null;
        selectedAssist = null;
    }

    @FXML
    void onAddGoalSecondGoalie(ActionEvent event) {
        if (selectedShooter == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Zle oznaceny strelec!");

            alert.showAndWait();

            return;
        }

        if (selectedAssist != null && selectedShooter.getId() == selectedAssist.getId()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Strelec a nahravac nemoze byt rovnaky clovek!");

            alert.showAndWait();

            return;
        }

        if(secondGoalie == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Vyber druheho brankara!");

            alert.showAndWait();

            return;
        }

        selectedShooter.setGoals(selectedShooter.getGoals() + 1);
        playerDao.add(selectedShooter);
        if (selectedAssist != null) {
            selectedAssist.setAssists(selectedAssist.getAssists() + 1);
            playerDao.add(selectedAssist);
        }
        secondGoalie.setFailedSaves(secondGoalie.getFailedSaves() + 1);
        playerDao.add(secondGoalie);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText(selectedShooter + " " + selectedAssist);

        alert.showAndWait();

        scoreBoardSceneController.secondTeamGoal();

        scoreBoardSceneController.addSecondTeamShooter(selectedShooter);

        selectedShooter = null;
        selectedAssist = null;
    }
    @FXML
    void onRefreshTable(ActionEvent event) {
        try {
            StatisticsSceneController controller = new StatisticsSceneController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsScene.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage statisticsStage = new Stage();
            statisticsStage.setScene(scene);
            statisticsStage.show();
            String mainCss = this.getClass().getResource("/style/mainStyle.css").toExternalForm();
            scene.getStylesheets().add(mainCss);
            String tableViewCss = this.getClass().getResource("/style/tableViewStyle.css").toExternalForm();
            scene.getStylesheets().add(tableViewCss);
            statisticsStage.setTitle("CrossCamp - Liga");
            statisticsStage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/images/logo.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAddFirstGoalie(ActionEvent event) {
        firstGoalie = goalieChoiceBox.getValue();
        if (secondGoalie!= null && secondGoalie == goalieChoiceBox.getValue()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("1. a 2. brankar nemozu byt rovnaky brankar!");

            alert.showAndWait();

            return;
        }
        if (firstGoalie != null) {
            firstGoalieNameLabel.setText(firstGoalie.getName());
        }
    }

    @FXML
    void onAddFirstGoalieSave(ActionEvent event) {
        if (firstGoalie != null) {
            firstGoalie.setSaves(firstGoalie.getSaves() + 1);
            playerDao.add(firstGoalie);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Musis zadat brankara!");

            alert.showAndWait();
        }
    }

    @FXML
    void onAddSecondGoalie(ActionEvent event) {
        if (firstGoalie == goalieChoiceBox.getValue()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("1. a 2. brankar nemozu byt rovnaky brankar!");

            alert.showAndWait();

            return;
        }
        secondGoalie = goalieChoiceBox.getValue();
        secondGoalieNameLabel.setText(secondGoalie.getName());
    }

    @FXML
    void onAddSecondGoalieSave(ActionEvent event) {
        secondGoalie.setSaves(secondGoalie.getSaves() + 1);
        playerDao.add(secondGoalie);
    }

    private void handleShooterListClick(MouseEvent mouseEvent) {
        Player clickedPlayer = goalShooterListView.getSelectionModel().getSelectedItem();
        if(clickedPlayer != null){
            selectedShooter = playerDao.getByName(clickedPlayer.getName());
        }
    }
    private void handleAssistListClick(MouseEvent mouseEvent) {
        Player clickedPlayer = goalAssistListView.getSelectionModel().getSelectedItem();
        if(clickedPlayer != null){
            selectedAssist = playerDao.getByName(clickedPlayer.getName());
        }
    }

    public void setScoreBoardSceneController(ScoreBoardSceneController controller) {
        this.scoreBoardSceneController = controller;
    }

    @FXML
    void onStartMatch(ActionEvent event){
        if (scoreBoardSceneController != null) {
            setTime();
            scoreBoardSceneController.stopCountdown();
            scoreBoardSceneController.startGame();
        }
    }
    @FXML
    void onStartTime(ActionEvent event) {
        if (scoreBoardSceneController != null) {
            scoreBoardSceneController.startCountdown();
        }
    }

    public void setTime(){
        String[] cas = timeTextField.getText().split(":");
        int min = Integer.parseInt(cas[0]);
        int sec = Integer.parseInt(cas[1]);
        scoreBoardSceneController.setTime(min, sec);
    }

    @FXML
    void onSetTime(){
        setTime();
    }

    @FXML
    void onStopTime(){
        scoreBoardSceneController.stopCountdown();
    }

    @FXML
    void onRemoveFirstTeamGoal(){
        scoreBoardSceneController.removeFirstTeamGoal();
        scoreBoardSceneController.removeFirstTeamShooter();
    }

    @FXML
    void onRemoveSecondTeamGoal(){
        scoreBoardSceneController.removeSecondTeamGoal();
        scoreBoardSceneController.removeSecondTeamShooter();
    }

    @FXML
    void onHalfTime(){
        String firstTeamScore = scoreBoardSceneController.getFirstTeamScore();
        String secondTeamScore = scoreBoardSceneController.getSecondTeamScore();
        scoreBoardSceneController.setFirstTeamScore(secondTeamScore);
        scoreBoardSceneController.setSecondTeamScore(firstTeamScore);

        setTime();

        String firstTeamName = scoreBoardSceneController.getFirstTeamName();
        String secondTeamName = scoreBoardSceneController.getSecondTeamName();
        scoreBoardSceneController.setFirstTeamLabel(secondTeamName);
        scoreBoardSceneController.setSecondTeamLabel(firstTeamName);

        List<String> firstTeamShooters = scoreBoardSceneController.getFirstTeamShooters();
        List<String> secondTeamShooters = scoreBoardSceneController.getSecondTeamShooters();
        scoreBoardSceneController.setFirstTeamShootersGridPane(firstTeamShooters);
        scoreBoardSceneController.setSecondTeamShootersGridPane(secondTeamShooters);
    }

    @FXML
    void onAddSecondTeam(ActionEvent event) {
        scoreBoardSceneController.secondTeamGoal();
    }
    @FXML
    void onAddFirstTeamGoal(ActionEvent event) {
        scoreBoardSceneController.firstTeamGoal();
    }

    @FXML
    void onSetFirstTeamName(){
        scoreBoardSceneController.setFirstTeamLabel(firstTeamNameTextField.getText());
    }

    @FXML
    void onSetSecondTeamName(){
        scoreBoardSceneController.setSecondTeamLabel(secondTeamNameTextField.getText());
    }
}

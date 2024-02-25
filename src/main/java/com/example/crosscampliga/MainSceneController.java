package com.example.crosscampliga;

import com.example.crosscampliga.storage.DaoFactory;
import com.example.crosscampliga.storage.Player;
import com.example.crosscampliga.storage.PlayerDao;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainSceneController {
    @FXML
    private Button addGoalButton;

    @FXML
    private Button addPlayerButton;

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
    private TableView<Player> assistsStandingsTable;
    @FXML
    private TableColumn<Player, String> nameAssistsColumn;
    @FXML
    private TableColumn<Player, Integer> numAssistsColumn;

    @FXML
    private TableView<Player> goalsStandingsTable;
    @FXML
    private TableColumn<Player, String> nameGoalsColumn;
    @FXML
    private TableColumn<Player, Integer> numGoalsColumn;

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
    ObservableList<Player> playersAssist;
    private Player firstGoalie;
    private Player secondGoalie;


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

        nameGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("goals"));
        goalsStandingsTable.setItems(FXCollections.observableList(playerDao.getAll()));
        numGoalsColumn.setSortType(TableColumn.SortType.DESCENDING);
        goalsStandingsTable.getSortOrder().add(numGoalsColumn);
        goalsStandingsTable.sort();

        nameAssistsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numAssistsColumn.setCellValueFactory(new PropertyValueFactory<>("assists"));
        assistsStandingsTable.setItems(FXCollections.observableList(playerDao.getAll()));
        numAssistsColumn.setSortType(TableColumn.SortType.DESCENDING);
        assistsStandingsTable.getSortOrder().add(numAssistsColumn);
        assistsStandingsTable.sort();

        ObservableList<Player> observableGoalies = FXCollections.observableArrayList(
                playerDao.getAllGoalies()
        );
        goalieChoiceBox.setItems(observableGoalies);
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

        ObservableList<Player> observableGoalies = FXCollections.observableArrayList(
                playerDao.getAllGoalies()
        );
        goalieChoiceBox.setItems(observableGoalies);

        List<Player> players = playerDao.getAll();
        playersModel = FXCollections.observableList(players);
        goalShooterListView.setItems(playersModel);
        goalAssistListView.setItems(playersModel);
    }

    @FXML
    void onAddGoal(ActionEvent event) {
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

        selectedShooter.setGoals(selectedShooter.getGoals() + 1);
        playerDao.add(selectedShooter);
        if (selectedAssist != null) {
            selectedAssist.setAssists(selectedAssist.getAssists() + 1);
            playerDao.add(selectedAssist);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText(selectedShooter + " " + selectedAssist);

        alert.showAndWait();

        selectedShooter = null;
        selectedAssist = null;
    }

    @FXML
    void onRefreshTable(ActionEvent event) {
        nameGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("goals"));
        goalsStandingsTable.setItems(FXCollections.observableList(playerDao.getAll()));
        numGoalsColumn.setSortType(TableColumn.SortType.DESCENDING);
        goalsStandingsTable.getSortOrder().add(numGoalsColumn);
        goalsStandingsTable.sort();

        nameAssistsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numAssistsColumn.setCellValueFactory(new PropertyValueFactory<>("assists"));
        assistsStandingsTable.setItems(FXCollections.observableList(playerDao.getAll()));
        numAssistsColumn.setSortType(TableColumn.SortType.DESCENDING);
        assistsStandingsTable.getSortOrder().add(numAssistsColumn);
        assistsStandingsTable.sort();
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
}

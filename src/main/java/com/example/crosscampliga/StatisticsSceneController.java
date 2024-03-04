package com.example.crosscampliga;

import com.example.crosscampliga.storage.DaoFactory;
import com.example.crosscampliga.storage.Player;
import com.example.crosscampliga.storage.PlayerDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StatisticsSceneController {

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
    private TableView<Player> goalieSavesTable;
    @FXML
    private TableColumn<Player, String> nameGoalieColumn;
    @FXML
    private TableColumn<Player, Double> percentageSavesColumn;

    PlayerDao playerDao = DaoFactory.INSTANCE.getPlayerDao();
    private ObservableList<Player> playersAssist;
    private ObservableList<Player> goaliesModel;

    @FXML
    public void initialize(){
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

        List<Player> goalies = playerDao.getAllGoalies();
        goaliesModel = FXCollections.observableList(goalies);

        nameGoalieColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        percentageSavesColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();

            double percentage = 0;
            if (player.getFailedSaves() != 0) {
                percentage = player.getSaves() == 0 ? 0 :
                        Math.round(((double) player.getSaves() / (player.getSaves() + player.getFailedSaves())) * 100);
            }else{
                percentage = player.getSaves() == 0 ? 0 : 100;
            }

            return new SimpleDoubleProperty(percentage).asObject();
        });
        goalieSavesTable.setItems(goaliesModel);

    }
}

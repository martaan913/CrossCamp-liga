package com.example.crosscampliga;

import com.example.crosscampliga.storage.DaoFactory;
import com.example.crosscampliga.storage.PlayerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Objects;

public class MainSceneController {
    @FXML
    private Button addButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<String> positionChoiceBox;

    @FXML
    private void initialize(){

        PlayerDao playerDao = DaoFactory.INSTANCE.getPlayerDao();

        positionChoiceBox.getItems().addAll("Hrac", "Brankar");
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

    }
}

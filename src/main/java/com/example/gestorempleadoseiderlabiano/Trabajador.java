package com.example.gestorempleadoseiderlabiano;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Trabajador implements Initializable{
    public TextField nameText;
    public ComboBox<String> poschoice;
    public TextField salaryText;
    public Button insertButton;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        poschoice.setItems(FXCollections.observableArrayList("Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst ProgrammerAnalyst Programmer", "Junior Programmer"));
    }


    public void anyadirTrabajador()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Mensaje");
        alert.setContentText("Empleado " + nameText.getText() + " introducido en la base de datos");
        alert.setTitle("HECHO");
        alert.show();
    }

}
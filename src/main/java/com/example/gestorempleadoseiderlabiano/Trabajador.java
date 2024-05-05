package com.example.gestorempleadoseiderlabiano;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
public class Trabajador implements Initializable {
    public TextField nameText;
    public ComboBox<String> poschoice;
    public TextField salaryText;
    public Button insertButton;
    @FXML
    public ListView<String> consultText;
    @FXML
    public Button refreshBTN;
    @FXML
    public Button editBTN;
    @FXML
    public Button deleteBTN;
    @FXML
    public Label nameLabel;
    @FXML
    public Label posLabel;
    @FXML
    public Label salaryLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label idLabel;
    public Button loadDataBTN;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        poschoice.setItems(FXCollections.observableArrayList("Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst ProgrammerAnalyst Programmer", "Junior Programmer"));
    }


    public void anyadirTrabajador() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Mensaje");
        alert.setContentText("Empleado " + nameText.getText() + " introducido en la base de datos");
        alert.setTitle("HECHO");
        alert.show();
    }

    public void trabajador() {
        if (nameText.getText().isEmpty() || salaryText.getText().isEmpty() || poschoice.getItems().isEmpty()) {
            error();
        } else {
            insertar();
            anyadirTrabajador();
        }
    }

    public void error() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText("Si no rellena los campos no se puede añadir a la lista de los trabajadores");
        alert.setTitle("IMPOSIBLE AÑADIR TRABAJADOR");
        alert.show();
    }

    public void insertar() {
        Connection miConexion = null;
        try {
            miConexion = conectarBBDD();
            PreparedStatement statmen = miConexion.prepareStatement("insert into Empleado (nombre, puesto, salario, fecha) values (?,?,?,now())");
            statmen.setString(1, nameText.getText());
            statmen.setString(2, poschoice.getValue());
            statmen.setInt(3, Integer.parseInt(salaryText.getText()));
            statmen.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No funciona la conexion");
        } finally {
            if (miConexion != null) {
                try {
                    miConexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar");
                }
            }
        }
    }

    public Connection conectarBBDD() {
        String url = "jdbc:mysql://localhost:3306/gestorEmpleados";
        String usuario = "root";
        String contrasenya = "root";
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenya);
            if (conexion != null) {
                return conexion;
            }
        } catch (SQLException e) {
            System.out.println("Error al acceder");
        }
        return null;
    }

    public void parsearLinea() {
        File miFichero = new File("src/main/resources/com/example/gestorempleadoseiderlabiano/trabajadores (1).txt");
        try {
            Scanner miScaner = new Scanner(miFichero);
            while (miScaner.hasNext()) {
                String[] trabajador;
                trabajador = miScaner.nextLine().split(";");
                String Nombre = trabajador[0];
                String Puesto = trabajador[1];
                int Salario = Integer.parseInt(trabajador[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo");
        }
    }

    public void listView() {
        ArrayList<String> entrada = addNombre();
        for (String s : entrada) {
            consultText.getItems().add(s);
        }
    }

    public ArrayList<String> addNombre() {
        ArrayList<String> nombres = new ArrayList<>();
        try {
            Connection conect = conectarBBDD();
            Statement miStatment = conect.createStatement();
            ResultSet resul = miStatment.executeQuery("select nombre from Empleado");
            while (resul.next()) {
                nombres.add(resul.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar");
        }
        return nombres;
    }

    public void eliminarElegido(String nombre) {
        try {
            Connection connection = conectarBBDD();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from Empleado where nombre = ?");
            preparedStatement.setString(1, nombre);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al Borrar");
        }
    }

    public void eliminar() {
        eliminarElegido(consultText.getSelectionModel().getSelectedItem());
    }

    public void refresh() {
        try {
            Connection connection = conectarBBDD();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select nombre from Empleado");
            consultText.getItems().clear();
            while (resultSet.next()) {
                consultText.getItems().add(resultSet.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al refrescar");
        }
    }
}

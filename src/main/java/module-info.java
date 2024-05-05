module com.example.gestorempleadoseiderlabiano {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.gestorempleadoseiderlabiano to javafx.fxml;
    exports com.example.gestorempleadoseiderlabiano;
}
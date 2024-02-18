module com.example.crosscampliga {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires spring.jdbc;
    requires spring.tx;
    requires java.sql;


    opens com.example.crosscampliga to javafx.fxml;
    exports com.example.crosscampliga;
}
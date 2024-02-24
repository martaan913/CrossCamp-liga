module com.example.crosscampliga {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.jdbc;
    requires spring.tx;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;


    opens com.example.crosscampliga to javafx.fxml;
    opens com.example.crosscampliga.storage to javafx.base;
    exports com.example.crosscampliga;
}
module com.example.familycash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.familycash to javafx.fxml;
    exports com.example.familycash;
}
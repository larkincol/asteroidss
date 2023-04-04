module com.example.asteroidss {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.asteroidss to javafx.fxml;
    exports com.example.asteroidss;
}
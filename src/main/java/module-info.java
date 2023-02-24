module com.example.konverter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.konverter to javafx.fxml;
    exports com.example.konverter;
}
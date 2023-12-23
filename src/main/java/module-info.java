module com.example.yenia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens com.example.yenia to javafx.fxml;
    exports com.example.yenia;
}
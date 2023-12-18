module com.example.yenia {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.yenia to javafx.fxml;
    exports com.example.yenia;
}
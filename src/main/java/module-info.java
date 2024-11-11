module com.example.banquitofeliz {
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.controlsfx.controls;

    opens testeos to javafx.graphics;
    exports testeos;
    exports testeos.Controllers;
    exports testeos.Controllers.Admin;
    exports testeos.Controllers.Client;
    exports testeos.Models;
    exports testeos.Views;
}
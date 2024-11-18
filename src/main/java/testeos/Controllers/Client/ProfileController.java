package testeos.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import testeos.Models.DatabaseDriver;
import testeos.Models.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Label profile_name_lbl;
    public Label profile_lastname_lbl;
    public Label profile_address_lbl;
    public Label profile_password_lbl;
    public Label profile_date_lbl;

    private DatabaseDriver databaseDriver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseDriver = new DatabaseDriver();
        bindData();
    }

    private void bindData() {
        profile_name_lbl.textProperty().bind(Model.getInstance().getClient().firstNameProperty());
        profile_lastname_lbl.textProperty().bind(Model.getInstance().getClient().lastNameProperty());
        profile_address_lbl.textProperty().bind(Model.getInstance().getClient().pAddressProperty());
        bindPassword();
        profile_date_lbl.textProperty().bind(Model.getInstance().getClient().dateProperty().asString());

       }

    private void bindPassword() {
        String pAddress = Model.getInstance().getClient().pAddressProperty().get();
        ResultSet resultSet = databaseDriver.getPassword(pAddress);
        try {
            profile_password_lbl.setText(resultSet.getString("Password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
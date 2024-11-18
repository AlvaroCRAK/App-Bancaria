package testeos.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import testeos.Models.Model;
import testeos.Views.AdminMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    public void addListeners () {
        create_client_btn.setOnAction(e -> onCreateClient());
        clients_btn.setOnAction(e -> onClients());
        deposit_btn.setOnAction(e -> onDeposit());
        logout_btn.setOnAction(e -> onLogout());
    }

    private void onCreateClient () {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onClients () {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
    }

    private void onDeposit () {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DEPOSIT);
    }

    private void onLogout(){
        // Obtener el stage
        Stage stage = (Stage) clients_btn.getScene().getWindow();
        // Cerramos la ventana del admin
        Model.getInstance().getViewFactory().closeStage(stage);
        // Mostrar la ventana del login
        Model.getInstance().getViewFactory().showLoginWindow();
        // Cambiar la SuccessAdminFlag a false
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}

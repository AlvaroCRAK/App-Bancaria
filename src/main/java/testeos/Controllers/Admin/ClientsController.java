package testeos.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import testeos.Models.Client;
import testeos.Models.DatabaseDriver;
import testeos.Models.Model;
import testeos.Views.ClientCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClientsList();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e -> new ClientCellFactory());

        //delete_btn.setOnAction(event -> borrarCliente());
    }

    private void initClientsList() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }

    private void borrarCliente() {

        try {
            ///Model.getInstance().getDatabaseDriver().deleteClient(delete_client_fld.getText());
            //Model.getInstance().getClients().remove(client);
        } catch (Exception e) {
            System.out.println("Cliente no encontrado");
        }

    }
}

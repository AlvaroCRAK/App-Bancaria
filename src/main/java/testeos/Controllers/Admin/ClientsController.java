package testeos.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import testeos.Models.Client;
import testeos.Models.Model;
import testeos.Views.ClientCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e -> new ClientCellFactory());
    }

    private void initData() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }
}

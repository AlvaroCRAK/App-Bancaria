package testeos.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import testeos.Models.Model;
import testeos.Models.Transaction;
import testeos.Views.TransactionCellFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transactions_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAllTransactionsList();
        transactions_listview.setItems(Model.getInstance().getAllTransactions());
        transactions_listview.setCellFactory(event -> new TransactionCellFactory());
    }

    private void initAllTransactionsList() {
        if(Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
    }
}

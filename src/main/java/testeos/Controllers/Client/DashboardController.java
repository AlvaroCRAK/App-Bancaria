package testeos.Controllers.Client;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import testeos.Models.Model;
import testeos.Models.Transaction;
import testeos.Views.TransactionCellFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_number;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView<Transaction> transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initLatestTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(event -> new TransactionCellFactory());
        send_money_btn.setOnAction(event -> onSendMoney());
        accountSummary();
    }

    private void bindData() {
        user_name.textProperty().bind(Bindings.concat("Hola, ").concat(Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Hoy, " + LocalDate.now());
        checking_bal.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        savings_bal.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        savings_acc_number.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    private void initLatestTransactionsList(){
        if(Model.getInstance().getLatestTransactions().isEmpty()){
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onSendMoney(){
        String receiver = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());
        String message = message_fld.getText();
        String sender = Model.getInstance().getClient().pAddressProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(receiver);
        try {
            if (resultSet.isBeforeFirst()){
                Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Sustraer dinero de la cuenta del enviador
        Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "SUB");
        // Actualizamos el balance de la cuenta de ahorros en el objeto cliente
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(sender));
        // Guardamos la nueva transaccion
        Model.getInstance().getDatabaseDriver().newTransaction(sender, receiver, amount, message);
        // Limpiamos los campos
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }

    // Metodo que calcula los gastos y ingresos
    private void accountSummary() {
        double income = 0;
        double expenses = 0;
        if (Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
        }
        for (Transaction transaction : Model.getInstance().getAllTransactions()) {
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().pAddressProperty().get())){
                expenses = expenses + transaction.amountProperty().get();
            } else {
                income = income + transaction.amountProperty().get();
            }
        }
        income_lbl.setText("+ $" + income);
        expense_lbl.setText("- $" + expenses);
    }
}

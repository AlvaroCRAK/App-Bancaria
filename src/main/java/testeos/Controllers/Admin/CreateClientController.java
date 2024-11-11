package testeos.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import testeos.Models.Model;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_lbl;
    public CheckBox pAddress_box;
    public Label pAddress_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;

    private String payeeAddress;
    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(event -> createClient());
        pAddress_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        });
        ch_acc_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                createCheckingAccountFlag = true;
            }
        });
        sv_acc_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                createSavingsAccountFlag = true;
            }
        });
    }

    private void createClient(){
        // Creamos la cuenta de cheques
        if (createCheckingAccountFlag){
            createAccount("Checking");
        }

        // Creamos la cuenta de ahorros
        if (createSavingsAccountFlag){
            createAccount("Savings");
        }

        // Creamos al cliente
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String password = password_lbl.getText();
        Model.getInstance().getDatabaseDriver().createClient(fName, lName, payeeAddress, password, LocalDate.now());
        error_lbl.setText("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
        error_lbl.setText("Cliente creado exitosamente!");
        emptyFields();
    }

    private void createAccount(String accountType) {
        double balance = Double.parseDouble(ch_amount_fld.getText());
        // Generar numero de cuenta
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999)+1000);
        String accountNumber = firstSection + " " + lastSection;
        // Creamos la cuenta de ahorros y de cheques
        if (accountType.equals("Checking")) {
            Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber, 10, balance);
        }
        else {
            Model.getInstance().getDatabaseDriver().createSavingsAccount(payeeAddress, accountNumber, 2000, balance);
        }
    }

    private void onCreatePayeeAddress() {
        if((fName_fld.getText() != null) & (lName_fld.getText() != null)){
            pAddress_lbl.setText(payeeAddress);
        }
    }

    private String createPayeeAddress() {
        int id = Model.getInstance().getDatabaseDriver().getLastClientsId() + 1;
        char fChar = Character.toLowerCase(fName_fld.getText().charAt(0));
        return "@"+fChar+lName_fld.getText()+id;
    }

    private void emptyFields() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_lbl.setText("");
        pAddress_box.setSelected(false);
        pAddress_lbl.setText("");
        ch_acc_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_acc_box.setSelected(false);
        sv_amount_fld.setText("");
    }

}

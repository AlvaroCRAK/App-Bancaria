package testeos;

import javafx.application.Application;
import javafx.stage.Stage;
import testeos.Models.Model;

public class App extends Application {


    @Override
    public void start(Stage stage){
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}

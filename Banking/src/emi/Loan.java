package emi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author JAHANVI
 */
public class Loan extends Application{
    public static Stage stage = null;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("loanfxml.fxml"));
       Scene s = new Scene(root);
       stage.setScene(s);
       this.stage=stage;
       stage.show();
       
        
    }
}
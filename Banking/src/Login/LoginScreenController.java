package Login;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
/*
@author ARYAN
*/
public class LoginScreenController implements Initializable {
    
     private double xOffset = 0;
    private double yOffset = 0;
    
    public static Stage stage=null;
    public static String acc;
    @FXML 
    private Pane main_area;
    @FXML
    private TextField accountno;
    @FXML
    private PasswordField pin;
    @FXML
    private FontAwesomeIconView ico;   
     @FXML
    public void click(MouseEvent event)
    {
         xOffset = event.getSceneX();
         yOffset = event.getSceneY();
        
    }
    
    public void drag(MouseEvent event)
    {
      LoginScreenController.stage.setX(event.getScreenX() - xOffset);
      LoginScreenController.stage.setY(event.getScreenY() - yOffset);   
    }
    
     @FXML
    private void miniApp(MouseEvent event)
    {
        Stage stage = (Stage) ico.getScene().getWindow();
        stage.setIconified(true);
        
    }
     @FXML
    private void closeApp(MouseEvent event)
    {
        Platform.exit();
        System.exit(0);
    }

   /** 
    * Loads the create account stage.
    * @param event
    * @throws IOException 
    */
    @FXML 
    public void createAccount(MouseEvent event) throws IOException
    {
        Parent fxml = FXMLLoader.load(getClass().getResource("/createaccount/CreateAccount.fxml"));
        main_area.getChildren().removeAll();
        main_area.getChildren().addAll(fxml);

    }
   /**
    * Loads the forgot password stage.
    * @param event
    * @throws IOException 
    */
     @FXML
     public void forgotPassword(MouseEvent event) throws IOException
    {
        main_area.getChildren().removeAll();
        
        Parent fxml = FXMLLoader.load(getClass().getResource("/forgotpass/ForgotPass.fxml"));
        main_area.getChildren().addAll(fxml);       
    }
     
     /**
      * Logs in the user.Navigates user to dashboard/dashboardController.java
      * @param event 
      */
    public void LoginAccount(MouseEvent event)
    {
        try {
            
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);
            
            ps.setString(1, accountno.getText());
            ps.setString(2, pin.getText());
            acc = accountno.getText();
            
            rs = ps.executeQuery();
            
            if (rs.next()) {                
                ((Node) event.getSource()).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.getIcons().add(new Image("/Images/icon2.jpg"));
                stage.setTitle("Ace Bank");
                stage.show();
                this.stage = stage;
                
            } else {
                
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in Login");
                a.setContentText("Your Account no or Pin is incorrect.");
                a.showAndWait();
                
            }
        } catch (ClassNotFoundException classNotFoundException) {
                 Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Exception Found");
                a.setContentText("Exception Found -"+classNotFoundException.getMessage());
                a.showAndWait();
            
        } catch (SQLException sQLException) {
                 Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Exception Found");
                a.setContentText("Exception Found -"+sQLException.getMessage());
                a.showAndWait();
            
        } catch (IOException iOException) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Exception Found");
                a.setContentText("Exception Found -"+iOException.getMessage());
                a.showAndWait();
                
            
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

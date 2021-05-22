
package dashboard;

import Login.LoginScreenController;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class MainScreemController implements Initializable {
     
     @FXML
     private Label body;
     @FXML
     private Label name;
    @FXML
    private Pane mainscreen;
     
 
    @FXML
    public void transfer(MouseEvent event) throws IOException
    {
      Parent fxml = FXMLLoader.load(getClass().getResource("/transferamount/TransferAmount.fxml"));
        mainscreen.getChildren().removeAll();
        mainscreen.getChildren().addAll(fxml);
    }
     @FXML
    public void SIP(MouseEvent event) throws IOException
    {
      Parent fxml = FXMLLoader.load(getClass().getResource("/SipCalc/sipcal.fxml"));
        mainscreen.getChildren().removeAll();
        mainscreen.getChildren().addAll(fxml);
    }
    @FXML
    public void view(MouseEvent event) throws IOException
    {
      Parent fxml = FXMLLoader.load(getClass().getResource("/transaction/Transaction.fxml"));
        mainscreen.getChildren().removeAll();
        mainscreen.getChildren().addAll(fxml);
    } 
   
    public void setInfo()
    {
         Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
           String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
         
            rs=ps.executeQuery();

            
            
            if(rs.next())
            {
               
                name.setText(rs.getString("Name"));
              
           
            }
            else
            {
             
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Login");
            a.setContentText("Your Account no or Pin is incorrect.");
            a.showAndWait();
           
            }
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in creating Account.");
            a.setContentText("1.Your Account is not created there is some technical issue.here"+ e.getMessage());
            a.showAndWait();
            
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        body.setText("Ace Bank is an Indian multinational, public sector banking and financial\nservices statutory body headquartered in Pune, Maharashtra.\n\nGrowing with times, Ace Bank continues to redefine banking in India, as it\naims to offer responsible and sustainable Banking solutions. ");
        setInfo();
    }
    
}

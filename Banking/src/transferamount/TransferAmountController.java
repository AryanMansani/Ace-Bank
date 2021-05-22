
package transferamount;

import Login.LoginScreenController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import withdraw.WithdrawMail;


public class TransferAmountController implements Initializable {

     Connection con=null;
     PreparedStatement ps=null;
     ResultSet rs = null;
 
    @FXML
    private Label accountno;
    @FXML
    private Label balance;
    @FXML
    private TextField amt_field;
    @FXML
    private TextField account_no_field;
    @FXML
    private TextField pin_field;
    
    private String email;
    
    Calendar cal = Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH);
    int day=cal.get(Calendar.DAY_OF_MONTH);
    int hour = cal.get(Calendar.HOUR);
    int mins = cal.get(Calendar.MINUTE);
    int seconds = cal.get(Calendar.SECOND);
    int daynight = cal.get(Calendar.AM_PM);
    
    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    Date d = new Date();
    String date = dateformat.format(d);
    
    LocalTime localtime = LocalTime.now();
    DateTimeFormatter dt = DateTimeFormatter.ofPattern("hh:mm:s a");
    String time = localtime.format(dt);
    
    
       public void setInfo()
    {
         
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
         
            rs=ps.executeQuery();

            
            
            if(rs.next())
            {
             
                accountno.setText(rs.getString("AccountNo"));
                balance.setText(rs.getString("Balance"));
                email=(rs.getString("Email"));
                
              
           
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
            a.setContentText("2Your Account is not created there is some technical issue."+ e.getMessage());
            a.showAndWait();
            
        }
        
    }
       public void checkButton(MouseEvent event) throws ClassNotFoundException, SQLException
       {
             Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, account_no_field.getText());
         
            rs=ps.executeQuery();

            
            
            if(rs.next())
            {
             
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Account Information");
            a.setHeaderText("Below is the information of Account");
            a.setContentText("Account No ="+account_no_field.getText()+"\nName = "+rs.getString("Name"));
            a.showAndWait(); 
                
              
           
            }
            else
            {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Account Not Found");
            a.setHeaderText("Account Not Found");
            a.setContentText("Please check your Account No.");
            a.showAndWait(); 
                
            }
       }
  public void transferAmountButton()
    {
        int amt=Integer.parseInt(amt_field.getText());
        if(amt>0)
        {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
           String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
            ps.setString(2, pin_field.getText());
            
         
            rs=ps.executeQuery();

            
            
             if(rs.next())
            {
               
              int tramt = Integer.parseInt(amt_field.getText());
              int ta=Integer.parseInt(balance.getText());
              String emailS=rs.getString("Email");
              
              
              if(tramt>ta)
              {
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in Transfer");
                a.setContentText("Your don't have enough balance.");
                a.showAndWait();
                  
              }
              else
              {
                  int total=ta-tramt;
                  String sql1 = "UPDATE userdata SET Balance='"+total+"' WHERE AccountNo='"+LoginScreenController.acc+"'";
                  ps=con.prepareStatement(sql1);
                  ps.execute();
                  
                  
                  String sql2 = "SELECT * FROM userdata WHERE AccountNo=?";
                  ps = con.prepareStatement(sql2);
                  ps.setString(1, account_no_field.getText());
                  rs=ps.executeQuery();
                  
                  if(rs.next())
                  {
                  
                   int cur=Integer.parseInt(amt_field.getText());
                   int prev=Integer.parseInt(rs.getString("Balance"));
                   String emailR=rs.getString("Email");
                   
                   int total1=cur+prev;
                      
                   
                  String sql4 = "UPDATE userdata SET Balance='"+total1+"' WHERE AccountNo='"+account_no_field.getText()+"'";
                  ps=con.prepareStatement(sql4);
                  ps.execute();
                  
                  
                  
                  String sql5= "INSERT INTO transferamount(AccountNo, Amount, SendTo, Date, Time) VALUES (?,?,?,?,?)";
                  ps = con.prepareStatement(sql5);
                  ps.setString(1, LoginScreenController.acc);
                  ps.setString(2, String.valueOf(amt_field.getText()));
                  ps.setString(3, String.valueOf(account_no_field.getText()));
                  ps.setString(4, date);
                  ps.setString(5, time);
                  
                  int i = ps.executeUpdate();
                  
                  if(i>0)
                  {
                       //Email Verification Here
                      ReceiverMail.sendMail(emailR, cur, total1,account_no_field.getText() , date);
                      SenderMail.sendMail(emailS, cur , total, LoginScreenController.acc, date);
                       Alert a=new Alert(Alert.AlertType.INFORMATION);
                       a.setTitle("Amount Transfer");
                       a.setHeaderText("Amount Transfered Sucessfully");
                       a.setContentText("Amount "+cur+" has been sucessfully transffered\n"+"To Account No = "+account_no_field.getText());
                       a.showAndWait();
                       
                       account_no_field.setText("");
                       amt_field.setText("");
                       pin_field.setText("");
                       balance.setText(String.valueOf(total));
                  }
                  
                  
              }
           
            
            else
            {
             
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Transfer.");
            a.setContentText("No Account Found!");
            a.showAndWait();
           
            }
            
            
                 
            
        }
        
        }
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in creating Account.");
            a.setContentText("Theres been a error in transfer. "+ e.getMessage());
            a.showAndWait();
            
        }
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Transfer.");
            a.setContentText("Incorrect Transfer Amount.");
            a.showAndWait();
        }
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        setInfo();
      
    }    
    
}

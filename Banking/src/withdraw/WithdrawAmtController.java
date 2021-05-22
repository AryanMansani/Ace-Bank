
package withdraw;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import Login.LoginScreenController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class WithdrawAmtController implements Initializable {
    
    @FXML
    private Label accountno;
    @FXML
    private Label balance;
    @FXML
    private TextField amt_field;
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
      public boolean validateamt()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(amt_field.getText());
        if(m.find()&&m.group().equals(amt_field.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect Amount Format");
            a.setHeaderText("Your Amount is wrong.");
            a.setContentText("Please enter amount again.");
            a.showAndWait();
            return false;
        }
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
  public void withdrawButton()
    {
        if(validateamt())
        {
        int amt=Integer.parseInt(amt_field.getText());;
        if(amt>0)
        {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs = null;
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
               
              int wda = Integer.parseInt(amt_field.getText());
              int ta=Integer.parseInt(balance.getText());
              
              if(wda>ta)
              {
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in Withdraw");
                a.setContentText("Your don't have enough balance.");
                a.showAndWait();
                  
              }
              else
              {
                  int total=ta-wda;
                  String sql1 = "UPDATE userdata SET Balance='"+total+"' WHERE AccountNo='"+LoginScreenController.acc+"'";
                  ps=con.prepareStatement(sql1);
                  
                  ps.execute();
                  
                  String sql2= "INSERT INTO withdraw(AccountNo, WithdrawAmount, RemainingAmount, Date, Time) VALUES (?,?,?,?,?)";
                  ps = con.prepareStatement(sql2);
                  ps.setString(1, LoginScreenController.acc);
                  ps.setString(2, String.valueOf(wda));
                  ps.setString(3, String.valueOf(total));
                  ps.setString(4, date);
                  ps.setString(5, time);
                  
                  int i = ps.executeUpdate();
                  
                  if(i>0)
                  {
                       //Email Verification Here
                      WithdrawMail.sendMail(email,String.valueOf(wda),String.valueOf(total),LoginScreenController.acc,date);
                       Alert a=new Alert(Alert.AlertType.INFORMATION);
                       a.setTitle("Amount Withdrawn");
                       a.setHeaderText("Amount Withdrawn Sucessfully");
                       a.setContentText("Amount "+wda+" has been sucessfully withdrawn\n"+"Current Balance = "+total);
                       a.showAndWait();
                       
                       amt_field.setText("");
                       pin_field.setText("");
                       balance.setText(String.valueOf(total));
                  }
                  
                  
              }
           
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
         else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Transfer.");
            a.setContentText("Incorrect Transfer Amount");
            a.showAndWait();
        }
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      setInfo();
    }    

   
}

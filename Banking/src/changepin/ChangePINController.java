
package changepin;

import Login.LoginScreenController;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import dashboard.DashboardController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class ChangePINController implements Initializable {
    
    DashboardController d = new DashboardController();
    @FXML
    private TextField oldpin;
    @FXML
    private TextField newpin;
    @FXML
    private TextField confirmpin;
     @FXML
    private TextField otpfield;
    private int otp;
    Calendar cal = Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH);
    int day=cal.get(Calendar.DAY_OF_MONTH);
    int hour = cal.get(Calendar.HOUR);
    int mins = cal.get(Calendar.MINUTE);
    int seconds = cal.get(Calendar.SECOND);
    int daynight = cal.get(Calendar.AM_PM);
    
    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    Date d1 = new Date();
    String date = dateformat.format(d1);
    
    
  /**
   * Generates OTP to change user PIN
   * @throws Exception 
   */
    public void generateOtp()throws Exception
    {
        otp = OTP.OtpMail.sendMail(dashboard.DashboardController.email);
        Alert a=new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("OTP SENT");
        a.setHeaderText("OTP SENT");
        a.setContentText("OTP is sent on the email address  "+dashboard.DashboardController.email);
        a.showAndWait();
        otpfield.setEditable(true);
        
    }
    /**
     * validates user's OTP
     * @return boolean
     */
    public boolean validateOtp()
    {
        if(otp==Integer.parseInt(otpfield.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect OTP");
            a.setHeaderText("Incorrect OTP");
            a.setContentText("Please enter  correct OTP.");
            a.showAndWait();
            return false;    
        
        }
    }
      /**
       * validates user's PIN
       * @return boolean
       */  
      public boolean validatePin()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(newpin.getText());
        if(m.find()&&m.group().equals(newpin.getText()))
        {
            if(newpin.getText().equals(oldpin.getText()))
            {
                 Alert a=new Alert(Alert.AlertType.ERROR);
                 a.setTitle("Incorrect Pin");
                 a.setHeaderText("Pin is incorrect.");
                 a.setContentText("New pin cant be same as Old pin.");
                 a.showAndWait();
                return false;
            }
            return true;
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect Pin");
            a.setHeaderText("Your Pin is wrong");
            a.setContentText("Only numbers are allowed in Pin");
            a.showAndWait();
            return false;
        }
    }
      /**
       * Modifies users PIN and updates to MySQL database
       * @param event 
       */
     
    public void changeButton(MouseEvent event)
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
            ps.setString(2, oldpin.getText());
         
            rs=ps.executeQuery();

            
            
            if(rs.next()&&validatePin()&&validateOtp())
            {
               
                  if(newpin.getText().equals(confirmpin.getText()))
                  {
                  String sql1 = "UPDATE userdata SET PIN='"+newpin.getText()+"' WHERE AccountNo='"+LoginScreenController.acc+"'";
                  ps=con.prepareStatement(sql1);
                  
                  ps.execute();
                       PinChange.sendMail(dashboard.DashboardController.email,LoginScreenController.acc, date);
                       Alert a=new Alert(Alert.AlertType.INFORMATION);
                       a.setTitle("PIN Updated");
                       a.setHeaderText("PIN Sucessfully changed");
                       a.setContentText("Your pin has been changed now you have to login again");
                       a.showAndWait();
                       
                       oldpin.setText("");
                       newpin.setText("");
                       confirmpin.setText("");
                       
                       d.logout(event);
                      
                
                  }
                  else
                  {
                      Alert a=new Alert(Alert.AlertType.ERROR);
                      a.setTitle("Error");
                      a.setHeaderText("New Pin and Confirm Pin mismatch");
                      a.setContentText("New Pin and Confirm Pin doesnt match");
                      a.showAndWait();
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
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}

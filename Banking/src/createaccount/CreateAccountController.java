package createaccount;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import Login.Banking;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import javax.mail.MessagingException;

/*
@author ARYAN
*/

public class CreateAccountController implements Initializable {
 
    private FileChooser filechooser = new FileChooser();
    private File file;
    private FileInputStream fis;
    @FXML
    private ImageView pic;
    @FXML
    private TextField name;
    @FXML
    private TextField idcardno;
    @FXML
    private TextField mobileno;
    @FXML
    private TextField city;
    @FXML
    private TextField address;
    @FXML
    private TextField accountno;
    @FXML
    private TextField pin;
    @FXML
    private TextField balance;
    @FXML
    private TextField answer;
    @FXML
    private TextField email;
    @FXML
    private TextField otpfield;
    @FXML
    private DatePicker dob;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> maritialstatus;
    @FXML
    private ComboBox<String> religion;
    @FXML
    private ComboBox<String> accounttype;
    @FXML
    private ComboBox<String> questions;
    private int otp;
    
    ObservableList<String> list = FXCollections.observableArrayList("Male", "Female", "Other");
    ObservableList<String> list1 = FXCollections.observableArrayList("Un-Married", "Married");
    ObservableList<String> list2 = FXCollections.observableArrayList("Hindu", "Christian", "Islam", "Other");
    ObservableList<String> list3 = FXCollections.observableArrayList("Current", "Savings");
    ObservableList<String> list4 = FXCollections.observableArrayList("What is your pet name?", "What is your childhood town?", "What is your nick name?");
    
    public boolean flag=false;
    /**
     * checks if input name is valid or not
     * @return boolean
     */
    public boolean validateName()
    {
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(name.getText());
        if(m.find()&&m.group().equals(name.getText())&&name.getText().length()>3)
        {
            return true;
        }
        else
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Incorrect Name");
            a.setHeaderText("Name entered is invalid");
            a.setContentText("Please enter your name again.");
            name.setText("");
            a.showAndWait();
            return false;
        }
    }
    /**
     * checks if input PIN is valid or not
     * @return boolean
     */
     public boolean validatePin()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(pin.getText());
        if(m.find()&&m.group().equals(pin.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Incorrect Pin");
            a.setHeaderText("Your Pin is wrong");
            a.setContentText("Only numbers are allowed in Pin");
            pin.setText("");
            a.showAndWait();
            return false;
        }
    }
    /**
     * checks if input Number is valid or not
     * @return boolean
     */
    public boolean validateMobileNo()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobileno.getText());
        if(m.find()&&m.group().equals(mobileno.getText())&&mobileno.getText().length()==10)
        {
            return true;
        }
        else
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Incorrect MobileNo");
            a.setHeaderText("Your MobileNo is wrong");
            a.setContentText("Only numbers are allowed in mobile no");
            mobileno.setText("");
            a.showAndWait();
            return false;
        }
    }
   /**
    * generates One Time Password for email verification
    * @throws MessagingException 
    */
    public void generateOtp()throws MessagingException    {
       
             otp = OTP.OtpMail.sendMail(email.getText());
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle("OTP SENT");
            a.setHeaderText("OTP SENT");
            a.setContentText("OTP is sent on the email address  " + email.getText());
            a.showAndWait();
            flag = true;
            otpfield.setEditable(true);       
    }
    /**
     * Validates user OTP input
     * @return boolean
     */
    public boolean validateOtp()
    {  
        try{
        if(flag)
            
        {
            if(otp==Integer.parseInt(otpfield.getText()))
            {
                
                return true;
            }
            else
            {
                Alert a=new Alert(AlertType.ERROR);
                a.setTitle("Incorrect OTP");
                a.setHeaderText("Incorrect OTP");
                a.setContentText("Please enter  correct OTP.");
                a.showAndWait();
                otpfield.setText("");
                return false;
                
            }
        }
        else
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Error in OTP Verification");
            a.setHeaderText("OTP not Generated.");
            a.setContentText("Please generate OTP.");
            a.showAndWait();
            otpfield.setText("");
            return false;
        }
        }catch(Exception ex)
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Incorrect OTP");
            a.setHeaderText("Incorrect OTP");
            a.setContentText("Please enter  correct OTP.");
            a.showAndWait();
            otpfield.setText("");
            return false;    
        }
    }
    /**
     * checks if input number is valid or not 
     * @return boolean 
     */
     public boolean validateIdNo()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(idcardno.getText());
        if(m.find()&&m.group().equals(idcardno.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Incorrect ID No");
            a.setHeaderText("Your ID No is wrong");
            a.setContentText("Only numbers are allowed in ID no");
            idcardno.setText("");
            a.showAndWait();
            return false;
        }
    }
     
     public void clearAllFields()
     {
         name.clear();
         idcardno.clear();
         mobileno.clear();
         gender.getSelectionModel().clearSelection();
         religion.getSelectionModel().clearSelection();
         maritialstatus.getSelectionModel().clearSelection();
         dob.getEditor().clear();
         city.clear();
         address.clear();
         pin.clear();
         accounttype.getSelectionModel().clearSelection();
         balance.clear();
         questions.getSelectionModel().clearSelection();
         answer.clear();
         Image img=new Image("/Images/150.png");
         pic.setImage(img);
         accountno.setText((String.valueOf((generateAccuntNo()))));
     }
     public int generateAccuntNo()
     {
         Random ran=new Random();
         int num = ran.nextInt(89999999) + 1000000;
         return num;
     }
     public boolean validatebalance()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(balance.getText());
        if(m.find()&&m.group().equals(balance.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Incorrect Balance Format");
            a.setHeaderText("Your Balance is wrong");
            a.setContentText("Only numbers are allowed in Balance");
            balance.setText("");
            a.showAndWait();
            return false;
        }
    }
     /**
      * checks if users age is valid ( > 14 ) or not
      * @return boolean 
      */
     public boolean validateDob()
     {
         try{
         String dobb = ((TextField) dob.getEditor()).getText();
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         Date d = sdf.parse(dobb);
         Calendar c = Calendar.getInstance();
         c.setTime(d);
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH) + 1;
         int date = c.get(Calendar.DATE);
         LocalDate l1 = LocalDate.of(year, month, date);
         LocalDate now1 = LocalDate.now();
         Period diff1 = Period.between(l1, now1);
         System.out.println("age:" + diff1.getYears() + "years");
        
         if(diff1.getYears()>14)
         {
             return true;
         }
         Alert a = new Alert(AlertType.ERROR);
         a.setTitle("DOB Error");
         a.setHeaderText("Invalid Date of Birth");
         
         a.setContentText("Minimum age to open a account is 15 years. Please try again later.");
         a.showAndWait();
         return false;
          }catch(ParseException ex)
         {
           Alert a = new Alert(AlertType.ERROR);
           a.setTitle("DOB Error");
           a.setHeaderText("Invalid Date of Birth");
         
           a.setContentText("Please enter your date of birth.");
           a.showAndWait();
         return false;
         }

     }
     /**
      * navigates to Login/LoginScreenController.java
      * @param event
      * @throws IOException 
      */
    public void backToLogin(MouseEvent event) throws IOException
    {
        Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/Login/LoginScreen.fxml")));
        
    }
    /**
     * Allows user to add image to user profile
     * @param e 
     */
    public void setUpPic(MouseEvent e)
    {
        filechooser.getExtensionFilters().add(new ExtensionFilter("Images Files", "*.png", "*.jpg"));
        file= filechooser.showOpenDialog(null);
        if(file!=null)
        {
            Image img = new Image(file.toURI().toString(), 150, 150, true, true);
            pic.setImage(img);
            pic.setPreserveRatio(true);
            
        }
        
    }
    /**
     * checks if user email-id is valid or not
     * @return boolean
     */
      public boolean validateEmail()
    {
        Pattern p = Pattern.compile("[a-zA-Z0-9@.]+");
        Matcher m = p.matcher(email.getText());
        if(m.find()&&m.group().equals(email.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect Email.");
            a.setHeaderText("Email entered is invalid.");
            a.setContentText("Please check your email.");
            email.setText("");
            a.showAndWait();
            return false;
        }
    }
    /**
     * Creates new user account, and adds user data to MySQL database
     * @param event
     * @throws ParseException 
     */
    public void newAccount(MouseEvent event) throws ParseException
    {
        Connection con=null;
        PreparedStatement ps=null;
        if(validateName()&&validateMobileNo()&&validateIdNo()&&validateEmail()&&validateDob()&&validatebalance()&&validateOtp()&&validatePin())
        {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql ="INSERT INTO userdata (Name, ICN, MobileNo, Gender, Religion, MaritialStatus,DOB,City,Address, AccountNo, PIN, AccountType, Balance, SecurityQuestion, Answer, ProfilePicture, Email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            
            ps.setString(1, name.getText());
            ps.setString(2, idcardno.getText());
            ps.setString(3, mobileno.getText());
            ps.setString(4, gender.getValue());
            ps.setString(5, religion.getValue());
            ps.setString(6, maritialstatus.getValue());
            ps.setString(7, ((TextField)dob.getEditor()).getText());
            ps.setString(8, city.getText());
            ps.setString(9, address.getText());
            ps.setString(10, accountno.getText());
            ps.setString(11, pin.getText());
            ps.setString(12, accounttype.getValue());
            ps.setString(13, balance.getText());
            ps.setString(14, questions.getValue());
            ps.setString(15, answer.getText());
            ps.setString(17, email.getText());
            
            
            
            fis=new FileInputStream(file);
            ps.setBinaryStream(16, (InputStream)fis, (int)file.length());
            
            int i = ps.executeUpdate();
            if(i>0)
            {
            Alert a=new Alert(AlertType.INFORMATION);
            a.setTitle("Account Created");
            a.setHeaderText("Welcome To Ace Bank.");
            a.setContentText("Your Account has been  created sucessfully.\n You can now Login with your account no and pin.");
            JoiningMail.sendMail(email.getText(),name.getText(),balance.getText(), accounttype.getValue());
            a.showAndWait();  
            clearAllFields();
             Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/Login/LoginScreen.fxml")));
            }
            else
            {
             
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Account not created ");
            a.setContentText("Your Account is not created there is some error .");
            a.showAndWait();
           
            }
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in creating Account.");
            a.setContentText("Your Account is not created there is some technical issue."+ e.getMessage());
            a.showAndWait();
            
        }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        gender.setItems(list);
        maritialstatus.setItems(list1);
        religion.setItems(list2);
        accounttype.setItems(list3);
        questions.setItems(list4);
        
        
        
        accountno.setText((String.valueOf((generateAccuntNo()))));
        accountno.setEditable(false);
        otpfield.setEditable(false);
        
        
     
    }    
    
}


package updateprofile;

import Login.LoginScreenController;
import dashboard.DashboardController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;


public class UpdateProfController implements Initializable {
    DashboardController d = new DashboardController();
    @FXML
    private TextField pin;
    @FXML
    private Circle profilepic;
    @FXML
    private TextField name;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    private FileChooser filechooser = new FileChooser();
    private File file;
    private FileInputStream fis;
    
    boolean flag=false;
    
    
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
            a.setTitle("Incorrect Email");
            a.setHeaderText("Your Email is wrong");
            a.setContentText("Please check your email");
            a.showAndWait();
            return false;
        }
    }
     public boolean validateName()
    {
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(name.getText());
        if(m.find()&&m.group().equals(name.getText()))
        {
            return true;
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect Name");
            a.setHeaderText("Your Name is wrong");
            a.setContentText("Only characters are allowed in name");
            a.showAndWait();
            return false;
        }
    }
     
    public boolean validateMobileNo()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobile.getText());
        int l=mobile.getText().length();
        if(m.find()&&m.group().equals(mobile.getText())&&l==10)
        {
            return true;
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect MobileNo");
            a.setHeaderText("Your MobileNo is wrong");
            a.setContentText("Enter correct mobile no");
            a.showAndWait();
            return false;
        }
    }
    
    
    
      public void setUpPic(MouseEvent e)
    {
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images Files", "*.png", "*.jpg"));
        file= filechooser.showOpenDialog(null);
        if(file!=null)
        {
            Image img = new Image(file.toURI().toString(), 150, 150, true, true);
            profilepic.setFill(new ImagePattern(img));
            
            flag=true;
            
            
            
        }
        
    }
    
    
    public void editName()
    {
        name.setEditable(true);
        name.setText("");
    }
     public void editMobile()
    {
        mobile.setEditable(true);
        mobile.setText("");
        
        
    }
      public void editEmail()
    {
        email.setEditable(true);
        email.setText("");
    }
    public void restore()
    {
        setInfo();
        name.setEditable(false);
        mobile.setEditable(false);
        email.setEditable(false);
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
               String ann;
               name.setText(rs.getString("NAME"));
               mobile.setText(rs.getString("MobileNo"));
               email.setText(rs.getString("Email"));
               
                InputStream is = rs.getBinaryStream("ProfilePicture");
               OutputStream os = new FileOutputStream(new File("pic.jpg"));
               byte[] content = new byte[1024];
               int size=0;
               while((size=is.read(content))!=-1)
               {
                   os.write(content,0,size);
               }
               os.close();
               is.close();
             
               Image img = new Image("file:pic.jpg",false);
               profilepic.setFill(new ImagePattern(img));
               
              
           
            }
            else
            {
             
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Login");
            a.setContentText("Your Pin is incorrect.");
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
    
    
    public void update(MouseEvent event)
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
            ps.setString(2, pin.getText());
         
            rs=ps.executeQuery();

            
            
            if(rs.next())
            {
               
                
                  
                if(validateName()&&validateMobileNo()&&validateEmail())
                {
                String sql1 = "UPDATE userdata SET NAME='"+name.getText()+"', Email='"+email.getText()+"', MobileNo='"+mobile.getText()+"' WHERE AccountNo='"+LoginScreenController.acc+"'";
                       ps=con.prepareStatement(sql1);
                       ps.execute();      
                if(flag)
                      {
                       fis=new FileInputStream(file);
                       String sql9 ="UPDATE userdata SET ProfilePicture=? WHERE AccountNo ='"+LoginScreenController.acc+"'" ;  
                       
                     
                      
                       ps=con.prepareStatement(sql9);
                       ps.setBinaryStream(1, (InputStream)fis, (int)file.length());
                      
            
                       int i=ps.executeUpdate();
                       
                      
                      }
                  
                     
                       Alert a=new Alert(Alert.AlertType.INFORMATION);
                       a.setTitle("Account Updated");
                       a.setHeaderText("Account Information Updated");
                       a.setContentText("Your new information is now saved.");
                       a.showAndWait();
                       
                       pin.setText("");
                       name.setEditable(false);
                       mobile.setEditable(false);
                       email.setEditable(false);
                       
                       d.logout(event);
                }
                       
                       
            }
            else
            {
             
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Updation");
            a.setContentText("Your Pin is incorrect.");
            a.showAndWait();
           
            }
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in updating Account.");
             a.showAndWait();
            
        }
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfo();
        name.setEditable(false);
        mobile.setEditable(false);
        email.setEditable(false);
       
    }    
    
}

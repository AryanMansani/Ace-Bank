
package transaction;

import Login.LoginScreenController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class TransactionController implements Initializable {

     Connection con=null;
     PreparedStatement ps=null;
     ResultSet rs = null;
     
    @FXML 
    private TableView<history> deposit_table;
    @FXML 
    private TableColumn<history, String> deposit_accountno;
    @FXML 
    private TableColumn<history, String> deposit_amount;
     @FXML 
    private TableColumn<history, String> deposit_remainingamount;
    @FXML 
    private TableColumn<history, String> deposit_date;
    @FXML 
    private TableColumn<history, String> deposit_time; 
    
    
    @FXML 
    private TableView<history> withdraw_table;
    @FXML 
    private TableColumn<history, String> withdraw_accountno;
    @FXML 
    private TableColumn<history, String> withdraw_amount;
     @FXML 
    private TableColumn<history, String> withdraw_remainingamount;
    @FXML 
    private TableColumn<history, String> withdraw_date;
    @FXML 
    private TableColumn<history, String> withdraw_time; 
    
     @FXML 
    private TableView<history> transfer_table;
    @FXML 
    private TableColumn<history, String> transfer_accountno;
    @FXML 
    private TableColumn<history, String> transfer_amount;
     @FXML 
    private TableColumn<history, String> transfer_remainingamount;
    @FXML 
    private TableColumn<history, String> transfer_date;
    @FXML 
    private TableColumn<history, String> transfer_time; 
    
     @FXML 
    private TableView<history> recieve_table;
    @FXML 
    private TableColumn<history, String> recieve_accountno;
    @FXML 
    private TableColumn<history, String> recieve_amount;
     @FXML 
    private TableColumn<history, String> recieve_remainingamount;
    @FXML 
    private TableColumn<history, String> recieve_date;
    @FXML 
    private TableColumn<history, String> recieve_time; 
    
    
    public void withdraw()
    {
        withdraw_accountno.setCellValueFactory(new PropertyValueFactory<history, String>("name"));
        withdraw_amount.setCellValueFactory(new PropertyValueFactory<history, String>("amount"));
        withdraw_remainingamount.setCellValueFactory(new PropertyValueFactory<history, String>("generic"));
        withdraw_date.setCellValueFactory(new PropertyValueFactory<history, String>("date"));
        withdraw_time.setCellValueFactory(new PropertyValueFactory<history, String>("time"));
        
        ObservableList<history> list=FXCollections.observableArrayList();
        
         try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql = "SELECT * FROM withdraw WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
         
            rs=ps.executeQuery();

            
            
            while(rs.next())
            {
             
                list.add(new history(rs.getString("AccountNo"),rs.getString("WithdrawAmount"),rs.getString("RemainingAmount"),rs.getString("Date"),rs.getString("Time")));
                
              
           
            }
           
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is Some error."+ e.getMessage());
            a.showAndWait();
            
        }
         withdraw_table.setItems(list);
    }
     public void deposit()
    {
        deposit_accountno.setCellValueFactory(new PropertyValueFactory<history, String>("name"));
        deposit_amount.setCellValueFactory(new PropertyValueFactory<history, String>("amount"));
        deposit_remainingamount.setCellValueFactory(new PropertyValueFactory<history, String>("generic"));
        deposit_date.setCellValueFactory(new PropertyValueFactory<history, String>("date"));
        deposit_time.setCellValueFactory(new PropertyValueFactory<history, String>("time"));
        
        ObservableList<history> list=FXCollections.observableArrayList();
        
         try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql = "SELECT * FROM deposit WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
         
            rs=ps.executeQuery();

            
            
            while(rs.next())
            {
             
                list.add(new history(rs.getString("AccountNo"),rs.getString("DepositAmount"),rs.getString("NewAmount"),rs.getString("Date"),rs.getString("Time")));
                
              
           
            }
           
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is Some error."+ e.getMessage());
            a.showAndWait();
            
        }
         deposit_table.setItems(list);
    }
      public void transfermoney()
    {
        transfer_accountno.setCellValueFactory(new PropertyValueFactory<history, String>("name"));
        transfer_amount.setCellValueFactory(new PropertyValueFactory<history, String>("amount"));
        transfer_remainingamount.setCellValueFactory(new PropertyValueFactory<history, String>("generic"));
        transfer_date.setCellValueFactory(new PropertyValueFactory<history, String>("date"));
        transfer_time.setCellValueFactory(new PropertyValueFactory<history, String>("time"));
        
        ObservableList<history> list=FXCollections.observableArrayList();
        
         try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql = "SELECT * FROM transferamount WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
         
            rs=ps.executeQuery();

            
            
            while(rs.next())
            {
             
                list.add(new history(rs.getString("AccountNo"),rs.getString("Amount"),rs.getString("SendTo"),rs.getString("Date"),rs.getString("Time")));
                
              
           
            }
           
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is Some error."+ e.getMessage());
            a.showAndWait();
            
        }
         transfer_table.setItems(list);
    }
     public void recievedmoney()
    {
       recieve_accountno.setCellValueFactory(new PropertyValueFactory<history, String>("name"));
       recieve_amount.setCellValueFactory(new PropertyValueFactory<history, String>("amount"));
       recieve_remainingamount.setCellValueFactory(new PropertyValueFactory<history, String>("generic"));
       recieve_date.setCellValueFactory(new PropertyValueFactory<history, String>("date"));
       recieve_time.setCellValueFactory(new PropertyValueFactory<history, String>("time"));
        
        ObservableList<history> list=FXCollections.observableArrayList();
        
         try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
            String sql = "SELECT * FROM transferamount WHERE SendTo=?";
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, LoginScreenController.acc);
         
            rs=ps.executeQuery();

            
            
            while(rs.next())
            {
             
                list.add(new history(rs.getString("SendTo"),rs.getString("Amount"),rs.getString("AccountNo"),rs.getString("Date"),rs.getString("Time")));
                
              
           
            }
           
            
            
            
            
        }catch(Exception e)
        {
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is Some error."+ e.getMessage());
            a.showAndWait();
            
        }
         recieve_table.setItems(list);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       withdraw();
       deposit();
       transfermoney();
       recievedmoney();
    }    
    
}

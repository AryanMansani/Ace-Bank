
package SipCalc;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class SipcalController implements Initializable {

    @FXML  
    private TextField monthlyin;
    @FXML
    private TextField time;
    @FXML
    private TextField rate;
    @FXML
    private TextField TotalInve;
    @FXML
    private TextField TotalAm;
    @FXML
    private TextField EstimatRe;
    @FXML
    private Slider monsli;
    @FXML
    private Slider Tsli;
    @FXML
    private Slider Rsli;
    

     
    public void calculate(MouseEvent event)
    {
       
        
        float MI=Float.parseFloat(monthlyin.getText());
        int mnths=(int)Float.parseFloat(time.getText());
        float rat=(Float.parseFloat(rate.getText()))/1200;
        if(MI>0&&mnths>0&&rat>0)
        {
        BigInteger TI,TA,ER;
        
        /*
        Formula for total amount -
        M = P × ({[1 + i]n – 1} / i) × (1 + i).
        In the above formula –
        M is the amount you receive upon maturity.
        P is the amount you invest at regular intervals.
        n is the number of payments you have made.
        i is the periodic rate of interest.*/
        
        
        
        TA = BigInteger.valueOf( (int)(MI * ((((Math.pow((1+rat), (mnths)))-1))) * (1+rat)/rat));
     
        TotalAm.setText("₹ "+String.valueOf(TA));
        
        TI=BigInteger.valueOf((int) (mnths*MI) );
        
        TotalInve.setText("₹ "+String.valueOf(TI));
        
        ER=TA.subtract(TI);
         
        EstimatRe.setText("₹ "+String.valueOf(ER));
         
           
         
        }
        else
        {
             Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Incorrect Information");
            a.setContentText("Please enter  correct information.");
            a.showAndWait();
            
        }
         
         
         
         
        
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
     Tsli.valueProperty().addListener((obs, oldval, newVal) ->
    Tsli.setValue(Math.round(newVal.doubleValue()))); 
    monsli.valueProperty().addListener((obs, oldval, newVal) ->
    monsli.setValue(Math.round(newVal.doubleValue()))); 
     monthlyin.textProperty().bind(
        Bindings.format(
                "%.2f",monsli.valueProperty()
        )
     );
     time.textProperty().bind(
        Bindings.format(
                "%.2f",Tsli.valueProperty()
        )
     );
     rate.textProperty().bind(
        Bindings.format(
                "%.2f",Rsli.valueProperty()
        )
     );
    }    
    
}

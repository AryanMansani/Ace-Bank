package emi;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author JAHANVI
 */
public class loanfxmlController implements Initializable {

    @FXML
    private Slider loanAmtSli;
    @FXML
    private Slider roiSli;
    @FXML
    private Slider tenureSli;
    @FXML
    private TextField amtTF;
    @FXML
    private TextField rateTF;
    @FXML
    private TextField tenureTF;
    @FXML
    private TextField emi;
    @FXML
    private TextField totalIntrest;

    /**
     * Initializes the controller class.
     */
    @FXML
    void getEmi() {

        try {
            Long p = (long) Float.parseFloat(amtTF.getText());
            float perAnnumROI = (float) Float.parseFloat(rateTF.getText());
            int tenure = (int) Float.parseFloat(tenureTF.getText());
            float roi = perAnnumROI / (100 * 12);
            double numerator = Math.pow(1 + roi, tenure);
            double denominator = (Math.pow(1 + roi, tenure) - 1);
            long monthlyEmi = (long) (p * roi * (numerator / denominator));
            emi.setText("₹ "+String.valueOf(monthlyEmi));
        } catch (NumberFormatException numberFormatException) {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Ace Bank");
            a.setContentText("Something went wrong. Try again");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loanAmtSli.valueProperty().addListener((obs, oldval, newVal)
                -> loanAmtSli.setValue(Math.round(newVal.doubleValue())));

        tenureSli.valueProperty().addListener((obs, oldval, newVal)
                -> tenureSli.setValue(Math.round(newVal.doubleValue())));

        amtTF.textProperty().bind(
                Bindings.format(
                        "%.2f",
                        loanAmtSli.valueProperty()
                )
        );
        rateTF.textProperty().bind(
                Bindings.format(
                        "%.2f",
                        roiSli.valueProperty()
                )
        );
        tenureTF.textProperty().bind(
                Bindings.format(
                        "%.2f",
                        tenureSli.valueProperty()
                )
        );
    }

}

package miamifx.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class AdministrarConfiguracionController implements Initializable {

    private Preferences prefs;
    
    @FXML
    private TextField txtMonto;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userRoot().node("miamiPref");
        double montoDefault = prefs.getDouble("monto-inscripcion", 500.00);
        txtMonto.setText(Double.toString(montoDefault));
    }    
    
    @FXML
    private void onGuardarPreferencias(ActionEvent event){
        Alert alert;
        try{
            double montoNuevo = Double.parseDouble(txtMonto.getText());
            DecimalFormat df = new DecimalFormat("#.##"); 
            montoNuevo = Double.valueOf(df.format(montoNuevo));
            prefs.putDouble("monto-inscripcion", montoNuevo);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setContentText("Guardado");
            alert.setHeaderText("Se ha guardado con exito el cambio");
            alert.show();
            txtMonto.setText(Double.toString(montoNuevo));
        } catch (Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Número mal escrito");
            alert.setContentText("Corrige el monto a un número real");
            alert.setHeaderText("Error en la definición del número");
            alert.show();
        }
        
    }
}

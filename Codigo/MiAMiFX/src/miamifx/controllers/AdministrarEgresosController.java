package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Missael Hernandez Rosado
 */
public class AdministrarEgresosController implements Initializable {
    
    @FXML
    DatePicker datePickerSalarioInicio, datePickerSalarioFin, datePickerEgresoInicio, datePickerEgresoFin;
    
    @FXML
    TableView tableSalarios, tableEgresos;
    
    @FXML
    Button btnRegistrarPagoSalario, btnEditarPagoSalario, btnEliminarPagoSalario;
    @FXML
    Button btnRegistrarEgreso, btnEditarEgreso, btnEliminarEgreso;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

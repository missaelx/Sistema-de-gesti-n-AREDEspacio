package miamifx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modelo.Egreso;
import modelo.Maestro;
import modelo.Pagodesalario;
import recursos.EgresosResource;
import recursos.MaestroResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class RegistrarPagoSalarioController implements Initializable {
    
    @FXML
    private ComboBox<Maestro> cmbEmpleado;
    @FXML
    private TextField txtMonto;
    @FXML
    private DatePicker datePickerFechaPago;
    @FXML
    private Button btnRegistrarPago;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarControles();
    }
    
    private void inicializarControles(){
        MaestroResource maestroRecurso = new MaestroResource();
        
        ObservableList<Maestro> observableList = FXCollections.observableList(maestroRecurso.visualizarRegistros());
        cmbEmpleado.setItems( observableList );
        
        
        cmbEmpleado.getSelectionModel().selectFirst();
        datePickerFechaPago.setValue(LocalDate.now());
        
        
    }
    
    
    @FXML
    public void OnRegistrarPago(ActionEvent event){
        EgresosResource recurso = new EgresosResource();
        Pagodesalario pago = new Pagodesalario();
        Egreso monto = new Egreso();
        
        monto.setFecha(new Date());
        monto.setMonto(new BigDecimal(1001.21));
        
        pago.setIdegreso(monto);
        
        //if(!recurso.registrarPagoSalario(pago)){
        if(true){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error al registrar pago");
            alert.setHeaderText("Hubo un error al contactar con la base de datos");
            alert.setContentText("Error");
            alert.showAndWait();
        } else {
            
        }
    }
    
}

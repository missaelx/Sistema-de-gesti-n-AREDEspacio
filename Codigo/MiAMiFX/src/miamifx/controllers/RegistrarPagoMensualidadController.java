package miamifx.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Alumno;
import modelo.GrupoClase;
import modelo.Promociones;
import recursos.AlumnoResource;
import recursos.DanzaResource;
import recursos.PromocionesResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class RegistrarPagoMensualidadController implements Initializable {
    @FXML
    private ComboBox cmbAlumno, cmbClase, cmbPromocion;
    @FXML
    private TextField txtMonto;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Button btnGuardar;
    @FXML
    private CheckBox checkBoxPromocion;

    private AdministrarIngresosController controladorPadre;

    public AdministrarIngresosController getControladorPadre() {
        return controladorPadre;
    }

    public void setControladorPadre(AdministrarIngresosController controladorPadre) {
        this.controladorPadre = controladorPadre;
        inicializarControles();
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void inicializarControles() {
        boolean activamosBoton = true;
        
        AlumnoResource recursoAlumno = new AlumnoResource();
        List<Alumno> alumnos = recursoAlumno.visualizarRegistros();
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(alumnos.size() <= 0){
            activamosBoton = false;    
            alert.setTitle("No existen alumnos registrados");
            alert.setHeaderText("No se han encontrado registros de alumnos");
            alert.setContentText("Registra un alumno primero");
            alert.show();
        } else {
            ObservableList llistaAlumno = FXCollections.observableArrayList(alumnos);
            cmbAlumno.setItems(llistaAlumno);
            cmbAlumno.getSelectionModel().selectFirst();
            
            Alumno alumnoSeleccionado = (Alumno) cmbAlumno.getSelectionModel().getSelectedItem();
            ObservableList llistaClasesAlumno = FXCollections.observableArrayList(alumnoSeleccionado.getGrupoClaseList());
            if(llistaClasesAlumno.size() <= 0){
                activamosBoton = false;
                alert.setTitle("No existen clases registradas para el alumno");
                alert.setHeaderText("No se han encontrado registros de clases");
                alert.setContentText("Inscribe al alumno en una clase antes");
                alert.show();
            } else {
                cmbClase.setItems(llistaClasesAlumno);
                cmbClase.getSelectionModel().selectFirst();
                PromocionesResource recursoPromociones = new PromocionesResource();
                ObservableList llistaPromociones = FXCollections.observableArrayList(recursoPromociones.getActivos());
                cmbPromocion.setItems(llistaPromociones);
            }
        }
        
        datePickerFecha.setValue(LocalDate.now());
        
        btnGuardar.setDisable(!activamosBoton);
        
    }
    
    @FXML
    public void onPromocionCheck(ActionEvent event){
        cmbPromocion.setDisable(!checkBoxPromocion.isSelected());
    }
    @FXML
    public void onPromocionClaseChange(ActionEvent event){
        String descripcion = "";
        GrupoClase claseSeleccionada = (GrupoClase) cmbClase.getSelectionModel().getSelectedItem();
        BigDecimal montoFinal = claseSeleccionada.getCostoMensual();
        descripcion += "Monto por mensualidad: $" + montoFinal + " - ";
        if(checkBoxPromocion.isSelected()){
            Promociones promocionSeleccionada = (Promociones) cmbPromocion.getSelectionModel().getSelectedItem();
            BigDecimal factorDescuento = new BigDecimal((
                    Float.parseFloat(Integer.toString(promocionSeleccionada.getPorcentajeDescuento()))/100) + 1);
            montoFinal = montoFinal.divide(factorDescuento, 2 ,RoundingMode.CEILING);
            descripcion += "Monto con descuento: $" + montoFinal;
        }
        txtMonto.setText(montoFinal.toString());
        txtDescripcion.setText(descripcion);
    }
    
}

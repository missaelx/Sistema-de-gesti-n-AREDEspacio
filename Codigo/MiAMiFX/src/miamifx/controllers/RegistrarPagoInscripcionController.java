package miamifx.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Alumno;
import modelo.Ingreso;
import modelo.Inscripcion;
import modelo.Promociones;
import recursos.AlumnoResource;
import recursos.IngresosResource;
import recursos.PromocionesResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class RegistrarPagoInscripcionController implements Initializable {

    private Preferences prefs;
    
    private AdministrarIngresosController controladorPadre;
    
    private static double montoPredefinido = 0;
    
    @FXML
    private TextField txtMonto;
    @FXML
    private ComboBox cmbAlumno, cmbPromocion;
    @FXML
    private CheckBox checkPromocion;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Button btnRegistrar;

    public AdministrarIngresosController getControladorPadre() {
        return controladorPadre;
    }

    public void setControladorPadre(AdministrarIngresosController controladorPadre) {
        this.controladorPadre = controladorPadre;
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ponemos el monto predefinido
        prefs = Preferences.userRoot().node("miamiPref");
        montoPredefinido = prefs.getDouble("monto-inscripcion", 500);
        String strMontoPredefinido = Double.toString(montoPredefinido);
        txtMonto.setText(strMontoPredefinido);
        
        //actualizamos a la fecha actual
        datePickerFecha.setValue(LocalDate.now());
        
        //llenamos el cmb de alumnos
        AlumnoResource recursoAlumno = new AlumnoResource();
        ObservableList llistaAlumnos = FXCollections.observableArrayList(recursoAlumno.visualizarRegistros());
        cmbAlumno.setItems(llistaAlumnos);
        cmbAlumno.getSelectionModel().selectFirst();
        
        //activamos el boton guardar si es que hay alumnos
        if(llistaAlumnos.size() <= 0){
            btnRegistrar.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se han encontrado alumnos registrados");
            alert.setHeaderText("No existen alumnos para registrar su inscripción");
            alert.setContentText("Registra un alumno para continuar");
            alert.show();
        } else {
            btnRegistrar.setDisable(false);
        }
        
        
        //llenamos el cmb de promociones
        PromocionesResource recursoPromociones = new PromocionesResource();
        ObservableList llistaPromociones = FXCollections.observableArrayList(recursoPromociones.getPromoInscripcion());
        cmbPromocion.setItems(llistaPromociones);
        cmbPromocion.getSelectionModel().selectFirst();
        
        calcularPreciosDescripcion();
    }

    private void calcularPreciosDescripcion() {
        Promociones promocionSeleccionada = (Promociones) cmbPromocion.getSelectionModel().getSelectedItem();
        double montoFinal = montoPredefinido;
        if(promocionSeleccionada != null && checkPromocion.isSelected()){
            double descuento = montoPredefinido * (new Double(promocionSeleccionada.getPorcentajeDescuento())/100);
            montoFinal = montoPredefinido - descuento;
        }
        
        txtMonto.setText(Double.toString(montoFinal));
        
        String descripcion = "";
        if(montoFinal != montoPredefinido)
            descripcion = "Monto total: " + Double.toString(montoPredefinido) + " Monto con descuento: " + Double.toString(montoFinal);
        else
            descripcion = "Monto total: " + Double.toString(montoFinal);
        txtDescripcion.setText(descripcion);
    }
    
    @FXML
    private void onCheckPromocionClick(ActionEvent event){
        cmbPromocion.setDisable(!checkPromocion.isSelected());
        calcularPreciosDescripcion();
    }
    @FXML
    private void onCancelar(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    private void onCmbPromocionChange(ActionEvent event){
        calcularPreciosDescripcion();
    }
    
    @FXML
    private void onRegistrarInscripcion(ActionEvent event){
        IngresosResource recursoIngresos = new IngresosResource();
        
        Ingreso ingreso = new Ingreso();
        ingreso.setDescripcion(txtDescripcion.getText());
        ingreso.setFecha(Date.from(datePickerFecha.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ingreso.setIdPromocion((Promociones) cmbPromocion.getSelectionModel().getSelectedItem());
        BigDecimal monto = null;
        try{
            monto = new BigDecimal(txtMonto.getText());
            monto.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Número mal definido");
            alert.setHeaderText("El número escrito no tiene el formato adecuado");
            alert.setContentText("El número no corresponde a la definición de un número");
            alert.show();
            return;
        }
        ingreso.setMonto(monto);
        
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdalumno((Alumno) cmbAlumno.getSelectionModel().getSelectedItem());
        inscripcion.setIdingreso(ingreso);
        
        Alert alert;
        if(recursoIngresos.registrarInscripcion(inscripcion)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscripción exitosa");
            alert.setHeaderText("La inscripción se ha realizado con exito");
            alert.setContentText("Se ha registrado el pago en la base de datos");
            alert.show();
            controladorPadre.setTableInscripciones();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al conectar a la base de datos");
            alert.setHeaderText("No se ha registrado el pago en la base de datos");
            alert.setContentText("Contácta al tu técnico");
            alert.show();
        }
    }
}

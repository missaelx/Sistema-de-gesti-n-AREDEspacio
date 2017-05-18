package miamifx.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.GrupoClase;
import modelo.Ingreso;
import modelo.Mensualidad;
import modelo.Promociones;
import recursos.AlumnoResource;
import recursos.DanzaResource;
import recursos.IngresosResource;
import recursos.PromocionesResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class RegistrarPagoMensualidadController implements Initializable {
    @FXML
    private ComboBox cmbAlumno, cmbPromocion;
    @FXML
    private TextField txtMonto;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Button btnGuardar;
    @FXML
    private CheckBox checkBoxPromocion, checkAdeudo;
    @FXML
    private TableView tableClases;
    @FXML
    private TableColumn colMaestro, colTipoDanza;
    @FXML
    private Label lbUltimoPago, lbFechaReinscripcion, lbAdeudo;

    private static BigDecimal adeudoAlumno = new BigDecimal(0);
    private static BigDecimal montoTotalAPagar = new BigDecimal(0);
    
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
        datePickerFecha.setValue(LocalDate.now());
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
            return;
        } else {
            ObservableList llistaAlumno = FXCollections.observableArrayList(alumnos);
            cmbAlumno.setItems(llistaAlumno);
            cmbAlumno.getSelectionModel().selectFirst();
            
            
            PromocionesResource recursoPromociones = new PromocionesResource();
            ObservableList llistaPromociones = FXCollections.observableArrayList(recursoPromociones.getPromoMensualidad());
            cmbPromocion.setItems(llistaPromociones);
            cmbPromocion.getSelectionModel().selectFirst();
            
            onAlumnoChange(null);
            
            colMaestro.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupoClase) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        if(grupoClase.getValue().getIdMaestro() != null)
                            property.setValue(grupoClase.getValue().getIdMaestro().getNombre() + " " + grupoClase.getValue().getIdMaestro().getApellidos());
                        else
                            property.setValue("N/A");
                        return property;
                    }
                }

            );

            colTipoDanza.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupoClase) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        if(grupoClase.getValue().getIdTipoDanza() != null)
                            property.setValue(grupoClase.getValue().getIdTipoDanza().getNombre());
                        else
                            property.setValue("N/A");
                        return property;
                    }
                }
            );
                
            if(tableClases.getItems().size() <= 0){
                activamosBoton = false;
                alert.setTitle("No existen clases registradas para el alumno");
                alert.setHeaderText("No se han encontrado registros de clases");
                alert.setContentText("Inscribe al alumno en una clase antes");
                alert.show();
            } else {
                onPromocionClaseChange(null);
            }
        }
        
        btnGuardar.setDisable(!activamosBoton);
        
    }
    
    public void setTablaClases(){
        Alumno alumnoSeleccionado = (Alumno) cmbAlumno.getSelectionModel().getSelectedItem();
        ObservableList llistaClases = FXCollections.observableArrayList(alumnoSeleccionado.getGrupoClaseList());
        tableClases.setItems(llistaClases);
        onPromocionClaseChange(null);
    }
    
    @FXML
    public void onPromocionCheck(ActionEvent event){
        cmbPromocion.setDisable(!checkBoxPromocion.isSelected());
        onPromocionClaseChange(null);
    }
    @FXML
    public void onPromocionClaseChange(ActionEvent event){
        String descripcion = "";
        List<GrupoClase> listaClases = tableClases.getItems();
        
        BigDecimal montoFinal = new BigDecimal(0);
        for(GrupoClase g: listaClases){
            montoFinal = montoFinal.add(g.getCostoMensual());
        }
        montoTotalAPagar = montoFinal;
        descripcion += "Monto por mensualidad: $" + montoFinal;
        
        calcularAdeudo();
        if(checkAdeudo.isSelected()){
            montoFinal = montoFinal.add(adeudoAlumno);
            descripcion += " - Monto más adeudo: $" + Double.toString(montoFinal.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        
        
        if(checkBoxPromocion.isSelected()){
            Promociones promocionSeleccionada = (Promociones) cmbPromocion.getSelectionModel().getSelectedItem();
            BigDecimal factorDescuento = new BigDecimal((
                    Float.parseFloat(Integer.toString(promocionSeleccionada.getPorcentajeDescuento()))/100) + 1);
            montoFinal = montoFinal.divide(factorDescuento, 2 ,RoundingMode.CEILING);
            descripcion += " - Monto con descuento: $" + montoFinal;
        }
        
        txtMonto.setText(Double.toString(montoFinal.setScale(2, RoundingMode.HALF_UP).doubleValue()));
        txtDescripcion.setText(descripcion);
    }
    
    @FXML
    public void onAlumnoChange(ActionEvent event){
        setTablaClases();
        calcularAdeudo();
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        
        Date proximoPago = ((Alumno) cmbAlumno.getSelectionModel().getSelectedItem()).getDiapago();
        if(proximoPago != null)
            lbUltimoPago.setText(formater.format(proximoPago));
        else
            lbUltimoPago.setText("No registrado");
        
        Date reinscripcion = ((Alumno) cmbAlumno.getSelectionModel().getSelectedItem()).getFechaInscripcion();
        if(reinscripcion != null)
            lbFechaReinscripcion.setText(formater.format(reinscripcion));
        else
            lbFechaReinscripcion.setText("No registrado");
        
        
        lbAdeudo.setText("$" + Double.toString(adeudoAlumno.setScale(2, RoundingMode.HALF_UP).doubleValue()));
        
    }
    
    @FXML
    public void cancelarPago(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Segura que desea cancelar el registro del pago?");
        alert.setHeaderText("Los cambios realizados no serán guardado");
        alert.setContentText("¿Realmente desea salir?");
        if(alert.showAndWait().get().equals(ButtonType.OK)){
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
    }
    
    @FXML
    public void registrarPagoMensualidad(ActionEvent event){
        BigDecimal monto;
        try{
            monto = new BigDecimal(txtMonto.getText());
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Monto no valido");
            alert.setHeaderText("El número escrito en monto en incorrecto");
            alert.setContentText("Modifica el monto por un número valido para continuar");
            alert.show();
            return;
        }
        
        
        Ingreso ingreso = new Ingreso();
        ingreso.setDescripcion(txtDescripcion.getText());
        ingreso.setFecha(Date.from(datePickerFecha.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ingreso.setIdPromocion((Promociones) cmbPromocion.getSelectionModel().getSelectedItem());
        ingreso.setMonto(monto);
        
        Mensualidad mensualidad = new Mensualidad();
        mensualidad.setGrupoClaseList((List<GrupoClase>)tableClases.getItems());
        mensualidad.setIdalumno((Alumno)cmbAlumno.getSelectionModel().getSelectedItem());
        mensualidad.setIdingreso(ingreso);
        
        IngresosResource recursoIngresos = new IngresosResource();
        if(recursoIngresos.registrarPagoMensualidad(mensualidad)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensualidad registrada");
            alert.setHeaderText("Se ha guardado el registro correctamente");
            alert.setContentText("El pago de la mensualidad ha sido guardado");
            alert.show();
            controladorPadre.setTableMensualidad();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problema al conectar con la base de datos");
            alert.setHeaderText("Hubo un error al conectar con la base de datos");
            alert.setContentText("Contácta con tu administrador del sistema");
            alert.show();
        }
    }

    private void calcularAdeudo() {
        Alumno alumnoSeleccionado = (Alumno) cmbAlumno.getSelectionModel().getSelectedItem();
        Date fechaDePagoCorrespondiente = alumnoSeleccionado.getDiapago();
        Date fechaDePago = Date.from(datePickerFecha.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        int diasPasados = (int)( (fechaDePago.getTime() - fechaDePagoCorrespondiente.getTime()) 
                 / (1000 * 60 * 60 * 24));
        
        int semanasPasadas = (diasPasados+1)/8;
        
        BigDecimal adeudo = montoTotalAPagar;
        
        for(int i = 0; i < semanasPasadas; i++){
            BigDecimal porcentaje5 = adeudo.add(adeudo.multiply(new BigDecimal(0.05))).add(montoTotalAPagar.negate());
            adeudo = adeudo.add(porcentaje5);
        }
        adeudo = adeudo.add(montoTotalAPagar.negate());
        adeudoAlumno = adeudo;
    }
}

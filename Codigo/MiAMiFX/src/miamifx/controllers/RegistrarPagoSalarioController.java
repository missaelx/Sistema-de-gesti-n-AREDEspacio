package miamifx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Egreso;
import modelo.GrupoClase;
import modelo.Maestro;
import modelo.Pagodesalario;
import recursos.EgresosResource;
import recursos.GrupoClaseResource;
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
    @FXML
    private TextArea txtDescripcion;
    
    private AdministrarEgresosController controladorPadre; //para poder refrescar la tabla

    public AdministrarEgresosController getControladorPadre() {
        return controladorPadre;
    }

    public void setControladorPadre(AdministrarEgresosController controladorPadre) {
        this.controladorPadre = controladorPadre;
    }
    
    
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
        
        buscarSalarioRecomendado();
    }
    private void buscarSalarioRecomendado(){
        Maestro maestroSeleccionado = cmbEmpleado.getValue();
        int idMaestroSeleccionado = maestroSeleccionado.getId();
        
        GrupoClaseResource recursoGrupos = new GrupoClaseResource();
        List<GrupoClase> listaGrupos = recursoGrupos.getClasesImpartidasPorMaestro(idMaestroSeleccionado);
        
        BigDecimal suma = new BigDecimal(0);
        
        for(GrupoClase g: listaGrupos){
            BigDecimal costo = g.getCostoMensual();
            float porcentajeDelMaestro = g.getPorcentajeGananciaMaestro() / 100;
            BigDecimal gananciaPorAlumno = costo.divide(BigDecimal.valueOf(porcentajeDelMaestro));
            
            List<Alumno> alumnoDeClase = g.getAlumnoList();
            
            for(Alumno a : alumnoDeClase){
                if(a.getDiapago().getMonth() == new Date().getMonth()){ //preguntamos si ha pagado el mes pasado (se denota porque la fecha de pago esta en el mes actual)
                    suma = suma.add(gananciaPorAlumno);
                }
            }
        }
        
        txtMonto.setText(suma.toString());
    }
    
    
    @FXML
    public void OnRegistrarPago(ActionEvent event){
        EgresosResource recurso = new EgresosResource();
        Pagodesalario pago = new Pagodesalario();
        Egreso monto = new Egreso();
        
        Date fechaSeleccionadaUsuario = Date.from(datePickerFechaPago.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        monto.setFecha(fechaSeleccionadaUsuario);
        monto.setMonto(new BigDecimal(txtMonto.getText()));
        monto.setDescripcion(txtDescripcion.getText());
        
        pago.setIdegreso(monto);
        pago.setIdmaestro(cmbEmpleado.getValue());
        
        if(!recurso.registrarPagoSalario(pago)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error al registrar pago");
            alert.setHeaderText("Hubo un error al contactar con la base de datos");
            alert.setContentText("Error");
            alert.showAndWait();
        } else {
            controladorPadre.actualizarTablaSalario();
            Stage e = (Stage) (((Node)event.getSource()).getScene().getWindow());
            e.close();
        }
    }
    
    
    
}

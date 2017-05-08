package miamifx.controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import modelo.Gastovariable;
import modelo.Inscripcion;
import modelo.Mensualidad;
import recursos.IngresosResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class AdministrarIngresosController implements Initializable {
    @FXML
    private DatePicker datePickerMensualidadInicio, datePickerMensualidadFin, datePickerInscripcionesInicio, datePickerInscripcionesFin;
    @FXML
    private TableView tableMensualidades, tableInscripciones;
    @FXML
    private TableColumn colMensualidadAlumno, colMensualidadMonto, colMensualidadDescripcion, colMensualidadFecha, colMensualidadPromocion, colMensualidadMaestro, colMensualidadDanza;
    @FXML
    private TableColumn colInscripcionAlumno, colInscripcionMonto, colInscripcionDescripcion, colInscripcionFecha, colInscripcionPromocion;
    
    
    @FXML
    private void onBuscarMensualidad(ActionEvent event){
        
    }
    @FXML
    private void onVerTodosMensualidad(ActionEvent event){
        
    }
    @FXML
    private void OnRegistrarPagoMensualidad(ActionEvent event){
        
    }
    @FXML
    private void onEliminarMensualidad(ActionEvent event){
        
    }
    @FXML
    private void onSumaMensualidades(ActionEvent event){
        
    }
    @FXML
    private void onBuscarInscripciones(ActionEvent event){
        
    }
    @FXML
    private void onVerTodosInscripciones(ActionEvent event){
        
    }
    @FXML
    private void onRegistrarInscripciones(ActionEvent event){
        
    }
    @FXML
    private void onEliminarInscripciones(ActionEvent event){
        
    }
    @FXML
    private void onSumaInscripciones(ActionEvent event){
        
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableMensualidad();
        setTableInscripciones();
    }
    
    public void setTableMensualidad(){
        IngresosResource recurso = new IngresosResource();
        ObservableList list = FXCollections.observableArrayList(recurso.getMensualidades());
        
        colMensualidadAlumno.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(mensualidad.getValue().getIdalumno().getNombre() + " " + mensualidad.getValue().getIdalumno().getApellidos());
                    return property;
                }
            }
        );
        
        colMensualidadMaestro.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(mensualidad.getValue().getIdGrupoClase().getIdMaestro().getNombre() + " " + mensualidad.getValue().getIdGrupoClase().getIdMaestro().getApellidos());
                    return property;
                }
            }
        );
        
        colMensualidadDanza.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(mensualidad.getValue().getIdGrupoClase().getIdTipoDanza().getNombre());
                    return property;
                }
            }
        );
        
        colMensualidadMonto.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue("$" + mensualidad.getValue().getIdingreso().getMonto());
                    return property;
                }
            }
        );
        
        colMensualidadDescripcion.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(mensualidad.getValue().getIdingreso().getDescripcion());
                    return property;
                }
            }
        );
        
        colMensualidadFecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    Date fecha = mensualidad.getValue().getIdingreso().getFecha();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(fecha != null)
                        property.setValue(dateFormat.format(fecha));
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        
        colMensualidadPromocion.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Mensualidad, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mensualidad, String> mensualidad) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(mensualidad.getValue().getIdingreso().getIdPromocion() != null)
                        property.setValue(mensualidad.getValue().getIdingreso().getIdPromocion().getTitulo());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        
        
        
        tableMensualidades.setItems(list);
    }
    
    public void setTableInscripciones(){
        IngresosResource recurso = new IngresosResource();
        ObservableList list = FXCollections.observableArrayList(recurso.getInscripciones());
        
        colInscripcionAlumno.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Inscripcion, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscripcion, String> inscripcion) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(inscripcion.getValue().getIdalumno().getNombre() + " " + inscripcion.getValue().getIdalumno().getApellidos());
                    return property;
                }
            }
        );
        colInscripcionMonto.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Inscripcion, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscripcion, String> inscripcion) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue("$" + inscripcion.getValue().getIdingreso().getMonto());
                    return property;
                }
            }
        );
        colInscripcionDescripcion.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Inscripcion, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscripcion, String> inscripcion) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(inscripcion.getValue().getIdingreso().getDescripcion());
                    return property;
                }
            }
        );
        colInscripcionFecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Inscripcion, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscripcion, String> inscripcion) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    Date fecha = inscripcion.getValue().getIdingreso().getFecha();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(fecha != null)
                        property.setValue(dateFormat.format(fecha));
                    else
                        property.setValue("N/A");
                    return property;
                }
            }    
        );
        colInscripcionPromocion.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Inscripcion, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscripcion, String> inscripcion) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(inscripcion.getValue().getIdingreso().getIdPromocion() != null)
                        property.setValue(inscripcion.getValue().getIdingreso().getIdPromocion().getTitulo());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        
        tableInscripciones.setItems(list);
    }
    
}

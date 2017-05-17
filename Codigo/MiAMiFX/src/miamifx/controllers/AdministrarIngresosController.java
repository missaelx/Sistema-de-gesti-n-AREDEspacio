package miamifx.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private TableColumn colMensualidadAlumno, colMensualidadMonto, colMensualidadDescripcion, colMensualidadFecha, colMensualidadPromocion;
    @FXML
    private TableColumn colInscripcionAlumno, colInscripcionMonto, colInscripcionDescripcion, colInscripcionFecha, colInscripcionPromocion;
    
    
    @FXML
    private void onBuscarMensualidad(ActionEvent event){
        LocalDate dateInicio = datePickerMensualidadInicio.getValue();
        LocalDate dateFin = datePickerMensualidadFin.getValue();
        
        if(dateInicio == null || dateFin == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Falta alguna fecha por seleccionar");
            alert.setHeaderText("Selecciona una fecha de inicio y fin");
            alert.setContentText("Las fecha de fin o de inicio no se han definido, corrige para continuar");
            alert.show();
        } else {
            Date inicio = Date.from(dateInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if(dateInicio.compareTo(dateFin)  <= 0){ // Inicio <= Fin

                IngresosResource ingresosRecurso = new IngresosResource();
                List<Mensualidad> listaResultado = ingresosRecurso.getMensualidadesEntreFechas(inicio, fin);

                tableMensualidades.refresh();
                ObservableList lista = FXCollections.observableArrayList(listaResultado);
                tableMensualidades.setItems(lista);

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error en las fechas seleccionadas");
                alert.setHeaderText("Selecciona otra fecha");
                alert.setContentText("Las fecha de fin es menor a la de inicio, corrige para continuar");
                alert.show();
            }
        }
    }
    @FXML
    private void onVerTodosMensualidad(ActionEvent event){
        this.datePickerMensualidadInicio.setValue(null);
        this.datePickerMensualidadFin.setValue(null);
        setTableMensualidad();
    }
    @FXML
    private void OnRegistrarPagoMensualidad(ActionEvent event){
        Stage registrarMensualidadStage = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/RegistrarPagoMensualidad.fxml"));
        AnchorPane root = null;
        try {
             root = cargador.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al buscar el FXML");
        }
        
        RegistrarPagoMensualidadController controlHijo = (RegistrarPagoMensualidadController) cargador.getController();
        controlHijo.setControladorPadre(this);
        
        Scene escena = new Scene(root);
        
        registrarMensualidadStage.setScene(escena);
        registrarMensualidadStage.initModality(Modality.WINDOW_MODAL);
        registrarMensualidadStage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        registrarMensualidadStage.setResizable(false);
        registrarMensualidadStage.show();
    }
    @FXML
    private void onEliminarMensualidad(ActionEvent event){
        Mensualidad mensualidadSeleccionada = (Mensualidad) tableMensualidades.getSelectionModel().getSelectedItem();
        if(mensualidadSeleccionada == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Selecciona un elemento de la tabla antes");
            alert.setHeaderText("No se ha seleccionado ningún registro");
            alert.setContentText("Selecciona un registro para continuar");
            alert.show();
            return;
        }
        
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar mensualidad");
        alerta.setHeaderText("Esta seguro(a) de eliminar el registro de la mensualidad");
        alerta.setContentText("No quedará rastro del registro en la base de datos");
        if(alerta.showAndWait().get().equals(ButtonType.OK)){
            IngresosResource recursoIngresos = new IngresosResource();
            Alert eliminado = new Alert(AlertType.INFORMATION);
            if(recursoIngresos.eliminarMensualidad(mensualidadSeleccionada)){
                eliminado.setTitle("Pago eliminado correctamente");
                eliminado.setHeaderText("Se ha eliminado el pago");
                eliminado.show();
                setTableMensualidad();
            } else {
                eliminado.setTitle("Conexión perdida con la base de datos");
                eliminado.setHeaderText("No se ha eliminado el pago");
                eliminado.setContentText("Hubo un problema al contáctar con la base de datos");
                eliminado.show();
            }
            
        } else {
            alerta.close();
        }
    }
    @FXML
    private void onSumaMensualidades(ActionEvent event){
        List<Mensualidad> lista = tableMensualidades.getItems();
        BigDecimal suma = new BigDecimal(0);
        for(Mensualidad m : lista){
            suma = suma.add(m.getIdingreso().getMonto());
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cuentas");
        alert.setHeaderText("Suma de las mensualidades");
        alert.setContentText("La suma de las mensualidades registradas es: $" + suma);
        alert.show();
    }
    
    
    @FXML
    private void onBuscarInscripciones(ActionEvent event){
        LocalDate dateInicio = datePickerInscripcionesInicio.getValue();
        LocalDate dateFin = datePickerInscripcionesFin.getValue();
        
        if(dateInicio == null || dateFin == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Falta alguna fecha por seleccionar");
            alert.setHeaderText("Selecciona una fecha de inicio y fin");
            alert.setContentText("Las fecha de fin o de inicio no se han definido, corrige para continuar");
            alert.show();
        } else {
            Date inicio = Date.from(dateInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if(dateInicio.compareTo(dateFin)  <= 0){ // Inicio <= Fin

                IngresosResource ingresosRecurso = new IngresosResource();
                List<Inscripcion> listaResultado = ingresosRecurso.getInscripcionesEntreFechas(inicio, fin);

                tableMensualidades.refresh();
                ObservableList lista = FXCollections.observableArrayList(listaResultado);
                tableMensualidades.setItems(lista);

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error en las fechas seleccionadas");
                alert.setHeaderText("Selecciona otra fecha");
                alert.setContentText("Las fecha de fin es menor a la de inicio, corrige para continuar");
                alert.show();
            }
        }
    }
    @FXML
    private void onVerTodosInscripciones(ActionEvent event){
        this.datePickerInscripcionesInicio.setValue(null);
        this.datePickerInscripcionesFin.setValue(null);
        setTableInscripciones();
    }
    @FXML
    private void onRegistrarInscripciones(ActionEvent event){
        Stage registrarInscripcionStage = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/RegistrarPagoInscripcion.fxml"));
        AnchorPane root = null;
        try {
             root = cargador.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al buscar el FXML");
        }
        
        RegistrarPagoInscripcionController controlHijo = (RegistrarPagoInscripcionController) cargador.getController();
        controlHijo.setControladorPadre(this);
        
        Scene escena = new Scene(root);
        
        registrarInscripcionStage.setScene(escena);
        registrarInscripcionStage.initModality(Modality.WINDOW_MODAL);
        registrarInscripcionStage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        registrarInscripcionStage.setResizable(false);
        registrarInscripcionStage.show();
    }
    @FXML
    private void onEliminarInscripciones(ActionEvent event){
        Inscripcion inscripcionSeleccionada = (Inscripcion) tableInscripciones.getSelectionModel().getSelectedItem();
        if(inscripcionSeleccionada == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Selecciona un elemento de la tabla antes");
            alert.setHeaderText("No se ha seleccionado ningún registro");
            alert.setContentText("Selecciona un registro para continuar");
            alert.show();
            return;
        }
        
        
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar inscripción");
        alerta.setHeaderText("Esta seguro(a) de eliminar el registro de la inscripción");
        alerta.setContentText("No quedará rastro del registro en la base de datos");
        if(alerta.showAndWait().get().equals(ButtonType.OK)){
            IngresosResource recursoIngresos = new IngresosResource();
            Alert eliminado = new Alert(AlertType.INFORMATION);
            if(recursoIngresos.eliminarInscripcion(inscripcionSeleccionada)){
                eliminado.setTitle("Pago eliminado correctamente");
                eliminado.setHeaderText("Se ha eliminado el pago");
                eliminado.show();
            } else {
                eliminado.setTitle("Conexión perdida con la base de datos");
                eliminado.setHeaderText("No se ha eliminado el pago");
                eliminado.setContentText("Hubo un problema al contáctar con la base de datos");
                eliminado.show();
            }
            
        } else {
            alerta.close();
        }
    }
    @FXML
    private void onSumaInscripciones(ActionEvent event){
        List<Inscripcion> lista = tableInscripciones.getItems();
        BigDecimal suma = new BigDecimal(0);
        for(Inscripcion i : lista){
            suma = suma.add(i.getIdingreso().getMonto());
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cuentas");
        alert.setHeaderText("Suma de las inscripciones");
        alert.setContentText("La suma de las inscripciones registradas es: $" + suma);
        alert.show();
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

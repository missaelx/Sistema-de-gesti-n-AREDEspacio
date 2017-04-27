package miamifx.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Gastovariable;
import modelo.Pagodesalario;
import recursos.EgresosResource;

/**
 * FXML Controller class
 *
 * @author Missael Hernandez Rosado
 */
public class AdministrarEgresosController implements Initializable {
    
    @FXML
    private DatePicker datePickerSalarioInicio, datePickerSalarioFin, datePickerEgresoInicio, datePickerEgresoFin;
    
    @FXML
    private TableView<Pagodesalario> tableSalarios;
    @FXML
    private TableView<Gastovariable> tableEgresos;
    @FXML
    private TableColumn colSalarioMaestro, colSalarioMonto, colSalarioDescripcion, colSalarioFecha;
    @FXML
    private TableColumn colEgresoMonto, colEgresoDescripcion, colEgresoFecha;
    
    @FXML
    private Button btnRegistrarPagoSalario, btnEliminarPagoSalario;
    @FXML
    private Button btnRegistrarEgreso, btnEliminarEgreso;

    private EgresosResource recursoEgresos;
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recursoEgresos = new EgresosResource();
        setTablaEgresosVariables();
        setTablaSalario();
    }    
    
    public void actualizarTablaSalario(){
        tableSalarios.refresh();
        ObservableList lista = FXCollections.observableArrayList(recursoEgresos.getPagosSalariales());
        tableSalarios.setItems(lista);
    }
    public void actualizarTablaEgresos(){
        tableEgresos.refresh();
        ObservableList lista = FXCollections.observableArrayList(recursoEgresos.getEgresosVariables());
        tableEgresos.setItems(lista);
    }
    public void setTablaSalario(){
        ObservableList lista = FXCollections.observableArrayList(recursoEgresos.getPagosSalariales());
        colSalarioMaestro.setCellValueFactory(new PropertyValueFactory<>("maestro"));
        //colSalarioMonto.setCellValueFactory( new PropertyValueFactory<>("monto"));
        colSalarioMonto.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Pagodesalario, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Pagodesalario, String> film) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(film.getValue().getMonto() != null)
                        property.setValue("$" + film.getValue().getMonto());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        colSalarioDescripcion.setCellValueFactory( new PropertyValueFactory<>("descripcion"));
        //colSalarioFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colSalarioFecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Pagodesalario, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Pagodesalario, String> film) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(film.getValue().getFecha() != null)
                        property.setValue(dateFormat.format(film.getValue().getFecha()));
                    else
                        property.setValue("N/A");
                    return property;
                }
        });
        tableSalarios.setItems(lista);
    }
    public void setTablaEgresosVariables(){
        ObservableList lista = FXCollections.observableArrayList(recursoEgresos.getEgresosVariables());
        //colEgresoMonto.setCellValueFactory( new PropertyValueFactory<>("monto"));
        colEgresoMonto.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Gastovariable, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Gastovariable, String> film) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(film.getValue().getMonto() != null)
                        property.setValue("$" + film.getValue().getMonto());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        colEgresoDescripcion.setCellValueFactory( new PropertyValueFactory<>("descripcion"));
        //colEgresoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEgresoFecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Gastovariable, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Gastovariable, String> film) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(film.getValue().getFecha() != null)
                        property.setValue(dateFormat.format(film.getValue().getFecha()));
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        tableEgresos.setItems(lista);
    }
    
    @FXML
    public void OnRegistrarPagoSalario(ActionEvent event){
        Stage registrarPagoSalarioStage = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/RegistrarPagoSalario.fxml"));
        AnchorPane root = null;
        try {
             root = cargador.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al buscar el FXML");
        }
        
        RegistrarPagoSalarioController controlHijo = (RegistrarPagoSalarioController) cargador.getController();
        controlHijo.setControladorPadre(this);
        
        Scene escena = new Scene(root);
        
        registrarPagoSalarioStage.setScene(escena);
        registrarPagoSalarioStage.initModality(Modality.WINDOW_MODAL);
        registrarPagoSalarioStage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        registrarPagoSalarioStage.setResizable(false);
        registrarPagoSalarioStage.show();
            
        
    }
    
    @FXML
    public void onRegistrarEgreso(ActionEvent event){
        Stage registrarEgresoStage = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/RegistrarEgresoVariable.fxml"));
        AnchorPane root = null;
        try {
             root = cargador.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al buscar el FXML");
        }
        
        RegistrarEgresoVariableController controlHijo = (RegistrarEgresoVariableController) cargador.getController();
        controlHijo.setControladorPadre(this);
        
        Scene escena = new Scene(root);
        
        registrarEgresoStage.setScene(escena);
        registrarEgresoStage.initModality(Modality.WINDOW_MODAL);
        registrarEgresoStage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        registrarEgresoStage.setResizable(false);
        registrarEgresoStage.show();
        
    }
    
    @FXML
    public void onEliminarSalarioClick(ActionEvent event){
        Alert alert = null;
        if(tableSalarios.getSelectionModel().isEmpty()){ //retorna true si no se ha seleccionado ninguna fila
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Selecciona un pago");
            alert.setHeaderText("No se ha seleccionado ninguna fila de la tabla");
            alert.setContentText("Selecciona la fila correspondiente al pago que deseas eliminar");

            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma la eliminación del registro");
            alert.setHeaderText("Se eliminará por completo el pago");
            alert.setContentText("Se eliminará por completo el registro del pago, ¿Desea continiar?");

            Optional<ButtonType> op = alert.showAndWait();
            if (op.get() == ButtonType.OK){
                EgresosResource egresosRecurso = new EgresosResource();
                Pagodesalario pago = (Pagodesalario) tableSalarios.getSelectionModel().getSelectedItem();
                
                if(!egresosRecurso.eliminarPagoSalario(pago)){
                    System.out.println("Un error ocurrio");
                }
                actualizarTablaSalario();
            }
        }
    }
    
    @FXML
    private void onEliminarEgresoClick(){
        Alert alert = null;
        if(tableEgresos.getSelectionModel().isEmpty()){ //retorna true si no se ha seleccionado ninguna fila
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Selecciona un pago");
            alert.setHeaderText("No se ha seleccionado ninguna fila de la tabla");
            alert.setContentText("Selecciona la fila correspondiente al pago que deseas eliminar");

            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma la eliminación del registro");
            alert.setHeaderText("Se eliminará por completo el egreso");
            alert.setContentText("Se eliminará por completo el registro del egreso, ¿Desea continiar?");

            Optional<ButtonType> op = alert.showAndWait();
            if (op.get() == ButtonType.OK){
                EgresosResource egresosRecurso = new EgresosResource();
                Gastovariable pago = (Gastovariable) tableEgresos.getSelectionModel().getSelectedItem();
                
                if(!egresosRecurso.eliminarEgreso(pago)){
                    System.out.println("Un error ocurrio");
                }
                actualizarTablaEgresos();
            }
        }
    }
    
    @FXML
    private void onBuscarSalarios(ActionEvent event){
        LocalDate dateInicio = datePickerSalarioInicio.getValue();
        LocalDate dateFin = datePickerSalarioFin.getValue();
        
        Date inicio = Date.from(dateInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());;
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());;;
        
        
        if(dateInicio.compareTo(dateFin)  <= 0){ // Inicio <= Fin
            EgresosResource egresosRecurso = new EgresosResource();
            List<Gastovariable> listaResultado = egresosRecurso.getEgresosEntreFechas(inicio, fin);
            
            tableSalarios.refresh();
            ObservableList lista = FXCollections.observableArrayList(listaResultado);
            tableSalarios.setItems(lista);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error en las fechas seleccionadas");
            alert.setHeaderText("Selecciona otra fecha");
            alert.setContentText("Las fecha de fin es menor a la de inicio, corrige para continuar");
            alert.show();
        }
    }
    
    @FXML
    private void onBuscarEgresos(ActionEvent event){
        LocalDate dateInicio = datePickerEgresoInicio.getValue();
        LocalDate dateFin = datePickerEgresoFin.getValue();
        
        Date inicio = Date.from(dateInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());;
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());;;
        
        if(dateInicio.compareTo(dateFin)  <= 0){ // Inicio <= Fin
            
            
            
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error en las fechas seleccionadas");
            alert.setHeaderText("Selecciona otra fecha");
            alert.setContentText("Las fecha de fin es menor a la de inicio, corrige para continuar");
            alert.show();
        }
    }
}

package miamifx.controllers;

import java.math.BigDecimal;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import modelo.Egreso;
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
    private Button btnRegistrarPagoSalario, btnEditarPagoSalario, btnEliminarPagoSalario;
    @FXML
    private Button btnRegistrarEgreso, btnEditarEgreso, btnEliminarEgreso;

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
        EgresosResource recurso = new EgresosResource();
        Pagodesalario pago = new Pagodesalario();
        Egreso monto = new Egreso();
        monto.setFecha(new Date());
        monto.setMonto(new BigDecimal(1001.21));
        
        pago.setIdegreso(monto);
        
        if(!recurso.registrarPagoSalario(pago)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error al registrar pago");
            alert.setHeaderText("Hubo un error al contactar con la base de datos");
            alert.setContentText("Error");
            alert.showAndWait();
        } else {
            
        }
            
        
    }
}

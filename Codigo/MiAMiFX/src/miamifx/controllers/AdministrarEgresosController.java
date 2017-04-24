package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
    public void setTablaSalario(){
        ObservableList lista = FXCollections.observableArrayList(recursoEgresos.getPagosSalariales());
        colSalarioMaestro.setCellValueFactory(new PropertyValueFactory<>("maestro"));
        colSalarioMonto.setCellValueFactory( new PropertyValueFactory<>("monto"));
        colSalarioDescripcion.setCellValueFactory( new PropertyValueFactory<>("descripcion"));
        colSalarioFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableSalarios.setItems(lista);
    }
    public void setTablaEgresosVariables(){
        ObservableList lista = FXCollections.observableArrayList(recursoEgresos.getEgresosVariables());
        colEgresoMonto.setCellValueFactory( new PropertyValueFactory<>("monto"));
        colEgresoDescripcion.setCellValueFactory( new PropertyValueFactory<>("descripcion"));
        colEgresoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableEgresos.setItems(lista);
    }
    
    @FXML
    public void OnRegistrarPagoSalario(ActionEvent event){
        
    }
}

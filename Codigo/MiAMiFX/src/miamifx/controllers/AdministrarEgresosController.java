package miamifx.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
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
        Stage registrarPagoSalarioStage = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/RegistrarPagoSalario.fxml"));
        AnchorPane root = null;
        try {
             root = cargador.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al buscar el FXML");
        }
        
        Scene escena = new Scene(root);
        
        registrarPagoSalarioStage.setScene(escena);
        registrarPagoSalarioStage.initModality(Modality.WINDOW_MODAL);
        registrarPagoSalarioStage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        registrarPagoSalarioStage.setResizable(false);
        registrarPagoSalarioStage.show();
            
        
    }
}

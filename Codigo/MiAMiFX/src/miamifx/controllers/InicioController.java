package miamifx.controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.Gastovariable;
import recursos.AlumnoResource;

/**
 * FXML Controller class
 *
 * @author Missael Hernandez Rosado
 */
public class InicioController implements Initializable {

    @FXML
    private TableView tableReinscripciones, tableMensualidades;
    @FXML
    private TableColumn colResinscripcionNombre, colResinscripcionFecha, colMensualidadesNombre, colMensualidadesFecha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setReinscripciones();
        setMensualidades();
    }
    
    public void setReinscripciones(){
        AlumnoResource recursoAlumnos = new AlumnoResource();
        List<Alumno> listaAlumnosReinscripcion = recursoAlumnos.buscarProximasReinscripciones(new Date());
        
        ObservableList listaReinscripcion = FXCollections.observableArrayList(listaAlumnosReinscripcion);
        colResinscripcionNombre.setCellValueFactory
                (
            new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> alumno) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    if(alumno.getValue() != null)
                        property.setValue(alumno.getValue().getNombre() + " " + alumno.getValue().getApellidos());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        colResinscripcionFecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> alumno) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(alumno.getValue().getFechaInscripcion()!= null)
                        property.setValue(dateFormat.format(alumno.getValue().getFechaInscripcion()));
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        
        tableReinscripciones.setItems(listaReinscripcion);
    }

    private void setMensualidades() {
        AlumnoResource recursoAlumnos = new AlumnoResource();
        List<Alumno> listaAlumnosMensualidades = recursoAlumnos.buscarProximasMensualidades(new Date());
        
        ObservableList listaMensualidades = FXCollections.observableArrayList(listaAlumnosMensualidades);
        
        colMensualidadesNombre.setCellValueFactory
                (
            new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> alumno) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    if(alumno.getValue() != null)
                        property.setValue(alumno.getValue().getNombre() + " " + alumno.getValue().getApellidos());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        colMensualidadesFecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> alumno) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(alumno.getValue().getDiapago()!= null)
                        property.setValue(dateFormat.format(alumno.getValue().getDiapago()));
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        
        tableMensualidades.setItems(listaMensualidades);
        
    }
    
}

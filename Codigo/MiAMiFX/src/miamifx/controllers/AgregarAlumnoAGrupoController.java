package miamifx.controllers;

import java.net.URL;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.GrupoClase;
import recursos.AlumnoResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class AgregarAlumnoAGrupoController implements Initializable {
    
    private VerListaAlumnoGrupoController controladorPadre;
    private GrupoClase grupoSeleccionado;

    public VerListaAlumnoGrupoController getControladorPadre() {
        return controladorPadre;
    }

    public void setControladorPadre(VerListaAlumnoGrupoController controladorPadre) {
        this.controladorPadre = controladorPadre;
    }

    public GrupoClase getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(GrupoClase grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
        
        tableAlumnos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        AlumnoResource recursoAlumnos = new AlumnoResource();
        ObservableList list = FXCollections.observableArrayList(recursoAlumnos.visualizarRegistrosNoInscritosAGrupo(grupoSeleccionado));
        
        columAlumnos.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> alumno) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(alumno.getValue().getNombre() + " " + alumno.getValue().getApellidos());
                    return property;
                }
            }
        );
        
        
        tableAlumnos.setItems(list);
    }
    
    
    @FXML
    private TableView tableAlumnos;
    @FXML
    private TableColumn columAlumnos;
    
    @FXML
    public void onAgregarAlumno(ActionEvent event){
        List<Alumno> alumnosSeleccionado = tableAlumnos.getSelectionModel().getSelectedItems();
        for(Alumno a: alumnosSeleccionado){
            AlumnoResource recursoAlumno = new AlumnoResource();
            if(!recursoAlumno.inscribirAGrupo(a, grupoSeleccionado)){
                System.out.println("Error al inscribir alumno");
            }
        }
        
        controladorPadre.setTabla();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    public void onCancelar(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

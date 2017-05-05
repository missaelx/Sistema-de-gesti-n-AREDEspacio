package miamifx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.GrupoClase;
import recursos.GrupoClaseResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class VerListaAlumnoGrupoController implements Initializable {

    @FXML
    private TableView tableAlumnos;
    @FXML
    TableColumn columNombre;
    
    private GrupoClase grupoSeleccionado;
    private AdministrarDanzasController controladorPadre;

    public AdministrarDanzasController getControladorPadre() {
        return controladorPadre;
    }

    public void setControladorPadre(AdministrarDanzasController controladorPadre) {
        this.controladorPadre = controladorPadre;
    }
    

    public GrupoClase getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(GrupoClase grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
        setTabla();
    }
    
    public void setTabla(){
        columNombre.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> alumno) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(alumno.getValue().getNombre() + " " + alumno.getValue().getApellidos());
                    return property;
                }
            }
        
        );
        tableAlumnos.refresh();
        
        GrupoClaseResource recursoGrupo = new GrupoClaseResource();
        grupoSeleccionado = recursoGrupo.getGrupoClasePorId(grupoSeleccionado.getId());
        
        ObservableList lista = FXCollections.observableArrayList(grupoSeleccionado.getAlumnoList());
        
        tableAlumnos.setItems(lista);
    }
    
    @FXML
    public void onAgregarAlumnoAGrupo(ActionEvent event){
        try {
            Stage verListaAlumnos = new Stage();
            
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/AgregarAlumnoAGrupo.fxml")); 
            AnchorPane root = cargador.load();
            AgregarAlumnoAGrupoController control = (AgregarAlumnoAGrupoController) cargador.getController();
            
            control.setControladorPadre(this);
            control.setGrupoSeleccionado(grupoSeleccionado);
            
            Scene escena = new Scene(root);
            verListaAlumnos.setScene(escena);
            verListaAlumnos.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

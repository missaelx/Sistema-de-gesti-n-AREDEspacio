/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.GrupoClase;
import recursos.AlumnoResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class ListaAlumnosController implements Initializable {
    
    @FXML
    private ComboBox comboBusqueda;
    @FXML
    private TextField campoBusqueda;
    @FXML
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn columnaNombre, columnoApellidos, columnaAsistencia;
    @FXML
    private ImageView fotoAlumno;
    @FXML
    private Button bAgregar, bEliminar, bAsistencia,bCerrar;
    private GrupoClase grupoDanza;
    private List<Alumno> listaAlumnos;
    private AdministrarDanzasController controlador;
    
    
    
    @FXML
    private void buscarAlumno(ActionEvent event) {
        AlumnoResource recurso = new AlumnoResource();
        //ObservableList lista = FXCollections.observableArrayList();

        List<Alumno> listaBusqueda = new ArrayList<>();

        if (comboBusqueda.getValue().toString().equals("Nombre")) {
            listaBusqueda = recurso.buscarAlumnoPorNombre(campoBusqueda.getText());
        } else if (comboBusqueda.getValue().toString().equals("Correo")) {
            listaBusqueda = recurso.buscarAlumnoPorCorreo(campoBusqueda.getText());
        }

        ObservableList lista = FXCollections.observableList(listaBusqueda);
        tablaAlumnos.setItems(lista);

    }
    
    @FXML 
    private void registrarAsistencia(ActionEvent event){
        Stage registrarAsistencia = new Stage();
        FXMLLoader cargador = new FXMLLoader (getClass().getClassLoader().getResource("miamifx/interfaces/PasarLista.fxml"));
        try {
            AnchorPane root = cargador.load();
            PasarListaController controller = cargador.getController();
        controller.setClase(grupoDanza);
        registrarAsistencia.setScene(new Scene(root));
        registrarAsistencia.show();
        } catch (IOException ex) {
            Logger.getLogger(ListaAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @FXML
    private void activarBusqueda(ActionEvent event) {
        //btnBuscar.setDisable(false);
        campoBusqueda.setDisable(false);
    }
    
    public void setTablaAgregar() {
        tablaAlumnos.refresh();
        AlumnoResource recurso = new AlumnoResource();
        ObservableList lista = FXCollections.observableArrayList(recurso.visualizarRegistros());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnoApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaAsistencia.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tablaAlumnos.setItems(lista);

    }
    
    public void setTabla() {
        tablaAlumnos.refresh();
        AlumnoResource recurso = new AlumnoResource();
        //ObservableList lista
    }
    
    private void activarBotones() {
        this.bEliminar.setDisable(false);
        this.bCerrar.setDisable(false);
        
    }
    
    public void setControlador(AdministrarDanzasController controlador) {
        this.controlador = controlador;
    }
    public void setGrupoDanza(GrupoClase grupoDanza){
        this.grupoDanza = grupoDanza;
    }
    @FXML
    private void cerrar(ActionEvent evento) {
        ((Node) (evento.getSource())).getScene().getWindow().hide();
    }
    
    
    public void onTableSelection() {
        activarBotones();
        Alumno alumnoSeleccionado = (Alumno) tablaAlumnos.getSelectionModel().getSelectedItem();
        if (alumnoSeleccionado.getFoto() != "" && alumnoSeleccionado.getFoto() != null) {
            Image image = null;
            File file;
            try {
                file = new File(alumnoSeleccionado.getFoto());
                image = new Image(file.toURI().toURL().toExternalForm());
                fotoAlumno.setImage(image);
            } catch (MalformedURLException ex) {
                System.out.println("Ruta incorrecta");
            }
        } else {
            Path currentRelativePath = Paths.get("");
            String currentPath = currentRelativePath.toAbsolutePath().toString();
            String dfu = currentPath + "/USERSPICTURES/userDefault.png";
            File file = new File(dfu);
            try {
                Image image = new Image(file.toURI().toURL().toExternalForm());
                fotoAlumno.setImage(image);
            } catch (MalformedURLException ex1) {
                Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void agregarAlumnoALista(){
        grupoDanza.getAlumnoList().add(tablaAlumnos.getSelectionModel().getSelectedItem());
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

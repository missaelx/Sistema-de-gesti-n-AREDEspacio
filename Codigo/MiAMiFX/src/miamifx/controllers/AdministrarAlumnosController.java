/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Alumno;
import recursos.AlumnoResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class AdministrarAlumnosController implements Initializable {
    
    @FXML 
    private Button btnEliminar, btnInscribir, btnDetalles;
    @FXML 
    private ComboBox comboBusqueda;
    @FXML 
    private TextField campoBusqueda;
    @FXML 
    private TableView tablaAlumnos;
    @FXML
    private TableColumn columnaNombre, columnaDireccion, columnaTelefono;
    @FXML 
    private ImageView fotoAlumno;
    
    @FXML
    private void inscribirAlumno(ActionEvent event){
        try {
            Stage inscribirAlumno = new Stage();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/RegistrarAlumno.fxml").toURL();            
            AnchorPane root = FXMLLoader.load(url);
            Scene escena = new Scene(root);
            inscribirAlumno.setScene(escena);
            inscribirAlumno.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML 
    private void verDetalles(ActionEvent event){
        try {
            Stage inscribirAlumno = new Stage();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/EditarAlumno.fxml").toURL();            
            AnchorPane root = FXMLLoader.load(url);
            Scene escena = new Scene(root);
            inscribirAlumno.setScene(escena);
            inscribirAlumno.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void buscarAlumno(ActionEvent event){
        ArrayList<Alumno> alumnos = new ArrayList();
        
        campoBusqueda.getText();
    }
    
    @FXML 
    private void editarDatos(ActionEvent event){
        
        
    }
    
    @FXML 
    private void eliminarRegistro(ActionEvent evento){
        
    }
            
    private void setTabla(){
        AlumnoResource recurso = new AlumnoResource();        
        ObservableList lista = FXCollections.observableArrayList(recurso.visualizarRegistros());
        
        columnaNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        columnaDireccion.setCellFactory(new PropertyValueFactory<Alumno, String>("direccion"));
        columnaTelefono.setCellFactory(new PropertyValueFactory<Alumno, String>("telefono"));
        
        tablaAlumnos.setItems(lista);
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBusqueda.getItems().addAll("Nombre","telefono");
        setTabla();
    }
}

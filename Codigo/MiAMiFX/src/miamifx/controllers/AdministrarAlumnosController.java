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
import javafx.scene.Node;
import javafx.scene.Parent;
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
    private Button btnEliminar, btnInscribir, btnDetalles, btnBuscar;
    @FXML 
    private ComboBox comboBusqueda;
    @FXML 
    private TextField campoBusqueda;
    @FXML 
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn columnaNombre, columnoApellidos, columnaCorreo, columnaTelefono;
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
            Stage editarAlumno = new Stage();
            Alumno alumno = tablaAlumnos.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/EditarAlumno.fxml"));
            
            
            AnchorPane root = cargador.load();
            
            EditarAlumnoController editarAlumnoController = (EditarAlumnoController) cargador.getController();
            
            editarAlumnoController.setAlumno(alumno);
            cargador.setController(editarAlumnoController);
            editarAlumnoController.setCampos(alumno);
            
            Scene escena = new Scene(root);
            editarAlumno.setScene(escena);
            editarAlumno.show();
        } catch (IOException ex) {
            Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void buscarAlumno(ActionEvent event){        
        AlumnoResource recurso = new AlumnoResource();
        //ObservableList lista = FXCollections.observableArrayList();
        
        
        List<Alumno> listaBusqueda = new ArrayList<>();
        
        if(comboBusqueda.getValue().toString().equals("Nombre")){
           listaBusqueda = recurso.buscarAlumnoPorNombre(campoBusqueda.getText());
        }
        else if(comboBusqueda.getValue().toString().equals("Correo")){
            listaBusqueda = recurso.buscarAlumnoPorCorreo(campoBusqueda.getText());
        }
        
        ObservableList lista = FXCollections.observableList(listaBusqueda);
        tablaAlumnos.setItems(lista);
        
        
    }
    
    @FXML 
    private void activarBusqueda(ActionEvent event){
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> da8e5547bbec9ce83427bb7f2bd3c7743dc444af
>>>>>>> 83c6bf1376a33b88435cca7e698a0918de564616
>>>>>>> c4401e3562f26a4afb368c9926c0ae0960b6479f
        System.out.println("si jala");
        btnBuscar.setDisable(false);
        campoBusqueda.setDisable(false);
    }
    @FXML 
    private void editarDatos(ActionEvent event){
        
        
    }
    
    @FXML 
    private void eliminarRegistro(ActionEvent evento){
        Alumno alumno = tablaAlumnos.getSelectionModel().getSelectedItem();
        AlumnoResource recurso = new AlumnoResource();
        try {
            recurso.eliminarAlumno(alumno);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTabla();
    }
            
    private void setTabla(){
        AlumnoResource recurso = new AlumnoResource();        
        ObservableList lista = FXCollections.observableArrayList(recurso.visualizarRegistros());
        columnaNombre.setCellValueFactory( new PropertyValueFactory<>("nombre"));
        columnoApellidos.setCellValueFactory( new PropertyValueFactory<>("apellidos"));
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tablaAlumnos.setItems(lista);
        
    }
    
    private void setTabla(ObservableList lista){
        tablaAlumnos.setItems(lista);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBusqueda.getItems().addAll("Nombre","Telefono","Correo");
        campoBusqueda.setDisable(true);
        btnBuscar.setDisable(true);
        comboBusqueda.getItems().addAll("Nombre","Correo");
        setTabla();
    }
}

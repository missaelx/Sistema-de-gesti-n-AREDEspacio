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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.TipoDanza;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class AdministrarDanzasController implements Initializable {
    @FXML 
    private TabPane pestañas;
    @FXML 
    private Button bNuevaDanza, bVerDetalles, bCrearGrupo, bEliminarDanza;
    @FXML
    private TableView<TipoDanza> tablaDanzas;
    
    @FXML 
    public void nuevaDanza(ActionEvent evento) throws MalformedURLException, IOException{
        
        try {
            Stage crearDanza = new Stage();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/CrearDanzas.fxml").toURL();            
            AnchorPane root = FXMLLoader.load(url);
            Scene escena = new Scene(root);
            crearDanza.setScene(escena);
            crearDanza.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    @FXML 
    private void verDetalles(ActionEvent evento){
        try {
            Stage editarAlumno = new Stage();
            TipoDanza danza = tablaDanzas.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/EditarAlumno.fxml").toURL();            
            AnchorPane root = cargador.load(url);
            EditarAlumnoController editarAlumnoController = (EditarAlumnoController) cargador.getController();
            //editarAlumnoController.setAlumno(alumno);
            Scene escena = new Scene(root);
            editarAlumno.setScene(escena);
            editarAlumno.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML 
    private void crearGrupo(ActionEvent evento){
        
        
    }
    @FXML 
    private void eliminarDanza(ActionEvent evento){
        
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}


/*final Tab pestañaCrearDanzas = new Tab("Crear Danza " + (pestañas.getTabs().size() + 1));
        pestañas.getTabs().add(pestañaCrearDanzas);
        pestañas.getSelectionModel().select(pestañaCrearDanzas);
        URL url = new File("src/miamifx/CrearDanza.fxml").toURL();
        pestañaCrearDanzas.setContent((Node)  FXMLLoader.load(url));
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CrearDanza.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Crear Danza");
            stage.setScene(new Scene(root1));  
            stage.show();
        */
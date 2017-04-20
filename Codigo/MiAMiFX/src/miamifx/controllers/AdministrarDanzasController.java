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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.TipoDanza;
import recursos.DanzaResource;

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
    private TableColumn columnaDanza, columnaDescripcion, columnaMaestros, columnaHorario;
    @FXML
    private TableView<TipoDanza> tablaDanzas, tablaGrupoDanzas;
    
    @FXML 
    public void nuevaDanza(ActionEvent evento) throws MalformedURLException, IOException{
        
        try {
            Stage crearDanza = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearDanza.fxml"));

            //URL url = new File("src/miamifx/CrearDanza.fxml").toURL();            
            AnchorPane root = cargador.load();
            CrearDanzaController control = (CrearDanzaController) cargador.getController();
            control.setControlador(this);
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
            Stage editarDanzas = new Stage();
            TipoDanza danza = tablaDanzas.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/interfaces/EditarDanza.fxml").toURL();
            AnchorPane root = cargador.load(url);
            Scene escena = new Scene(root);
            editarDanzas.setScene(escena);
            editarDanzas.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    public void setTabla(){
        DanzaResource recurso = new DanzaResource();
        ObservableList list = FXCollections.observableArrayList(recurso.visualizarRegistros());
        columnaDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaDanzas.setItems(list);
        
        
    }
    
    @FXML 
    private void crearGrupo(ActionEvent evento){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText("No funciona aun  D: \n       '\\\n" +
"       _\\______\n" +
"         /        \\========\n" +
"  ____|__________\\_____\n" +
" / ___________________ \\\n" +
" \\/ _===============_ \\/\n" +
"     -===============-");
        alerta.show();
        
    }
    @FXML 
    private void eliminarDanza(ActionEvent evento){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea eliminar la seleccion?");
        confirmacion.setTitle("Confirmacion");
        if(confirmacion.showAndWait().get().equals(ButtonType.OK)){
        try {
            TipoDanza tipoDanza = tablaDanzas.getSelectionModel().getSelectedItem();
            DanzaResource recurso = new DanzaResource();
            recurso.eliminarDanza(tipoDanza);
        } catch (Exception e) {
            Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, e);
        }
        setTabla();
        }
        
    }
    
    @FXML
    private void seSelecciono(){
        if(tablaDanzas.getSelectionModel().getSelectedItem()!=null){
            //bVerDetalles.setDisable(false);
            bEliminarDanza.setDisable(false);
            bCrearGrupo.setDisable(false);
        }else{
            bVerDetalles.setDisable(true);
            bEliminarDanza.setDisable(true);
            bCrearGrupo.setDisable(true);
        }
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*
        bVerDetalles.setDisable(true);
        bEliminarDanza.setDisable(true);
        bCrearGrupo.setDisable(true);
        */
        setTabla();
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
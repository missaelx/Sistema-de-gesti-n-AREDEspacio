/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class PasarListaController implements Initializable {

   @FXML
   private TableView tablaAlumnos;
   @FXML
   private TableColumn columnaNombre, columnaAsistencia;
   @FXML
   private JFXButton btnGuardar, btnCancelar;
   @FXML
   private DatePicker datePicker;
   
   
   @FXML
   private void Guardar(ActionEvent event){
       
   }
   
   @FXML
   private void Cancelar(ActionEvent event){
       
   }
   
   public void setTabla(){
       
   }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

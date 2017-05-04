/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.GrupoClase;
import recursos.GrupoClaseResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class PasarListaController implements Initializable {

   @FXML
   private TableView<Alumno> tablaAlumnos;
   @FXML
   private TableColumn columnaNombre, columnaAsistencia;
   @FXML
   private JFXButton btnGuardar, btnCancelar;
   @FXML
   private DatePicker datePicker;
   
   private GrupoClase clase;
   
   
   @FXML
   private void Guardar(ActionEvent event){
       
   }
   
   @FXML
   private void Cancelar(ActionEvent event){
       
   }
   
   public void setTabla(){
       ObservableList<Alumno> lista = (ObservableList<Alumno>) clase.getAlumnoList();
       columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
       //columnaAsistencia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumno, CheckBox>>(){
           
       //};
               
       tablaAlumnos.setItems(lista);
       
       
   }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

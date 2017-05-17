/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import com.jfoenix.controls.JFXButton;
import controladores.AsistenciaJpaController;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.Asistencia;
import modelo.GrupoClase;
import recursos.AlumnoResource;
import recursos.AsistenciaResource;
import recursos.GrupoClaseResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class PasarListaController implements Initializable {

   @FXML
   private VBox boxAlumnos;
   @FXML
   private Text txtNombreGrupo;
   
   @FXML
   private Button btnGuardar, btnCancelar;
   @FXML
   private DatePicker datePicker;
   
   private HashMap <Integer, CheckBox> asistenciaAlumnos = new HashMap();
   
   private GrupoClase clase;
   
   public void setClase(GrupoClase clase){
       this.clase = clase;
   }
   
   
   @FXML
   private void Guardar(ActionEvent event){       
       AlumnoResource recursoAlumno = new AlumnoResource();
       AsistenciaResource recursoAsistencia = new AsistenciaResource();
       asistenciaAlumnos.forEach((t, u) -> {
           if(u.isSelected()){
               Asistencia asistencia = new Asistencia();
               asistencia.setDia(java.sql.Date.valueOf(datePicker.getValue()));
               asistencia.setIdAlumno(recursoAlumno.getAlumnoPorId(t));
               asistencia.setIdGrupoClase(clase);
               recursoAsistencia.registrarAsistencia(asistencia);
           }
       });
       btnGuardar.getScene().getWindow().hide();
       
   }
   
   @FXML
   private void Cancelar(ActionEvent event){
       Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
       confirmacion.setContentText("Esta seguro que desea cancelar el registro?");
        confirmacion.setTitle("Cancelar Registro");

        if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
            btnGuardar.getScene().getWindow().hide();
        }

   }
   
   public void setListaAsistencia(){
        List<Alumno> lista = clase.getAlumnoList();
        int id =0;
        for(Alumno alumno:lista){
            CheckBox check = new CheckBox(alumno.getNombre() + " " +alumno.getApellidos());
            asistenciaAlumnos.put(alumno.getId(), check);
            Separator separador = new Separator();
            boxAlumnos.getChildren().add(check);
            boxAlumnos.getChildren().add(separador);
        }
       
   }
   
   public void setNombreGrupo(){
       this.txtNombreGrupo.setText("Clase de " + clase.getIdTipoDanza().getNombre() + " del maestro " + clase.getIdMaestro().getNombre() + " " + clase.getIdMaestro().getApellidos());
   }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setValue(LocalDate.now());
        boxAlumnos.setPadding(new Insets(10, 10, 10, 10));
    }    
    
}

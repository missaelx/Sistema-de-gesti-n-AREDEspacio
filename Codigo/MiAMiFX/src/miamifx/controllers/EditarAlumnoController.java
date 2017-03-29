/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class EditarAlumnoController implements Initializable {

    @FXML
    private Button btnEditar, btnCancelar, btnExaminar;
    @FXML
    private TextField campoNumeroEmergencia, campoNumero, campoCorreo, campoNombre, campoApellidos;
    @FXML 
    private DatePicker campoFechaNacimiento;
    @FXML
    private ComboBox campoSangre;
    @FXML
    private ImageView imagenAlumno;
    
    private Alumno alumno;
    
    public void setAlumno(Alumno alumn){
        this.alumno = alumn;
    }
    
    
    @FXML
    private void editarDatos(ActionEvent event){
        try {
            Stage inscribirAlumno = new Stage();
            FXMLLoader cargador = new FXMLLoader();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/RegistrarAlumno.fxml").toURL();            
            AnchorPane root = cargador.load(url);
            RegistrarAlumnoController registrarAlumnoController = (RegistrarAlumnoController) cargador.getController();
            registrarAlumnoController.setAlumno(alumno);
            Scene escena = new Scene(root);
            inscribirAlumno.setScene(escena);
            inscribirAlumno.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setCampos(Alumno alumn){
        if(alumn != null){
        this.campoNumeroEmergencia.setText(alumn.getTelefonoEmergencia());
        this.campoNumeroEmergencia.setText(alumn.getTelefonoEmergencia());
        this.campoCorreo.setText(alumn.getCorreo());
        this.campoNombre.setText(alumn.getNombre());
        this.campoFechaNacimiento.setPromptText(alumn.getFechaNacimiento().toString());
        this.campoApellidos.setText(alumn.getApellidos());
        this.campoSangre.setValue(alumn.getTipoSangre());
        this.campoNumero.setText(alumn.getTelefono());
        }else{
            System.out.println("alumno nulo");
        }

    }
    @FXML 
    private void cancelar(ActionEvent event){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(alumno != null ){
            this.campoNumeroEmergencia.setText(alumno.getTelefonoEmergencia());
        this.campoCorreo.setText(alumno.getCorreo());
        this.campoNombre.setText(alumno.getNombre());
        this.campoFechaNacimiento.setPromptText(alumno.getFechaNacimiento().toString());
        this.campoApellidos.setText(alumno.getApellidos());
        this.campoSangre.setPromptText(alumno.getTipoSangre());
        this.campoNumero.setText(alumno.getTelefono());
        }
        
    }    
    
}

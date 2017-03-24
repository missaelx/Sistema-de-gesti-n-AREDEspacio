/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class EditarAlumnoController implements Initializable {

    @FXML
    private Button btnEditar, btnCancelar, btnExaminar;
    @FXML
    private TextField campoNumeroEmergencia, campoNumero, campoCorreo, campoNombre, campoNombreApellidos;
    @FXML 
    private DatePicker campoFechaNacimiento;
    @FXML
    private ComboBox campoSangre;
    @FXML
    private ImageView imagenAlumno;
    
    
    
    @FXML
    private void editarDatos(ActionEvent event){
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

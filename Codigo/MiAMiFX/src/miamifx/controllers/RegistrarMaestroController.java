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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Maestro;
import recursos.MaestroResource;


public class RegistrarMaestroController implements Initializable {

    @FXML
    private TextField campoNombre, campoApellido, campoCorreo, campoTelefono;
    @FXML
    private Button btnGuardar, btnCancelar;
    
    @FXML
    private void guardarRegistro(ActionEvent event){
        MaestroResource recurso = new MaestroResource();
        if(campoNombre.getText().equals("" ) || campoApellido.getText().equals("" )
                || campoCorreo.getText().equals("" ) || campoTelefono.getText().equals("" )){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacion incompleta");
            alerta.setContentText("Los campos de registro no pueden estar vacios. Porfavor ingresa la informacion completa. ");
        }else{
            Maestro maestro = new Maestro();
            maestro.setActivo(true);
            maestro.setNombre(campoNombre.getText());
            maestro.setApellidos(campoApellido.getText());
            maestro.setCorreo(campoCorreo.getText());
            maestro.setTelefono(campoTelefono.getText());
            recurso.registrarMaestro(maestro);
        }
        btnGuardar.getScene().getWindow().hide();
    }
    
    @FXML 
    private void cancelar(ActionEvent event){
        btnCancelar.getScene().getWindow().hide();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

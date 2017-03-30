/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Maestro;
import recursos.MaestroResource;


public class RegistrarMaestroController implements Initializable {

     private AdministrarMaestrosController control;
     private Stage stage;
    @FXML
    private TextField campoNombre, campoApellido, campoCorreo, campoTelefono;
    @FXML
    private Button btnGuardar, btnCancelar;
    
    
    public void setContro(AdministrarMaestrosController controlador){
        this.control = controlador;
    }
    
    public void setStage(Stage escenario){
        this.stage = escenario;
    }
    @FXML
    private void guardarRegistro(ActionEvent event){
        MaestroResource recurso = new MaestroResource();
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        if(campoNombre.getText().equals("" ) || campoApellido.getText().equals("" )
                || campoCorreo.getText().equals("" ) || campoTelefono.getText().equals("" )){
            alerta.setTitle("Informacion incompleta");
            alerta.setContentText("Los campos de registro no pueden estar vacios. Porfavor ingresa la informacion completa. ");
            alerta.show();
        }else{
            Maestro maestro = new Maestro();
            maestro.setActivo(true);
            maestro.setNombre(campoNombre.getText());
            maestro.setApellidos(campoApellido.getText());
            maestro.setCorreo(campoCorreo.getText());
            maestro.setTelefono(campoTelefono.getText());
            if(recurso.registrarMaestro(maestro)){
                alerta.setTitle("Transaccion Exitosa");
                alerta.setContentText("El registro se llevo a cabo exitosamente");
                alerta.show();
                control.setTabla();
                control.setVentana(false);
                btnGuardar.getScene().getWindow().hide();
            }else{
                alerta.setTitle("Transaccion nula");
                alerta.setContentText("El registro no se ha podido completar correctamente. Porfavor intenta nuevamente");
                alerta.show();
            }
        }
    }
    
    @FXML 
    private void cancelar(ActionEvent event){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea cancelar el registro?");
        confirmacion.setTitle("Cancelar Registro");
        
        if(confirmacion.showAndWait().get().equals(ButtonType.OK)){
            control.setVentana(false);
            btnCancelar.getScene().getWindow().hide();
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

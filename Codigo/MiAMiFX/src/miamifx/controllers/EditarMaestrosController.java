/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import controladores.exceptions.NonexistentEntityException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import modelo.Maestro;
import recursos.MaestroResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class EditarMaestrosController implements Initializable {

    private Maestro maestro;
    private AdministrarMaestrosController administrar;
    
    @FXML 
    private Button btnEditar, btnCancelar, btnPagos, btnGuardar;
    
    @FXML
    private TextField campoNombre, campoApellido, campoTelefono, campoCorreo;
    
    public void setMaestro(Maestro maestr){
        this.maestro = maestr;
    }
    
    public void setAdministrar(AdministrarMaestrosController control){
        this.administrar = control;
    }
    
    @FXML 
    private void cerrar(ActionEvent event){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Desea cancelar la edicion?");
        confirmacion.setTitle("Confirmacion");
        if(btnCancelar.getText().equals("Cancelar")){
            if(confirmacion.showAndWait().get().equals(ButtonType.OK)){
                btnCancelar.getScene().getWindow().hide();
            }
        }        
        btnCancelar.getScene().getWindow().hide();
    }
    
    @FXML
    private void editarInformacion(){
        btnEditar.setVisible(false);
        btnGuardar.setVisible(true);
        btnCancelar.setText("Cancelar");
        btnCancelar.setCancelButton(true);
        this.campoNombre.setEditable(true);
        this.campoApellido.setEditable(true);
        this.campoTelefono.setEditable(true);
        this.campoCorreo.setEditable(true);
    }
    
    @FXML
    private void guardarCambios(ActionEvent event){
        MaestroResource recurso = new MaestroResource();
        
        if(!this.maestro.getNombre().equals(this.campoNombre.getText())){
            maestro.setNombre(this.campoNombre.getText());
        }
        if(!this.maestro.getApellidos().equals(this.campoApellido.getText())){
            maestro.setApellidos(this.campoApellido.getText());
        }
        if(!this.maestro.getTelefono().equals(this.campoTelefono.getText())){
            maestro.setTelefono(this.campoTelefono.getText());
        }
        if(!this.maestro.getCorreo().equals(this.campoCorreo.getText())){
            maestro.setCorreo(this.campoCorreo.getText());
        }
        try {
            recurso.modificarMaestro(maestro);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditarMaestrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        administrar.setTabla();
        btnGuardar.getScene().getWindow().hide();
        
        
    }
    public void setCampos(){
        this.campoNombre.setText(maestro.getNombre());
        this.campoApellido.setText(maestro.getApellidos());
        this.campoTelefono.setText(maestro.getTelefono());
        this.campoCorreo.setText(maestro.getCorreo());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnGuardar.setVisible(false);
    }    
    
}

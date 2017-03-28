/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class CrearDanzaController implements Initializable {
    
    private Button botonGuardar, botonCancelar, botonGuardarYCGrupo;
    private TextField tfNuevaDanza;
    
    public void tfNoVacio(){
        if(tfNuevaDanza.getText().isEmpty()){
            botonGuardar.setDisable(true);
            botonGuardarYCGrupo.setDisable(true);
        }else{ botonGuardar.setDisable(false); botonGuardarYCGrupo.setDisable(false);}
        
    }
    
    private void cancelar(ActionEvent evento){
        ((Node)(evento.getSource())).getScene().getWindow().hide();
        
        
        
    }
    
    
    private void guardar(ActionEvent evento){
        
    }
    
    private void guardarYCGrupo(ActionEvent evento){
        
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

/*tfNuevaDanza.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        */
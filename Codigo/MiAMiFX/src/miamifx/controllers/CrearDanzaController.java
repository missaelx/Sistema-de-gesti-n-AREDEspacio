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
import modelo.TipoDanza;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class CrearDanzaController implements Initializable {
<<<<<<< HEAD
    @FXML
=======
>>>>>>> eeb483894afafb1b211cc23fa16ad00e77e50f0c
    private Button botonGuardar, botonCancelar, botonGuardarYCGrupo;
    @FXML
    private TextField tfNombreDanza, tfDescripcion;
    private TipoDanza tipoDanza;
    
    @FXML
    public void tfNoVacio(){
        if(tfNombreDanza.getText().isEmpty() || tfDescripcion.getText().isEmpty()){
            botonGuardar.setDisable(true);
            botonGuardarYCGrupo.setDisable(true);
        }else{ botonGuardar.setDisable(false); botonGuardarYCGrupo.setDisable(false);}
        
    }
    @FXML
    private void cancelar(ActionEvent evento){
        ((Node)(evento.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    private void guardar(ActionEvent evento){
        tipoDanza = new TipoDanza();
        tipoDanza.setActivo(true);
        tipoDanza.setNombre(tfNombreDanza.getText());
        tipoDanza.setDescripcion(tfDescripcion.getText());
    }
    @FXML
    private void guardarYCGrupo(ActionEvent evento){
        tipoDanza = new TipoDanza();
        tipoDanza.setActivo(true);
        tipoDanza.setNombre(tfNombreDanza.getText());
        tipoDanza.setDescripcion(tfDescripcion.getText());
        
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
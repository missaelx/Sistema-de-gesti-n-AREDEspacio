/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import modelo.Maestro;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class EditarMaestrosController implements Initializable {

    private Maestro maestro;
    
    public void setMaestro(Maestro maestr){
        this.maestro = maestr;
    }
    
    public void setCampos(){
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

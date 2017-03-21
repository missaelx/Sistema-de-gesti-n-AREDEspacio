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

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class AdministrarAlumnosController implements Initializable {
    
    private mainController main;
    @FXML
    private Button btnInscribir;
    
    @FXML
    private void seleccionarFoto(ActionEvent event){
        System.out.println("Hola mundillo");
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void init(mainController aThis) {
        main = aThis;
    }
    
}

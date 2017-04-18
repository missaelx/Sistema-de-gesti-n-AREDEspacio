package miamifx.controllers;

import javafx.fxml.FXML;

/**
 *
 * @author Miguel Acosta
 */
public class mainController {
    @FXML AdministrarAlumnosController administrarAlumnos;
    @FXML PrincipalController principal ;
    
    @FXML public void initialize(){
        System.out.println("aplicacion lanzada");

    }
}

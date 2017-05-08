package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class PrincipalController implements Initializable {

    @FXML 
    private TabPane pestañas;
    @FXML 
    private Tab tabInicio, tabAlumnos, tabMaestros, tabDanzas, tabPromociones, tabEgresos, tabIngresos;
    @FXML
    private ImageView promociones, aredEspacioImagen;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            URL urlInicio = new File("src/miamifx/interfaces/Inicio.fxml").toURL();
            tabInicio.setContent((Node) FXMLLoader.load(urlInicio));
            
            URL urlAlumnos = new File("src/miamifx/interfaces/AdministrarAlumnos.fxml").toURL();
            tabAlumnos.setContent((Node) FXMLLoader.load(urlAlumnos));
            
            URL urlMaestros = new File("src/miamifx/interfaces/AdministrarMaestros.fxml").toURL();
            tabMaestros.setContent((Node) FXMLLoader.load(urlMaestros));
            
            URL urlDanzas = new File("src/miamifx/interfaces/AdministrarDanzas.fxml").toURL();
            tabDanzas.setContent((Node) FXMLLoader.load(urlDanzas));

            URL urlPromociones = new File("src/miamifx/interfaces/AdministrarPromociones.fxml").toURL();
            tabPromociones.setContent((Node) FXMLLoader.load(urlPromociones));
            
            URL urlEgresos = new File("src/miamifx/interfaces/AdministrarEgresos.fxml").toURL();
            tabEgresos.setContent((Node) FXMLLoader.load(urlEgresos));

            URL urlIngresos = new File("src/miamifx/interfaces/AdministrarIngresos.fxml").toURL();
            tabIngresos.setContent((Node) FXMLLoader.load(urlIngresos));
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        
    }    

    
    
}

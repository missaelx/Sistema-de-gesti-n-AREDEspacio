package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import miamifx.ControlPantalla.ControPantallas;
import miamifx.ControlPantalla.ControladorPantallas;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class PrincipalController implements Initializable {

    @FXML 
    private TabPane pestañas;
    @FXML 
    private Tab tabInicio, tabAlumnos, tabMaestros, tabDanzas;
    @FXML
    private ImageView promociones, aredEspacioImagen;
    
    @FXML 
    private void inscribirAlumno(ActionEvent evento){
        try {
            Stage inscribirAlumno = new Stage();
            //FXMLLoader cargador = javafx.fxml.FXMLLoader.load(getClass().getClassLoader().getResource("miamifx/RegistrarAlumno.fxml"));

            URL url = new File("src/miamifx/RegistrarAlumno.fxml").toURL();            
            AnchorPane root = FXMLLoader.load(url);
            Scene escena = new Scene(root);
            inscribirAlumno.setScene(escena);
            inscribirAlumno.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*try{
            Tab nuevaPestana = new Tab("Registro de alumno");
            nuevaPestana.setStyle("-fx-background-color: #2f2");
            pestañas.getTabs().add(nuevaPestana);
            URL url = new File("src/miamifx/RegistrarAlumno.fxml").toURL();
            nuevaPestana.setContent((Node) FXMLLoader.load(url));
            }catch(IOException e){
            e.printStackTrace();
            }*/
    }
    @FXML 
    private void seleccionarFoto(ActionEvent evento){
        System.out.println("nada");
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            URL urlAlumnos = new File("src/miamifx/AdministrarAlumnos.fxml").toURL();
            tabAlumnos.setContent((Node) FXMLLoader.load(urlAlumnos));
            
            URL urlMaestros = new File("src/miamifx/AdministrarMaestros.fxml").toURL();
            tabMaestros.setContent((Node) FXMLLoader.load(urlMaestros));
            
            URL urlDanzas = new File("src/miamifx/AdministrarDanzas.fxml").toURL();
            tabDanzas.setContent((Node) FXMLLoader.load(urlDanzas));
            
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx;
import miamifx.ControlPantalla.ControPantallas;
import miamifx.ControlPantalla.ControladorPantallas;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author macbookpro
 */
public class MiAMiFX extends Application {

    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Principal.fxml"));
 
        Scene scene = new Scene(root);
 
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        /*ControPantallas control = new ControPantallas();
        if(control.cargarPantalla(this.idRegistrarAlumno, this.escenaRegistrarAlumno)){        
            control.establecerPantalla(MiAMiFX.idRegistrarAlumno);
        }else{
            System.out.println("no se agrego");
        }
        Group root = new Group();
        root.getChildren().addAll(control);
        Scene escena = new Scene(root);
        primaryStage.setScene(escena);
        primaryStage.show();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

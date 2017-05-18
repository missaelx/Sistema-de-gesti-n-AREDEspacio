package miamifx;

import java.io.IOException;
import java.util.prefs.Preferences;
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
    private Preferences prefs;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        prefs = Preferences.userRoot().node("miamiPref");
        
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Principal.fxml"));
 
        Scene scene = new Scene(root);
 
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Ared Espacio - MiAMi System");
        primaryStage.show();
        
        prefs.getDouble("monto-inscripcion", 500);
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

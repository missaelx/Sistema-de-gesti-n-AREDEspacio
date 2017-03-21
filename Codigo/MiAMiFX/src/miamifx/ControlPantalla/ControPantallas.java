package miamifx.ControlPantalla;


import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


/**
 *
 * @author Miguel Acosta
 */
public class ControPantallas extends AnchorPane{
    
     private HashMap<String, Node> pantallas = new HashMap<>();
     
     public void agregarPantalla(String name, Node screen) { 
       pantallas.put(name, screen); 
   } 
     
     public ControPantallas(){};
    
     public boolean cargarPantalla(String name, String resource) { 
     try { 
       FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource)); 
       Parent loadScreen = (Parent) myLoader.load(); 
       ControladorPantallas controlador = 
              ((ControladorPantallas) myLoader.getController()); 
       controlador.setScreenParent((ControladorPantallas) this); 
       agregarPantalla(name, loadScreen); 
       return true; 
     }catch(Exception e) { 
       System.out.println(e.getMessage()); 
       return false; 
     }
   } 
     
     public boolean cargaPantalla(String nombre){
         try{
             FXMLLoader cargador = new FXMLLoader();
             return cargador.load(getClass().getResource(nombre));
         }catch(Exception e){
             System.out.println(e.getMessage());
             return false;
         }
     }
     
public boolean establecerPantalla(final String name) { 
    if(pantallas.get(name) != null) { //screen loaded 
        final DoubleProperty opacity = opacityProperty(); 

        //Is there is more than one screen 
        if(!getChildren().isEmpty()){ 
            Timeline fade = new Timeline( 
                new KeyFrame(Duration.ZERO, 
                    new KeyValue(opacity,1.0)), 
                new KeyFrame(new Duration(1000),
                (ActionEvent t) -> {
                    //remove displayed screen
                    getChildren().remove(0);
                    //add new screen
                    getChildren().add(0, pantallas.get(name));
                    Timeline fadeIn = new Timeline(
                            new KeyFrame(Duration.ZERO,
                                    new KeyValue(opacity, 0.0)),
                            new KeyFrame(new Duration(800),
                                    new KeyValue(opacity, 1.0)));
                    fadeIn.play();
            }, new KeyValue(opacity, 0.0))); 
         fade.play(); 
        } else { 
         //no one else been displayed, then just show 
         setOpacity(0.0); 
         getChildren().add(pantallas.get(name)); 
         Timeline fadeIn = new Timeline( 
            new KeyFrame(Duration.ZERO, 
                        new KeyValue(opacity, 0.0)), 
            new KeyFrame(new Duration(2500), 
                        new KeyValue(opacity, 1.0))); 
         fadeIn.play(); 
        } 
        return true; 
        } else { 
            System.out.println("La pantalla no se cargo\n"); 
            return false; 
        } 
    }  
}


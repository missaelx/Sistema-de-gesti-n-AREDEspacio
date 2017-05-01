package miamifx.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.GrupoClase;
import modelo.TipoDanza;
import recursos.DanzaResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class CrearGrupoDeDanzaController implements Initializable {
    
    @FXML
    private Button botonGuardar, botonCancelar;
    @FXML
    private JFXTimePicker entradaLunes,salidaLunes,entradaMartes,salidaMartes,entradaMiercoles,salidaMiercoles,entradaJueves,salidaJueves,
            entradaViernes,salidaViernes,entradaSabado,salidaSabado;
    @FXML
    private Label nombreDanza;
    
    private TipoDanza tipoDanza;
    private GrupoClase grupoDanza;
    private AdministrarDanzasController controlador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setTipoDazanza(TipoDanza tipoDanza){
        this.tipoDanza = tipoDanza;
        
    }
    public void setTituloNombreDanza(){
        nombreDanza.setText(tipoDanza.getNombre());
    }
    
    public void setControlador(AdministrarDanzasController controlador) {
        this.controlador = controlador;
    }
    
    @FXML
    private void cancelar(ActionEvent evento) {
        ((Node) (evento.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void horaClaseComplta(){
        if(!entradaLunes.getValue().equals(null) && !salidaLunes.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaMartes.getValue().equals(null) && !salidaMartes.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaMiercoles.getValue().equals(null) && !salidaMiercoles.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaJueves.getValue().equals(null) && !salidaJueves.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaViernes.getValue().equals(null) && !salidaViernes.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaSabado.getValue().equals(null) && !salidaSabado.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        
    }
    
    @FXML
    private void guardar(ActionEvent evento) {
        try {
            DanzaResource recurso = new DanzaResource();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Algun dia no tiene hora de salida, por favor ingresa la informacion completa");
            
            if (entradaSabado.converterProperty().toString().isEmpty()) {
                alerta.show();
            } else {
                grupoDanza = new GrupoClase();
                grupoDanza.setActivo(true);
                grupoDanza.setIdTipoDanza(tipoDanza);
                //grupoDanza.setNombre(tfNombreDanza.getText());
                //grupoDanza.setDescripcion(tfDescripcion.getText());
                recurso.crearGrupoClase(grupoDanza);
                controlador.setTabla();
                botonGuardar.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.show();

        }

    }
}

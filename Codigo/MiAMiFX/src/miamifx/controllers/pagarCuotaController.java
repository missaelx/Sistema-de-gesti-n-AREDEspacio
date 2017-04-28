package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import modelo.Promociones;
import recursos.PromocionesResource;

/**
 * Created by Miguel Acosta on 20/04/2017.
 */
public class pagarCuotaController implements Initializable {

    private ToggleGroup opciones = null;
    @FXML
    private Button btnPagar, btnCancelar;
    @FXML
    private ComboBox promociones;
    @FXML
    private Label textCantidadPago,textFechaPago;
    @FXML
    private CheckBox activarPromo;
    @FXML
    private RadioButton opcInscripcion, opcMensualidad;
    
    @FXML 
    private void pagarCuota(ActionEvent event){
        
    }
    
    private void setCantidadPago(String cuota){
        double pago;
        if(cuota.equals("inscripcion")){
            pago= 500;
        }else{
            pago=400;
        }
        if(promociones.getPlaceholder()!=null){
            Promociones promocion = (Promociones) promociones.getValue();
            pago = pago*(promocion.getPorcentajeDescuento()/100);
        }
        textCantidadPago.setText(String.valueOf(pago));
    }
    @FXML
    private void cancelar(ActionEvent event){
        
    }
    
    @FXML
    private void activarPromociones(ActionEvent event){
        if(activarPromo.isSelected()){
            promociones.setDisable(false);
        }else{
            promociones.setDisable(true);
        }
    }
    
    private void setPromociones(){
        PromocionesResource recurso = new PromocionesResource();
        ObservableList lista = FXCollections.observableArrayList(recurso.getActivos());
        promociones.setItems(lista);
    }
    private void setOpciones(){
        opcInscripcion.setToggleGroup(opciones);
        opcMensualidad.setToggleGroup(opciones);
        opciones.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(opciones.getSelectedToggle()==opcInscripcion){
                    setCantidadPago("Inscripcion");
                }else{
                    setCantidadPago("Mensualidad");
                }
            }
            
        });
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPromociones();
        setCantidadPago("");
        promociones.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                setCantidadPago("");
            }
        });
        
        
        
    }
}


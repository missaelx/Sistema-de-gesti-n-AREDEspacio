/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.TipoDanza;
import recursos.DanzaResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class EditarDanzaController implements Initializable {
    
    @FXML
    private Button botonGuardar, botonCancelar, botonEditar;
    @FXML
    private TextField tfNombreDanza, tfDescripcion;
    private TipoDanza tipoDanza;
    private AdministrarDanzasController controlador;
    

    @FXML
    public void tfNoVacio() {
        if (tfNombreDanza.getText().isEmpty() || tfDescripcion.getText().isEmpty()) {
            botonGuardar.setDisable(true);
        } else {
            botonGuardar.setDisable(false);
        }

    }

    public void añadirLimiteTexto(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
    
    public void soloNumerosTexto(){
        //tfDescripcion.setTextFormatter(value);
    }

    @FXML
    private void cancelar(ActionEvent evento) {
        ((Node) (evento.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void guardar(ActionEvent evento) {
        try {
            DanzaResource recurso = new DanzaResource();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Algunos campos se encuentan vacios, por favor ingresa la informacion completa");
            if (tfNombreDanza.getText().isEmpty() || tfDescripcion.getText().isEmpty()) {
                alerta.show();
            } else {
                tipoDanza.setNombre(tfNombreDanza.getText());
                tipoDanza.setDescripcion(tfDescripcion.getText());
                recurso.modificarDanza(tipoDanza);
                controlador.setTabla();
                botonGuardar.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.show();

        }

    }
    @FXML
    private void editar(ActionEvent evento){
        botonEditar.setVisible(false);
        botonGuardar.setVisible(true);
        tfNombreDanza.setEditable(true);
        tfDescripcion.setEditable(true);
    }
    
    public void setCampos(TipoDanza tipoDanza){
        tfNombreDanza.setText(tipoDanza.getNombre());
        tfDescripcion.setText(tipoDanza.getDescripcion());
        
    }
    
    public void setControlador(AdministrarDanzasController controlador) {
        this.controlador = controlador;
    }
    public void setTipoDanza(TipoDanza tipodanza){
        this.tipoDanza = tipodanza;
    }

    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

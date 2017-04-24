/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.TipoDanza;
import recursos.DanzaResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class CrearDanzaController implements Initializable {

    @FXML
    private Button botonGuardar, botonCancelar, botonGuardarYCGrupo;
    @FXML
    private TextField tfNombreDanza, tfDescripcion;
    private TipoDanza tipoDanza;
    private AdministrarDanzasController controlador;
    

    @FXML
    public void tfNoVacio() {
        if (tfNombreDanza.getText().isEmpty() || tfDescripcion.getText().isEmpty()) {
            botonGuardar.setDisable(true);
            botonGuardarYCGrupo.setDisable(true);
        } else {
            botonGuardar.setDisable(false);
            botonGuardarYCGrupo.setDisable(false);
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
                tipoDanza = new TipoDanza();
                tipoDanza.setActivo(true);
                tipoDanza.setNombre(tfNombreDanza.getText());
                tipoDanza.setDescripcion(tfDescripcion.getText());
                recurso.crearDanza(tipoDanza);
                controlador.setTabla();
                botonGuardar.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.show();

        }

    }

    @FXML
    private void guardarYCGrupo(ActionEvent evento) {
        DanzaResource recurso = new DanzaResource();
        tipoDanza = new TipoDanza();
        tipoDanza.setActivo(true);
        tipoDanza.setNombre(tfNombreDanza.getText());
        tipoDanza.setDescripcion(tfDescripcion.getText());
        recurso.crearDanza(tipoDanza);
        controlador.setTabla();
        botonGuardarYCGrupo.getScene().getWindow().hide();
        try {
            Stage crearGrupoDanza = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearGrupoDeDanza.fxml"));
            CrearGrupoDeDanzaController crearGrupoDeDanzaControl = (CrearGrupoDeDanzaController) cargador.getController();
            AnchorPane root = cargador.load();
            CrearGrupoDeDanzaController control = (CrearGrupoDeDanzaController) cargador.getController();
            control.setTipoDazanza(tipoDanza);
            control.setTituloNombreDanza();
            Scene escena = new Scene(root);
            crearGrupoDanza.setScene(escena);
            crearGrupoDanza.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        añadirLimiteTexto(tfNombreDanza, 45);
        añadirLimiteTexto(tfDescripcion, 45);

        /*
        botonCancelar.setDisable(true);
        botonGuardar.setDisable(true);
        botonGuardarYCGrupo.setDisable(true);
         */
    }

    /**
     * @param controlador the controlador to set
     */
    public void setControlador(AdministrarDanzasController controlador) {
        this.controlador = controlador;
    }

}

/*tfNuevaDanza.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
 */

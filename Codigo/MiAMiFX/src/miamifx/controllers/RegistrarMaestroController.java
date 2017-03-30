/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Maestro;
import recursos.MaestroResource;

public class RegistrarMaestroController implements Initializable {

    private AdministrarMaestrosController control;
    private Stage stage;
    @FXML
    private TextField campoNombre, campoApellido, campoCorreo, campoTelefono;
    @FXML
    private Button btnGuardar, btnCancelar;

    public void setContro(AdministrarMaestrosController controlador) {
        this.control = controlador;
    }

    public void setStage(Stage escenario) {
        this.stage = escenario;
    }

    @FXML
    private void guardarRegistro(ActionEvent event) {
        MaestroResource recurso = new MaestroResource();
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        if (campoNombre.getText().equals("") || campoApellido.getText().equals("")
                || campoCorreo.getText().equals("") || campoTelefono.getText().equals("")) {
            alerta.setTitle("Informacion no valida");
            alerta.setContentText("Los campos de registro no pueden estar vacios. Porfavor ingresa la informacion completa. ");
            alerta.show();
        } else {
            Maestro maestro = new Maestro();
            if (isValidName(campoNombre.getText())) {
                maestro.setNombre(this.campoNombre.getText());
                if (isValidName(campoApellido.getText())) {
                    maestro.setApellidos(this.campoApellido.getText());
                    if (isValidPhoneNumber(campoTelefono.getText())) {
                        maestro.setTelefono(this.campoTelefono.getText());
                        if (isValidEmailAddress(campoCorreo.getText())) {
                            maestro.setCorreo(campoCorreo.getText());
                            maestro.setActivo(true);
                            if (recurso.registrarMaestro(maestro)) {
                                alerta.setTitle("Transaccion Exitosa");
                                alerta.setContentText("El registro se llevo a cabo exitosamente");
                                alerta.show();
                                control.setTabla();
                                control.setVentana(false);
                                btnGuardar.getScene().getWindow().hide();
                            } else {
                                alerta.setTitle("Transaccion nula");
                                alerta.setContentText("El registro no se ha podido completar correctamente. Porfavor intenta nuevamente");
                                alerta.show();
                            }
                        }else{
                            alerta.setContentText("Formato incorrecto de correo electronico, por favor ingresa un correo electronico valido");
                            alerta.show();
                        }
                    } else {
                        alerta.setContentText("Numero de telefono invalido. Por favor ingresa un numero telefonico que incluya solo numeros (0-9)");
                        alerta.show();
                    }
                } else {
                    alerta.setContentText("Apellido invalido, por favor ingresa un nombre que solo incluya letras (a-z)");
                    alerta.show();
                }
            } else {
                alerta.setContentText("Nombre invalido, por favor ingresa un nombre que solo incluya letras (a-z)");
                alerta.show();
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event
    ) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea cancelar el registro?");
        confirmacion.setTitle("Cancelar Registro");

        if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
            control.setVentana(false);
            btnCancelar.getScene().getWindow().hide();
        }

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches() && email.length() <= 254;
    }

    public boolean isValidPhoneNumber(String phone) {
        String regexStr = "^[0-9]*$";
        return phone.matches(regexStr) && phone.length() <= 22;
    }

    public boolean isValidName(String name) {
        return name.matches("([a-z]|[A-Z]|\\s)+") && name.length() <= 45;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}

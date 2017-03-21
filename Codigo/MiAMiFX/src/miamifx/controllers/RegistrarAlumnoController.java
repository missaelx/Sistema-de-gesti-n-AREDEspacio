package miamifx.controllers;

import miamifx.ControlPantalla.ControladorPantallas;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modelo.Alumnos;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class RegistrarAlumnoController implements Initializable, ControladorPantallas {
    
    @FXML
    private Button btnGuardar, btnCancelar, btnExaminar;
    @FXML
    private TextField campoNombre, campoApellidos, campoCorreo, campoNumero, campoEmergencia;
    @FXML
    private ComboBox campoSangre;
    @FXML
    private DatePicker campoFechaNacimiento;
    
    ControladorPantallas controlador;
            
    
    public boolean esAlfanumerico(String cadena) {
        for (int i = 0; i < cadena.length(); ++i) {
            char caracter = cadena.charAt(i);
            if (!Character.isLetterOrDigit(caracter)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean tamañoCorrecto(String cadena) {
        return cadena.length() <= 20;
    }
    @FXML
    private void seleccionarFoto(ActionEvent event){
        System.out.println("Hola mundillo");
    }
    @FXML
    private void registrarAlumno(ActionEvent event){
        Alumnos alumno = new Alumnos();
        if(campoNombre.getText().equals("") || campoApellidos.getText().equals("")
                || campoCorreo.getText().equals("") || campoNumero.getText().equals("")
                || campoSangre.getValue().toString().equals("")
                ||campoFechaNacimiento.getValue()==null|| campoEmergencia.getText().equals("")){
            alumno.setNombre(campoNombre.getText());
            alumno.setApellidos(campoApellidos.getText());
            alumno.setCorreo(campoCorreo.getText());
            alumno.setTelefono(campoNumero.getText());
            alumno.setTipoSangre(campoSangre.getValue().toString());
            alumno.setFechaNacimiento(Date.from(campoFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            alumno.setTelefonoEmergencia(campoEmergencia.getText());
            
        }        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setScreenParent(ControladorPantallas screenParent) {
        controlador = screenParent;
    }
    
}

package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import miamifx.ControlPantalla.ControladorPantallas;
import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Alumno;

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
    
    private Stage contenedor;
    
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

    public void setContenedor(Stage contenedor) {
        this.contenedor = contenedor;
    }
    
    
    
    public boolean tamañoCorrecto(String cadena) {
        return cadena.length() <= 20;
    }
    @FXML
    private void seleccionarFoto(ActionEvent event){
        FileChooser chooser = new FileChooser();
    chooser.setTitle("Open File");
    File file = chooser.showOpenDialog(new Stage());
    
    }
    
    @FXML 
    private void cancelar(ActionEvent event) throws MalformedURLException, IOException{
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmacion");
        alerta.setContentText("Esta seguro que desea cancelar la operacion?");
        
        ButtonType si = new ButtonType("Si");
        ButtonType no = new ButtonType("No");
        
        
        
        alerta.getButtonTypes().addAll(si, no);
        alerta.show();
        try{
            alerta.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //((Stage) .getSource()).close();
                    contenedor.close();
                }
            });
        } catch(Exception e){
            System.out.println("-------");
            System.out.println(e.getMessage());
            
        }
        
        
    }
    @FXML
    private void registrarAlumno(ActionEvent event){
        Alumno alumno = new Alumno();
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
            alumno.setActivo(true);
            alumno.setDiapago(Calendar.getInstance().getTime());
        }        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        campoSangre.setEditable(false);
        campoSangre.getItems().addAll("O-", "O+","A-","A+","B+","B-","AB+","AB-");
    }    

    @Override
    public void setScreenParent(ControladorPantallas screenParent) {
        controlador = screenParent;
    }
    
}

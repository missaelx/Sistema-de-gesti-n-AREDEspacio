package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.TipoDanza;
import recursos.AlumnoResource;
import recursos.DanzaResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class RegistrarAlumnoController implements Initializable {

    
    @FXML
    private Button btnGuardar, btnCancelar, btnExaminar;
    @FXML
    private TextField campoNombre, campoApellidos, campoCorreo, campoNumero, campoEmergencia;
    @FXML
    private ComboBox campoSangre;
    @FXML
    private DatePicker campoFechaNacimiento;
    @FXML
    private ImageView imgAlumno;
    
    private Stage contenedor;
    private Alumno alumno;
    private String pathImgValidaTemporal;

    
    private AdministrarAlumnosController controlPadre;

    public AdministrarAlumnosController getControlPadre() {
        return controlPadre;
    }

    public void setControlPadre(AdministrarAlumnosController controlPadre) {
        this.controlPadre = controlPadre;
    }
            
    
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
    
    public void setAlumno(Alumno alumn){
        this.alumno = alumn;
    }
    
    
    
    public boolean tamañoCorrecto(String cadena) {
        return cadena.length() <= 20;
    }
    @FXML
    private void seleccionarFoto(ActionEvent event){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions = 
            new FileChooser.ExtensionFilter(
              "Imagenes", "*.png", "*.jpg", "*.bmp");
        chooser.setTitle("Selecciona una imagen");
        chooser.getExtensionFilters().add(fileExtensions);
        File file = chooser.showOpenDialog(new Stage());
        
        if(file != null){
            if(file.length() > 2000000){ //no fotos de mas de dos megas
                Alert confirmacion = new Alert(Alert.AlertType.WARNING);
                confirmacion.setContentText("La imagen tiene un tamaño mayor a 2MB, elija otra");
                confirmacion.setTitle("Imagen muy grande");
                confirmacion.showAndWait();
            } else {
                try {
                    String imageUrl = file.toURI().toURL().toExternalForm();
                    Image image = new Image(imageUrl);
                    imgAlumno.setImage(image);
                    pathImgValidaTemporal = file.getAbsolutePath();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(RegistrarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
    
    }
    
    @FXML 
    private void cancelar(ActionEvent event) throws MalformedURLException, IOException{
        btnCancelar.getScene().getWindow().hide();
    }
    @FXML
    private void registrarAlumno(ActionEvent event){
        Alumno alumno = new Alumno();
        java.util.Date fecha = new Date();
        AlumnoResource recurso = new AlumnoResource();
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        
        String erroresEntradas = "";
        boolean error = false;
        
        if(!isValidName(campoNombre.getText())){
            erroresEntradas += "Nombre incorrecto\n";
            error = true;
        }
            
        if(!isValidName(campoApellidos.getText())){
            error = true;
            erroresEntradas += "Apellidos incorrecto\n";
        }
            
        if(!isValidEmailAddress(campoCorreo.getText())){
            error = true;
            erroresEntradas += "Correo no valido\n";
        }
            
        if(!isValidPhoneNumber(campoEmergencia.getText())){
            error = true;
            erroresEntradas += "Numero de emergencia invalido\n";
        }
            
        if(!isValidPhoneNumber(campoNumero.getText())){
            error = true;
            erroresEntradas += "Numero de telefono invalido\n";     
        }
            
        if(
                campoNombre.getText().equals("") || 
                campoApellidos.getText().equals("") ||
                campoCorreo.getText().equals("") ||
                campoNumero.getText().equals("") ||
                campoSangre.getValue().toString().equals("") ||
                campoFechaNacimiento.getValue() == null ||
                campoEmergencia.getText().equals(""))
        {
            erroresEntradas += "Algunos campos se encuentan vacios";
            error = true;
        } 
        
        if(error){
            alerta.setContentText(erroresEntradas);
            alerta.showAndWait();
        }
        else {
            alumno.setActivo(true);
            alumno.setApellidos(campoApellidos.getText());
            alumno.setNombre(campoNombre.getText());
            alumno.setCorreo(campoCorreo.getText());
            alumno.setTelefono(campoNumero.getText());
            alumno.setTipoSangre(campoSangre.getValue().toString());
            alumno.setTelefonoEmergencia(campoEmergencia.getText());
            alumno.setFechaNacimiento(java.sql.Date.valueOf(campoFechaNacimiento.getValue()));
            alumno.setDiapago(fecha);
            
            String img = getImagenRealAlumno();
            if(img != null) alumno.setFoto(img);
            
            boolean resultado = false;
            try{
                resultado = recurso.registrarAlumno(alumno);
            } catch (Exception e){
                System.out.println("Error: Linea 162 = " + e.getMessage());
            }
            if(resultado){
                Alert mensajeGuardar = new Alert(Alert.AlertType.INFORMATION);
                mensajeGuardar.setTitle("Alumno guardado");
                mensajeGuardar.setContentText("Se ha registrado con exito el alumno");
                mensajeGuardar.showAndWait();
                this.controlPadre.setTabla();
                btnGuardar.getScene().getWindow().hide();
                
               
            } else {
                Alert mensajeGuardar = new Alert(Alert.AlertType.WARNING);
                mensajeGuardar.setTitle("Alumno no guardado");
                mensajeGuardar.setContentText("Vuelve a verificar tus datos");
                mensajeGuardar.showAndWait();
            }
        }
        
    }
    
    private String getImagenRealAlumno(){
        Path currentRelativePath = Paths.get("");
        String currentPath = currentRelativePath.toAbsolutePath().toString();

        DateFormat df = new SimpleDateFormat("MM-dd-yyyyHH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String imageUploadDate = df.format(today);
        String destinyPath = "";
        
        
        if(this.pathImgValidaTemporal != null){
            destinyPath = currentPath + "/USERSPICTURES/" + "user_pic" + imageUploadDate + this.pathImgValidaTemporal.substring(this.pathImgValidaTemporal.length()-4);
            try {
                Path source = Paths.get(this.pathImgValidaTemporal);
                Path dest = Paths.get(destinyPath);
                Files.copy(source, dest, REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(RegistrarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null; //porque no se ha seleccionado una imagen
        }
        return destinyPath;
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        campoSangre.setEditable(false);
        campoSangre.getItems().addAll("O-", "O+","A-","A+","B+","B-","AB+","AB-");
        if(alumno != null){
            this.campoEmergencia.setText(alumno.getTelefonoEmergencia());
            this.campoCorreo.setText(alumno.getCorreo());
            this.campoNombre.setText(alumno.getNombre());
            this.campoFechaNacimiento.setPromptText(alumno.getFechaNacimiento().toString());
            this.campoApellidos.setText(alumno.getApellidos());
            this.campoSangre.setPromptText(alumno.getTipoSangre());
            this.campoNumero.setText(alumno.getTelefono());
        }
    }    


    //funciones de verificacion
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
    public boolean isValidName(String name){
        return name.matches("[a-zA-Z]+") && name.length() <= 45;
    }
}

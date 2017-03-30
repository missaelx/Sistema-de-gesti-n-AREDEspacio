/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Alumno;
import recursos.AlumnoResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class EditarAlumnoController implements Initializable {

    @FXML
    private Button btnEditar, btnCancelar, btnExaminar, btnGuardar;
    @FXML
    private TextField campoNumeroEmergencia, campoNumero, campoCorreo, campoNombre, campoApellidos;
    @FXML 
    private DatePicker campoFechaNacimiento;
    @FXML
    private ComboBox campoSangre;
    @FXML
    private ImageView imgAlumno;
    
    private String pathImgValidaTemporal;
    private Alumno alumno;
    private AdministrarAlumnosController controlPadre;

    public AdministrarAlumnosController getControlPadre() {
        return controlPadre;
    }

    public void setControlPadre(AdministrarAlumnosController controlPadre) {
        this.controlPadre = controlPadre;
    }
    
    
    public void setAlumno(Alumno alumn){
        this.alumno = alumn;
    }
    
    
    @FXML
    private void editarDatos(ActionEvent event){
        this.campoNumeroEmergencia.setEditable(true);
        this.campoCorreo.setEditable(true);
        this.campoNombre.setEditable(true);
        this.campoFechaNacimiento.setDisable(false);
        this.campoApellidos.setEditable(true);
        this.campoSangre.setDisable(false);
        this.campoNumero.setEditable(true);
        this.btnExaminar.setDisable(false);
        this.btnGuardar.setVisible(true);
        this.btnEditar.setVisible(false);
        
    }
    @FXML
    public void guardarCambiosUsuario(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        
        //alerta.setContentText("Algunos campos se encuentan vacios, por favor ingresa la informacion completa");
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
            
        if(!isValidPhoneNumber(campoNumeroEmergencia.getText())){
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
                campoNumeroEmergencia.getText().equals(""))
        {
            erroresEntradas += "Algunos campos se encuentan vacios";
            error = true;
        } 
        
        if(error){
            alerta.setContentText(erroresEntradas);
            alerta.showAndWait();
        } 
       else {
            this.alumno.setNombre(this.campoNombre.getText());
            this.alumno.setApellidos(this.campoApellidos.getText());
            this.alumno.setCorreo(this.campoCorreo.getText());
            this.alumno.setFechaNacimiento(java.sql.Date.valueOf(campoFechaNacimiento.getValue()));
            this.alumno.setTelefono(this.campoNumero.getText());
            this.alumno.setTelefonoEmergencia(this.campoNumeroEmergencia.getText());
            this.alumno.setTipoSangre(this.campoSangre.getValue().toString());
            String img = getImagenRealAlumno();
            if(img != null) alumno.setFoto(img);

            AlumnoResource recurso = new AlumnoResource();
            boolean resultado = false;
            try{
                resultado = recurso.modificarAlumno(this.alumno);
            } catch (Exception e){
                System.out.println("Error: Linea 124 = " + e.getMessage());
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
    
    @FXML
    public void buscarFotoNueva(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions = 
            new FileChooser.ExtensionFilter(
              "Imagenes", "*.png", "*.jpg", "*.bmp");
        chooser.setTitle("Selecciona una imagen");
        chooser.getExtensionFilters().add(fileExtensions);
        File file = chooser.showOpenDialog(new Stage());
        
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
    public void setCampos(Alumno alumn){
        if(alumn != null){
            this.campoNumeroEmergencia.setText(alumn.getTelefonoEmergencia());
            this.campoCorreo.setText(alumn.getCorreo());
            this.campoNombre.setText(alumn.getNombre());
            this.campoFechaNacimiento.setValue(Instant.ofEpochMilli(alumn.getFechaNacimiento().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            this.campoApellidos.setText(alumn.getApellidos());
            this.campoSangre.setValue(alumn.getTipoSangre());
            this.campoNumero.setText(alumn.getTelefono());
            if(alumn.getFoto() != "" && alumn.getFoto() != null){
                cargarImagen(alumno.getFoto());
                this.pathImgValidaTemporal = alumn.getFoto();
            } else {
                Path currentRelativePath = Paths.get("");
                String currentPath = currentRelativePath.toAbsolutePath().toString();
                String dfu = currentPath+ "/USERSPICTURES/userDefault.png";
                
                File file = new File(dfu);
                this.pathImgValidaTemporal = dfu;
                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imgAlumno.setImage(image);
                } catch (MalformedURLException ex1) {
                    Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }else{
            System.out.println("alumno nulo");
        }
    }
    
    @FXML 
    private void cancelar(ActionEvent event){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        campoSangre.getItems().addAll("O-", "O+","A-","A+","B+","B-","AB+","AB-");
    }    
    
    
    public void cargarImagen(String path){
        Image image = null;
        File file;
        try {
            file = new File(path);
            image = new Image(file.toURI().toURL().toExternalForm());
            imgAlumno.setImage(image);
        } catch (MalformedURLException ex) {
            System.out.println("Ruta incorrecta");
        }
        
    }
    
    private String getImagenRealAlumno(){
        Path currentRelativePath = Paths.get("");
        String currentPath = currentRelativePath.toAbsolutePath().toString();

        DateFormat df = new SimpleDateFormat("MM-dd-yyyyHH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String imageUploadDate = df.format(today);
        
        String destinyPath = currentPath + "/USERSPICTURES/" + "user_pic" + imageUploadDate + this.pathImgValidaTemporal.substring(this.pathImgValidaTemporal.length()-4);
        
        if(this.pathImgValidaTemporal != null){
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
    
    //funciones de verificacion
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean isValidPhoneNumber(String phone) {
        String regexStr = "^[0-9]*$";
        return phone.matches(regexStr) && phone.length() <= 22;
    }
    public boolean isValidName(String name){
        return name.matches("[a-zA-Z]+");
    }
    
}

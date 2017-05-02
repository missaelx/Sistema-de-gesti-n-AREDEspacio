package miamifx.controllers;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modelo.Alumno;
import modelo.Promociones;
import recursos.AlumnoResource;
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
    
    private AdministrarAlumnosController controlPadre;
    
    private Alumno alumno;
    
    public void setAlumno(Alumno alumno){
        this.alumno = alumno;
    }
    
    public void setControlPadre(AdministrarAlumnosController control){
        this.controlPadre = control;
    }
    
    @FXML 
    private void pagarCuota(ActionEvent event) throws Exception{
        Date fecha = new Date();
        AlumnoResource recurso = new AlumnoResource();
        if(opcInscripcion.isSelected()){
            alumno.setFechaInscripcion(fecha);
            recurso.modificarAlumno(alumno);
        }else{
            if(opcMensualidad.isSelected()){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                calendar.add(Calendar.MONTH, 1);
                alumno.setDiapago(calendar.getTime());
                recurso.modificarAlumno(alumno);
            }
        }
        controlPadre.setTabla();
        btnPagar.getScene().getWindow().hide();
    }

    @FXML
    private void setCantidadPago(ActionEvent event){
        double pago=0;
        if(opcMensualidad.isSelected()){
            pago=500;
        }else{
            if(opcInscripcion.isSelected())
            pago=400;
        }
        if(promociones.getValue()!=null){
            Promociones promocion = (Promociones) promociones.getValue();
            double porcentajeDescuento = promocion.getPorcentajeDescuento();
            double descuento = porcentajeDescuento/100*pago;
            double total = pago - descuento;
            textCantidadPago.setText(String.valueOf(total));
        }

    }

    private void setCantidadPago(){
        double pago=500;
        if(opcInscripcion.isSelected())
            pago=400;
        textCantidadPago.setText(String.valueOf(pago));
    }
    @FXML
    private void cancelar(ActionEvent event){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Desea cancelar la operacion?");
        confirmacion.setTitle("Confirmacion");
        if (btnCancelar.getText().equals("Cancelar")) {
            if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
                btnCancelar.getScene().getWindow().hide();
            } else {
                confirmacion.close();
            }
        }
        btnCancelar.getScene().getWindow().hide();
    }
    
    @FXML
    private void activarPromociones(ActionEvent event){
        if(activarPromo.isSelected()){
            promociones.setDisable(false);
        }else{
            promociones.setDisable(true);
        }
    }
    
    public void setFechaPago() throws ParseException{
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = new Date();
        if(fechaActual.before(alumno.getDiapago())){
            textFechaPago.setText(formato.format(alumno.getDiapago()));
        }else{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(alumno.getDiapago());
            while(calendar.getTime().before(fechaActual)){
                calendar.add(Calendar.MONTH, 1);
            }
            textFechaPago.setText(formato.format(calendar.getTime()));
        }
        
    }
    
    private void setPromociones(){
        PromocionesResource recurso = new PromocionesResource();
        ObservableList lista = FXCollections.observableArrayList(recurso.getPromoMensualidad());
        promociones.setItems(lista);
        if(opcInscripcion.isSelected()){
            lista = FXCollections.observableArrayList(recurso.getPromoInscripcion());
            promociones.setItems(lista);
        }

    }
    private void setOpciones(){
        opciones=new ToggleGroup();
        opcInscripcion.setToggleGroup(opciones);
        opcMensualidad.setToggleGroup(opciones);
        opciones.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                setCantidadPago();
                setPromociones();
            }

        });

    }
    
    public void setInscripcionEnable(){
        if(alumno.getFechaInscripcion()!=null){
            opcInscripcion.setDisable(true);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPromociones();
        opcMensualidad.setSelected(true);
        setOpciones();
        setCantidadPago();        
        
    }
}


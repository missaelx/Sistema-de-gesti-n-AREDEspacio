package miamifx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import modelo.GrupoClase;
import modelo.Ingreso;
import modelo.Promociones;
import recursos.AlumnoResource;
import recursos.IngresosResource;
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
    private Label textFechaPago;
    @FXML
    private TextField campoCantidad;
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
        Alert Alerta = new Alert(Alert.AlertType.INFORMATION);
        Alerta.setTitle("Transaccion exitosa");
        BigDecimal pago = new BigDecimal(campoCantidad.getText());
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        Ingreso ingreso = new Ingreso();
        IngresosResource recursoIngreso = new IngresosResource();
        ingreso.setFecha(fecha);
        ingreso.setMonto(pago);
        if(promociones.getValue()!=null){
            ingreso.setIdPromocion((Promociones) promociones.getValue());
        }
        AlumnoResource recurso = new AlumnoResource();
        if(opcInscripcion.isSelected()){
            ingreso.setDescripcion("Inscripcion");
            calendar.add(Calendar.YEAR, 1);
            alumno.setFechaInscripcion(calendar.getTime());
            if(recurso.modificarAlumno(alumno) && recursoIngreso.registrarIngreso(ingreso)){
                Alerta.setContentText("La inscripcion se ha llevado a cabo exitosamente");
            }else{
                Alerta.setAlertType(Alert.AlertType.ERROR);
                Alerta.setContentText("Hubo un error, por favor intente de nuevo");
            }
        }else{
            if(opcMensualidad.isSelected()){
                ingreso.setDescripcion("mensualidad");
                calendar.add(Calendar.MONTH, 1);
                alumno.setDiapago(calendar.getTime());
                if(recurso.modificarAlumno(alumno) && recursoIngreso.registrarIngreso(ingreso)){
                    Alerta.setContentText("La mensualidad ha sido pagada exitosamente");
                }else{
                    Alerta.setAlertType(Alert.AlertType.ERROR);
                    Alerta.setContentText("Hubo un error, por favor intente de nuevo");
                }
            }
        }
        Alerta.show();
        controlPadre.setTabla();
        btnPagar.getScene().getWindow().hide();
    }

    private BigDecimal calcularPago(){
        BigDecimal mensualidad = new BigDecimal(0);
        List<GrupoClase> clases = alumno.getGrupoClaseList();
        for(GrupoClase clase: clases){
           mensualidad = mensualidad.add(clase.getCostoMensual());
        }
        return mensualidad;
    }
    
    @FXML
    private void setCantidadPago(ActionEvent event){
        double pago=0;
        if(opcMensualidad.isSelected()){
            pago= calcularPago().doubleValue();
        }else{
            if(opcInscripcion.isSelected())
            pago=400;
        }
        if(promociones.getValue()!=null){
            Promociones promocion = (Promociones) promociones.getValue();
            double porcentajeDescuento = promocion.getPorcentajeDescuento();
            double descuento = porcentajeDescuento/100*pago;
            double total = pago - descuento;
            campoCantidad.setText(String.valueOf(total));
        }

    }

    public void setCantidadPago(){
        double pago=calcularPago().doubleValue();
        if(opcInscripcion.isSelected())
            pago=400;
        campoCantidad.setText(String.valueOf(pago));
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
            setCantidadPago();
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
              
        
    }
}


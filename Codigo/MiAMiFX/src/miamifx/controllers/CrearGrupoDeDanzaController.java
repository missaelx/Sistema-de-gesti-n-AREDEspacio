package miamifx.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
<<<<<<< HEAD
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
=======
>>>>>>> 7df33928cf4a2e7bac079efeff58cd0135520004
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.GrupoClase;
import modelo.Horario;
import modelo.TipoDanza;
import recursos.DanzaResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class CrearGrupoDeDanzaController implements Initializable {
    
    @FXML
    private Button botonGuardar, botonCancelar;
    @FXML
    private JFXTimePicker entradaLunes,salidaLunes,entradaMartes,salidaMartes,entradaMiercoles,salidaMiercoles,entradaJueves,salidaJueves,
            entradaViernes,salidaViernes,entradaSabado,salidaSabado;
    @FXML
    private Label nombreDanza;
<<<<<<< HEAD
    @FXML
    private ComboBox<Maestro> listaCBmaestros;
    @FXML
    private TextField costoMensual;
    @FXML
    private Spinner<Float> porcentajeMaestro;

=======
    
>>>>>>> 7df33928cf4a2e7bac079efeff58cd0135520004
    private TipoDanza tipoDanza;
    private GrupoClase grupoDanza;
    private AdministrarDanzasController controlador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setTipoDazanza(TipoDanza tipoDanza){
        this.tipoDanza = tipoDanza;
        
    }
    public void setTituloNombreDanza(){
        nombreDanza.setText(tipoDanza.getNombre());
    }
    
    public void setControlador(AdministrarDanzasController controlador) {
        this.controlador = controlador;
    }
    
    @FXML
    private void cancelar(ActionEvent evento) {
        ((Node) (evento.getSource())).getScene().getWindow().hide();
    }
    @FXML
<<<<<<< HEAD
    public void horasClaseCompletas() {
        if (entradaLunes.getValue().equals(null) || salidaLunes.getValue().equals(null)) {
            botonGuardar.setDisable(true);
        }
        if (entradaMartes.getValue().equals(null) || salidaMartes.getValue().equals(null)) {
            botonGuardar.setDisable(true);
        }
        if (entradaMiercoles.getValue().equals(null) || salidaMiercoles.getValue().equals(null)) {
            botonGuardar.setDisable(true);
        }
        if (entradaJueves.getValue().equals(null) || salidaJueves.getValue().equals(null)) {
            botonGuardar.setDisable(true);
        }
        if (entradaViernes.getValue().equals(null) || salidaViernes.getValue().equals(null)) {
            botonGuardar.setDisable(true);
        }
        if (entradaSabado.getValue().equals(null) || salidaSabado.getValue().equals(null)) {
            botonGuardar.setDisable(true);
        }

        if (!entradaLunes.getValue().equals(null) && !salidaLunes.getValue().equals(null)) {
=======
    public void horaClaseComplta(){
        if(!entradaLunes.getValue().equals(null) && !salidaLunes.getValue().equals(null)){
>>>>>>> 7df33928cf4a2e7bac079efeff58cd0135520004
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaMartes.getValue().equals(null) && !salidaMartes.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaMiercoles.getValue().equals(null) && !salidaMiercoles.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaJueves.getValue().equals(null) && !salidaJueves.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaViernes.getValue().equals(null) && !salidaViernes.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
        if(!entradaSabado.getValue().equals(null) && !salidaSabado.getValue().equals(null)){
            botonGuardar.setDisable(false);
        }else{botonGuardar.setDisable(true);}
<<<<<<< HEAD
        
=======
    }
<<<<<<< HEAD
>>>>>>> cd53460477540e781677cc0247775ec9dc04af91

    public Horario setHorario(LocalTime entrada, LocalTime salida,String dia) {
        //entrada.atDate(LocalDate.now());
        Date provisionalEntrada,provisionalSalida;
        provisionalEntrada = new Date(1970,1,1,entrada.getHour(),entrada.getMinute());
        provisionalSalida = new Date(1970,1,1,salida.getHour(),salida.getMinute());
        Horario clase = new Horario();
        clase.setHorainicio(provisionalEntrada);
        clase.setHorafinal(provisionalSalida);
        clase.setDia(dia);
        return clase;

<<<<<<< HEAD
        //Horario clase = new Horario();
=======
>>>>>>> cd53460477540e781677cc0247775ec9dc04af91
    }
    private void setListaHorario(){
        if (!entradaLunes.getValue().equals(null) && !salidaLunes.getValue().equals(null)) {
            horario.add(setHorario(entradaLunes.getValue(), salidaLunes.getValue(), "lun"));
        }
        if (!entradaMartes.getValue().equals(null) && !salidaMartes.getValue().equals(null)) {
            horario.add(setHorario(entradaMartes.getValue(), salidaMartes.getValue(), "mar"));
        }
        if (!entradaMiercoles.getValue().equals(null) && !salidaMiercoles.getValue().equals(null)) {
            horario.add(setHorario(entradaMiercoles.getValue(), salidaMiercoles.getValue(), "mie"));
        }
        if (!entradaJueves.getValue().equals(null) && !salidaJueves.getValue().equals(null)) {
            horario.add(setHorario(entradaJueves.getValue(), salidaJueves.getValue(), "jue"));
        }
        if (!entradaViernes.getValue().equals(null) && !salidaViernes.getValue().equals(null)) {
            horario.add(setHorario(entradaViernes.getValue(), salidaViernes.getValue(), "vie"));
        }
        if (!entradaSabado.getValue().equals(null) && !salidaSabado.getValue().equals(null)) {
            horario.add(setHorario(entradaSabado.getValue(), salidaSabado.getValue(), "sab"));
        }
    }
    
    private BigDecimal setCosto(String costo){
        BigDecimal costoMensual = new BigDecimal(costo);
        return costoMensual;
    }
// falta el spiinner de porcentaje, como setear elvalor y como 
    @FXML
    private void guardar(ActionEvent evento) {
        DanzaResource recurso = new DanzaResource();
        
        grupoDanza = new GrupoClase();
        grupoDanza.setActivo(true);
        grupoDanza.setIdTipoDanza(tipoDanza);
        grupoDanza.setIdMaestro(listaCBmaestros.getSelectionModel().getSelectedItem());
        setListaHorario();
        grupoDanza.setHorarioList(horario);
        grupoDanza.setCostoMensual(setCosto(costoMensual.getText()));//bigdecimal
        grupoDanza.setPorcentajeGananciaMaestro(porcentajeMaestro.getValue());//float
        recurso.crearGrupoClase(grupoDanza);
        controlador.setTabla();
        botonGuardar.getScene().getWindow().hide();
=======
    
    @FXML
    private void guardar(ActionEvent evento) {
        try {
            DanzaResource recurso = new DanzaResource();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Algun dia no tiene hora de salida, por favor ingresa la informacion completa");
            
            if (entradaSabado.converterProperty().toString().isEmpty()) {
                alerta.show();
            } else {
                grupoDanza = new GrupoClase();
                grupoDanza.setActivo(true);
                grupoDanza.setIdTipoDanza(tipoDanza);
                //grupoDanza.setNombre(tfNombreDanza.getText());
                //grupoDanza.setDescripcion(tfDescripcion.getText());
                recurso.crearGrupoClase(grupoDanza);
                controlador.setTabla();
                botonGuardar.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.show();

        }
>>>>>>> 7df33928cf4a2e7bac079efeff58cd0135520004

    }
}

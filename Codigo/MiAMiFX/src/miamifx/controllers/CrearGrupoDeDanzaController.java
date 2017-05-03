package miamifx.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import modelo.GrupoClase;
import modelo.Horario;
import modelo.Maestro;
import modelo.TipoDanza;
import recursos.DanzaResource;
import recursos.MaestroResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class CrearGrupoDeDanzaController implements Initializable {

    @FXML
    private Button botonGuardar, botonCancelar;
    @FXML
    private JFXTimePicker entradaLunes, salidaLunes, entradaMartes, salidaMartes, entradaMiercoles, salidaMiercoles, entradaJueves, salidaJueves,
            entradaViernes, salidaViernes, entradaSabado, salidaSabado;
    @FXML
    private Label nombreDanza;

    @FXML
    private ComboBox<Maestro> listaCBmaestros;
    @FXML
    private TextField costoMensual;
    @FXML
    private Spinner<Integer> porcentajeMaestro;
    private TipoDanza tipoDanza;
    private GrupoClase grupoDanza;
    private AdministrarDanzasController controlador;
    private List<Horario> horario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCBoxMaestros();
        setSpinnerPorcentaje();
    }

    private void setCBoxMaestros() {
        MaestroResource maestroRecurso = new MaestroResource();
        ObservableList<Maestro> observableList = FXCollections.observableList(maestroRecurso.visualizarRegistros());
        listaCBmaestros.setItems(observableList);
        listaCBmaestros.getSelectionModel().selectFirst();
    }

    private void setSpinnerPorcentaje() {
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100,20,1);
        porcentajeMaestro = new Spinner();
        porcentajeMaestro.setValueFactory(svf);
        porcentajeMaestro.setEditable(true);
        
    }
    public boolean sonNumeros(String numeros){
        String formato = "^[0-9]*$";
        return numeros.matches(formato) && numeros.length() <= 12;
    }

    public void setTipoDazanza(TipoDanza tipoDanza) {
        this.tipoDanza = tipoDanza;

    }

    public void setTituloNombreDanza() {
        nombreDanza.setText(tipoDanza.getNombre());
    }

    public void setControlador(AdministrarDanzasController controlador) {
        this.controlador = controlador;
    }

    @FXML
    private void cancelar(ActionEvent evento) {
        ((Node) (evento.getSource())).getScene().getWindow().hide();
    }

    public void horaClaseComplta() {
        if (!entradaLunes.getValue().equals(null) && !salidaLunes.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        } else {
            botonGuardar.setDisable(true);
        }
        if (!entradaMartes.getValue().equals(null) && !salidaMartes.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        } else {
            botonGuardar.setDisable(true);
        }
        if (!entradaMiercoles.getValue().equals(null) && !salidaMiercoles.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        } else {
            botonGuardar.setDisable(true);
        }
        if (!entradaJueves.getValue().equals(null) && !salidaJueves.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        } else {
            botonGuardar.setDisable(true);
        }
        if (!entradaViernes.getValue().equals(null) && !salidaViernes.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        } else {
            botonGuardar.setDisable(true);
        }
        if (!entradaSabado.getValue().equals(null) && !salidaSabado.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        } else {
            botonGuardar.setDisable(true);
        }
    }

    public Horario setHorario(LocalTime entrada, LocalTime salida, String dia) {
        //entrada.atDate(LocalDate.now());
        Date provisionalEntrada, provisionalSalida;
        provisionalEntrada = new Date(1970, 1, 1, entrada.getHour(), entrada.getMinute());
        provisionalSalida = new Date(1970, 1, 1, salida.getHour(), salida.getMinute());
        Horario clase = new Horario();
        clase.setHorainicio(provisionalEntrada);
        clase.setHorafinal(provisionalSalida);
        clase.setDia(dia);
        return clase;

    }

    private void setListaHorario() {
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

    private BigDecimal setCosto(String costo) {
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
    }

}

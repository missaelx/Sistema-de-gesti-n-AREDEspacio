package miamifx.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalTime;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Spinner porcentajeMaestro;

    private TipoDanza tipoDanza;
    private GrupoClase grupoDanza;
    private List<Horario> horario;
    private AdministrarDanzasController controlador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMaestros();
        
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

    @FXML
    public void horaClaseCompleta() {
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
            botonGuardar.setDisable(false);
        }
        if (!entradaMartes.getValue().equals(null) && !salidaMartes.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        }
        if (!entradaMiercoles.getValue().equals(null) && !salidaMiercoles.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        }
        if (!entradaJueves.getValue().equals(null) && !salidaJueves.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        }
        if (!entradaViernes.getValue().equals(null) && !salidaViernes.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        }
        if (!entradaSabado.getValue().equals(null) && !salidaSabado.getValue().equals(null)) {
            botonGuardar.setDisable(false);
        }

    }

    @FXML
    private void setMaestros() {
        MaestroResource recurso = new MaestroResource();
        ObservableList<Maestro> nombresMaestros = FXCollections.observableArrayList(recurso.visualizarRegistros());
        listaCBmaestros.setItems(nombresMaestros);

    }

    private void setHorario(LocalTime entrada, LocalTime salida) {
        
        Horario clase = new Horario();
<<<<<<< HEAD
        //clase.setHorafinal(entrada);
=======
        clase.setHorafinal(entrada);
>>>>>>> 442846ac0ba7ed6c101dcbd1065aa37de4e9a9df
    }

    @FXML
    private void guardar(ActionEvent evento) {
        DanzaResource recurso = new DanzaResource();

        grupoDanza = new GrupoClase();
        grupoDanza.setActivo(true);
        grupoDanza.setIdTipoDanza(tipoDanza);
        //grupoDanza.setCostoMensual(costoMensual.gett);
        grupoDanza.setIdMaestro(listaCBmaestros.getSelectionModel().getSelectedItem());
        grupoDanza.setHorarioList(horario);
        //grupoDanza.setDescripcion(tfDescripcion.getText());
        recurso.crearGrupoClase(grupoDanza);
        controlador.setTabla();
        botonGuardar.getScene().getWindow().hide();

    }

}

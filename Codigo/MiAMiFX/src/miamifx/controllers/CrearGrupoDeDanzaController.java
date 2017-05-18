package miamifx.controllers;

import com.jfoenix.controls.JFXTimePicker;
import controladores.exceptions.NonexistentEntityException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.KeyEvent;
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
    private AdministrarDanzasController controlador;

    private GrupoClase clase;
    private boolean editar = false;

    /**
     * Initializes the controller class.
     */
    public void setEditar(boolean opc) {
        editar = opc;
    }

    public void setClase(GrupoClase clase) {
        this.clase = clase;
    }

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
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 20, 1);
        porcentajeMaestro.setValueFactory(svf);

    }

    public void setSpinnerClase(GrupoClase clase) {
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, (int) clase.getPorcentajeGananciaMaestro(), 1);
        porcentajeMaestro.setValueFactory(svf);
    }

    public boolean sonNumeros(String numeros) {
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

    public boolean verificarHorasFromUsuario() {
        //para ver si se verifican las entradas con las salidas
        boolean bandLunes = false, bandMartes = false, bandMiercoles = false, bandJueves = false, bandViernes = false, bandSabado = false;
        //verificamos existencias del par de horas, es decir si existen las dos o si ninguna existe
        if ((entradaLunes.getValue() != null && salidaLunes.getValue() != null)
                || (entradaLunes.getValue() == null && salidaLunes.getValue() == null)) {
            if (entradaLunes.getValue() != null) {
                bandLunes = true;
            }
        } else {
            return false;
        }

        if ((entradaMartes.getValue() != null && salidaMartes.getValue() != null)
                || (entradaMartes.getValue() == null && salidaMartes.getValue() == null)) {
            if (entradaMartes.getValue() != null) {
                bandMartes = true;
            }
        } else {
            return false;
        }

        if ((entradaMiercoles.getValue() != null && salidaMiercoles.getValue() != null)
                || (entradaMiercoles.getValue() == null && salidaMiercoles.getValue() == null)) {
            if (entradaMiercoles.getValue() != null) {
                bandMiercoles = true;
            }
        } else {
            return false;
        }

        if ((entradaJueves.getValue() != null && salidaJueves.getValue() != null)
                || (entradaJueves.getValue() == null && salidaJueves.getValue() == null)) {
            if (entradaJueves.getValue() != null) {
                bandJueves = true;
            }
        } else {
            return false;
        }

        if ((entradaViernes.getValue() != null && salidaViernes.getValue() != null)
                || (entradaViernes.getValue() == null && salidaViernes.getValue() == null)) {
            if (entradaViernes.getValue() != null) {
                bandViernes = true;
            }
        } else {
            return false;
        }

        if ((entradaSabado.getValue() != null && salidaSabado.getValue() != null)
                || (entradaSabado.getValue() == null && salidaSabado.getValue() == null)) {
            if (entradaSabado.getValue() != null) {
                bandSabado = true;
            }
        } else {
            return false;
        }

        //verificamos que la hora de entrada sea menor a la salida
        if (bandLunes) {
            if (entradaLunes.getValue().compareTo(salidaLunes.getValue()) >= 0) {
                return false;
            }
        }

        if (bandMartes) {
            if (entradaMartes.getValue().compareTo(salidaMartes.getValue()) >= 0) {
                return false;
            }
        }

        if (bandMiercoles) {
            if (entradaMiercoles.getValue().compareTo(salidaMiercoles.getValue()) >= 0) {
                return false;
            }
        }

        if (bandJueves) {
            if (entradaJueves.getValue().compareTo(salidaJueves.getValue()) >= 0) {
                return false;
            }
        }

        if (bandViernes) {
            if (entradaViernes.getValue().compareTo(salidaViernes.getValue()) >= 0) {
                return false;
            }
        }

        if (bandSabado) {
            if (entradaSabado.getValue().compareTo(salidaSabado.getValue()) >= 0) {
                return false;
            }
        }

        //si ninguna hora se ha seleccionado
        if (!bandLunes && !bandMartes && !bandMiercoles && !bandJueves && !bandViernes && !bandSabado) {
            return false;
        }
        return true;
    }

    public Horario LocalTimeToHorario(LocalTime entrada, LocalTime salida, String dia, GrupoClase Grupo) {
        //entrada.atDate(LocalDate.now());
        Date provisionalEntrada, provisionalSalida;
        provisionalEntrada = new Date(1970, 1, 1, entrada.getHour(), entrada.getMinute());
        provisionalSalida = new Date(1970, 1, 1, salida.getHour(), salida.getMinute());
        Horario clase = new Horario();
        clase.setHorainicio(provisionalEntrada);
        clase.setHorafinal(provisionalSalida);
        clase.setDia(dia);
        clase.setIdGrupoClase(Grupo);
        return clase;

    }

    private List<Horario> getListaHorario(GrupoClase clase) {
        List<Horario> horario = new ArrayList<>();
        if (entradaLunes.getValue() != null && salidaLunes.getValue() != null) {
            horario.add(LocalTimeToHorario(entradaLunes.getValue(), salidaLunes.getValue(), "lun", clase));
        }
        if (entradaMartes.getValue() != null && salidaMartes.getValue() != null) {
            horario.add(LocalTimeToHorario(entradaMartes.getValue(), salidaMartes.getValue(), "mar", clase));
        }
        if (entradaMiercoles.getValue() != null && salidaMiercoles.getValue() != null) {
            horario.add(LocalTimeToHorario(entradaMiercoles.getValue(), salidaMiercoles.getValue(), "mie", clase));
        }
        if (entradaJueves.getValue() != null && salidaJueves.getValue() != null) {
            horario.add(LocalTimeToHorario(entradaJueves.getValue(), salidaJueves.getValue(), "jue", clase));
        }
        if (entradaViernes.getValue() != null && salidaViernes.getValue() != null) {
            horario.add(LocalTimeToHorario(entradaViernes.getValue(), salidaViernes.getValue(), "vie", clase));
        }
        if (entradaSabado.getValue() != null && salidaSabado.getValue() != null) {
            horario.add(LocalTimeToHorario(entradaSabado.getValue(), salidaSabado.getValue(), "sab", clase));
        }

        return horario;
    }
// falta el spiinner de porcentaje, como setear elvalor y como 

    @FXML
    private void guardar(ActionEvent evento) {
        DanzaResource recurso = new DanzaResource();
        if (!verificarHorasFromUsuario()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en los horarios definidos");
            alert.setContentText("Posiblemente no has seleccionado un horario, o los horarios de entrada son mayores a los de salida");
            alert.setHeaderText("Horarios mal formulados");
            alert.show();
        } else {

            if (editar) {
                clase.setActivo(true);
                clase.setIdTipoDanza(tipoDanza);
                clase.setIdMaestro(listaCBmaestros.getSelectionModel().getSelectedItem());
                clase.setHorarioList(getListaHorario(clase));
                clase.setCostoMensual(new BigDecimal(costoMensual.getText()));//bigdecimal
                clase.setPorcentajeGananciaMaestro(porcentajeMaestro.getValue());//float
                try {
                    recurso.modificarGrupoClase(clase);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CrearGrupoDeDanzaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                controlador.setTabla();
                botonGuardar.getScene().getWindow().hide();
            } else {
                GrupoClase grupoDanza = new GrupoClase();
                grupoDanza.setActivo(true);
                grupoDanza.setIdTipoDanza(tipoDanza);
                grupoDanza.setIdMaestro(listaCBmaestros.getSelectionModel().getSelectedItem());
                grupoDanza.setHorarioList(getListaHorario(grupoDanza));
                grupoDanza.setCostoMensual(new BigDecimal(costoMensual.getText()));//bigdecimal
                grupoDanza.setPorcentajeGananciaMaestro(porcentajeMaestro.getValue());//float
                recurso.crearGrupoClase(grupoDanza);
                controlador.setTabla();
                botonGuardar.getScene().getWindow().hide();
            }
        }
    }

    public void iniciarModificacion() {
        List<Horario> horario = clase.getHorarioList();
        Horario lunes, martes, miercoles, jueves, viernes, sabado;
        lunes = getHorarioFromDia(horario, "lun");
        martes = getHorarioFromDia(horario, "mar");
        miercoles = getHorarioFromDia(horario, "mie");
        jueves = getHorarioFromDia(horario, "jue");
        viernes = getHorarioFromDia(horario, "vie");
        sabado = getHorarioFromDia(horario, "sab");

        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
        if(lunes!=null){
            Instant instant = Instant.ofEpochMilli(lunes.getHorainicio().getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            entradaLunes.setValue(res);
            
            instant = Instant.ofEpochMilli(lunes.getHorafinal().getTime());
            res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        salidaLunes.setValue(res);
        }
        if(martes!=null){
            Instant instant = Instant.ofEpochMilli(martes.getHorainicio().getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            entradaMartes.setValue(res);
            instant = Instant.ofEpochMilli(martes.getHorafinal().getTime());
            res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        salidaMartes.setValue(res);
        }
        if(miercoles!=null){
            Instant instant = Instant.ofEpochMilli(miercoles.getHorainicio().getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            entradaMiercoles.setValue(res);
            
            instant = Instant.ofEpochMilli(miercoles.getHorafinal().getTime());
            res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        salidaMiercoles.setValue(res);
        }
        if(jueves!=null){
            Instant instant = Instant.ofEpochMilli(jueves.getHorainicio().getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            entradaJueves.setValue(res);
            
            instant = Instant.ofEpochMilli(jueves.getHorafinal().getTime());
            res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        salidaJueves.setValue(res);
        }
        if(viernes!=null){
            Instant instant = Instant.ofEpochMilli(viernes.getHorainicio().getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            entradaViernes.setValue(res);
            instant = Instant.ofEpochMilli(viernes.getHorafinal().getTime());
            res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        salidaViernes.setValue(res);
        }
        if(sabado!=null){
            Instant instant = Instant.ofEpochMilli(sabado.getHorainicio().getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            entradaSabado.setValue(res);
            instant = Instant.ofEpochMilli(sabado.getHorafinal().getTime());
             res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        salidaSabado.setValue(res);
        }
        
        listaCBmaestros.setValue(clase.getIdMaestro());
        this.nombreDanza.setText(clase.getIdTipoDanza().getNombre());
        this.costoMensual.setText(clase.getCostoMensual().toString());

    }

    @FXML
    private void cancelar(ActionEvent evento) {
        ((Node) (evento.getSource())).getScene().getWindow().hide();
    }

    private Horario getHorarioFromDia(List<Horario> lista, String diaEnum) {
        for (Horario h : lista) {
            if (h.getDia().equals(diaEnum)) {
                return h;
            }
        }

        return null;
    }

    @FXML
    public void setTimeToNull(KeyEvent event) {
        JFXTimePicker pickerEditado = (JFXTimePicker) event.getSource();
        pickerEditado.setValue(null);
        System.out.println("setTimeToNull");
    }
}

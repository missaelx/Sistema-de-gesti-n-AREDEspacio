package miamifx.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.GrupoClase;
import modelo.Horario;
import modelo.TipoDanza;
import recursos.DanzaResource;

/**
 * FXML Controller class
 *
 * @author AndrésRoberto
 */
public class AdministrarDanzasController implements Initializable {

    @FXML
    private TabPane pestañas;
    @FXML
    private Button bNuevaDanza, bVerDetalles, bCrearGrupo, bEliminarDanza,bListaAlumnos, bEliminarGrupo;
    @FXML
    private TableColumn columnaDanza, columnaMaestros, columnaDescripcion, columnaLun, columnaMar
            , columnaMie, columnaJue, columnaVie, columnaSab;
    @FXML
    private TableView<TipoDanza> tablaDanzas;
    @FXML
    private TableView<GrupoClase> tablaGrupos;
    

    @FXML
    public void nuevaDanza(ActionEvent evento) throws MalformedURLException, IOException {

        try {
            Stage crearDanza = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearDanza.fxml"));

            //URL url = new File("src/miamifx/CrearDanza.fxml").toURL();            
            AnchorPane root = cargador.load();
            CrearDanzaController control = (CrearDanzaController) cargador.getController();
            control.setControlador(this);
            Scene escena = new Scene(root);
            crearDanza.setScene(escena);
            crearDanza.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verDetalles(ActionEvent evento) {
        try {
            Stage editarDanzas = new Stage();
            TipoDanza danza = tablaDanzas.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/EditarDanza.fxml"));            
            AnchorPane root = cargador.load();
            EditarDanzaController control = (EditarDanzaController) cargador.getController();
            control.setControlador(this);
            control.setTipoDanza(danza);
            control.setCampos(danza);
            Scene escena = new Scene(root);
            editarDanzas.setScene(escena);
            editarDanzas.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void setTabla() {
        DanzaResource recurso = new DanzaResource();
        ObservableList list = FXCollections.observableArrayList(recurso.visualizarRegistros());
        columnaDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaDanzas.refresh();
        tablaDanzas.setItems(list);

    }

    @FXML
    private void crearGrupo(ActionEvent evento) {
        try {
            Stage crearGrupoDanza = new Stage();
            TipoDanza tipoDanza = tablaDanzas.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearGrupoDeDanza.fxml"));

            //URL url = new File("src/miamifx/CrearDanza.fxml").toURL();            
            AnchorPane root = cargador.load();
            CrearGrupoDeDanzaController control = (CrearGrupoDeDanzaController) cargador.getController();
            control.setControlador(this);
            control.setTipoDazanza(tipoDanza);
            control.setTituloNombreDanza();
            Scene escena = new Scene(root);
            crearGrupoDanza.setScene(escena);
            crearGrupoDanza.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void VerListaAlumnos(ActionEvent evento){
        try {
            Stage verListaAlumnos = new Stage();
            GrupoClase grupoDanza = tablaGrupos.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/ListaAlumnos.fxml")); 
            AnchorPane root = cargador.load();
            ListaAlumnosController control = (ListaAlumnosController) cargador.getController();
            control.setControlador(this);
            control.setGrupoDanza(grupoDanza);
            Scene escena = new Scene(root);
            verListaAlumnos.setScene(escena);
            verListaAlumnos.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void eliminarDanza(ActionEvent evento) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea eliminar la seleccion?");
        confirmacion.setTitle("Confirmacion");
        if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
            try {
                TipoDanza tipoDanza = tablaDanzas.getSelectionModel().getSelectedItem();
                DanzaResource recurso = new DanzaResource();
                recurso.eliminarDanza(tipoDanza);
            } catch (Exception e) {
                Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, e);
            }
            setTabla();
        }

    }
    
    @FXML
    private void eliminarGrupo(ActionEvent evento){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea eliminar la seleccion?");
        confirmacion.setTitle("Confirmacion");
        if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
            try {
                GrupoClase grupoDanza = tablaGrupos.getSelectionModel().getSelectedItem();
                DanzaResource recurso = new DanzaResource();
                recurso.eliminarGrupoClase(grupoDanza);
            } catch (Exception e) {
                Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, e);
            }
            setTablaGrupoDanzas();
        }
    }

    @FXML
    private void seSelecciono() {
        if (tablaDanzas.getSelectionModel().getSelectedItem() != null) {
            bVerDetalles.setDisable(false);
            bEliminarDanza.setDisable(false);
            bCrearGrupo.setDisable(false);
        } else {
            bVerDetalles.setDisable(true);
            bEliminarDanza.setDisable(true);
            bCrearGrupo.setDisable(true);
        }
    }
    
    @FXML
    private void seSelecciono2(){
        if (tablaDanzas.getSelectionModel().getSelectedItem() != null) {
            
            bEliminarGrupo.setDisable(false);
            bListaAlumnos.setDisable(false);
        } else {
            
            bEliminarGrupo.setDisable(true);
            bListaAlumnos.setDisable(true);
        }
    }
    
    private void setTablaGrupoDanzas(){
        DanzaResource recurso = new DanzaResource();
        List<GrupoClase> llista = recurso.visualizarRegistrosGClase();
        
        ObservableList list = FXCollections.observableArrayList(llista);
        
        columnaMaestros.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(grupo.getValue().getIdMaestro() != null)
                        property.setValue(grupo.getValue().getIdMaestro().getNombre() + " " + grupo.getValue().getIdMaestro().getApellidos());
                    else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        columnaLun.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    Horario horario = getHorarioFromDia(grupo.getValue().getHorarioList(), "lun");
                    
                    if(horario != null){
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                    }else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        columnaMar.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    Horario horario = getHorarioFromDia(grupo.getValue().getHorarioList(), "mar");
                    
                    if(horario != null){
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                    }else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        columnaMie.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    Horario horario = getHorarioFromDia(grupo.getValue().getHorarioList(), "mie");
                    
                    if(horario != null){
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                    }else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        columnaJue.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    Horario horario = getHorarioFromDia(grupo.getValue().getHorarioList(), "jue");
                    
                    if(horario != null){
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                    }else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        columnaVie.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    Horario horario = getHorarioFromDia(grupo.getValue().getHorarioList(), "vie");
                    
                    if(horario != null){
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                    }else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        columnaSab.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    
                    Horario horario = getHorarioFromDia(grupo.getValue().getHorarioList(), "sab");
                    
                    if(horario != null){
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                    }else
                        property.setValue("-");
                    return property;
                }
            }
        );
        
        tablaGrupos.setItems(list);
    }

    /**
     * Initializes the controller class. "No funciona aun D: \n '\\\n" + "
     * _\\______\n" + " / \\========\n" + " ____|__________\\_____\n" + " /
     * ___________________ \\\n" + " \\/ _===============_ \\/\n" + "
     * -===============-"
     *
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setTablaGrupoDanzas();
        setTabla();
    }

    
    private Horario getHorarioFromDia(List<Horario> lista, String diaEnum){
        for(Horario h: lista){
            if(h.getDia().equals(diaEnum)) return h;
        }

        return null;
    }
}



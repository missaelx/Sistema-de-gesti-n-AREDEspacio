package miamifx.controllers;

import controladores.exceptions.NonexistentEntityException;
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
import javafx.scene.input.MouseEvent;
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
    private Button bNuevaDanza, bVerDetalles, bCrearGrupo, bEliminarDanza, bListaAlumnos, bEliminarGrupo;
    @FXML
    private TableColumn columnaDanza, columnaMaestros, columnaDescripcion, columnaLun, columnaMar, columnaMie, columnaJue, columnaVie, columnaSab;
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
        Alert aviso = new Alert(Alert.AlertType.INFORMATION);
        try {
            Stage editarDanzas = new Stage();
            TipoDanza danza = null;
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/EditarDanza.fxml"));
            AnchorPane root = cargador.load();
            EditarDanzaController control = (EditarDanzaController) cargador.getController();
            control.setControlador(this);
            if (tablaDanzas.getSelectionModel().getSelectedItem() != null) {
                danza = tablaDanzas.getSelectionModel().getSelectedItem();
                control.setTipoDanza(danza);
            } else {
                aviso.setTitle("Upss");
                aviso.setContentText("Por favor, elige una danza para editar");
                aviso.setHeaderText("No se selecciono ninguna danza");
                aviso.show();
                return;
            }

            control.setCampos(danza);
            Scene escena = new Scene(root);
            editarDanzas.setScene(escena);
            editarDanzas.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void crearGrupo(ActionEvent evento) {
        try {
            Stage crearGrupoDanza = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearGrupoDeDanza.fxml"));

            //URL url = new File("src/miamifx/CrearDanza.fxml").toURL();            
            AnchorPane root = cargador.load();
            CrearGrupoDeDanzaController control = (CrearGrupoDeDanzaController) cargador.getController();
            control.setControlador(this);
            if(tablaDanzas.getSelectionModel().getSelectedItem()!=null){
                TipoDanza tipoDanza = tablaDanzas.getSelectionModel().getSelectedItem();
                control.setTipoDazanza(tipoDanza);
                
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("No se eligió ninguna danza");
                alerta.setTitle("Error");
                alerta.setContentText("Por favor, selecciona el tipo de danza al que pertenecerá el grupo");
                alerta.show();
                return;
            }
            control.setTituloNombreDanza();
            Scene escena = new Scene(root);
            crearGrupoDanza.setScene(escena);
            crearGrupoDanza.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void editarGrupo(ActionEvent event) {
        System.out.println("Hola mundo");
    }

    @FXML
    private void verListaAlumnos(ActionEvent evento) {
        if(tablaGrupos.getSelectionModel().getSelectedItem()!=null){
        try {
            Stage verListaAlumnos = new Stage();
            GrupoClase grupoDanza = tablaGrupos.getSelectionModel().getSelectedItem();

            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/VerListaAlumnoGrupo.fxml"));
            AnchorPane root = cargador.load();
            VerListaAlumnoGrupoController control = (VerListaAlumnoGrupoController) cargador.getController();

            control.setControladorPadre(this);
            control.setGrupoSeleccionado(grupoDanza);

            Scene escena = new Scene(root);
            verListaAlumnos.setScene(escena);
            verListaAlumnos.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, selecciona el grupo del que deseas ver la lista de alumnos.");
            alerta.setHeaderText("No se selecciono ningún grupo");
            alerta.show();
        }
    }

    @FXML
    private void eliminarDanza(ActionEvent evento) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea eliminar la seleccion?");
        confirmacion.setTitle("Confirmacion");
        if (tablaDanzas.getSelectionModel().getSelectedItem() != null) {
            TipoDanza tipoDanza = tablaDanzas.getSelectionModel().getSelectedItem();
            DanzaResource recurso = new DanzaResource();
            if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
                try {
                    recurso.eliminarDanza(tipoDanza);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(AdministrarDanzasController.class.getName()).log(Level.SEVERE, null, ex);
                }
                setTabla();
            }

        } else {
            confirmacion.setAlertType(Alert.AlertType.INFORMATION);
            confirmacion.setContentText("Por favor, elige la danza que quieres eliminar");
            confirmacion.setHeaderText("No se ha elegido ninguna danza");
            confirmacion.setTitle("Upss");
            confirmacion.show();
            return;
        }

    }

    @FXML
    private void eliminarGrupo(ActionEvent evento) {
        if (tablaGrupos.getSelectionModel().getSelectedItem() == null) {
            Alert confirmacion = new Alert(Alert.AlertType.WARNING);
            confirmacion.setContentText("No se ha seleccionado un grupo que eliminar");
            confirmacion.setTitle("Grupo no seleccionado");
            confirmacion.setHeaderText("Selecciona un grupo");
            confirmacion.show();
            return;
        }
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

            setTablaGrupoDanzas(tablaDanzas.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void onTableDanzaSelect(MouseEvent event) {
        setTablaGrupoDanzas(tablaDanzas.getSelectionModel().getSelectedItem());
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

    private void setTablaGrupoDanzas(TipoDanza tipoDanza) {
        if (tipoDanza == null) {
            return;
        }
        DanzaResource recurso = new DanzaResource();
        List<GrupoClase> llista = recurso.visualizarRegistrosGClase(tipoDanza);

        ObservableList list = FXCollections.observableArrayList(llista);

        columnaMaestros.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<GrupoClase, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoClase, String> grupo) {
                SimpleStringProperty property = new SimpleStringProperty();
                if (grupo.getValue().getIdMaestro() != null) {
                    property.setValue(grupo.getValue().getIdMaestro().getNombre() + " " + grupo.getValue().getIdMaestro().getApellidos());
                } else {
                    property.setValue("-");
                }
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

                if (horario != null) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                } else {
                    property.setValue("-");
                }
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

                if (horario != null) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                } else {
                    property.setValue("-");
                }
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

                if (horario != null) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                } else {
                    property.setValue("-");
                }
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

                if (horario != null) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                } else {
                    property.setValue("-");
                }
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

                if (horario != null) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                } else {
                    property.setValue("-");
                }
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

                if (horario != null) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    property.setValue(dateFormat.format(horario.getHorainicio()) + " - " + dateFormat.format(horario.getHorafinal()));
                } else {
                    property.setValue("-");
                }
                return property;
            }
        }
        );

        tablaGrupos.refresh();
        tablaGrupos.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setTablaGrupoDanzas(tablaDanzas.getSelectionModel().getSelectedItem());
        setTabla();
    }

    private Horario getHorarioFromDia(List<Horario> lista, String diaEnum) {
        for (Horario h : lista) {
            if (h.getDia().equals(diaEnum)) {
                return h;
            }
        }

        return null;
    }
}

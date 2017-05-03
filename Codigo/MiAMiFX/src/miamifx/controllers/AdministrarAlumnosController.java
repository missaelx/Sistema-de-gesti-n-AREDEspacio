package miamifx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import recursos.AlumnoResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class AdministrarAlumnosController implements Initializable {

    private Button btnEliminar;

    @FXML
    private Button btnPagar, btnDetalles, btnBuscar;
    @FXML
    private ComboBox comboBusqueda;
    @FXML
    private TextField campoBusqueda;
    @FXML
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn columnaNombre;
    @FXML
    private TableColumn columnoApellidos, columnaCorreo, columnaTelefono;
    private ImageView fotoAlumno;
    @FXML
    private TableColumn<?, ?> columnaAsistencia;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;

    @FXML
    private void registrarAlumno(ActionEvent event) {
        try {
            Stage registrarAlumno = new Stage();

            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/RegistrarAlumno.fxml"));
            AnchorPane root = cargador.load();
            RegistrarAlumnoController control = (RegistrarAlumnoController) cargador.getController();
            control.setControlPadre(this);
            Scene escena = new Scene(root);
            registrarAlumno.setScene(escena);
            registrarAlumno.initModality(Modality.WINDOW_MODAL);
            registrarAlumno.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            registrarAlumno.setResizable(false);
            registrarAlumno.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verDetalles(ActionEvent event) {
        try {
            Stage editarAlumno = new Stage();
            Alumno alumno = (Alumno) tablaAlumnos.getSelectionModel().getSelectedItem();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/EditarAlumno.fxml"));

            AnchorPane root = cargador.load();

            EditarAlumnoController editarAlumnoController = (EditarAlumnoController) cargador.getController();

            editarAlumnoController.setAlumno(alumno);
            cargador.setController(editarAlumnoController);
            editarAlumnoController.setCampos(alumno);
            editarAlumnoController.setControlPadre(this);

            Scene escena = new Scene(root);
            editarAlumno.setScene(escena);
            editarAlumno.initModality(Modality.WINDOW_MODAL);
            editarAlumno.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            editarAlumno.setResizable(false);
            editarAlumno.show();
        } catch (IOException ex) {
            Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void buscarAlumno(ActionEvent event) {
        AlumnoResource recurso = new AlumnoResource();
        //ObservableList lista = FXCollections.observableArrayList();

        List<Alumno> listaBusqueda = new ArrayList<>();

        if (comboBusqueda.getValue().toString().equals("Nombre")) {
            listaBusqueda = recurso.buscarAlumnoPorNombre(campoBusqueda.getText());
        } else if (comboBusqueda.getValue().toString().equals("Correo")) {
            listaBusqueda = recurso.buscarAlumnoPorCorreo(campoBusqueda.getText());
        }

        ObservableList lista = FXCollections.observableList(listaBusqueda);
        tablaAlumnos.setItems(lista);

    }

    private void activarBotones() {
        this.btnDetalles.setDisable(false);
        this.btnEliminar.setDisable(false);
        this.btnPagar.setDisable(false);
    }

    @FXML
    private void activarBusqueda(ActionEvent event) {
        btnBuscar.setDisable(false);
        campoBusqueda.setDisable(false);
    }


    @FXML
    private void eliminarRegistro(ActionEvent evento) {
        Alumno alumno = tablaAlumnos.getSelectionModel().getSelectedItem();
        AlumnoResource recurso = new AlumnoResource();
        try {
            recurso.eliminarAlumno(alumno);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTabla();
    }

    public void setTabla() {
        tablaAlumnos.refresh();
        AlumnoResource recurso = new AlumnoResource();
        ObservableList lista = FXCollections.observableArrayList(recurso.visualizarRegistros());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnoApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tablaAlumnos.setItems(lista);

    }

    private void setTabla(ObservableList lista) {
        tablaAlumnos.setItems(lista);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        campoBusqueda.setDisable(true);
        btnBuscar.setDisable(true);
        btnPagar.setDisable(true);
        comboBusqueda.getItems().addAll("Nombre", "Correo");
        setTabla();

        tablaAlumnos.setOnMouseClicked((MouseEvent event) -> {
            onTableSelection();
        });

        tablaAlumnos.setOnKeyReleased((KeyEvent event) -> {
            onTableSelection();
        });
    }

    public void onTableSelection() {
        activarBotones();
        Alumno alumnoSeleccionado = (Alumno) tablaAlumnos.getSelectionModel().getSelectedItem();
        if (!"".equals(alumnoSeleccionado.getFoto()) && alumnoSeleccionado.getFoto() != null) {
            Image image = null;
            File file;
            try {
                file = new File(alumnoSeleccionado.getFoto());
                image = new Image(file.toURI().toURL().toExternalForm());
                fotoAlumno.setImage(image);
            } catch (MalformedURLException ex) {
                System.out.println("Ruta incorrecta");
            }
        } else {
            Path currentRelativePath = Paths.get("");
            String currentPath = currentRelativePath.toAbsolutePath().toString();
            String dfu = currentPath + "/USERSPICTURES/userDefault.png";
            File file = new File(dfu);
            try {
                Image image = new Image(file.toURI().toURL().toExternalForm());
                fotoAlumno.setImage(image);
            } catch (MalformedURLException ex1) {
                Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        if(alumnoSeleccionado.getFechaInscripcion()!=null){
            btnPagar.setText("Pagar Mensualidad");
        }else{
            btnPagar.setText("Inscribir");
        }
    }

    public void pagarCuota(ActionEvent actionEvent) throws IOException, ParseException {
        Stage pagarCuota = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/PagarCuota.fxml"));
        AnchorPane root = cargador.load();
        pagarCuotaController control = (pagarCuotaController) cargador.getController();
        cargador.setController(control);
        control.setControlPadre(this);
        control.setAlumno((Alumno) tablaAlumnos.getSelectionModel().getSelectedItem());
        control.setInscripcionEnable();
        control.setFechaPago();
        control.setCantidadPago();
        Scene escena = new Scene(root);
        pagarCuota.setScene(escena);
        pagarCuota.show();
    }
}

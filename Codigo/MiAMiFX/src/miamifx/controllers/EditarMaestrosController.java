/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import controladores.exceptions.NonexistentEntityException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Maestro;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modelo.Pagodesalario;
import recursos.MaestroResource;

/**
 * FXML Controller class
 *
 * @author Miguel Acosta
 */
public class EditarMaestrosController implements Initializable {

    private Maestro maestro;
    private AdministrarMaestrosController administrar;

    @FXML
    private Button btnEditar, btnCancelar, btnPagos, btnGuardar;

    @FXML
    private TextField campoNombre, campoApellido, campoTelefono, campoCorreo;

    public void setMaestro(Maestro maestr) {
        this.maestro = maestr;
    }

    public void setAdministrar(AdministrarMaestrosController control) {
        this.administrar = control;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches() && email.length() <= 254;
    }

    public boolean isValidPhoneNumber(String phone) {
        String regexStr = "^[0-9]*$";
        return phone.matches(regexStr) && phone.length() <= 22;
    }

    public boolean isValidName(String name) {
        return name.matches("([a-z]|[A-Z]|\\s)+") && name.length() <= 45;
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Desea cancelar la edicion?");
        confirmacion.setTitle("Confirmacion");
        if (btnCancelar.getText().equals("Cancelar")) {
            if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
                administrar.setVentana(false);
                btnCancelar.getScene().getWindow().hide();
            } else {
                confirmacion.close();
            }
        }
        administrar.setVentana(false);
        btnCancelar.getScene().getWindow().hide();
    }

    @FXML
    private void editarInformacion() {
        btnEditar.setVisible(false);
        btnGuardar.setVisible(true);
        btnCancelar.setText("Cancelar");
        this.campoNombre.setEditable(true);
        this.campoApellido.setEditable(true);
        this.campoTelefono.setEditable(true);
        this.campoCorreo.setEditable(true);
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
        MaestroResource recurso = new MaestroResource();
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        if (isValidName(campoNombre.getText())) {
            maestro.setNombre(this.campoNombre.getText());
            if (isValidName(campoApellido.getText())) {
                maestro.setApellidos(this.campoApellido.getText());
                if (isValidPhoneNumber(campoTelefono.getText())) {
                    maestro.setTelefono(this.campoTelefono.getText());
                    if (isValidEmailAddress(campoCorreo.getText())) {
                        maestro.setCorreo(this.campoCorreo.getText());
                        try {
                            if (recurso.modificarMaestro(maestro)) {
                                alerta.setTitle("Transaccion exitosa");
                                alerta.setContentText("Los datos se modificaron exitosamente.");
                                alerta.showAndWait();
                                administrar.setTabla();
                                administrar.setVentana(false);
                                btnGuardar.getScene().getWindow().hide();
                            }
                        } catch (NonexistentEntityException ex) {
                            Logger.getLogger(EditarMaestrosController.class.getName()).log(Level.SEVERE, null, ex);
                            alerta.setTitle("Transaccion nula");
                            alerta.setContentText("La transaccion no se pudo llevar a cabo. Porfavor intenta de nuevo. Si el problema persiste acude al administrador");
                            alerta.show();
                        }
                    }else{
                        mostrarAlerta("Formato incorrecto de correo electronico, por favor ingresa un correo electronico valido");
                    }
                } else {
                    mostrarAlerta("Numero de telefono invalido. Por favor ingresa un numero telefonico que incluya solo numeros (0-9)");
                }

            } else {

                mostrarAlerta("Apellido invalido, por favor ingresa un nombre que solo incluya letras (a-z)");
            }

        } else {
            mostrarAlerta("Nombre invalido, por favor ingresa un nombre que solo incluya letras (a-z)");
        }

    }

    private void mostrarAlerta(String alerta) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion invalida");
        alert.setContentText(alerta);
        alert.showAndWait();
    }

    @FXML
    private void visualizarPagos(ActionEvent event) {
        Stage pagos = new Stage();
        pagos.setResizable(false);
        
        MaestroResource recurso = new MaestroResource();
        GridPane root = new GridPane();
        TableView tablaPagos = new TableView();
        TableColumn monto = new TableColumn("Monto");
        TableColumn fecha = new TableColumn("Fecha");
        TableColumn descripcion = new TableColumn("Descripcion");
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setMinWidth(100);

        btnCerrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnCerrar.getScene().getWindow().hide();
            }
        });
        ObservableList lista = FXCollections.observableArrayList(recurso.getPagosDeSalario(maestro.getId()));

        monto.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Pagodesalario, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Pagodesalario, String> film) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(film.getValue().getMonto() != null)
                        property.setValue("$" + film.getValue().getMonto());
                    else
                        property.setValue("N/A");
                    return property;
                }
            }
        );
        fecha.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Pagodesalario, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Pagodesalario, String> film) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    if(film.getValue().getFecha() != null)
                        property.setValue(dateFormat.format(film.getValue().getFecha()));
                    else
                        property.setValue("N/A");
                    return property;
                }
        });
        descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaPagos.getColumns().addAll(monto, fecha, descripcion);
        tablaPagos.setItems(lista);
        btnCerrar.setPadding(new Insets(5));
        monto.setMinWidth(100);
        fecha.setMinWidth(200);
        descripcion.setMinWidth(400);
        root.setRowIndex(btnCerrar, 1);
        tablaPagos.setMinWidth(700);
        root.getChildren().addAll(tablaPagos, btnCerrar);
        Scene escena = new Scene(root);
        pagos.setScene(escena);
        pagos.initModality(Modality.WINDOW_MODAL);
        pagos.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        pagos.show();

    }

    public void setCampos() {
        this.campoNombre.setText(maestro.getNombre());
        this.campoApellido.setText(maestro.getApellidos());
        this.campoTelefono.setText(maestro.getTelefono());
        this.campoCorreo.setText(maestro.getCorreo());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnGuardar.setVisible(false);

    }

}

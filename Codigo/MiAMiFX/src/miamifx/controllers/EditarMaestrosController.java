/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import controladores.exceptions.NonexistentEntityException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;
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
    
    public void setMaestro(Maestro maestr){
        this.maestro = maestr;
    }
    
    public void setAdministrar(AdministrarMaestrosController control){
        this.administrar = control;
    }
    
    @FXML 
    private void cerrar(ActionEvent event){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Desea cancelar la edicion?");
        confirmacion.setTitle("Confirmacion");
        if(btnCancelar.getText().equals("Cancelar")){
            if(confirmacion.showAndWait().get().equals(ButtonType.OK)){
                administrar.setVentana(false);
                btnCancelar.getScene().getWindow().hide();
            }else{
                
            }
        }        
        administrar.setVentana(false);
        btnCancelar.getScene().getWindow().hide();
    }
    
    @FXML
    private void editarInformacion(){
        btnEditar.setVisible(false);
        btnGuardar.setVisible(true);
        btnCancelar.setText("Cancelar");
        btnCancelar.setCancelButton(true);
        this.campoNombre.setEditable(true);
        this.campoApellido.setEditable(true);
        this.campoTelefono.setEditable(true);
        this.campoCorreo.setEditable(true);
    }
    
    @FXML
    private void guardarCambios(ActionEvent event){
        MaestroResource recurso = new MaestroResource();
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        
        if(!this.maestro.getNombre().equals(this.campoNombre.getText())){
            maestro.setNombre(this.campoNombre.getText());
        }
        if(!this.maestro.getApellidos().equals(this.campoApellido.getText())){
            maestro.setApellidos(this.campoApellido.getText());
        }
        if(!this.maestro.getTelefono().equals(this.campoTelefono.getText())){
            maestro.setTelefono(this.campoTelefono.getText());
        }
        if(!this.maestro.getCorreo().equals(this.campoCorreo.getText())){
            maestro.setCorreo(this.campoCorreo.getText());
        }
        try {
            if(recurso.modificarMaestro(maestro)){
                alerta.setTitle("Transaccion exitosa");
                alerta.setContentText("Los datos se modificaron exitosamente.");
                alerta.show();
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditarMaestrosController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.setTitle("Transaccion nula");
            alerta.setContentText("La transaccion no se pudo llevar a cabo. Porfavor intenta de nuevo. Si el problema persiste acude al administrador");
            alerta.show();
        }
        administrar.setTabla();
        administrar.setVentana(false);
        btnGuardar.getScene().getWindow().hide();
    }
    
    @FXML
    private void visualizarPagos(ActionEvent event){
        Stage pagos = new Stage();
        MaestroResource recurso = new MaestroResource();
        GridPane root = new GridPane();
        TableView tablaPagos = new TableView();
        TableColumn monto = new TableColumn("Monto");
        TableColumn fecha = new TableColumn("Fecha");
        TableColumn descripcion = new TableColumn("Descripcion");
        Button btnCerrar = new Button("Cerrar");
        
        btnCerrar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                btnCerrar.getScene().getWindow().hide();
            }
        });
        ObservableList lista = FXCollections.observableArrayList(recurso.getPagosDeSalario(maestro.getId()));
        
        monto.setCellValueFactory( new PropertyValueFactory<>("monto"));
        fecha.setCellValueFactory( new PropertyValueFactory<>("fecha"));
        descripcion.setCellValueFactory( new PropertyValueFactory<>("descripcion"));
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
        pagos.show();
        
        
    }
    public void setCampos(){
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

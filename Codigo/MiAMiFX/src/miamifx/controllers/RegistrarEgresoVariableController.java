/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miamifx.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Egreso;
import modelo.Gastovariable;
import modelo.Maestro;
import modelo.Pagodesalario;
import recursos.EgresosResource;
import recursos.MaestroResource;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class RegistrarEgresoVariableController implements Initializable {

    @FXML
    private DatePicker datePickerFechaPago;
    @FXML
    private TextField txtMonto;
    @FXML
    private TextArea txtDescripcion;
    
    private AdministrarEgresosController controladorPadre; //para poder refrescar la tabla

    public AdministrarEgresosController getControladorPadre() {
        return controladorPadre;
    }

    public void setControladorPadre(AdministrarEgresosController controladorPadre) {
        this.controladorPadre = controladorPadre;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePickerFechaPago.setValue(LocalDate.now());
    }
    
    @FXML
    public void OnRegistrarPago(ActionEvent event){
        EgresosResource recurso = new EgresosResource();
        Gastovariable pago = new Gastovariable();
        Egreso monto = new Egreso();
        
        Date fechaSeleccionadaUsuario = Date.from(datePickerFechaPago.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        monto.setFecha(fechaSeleccionadaUsuario);
        monto.setMonto(new BigDecimal(txtMonto.getText()));
        monto.setDescripcion(txtDescripcion.getText());
        
        pago.setIdEgreso(monto);
        
        
        if(!recurso.registrarEgreso(pago)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al registrar pago");
            alert.setHeaderText("Hubo un error al contactar con la base de datos");
            alert.setContentText("Error");
            alert.showAndWait();
        } else {
            controladorPadre.actualizarTablaEgresos();
            Stage e = (Stage) (((Node)event.getSource()).getScene().getWindow());
            e.close();
        }
    }
}

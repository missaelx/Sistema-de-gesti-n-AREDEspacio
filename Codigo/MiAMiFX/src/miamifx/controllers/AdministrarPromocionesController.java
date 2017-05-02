package miamifx.controllers;

import controladores.PromocionesJpaController;
import controladores.exceptions.NonexistentEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Maestro;
import modelo.Promociones;
import recursos.MaestroResource;
import recursos.PromocionesResource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import modelo.Gastovariable;

/**
 * @author Created by Miguel Acosta on 04/04/2017.
 */
public class AdministrarPromocionesController implements Initializable {

    @FXML
    private TableColumn columnaDescuento, columnaTitulo, columnaAplica;
    @FXML
    private Button btnCrear, btnEliminar;
    @FXML
    private TableView<Promociones> tablaPromociones;

    public void setTabla(){
        tablaPromociones.refresh();
        PromocionesResource recurso = new PromocionesResource();
        ObservableList lista = FXCollections.observableArrayList(recurso.getActivos());
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        //por miguel
        //columnaDescuento.setCellValueFactory(new PropertyValueFactory<>("porcentajeDescuento"));
        columnaDescuento.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Promociones, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Promociones, String> promocion) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(promocion.getValue().getPorcentajeDescuento() + "%");
                    return property;
                }
            }
        );
        
        
        columnaAplica.setCellValueFactory(new PropertyValueFactory<>("aplicaPara"));
        tablaPromociones.setItems(lista);
    }

    @FXML
    private void crearPromocion(ActionEvent actionEvent){
        try {
            Stage promocion = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearPromocion.fxml"));
            AnchorPane root = cargador.load();
            CrearPromocionController control = (CrearPromocionController) cargador.getController();
            cargador.setController(control);
            control.setControlPadre(this);
            Scene escena = new Scene(root);
            promocion.setScene(escena);
            promocion.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarPromocion(ActionEvent actionEvent){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea eliminar la seleccion?");
        confirmacion.setTitle("Confirmacion");

        if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
            Promociones promociones = tablaPromociones.getSelectionModel().getSelectedItem();
            PromocionesResource recurso = new PromocionesResource();
            try {
                recurso.eliminarPromocion(promociones);
            } catch (Exception ex) {
                Logger.getLogger(AdministrarPromocionesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            setTabla();
        }else{
            confirmacion.close();
        }
        setTabla();
    }


    @Override
        public void initialize(URL location, ResourceBundle resources) {
        btnEliminar.setDisable(true);
        tablaPromociones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btnEliminar.setDisable(false);
            }
        });
        setTabla();
        }
}

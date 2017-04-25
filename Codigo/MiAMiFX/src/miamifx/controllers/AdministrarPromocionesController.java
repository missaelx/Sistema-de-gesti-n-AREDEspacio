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

    private void setTabla(){
        tablaPromociones.refresh();
        PromocionesResource recurso = new PromocionesResource();
        ObservableList lista = FXCollections.observableArrayList(recurso.getAll());
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaDescuento.setCellValueFactory(new PropertyValueFactory<>("porcentaje_descuento"));
        columnaAplica.setCellValueFactory(new PropertyValueFactory<>("aplica_para"));
    }

    @FXML
    private void crearPromocion(ActionEvent actionEvent){
        try {
            Stage promocion = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("miamifx/interfaces/CrearPomocion.fxml"));
            AnchorPane root = cargador.load();
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
            recurso.eliminarPromocion(promociones);
            setTabla();
        }else{
            confirmacion.close();
        }
    }


    @Override
        public void initialize(URL location, ResourceBundle resources) {
            setTabla();
        }
}

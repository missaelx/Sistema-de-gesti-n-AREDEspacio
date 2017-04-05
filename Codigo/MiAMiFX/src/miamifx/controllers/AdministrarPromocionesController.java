package miamifx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import modelo.Promociones;

import javax.swing.text.TableView;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Miguel Acosta on 04/04/2017.
 */
public class AdministrarPromocionesController implements Initializable {

    @FXML
    private TableColumn columnaDescuento, columnaDescripcion, columnaAplica;
    @FXML
    private Button btnCrear, btnEliminar;
    @FXML
    private javafx.scene.control.TableView<Promociones> tablaPromociones;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

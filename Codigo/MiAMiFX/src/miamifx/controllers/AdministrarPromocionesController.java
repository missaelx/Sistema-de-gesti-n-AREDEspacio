package miamifx.controllers;

import controladores.PromocionesJpaController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import modelo.Promociones;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Created by Miguel Acosta on 04/04/2017.
 */
public class AdministrarPromocionesController implements Initializable {

    @FXML
    private TableColumn columnaDescuento, columnaDescripcion, columnaAplica;
    @FXML
    private Button btnCrear, btnEliminar;
    @FXML
    private TableView<Promociones> tablaPromociones;

    private void setTabla(){
        //PromocionesJpaController recurso = new PromocionesJpaController();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

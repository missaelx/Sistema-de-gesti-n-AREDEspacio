package miamifx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modelo.Promociones;
import recursos.PromocionesResource;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Miguel Acosta on 25/04/2017.
 */
public class CrearPromocionController implements Initializable {
    @FXML
    private Button btnGuardar, btnCancelar;
    @FXML
    private TextField campoNombrePromocion, campoDescuento;
    @FXML
    private ComboBox comboTipoPromocion;
    @FXML
    private TextArea campoDescripcion;

    private AdministrarPromocionesController controlPadre;

    public void setControlPadre(AdministrarPromocionesController controlPadre){
        this.controlPadre = controlPadre;
    }


    @FXML
    private void cancelar(ActionEvent actionEvent) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setContentText("Esta seguro que desea cancelar el registro?");
        confirmacion.setTitle("Cancelar Registro");

        if (confirmacion.showAndWait().get().equals(ButtonType.OK)) {
            btnCancelar.getScene().getWindow().hide();
        }

    }


    @FXML
    private void guardarPromocion(ActionEvent actionEvent) {
        PromocionesResource recurso = new PromocionesResource();
        Promociones promocion = new Promociones();
        if (campoNombrePromocion.getText().equals("") || campoDescuento.equals("") || comboTipoPromocion == null) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Los campos no pueden estar vacios, por favor ingresa la informacion completa.");
            alerta.setTitle("Campos vacios");
            alerta.show();
        } else {
            if (isValidPhoneNumber(campoDescuento.getText())) {
                promocion.setTitulo(campoNombrePromocion.getText());
                promocion.setActivo(true);
                promocion.setPorcentajeDescuento(Integer.parseInt(campoDescuento.getText()));
                promocion.setAplicaPara(comboTipoPromocion.getValue().toString());
                if (!campoDescripcion.getText().equals("")) {
                    promocion.setDescripcion(campoDescripcion.getText());
                }
                if (recurso.crearPromocion(promocion)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText("Registro Exitoso, La promocion se ha registrado exitosamente en el sistema");
                    alerta.setTitle("Promocion Registrada");
                    alerta.show();
                    btnGuardar.getScene().getWindow().hide();
                }else{
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText("El registro no se pudo llevar a cabo, por favor intentalo nuevamente");
                    alerta.setTitle("Ocurrio un error");
                    alerta.show();
                }
            }
        }
        controlPadre.setTabla();
    }

    public boolean isValidPhoneNumber(String phone) {
        String regexStr = "^[0-9]*$";
        return phone.matches(regexStr) && phone.length() <= 22;
    }

    public boolean isValidName(String name) {
        return name.matches("([a-z]|[A-Z]|\\s)+") && name.length() <= 45;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboTipoPromocion.getItems().addAll("Inscripcion", "Mensualidad");
    }

}

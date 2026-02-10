package View.Botones;

import static AppConfig.AppConfig.PATH_JSON;
import service.GestorVehiculos;
import Model.Vehiculo;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;

public class BtnModificarPrecios {

    private VBox view;
    private GestorVehiculos<Vehiculo> gestor;
    private TableView<Vehiculo> table;
    private ObservableList<Vehiculo> data;

    public BtnModificarPrecios(GestorVehiculos<Vehiculo> gestor, TableView<Vehiculo> table, ObservableList<Vehiculo> data) {
        this.gestor = gestor;
        this.table = table;
        this.data = data;
        construirFormulario();
    }

    private void construirFormulario() {
        
        Label lblTitulo = new Label("Modificar precio de toda la lista:");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        ComboBox<String> comboAccion = new ComboBox<>();
        comboAccion.getItems().addAll("Aumentar", "Disminuir");
        comboAccion.setValue("Aumentar");

        Label lblPorcentaje = new Label("Porcentaje:");
        TextField txtPorcentaje = new TextField();
        txtPorcentaje.setPromptText("%");

        Button btnAplicar = new Button("Aplicar cambios");

        btnAplicar.setOnAction(e -> {
            try {
                double porcentaje = Double.parseDouble(txtPorcentaje.getText()) / 100.0;
                boolean aumentar = comboAccion.getValue().equals("Aumentar");

                Consumer<Vehiculo> cambioPrecio = v -> {
                    double nuevoPrecio;
                    if (aumentar) {
                        nuevoPrecio = v.getPrecio() * (1 + porcentaje);
                    } else {
                        nuevoPrecio = v.getPrecio() * (1 - porcentaje);
                    }
                    v.setPrecio(nuevoPrecio);
                };

                gestor.aplicarCambios(cambioPrecio);
                gestor.guardarEnJSON(PATH_JSON);
                data.setAll(gestor.getLista());

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Precios actualizados correctamente");
                alert.showAndWait();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ingrese un porcentaje válido");
                alert.showAndWait();
            }
        });


        HBox formulario = new HBox(10, lblTitulo, comboAccion,lblPorcentaje,
                txtPorcentaje, btnAplicar );
        formulario.setPadding(new Insets(10));
        formulario.setAlignment(Pos.CENTER);

        view = new VBox(formulario, new Separator());
    }

    public VBox getView() {
        return view;
    }
}


package View.Botones;

import Model.Vehiculo;
import View.FormularioActualizarView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import service.GestorVehiculos;

public class BtnActualizar extends Button{
    
    private BorderPane root;
    private TableView<Vehiculo> table;
    private GestorVehiculos<Vehiculo> gestor;
    
    public BtnActualizar(BorderPane root, 
                         TableView<Vehiculo> table,
                         GestorVehiculos<Vehiculo> gestor) {
        super(" Actualizar seleccionado");
        
        this.root = root;
        this.table = table;
        this.gestor = gestor;
        
        // Estilo del botón
        this.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        
        configurarAccion();
    }
    
    private void configurarAccion() {
        this.setOnAction(e -> {
            Vehiculo seleccionado = table.getSelectionModel().getSelectedItem();
            
            if (seleccionado != null) {
                FormularioActualizarView formActualizar = 
                    new FormularioActualizarView(root, gestor, seleccionado);
                root.setCenter(formActualizar.getView());
                
            } else {
                mostrarAdvertencia();
            }
        });
    }
    
    
    private void mostrarAdvertencia() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selección requerida");
        alert.setHeaderText("No hay vehículo seleccionado");
        alert.setContentText("Por favor, seleccione un vehículo de la tabla para actualizarlo");
        alert.showAndWait();
    }



}

package View.Botones;

import static AppConfig.AppConfig.PATH_JSON;
import Model.Vehiculo;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import service.GestorVehiculos;

public class BtnEliminar extends Button{
    
    private TableView<Vehiculo> table;
    private ObservableList<Vehiculo> data;
    private GestorVehiculos<Vehiculo> gestor;
    
    public BtnEliminar(TableView<Vehiculo> table, ObservableList<Vehiculo> data, GestorVehiculos<Vehiculo> gestor) {
        super("Eliminar seleccionado");
        this.table = table;
        this.data = data;
        this.gestor = gestor;
        
        this.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold;");

        configurarAccion();
    }
    
    private void configurarAccion() {
        this.setOnAction(e -> {
            Vehiculo seleccionado = table.getSelectionModel().getSelectedItem();
            
            if (seleccionado != null) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText("¿Está seguro que desea eliminar este vehículo?");
                confirmacion.setContentText(
                    "Patente: " + seleccionado.getPatente() + "\n" +
                    "Marca: " + seleccionado.getMarca() + "\n" +
                    "Modelo: " + seleccionado.getModelo() + "\n\n" +
                    "Esta acción no se puede deshacer."
                );
                
                Optional<ButtonType> resultado = confirmacion.showAndWait();
                
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    gestor.eliminarPorPatente(seleccionado.getPatente());                   
                    gestor.guardarEnJSON(PATH_JSON);  
                    data.setAll(gestor.getLista());

                    mostrarExito(seleccionado);
                }
                
            } else {
                // Mostrar advertencia de selección
                mostrarAdvertenciaSeleccion();
            }
        });
    }
    
    private void mostrarExito(Vehiculo vehiculo) {
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("Éxito");
        exito.setHeaderText("Vehículo eliminado");
        exito.setContentText("El vehículo con patente " + vehiculo.getPatente() + " fue eliminado correctamente");
        exito.showAndWait();
    }
    
    private void mostrarAdvertenciaSeleccion() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selección requerida");
        alert.setHeaderText("No hay vehículo seleccionado");
        alert.setContentText("Por favor, seleccione un vehículo de la tabla antes de eliminar");
        alert.showAndWait();
    }
    
}

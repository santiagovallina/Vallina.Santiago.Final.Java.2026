package View.Botones;

import Model.Auto;
import Model.Camion;
import Model.Moto;
import Model.Vehiculo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class BtnLeer extends Button{
    
    private TableView<Vehiculo> table;
    
    public BtnLeer(TableView<Vehiculo> table){
        super("Leer seleccionado");
        
        this.table = table;
        
        this.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        
        configurarAccion();
    }
    
    private void configurarAccion() {
        this.setOnAction(e -> {
            Vehiculo seleccionado = table.getSelectionModel().getSelectedItem();
            
            if (seleccionado != null) {
                mostrarDetalles(seleccionado);
            } else {
                mostrarAdvertenciaSeleccion();
            }
        });
    }
    
    private void mostrarDetalles(Vehiculo vehiculo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle del vehículo");
        alert.setHeaderText("Información completa");
        
        StringBuilder detalles = new StringBuilder();
        detalles.append(String.format("Tipo: %s\n", vehiculo.getClass().getSimpleName()));
        detalles.append(String.format("Patente: %s\n", vehiculo.getPatente()));
        detalles.append(String.format("Modelo: %d\n", vehiculo.getModelo()));
        detalles.append(String.format("Marca: %s\n", vehiculo.getMarca()));
        detalles.append(String.format("Color: %s\n", vehiculo.getColor()));
        detalles.append(String.format("Precio: $%.2f\n", vehiculo.getPrecio()));
        

        if (vehiculo instanceof Auto) {
            Auto auto = (Auto) vehiculo;
            detalles.append("\n--- Detalles de Auto ---\n");
            detalles.append(String.format("Puertas: %d\n", auto.getPuertas()));
            detalles.append(String.format("Motor: %.1f\n", auto.getMotor()));
            
        } else if (vehiculo instanceof Moto) {
            Moto moto = (Moto) vehiculo;
            detalles.append("\n--- Detalles de Moto ---\n");
            detalles.append(String.format("Cilindrada: %d cc\n", moto.getCilindrada()));
            detalles.append(String.format("Peso: %.1f kg\n", moto.getTipoMoto()));
            
        } else if (vehiculo instanceof Camion) {
            Camion camion = (Camion) vehiculo;
            detalles.append("\n--- Detalles de Camión ---\n");
            detalles.append(String.format("Cantidad de ejes: %d\n", camion.getCantidadEjes()));
            detalles.append(String.format("Alto: %d m\n", camion.getAlto()));
            detalles.append(String.format("Largo: %d m\n", camion.getLargo()));
        }
        
        alert.setContentText(detalles.toString());
        alert.showAndWait();
    }
    
    private void mostrarAdvertenciaSeleccion() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selección requerida");
        alert.setHeaderText("No hay vehículo seleccionado");
        alert.setContentText("Por favor, seleccione un vehículo de la tabla para ver sus detalles");
        alert.showAndWait();
    }
    
}

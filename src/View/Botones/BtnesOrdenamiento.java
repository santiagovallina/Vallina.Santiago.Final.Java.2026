package View.Botones;

import Model.Vehiculo;
import service.GestorVehiculos;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Comparator;

import javafx.scene.control.Label;


public class BtnesOrdenamiento {
    
    private VBox panel;
    private ComboBox<String> cmbCriterio;
    private ComboBox<String> cmbDireccion;
    private Button btnOrdenar;
    private Label lblEstado;
    
    public BtnesOrdenamiento(ObservableList<Vehiculo> data, GestorVehiculos<Vehiculo> gestor) {
        
        
        Label lblTitulo = new Label("Ordenar lista:");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label lblCriterio = new Label("Por:");
        
        cmbCriterio = new ComboBox<>();
        cmbCriterio.getItems().addAll("Patente", "Año", "Precio");
        cmbCriterio.setValue("Patente");
        cmbCriterio.setPrefWidth(150);
        cmbCriterio.setStyle("-fx-font-size: 13px;");
        
        HBox boxCriterio = new HBox(8,lblCriterio, cmbCriterio);
        boxCriterio.setAlignment(Pos.CENTER_LEFT);
        
        Label lblDireccion = new Label("Orden:");
        
        cmbDireccion = new ComboBox<>();
        cmbDireccion.getItems().addAll("Ascendente", "Descendente");
        cmbDireccion.setValue("Ascendente");
        cmbDireccion.setPrefWidth(150);
        cmbDireccion.setStyle("-fx-font-size: 13px;");
        
        HBox boxDireccion = new HBox(8, lblDireccion, cmbDireccion);
        boxDireccion.setAlignment(Pos.CENTER_LEFT);
        
        btnOrdenar = new Button("Aplicar Ordenamiento");
        btnOrdenar.setPrefWidth(200);
        

        btnOrdenar.setOnAction(e -> aplicarOrdenamiento(data, gestor));
        
        
        HBox controles = new HBox(10, lblTitulo, boxCriterio, boxDireccion, btnOrdenar);
        controles.setAlignment(Pos.CENTER);
        
        panel = new VBox(10, controles);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(15));
        panel.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-border-color: #ddd; " +
            "-fx-border-radius: 5; " +
            "-fx-background-radius: 5;"
        );
    }
    
    private void aplicarOrdenamiento(ObservableList<Vehiculo> data, GestorVehiculos<Vehiculo> gestor) {
        String criterio = cmbCriterio.getValue();
        String direccion = cmbDireccion.getValue();
        boolean ascendente = direccion.startsWith("Ascendente");
        
        Comparator<Vehiculo> comparator = crearComparador(criterio, ascendente);
        
        if (comparator != null) {
            gestor.ordenar((Comparator) comparator);
            data.setAll(gestor.getLista());
            String simbolo = ascendente ? "Ascendente" : "Descendente";
            
        }
    }
    
    private Comparator<Vehiculo> crearComparador(String criterio, boolean ascendente) {
        Comparator<Vehiculo> comparator = null;
        
        switch (criterio) {
            case "Patente":
                comparator = Comparator.comparing(Vehiculo::getPatente, String.CASE_INSENSITIVE_ORDER);
                break;
                
            case "Año":
                comparator = Comparator.comparingInt(Vehiculo::getModelo);
                break;
                
            case "Precio":
                comparator = Comparator.comparingDouble(Vehiculo::getPrecio);
                break;
        }
        
        // Invertir si es descendente
        if (!ascendente && comparator != null) {
            comparator = comparator.reversed();
        }
        
        return comparator;
    }
    
    public VBox getPanel() {
        return panel;
    }
}


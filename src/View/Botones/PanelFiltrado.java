package View.Botones;

import Model.Vehiculo;
import View.TablaVehiculosView;
import service.GestorVehiculos;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.function.Predicate;

public class PanelFiltrado {
    
    private VBox panel;
    private TextField txtPatente;
    private Label lblResultado;
    
    public PanelFiltrado(BorderPane root, ObservableList<Vehiculo> data, GestorVehiculos<Vehiculo> gestor) {
        
        Label lblTitulo = new Label("Filtrar por patente:");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label lblPatente = new Label("Patente:");
        
        txtPatente = new TextField();
        txtPatente.setPromptText("Ej: ABC123");
        txtPatente.setPrefWidth(150);
        txtPatente.setStyle("-fx-font-size: 13px;");
        
        
        txtPatente.setOnAction(e -> aplicarFiltro(data, gestor));
        
        lblResultado = new Label("");
        
        
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle(
            "-fx-background-color: #2196F3; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px;"
        );
        btnBuscar.setOnAction(e -> aplicarFiltro(data, gestor));
        
        Button btnMostrarTodos = new Button("Mostrar Todos");
        btnMostrarTodos.setStyle(
            "-fx-background-color: #9E9E9E; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px;"
        );
        
        btnMostrarTodos.setOnAction(e -> mostrarTodos(root, gestor));
        
        HBox controles = new HBox(10, lblPatente, txtPatente, btnBuscar, btnMostrarTodos);
        controles.setAlignment(Pos.CENTER);
        
        panel = new VBox(5, lblTitulo, controles, lblResultado);
        panel.setAlignment(Pos.CENTER);
        
    }
    
    private void aplicarFiltro(ObservableList<Vehiculo> data, GestorVehiculos<Vehiculo> gestor) {
        String patenteBuscada = txtPatente.getText().trim();
        
        if (patenteBuscada.isEmpty()) {
            lblResultado.setText("Ingrese una patente para buscar");
            lblResultado.setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold; -fx-font-size: 12px;");
            return;
        }

        Predicate<Vehiculo> predicado = vehiculo -> 
            vehiculo.getPatente().equalsIgnoreCase(patenteBuscada);
        
        System.out.println("Predicate creado: Buscar patente '" + patenteBuscada + "'");
        
        List<Vehiculo> resultados = gestor.filtrar((Predicate) predicado);
        System.out.println("Resultados encontrados: " + resultados.size());
        
        data.setAll(resultados);
        
        if (resultados.isEmpty()) {
            lblResultado.setText("No se encontró ningún vehículo con la patente '" + patenteBuscada + "'");
            lblResultado.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold; -fx-font-size: 12px;");
        } else {
            lblResultado.setText("Se encontró " + resultados.size() + " vehículo(s) con la patente '" + patenteBuscada + "'");
            
        }
    }
    
    private void mostrarTodos(BorderPane root, GestorVehiculos<Vehiculo> gestor) {
        TablaVehiculosView tablaCompleta = new TablaVehiculosView(root, gestor);
        root.setCenter(tablaCompleta.getView());
    }
    
    public VBox getPanel() {
        return panel;
    }
}
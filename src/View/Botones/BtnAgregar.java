package View.Botones;

import View.FormularioAgregarView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import service.GestorVehiculos;

public class BtnAgregar extends Button {
    
    private BorderPane root;
    private GestorVehiculos gestor;
    
    public BtnAgregar(BorderPane root, GestorVehiculos gestor) {
        super("Agregar vehículo");
        
        this.root = root;
        this.gestor = gestor;
        
        this.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        
        configurarAccion();
    }
    
    
    private void configurarAccion() {
        this.setOnAction(e -> {
            FormularioAgregarView formulario = new FormularioAgregarView(root, gestor);
            root.setCenter(formulario.getView());
        });
    }
}
    


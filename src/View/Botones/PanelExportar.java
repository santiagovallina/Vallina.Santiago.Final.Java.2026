package View.Botones;

import AppConfig.AppConfig;
import Model.Vehiculo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.GestorVehiculos;

public class PanelExportar {
    
    private VBox view;
    
    public PanelExportar(GestorVehiculos<Vehiculo> gestor){
        
        Label lblTitulo = new Label("Exportar lista:");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        ComboBox<String> cmbExportar = new ComboBox<>();
        cmbExportar.getItems().addAll("CSV", "Binario");
        cmbExportar.setPromptText("Seleccione formato:");
        cmbExportar.setValue("Tipo de archivo");
        
        Button btnExportar = new Button("Exportar");
        btnExportar.setOnAction(e -> {
            String opcion = cmbExportar.getValue();
            if(opcion != null){
                switch (opcion) {
                    case "CSV":
                        gestor.guardarEnCSV(AppConfig.PATH_CSV);
                        break;
                    case "Binario":
                        gestor.guardarEnBinario(AppConfig.PATH_SER);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Datos exportados correctamente a " + opcion);
                alert.showAndWait();
            }
        });
       
        Separator separator = new Separator();

        HBox formulario = new HBox(10, lblTitulo, cmbExportar, btnExportar,
                separator);
        formulario.setPadding(new Insets(10));
        formulario.setAlignment(Pos.CENTER);

        view = new VBox(formulario, new Separator());


    }
    
    public VBox getView(){
        return view;
    }
    
}

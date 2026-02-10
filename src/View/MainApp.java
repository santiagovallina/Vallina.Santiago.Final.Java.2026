package View;

import static AppConfig.AppConfig.PATH_JSON;
import Model.Auto;
import Model.Camion;
import Model.Color;
import Model.Moto;
import Model.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import service.GestorVehiculos;

public class MainApp extends Application {
    
    List inicializarDatosEjemplo = new ArrayList<>();
    
    @Override
    public void start(Stage stage) {
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Gestión de Vehículos");
        
        GestorVehiculos<Vehiculo> gestor = new GestorVehiculos<>();
        
        BienvenidaView bienvenida = new BienvenidaView(root, gestor);
        root.setCenter(bienvenida.getView());
        
        Vehiculo v = new Auto("ZAE321", 2020, "CHEVROLET", Color.AZUL, 6000000);
        Vehiculo v1 = new Auto("ABC123", 2020, "TOYOTA", Color.ROJO, 5000000);
        Vehiculo v2 = new Auto("DEF456", 2019, "FORD", Color.AZUL, 4500000);
        Vehiculo v3 = new Auto("GHI789", 2021, "FIAT", Color.BLANCO, 3800000);
        Vehiculo c1 = new Camion("PLD789", 2021, "SCANIA", Color.VERDE, 7800000);
        Vehiculo m1 = new Moto("RTE123", 2021, "HONDA", Color.VERDE, 1800000);
        
        inicializarDatosEjemplo.add(v);
        inicializarDatosEjemplo.add(v1);
        inicializarDatosEjemplo.add(v2);
        inicializarDatosEjemplo.add(v3);
        inicializarDatosEjemplo.add(c1);
        inicializarDatosEjemplo.add(m1);
        
        try {
            List<Vehiculo> vehiculosGuardados = gestor.cargarDesdeJSON(PATH_JSON);

            if (vehiculosGuardados != null && !vehiculosGuardados.isEmpty()) {
                for (Vehiculo ve : vehiculosGuardados) {
                    gestor.agregar(ve);
                    System.out.println("Datos cargados: " + vehiculosGuardados.size() + " vehículos");
                }
            }
            else {
                for ( Object ve : inicializarDatosEjemplo){
                    gestor.agregar((Vehiculo) ve);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        
        
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


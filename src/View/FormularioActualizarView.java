package View;

import static AppConfig.AppConfig.PATH_JSON;
import Model.*;
import View.Botones.ValidadorVehiculo;
import java.text.DecimalFormat;
import service.GestorVehiculos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormularioActualizarView {
    private VBox view;
    private GestorVehiculos<Vehiculo> gestor;
    private Vehiculo vehiculo;
    
    public FormularioActualizarView(BorderPane root, GestorVehiculos<Vehiculo> gestor, Vehiculo actualizado) {
        this.gestor = gestor;
        this.vehiculo = actualizado;
        construirFormulario(root);
    }
    
    private void construirFormulario(BorderPane root) {
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        
        // Campos prellenados
        TextField txtPatente = new TextField(vehiculo.getPatente().toUpperCase());
        txtPatente.setDisable(true); // la patente no debería cambiar
        
        TextField txtMarca = new TextField(vehiculo.getMarca().toUpperCase());
        txtMarca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtMarca.setText(newValue.toUpperCase());
            }
        });
        
        TextField txtModelo = new TextField(String.valueOf(vehiculo.getModelo()));
        
        // ComboBox para color en lugar de TextField
        ComboBox<Color> cmbColor = new ComboBox<>();
        cmbColor.getItems().addAll(Color.values());
        cmbColor.setValue(vehiculo.getColor());
        
        TextField txtPrecio = new TextField(String.valueOf(vehiculo.getPrecio()));
        
        Button btnGuardar = new Button("Guardar cambios");
        btnGuardar.setOnAction(e -> {
            
            // Validar marca
            ValidadorVehiculo.ResultadoValidacion resultadoMarca = 
                ValidadorVehiculo.validarMarcaNoVacia(txtMarca);
            if (!resultadoMarca.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoMarca);
                return;
            }
            
            // Validar año es número
            ValidadorVehiculo.ValidacionAnio validacionAnioNumero = 
                ValidadorVehiculo.validarAnioNumero(txtModelo);
            if (!validacionAnioNumero.valido) {
                ValidadorVehiculo.mostrarErrorAnio(validacionAnioNumero);
                return;
            }
            int modelo = validacionAnioNumero.anio;
            
            // Validar rango de año
            ValidadorVehiculo.ResultadoValidacion resultadoAnioRango = 
                ValidadorVehiculo.validarAnioRango(txtModelo, modelo);
            if (!resultadoAnioRango.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoAnioRango);
                return;
            }
            
            // Validar color
            ValidadorVehiculo.ResultadoValidacion resultadoColor = 
                ValidadorVehiculo.validarColorSeleccionado(cmbColor);
            if (!resultadoColor.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoColor);
                return;
            }
            
            // Validar precio es número
            ValidadorVehiculo.ValidacionPrecio validacionPrecioNumero = 
                ValidadorVehiculo.validarPrecioNumero(txtPrecio);
            if (!validacionPrecioNumero.valido) {
                ValidadorVehiculo.mostrarErrorPrecio(validacionPrecioNumero);
                return;
            }
            double precio = validacionPrecioNumero.precio;
            
            // Validar precio positivo
            ValidadorVehiculo.ResultadoValidacion resultadoPrecioPositivo = 
                ValidadorVehiculo.validarPrecioPositivo(txtPrecio, precio);
            if (!resultadoPrecioPositivo.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoPrecioPositivo);
                return;
            }
            
            // Obtener valores validados
            String marca = txtMarca.getText().trim();
            Color color = cmbColor.getValue();
            
            // Crear vehículo actualizado según su tipo
            Vehiculo actualizado = null;
            if (vehiculo instanceof Auto) {
                Auto autoOriginal = (Auto) vehiculo;
                actualizado = new Auto(
                    vehiculo.getPatente(), 
                    modelo, 
                    marca, 
                    color, 
                    precio,
                    autoOriginal.getPuertas(),
                    autoOriginal.getMotor()
                );
            } else if (vehiculo instanceof Moto) {
                Moto motoOriginal = (Moto) vehiculo;
                actualizado = new Moto(
                    vehiculo.getPatente(), 
                    modelo, 
                    marca, 
                    color, 
                    precio,
                    motoOriginal.getCilindrada()
                );
            } else if (vehiculo instanceof Camion) {
                Camion camionOriginal = (Camion) vehiculo;
                actualizado = new Camion(
                    vehiculo.getPatente(), 
                    modelo, 
                    marca, 
                    color, 
                    precio
                    
                );
            }
            
            if (actualizado != null) {
                gestor.actualizar(vehiculo.getPatente(), actualizado);
                gestor.guardarEnJSON(PATH_JSON);
                
                root.setCenter(new TablaVehiculosView(root, gestor).getView()); 
            }
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> {
            root.setCenter(new TablaVehiculosView(root, gestor).getView());
        });
        
        // Añadir al grid
        grid.add(new Label("Patente:"), 0, 0);
        grid.add(txtPatente, 1, 0);
        grid.add(new Label("Marca:"), 0, 1);
        grid.add(txtMarca, 1, 1);
        grid.add(new Label("Modelo:"), 0, 2);
        grid.add(txtModelo, 1, 2);
        grid.add(new Label("Color:"), 0, 3);
        grid.add(cmbColor, 1, 3);
        grid.add(new Label("Precio:"), 0, 4);
        grid.add(txtPrecio, 1, 4);
        
        HBox botonesBox = new HBox(10, btnGuardar, btnCancelar);
        grid.add(botonesBox, 1, 5);
        
        view = new VBox(grid);
    }
    
    public VBox getView() {
        return view;
    }
}

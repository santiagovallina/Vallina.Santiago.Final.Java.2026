package View;

import static AppConfig.AppConfig.PATH_JSON;
import Model.Auto;
import Model.Camion;
import Model.Color;
import Model.Moto;
import Model.TipoMoto;
import Model.Vehiculo;
import View.Botones.ValidadorVehiculo;
import View.TablaVehiculosView;
import java.text.DecimalFormat;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.GestorVehiculos;

public class FormularioAgregarView {
    private VBox view;
    private GestorVehiculos<Vehiculo> gestor;
    
    public FormularioAgregarView(BorderPane root, GestorVehiculos<Vehiculo> gestor) {
        this.gestor = gestor;
        construirFormulario(root);
    }
    
    private void construirFormulario(BorderPane root) {
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        
        // Título
        Label lblTitulo = new Label("Agregar nuevo vehículo");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Campos del formulario
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Auto", "Moto", "Camión");
        cmbTipo.setPromptText("Seleccione el tipo de vehículo");
        
        TextField txtPatente = new TextField();
        txtPatente.setPromptText("Patente");
        txtPatente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtPatente.setText(newValue.toUpperCase());
            }
        });
        
        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");
        txtMarca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtMarca.setText(newValue.toUpperCase());
            }
        });
        
        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Año");
        
        ComboBox<Color> cmbColor = new ComboBox<>();
        cmbColor.getItems().addAll(Color.values());
        cmbColor.setPromptText("Seleccione el color del vehículo");
        
        DecimalFormat df = new DecimalFormat("0");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        
        // Campos específicos para Auto
        Label lblPuertas = new Label("Puertas:");
        TextField txtPuertas = new TextField();
        txtPuertas.setPromptText("Cantidad de puertas");
        
        Label lblMotor = new Label("Motor:");
        TextField txtMotor = new TextField();
        txtMotor.setPromptText("Cilindrada del motor (ej: 1.6)");
        
        // Campos específicos para Moto
        Label lblTipoMoto = new Label("Tipo de moto:");
        ComboBox<TipoMoto> cmbTipoMoto = new ComboBox<>();
        cmbTipoMoto.getItems().addAll(TipoMoto.values());
        cmbTipoMoto.setPromptText("Seleccione el tipo de moto");
        
        Label lblCilindrada = new Label("Cilindrada:");
        TextField txtCilindrada = new TextField();
        txtCilindrada.setPromptText("Cilindrada (ej: 500)");
        
        // Campos específicos para Camión
        Label lblEjes = new Label("Cantidad de ejes:");
        TextField txtEjes = new TextField();
        txtEjes.setPromptText("Cantidad de ejes");
        
        Label lblAlto = new Label("Alto:");
        TextField txtAlto = new TextField();
        txtAlto.setPromptText("Alto en cm");
        
        Label lblLargo = new Label("Largo:");
        TextField txtLargo = new TextField();
        txtLargo.setPromptText("Largo en cm");
        
        // Inicialmente ocultos
        lblPuertas.setVisible(false);
        txtPuertas.setVisible(false);
        lblMotor.setVisible(false);
        txtMotor.setVisible(false);
        
        lblTipoMoto.setVisible(false);
        cmbTipoMoto.setVisible(false);
        lblCilindrada.setVisible(false);
        txtCilindrada.setVisible(false);
        
        lblEjes.setVisible(false);
        txtEjes.setVisible(false);
        lblAlto.setVisible(false);
        txtAlto.setVisible(false);
        lblLargo.setVisible(false);
        txtLargo.setVisible(false);
        
        // Listener para mostrar campos según tipo seleccionado
        cmbTipo.setOnAction(e -> {
            String tipoSeleccionado = cmbTipo.getValue();
            
            // Ocultar todos primero
            lblPuertas.setVisible(false);
            txtPuertas.setVisible(false);
            lblMotor.setVisible(false);
            txtMotor.setVisible(false);
            
            lblTipoMoto.setVisible(false);
            cmbTipoMoto.setVisible(false);
            lblCilindrada.setVisible(false);
            txtCilindrada.setVisible(false);
            
            lblEjes.setVisible(false);
            txtEjes.setVisible(false);
            lblAlto.setVisible(false);
            txtAlto.setVisible(false);
            lblLargo.setVisible(false);
            txtLargo.setVisible(false);
            
            // Mostrar según tipo
            if (tipoSeleccionado != null) {
                switch (tipoSeleccionado) {
                    case "Auto":
                        lblPuertas.setVisible(true);
                        txtPuertas.setVisible(true);
                        lblMotor.setVisible(true);
                        txtMotor.setVisible(true);
                        break;
                    case "Moto":
                        lblTipoMoto.setVisible(true);
                        cmbTipoMoto.setVisible(true);
                        lblCilindrada.setVisible(true);
                        txtCilindrada.setVisible(true);
                        break;
                    case "Camión":
                        lblEjes.setVisible(true);
                        txtEjes.setVisible(true);
                        lblAlto.setVisible(true);
                        txtAlto.setVisible(true);
                        lblLargo.setVisible(true);
                        txtLargo.setVisible(true);
                        break;
                }
            }
        });
        
        // Botones
        Button btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> {
            
            // Validar tipo de vehículo
            ValidadorVehiculo.ResultadoValidacion resultadoTipo = 
                ValidadorVehiculo.validarTipoSeleccionado(cmbTipo);
            if (!resultadoTipo.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoTipo);
                return;
            }
            
            // Validar patente no vacía
            ValidadorVehiculo.ResultadoValidacion resultadoPatente = 
                ValidadorVehiculo.validarPatenteNoVacia(txtPatente);
            if (!resultadoPatente.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoPatente);
                return;
            }
            
            // Validar patente única
            ValidadorVehiculo.ResultadoValidacion resultadoPatenteUnica = 
                ValidadorVehiculo.validarPatenteUnica(txtPatente, gestor);
            if (!resultadoPatenteUnica.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoPatenteUnica);
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
            
            // Validar marca
            ValidadorVehiculo.ResultadoValidacion resultadoMarca = 
                ValidadorVehiculo.validarMarcaNoVacia(txtMarca);
            if (!resultadoMarca.esValido()) {
                ValidadorVehiculo.mostrarError(resultadoMarca);
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
            String patente = txtPatente.getText().trim();
            String marca = txtMarca.getText().trim();
            Color color = cmbColor.getValue();
            
            // Crear vehículo según tipo
            Vehiculo nuevo = null;
            switch (cmbTipo.getValue()) { 
                case "Auto":
                    nuevo = new Auto(patente, modelo, marca, color, precio, 5, 1.6);
                    break;
                case "Moto":
                    nuevo = new Moto(patente, modelo, marca, color, precio, 500);
                    break;
                case "Camión":
                    nuevo = new Camion(patente, modelo, marca, color, precio, 10000);
                    break;
            }
            
            if (nuevo != null) { 
                gestor.getLista();
                gestor.agregar(nuevo);
                gestor.guardarEnJSON(PATH_JSON);
                root.setCenter(new TablaVehiculosView(root, gestor).getView());
            }
                 
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> {
            root.setCenter(new TablaVehiculosView(root, gestor).getView());
        });
        
        // Añadir al grid
        grid.add(lblTitulo, 0, 0, 2, 1);
        grid.add(new Label("Tipo de vehículo:"), 0, 1);
        grid.add(cmbTipo, 1, 1);
        grid.add(new Label("Patente:"), 0, 2);
        grid.add(txtPatente, 1, 2);
        grid.add(new Label("Marca:"), 0, 3);
        grid.add(txtMarca, 1, 3);
        grid.add(new Label("Año:"), 0, 4);
        grid.add(txtModelo, 1, 4);
        grid.add(new Label("Color:"), 0, 5);
        grid.add(cmbColor, 1, 5);
        grid.add(new Label("Precio:"), 0, 6);
        grid.add(txtPrecio, 1, 6);
        
        // Campos específicos de Auto
        grid.add(lblPuertas, 0, 7);
        grid.add(txtPuertas, 1, 7);
        grid.add(lblMotor, 0, 8);
        grid.add(txtMotor, 1, 8);
        
        // Campos específicos de Moto
        grid.add(lblTipoMoto, 0, 7);
        grid.add(cmbTipoMoto, 1, 7);
        grid.add(lblCilindrada, 0, 8);
        grid.add(txtCilindrada, 1, 8);
        
        // Campos específicos de Camión
        grid.add(lblEjes, 0, 7);
        grid.add(txtEjes, 1, 7);
        grid.add(lblAlto, 0, 8);
        grid.add(txtAlto, 1, 8);
        grid.add(lblLargo, 0, 9);
        grid.add(txtLargo, 1, 9);
        
        HBox botonesBox = new HBox(10, btnAgregar, btnCancelar);
        grid.add(botonesBox, 1, 10);
        
        view = new VBox(grid);
    }

    public VBox getView() {
        return view;
    }
    
}
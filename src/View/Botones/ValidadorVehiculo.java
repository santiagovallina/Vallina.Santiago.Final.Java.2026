package View.Botones;

import Model.Color;
import Model.Vehiculo;
import java.time.Year;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.GestorVehiculos;

public class ValidadorVehiculo {
    
    /**
     * Resultado de validación
     */
    public static class ResultadoValidacion {
        private boolean valido;
        private String mensajeTitulo;
        private String mensajeContenido;
        
        public ResultadoValidacion(boolean valido, String titulo, String contenido) {
            this.valido = valido;
            this.mensajeTitulo = titulo;
            this.mensajeContenido = contenido;
        }
        
        public boolean esValido() {
            return valido;
        }
        
        public String getMensajeTitulo() {
            return mensajeTitulo;
        }
        
        public String getMensajeContenido() {
            return mensajeContenido;
        }
    }
    
    
    //Valida que un ComboBox tenga una selección 
    public static ResultadoValidacion validarTipoSeleccionado(ComboBox<String> cmbTipo) {
        if (cmbTipo.getValue() == null || cmbTipo.getValue().isEmpty()) {
            cmbTipo.requestFocus();
            return new ResultadoValidacion(
                false,
                "Tipo de vehículo requerido",
                "Debe seleccionar un tipo de vehículo (Auto, Moto o Camión)"
            );
        }
        return new ResultadoValidacion(true, null, null);
    }
    
    
    //Valida que la patente no esté vacía 
    public static ResultadoValidacion validarPatenteNoVacia(TextField txtPatente) {
        String patente = txtPatente.getText().trim();
        if (patente.isEmpty()) {
            txtPatente.requestFocus();
            return new ResultadoValidacion(
                false,
                "Patente requerida",
                "La patente no puede estar vacía"
            );
        }
        return new ResultadoValidacion(true, null, null);
    }
    

    //Valida que la patente sea única (no duplicada)
    public static ResultadoValidacion validarPatenteUnica(TextField txtPatente, GestorVehiculos<Vehiculo> gestor) {
        String patente = txtPatente.getText().trim();
        
        for (Vehiculo v : gestor.getLista()) {
            if (v.getPatente().equalsIgnoreCase(patente)) {
                txtPatente.requestFocus();
                txtPatente.selectAll();
                return new ResultadoValidacion(
                    false,
                    "Patente duplicada",
                    "Ya existe un vehículo con la patente '" + patente + "'"
                );
            }
        }
        return new ResultadoValidacion(true, null, null);
    }
    
    /*
    Valida y obtiene el año (modelo) como entero
    Retorna null si no es válido */
    public static class ValidacionAnio {
        public boolean valido;
        public Integer anio;
        public String titulo;
        public String mensaje;
        
        public ValidacionAnio(boolean valido, Integer anio, String titulo, String mensaje) {
            this.valido = valido;
            this.anio = anio;
            this.titulo = titulo;
            this.mensaje = mensaje;
        }
    }
    
    //Valida que el año sea un número válidO
    public static ValidacionAnio validarAnioNumero(TextField txtModelo) {
        try {
            int anio = Integer.parseInt(txtModelo.getText().trim());
            return new ValidacionAnio(true, anio, null, null);
        } catch (NumberFormatException ex) {
            txtModelo.requestFocus();
            txtModelo.selectAll();
            return new ValidacionAnio(
                false,
                null,
                "Año inválido",
                "El año debe ser un número entero válido (ej: 2020)"
            );
        }
    }
    
    
    //Valida que el año esté en un rango razonable
    public static ResultadoValidacion validarAnioRango(TextField txtModelo, int anio) {
        int anioActual = java.time.Year.now().getValue();
        
        if (anio < 1900 || anio > anioActual + 1) {
            txtModelo.requestFocus();
            txtModelo.selectAll();
            return new ResultadoValidacion(
                false,
                "Año fuera de rango",
                "El año debe estar entre 1900 y " + (anioActual + 1)
            );
        }
        return new ResultadoValidacion(true, null, null);
    }
    
    //Valida que la marca no esté vacía
    public static ResultadoValidacion validarMarcaNoVacia(TextField txtMarca) {
        String marca = txtMarca.getText().trim();
        if (marca.isEmpty()) {
            txtMarca.requestFocus();
            return new ResultadoValidacion(
                false,
                "Marca requerida",
                "La marca no puede estar vacía"
            );
        }
        return new ResultadoValidacion(true, null, null);
    }
    
    //Valida que se haya seleccionado un color
    public static ResultadoValidacion validarColorSeleccionado(ComboBox<Color> cmbColor) {
        if (cmbColor.getValue() == null) {
            cmbColor.requestFocus();
            return new ResultadoValidacion(
                false,
                "Color requerido",
                "Debe seleccionar un color"
            );
        }
        return new ResultadoValidacion(true, null, null);
    }
    
    //Valida y obtiene el precio como double
    public static class ValidacionPrecio {
        public boolean valido;
        public Double precio;
        public String titulo;
        public String mensaje;
        
        public ValidacionPrecio(boolean valido, Double precio, String titulo, String mensaje) {
            this.valido = valido;
            this.precio = precio;
            this.titulo = titulo;
            this.mensaje = mensaje;
        }
    }
    
    //Valida que el precio sea un número válido
    public static ValidacionPrecio validarPrecioNumero(TextField txtPrecio) {
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            return new ValidacionPrecio(true, precio, null, null);
        } catch (NumberFormatException ex) {
            txtPrecio.requestFocus();
            txtPrecio.selectAll();
            return new ValidacionPrecio(
                false,
                null,
                "Precio inválido",
                "El precio debe ser un número válido (ej: 5000000)"
            );
        }
    }
    
    //Valida que el precio sea positivo
    public static ResultadoValidacion validarPrecioPositivo(TextField txtPrecio, double precio) {
        if (precio <= 0) {
            txtPrecio.requestFocus();
            txtPrecio.selectAll();
            return new ResultadoValidacion(
                false,
                "Precio inválido",
                "El precio debe ser mayor a cero"
            );
        }
        return new ResultadoValidacion(true, null, null);
    }
    
    //Muestra un mensaje de error
    public static void mostrarError(ResultadoValidacion resultado) {
        if (!resultado.esValido()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Validación");
            alert.setHeaderText(resultado.getMensajeTitulo());
            alert.setContentText(resultado.getMensajeContenido());
            alert.showAndWait();
        }
    }
    
    //Muestra mensaje de error para validaciones de año
    public static void mostrarErrorAnio(ValidacionAnio validacion) {
        if (!validacion.valido) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Validación");
            alert.setHeaderText(validacion.titulo);
            alert.setContentText(validacion.mensaje);
            alert.showAndWait();
        }
    }
    
    //Muestra mensaje de error para validaciones de precio
    public static void mostrarErrorPrecio(ValidacionPrecio validacion) {
        if (!validacion.valido) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Validación");
            alert.setHeaderText(validacion.titulo);
            alert.setContentText(validacion.mensaje);
            alert.showAndWait();
        }
    }
}

    


package Model;

import java.io.Serializable;
import java.time.LocalDate;
import service.CSVSerializable;

public abstract class Vehiculo implements Comparable<Vehiculo>, CSVSerializable, Serializable{
    
    private String patente;
    private int modelo;
    private String marca;
    private Color color;
    private double precio;

    public Vehiculo(String patente, int modelo, String marca, Color color, double precio) {
        this.patente = patente;
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "patente=" + patente + ", modelo=" + modelo + ", marca=" + marca + ", color=" + color + ", precio=" + precio + '}';
    }
    
    
    public void encender(){
        System.out.println("Encender vehiculo");
    }
    
    public void acelerar(){
        System.out.println("Acelerar vehiculo");
    }
    
    public void frenar(){
        System.out.println("Frenar vehiculo");
    }

    public String getPatente() {
        return patente;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
    @Override
    public int compareTo(Vehiculo otro) {
        return this.patente.compareToIgnoreCase(otro.patente);
    }
    
    @Override
    public String toCSV(){
        return patente + ", " + modelo + ", " + marca + ", " + color.toString() + ", " +precio;
    }
    
    @Override
    public String toHeaderCSV() {
        return "patente,modelo,marca,color,precio";
    }
    
    public static Vehiculo fromCSV(String linea) {
    String[] values = linea.split(",");
    for (int i = 0; i < values.length; i++) {
        values[i] = values[i].trim(); // limpia espacios extra
    }
    String tipo = values[0];

        switch (tipo.toUpperCase()) {
            case "AUTO":
                return new Auto(
                    values[1], // patente
                    Integer.parseInt(values[2]), // modelo
                    values[3], // marca
                    Color.valueOf(values[4]), // color
                    Double.parseDouble(values[5]) // precio
                );
                

            case "MOTO":
                return new Moto(
                    values[1],
                    Integer.parseInt(values[2]),
                    values[3],
                    Color.valueOf(values[4].toUpperCase()),
                    Double.parseDouble(values[5])
                );

            case "CAMION":
                return new Camion(
                    values[1],
                    Integer.parseInt(values[2]),
                    values[3],
                    Color.valueOf(values[4].toUpperCase()),
                    Double.parseDouble(values[5])
                );

            default:
                throw new IllegalArgumentException("Tipo de vehículo desconocido: " + tipo);
        }
}
 
}

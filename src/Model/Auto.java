package Model;

import java.io.Serializable;
import service.CSVSerializable;

public class Auto extends Vehiculo implements Cargable, CSVSerializable, Serializable{
    
    private int puertas;
    private double motor;

    public Auto(String patente, int modelo, String marca, Color color, double precio, int puertas, double motor) {
        super(patente, modelo, marca, color, precio);
        this.puertas = puertas;
        this.motor = motor;
    }
    

    public Auto(String patente, int modelo, String marca,  Color color, double precio,  double motor) {
        super(patente, modelo, marca, color, precio);
        this.motor = motor;
    }
    
    public Auto(String patente, int modelo, String marca,  Color color, double precio) {
        super(patente, modelo, marca, color, precio);
    }

    @Override
    public String toString() {
        return super.toString() + "Auto{" + "puertas=" + puertas + ", motor=" + motor + '}';
    }
    
    
    
    @Override
    public void encender(){
        System.out.println("Encendiendo el auto");
    }
    
    @Override
    public void acelerar(){
        System.out.println("Acelerando el auto");
    }
    
    @Override
    public void frenar(){
        System.out.println("Frenando el auto");
    }
    
    @Override
    public void cargarVehiculo() {
        System.out.println("Cargando el baul del auto.");
    }
    
    
    @Override
    public String toHeaderCSV() {
        return "patente,modelo,marca,color,precio, puertas, motor";
    }

    
    
    
}

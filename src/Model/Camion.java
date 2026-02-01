package Model;

import java.io.Serializable;
import service.CSVSerializable;

public class Camion extends Vehiculo implements Cargable, CSVSerializable, Serializable{
    
    private int cantidadEjes;
    private int alto;
    private int largo;

    public Camion(String patente, int modelo, String marca, Color color, double precio, int cantidadEjes, int alto, int largo) {
        super(patente, modelo, marca, color, precio);
        this.cantidadEjes = cantidadEjes;
        this.alto = alto;
        this.largo = largo;
    }


    public Camion(String patente, int modelo, String marca, Color color, double precio, int cantidadEjes) {
        super(patente, modelo, marca, color, precio);
        this.cantidadEjes = cantidadEjes;
    }
    
    public Camion(String patente, int modelo, String marca, Color color, double precio) {
        super(patente, modelo, marca, color, precio);
    }

    @Override
    public String toString() {
        return super.toString() + "Camion{" + "cantidadEjes=" + cantidadEjes + ", alto=" + alto + ", largo=" + largo + '}';
    }
    
 
    @Override
    public void encender(){
        System.out.println("Encendiendo el camion");
    }
    
    @Override
    public void acelerar(){
        System.out.println("Acelerando el camion");
    }
    
    @Override
    public void frenar(){
        System.out.println("Frenando el camion");
    }

    @Override
    public void cargarVehiculo() {
        System.out.println("Cargando mercadería en el camión");
    }

    
    
    @Override
    public String toHeaderCSV() {
        return "patente,modelo,marca,color,precio,cantidad de ejes, alto, largo";
    }

  
}

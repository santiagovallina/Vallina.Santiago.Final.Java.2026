package Model;

import java.io.Serializable;
import service.CSVSerializable;

public class Moto extends Vehiculo implements CSVSerializable, Serializable{
    
    private int cilindrada;
    private TipoMoto tipo;

    public Moto(String patente, int modelo, String marca, Color color, double precio,  int cilindrada, TipoMoto tipo) {
        super(patente, modelo, marca, color, precio);
        this.cilindrada = cilindrada;
        this.tipo = tipo;
    }
    
    public Moto(String patente, int modelo, String marca,  Color color, double precio, int cilindrada) {
        super(patente, modelo, marca, color, precio);
        this.cilindrada = cilindrada;
    }
    
    public Moto(String patente, int modelo, String marca,  Color color, double precio) {
        super(patente, modelo, marca, color, precio);
    }

    @Override
    public String toString() {
        return super.toString() + "Moto{" + "cilindrada=" + cilindrada + ", tipo=" + tipo + '}';
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public TipoMoto getTipoMoto() {
        return tipo;
    }
    
    @Override
    public void encender(){
        System.out.println("Encendiendo la moto");
    }
    
    @Override
    public void acelerar(){
        System.out.println("Acelerando la moto");
    }
    
    @Override
    public void frenar(){
        System.out.println("Frenando la moto");
    }

    
    
    @Override
    public String toHeaderCSV() {
        return "patente,modelo,marca,color,precio,cilindrada,tipo";
    }
    
    
    
    
}
